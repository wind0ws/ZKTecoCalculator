package com.threshold.zktecocalculator

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar()
        bindingEvents()
        refreshZKTPassword(fabRefresh)
    }

    private fun setSupportActionBar() {
        toolbar.setTitleTextColor(Color.YELLOW)
        setSupportActionBar(toolbar)
    }

    private fun bindingEvents() {
        fabRefresh.setOnClickListener(this)
        fabTimePicker.setOnClickListener(this)
    }


    //There is a bug on Android 5.0 and 5.0.2 ,the bug is TimePicker's onTimeChanged event is not performed.
    //If you have any solution,please let me know,Thanks!
    fun onTimeChanged(hourOfDay: Int, minute: Int) {
        val hour = String.format("%02d", hourOfDay)
        val minuteStr = String.format("%02d", minute)
        Log.d("ZKTeco", hour + minuteStr)
        val time = Integer.parseInt(hour + minuteStr)
        val password = Math.pow((9999 - time).toDouble(), 2.0)
        tvContent.text = password.toInt().toString()
        Snackbar.make(toolbar, "时间 $hourOfDay:$minute ,刷新成功", Snackbar.LENGTH_SHORT).show()
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.fabRefresh -> refreshZKTPassword(view)
            R.id.fabTimePicker -> showTimePickerDialog()
            else -> Snackbar.make(view, "No action.Check your code", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(this,{
            timePicker, hour, minute -> onTimeChanged(hour,minute)
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true)
        timePickerDialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_info -> {
                showInfo()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showInfo() {
        with(AlertDialog.Builder(this)) {
            setTitle("使用说明")
            setMessage("中控超级用户8888，密码是根据时间计算而来的。\n请在1分钟内输入获取的密码")
            setCancelable(true)
            setNegativeButton("了解了",{ dialog, _ ->
                dialog.dismiss()
            })
        }.apply {
            create()
        }.show()
    }


    private fun refreshZKTPassword(view: View) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        onTimeChanged(currentHour, currentMinute)
    }

}
