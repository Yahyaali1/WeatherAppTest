package com.example.a3.testapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat.setBackgroundTintList
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.SeekBar
import com.example.a3.testapp.ActivityMainActivity.Companion.CHOICE_TEMP
import com.example.a3.testapp.ActivityMainActivity.Companion.tag
import com.example.a3.testapp.SupportClasses.AlarmHandler
import com.example.a3.testapp.SupportClasses.PrefHandle
import com.example.a3.testapp.SupportClasses.PrefHandle.Companion.selectionModeT
import com.example.a3.testapp.SupportClasses.PrefHandle.Companion.selectionTimeH
import com.example.a3.testapp.SupportClasses.PrefHandle.Companion.selectionTimeM
import com.example.a3.testapp.SupportClasses.updateHandler
import kotlinx.android.synthetic.main.content_settings_page.*

import kotlinx.android.synthetic.main.activity_settings_page.*
import java.util.*

class ActivitySettingsPage : AppCompatActivity() {


    companion object {
        var hour:String=" Hour"
    }
    private var selectionTimeHour:Int=0
    private var selectionTimeMinute:Int=0
    private var selectionModeTemp=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_page)
        setSupportActionBar(toolbar)
        loadData() //to load the data from Sharedprefernecces

        tempChoice.setOnCheckedChangeListener{r,pos ->
            if(pos==degree.id){
                selectionModeTemp=1
            }else if (pos==fahrenheit.id){
                selectionModeTemp=2
            }
            fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))

        }

        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        seekBarHour.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
                textViewTimeUpdate.text = (seekBarHour.progress+1).toString()+ hour
                Log.d("Progress",progress.toString())
                selectionTimeHour=seekBar.progress+1 //1 is to avoid the minmum of 0 in this field
                Log.d("Progress",progress.toString())
                fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
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


            saveData()

            Snackbar.make(view, "Preferences updated ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun loadData(){
        selectionTimeHour=selectionTimeH(PrefHandle.getPref(this))
        selectionTimeMinute=selectionTimeM(PrefHandle.getPref(this))
        selectionModeTemp=selectionModeT(PrefHandle.getPref(this))
        Log.d("Progress to push ", selectionModeTemp.toString())

        seekBarHour.progress=selectionTimeHour-1 //setting the progress to be less then one hour
        if(selectionModeTemp==1){
            degree.isChecked=true
            fahrenheit.isChecked=false
        }else if (selectionModeTemp==2){

            fahrenheit.isChecked=true
            degree.isChecked=false
        }



        textViewTimeUpdate.text = (seekBarHour.progress+1).toString()+ hour //showing the progress to be greater then 1. This is to handle the  cases of 0 & 24
        //ACCES SHARE PREFERENCE
        //SET THE VARIABLES
        //SET THE DISPLAY

    }
    private fun saveData(){

        PrefHandle.saveData(this,selectionTimeHour,selectionTimeMinute,selectionModeTemp)
        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        AlarmHandler.resetUpAlarm(selectionTimeHour*PrefHandle.conversion.toLong(),this)

        updateHandler.widgetUpdate(application)


    }



}
