package com.dsl.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.dsl.base.R;
import com.dsl.base.databinding.LayoutTitleBinding;


public class TitleLayout extends RelativeLayout {
    private TitleListener listener;
    private LayoutTitleBinding mLayoutTitleBinding;
    private Context mContext;
    private String mTitle;
    private boolean mShowLine;

    public TitleLayout(Context context) {
        super(context, null);

    }

    public LayoutTitleBinding getLayoutTitleBinding() {
        return mLayoutTitleBinding;
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TitleLayout);
        mTitle = a.getString(R.styleable.TitleLayout_title);
        mShowLine = a.getBoolean(R.styleable.TitleLayout_showLine, false);

        a.recycle();
        init();
        mLayoutTitleBinding.titleTitleTv.setText("" + mTitle);
        if (!mShowLine) {
            mLayoutTitleBinding.vLine.setVisibility(GONE);
        } else {
            mLayoutTitleBinding.vLine.setVisibility(VISIBLE);
        }

    }

    private void init() {
        mLayoutTitleBinding = LayoutTitleBinding.inflate(LayoutInflater.from(mContext), this, true);
        mLayoutTitleBinding.titleBackImgV.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClickBack();
            } else {
                Context context1 = getContext();
                if (context1 != null && context1 instanceof Activity) {
                    Activity activity = (Activity) context1;
                    KeyboardUtils.hideSoftInput(activity);
                    activity.finish();
                }
            }
        });
        mLayoutTitleBinding.llTitleRight1.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClickRight1();
            }
        });
        mLayoutTitleBinding.llTitleRight2.setOnClickListener(view -> {
            if (listener != null) {
                listener.onClickRight2();
            }
        });

    }


    public void setBack(Activity activity) {
        mLayoutTitleBinding.titleBackImgV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public void hideLeft() {
        mLayoutTitleBinding.titleBackImgV.setVisibility(View.GONE);
    }

    public void hideLine() {
        mLayoutTitleBinding.vLine.setVisibility(View.GONE);
    }

    public void showLine() {
        mLayoutTitleBinding.vLine.setVisibility(View.VISIBLE);
    }

//    public void setLeftImg(int resId) {
//        mLayoutTitleBinding.titleBackImgV.setImageResource(resId);
//    }

    public void setTitleText(String title) {
        mLayoutTitleBinding.titleTitleTv.setText(title);
    }

    public void setTitleText(int resId) {
        setTitleText(getResources().getString(resId));
    }

    public void setRightTextColor(int color) {
        mLayoutTitleBinding.tvTitleRight1.setTextColor(color);
    }

    public void setRight1Style(int resId, String text) {
        if (resId != 0 && TextUtils.isEmpty(text)) {
            mLayoutTitleBinding.ivTitleRight1.setVisibility(View.VISIBLE);
            mLayoutTitleBinding.ivTitleRight1.setImageResource(resId);
            mLayoutTitleBinding.tvTitleRight1.setVisibility(GONE);
        } else {
            mLayoutTitleBinding.tvTitleRight1.setVisibility(VISIBLE);
            mLayoutTitleBinding.tvTitleRight1.setText(text);
            mLayoutTitleBinding.ivTitleRight1.setVisibility(View.GONE);
        }
    }

    public void setRight2Style(int resId, String text) {
        if (resId != 0 && TextUtils.isEmpty(text)) {
            mLayoutTitleBinding.ivTitleRight2.setVisibility(View.VISIBLE);
            mLayoutTitleBinding.ivTitleRight2.setImageResource(resId);
            mLayoutTitleBinding.tvTitleRight2.setVisibility(GONE);
        } else {
            mLayoutTitleBinding.tvTitleRight2.setVisibility(VISIBLE);
            mLayoutTitleBinding.tvTitleRight2.setText(text);
            mLayoutTitleBinding.ivTitleRight2.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题布局各组件点击事件
     *
     * @param listener
     */
    public void setOnTitleListener(TitleListener listener) {
        this.listener = listener;
    }

    public interface TitleListener {

        /**
         * 左侧Back按钮点击事件
         */
        public void onClickBack();

        /**
         * 右侧第一个按钮点击事件
         */
        public void onClickRight1();

        /**
         * 右侧第二个按钮点击事件
         */
        public void onClickRight2();

    }

    public abstract static class TitleBack implements TitleListener {
        @Override
        public void onClickRight1() {

        }

        @Override
        public void onClickRight2() {

        }
    }

    public void startStyle_A() {
        mLayoutTitleBinding.getRoot().setBackgroundColor(Color.parseColor("#00ffffff"));
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_white);
        mLayoutTitleBinding.titleTitleTv.setVisibility(GONE);

    }

    public void startStyle_B() {
        mLayoutTitleBinding.getRoot().setBackgroundColor(Color.parseColor("#ffffff"));
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_black);
        mLayoutTitleBinding.titleTitleTv.setVisibility(VISIBLE);
    }

    public void startStyle_C() {
        mLayoutTitleBinding.getRoot().setBackgroundColor(Color.parseColor("#00ffffff"));
        mLayoutTitleBinding.backicon.setBackgroundResource(R.drawable.ic_back_white);
        mLayoutTitleBinding.titleTitleTv.setTextColor(Color.parseColor("#ffffff"));
    }


    public void setScrollViewLis(ScrollView scrollView) {
        mLayoutTitleBinding.topbar.setVisibility(VISIBLE);
        startStyle_A();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener((view, i, i1, i2, i3) -> {
                if (i1 > 0) {
                    startStyle_B();
                } else {
                    startStyle_A();
                }
            });
        }

    }
}
