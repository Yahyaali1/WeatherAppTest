package com.example.a3.testapp

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData
import com.example.a3.testapp.DataModelDataBase.WeatherDatabase

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.SimpleDateFormat
import android.arch.lifecycle.ViewModelProviders
import com.example.a3.testapp.DataModelDataBase.DailyWeatherData
import com.example.a3.testapp.DataModelDataBase.Locations
import com.example.a3.testapp.StaticVaraibles.Repo
import com.example.a3.testapp.ViewModelsGroup.LocationsViewModel


class ActivityMainActivity : AppCompatActivity() {
companion object {
    val DAY_SELECTED="DAY_SELECTED"
    val CHOICE_HOUR="CHOICE_HOUR"
    val CHOICE_TEMP="CHOICE_TEMP"
    val CHOICE_MIN="CHOICE_MIN"
    val DEF_HOUR=1
    val DEF_MIN=15
    val DEF_TEMP=1
    val tag = "Main_Activity"

}
    private lateinit var mypageAdapter:PagerAdapter




    private fun checkSharePref(){
        var getPref=this.application.getSharedPreferences("Mine", Context.MODE_PRIVATE)

        if(!getPref.contains(CHOICE_HOUR)){
            with (getPref.edit()) {

                Log.d(tag,"Called")
                putInt(CHOICE_HOUR, DEF_HOUR)
                putInt(CHOICE_MIN, DEF_MIN)
                putInt(CHOICE_TEMP, DEF_TEMP)
                commit()
            }

        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        checkSharePref()
        mypageAdapter = PageViewAdapterMainScreen(supportFragmentManager)
        MainScreenViewPager.adapter=mypageAdapter


        var locationModel:LocationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel::class.java)
        locationModel.activeLocations.observe(this, Observer<List<Locations>> { resource->
            Log.d(tag,"Size of locations"+resource?.size.toString())
            var temp =mypageAdapter as PageViewAdapterMainScreen
            temp.UpdateData(resource)

            mypageAdapter.notifyDataSetChanged()
            //is called first time as well override fun
        })







        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            var Ani = AnimationUtils.loadAnimation(this,R.anim.rotate)
            view.startAnimation(Ani)
            var repo = Repo.getRepo(this).updateWeeklyDataForCities()


        }
    }



    inner class BackgroundThred : AsyncTask<Context,String,String>(){
        override fun doInBackground(vararg p0: Context?): String {
           var db = WeatherDatabase.getDatabase(applicationContext).weatherDataDao()

            val date_s = "2018-07-14T07:00:00+05:00"

            var location = Locations("260670","Islamabad")
            db.insertLocation(location)

            // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
            val dt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm")
            val date = dt.parse(date_s)
            var hourlyweather = HourlyWeatherData("260692",90,"1",date,
                    1)
            var daily = DailyWeatherData("260692",date,0,"Helo",0,0,"Night",0)

            var list = db.getFiveDayData("260692",date);


            return "Okay"

        }

    }


    override fun onBackPressed() {

        if(MainScreenViewPager.currentItem==0){
            super.onBackPressed()
        }else{
            MainScreenViewPager.currentItem=MainScreenViewPager.currentItem-1
        }
        Log.d("Number of Frags",supportFragmentManager.backStackEntryCount.toString())

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    private fun funAnimation(){
        val card = firstCard
        val animationUtils = AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left)
        card.startAnimation(animationUtils)
        secondCard.startAnimation(animationUtils)
        thirdCard.startAnimation(animationUtils)
        //create a static class with these

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, ActivitySettingsPage::class.java)
                startActivity(intent)

                return true

            }
            R.id.action_add_location ->{

                val intent = Intent(this, ActivityAddLocation::class.java)
                startActivity(intent)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
