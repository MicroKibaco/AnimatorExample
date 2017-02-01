package com.asiainfo.animator.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.asiainfo.animator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimatorActivity extends Activity implements Animator.AnimatorListener {

    //移动的图标
    @BindView(R.id.imv_animator)
    ImageView mImvAnimator;

    //控制移动的按钮
    @BindView(R.id.btn_move)
    Button mBtnMove;


    @BindView(R.id.btn_click)
    Button mBtnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);

        initDatas();

    }

    private void initDatas() {
    }


    @OnClick({R.id.imv_animator, R.id.btn_move, R.id.btn_click})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imv_animator:

                Toast.makeText(this, "被点击了!", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn_click:

                handleObjAnimator();

                startActivity(new Intent(this, UmbrellaActivity.class));

                break;

            case R.id.btn_move:

                //handleTransAnim();
                //handleObjAnim();
                //handleValuesHolder();
                //handleAnimSet();
                handleVueAnimofInt(view);
                // handleVueAnimofObj(view);

                break;

            default:
                break;
        }
    }

    private void handleObjAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mImvAnimator, "alpha", 0f, 1f);
        animator.setDuration(1000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(AnimatorActivity.this, "anim结束啦!", Toast.LENGTH_SHORT).show();
            }
        });
        animator.start();
    }

    private void handleVueAnimofObj(View view) {

        ValueAnimator.ofObject(new TypeEvaluator<PointF>() {

            @Override
            public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

                return null;

            }
        });

    }

    private void handleVueAnimofInt(View view) {

        final Button button = (Button) view;

        ValueAnimator animator = ValueAnimator.ofInt(0, 100);

        animator.setDuration(6000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Integer value = (Integer) animation.getAnimatedValue();

                button.setText("" + value);

            }
        });

        animator.start();

    }

    private void handleAnimSet() {

        ObjectAnimator rotation = ObjectAnimator.ofFloat(mImvAnimator, "rotation", 0f, 360f);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mImvAnimator, "translationX", 0f, 300f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mImvAnimator, "translationY", 0f, 400f);

        AnimatorSet set = new AnimatorSet();

        set.play(translationX).with(translationY);
        set.play(rotation).after(translationX);

        //set.playSequentially(rotation,translationX,translationY);

        //set.playTogether(rotation,translationX,translationY);
        set.setDuration(2000);
        set.start();


    }

    /**
     * 作者:小木箱 邮件:yangzy3@asiainfo.com 创建时间:2/1/17/22:21 描述:优化节省资源
     */

    private void handleValuesHolder() {

        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0f, 360f);
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0f, 300f);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0f, 400f);
        ObjectAnimator.ofPropertyValuesHolder(mImvAnimator, rotation,
                translationX, translationY).setDuration(1000).start();

    }

    private void handleObjAnim() {

        ObjectAnimator.ofFloat(mImvAnimator, "rotation", 0f, 360f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImvAnimator, "translationX", 0f, 300f).setDuration(1000).start();
        ObjectAnimator.ofFloat(mImvAnimator, "translationY", 0f, 400f).setDuration(1000).start();
    }

    private void handleTransAnim() {
        TranslateAnimation anim = new TranslateAnimation(0, 600, 0, 0);
        anim.setDuration(600);
        anim.setFillAfter(true);
        mImvAnimator.startAnimation(anim);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        Toast.makeText(this, "anim is end!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
