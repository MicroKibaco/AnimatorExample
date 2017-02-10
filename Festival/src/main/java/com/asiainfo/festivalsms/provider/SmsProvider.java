package com.asiainfo.festivalsms.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.asiainfo.festivalsms.bean.SendMsgBean;
import com.asiainfo.festivalsms.db.SmsDBOpenHelper;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月09日19点26分 描述:
 */
public class SmsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.asiainfo.sms.provider.SmsProvider";
    public static final Uri URI_SMS_ALL = Uri.parse("content://" + AUTHORITY + "/sms");

    private static final int SMS_ALL = 0;//访问列表中的所有数据
    private static final int SMS_ONE = 1;//访问列表的单条数据

    private static UriMatcher mUriMatcher;

    static {

        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "sms", SMS_ALL);
        mUriMatcher.addURI(AUTHORITY, "sms/#", SMS_ONE);

    }

    private SmsDBOpenHelper mHelper;
    private SQLiteDatabase mDb;

    @Override
    public boolean onCreate() {

        mHelper = SmsDBOpenHelper.getInstance(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int match = mUriMatcher.match(uri);
        switch (match) {

            case SMS_ONE:
                long id = ContentUris.parseId(uri);
                selection = "_id = ?";
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            case SMS_ALL:
                break;
            default:
                throw new IllegalArgumentException("wrong URI:" + uri);


        }

        mDb = mHelper.getReadableDatabase();
        Cursor cursor = mDb.query(SendMsgBean.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

        //用来在后台检测数据的变化，如果有变化就会有返回（因为在SmsHistoryFragment中使用了Loader）
        cursor.setNotificationUri(getContext().getContentResolver(), URI_SMS_ALL);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int match = mUriMatcher.match(uri);
        if (match != SMS_ALL) {

            throw new IllegalArgumentException("wrong URI:" + uri);

        }

        mDb = mHelper.getWritableDatabase();
        long rowId = mDb.insert(SendMsgBean.TABLE_NAME, null, values);

        if (rowId > 0) {

            notifiDateSetChaged();
            return ContentUris.withAppendedId(uri, rowId);
        }

        return uri;
    }

    private void notifiDateSetChaged() {

        getContext().getContentResolver().notifyChange(URI_SMS_ALL, null);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
