package com.asiainfo.animator.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.asiainfo.animator.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MicroKibaco on 2/1/17.
 */

public class UmbrellaActivity extends Activity implements View.OnClickListener {

    private int[] mViewIds = {R.id.imv_h, R.id.imv_a, R.id.imv_b, R.id.imv_c, R.id.imv_d, R.id.imv_e, R.id.imv_f, R.id.imv_g};

    private List<ImageView> mImageViewList = new ArrayList<>();

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);

        for (int i = 0; i < mViewIds.length; i++) {

            ImageView switchImg = (ImageView) findViewById(mViewIds[i]);

            switchImg.setOnClickListener(this);

            mImageViewList.add(switchImg);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imv_h:

                if (flag) {

                    startAnim();

                } else {

                    closeAnim();
                }


                break;

            default:
                Toast.makeText(this, "click" + v.getId(), Toast.LENGTH_SHORT).show();
                break;

        }
    }

    private void closeAnim() {

        for (int i = 1; i < mViewIds.length; i++) {

            ObjectAnimator translationY = ObjectAnimator.ofFloat(mImageViewList.get(i), "translationY", i * 200, 0f);
            translationY.setDuration(500);
            translationY.setInterpolator(new BounceInterpolator());
            translationY.setStartDelay(i * 300);
            translationY.start();
            flag = true;

        }

    }

    private void startAnim() {

        for (int i = 1; i < mViewIds.length; i++) {

            ObjectAnimator translationY = ObjectAnimator.ofFloat(mImageViewList.get(i), "translationY", 0f, i * 200);
            translationY.setDuration(500);
            translationY.setInterpolator(new BounceInterpolator());
            translationY.setStartDelay(i * 300);
            translationY.start();
            flag = false;

        }

    }
}
