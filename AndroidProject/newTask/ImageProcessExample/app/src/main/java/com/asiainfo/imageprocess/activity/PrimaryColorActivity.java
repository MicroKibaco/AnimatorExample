package com.asiainfo.imageprocess.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.asiainfo.imageprocess.R;
import com.asiainfo.imageprocess.utils.ImageHelper;

/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2017年02月14日19点27分 描述:
 */
public class PrimaryColorActivity extends Activity implements SeekBar.OnSeekBarChangeListener {

    private ImageView mImgPimaryColor;
    private SeekBar mSeekBarHue, mSeekBarSaturation, mSeekBarLum;

    private static final int MAX_VALUE = 200;
    private static final int MID_VALUE = 99;

    private float mHue, mSaturation, mLum;

    private Bitmap mBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_color);

        initView();
        initListener();


    }

    private void initListener() {

        mSeekBarHue.setOnSeekBarChangeListener(this);
        mSeekBarSaturation.setOnSeekBarChangeListener(this);
        mSeekBarLum.setOnSeekBarChangeListener(this);

    }

    private void initView() {

        mBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test3);
        mImgPimaryColor = (ImageView) findViewById(R.id.pimary_color_imagview);
        mSeekBarHue = (SeekBar) findViewById(R.id.Skb_Hue);
        mSeekBarSaturation = (SeekBar) findViewById(R.id.Skb_Saturation);
        mSeekBarLum = (SeekBar) findViewById(R.id.Skb_Lum);

        /***
         * 设置SeekBar最大刻度
         */
        mSeekBarHue.setMax(MAX_VALUE);
        mSeekBarSaturation.setMax(MAX_VALUE);
        mSeekBarLum.setMax(MAX_VALUE);

        /***
         * 设置SeekBar的初始默认中间值
         */
        mSeekBarHue.setProgress(MID_VALUE);
        mSeekBarSaturation.setProgress(MID_VALUE);
        mSeekBarLum.setProgress(MID_VALUE);

        mImgPimaryColor.setImageBitmap(mBitmap);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


        switch (seekBar.getId()) {

            case R.id.Skb_Hue:

                mHue = (progress - MID_VALUE) * 1.0F / MID_VALUE * 180;


                break;

            case R.id.Skb_Saturation:

                mSaturation = progress * 1.0F / MID_VALUE;


                break;

            case R.id.Skb_Lum:

                mLum = progress * 1.0F / MID_VALUE;


                break;

            default:
                break;

        }
        Log.e("TAG:","mHue="+mHue+" ,mSaturation=" +mSaturation+" ,mLum=" +mLum);
        Bitmap newBitmap = ImageHelper.handleImageEffect(mBitmap, mHue, mSaturation, mLum);
        mImgPimaryColor.setImageBitmap(newBitmap);


    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
