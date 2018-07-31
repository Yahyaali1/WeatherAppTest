package com.example.a3.testapp

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.example.a3.testapp.DataModelDataBase.Locations
import com.example.a3.testapp.StaticVaraibles.Repo
import kotlinx.android.synthetic.main.new_app_widget_configure.*

/**
 * The configuration screen for the [NewAppWidget] AppWidget.
 */
class ActivityNewAppWidgetConfigure : Activity() {


    companion object {

        private val PREFS_NAME = "com.example.a3.testapp.NewAppWidget"
        private val PREF_PREFIX_KEY = "appwidget_"
        private val PREF_POSFIX_KEY = "loc"


        // Write the prefix to the SharedPreferences object for this widget
        internal fun saveTitlePref(context: Context, appWidgetId: Int, text: String,text2: String) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId, text)
            prefs.putString(PREF_PREFIX_KEY + appWidgetId+ PREF_POSFIX_KEY, text2)
            prefs.apply()
        }

        // Read the prefix from the SharedPreferences object for this widget.
        // If there is no preference saved, get the default from a resource
        internal fun loadTitlePref(context: Context, appWidgetId: Int): String {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null)
            return titleValue ?: context.getString(R.string.appwidget_text)
        }

        internal fun deleteTitlePref(context: Context, appWidgetId: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY + appWidgetId)
            prefs.apply()
        }
    }
    internal var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    internal var mAppWidgetText: EditText?=null
    var cities = arrayListOf<Locations>()
    lateinit var listView:ListView
    lateinit var city:List<Locations>
    var attach = arrayListOf<String>()
    inner class BackgroundThred : AsyncTask<Context, String, String>(){

        override fun doInBackground(vararg p0: Context?): String {
//
//
            city=Repo.getRepo(applicationContext).db.allCities


            for (i in 0..city.size-1){
                var tmp = Locations(city.get(i).locationId,city.get(i).locationName)
                cities.add(tmp)
                attach.add(city.get(i).locationName)
            }

            return "Okay"

        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPostExecute(result: String?) {

            loadData()


        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPreExecute() {


        }

    }
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.new_app_widget_configure)
        listView = listViewWidget
        BackgroundThred().execute()
        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

        mAppWidgetText?.setText(loadTitlePref(this@ActivityNewAppWidgetConfigure, mAppWidgetId))
    }
    //SETTING UP THE NEXT WIDGET
    private fun exitActivity(){
        val context = this@ActivityNewAppWidgetConfigure
        // When the button is clicked, store the string locally
        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(context)
        NewAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
    //CALLED TO LOAD THE DATA INTO THE LIST VIEW AFTER THE POST EXECUTION
    private fun loadData(){
        listViewWidget.adapter=ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,attach)
        listView.setOnItemClickListener{ parent, view, position, id->

            saveTitlePref(this@ActivityNewAppWidgetConfigure,mAppWidgetId, cities[position].locationId, cities[position].locationName)

            exitActivity()

        }
    }

}

