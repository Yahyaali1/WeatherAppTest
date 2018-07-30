package com.example.a3.testapp.SupportClasses

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.a3.testapp.ActivityMainActivity
import com.example.a3.testapp.SupportClasses.AlarmHandler.Companion.setUpAlarm

class PrefHandle {
    companion object {
        val DAY_SELECTED="DAY_SELECTED"
        val CHOICE_HOUR="CHOICE_HOUR"
        val CHOICE_TEMP="CHOICE_TEMP"
        val CHOICE_MIN="CHOICE_MIN"
        val DEF_HOUR=1
        val DEF_MIN=15
        val DEF_TEMP=1
        val conversion=60*1000
        val ALARM = "ALARM"
        val LOCATION_PERMISSION=1
        val  UPDATE_INTERVAL:Long = 10 * 1000
        val FASTEST_INTERVAL:Long = 2000
        val SHARED_PREF="Mine"

        //pass activity context
        public fun checkSharePref(activity: Activity){

            val getPref=activity.applicationContext.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

            if(!getPref.contains(CHOICE_HOUR) ){
                with (getPref.edit()) {

                    putInt(CHOICE_HOUR, DEF_HOUR)
                    putInt(CHOICE_MIN, DEF_MIN)
                    putInt(CHOICE_TEMP, DEF_TEMP)

                    apply()
                }

            }

            if(!getPref.contains(ALARM)){
                with (getPref.edit()) {
                    putString(ALARM, ALARM)
                    apply()
                }


                //Create and alarm intent here using the default hou value. Or what ever the hour value is
                var time = getPref.getInt(CHOICE_HOUR,1).toLong()
                //prepare time
                time = time * conversion
                AlarmHandler.setUpAlarm(time,activity)
            }
        }

        public fun saveData(activity: Activity,selectionTimeHour:Int,selectionTimeMinute:Int,selectionModeTemp:Int){

            val getPref= getPref(activity)

            with (getPref.edit()) {
                putInt(CHOICE_HOUR, selectionTimeHour)
                putInt(CHOICE_MIN, selectionTimeMinute)
                putInt(CHOICE_TEMP, selectionModeTemp)
                apply()
            }
        }

         fun getPref(activity: Activity) =
                activity.application.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        fun getPref(context: Context) =
                context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        fun selectionModeT(sharePref: SharedPreferences):Int {
            return sharePref.getInt(CHOICE_TEMP, 1)
        }

        fun selectionTimeM(sharePref: SharedPreferences):Int {
           return sharePref.getInt(CHOICE_MIN, 10)
        }

         fun selectionTimeH(sharePref: SharedPreferences):Int {
            return sharePref.getInt(CHOICE_HOUR, 1)
        }
        fun saveWidgetData(activity: Activity,locationId: String,Data: String){

            with (getPref(activity).edit()) {
                putString(locationId, Data)
                apply()
            }
        }
        fun saveWidgetData(context: Context,locationId: String,Data: String){

            with (getPref(context).edit()) {
                putString(locationId, Data)
                apply()
            }
        }
    }

}