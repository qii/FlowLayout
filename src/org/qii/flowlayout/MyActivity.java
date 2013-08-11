package org.qii.flowlayout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FlowLayout flowlayout=(FlowLayout)findViewById(R.id.flowlayout);

        for(int i=0;i<20;i++){
            Button button=new Button(MyActivity.this);
            button.setText("sd"+System.currentTimeMillis());
            flowlayout.addViewAndSetMaxLine(button, 5);
        }

//        flowlayout.setMaxLine(5);
    }
}
