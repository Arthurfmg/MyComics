package com.arthurfmg.mycomics.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.arthurfmg.mycomics.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class FullscreenActivity extends Activity {

    PhotoView imageFull;
    String url = "";
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        url = getIntent().getStringExtra("url");

        imageFull = findViewById(R.id.idFullImage);
        final FlingAnimation flingAnimation = new FlingAnimation(imageFull, DynamicAnimation.SCROLL_Y);
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        Picasso.with(this)
                .load(url)
                .into(imageFull);


        final GestureDetector gdt = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent motionEvent) {

            }

            @Override
            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
                if(motionEvent.getY() - motionEvent1.getY() > SWIPE_MIN_DISTANCE && Math.abs(v1) > SWIPE_THRESHOLD_VELOCITY) {
                    flingAnimation.setStartVelocity(v1)
                            .setFriction(1.1f).start();
                    finish();
                    return false; // Bottom to top
                }  else if (motionEvent1.getY() - motionEvent.getY() > SWIPE_MIN_DISTANCE && Math.abs(v1) > SWIPE_THRESHOLD_VELOCITY) {
                    flingAnimation.setStartVelocity(v1)
                            .setMinValue(0f)
                            .setMaxValue((displayMetrics.heightPixels - imageFull.getHeight()))
                            .setFriction(1.1f).start();
                    finish();
                    return false; // Top to bottom
                }


                return false;
            }
        });

        imageFull.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gdt.onTouchEvent(motionEvent);
                return true;
            }
        });
    }
}
