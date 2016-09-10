package com.wanghaisheng.xiaoyaprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final int UPDATE_PROGRESS = 1;

    HorizontalProgressBarWithProgress mHProgress;
    RoundProgressBarWithProgress mRProgress;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = mHProgress.getProgress();
            mHProgress.setProgress(++progress);
            mRProgress.setProgress(++progress);

            mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHProgress = (HorizontalProgressBarWithProgress) findViewById(R.id.progress_01);
        mRProgress = (RoundProgressBarWithProgress) findViewById(R.id.round_progress);

        mHandler.sendEmptyMessage(UPDATE_PROGRESS);
    }
}
