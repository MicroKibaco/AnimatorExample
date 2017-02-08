package com.asiainfo.festivalsms.utils;

import android.content.Context;
import android.content.Intent;

import com.asiainfo.festivalsms.activity.SendMsgActivity;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日21点46分 描述:
 */
public class JumpUtil {

    public   static final String KEY_FESTIVAL_ID = "festivalid";
    public  static final String KEY_MSG_ID = "msgid";


    public static void toActivity (Context context, int festivalId, int msgId){

        Intent intent = new Intent(context, SendMsgActivity.class);
        intent.putExtra(KEY_FESTIVAL_ID,festivalId);
        intent.putExtra(KEY_MSG_ID,msgId);
        context.startActivity(intent);

    }

}
