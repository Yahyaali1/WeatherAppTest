package com.example.a3.testapp.SupportClasses

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import com.example.a3.testapp.ActivityMainActivity
import com.example.a3.testapp.ReceiverUpdateWeather
import java.util.*

class AlarmHandler {
    companion object {
        fun setUpAlarm(time: Long, activity: Activity) {
            Log.d(ActivityMainActivity.tag, "Making alarm ")
            val intent = Intent(activity.applicationContext, ReceiverUpdateWeather::class.java)
            val pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 0, intent, 0)
            val now = Calendar.getInstance().timeInMillis
            val AlarmManger = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            AlarmManger.setRepeating(AlarmManager.RTC_WAKEUP, now + time, time, pendingIntent)

            //pending intent is set.
        }

        fun resetUpAlarm(time: Long, activity: Activity) {
            val intent = Intent(activity.applicationContext, ReceiverUpdateWeather::class.java)
            val pendingIntent = PendingIntent.getBroadcast(activity.applicationContext, 0, intent, 0)
            val now = Calendar.getInstance().timeInMillis
            val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager


            try {
                // some code
                alarmManager.cancel(pendingIntent)//cancel the previous intent
            } catch (e: Exception) {
                // handler
                Log.d(ActivityMainActivity.tag, "Exception in creating intent ")
            }


            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, now, time, pendingIntent)
        }

    }
}