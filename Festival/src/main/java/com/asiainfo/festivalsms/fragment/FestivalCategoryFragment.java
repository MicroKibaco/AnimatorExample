package com.asiainfo.festivalsms.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.activity.ChooseMsgActivity;
import com.asiainfo.festivalsms.bean.Festival;
import com.asiainfo.festivalsms.bean.FestivalLib;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月08日15点24分 描述:
 */
public class FestivalCategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ID_FESTIVAL = "festival_id";
    private GridView mGridView;
    private ArrayAdapter<Festival> mAdapter;
    private LayoutInflater mInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_festival_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initView(view);
        initDatas();

    }


    private void initDatas() {
        mGridView.setAdapter(mAdapter = new ArrayAdapter<Festival>(getActivity(), -1, FestivalLib.getInstance().getFestivals()) {

            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {

                    convertView = mInflater.inflate(R.layout.item_festival, parent, false);

                }

                TextView tv = (TextView) convertView.findViewById(R.id.id_tv_festival_name);
                tv.setText(getItem(position).getName());

                return convertView;
            }
        });

        mGridView.setOnItemClickListener(this);
    }

    private void initView(View view) {

        mInflater = LayoutInflater.from(getActivity());
        mGridView = (GridView) view.findViewById(R.id.id_gv_festival_category);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getActivity(), ChooseMsgActivity.class);
        intent.putExtra(ID_FESTIVAL, mAdapter.getItem(position).getId());
        startActivity(intent);

    }
}
