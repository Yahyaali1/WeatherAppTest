package com.example.a3.testapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData
import com.example.a3.testapp.SupportClasses.AssetSupport
import com.example.a3.testapp.SupportClasses.Conversion
import com.google.gson.Gson


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [ActivityNewAppWidgetConfigure]
 */
class NewAppWidget : AppWidgetProvider() {
    lateinit var context: Context

    lateinit var appWidgetManager: AppWidgetManager
    lateinit var appWidgetIds: IntArray


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        this.context=context
        this.appWidgetManager=appWidgetManager
        this.appWidgetIds=appWidgetIds

        for (appWidgetId in appWidgetIds) {

        }


    }


    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            ActivityNewAppWidgetConfigure.deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int, data:HourlyWeatherData) {

            val widgetText = ActivityNewAppWidgetConfigure.loadTitlePref(context, appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)



            views.setTextViewText(R.id.textViewCityWidget,widgetText)



            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {

            val locationId = ActivityNewAppWidgetConfigure.loadTitlePref(context, appWidgetId)
            val locationName = loadCityName(context,appWidgetId)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)

            var hourly = loadHourlyData(context,locationId)

            fun onClickNotifIntent(): PendingIntent {
                val intent = Intent(context, ActivityMainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                return PendingIntent.getActivity(context, 0, intent, 0)
            }


            views.setOnClickPendingIntent(R.id.relativeViewWidget,onClickNotifIntent())
            views.setTextViewText(R.id.textViewCityWidget,locationName)
            views.setTextViewText(R.id.textViewLabelWidget,hourly.iconPhrase)
            views.setTextViewText(R.id.textViewTempWidget,Conversion.Convert(Conversion.Choice(context.applicationContext),hourly.temperatureValue))

            views.setImageViewResource(R.id.imageViewDayWidget,AssetSupport().getId(hourly.iconId))


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }



        private val PREFS_NAME = "com.example.a3.testapp.NewAppWidget"
        private val PREF_PREFIX_KEY = "appwidget_"
        private val PREF_POSFIX_KEY = "loc"

            // Write the prefix to the SharedPreferences object for this widget
            internal fun saveTitlePref(context: Context, appWidgetId: Int, text: String) {
                val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
                prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
                prefs.apply()
            }

            // Read the prefix from the SharedPreferences object for this widget.
            // If there is no preference saved, get the default from a resource

        internal fun loadTitlePref(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
            return titleValue ?: context.getString(R.string.appwidget_text)
        }
        internal fun loadHourlyData(context: Context, locationId: String): HourlyWeatherData{
            val prefs = context.applicationContext.getSharedPreferences("Mine", 0)
            val value = prefs.getString(locationId, null)

            val gson = Gson()
            val obj = gson.fromJson(value, HourlyWeatherData::class.java)
            return obj
        }
        internal fun loadCityName(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId+ PREF_POSFIX_KEY, null)
            return titleValue ?: context.getString(R.string.appwidget_text)
        }

            internal fun deleteTitlePref(context: Context, appWidgetId: Int) {
                val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
                prefs.remove(PREF_PREFIX_KEY + appWidgetId)
                prefs.apply()
            }
    }
}

