package com.asiainfo.imageprocess.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.asiainfo.imageprocess.R;

public class ImageProcessActivity extends Activity implements View.OnClickListener {

    private Button mBtnPrimaryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_process);

        initView();
        initListener();

    }

    private void initListener() {

        mBtnPrimaryColor.setOnClickListener(this);

    }

    private void initView() {

        mBtnPrimaryColor = (Button) findViewById(R.id.btn_primary_color);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_primary_color:

                startActivity(new Intent(this, PrimaryColorActivity.class));

                break;

        }

    }
}
