package com.xsgzt.framework.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.xsgzt.framework.R;


/**
 * Created by Administrator on 2018/4/23.
 */

public class VerificationButton extends Button {
    private VerificationFinishLisenter mVerificationFinishLisenter;
    long waitTime = 60;
    int mStarColor, mWaterColor;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            waitTime--;
            if (waitTime <= 0) {
                //重新获取
                waitTime = 60;
                reGetCode();
                //给个监听
                removeMessages(0);
                Log.e("handleMessage: ", "是不是你再搞鬼");
                if (mVerificationFinishLisenter != null) {
                    mVerificationFinishLisenter.finish();
                }
                return;
            }
            startClock(waitTime);
        }
    };

    public void reGetCode() {
        setEnabled(true);
        setText("重新获取");
        initView();
    }

    public VerificationButton(Context context) {
        this(context, null);
    }

    public VerificationButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerificationButton);
        mStarColor = typedArray.getResourceId(R.styleable.VerificationButton_starColor, R.drawable.main_rund_shap);
        mWaterColor = typedArray.getResourceId(R.styleable.VerificationButton_waterColor, R.drawable.verification_shap);
        typedArray.recycle();
        initView();

    }

    public void initView() {
        setBackground(getResources().getDrawable(mStarColor));
        setText("获取验证码");
        setEnabled(true);
//        setBackground(getResources().getDrawable(R.drawable.verification_shap));
    }

    //智慧状态
    public void waite() {
        setEnabled(false);
        setBackground(getResources().getDrawable(mWaterColor));
        setText("请稍等...");
    }

    public void startClock(long time) {
        waitTime = time;
        setEnabled(false);
        setBackground(getResources().getDrawable(mWaterColor));
        setText(time + "s");
        handler.sendEmptyMessageDelayed(0, 1000);
    }


    public void setOnVerificationFinishLisenter(VerificationFinishLisenter verificationFinishLisenter) {
        mVerificationFinishLisenter = verificationFinishLisenter;
    }

    public interface VerificationFinishLisenter {
        void finish();
    }

    public void release() {
        if(handler != null) {
            handler. removeMessages(0);
            handler= null;
        }

        mVerificationFinishLisenter = null;
    }

}
