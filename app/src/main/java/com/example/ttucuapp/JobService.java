package com.example.ttucuapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class JobService extends android.app.job.JobService {
    boolean jobFinished=false;
    SQLitebatabase sqLitebatabase=new SQLitebatabase(this);


    @Override
    public boolean onStartJob(JobParameters params) {

        backgroundWork(params);
        return true;
    }

    public void backgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<1; i++){
                    test();
                   churchUpdates();
                   deletePastEvents();

                    if(jobFinished){
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                jobFinished(params, true);

            }
        }).start();
    }
    @Override
    public boolean onStopJob(JobParameters params) {
        jobFinished=true;
        return false;
    }

    public  void churchUpdates(){
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    final String type="cUpdates";
                    int user_id=UserDetails.INSTANCE.getUserId();
                    String strings[]=new String[2];
                    strings[0]=type;
                    strings[1]= String.valueOf(user_id);
                    HttpProcesses httpProcesses=new HttpProcesses();
                    response = httpProcesses.sendRequest(strings);
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String response) {
                //do something with response
                try {
                   // Log.i("service",response);
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("status");
                    JSONArray jsonArray = jsonObject.optJSONArray("data");

                    if(result==true){

                        JSONObject jsonObject1=null;
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject1 =jsonArray.getJSONObject(i);
                            int eventId=jsonObject1.getInt("id");
                            String event=jsonObject1.getString("event");
                            String startingDate=jsonObject1.getString("StartDate");
                            String startingTime=jsonObject1.getString("startTime");
                            String eventDetails=jsonObject1.getString("description");
                            String endDate=jsonObject1.getString("expiryDate");

                            NotificationClass notificationClass=new NotificationClass(getBaseContext());
                            notificationClass.createNotification(event, eventDetails);


                            boolean insert=sqLitebatabase.insertUpdate(eventId,event, eventDetails, startingTime, startingDate, endDate);
                            if(insert){
                                Toast.makeText(getApplicationContext(), "Saved offline", Toast.LENGTH_SHORT).show();
                            }




                        }
                        if(jsonObject1==null){
                            return;
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();


    }

    public void test(){
        Log.i("servive","running");
      //  Toast.makeText(getBaseContext(), "Running", Toast.LENGTH_SHORT).show();
    }

    public void deletePastEvents(){
        SQLitebatabase sqLitebatabase=new SQLitebatabase(getApplicationContext());
        Boolean del=sqLitebatabase.delete();
        if(del==true){
            Log.i("Del","Past event deleted");
        }
    }
    public void notifyEvent(){

    }


}