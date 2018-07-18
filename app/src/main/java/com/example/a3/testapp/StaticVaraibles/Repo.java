package com.example.a3.testapp.StaticVaraibles;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.example.a3.testapp.ApiInterfaces.WeatherApiInterface;
import com.example.a3.testapp.DataModel.ApiHourlyWeatherData;
import com.example.a3.testapp.DataModel.ApiWeeklyWeatherData;
import com.example.a3.testapp.DataModel.ApiWeeklyWeatherDataList;
import com.example.a3.testapp.DataModelDataBase.DailyWeatherData;
import com.example.a3.testapp.DataModelDataBase.HourlyWeatherData;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.DataModelDataBase.WeatherDataDao;
import com.example.a3.testapp.DataModelDataBase.WeatherDatabase;

import java.text.ParseException;
import java.util.ArrayList;
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
    private WeatherDataDao db;
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
        LiveData<List<HourlyWeatherData>> obj = db.getHourlyDayDetail(location, today);
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
                Log.d(tag, tmp.toString());
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


    public void getHourlyData(final String locationId) {
        Call<List<ApiHourlyWeatherData>> call = weatherApiInterface.getHourlyWeatherData(locationId, weatherApiClient.apiKey);
        call.enqueue(new Callback<List<ApiHourlyWeatherData>>() {
            @Override
            public void onResponse(Call<List<ApiHourlyWeatherData>> call, Response<List<ApiHourlyWeatherData>> response) {
                List<ApiHourlyWeatherData> tmp = response.body();
                Log.d(tag, tmp.toString());
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
                        Log.d(tag,tmp2.toString());

                    }
                    Thread thread = new Thread() {
                        @Override
                        public void run() {

                            //db.deleteDailyWeatherData(locationId);
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
}