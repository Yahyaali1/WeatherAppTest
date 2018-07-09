package com.example.a3.testapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.widget.RadioGroup
import android.widget.SeekBar
import com.example.a3.testapp.MainActivity.Companion.CHOICE_HOUR
import com.example.a3.testapp.MainActivity.Companion.CHOICE_MIN
import com.example.a3.testapp.MainActivity.Companion.CHOICE_TEMP
import kotlinx.android.synthetic.main.content_settings_page.*

import kotlinx.android.synthetic.main.activity_settings_page.*
import java.nio.channels.SelectableChannel

class SettingsPage : AppCompatActivity() {


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
        var sharePref = this.getPreferences(Context.MODE_PRIVATE)
        SelectionTimeHour=sharePref.getInt(MainActivity.CHOICE_HOUR,1)
        SelectionTimeMinute=sharePref.getInt(MainActivity.CHOICE_MIN,10)
        SelectionModeTemp=sharePref.getInt(MainActivity.CHOICE_TEMP,1)
        Log.d("Progress to push ", SelectionTimeHour.toString())

        seekBarHour.progress=SelectionTimeHour-1 //setting the progress to be less then one hour
        if(SelectionModeTemp==1){
            degree.isChecked=true
            fahrenheit.isChecked=false
        }else{
            fahrenheit.isChecked=true
            degree.isChecked=false
        }



        textViewTimeUpdate.setText((seekBarHour.progress+1).toString()+ hour) //showing the progress to be greater then 1. This is to handle the  cases of 0 & 24
        //ACCES SHARE PREFERENCE
        //SET THE VARIABLES
        //SET THE DISPLAY

    }
    private fun SaveData(){

        var getPref=this.getPreferences(Context.MODE_PRIVATE)

            with (getPref.edit()) {
                putInt(CHOICE_HOUR, SelectionTimeHour)
                putInt(CHOICE_MIN, SelectionTimeMinute)
                putInt(CHOICE_TEMP, SelectionModeTemp)
                commit()
            }


    }

}
