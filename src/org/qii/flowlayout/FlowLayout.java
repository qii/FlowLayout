package org.qii.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * User: qii
 * Date: 13-8-10
 */
public class FlowLayout extends ViewGroup {

    private int maxLine = -1;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
        requestLayout();
    }

    public void addViewAndSetMaxLine(View child, int maxLine) {
        this.maxLine=maxLine;
        addView(child);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED) {

            throw new IllegalStateException("FlowLayout can not be used with "
                    + "measure spec mode=UNSPECIFIED");
        }


        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = l + getPaddingLeft();
        int right = r - getPaddingRight();
        int top = t + getPaddingTop();
        int bottom = b - getPaddingBottom();

        int currentWidth = left;
        int currentHeight = 0;

        int currentLine = 1;

        int maxChildHeight = 0;

        int childCount=getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                if (currentWidth + childWidth + layoutParams.leftMargin + layoutParams.rightMargin > right) {

                    if (maxLine > 0 && currentLine + 1 > maxLine)
                        break;

                    currentWidth = left;
                    currentHeight += maxChildHeight;
                    maxChildHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                    currentLine++;
                } else {
                    maxChildHeight = Math.max(maxChildHeight, childHeight + layoutParams.topMargin + layoutParams.bottomMargin);

                }

                child.layout(currentWidth + layoutParams.leftMargin, currentHeight + layoutParams.topMargin,
                        currentWidth + layoutParams.leftMargin + childWidth, currentHeight + layoutParams.topMargin + childHeight);
                currentWidth += (childWidth + layoutParams.leftMargin + layoutParams.rightMargin);


            }
        }

    }


    @Override
    public ViewGroup.MarginLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ViewGroup.MarginLayoutParams(getContext(), attrs);
    }


    @Override
    protected ViewGroup.MarginLayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.MarginLayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new ViewGroup.MarginLayoutParams(p);
    }


    // Override to allow type-checking of LayoutParams.
    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof ViewGroup.MarginLayoutParams;
    }


}
