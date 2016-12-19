
package com.framgia.levelupanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CountDownTimer mLevelUpCountDownTimer;
    private CountDownTimer mLevelNumberCountDownTimer;
    private AnimatorSet mLevelUpAnimationSet = new AnimatorSet();
    private AnimatorSet mLevelNumberAnimationSet = new AnimatorSet();
    private LinearLayout mLevelUpLayout;
    private LinearLayout mLevelNumberLayout;
    private int mIndex;
    private static final int DURATION_TIME = 1800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLevelUpLayout = (LinearLayout) findViewById(R.id.layout_level_up);
        mLevelNumberLayout = (LinearLayout) findViewById(R.id.layout_level_number);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation(2389);
            }
        }, 1500);
    }

    private void startAnimation(int levelNumber) {
        String levelUpString = "LEVEL UP!";
        final int levelUpLength = levelUpString.length();
        for (int i = 0; i < levelUpLength; i++) {
            TextView textView = createTextView();
            textView.setText(String.valueOf(levelUpString.charAt(i)));
            mLevelUpLayout.addView(textView);
        }

        String levelNumberString = String.valueOf(levelNumber);
        final int levelNumberLength = levelNumberString.length();
        for (int i = 0; i < levelNumberLength; i++) {
            TextView textView = createTextView();
            textView.setText(String.valueOf(levelNumberString.charAt(i)));
            mLevelNumberLayout.addView(textView);
        }

        final int interval = 120;
        int totalLevelUpTime = DURATION_TIME;
        final int totalLevelNumberTime = levelNumberLength * interval + interval;

        mIndex = 0;

        mLevelUpCountDownTimer = new CountDownTimer(totalLevelUpTime, interval) {

            @Override
            public void onTick(long l) {
                if (mIndex < levelUpLength) {
                    TextView textView = (TextView) mLevelUpLayout.getChildAt(mIndex);
                    playLevelUpAnimation(textView);
                    mIndex++;
                }
            }

            @Override
            public void onFinish() {
                mIndex = 0;
                mLevelNumberCountDownTimer = new CountDownTimer(totalLevelNumberTime, interval) {

                    @Override
                    public void onTick(long l) {
                        TextView textView = (TextView) mLevelNumberLayout.getChildAt(mIndex);
                        playLevelNumberAnimation(textView);
                        mIndex++;
                    }

                    @Override
                    public void onFinish() {

                    }
                }.start();
            }
        }.start();

    }

    private void playLevelUpAnimation(View view) {
        mLevelUpAnimationSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.2f, 0.6f, 1, 1).setDuration(DURATION_TIME),
                ObjectAnimator.ofFloat(view, "alpha", 0, 0.6f, 0.8f, 1, 1).setDuration(DURATION_TIME),
                ObjectAnimator.ofFloat(view, "translationY", -40, 70, -50, 30, 0)
                        .setDuration(DURATION_TIME));
        mLevelUpAnimationSet.start();
    }

    private void playLevelNumberAnimation(View view) {
        mLevelNumberAnimationSet.playTogether(
                ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.2f, 0.6f, 1, 1).setDuration(DURATION_TIME),
                ObjectAnimator.ofFloat(view, "alpha", 0, 0.6f, 0.8f, 1, 1).setDuration(DURATION_TIME),
                ObjectAnimator.ofFloat(view, "translationY", -40, 70, -50, 30, 0)
                        .setDuration(DURATION_TIME));
        mLevelNumberAnimationSet.start();
    }

    private TextView createTextView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int padding = getResources().getDimensionPixelSize(R.dimen.dp_30);
        int textSize = getResources().getDimensionPixelSize(R.dimen.sp_18);
        TextView textView = new TextView(this);
        textView.setPadding(0, padding, 0, padding);
        textView.setTextColor(Color.parseColor("#FFFF00"));
        textView.setTextSize(textSize);
        textView.setLayoutParams(params);
        textView.setAlpha(0);
        textView.setTypeface(null, Typeface.BOLD);
        return textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLevelUpCountDownTimer != null) {
            mLevelUpCountDownTimer.cancel();
        }
        if (mLevelNumberCountDownTimer != null) {
            mLevelNumberCountDownTimer.cancel();
        }
    }
}
