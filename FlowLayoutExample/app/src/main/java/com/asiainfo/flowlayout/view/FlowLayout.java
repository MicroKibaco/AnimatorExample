package com.asiainfo.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * 作者:小木箱 邮箱:yangzy3@asiainfo.com 创建时间:2/2/17/12:31 描述:流式布局
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int withMeasureSpec, int heightMeasureSpec) {

        int sizeWidth = MeasureSpec.getSize(withMeasureSpec);
        int modeWidth = MeasureSpec.getMode(withMeasureSpec);

        int sizeHigh = MeasureSpec.getSize(heightMeasureSpec);
        int modeHigh = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content

        int width = 0;
        int hight = 0;

        //记录每一行的高度和宽度
        int lineWidth = 0;
        int lineHight = 0;

        //得到内部元素的个数
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {

            View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child, withMeasureSpec, heightMeasureSpec);
            //得到layoutparam
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            //子view占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            //子view占据的高度
            int childHight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childHight > sizeWidth) {

                //对比得到最大的宽度
                width = Math.max(width, lineWidth);
                //重置linewidth
                lineWidth = childWidth;
                //记录行高
                hight += lineHight;

                lineHight = childHight;

            } else {//未换行

                //叠加行宽
                lineWidth += childWidth;
                //得到当前行最大的高度
                lineHight = Math.max(lineHight, childHight);

            }

            //最后一个控件

            if (i == cCount) {

                width = Math.max(lineWidth, width);
                hight += lineHight;
            }

        }
        Log.e("TAG", "sizeWidth = " + sizeWidth);
        Log.e("TAG", "sizeHigh = " + sizeHigh);

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width
                , modeHigh == MeasureSpec.EXACTLY ? sizeHigh : hight
        );

       /* //计算

        if (modeWidth == MeasureSpec.AT_MOST) {

            setMeasuredDimension(width,hight);

        }else {

            setMeasuredDimension(sizeWidth,sizeHigh);

        }

        super.onMeasure(withMeasureSpec, heightMeasureSpec);*/
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
