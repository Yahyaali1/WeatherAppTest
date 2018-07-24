package com.example.a3.testapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
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
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.Toast
import com.example.a3.testapp.ActivityMainActivity.Companion.LOCATION_PERMISSION
import com.example.a3.testapp.DataModelDataBase.DailyWeatherData
import com.example.a3.testapp.DataModelDataBase.Locations
import com.example.a3.testapp.R.id.*
import com.example.a3.testapp.StaticVaraibles.Repo
import com.example.a3.testapp.ViewModelsGroup.LocationsViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import java.util.*


class ActivityMainActivity : AppCompatActivity() {


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
    val tag = "Main_Activity"
    val LOCATION_PERMISSION=1
        val  UPDATE_INTERVAL:Long = 10 * 1000
        val FASTEST_INTERVAL:Long = 2000

}
    private lateinit var mypageAdapter:PagerAdapter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun checkSharePref(){
        val getPref=this.application.getSharedPreferences("Mine", Context.MODE_PRIVATE)

        if(!getPref.contains(CHOICE_HOUR) ){
            with (getPref.edit()) {

                Log.d(tag,"Called")
                putInt(CHOICE_HOUR, DEF_HOUR)
                putInt(CHOICE_MIN, DEF_MIN)
                putInt(CHOICE_TEMP, DEF_TEMP)
                putString(ALARM, ALARM)

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
            setUpAlarm(time)
        }

    }

    private fun setUpAlarm(time:Long){
        val intent = Intent(this.applicationContext,ReceiverUpdateWeather::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this.applicationContext,0,intent,0)
        val now = Calendar.getInstance().timeInMillis
        val AlarmManger =getSystemService(Context.ALARM_SERVICE) as AlarmManager
        AlarmManger.setRepeating(AlarmManager.RTC_WAKEUP,now+time,time,pendingIntent)

        //pending intent is set.

    }


    override fun onResume() {
        super.onResume()
        mypageAdapter.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        checkSharePref()
        mypageAdapter = PageViewAdapterMainScreen(supportFragmentManager)
        MainScreenViewPager.adapter=mypageAdapter
        setUpViewModel()
        SetUpFloatingButtons()
    }

    private fun setUpViewModel() {
        val locationModel: LocationsViewModel = ViewModelProviders.of(this).get(LocationsViewModel::class.java)
        locationModel.activeLocations.observe(this, Observer<List<Locations>> { resource ->
            Log.d(tag, "Size of locations" + resource?.size.toString())
            var temp = mypageAdapter as PageViewAdapterMainScreen
            temp.UpdateData(resource)
            mypageAdapter.notifyDataSetChanged()
            //is called first time as well override fun
        })
    }

    private fun SetUpFloatingButtons() {
        fab.setOnClickListener { view ->

            val Ani = AnimationUtils.loadAnimation(this, R.anim.rotate)
            view.startAnimation(Ani)
            GetUpdatedData()
            Snackbar.make(view, "Updating data for all locations ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()


        }


        locationfab.setOnClickListener { view ->
            DisplaySnackBar(view, "Finding Current Location")
            val Ani = AnimationUtils.loadAnimation(this, R.anim.rotate)
            view.startAnimation(Ani)
            settingPermission()


        }
    }

    private fun DisplaySnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    private fun GetUpdatedData() {
        Repo.getRepo(this).updateWeeklyDataForCities()
        Repo.getRepo(this).updateHourlyDataForCities()

    }


    private fun settingPermission(){

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){


            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                Log.d(tag,"Already persmisison")
                getWeatherByGeocode()

            }else{
                val perms = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION)

                ActivityCompat.requestPermissions(this,perms, LOCATION_PERMISSION)

                Log.d(tag,"Request permission")
            }


        }else{
            getWeatherByGeocode()
        }
    }

    @SuppressLint("MissingPermission", "RestrictedApi")
    private fun getWeatherByGeocode(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location->
            if(location!=null){ //add location to the database and update the data
                Log.d(tag, location.latitude.toString())
                Repo.getRepo(this).AddGeoLocation(location.latitude.toString()+","+location.longitude.toString())
                GetUpdatedData()
                //call the location
            }else{ //start activity to enable location settings

                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==false){
                    Toast.makeText(this,"Location Settings not Enabled ", Toast.LENGTH_LONG).show()
                    val callGPSSettingIntent = Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(callGPSSettingIntent, LOCATION_PERMISSION)

                }
            }

        }
        fusedLocationClient.lastLocation.addOnFailureListener{
            failed->

            }

        }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    getWeatherByGeocode()

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    DisplaySnackBar(findViewById(R.id.MainScreenLayout),"Can not fetch data without permission")

                }
                return
            }

        // Add other 'when' lines to check for other
        // permissions this app might request.
            else -> {
                // Ignore all other requests.

            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== LOCATION_PERMISSION){
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)==false) {
            Toast.makeText(this,"Location settings not enabled, can not fetch data ", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Fetching data now ", Toast.LENGTH_LONG).show()
                getWeatherByGeocode()


            }
        }


    }
    inner class BackgroundThred : AsyncTask<Context,String,String>(){
        override fun doInBackground(vararg p0: Context?): String {
//            var db = WeatherDatabase.getDatabase(applicationContext).weatherDataDao()
//
//            val date_s = "2018-07-14T07:00:00+05:00"
//
//            var location = Locations("260670","Islamabad")
//            db.insertLocation(location)
//
//            // *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
//            val dt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'HH:mm")
//            val date = dt.parse(date_s)
//            var hourlyweather = HourlyWeatherData("260692",90,"1",date,
//                    1)
//            var daily = DailyWeatherData("260692",date,0,"Helo",0,0,"Night",0)
//
//            var list = db.getFiveDayData("260692",date);
//

            return "Okay"

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

    }
}
