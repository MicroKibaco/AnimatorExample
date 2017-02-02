package com.asiainfo.flowlayout.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;

import com.asiainfo.flowlayout.R;
import com.asiainfo.flowlayout.view.FlowLayout;

public class FlowActivity extends Activity {

    private String[] mValues = new String[]{
            "亚信", "华为", "阿里巴巴", "腾讯", "百度", "京东", "途牛", "携程",
            "美团", "美丽说", "微软", "谷歌", "饿了么", "飞牛", "小影", "58同城",
            "咖啡之翼", "小矮人", "芒果TV", "映客", "VR", "图灵机器人", "mob", "高德"
    };

    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        mFlowLayout = (FlowLayout) findViewById(R.id.flow_layout);
        initDatas();
    }

    private void initDatas() {

        for (int i = 0; i < mValues.length; i++) {
            Button btn = new Button(this);

            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams
                    (ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);

            btn.setText(mValues[i]);
            mFlowLayout.addView(btn, lp);


        }
    }
}
