package com.example.a3.testapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.StaticVaraibles.Repo;
import com.example.a3.testapp.SupportClasses.updateHandler;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 * helper methods.
 */
public class IntentServiceWeatherUpdate extends IntentService {

    private Repo repo;
    private List<Locations> cities;
    private static int sucess =1;
    private static int failure=0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        fetchDataInBackground();//Notification creator her
        setNotification(repo.dbNotificationData(cities));
        repo.prefWidgetService(cities);
        updateHandler.Companion.widgetUpdate(getApplication());
    }
    public IntentServiceWeatherUpdate(){
        super("IntentServiceWeatherUpdate");
    }
    public IntentServiceWeatherUpdate(String name) {
        super(name);

    }
    private boolean connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
    private void fetchDataInBackground() {
        repo= Repo.getRepo(this);
        cities=repo.ListCitiesDataService();

        if(cities.size()!=0 && connected()){
            int i=0;
            for(;i<cities.size();i++){
                if (repo.ApiWeeklyDataCityService(cities.get(i).getLocationId())==failure){
                    Log.d("Service","Failed in Weekly");
                    break;
                }
                if (repo.ApiHourlyDataCityService(cities.get(i).getLocationId())==failure){
                    Log.d("Service","Failed in Hourly");
                    break;
                }
            }
        }
    }
    private PendingIntent onClickNotifIntent
            (){
        Intent intent = new Intent(this, ActivityMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setNotification(List<String> data){
//        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.layoutnotification);
//       // notificationLayout.setImageViewResource(R.id.NotifcaitonImageView,R.drawable.icon1);
//
//        notificationLayout.setTextViewText(R.id.NotificationtextViewDetailTemp,"21 C");
//        notificationLayout.setTextViewText(R.id.NotificationDetailLabel,"Lahore is raining");
//        notificationLayout.setImageViewResource(R.id.NotifcaitonImageView,R.drawable.icon1);

        NotificationCompat.InboxStyle notificationCompat = new NotificationCompat.InboxStyle();
        for ( int i=0 ; i<data.size();i++){
            notificationCompat.addLine(Html.fromHtml(data.get(i)));
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this,"Notification")
                .setStyle(notificationCompat)
                .setSmallIcon(R.drawable.icon1)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentTitle("Here is your weather update !")
                .setContentText("Checkout weather of each city")

                // Set the intent that will fire when the user taps the notification
                .setContentIntent(onClickNotifIntent())
                .setAutoCancel(true);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1,mBuilder.build());

    }

    private void widgetUpdate(){
        Intent intent = new Intent(this, NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
// Use an array and EXTRA_APPWIDGET_IDS instead of AppWidgetManager.EXTRA_APPWIDGET_ID,
// since it seems the onUpdate() is only fired on that:
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

}
