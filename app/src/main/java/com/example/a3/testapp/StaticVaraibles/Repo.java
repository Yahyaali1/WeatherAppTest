package com.example.a3.testapp.StaticVaraibles;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.location.Location;
import android.nfc.Tag;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.a3.testapp.ActivityMainActivity;
import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.DataModel.ApiHourlyWeatherData;
import com.example.a3.testapp.DataModel.ApiWeeklyWeatherData;
import com.example.a3.testapp.DataModel.ApiWeeklyWeatherDataList;
import com.example.a3.testapp.DataModel.GeoLocation;
import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.DataModelDataBase.WeatherDataDao;
import com.example.a3.testapp.DataModelDataBase.WeatherDatabase;
import com.example.a3.testapp.R;
import com.example.a3.testapp.ViewModelsGroup.LocationsViewModel;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by IBM on 7/16/2018.
 */

public class Repo {
    private static final String tag = "Repo_Class";
    private static int sucess =1;
    private static int failure=0;
    private WeatherDataDao db;
    private Context context;
    private WeatherApiInterface weatherApiInterface;

    public WeatherDataDao getDb() {
        return db;
    }

    public WeatherApiInterface getWeatherApiInterface() {
        return weatherApiInterface;
    }

    private static Repo Inst;

    public Repo(Context context) {
        db = WeatherDatabase.getDatabase(context).weatherDataDao();
        weatherApiInterface = weatherApiClient.getClient().create(WeatherApiInterface.class);

        this.context=context;
    }

    public static Repo getRepo(final Context context) {

        if (Inst == null) {
            synchronized (Repo.class) {
                if (Inst == null) {

                    Inst = new Repo(context);


                }
            }
        }
        return Inst;
    }
    public List<String> noticationData(List<Locations> locations){
        ArrayList<String> data = new ArrayList<>();

        for ( int i=0;i<locations.size();i++){

            List<DailyWeatherData> summary = db.getNotifcationData(locations.get(i).getLocationId());

            String tmp = context.getString(R.string.NotificationMessage,"<b>"+locations.get(i).getLocationName()+"</b>",summary.get(0).getIconPhraseDay());
            data.add(tmp);


        }

        return data;

    }

    public LiveData<List<Locations>> getAllLocations() {

        LiveData<List<Locations>> obj = db.getAllLocations();
        if (obj == null) {
            return null;
        } else {
            return obj;
        }

    }

    public LiveData<List<HourlyWeatherData>> getNumberofDays(String location, Date today) {
        LiveData<List<HourlyWeatherData>> obj = db.getNumberOfDays(location, today);
        if (obj == null) {
            return null;
        } else {
            return obj;
        }

    }

    public LiveData<List<HourlyWeatherData>> getDailyHourlyDetail(String location, Date today) {


        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_YEAR,1);
        Date tomo = cal.getTime();
        Log.d(tag,tomo.toString()+"tomorrow");

                LiveData<List<HourlyWeatherData>> obj = db.getHourlyDayDetail(location, today, tomo);
        if (obj == null) {
            return null;
        } else {
            return obj;
        }

    }

    public LiveData<List<DailyWeatherData>> getFiveDaysData(String Location, Date date) {

        LiveData<List<DailyWeatherData>> obj = db.getFiveDayData(Location, date);
        if (obj == null) {
            return null;
        } else {
            return obj;
        }

    }

    public LiveData<List<DailyWeatherData>> getFiveDaysDataTest(String Location) {

        LiveData<List<DailyWeatherData>> obj = db.getFiveDayData(Location);
        if (obj == null) {
            Log.d(tag, "Object is null");

            return null;

        } else {
            Log.d(tag, "Object not Null");


            return obj;
        }

    }

    public LiveData<List<HourlyWeatherData>> getHourlyData(String Location, Date date) {

        LiveData<List<HourlyWeatherData>> obj = db.getDailyData(Location, date);
        if (obj == null) {
            return null;
        } else {
            return obj;
        }

    }

    public void getWeeklyData(final String locationId) {
        Call<ApiWeeklyWeatherDataList> call = weatherApiInterface.getWeeklyWeatherDataList(locationId, weatherApiClient.apiKey);

        call.enqueue(new Callback<ApiWeeklyWeatherDataList>() {
            @Override
            public void onResponse(Call<ApiWeeklyWeatherDataList> call, Response<ApiWeeklyWeatherDataList> response) {
                final ApiWeeklyWeatherDataList tmp = response.body();
                //Log.d(tag, tmp.toString());
                final ArrayList<DailyWeatherData> dailyWeatherData = new ArrayList<>();
                if (tmp != null) {
                    List<ApiWeeklyWeatherData> list = tmp.getApiWeeklyWeatherData();


                    for (int i = 0; i < list.size(); i++) {
                        ApiWeeklyWeatherData tmp2 = list.get(i);
                        try {
                            tmp2.prepDate();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DailyWeatherData temp = new DailyWeatherData(locationId, tmp2.getPrepareDate(),
                                (int) tmp2.getTemperature().getMaximum().getValue(), tmp2.getDay().getIconPhrase(),
                                tmp2.getDay().getIcon(), (int) tmp2.getTemperature().getMinimum().getValue()
                                , tmp2.getNight().getIconPhrase(), tmp2.getNight().getIcon());
                        dailyWeatherData.add(temp);
                        Log.d(tag,temp.getDateTime().toString());

                    }
                    Thread thread = new Thread() {
                        @Override
                        public void run() {

                            db.deleteDailyWeatherData(locationId);
                            db.insertDailyWeather(dailyWeatherData);

                        }
                    };
                    thread.start();


                }

            }

            @Override
            public void onFailure(Call<ApiWeeklyWeatherDataList> call, Throwable t) {

                Log.d("onFailure ", "MSg");
            }
        });

    }
    public void UpdateAll(){
        Thread thread = new Thread() {
            @Override
            public void run() {

                List<Locations> listOld = db.getAllCities();

                for (int i = 0; i < listOld.size(); i++) {

                    getWeeklyData(listOld.get(i).getLocationId());
                    getHourlyData(listOld.get(i).getLocationId());


                }

            }
        };

        thread.start();
    }

    public void updateWeeklyDataForCities() {
        final ArrayList<Locations> list = new ArrayList<>();
        Thread thread = new Thread() {
            @Override
            public void run() {

                List<Locations> listOld = db.getAllCities();

                for (int i = 0; i < listOld.size(); i++) {

                    getWeeklyData(listOld.get(i).getLocationId());

                }

            }
        };

        thread.start();


    }

    public void updateHourlyDataForCities(){
        Thread thread = new Thread() {
            @Override
            public void run() {

                List<Locations> listOld = db.getAllCities();

                for (int i = 0; i < listOld.size(); i++) {

                    getHourlyData(listOld.get(i).getLocationId());

                }

            }
        };

        thread.start();
    }

    public List<Locations> ListCitiesDataService(){
        List<Locations> listOld = db.getAllCities();
        if(listOld!=null){
            return listOld;
        }else{
            return null;
        }

    }

    public int updateWeeklyDataCityService(final String locationId){
         final ArrayList<Integer> code = new ArrayList<>();
        Call<ApiWeeklyWeatherDataList> call = weatherApiInterface.getWeeklyWeatherDataList(locationId, weatherApiClient.apiKey);
        try {
            final ApiWeeklyWeatherDataList tmp=call.execute().body();
            //Log.d(tag, tmp.toString());
            final ArrayList<DailyWeatherData> dailyWeatherData = new ArrayList<>();
            if (tmp != null) {
                List<ApiWeeklyWeatherData> list = tmp.getApiWeeklyWeatherData();
                for (int i = 0; i < list.size(); i++) {
                    ApiWeeklyWeatherData tmp2 = list.get(i);
                    try {
                        tmp2.prepDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DailyWeatherData temp = new DailyWeatherData(locationId, tmp2.getPrepareDate(),
                            (int) tmp2.getTemperature().getMaximum().getValue(), tmp2.getDay().getIconPhrase(),
                            tmp2.getDay().getIcon(), (int) tmp2.getTemperature().getMinimum().getValue()
                            , tmp2.getNight().getIconPhrase(), tmp2.getNight().getIcon());
                    dailyWeatherData.add(temp);
                    Log.d(tag,temp.getDateTime().toString()+"Service Downloaded");

                }

                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        db.deleteDailyWeatherData(locationId);
                        db.insertDailyWeather(dailyWeatherData);
                    }
                };

                thread.start();

                code.add(sucess);


            }else {

                code.add(failure);
            }
        } catch (IOException e) {
            e.printStackTrace();
            code.add(failure);
        }


       if(code.size()!=0){
           return code.get(0);
       }else{
           return failure;
       }
    }
    public int updateHourlyDataCityService(final String locationId){
        final ArrayList<Integer> code = new ArrayList<>();

        Call<List<ApiHourlyWeatherData>> call = weatherApiInterface.getHourlyWeatherData(locationId, weatherApiClient.apiKey);

        try {
            List<ApiHourlyWeatherData> tmp = call.execute().body();
            //Log.d(tag, tmp.toString());
            final ArrayList<HourlyWeatherData> hourlyWeatherDataWeatherData = new ArrayList<>();
            if(tmp!=null){

                for (int i =0;i<tmp.size();i++){
                    try {
                        tmp.get(i).prepDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    ApiHourlyWeatherData tmp2 = tmp.get(i);
                    hourlyWeatherDataWeatherData.add(new HourlyWeatherData(locationId,
                            (int)tmp2.getTemperature().getValue(),tmp2.getIconPhrase(),
                            tmp2.getPrepareDate(),tmp2.getWeatherIcon()));
                    Log.d(tag,hourlyWeatherDataWeatherData.get(i).getDateTime().toString());

                }

                Thread thread = new Thread() {
                    @Override
                    public void run() {

                        db.deleteHourlyWeatherData(locationId);
                        db.insertHourlyWeather(hourlyWeatherDataWeatherData);
                    }
                };

                thread.start();
                code.add(sucess);

            }else {
                code.add(failure);

            }
        } catch (IOException e) {
            e.printStackTrace();
            code.add(failure);
        }


        if(code.size()!=0){
            return code.get(0);
        }else{
            return failure;
        }
    }



    public void getHourlyData(final String locationId) {
        Call<List<ApiHourlyWeatherData>> call = weatherApiInterface.getHourlyWeatherData(locationId, weatherApiClient.apiKey);
        call.enqueue(new Callback<List<ApiHourlyWeatherData>>() {
            @Override
            public void onResponse(Call<List<ApiHourlyWeatherData>> call, Response<List<ApiHourlyWeatherData>> response) {
                List<ApiHourlyWeatherData> tmp = response.body();
                //Log.d(tag, tmp.toString());
                final ArrayList<HourlyWeatherData> hourlyWeatherDataWeatherData = new ArrayList<>();
                if(tmp!=null){

                    for (int i =0;i<tmp.size();i++){
                        try {
                            tmp.get(i).prepDate();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ApiHourlyWeatherData tmp2 = tmp.get(i);
                        hourlyWeatherDataWeatherData.add(new HourlyWeatherData(locationId,
                                (int)tmp2.getTemperature().getValue(),tmp2.getIconPhrase(),
                                tmp2.getPrepareDate(),tmp2.getWeatherIcon()));
                        Log.d(tag,hourlyWeatherDataWeatherData.get(i).getDateTime().toString());

                    }
                    Thread thread = new Thread() {
                        @Override
                        public void run() {

                           db.deleteHourlyWeatherData(locationId);
                            db.insertHourlyWeather(hourlyWeatherDataWeatherData);

                        }
                    };
                    thread.start();


                }else {

                }

            }

            @Override
            public void onFailure(Call<List<ApiHourlyWeatherData>> call, Throwable t) {

                Log.d(tag,"Hourly Update Failure ");
            }
        });


    }

    public void AddGeoLocation(String LatLong){
        final Call<GeoLocation> geoLocation = weatherApiInterface.getGeoLocation(weatherApiClient.apiKey,LatLong);
        geoLocation.enqueue(new Callback<GeoLocation>() {
            @Override
            public void onResponse(Call<GeoLocation> call, Response<GeoLocation> response) {

                //Log.d(tag,response.body().toString()+"Hello");
                GeoLocation geTmp = response.body();
                if(geTmp!=null){
                    final Locations locations = new Locations(geTmp.getCityCode(),geTmp.getCityName());


                    Thread thread = new Thread() {
                        @Override
                        public void run() {

                            List<Locations> cities = db.getAllCities();

                            if(cities.contains(locations.getLocationId())){

                            }else{
                                //ActivityMainActivity activityMainActivity = (ActivityMainActivity) context;
                                try{

                                    db.insertLocation(locations);
                                    getWeeklyData(locations.getLocationId());

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }

                        }
                    };
                    thread.start();

                }
            }

            @Override
            public void onFailure(Call<GeoLocation> call, Throwable t) {

                Log.d(tag,"Failed to get data on location service ");
            }
        });

    }
}