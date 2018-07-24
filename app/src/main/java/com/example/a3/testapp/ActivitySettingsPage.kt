package com.example.a3.testapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import com.example.a3.testapp.ActivityMainActivity.Companion.CHOICE_HOUR
import com.example.a3.testapp.ActivityMainActivity.Companion.CHOICE_MIN
import com.example.a3.testapp.ActivityMainActivity.Companion.CHOICE_TEMP
import com.example.a3.testapp.ActivityMainActivity.Companion.tag
import kotlinx.android.synthetic.main.content_settings_page.*

import kotlinx.android.synthetic.main.activity_settings_page.*
import java.util.*

class ActivitySettingsPage : AppCompatActivity() {


    companion object {
        var hour:String=" Hour"
    }
    var SelectionTimeHour:Int=0;
    var SelectionTimeMinute:Int=0;
    var SelectionModeTemp=1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)
        setSupportActionBar(toolbar)
        LoadData() //to load the data from Sharedprefernecces

        var radioParen = tempChoice.setOnCheckedChangeListener{r,pos ->
            if(pos==degree.id){
                SelectionModeTemp=1
            }else if (pos==fahrenheit.id){
                SelectionModeTemp=2
            }
        }

        seekBarHour.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
                textViewTimeUpdate.setText((seekBarHour.progress+1).toString()+ hour)
                Log.d("Progress",progress.toString())
                SelectionTimeHour=seekBar.progress+1 //1 is to avoid the minmum of 0 in this field
                Log.d("Progress",progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
                //textViewTimeUpdate.setText((seekBarHour.progress+1).toString()+ hour)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                //textViewTimeUpdate.setText((seekBarHour.progress+1).toString()+ hour)
                //
            }
        })
        fab.setOnClickListener { view ->


            SaveData()

            Snackbar.make(view, "Data Has been saved", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun LoadData(){
        var sharePref = this.application.getSharedPreferences("Mine",Context.MODE_PRIVATE)
        SelectionTimeHour=sharePref.getInt(ActivityMainActivity.CHOICE_HOUR,1)
        SelectionTimeMinute=sharePref.getInt(ActivityMainActivity.CHOICE_MIN,10)
        SelectionModeTemp=sharePref.getInt(ActivityMainActivity.CHOICE_TEMP,1)
        Log.d("Progress to push ", SelectionModeTemp.toString())

        seekBarHour.progress=SelectionTimeHour-1 //setting the progress to be less then one hour
        if(SelectionModeTemp==1){
            degree.isChecked=true
            fahrenheit.isChecked=false
        }else if (SelectionModeTemp==2){

            fahrenheit.isChecked=true
            degree.isChecked=false
        }



        textViewTimeUpdate.setText((seekBarHour.progress+1).toString()+ hour) //showing the progress to be greater then 1. This is to handle the  cases of 0 & 24
        //ACCES SHARE PREFERENCE
        //SET THE VARIABLES
        //SET THE DISPLAY

    }
    private fun SaveData(){

        var getPref=this.application.getSharedPreferences("Mine",Context.MODE_PRIVATE)

            with (getPref.edit()) {
                putInt(CHOICE_HOUR, SelectionTimeHour)
                putInt(CHOICE_MIN, SelectionTimeMinute)
                putInt(CHOICE_TEMP, SelectionModeTemp)
                commit()
            }


        setUpAlarm(SelectionTimeHour*ActivityMainActivity.conversion.toLong())
        //setting up alarm again


    }
    private fun setUpAlarm(time:Long){
        var intent = Intent(this.applicationContext,ReceiverUpdateWeather::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this.applicationContext,0,intent,0)
        var now = Calendar.getInstance().timeInMillis
        var AlarmManger =getSystemService(Context.ALARM_SERVICE) as AlarmManager


        try {
            // some code
            AlarmManger.cancel(pendingIntent)//cancel the previous intent
        }
        catch (e: Exception) {
            // handler
            Log.d(tag,"Exception in creating intent ")
        }


        AlarmManger.setRepeating(AlarmManager.RTC_WAKEUP,now,time,pendingIntent)

        //pending intent is set.

    }

}
