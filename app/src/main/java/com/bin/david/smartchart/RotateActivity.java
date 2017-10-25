package com.bin.david.smartchart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationSet;

import com.bin.david.smartchart.matrix.RotateView;

/**
 * Created by huang on 2017/10/24.
 */

public class RotateActivity extends AppCompatActivity {

    private RotateView rotateView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        rotateView = (RotateView) findViewById(R.id.rotateView);

    }

    public void onClick(View view){
        ObjectAnimator animator1 = ObjectAnimator.ofInt(rotateView,"rotateX",0,60,0);
        animator1.setDuration(500);
        ObjectAnimator animator2 = ObjectAnimator.ofInt(rotateView,"rotateY",0,30);
        animator2.setDuration(200);
        ObjectAnimator animator3 = ObjectAnimator.ofInt(rotateView,"rotateAngle",0,360);
        animator3.setDuration(2000);
        ObjectAnimator animator4 = ObjectAnimator.ofInt(rotateView,"rotateY",30,0);
        animator4.setDuration(200);
        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                rotateView.setRotateY(0);
                rotateView.setRotateX(0);
                rotateView.setRotateAngle(0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rotateView.setRotateY(0);
                rotateView.setRotateX(0);
                rotateView.setRotateAngle(0);
            }
        });
        set.playSequentially(animator1,animator2,animator3,animator4);
        set.start();
    }
}
