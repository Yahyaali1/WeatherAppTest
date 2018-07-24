package com.example.a3.testapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.a3.testapp.ActivityMainActivity;
import com.example.a3.testapp.DataModelDataBase.Locations;
import com.example.a3.testapp.R;
import com.example.a3.testapp.StaticVaraibles.Repo;

import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IntentServiceWeatherUpdate extends IntentService {

    private Repo repo;
    private List<Locations> cities;
    private static int sucess =1;
    private static int failure=0;
    private float percentage=0;
    public IntentServiceWeatherUpdate(){
        super("IntentServiceWeatherUpdate");
    }

    public IntentServiceWeatherUpdate(String name) {
        super(name);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        repo=Repo.getRepo(this);
        cities=repo.ListCitiesDataService();
//        if(cities.size()!=0){
//            int i=0;
//            float y=0;
//            for(;i<cities.size();i++){
//
//                if (repo.updateWeeklyDataCityService(cities.get(i).getLocationId())==failure){
//                    Log.d("Service","Failed in Weekly");
//                    break;
//
//                }
//                if (repo.updateHourlyDataCityService(cities.get(i).getLocationId())==failure){
//                    Log.d("Service","Failed in Hourly");
//                    break;
//
//                }
//
//                y=y+1;
//            }
//
//            percentage=(y/Float.valueOf(cities.size()))*100;
//        }

        //Notification creator her

        setNotification(repo.noticationData(cities));


    }

    private PendingIntent Activity(){
        Intent intent = new Intent(this, ActivityMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        return pendingIntent;
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
                .setContentIntent(Activity())
                .setAutoCancel(true);




        if(percentage==0){

        }else{

            mBuilder.setContentText(String.valueOf(percentage)+" % data downloaded");
        }

        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        notificationManager.notify(1,mBuilder.build());

    }

}
