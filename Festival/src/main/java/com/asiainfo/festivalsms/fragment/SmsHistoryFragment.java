package com.asiainfo.festivalsms.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.bean.SendMsgBean;
import com.asiainfo.festivalsms.provider.SmsProvider;
import com.asiainfo.festivalsms.view.FlowLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 描述: 创建时间:2/10/17/14:16 作者:小木箱 邮箱:yangzy3@asiainfo.com
 */

public class SmsHistoryFragment extends ListFragment {

    private static final int LOAD_ID = 1002;
    private LayoutInflater mInflater;
    private CursorAdapter mCursorAdapter;
    private DateFormat df = new SimpleDateFormat("yyy-MM-dd HH:mm");


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initLoader();
        setupListAdapter();


    }

    private void initView() {

        mInflater = LayoutInflater.from(getActivity());

    }

    private void setupListAdapter() {

        mCursorAdapter = new CursorAdapter(getActivity(), null, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {

                return mInflater.inflate(R.layout.item_sended_msg, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {

                String names = cursor.getString(cursor.getColumnIndex(SendMsgBean.COLUMN_NAMES));
                long dateval = cursor.getLong(cursor.getColumnIndex(SendMsgBean.COLUMN_DATE));

                TextView tvMsg = (TextView) view.findViewById(R.id.tv_msg);
                FlowLayout fl = (FlowLayout) view.findViewById(R.id.fl_contacts);
                TextView fes = (TextView) view.findViewById(R.id.id_tv_festival_name);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);

                tvMsg.setText(cursor.getColumnIndex(SendMsgBean.COLUMN_MSG));
                fes.setText(cursor.getColumnIndex(SendMsgBean.COLUMN_FES_NAME));
                tvDate.setText(parseDate(dateval));


                if (TextUtils.isEmpty(names)) {
                    return;
                }

                fl.removeAllViews();

                for (String name : names.split(":")) {

                    addTag(name, fl);

                }

            }


        };

        setListAdapter(mCursorAdapter);
    }

    private String parseDate(long dateval) {

        return df.format(dateval);

    }

    private void addTag(String name, FlowLayout fl) {


        TextView tv = (TextView) mInflater.inflate(R.layout.tag, fl, false);
        tv.setText(name);
        fl.addView(tv);

    }

    private void initLoader() {


        getLoaderManager().initLoader(LOAD_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                CursorLoader loader = new CursorLoader(getActivity(), SmsProvider.URI_SMS_ALL, null, null, null, null);
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

                if (loader.getId() == LOAD_ID) {

                    mCursorAdapter.swapCursor(data);

                }

            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

                mCursorAdapter.swapCursor(null);

            }
        });


    }

}
