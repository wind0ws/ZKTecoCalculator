package com.threshold.zktecocalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TimePicker.OnTimeChangedListener {

    private TextView tvContent;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        bindingEvents();
        initData();
    }

    @SuppressWarnings("deprecation")
    private void initData() {
        timePicker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minute);
        onTimeChanged(timePicker,hour,minute);
    }


    private void bindingEvents() {
        timePicker.setOnTimeChangedListener(this);
//        timePicker.setOnTouchListener(this);
    }

    private void bindingViews() {
        tvContent = (TextView) findViewById(R.id.tvContent);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
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
