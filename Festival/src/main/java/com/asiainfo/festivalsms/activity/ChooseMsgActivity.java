package com.asiainfo.festivalsms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.bean.FestivalLib;
import com.asiainfo.festivalsms.bean.MsgBean;
import com.asiainfo.festivalsms.fragment.FestivalCategoryFragment;
import com.asiainfo.festivalsms.utils.JumpUtil;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日18点16分 描述:
 */
public class ChooseMsgActivity extends Activity implements View.OnClickListener {

    //祝福短信条目
    private ListView mLvMsg;

    //浮点发送控件Button
    private FloatingActionButton mFabToSend;

    private ArrayAdapter<MsgBean> mAdapter;

    private int mFestivalId;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_msg);

        initView();
        initEvent();

    }


    private void initEvent() {

        mFabToSend.setOnClickListener(this);

    }


    private void initView() {
        mInflater = LayoutInflater.from(this);
        mFestivalId = getIntent().getIntExtra(FestivalCategoryFragment.ID_FESTIVAL,-1);
        setTitle(FestivalLib.getInstance().getFestivalById(mFestivalId).getName());

        mLvMsg = (ListView) findViewById(R.id.id_lv_msgs);
        mFabToSend = (FloatingActionButton) findViewById(R.id.id_fab_toSend);


        mLvMsg.setAdapter(mAdapter = new ArrayAdapter<MsgBean>(this,-1,
        FestivalLib.getInstance().getMsgsByFestivalById(mFestivalId)){

            @NonNull
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null){

                    convertView = mInflater.inflate(R.layout.item_msg,parent,false);

                }
                TextView content = (TextView) convertView.findViewById(R.id.id_tv_content);
                Button tosend = (Button) convertView.findViewById(R.id.id_btn_to_send);

                content.setText("  "+getItem(position).getContent());
                tosend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        JumpUtil.toActivity(ChooseMsgActivity.this,mFestivalId,getItem(position).getId());

                    }
                });

                return convertView;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.id_btn_to_send:

                break;

            case R.id.id_fab_toSend:

                JumpUtil.toActivity(ChooseMsgActivity.this,mFestivalId,-1);
                break;

        }
    }
}
