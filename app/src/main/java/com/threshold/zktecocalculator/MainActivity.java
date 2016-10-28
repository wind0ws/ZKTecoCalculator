package com.threshold.zktecocalculator;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener,View.OnClickListener {

    private TextView tvContent;
    private TimePicker timePicker;
    private Toolbar mToolbar;
    private FloatingActionButton mFloatingActionButton;

    private boolean isRefreshing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        setSupportActionBar();
        bindingEvents();
        initData();
    }

    private void setSupportActionBar() {
        mToolbar.setTitleTextColor(Color.YELLOW);
        setSupportActionBar(mToolbar);
    }

    private void initData() {
        timePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }
        onTimeChanged(timePicker,hour,minute);
    }


    private void bindingEvents() {
        timePicker.setOnTimeChangedListener(this);
        mFloatingActionButton.setOnClickListener(this);
//        timePicker.setOnTouchListener(this);
    }

    private void bindingViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvContent = (TextView) findViewById(R.id.tvContent);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
    }

    //There is A Bug on Android 5.0 and 5.0.2 ,the bug is TimePicker's onTimeChanged Event is Not Performed.
    //If you Have Any Solution,please let me know,Thanks!

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        String hour = String.format("%02d", hourOfDay);
        String minuteStr = String.format("%02d", minute);
        Log.d("ZKTeco", hour + minuteStr);
        int time = Integer.parseInt(hour + minuteStr);
        double password= Math.pow(9999 - time, 2);
        int psw = (int) password;
        tvContent.setText(String.valueOf(psw));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                refreshZKTPassword(view);
                break;
            default:
                Snackbar.make(view,"No action.Check your code",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }



    private void refreshZKTPassword(View view) {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(timePicker.getHour() == hour &&
                    timePicker.getMinute() == minute) {
                Snackbar.make(view,"No need refresh.",Snackbar.LENGTH_SHORT).show();
                isRefreshing = false;
                return;
            }
            timePicker.setHour(hour);
            timePicker.setMinute(minute);
        } else {
            if(timePicker.getCurrentHour() == hour &&
                    timePicker.getCurrentMinute() == minute) {
                Snackbar.make(view,"No need refresh.",Snackbar.LENGTH_SHORT).show();
                isRefreshing = false;
                return;
            }
            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);
        }
        onTimeChanged(timePicker,hour,minute);
        Snackbar.make(view,"Refresh success",Snackbar.LENGTH_SHORT).show();
        isRefreshing = false;
    }

   /* @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                onTimeChanged(timePicker,timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                break;
        }
        return false;
    }*/
}
