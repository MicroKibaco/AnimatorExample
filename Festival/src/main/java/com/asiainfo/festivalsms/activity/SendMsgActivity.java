package com.asiainfo.festivalsms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.bean.Festival;
import com.asiainfo.festivalsms.bean.FestivalLib;
import com.asiainfo.festivalsms.bean.MsgBean;
import com.asiainfo.festivalsms.utils.JumpUtil;
import com.asiainfo.festivalsms.view.FlowLayout;

public class SendMsgActivity extends Activity {

    private int mFestivalId;
    private int msgId;

    private Festival mFestival;
    private MsgBean mMsgBean;

    private EditText mEdMsg;
    private Button mBtnAdd;
    private FlowLayout mlContacts;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        initView();
        initDatas();
    }

    private void initView() {

        mEdMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button) findViewById(R.id.id_btn_add);
        mlContacts = (FlowLayout) findViewById(R.id.id_fl_content);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading = findViewById(R.id.id_layout_loading);



    }

    private void initDatas() {

        mFestivalId = getIntent().getIntExtra(JumpUtil.KEY_FESTIVAL_ID, -1);
        msgId = getIntent().getIntExtra(JumpUtil.KEY_MSG_ID, -1);
        mLayoutLoading.setVisibility(View.GONE);
        if (msgId != -1) {

            mMsgBean =  FestivalLib.getInstance().getMsgBeanById(msgId);
            mEdMsg.setText(mMsgBean.getContent());

        }
        mFestival = FestivalLib.getInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
    }
}
