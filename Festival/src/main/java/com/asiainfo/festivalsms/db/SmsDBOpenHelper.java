package com.asiainfo.festivalsms.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.asiainfo.festivalsms.bean.SendMsgBean;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月09日19点04分 描述:
 */
public class SmsDBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sms.db";
    private static final int DB_VERSION = 1;

    private static SmsDBOpenHelper mHelper;


    private SmsDBOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    public static SmsDBOpenHelper getInstance(Context context) {


        if (mHelper == null) {

            synchronized (SmsDBOpenHelper.class) {

                if (mHelper == null) {

                    mHelper = new SmsDBOpenHelper(context);

                }

            }

        }

        return mHelper;


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + SendMsgBean.TABLE_NAME + " ( " +
                "_id integer primary key autoincrement, " +
                SendMsgBean.COLUMN_DATE + " integer, " +
                SendMsgBean.COLUMN_FES_NAME + " text," +
                SendMsgBean.COLUMN_NUMBERS + " text," +
                SendMsgBean.COLUMN_NAMES + " text," +
                SendMsgBean.COLUMN_NUMBERS + " text )";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
