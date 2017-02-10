package com.asiainfo.festivalsms.business;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.asiainfo.festivalsms.bean.SendMsgBean;
import com.asiainfo.festivalsms.provider.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月09日16点49分 描述:与短信有关的业务类
 */
public class SmsBiz {

    private Context mContext;

    public SmsBiz(Context context) {
        mContext = context;
    }

    //More than one may send the contact
    public int sendMsg(String number, String content, PendingIntent sendPi, PendingIntent delivery) {

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> contents = smsManager.divideMessage(content);

        for (String switchcontent : contents) {

            smsManager.sendTextMessage(number, null, content, sendPi, delivery);

        }

        return contents.size();

    }

    public int sendMsg(Set<String> numbers, SendMsgBean msg, PendingIntent sendPi, PendingIntent delivery) {

        /***
         *  TODO :save(msg)有问题
         */
        //  save(msg);
        int result = 0;
        for (String number : numbers) {
            int count = sendMsg(number, msg.getMsg(), sendPi, delivery);

            result += count;

        }

        return result;

    }

    /**
     * <p> 描述: 因为这里只涉及数据库的增和查，所以这里直接写在业务层下 但是如果涉及到的数据库的操作比较多（增删改查，以及按条件查询等），最好在项目的包下建立一个"dao"层
     * 或者在"db"下增加一个相关操作的类，然后业务层就可以根据其实例去访问数据库的一些内容 创建时间:2/10/17/11:13 作者:小木箱 邮箱:yangzy3@asiainfo.com
     * </p>
     */


    private void save(SendMsgBean sendMsgBean) {

        sendMsgBean.setDate(new Date());
        ContentValues values = new ContentValues();
        values.put(SendMsgBean.COLUMN_DATE, sendMsgBean.getDate().getTime());
        values.put(SendMsgBean.COLUMN_FES_NAME, sendMsgBean.getFestivalName());
        values.put(SendMsgBean.COLUMN_MSG, sendMsgBean.getMsg());
        values.put(SendMsgBean.COLUMN_NAMES, sendMsgBean.getNames());
        values.put(SendMsgBean.COLUMN_NUMBERS, sendMsgBean.getNumbers());
        mContext.getContentResolver().insert(SmsProvider.URI_SMS_ALL, values);

    }

}
