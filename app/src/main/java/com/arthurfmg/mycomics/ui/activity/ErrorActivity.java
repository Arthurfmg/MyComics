package com.arthurfmg.mycomics.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.ExceptionHandler;

/**
 * Created by phesto on 1/11/2017.
 */


public class ErrorActivity extends Activity {

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_error);

        error = (TextView) findViewById(R.id.error);

        error.setText(getIntent().getStringExtra("error"));
    }
}

