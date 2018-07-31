package com.example.a3.testapp.SupportClasses

import android.app.Application
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import com.example.a3.testapp.NewAppWidget


class updateHandler {
    companion object {
        public fun widgetUpdate(application : Application){

            val intent = Intent(application, NewAppWidget::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = AppWidgetManager.getInstance(application).getAppWidgetIds(ComponentName(application.applicationContext, NewAppWidget::class.java!!))
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            application.sendBroadcast(intent)
        }

    }

}