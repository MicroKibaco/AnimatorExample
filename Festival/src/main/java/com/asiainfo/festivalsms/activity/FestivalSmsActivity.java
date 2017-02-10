package com.asiainfo.festivalsms.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.asiainfo.festivalsms.R;
import com.asiainfo.festivalsms.fragment.FestivalCategoryFragment;
import com.asiainfo.festivalsms.fragment.SmsHistoryFragment;

public class FestivalSmsActivity extends FragmentActivity implements View.OnClickListener {

    final String[] mTitle = new String[]{"节日短信", "发送记录"};
    private TabLayout mSmsTablayout;
    private ViewPager mSmsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_sms);

        initView();
        initListener();
        initDatas();
    }

    private void initDatas() {


    }

    private void initListener() {

        mSmsViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 1) {

                    return new SmsHistoryFragment();

                }
                return new FestivalCategoryFragment();
            }

            @Override
            public int getCount() {
                return mTitle.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitle[position];
            }
        });

        mSmsTablayout.setupWithViewPager(mSmsViewPager);

    }

    private void initView() {

        mSmsTablayout = (TabLayout) findViewById(R.id.id_tablayout);
        mSmsViewPager = (ViewPager) findViewById(R.id.id_viewpager);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


        }

    }
}
