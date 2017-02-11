package com.asiainfo.festivalsms.activity;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.bean.Festival;
import com.asiainfo.festivalsms.bean.FestivalLib;
import com.asiainfo.festivalsms.bean.MsgBean;
import com.asiainfo.festivalsms.bean.SendMsgBean;
import com.asiainfo.festivalsms.business.SmsBiz;
import com.asiainfo.festivalsms.utils.JumpUtil;
import com.asiainfo.festivalsms.view.FlowLayout;

import java.util.HashSet;

public class SendMsgActivity extends Activity implements View.OnClickListener {

    public static final String ACTION_SEND_MSG = "action_send_msg";
    public static final String ACTION_DELIVER_MSG = "action_deliver_msg";
    private static final int CODE_REQUEST = 1001;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private PendingIntent mSendPi;
    private PendingIntent mDeliverPi;
    private BroadcastReceiver mSendBroadcastReceiver;
    private BroadcastReceiver mDeliverBroadcastReceiver;

    private int mFestivalId;
    private int msgId;

    private Festival mFestival;
    private MsgBean mMsgBean;

    private EditText mEdMsg;
    private Button mBtnAdd;
    private FlowLayout mFlContacts;
    private FloatingActionButton mFabSend;
    private View mLayoutLoading;

    private HashSet<String> mContactNames = new HashSet<>();
    private HashSet<String> mContactNums = new HashSet<>();
    private SmsBiz mSmsBiz;

    private LayoutInflater mInflater;
    private String mMsg;
    private int mMsgSendCount;
    private int mToTalCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);
        initView();
        initListener();
        initDatas();
        initReceiver();
    }


    private void initReceiver() {

        Intent sendIntent = new Intent(ACTION_SEND_MSG);
        mSendPi = PendingIntent.getBroadcast(this, 0, sendIntent, 0);

        Intent deliverIntent = new Intent(ACTION_DELIVER_MSG);
        mDeliverPi = PendingIntent.getBroadcast(this, 0, deliverIntent, 0);

        registerReceiver(mSendBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                mMsgSendCount++;

                if (getResultCode() == RESULT_OK) {

                    Log.e("SendMsgActivity", "短信发送成功" + mMsgSendCount + "/" + mToTalCount);


                } else {

                    Log.e("SendMsgActivity", "短信发送失败");

                }

                Toast.makeText(context, "短信发送成功:" + mMsgSendCount + "/" + mToTalCount, Toast.LENGTH_SHORT).show();

                if (mMsgSendCount == mToTalCount) {

                    finish();

                }

            }
        }, new IntentFilter(ACTION_SEND_MSG));

        registerReceiver(mDeliverBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (getResultCode() == RESULT_OK) {

                    Log.e("SendMsgActivity", "联系人已经成功接收到我们的短信");


                } else {

                    Log.e("SendMsgActivity", "联系人没有接收到我们的短信");

                }

            }
        }, new IntentFilter(ACTION_DELIVER_MSG));


    }

    private void initListener() {

        mBtnAdd.setOnClickListener(this);
        mFabSend.setOnClickListener(this);

    }

    private void initView() {

        mInflater = LayoutInflater.from(this);
        mSmsBiz = new SmsBiz(this);
        mEdMsg = (EditText) findViewById(R.id.id_et_content);
        mBtnAdd = (Button) findViewById(R.id.id_btn_add);
        mFlContacts = (FlowLayout) findViewById(R.id.id_fl_content);
        mFabSend = (FloatingActionButton) findViewById(R.id.id_fab_send);
        mLayoutLoading = findViewById(R.id.id_layout_loading);


    }

    private void initDatas() {

        mFestivalId = getIntent().getIntExtra(JumpUtil.KEY_FESTIVAL_ID, -1);
        msgId = getIntent().getIntExtra(JumpUtil.KEY_MSG_ID, -1);
        mLayoutLoading.setVisibility(View.GONE);
        if (msgId != -1) {

            mMsgBean = FestivalLib.getInstance().getMsgBeanById(msgId);
            mEdMsg.setText(mMsgBean.getContent());

        }
        mFestival = FestivalLib.getInstance().getFestivalById(mFestivalId);
        setTitle(mFestival.getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_REQUEST) {

            if (resultCode == RESULT_OK) {

                Uri contactUri = data.getData();
                Cursor cursor = managedQuery(contactUri, null, null, null, null);
                cursor.moveToFirst();
                String contactsName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                mContactNames.add(contactsName);
                String number = getContactNumber(cursor);

                if (!TextUtils.isEmpty(number)) {

                    mContactNums.add(number);
                    mContactNames.add(contactsName);

                    addTag(contactsName);

                }

            }

        }
    }

    private void addTag(String contactsName) {

        TextView tv = (TextView) mInflater.inflate(R.layout.tag, mFlContacts, false);
        tv.setText(contactsName);
        mFlContacts.addView(tv);

    }

    private String getContactNumber(Cursor cursor) {
        int numberCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

        String number = null;

        request6Permissions();

        if (numberCount > 0) {

            int contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);

            phoneCursor.moveToFirst();

            number = phoneCursor.getString(phoneCursor.getColumnIndex
                    (ContactsContract.CommonDataKinds.Phone.NUMBER));


            phoneCursor.close();

            return number;


        }

        cursor.close();

        return number;
    }

    /**
     * 描述:申请6.0权限 创建时间:2/9/17/16:21 作者:小木箱 邮箱:yangzy3@asiainfo.com
     */

    private void request6Permissions() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(SendMsgActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                        ||
                        ContextCompat.checkSelfPermission(SendMsgActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SendMsgActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.READ_CONTACTS},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    return;
                }
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.id_btn_add:

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, CODE_REQUEST);

                break;

            case R.id.id_fab_send:

                mMsg = mEdMsg.getText().toString();


                if (mContactNums.size() == 0) {

                    Toast.makeText(this, "请先选择联系人", Toast.LENGTH_SHORT).show();

                    return;
                }


                if (TextUtils.isEmpty(mMsg)) {

                    Toast.makeText(this, "短信内容不能为空", Toast.LENGTH_SHORT).show();
                    return;

                }


                mLayoutLoading.setVisibility(View.VISIBLE);

                mToTalCount = mSmsBiz.sendMsg(mContactNums, buildSendMsg(mMsg), mSendPi, mDeliverPi);
                mMsgSendCount = 0;

                break;

            default:

                break;

        }

    }

    private SendMsgBean buildSendMsg(String msg) {
        String names = "";
        String nums = "";

        SendMsgBean msgBean = new SendMsgBean();


        for (String contactName : mContactNames) {

            names = contactName + ":";

        }

        for (String contactnum : mContactNums) {

            nums = contactnum + ":";

        }

        msgBean.setMsg(msg);
        msgBean.setNames(names.substring(0, names.length() - 1));
        msgBean.setNumbers(nums.substring(0, nums.length() - 1));
        msgBean.setFestivalName(mFestival.getName());

        return msgBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mSendBroadcastReceiver);
        unregisterReceiver(mDeliverBroadcastReceiver);
    }
}
