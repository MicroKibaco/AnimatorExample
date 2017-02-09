package com.asiainfo.festivalsms.business;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Set;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月09日16点49分 描述:
 */
public class SmsBiz {

    public int sendMsg(String number, String content, PendingIntent sendPi, PendingIntent delivery) {

        SmsManager smsManager = SmsManager.getDefault();

        ArrayList<String> contents = smsManager.divideMessage(content);

        for (String switchcontent : contents) {

            smsManager.sendTextMessage(number, null, content, sendPi, delivery);

        }

        return contents.size();

    }

    public int sendMsg(Set<String> numbers, String msg, PendingIntent sendPi, PendingIntent delivery) {

        int result = 0;
        for (String number : numbers) {
            int count = sendMsg(number, msg, sendPi, delivery);

            result += count;

        }

        return result;

    }

}
