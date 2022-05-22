package com.example.rathaanelectronics.Common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class WrapViewPager extends ViewPager{

//    public WrapViewPager (Context context) {
//        super(context);
//    }
//
//    public WrapViewPager (Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mode = MeasureSpec.getMode(heightMeasureSpec);
//        // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
//        // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
//        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
//            // super has to be called in the beginning so the child views can be initialized.
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            int height = 0;
//            for (int i = 0; i < getChildCount(); i++) {
//                View child = getChildAt(i);
//                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                int childMeasuredHeight = child.getMeasuredHeight();
//                if (childMeasuredHeight > height) {
//                    height = childMeasuredHeight;
//                }
//            }
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        }
//        // super has to be called again so the new specs are treated as exact measurements
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    public WrapViewPager(Context context) {
        super(context);
        initPageChangeListener();
    }

    public WrapViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPageChangeListener();
    }

    private void initPageChangeListener() {
        addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                requestLayout();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(getCurrentItem());
        if (child != null) {
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


}