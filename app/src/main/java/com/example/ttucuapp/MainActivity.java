package com.example.ttucuapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    Handler handler;
    ImageView logo_splash;
    Button lsUpdates, notifications;
    TextView registerForEvent, clickToReg, verseToday, fellowshipToday;
    ScrollView scrollView;
    int selected=0;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Button back;
    TextView textView2;
    int eventId=0;
    String event="";
    String startingDate="";
    String regEndDate="";
    String eventDetails="";
    SQLitebatabase sqLitebatabase;
    BottomNavigationView bottomNavigationView;
    LinearLayout home, ministries, aboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo_splash=findViewById(R.id.logo_splash);

        sqLitebatabase=new SQLitebatabase(getApplicationContext());
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor cursor= sqLitebatabase.syncDetails();
                if(cursor !=null && cursor.getCount()>0){
                    homeLayout();

                    if(!isMyServiceRunning(JobService.class)) {
                        startService(new Intent(MainActivity.this, JobService.class));
                        startJob();
                    }
                }else{
                    startActivity(new Intent(MainActivity.this, Login.class));
                }

            }
        }, 3000);

        if(!isMyServiceRunning(JobService.class)) {
            startJob();
        }

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public void startJob() {
        ComponentName componentName=new ComponentName(this, JobService.class);
        JobInfo jobInfo=new JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();
        JobScheduler jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int result=jobScheduler.schedule(jobInfo);

        if(result==JobScheduler.RESULT_SUCCESS){
            Log.i("Scheduled", "Running" );
        }else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void homeLayout (){
        setContentView(R.layout.activity_church);
        registerForEvent=findViewById(R.id.registerForEvent);
        clickToReg=findViewById(R.id.clickToRegister);



        notifications=findViewById(R.id.notifications);
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), notifications.class));
            }
        });

       // newEventForRegister();

        lsUpdates=findViewById(R.id.LSUpdates);
        //aboutChurch=findViewById(R.id.aboutChurch);

        navigationView=findViewById(R.id.navigation);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.Open_navigation, R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if(UserDetails.INSTANCE.getExec()==2){
            lsUpdates.setVisibility(View.VISIBLE);
            registerForEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), DownloadPDF.class));
                }
            });
        }
        lsUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), LS.class);
                startActivity(intent);
            }
        });
       // bottomNavigationView = findViewById(R.id.bottomNavigationView);

     //   bottomNavigationView.setOnNavigationItemSelectedListener(this);
     //   bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        final TextView fell=findViewById(R.id.fell);
        final TextView ftime=findViewById(R.id.ftime);
        final TextView ven=findViewById(R.id.ven);
        final TextView des=findViewById(R.id.des);
        final String type="churchProgramme";

        switch (Calendar.DAY_OF_WEEK){
            case 1:
                selected=2;
                break;
            case 2:
            case 5:
            case 7:

                selected=1;
                break;
            case 3:
                selected=3;
                break;
            case 4:
                selected=4;
                break;
            case 6:
                selected=5;
                break;
        }
       /* if(Calendar.DAY_OF_WEEK==3){
            selected=3;
        }*/



        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {

                    String strings[]=new String[2];
                    strings[0]=type;
                    strings[1]= String.valueOf(selected);
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
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("status");
                    if(result==true){
                        JSONArray dataArr=jsonObject.getJSONArray("data");
                        JSONObject data = dataArr.getJSONObject(0);
                        String f=data.getString("fellowship");
                        String t=data.getString("time");
                        String v=data.getString("Venue");
                        String de=data.getString("description");

                        fell.setText(f);
                        ftime.setText(t);
                        ven.setText(v);
                        des.setText(de);

                    }
                } catch (JSONException e) {
                    setContentView(R.layout.error_layout);
                    Button retry=findViewById(R.id.retry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           homeLayout();
                        }
                    });
                    e.printStackTrace();
                }
            }

        }.execute();

    }


    public void aboutChurch(View v){
        setContentView(R.layout.church_info);
        scrollView=findViewById(R.id.about);
        scrollView.setVisibility(View.VISIBLE);
        final TextView churchVision=findViewById(R.id.vision);
        final TextView churchMission=findViewById(R.id.mission);
        final TextView aboutChurch=findViewById(R.id.infoAbout);
        final TextView history=findViewById(R.id.history);
        Button back=findViewById(R.id.backToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLayout();
            }
        });

        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    final String type="churchInfo";
                    String strings[]=new String[1];
                    strings[0]=type;
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


                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("status");
                    String message=jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.optJSONArray("data");

                    if(result==true){

                        JSONObject jsonObject1=null;
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject1 =jsonArray.getJSONObject(i);

                            // int eventId=jsonObject1.getInt("id");


                            String vis=jsonObject1.getString("vision");
                            String mis=jsonObject1.getString("mission");
                            String about=jsonObject1.getString("aboutUs");
                            String his=jsonObject1.getString("history");

                            churchVision.setText(vis);
                            churchMission.setText(mis);
                            aboutChurch.setText(about);
                            history.setText(his);



                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            // registerForEvent.setVisibility(View.VISIBLE);
                            //  registerForEvent.setText("Register for "+ event);

                            // NotificationClass notificationClass=new NotificationClass(getBaseContext());
                            // notificationClass.createNotification(event, eventDetails);

                        }}
                } catch (JSONException e) {
                    setContentView(R.layout.error_layout);
                    Button retry=findViewById(R.id.retry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           homeLayout();
                        }
                    });
                    e.printStackTrace();
                }
            }

        }.execute();

    }
   /* public void programme(){
        setContentView(R.layout.church_programe);
        back=findViewById(R.id.backToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeLayout();
            }
        });
        final TextView fell=findViewById(R.id.fell);
        final TextView day=findViewById(R.id.fday);
        final TextView ftime=findViewById(R.id.ftime);
        final TextView ven=findViewById(R.id.ven);
        final TextView des=findViewById(R.id.des);
        final String type="churchProgramme";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("These are Our Fellowships");
        final String[]  fellType=
                {"WEEKLY DEVOTIONS",
                "SUNDAY SERVICE",
                "TUESDAY FELLOWSHIP",
                "WORSHIP HOUR",
                "MINI-KESHA"};
        builder.setItems(fellType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        selected=0;
                        break;
                    case 1:
                        selected=1;
                        break;
                    case 2:
                        selected=2;
                        break;
                    case 3:
                        selected=3;
                        break;
                    case 4:
                        selected=4;
                        break;

                }
                new android.os.AsyncTask<Void, Void, String>(){
                    protected String doInBackground(Void[] params) {
                        String response="";
                        try {

                            String strings[]=new String[2];
                            strings[0]=type;
                            strings[1]= String.valueOf(selected+1);
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
                            JSONObject jsonObject=new JSONObject(response);
                            Boolean result=jsonObject.getBoolean("status");
                            if(result==true){
                                JSONArray dataArr=jsonObject.getJSONArray("data");
                                JSONObject data = dataArr.getJSONObject(0);
                                String f=data.getString("fellowship");
                                String d=data.getString("day");
                                String t=data.getString("time");
                                String v=data.getString("Venue");
                                String de=data.getString("description");

                                fell.setText(f);
                                day.setText(d);
                                ftime.setText(t);
                                ven.setText(v);
                                des.setText(de);

                            }
                        } catch (JSONException e) {
                            setContentView(R.layout.error_layout);
                            Button retry=findViewById(R.id.retry);
                            retry.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            });
                            e.printStackTrace();
                        }
                    }

                }.execute();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 10);
        c.set(Calendar.MINUTE, 9);
        c.set(Calendar.SECOND, 0);
        startAlarm(c);
        String hour= String.valueOf(hourOfDay);
        String min= String.valueOf(minute);
        textView2.setText(hour+" : "+min);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void startAlarm(Calendar c){
        AlarmManager alarmManager=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this, alertReciever.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,1, intent, 0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
        String alarmText="Alarm Set For :";
        alarmText += DateFormat.getDateTimeInstance().format(c.getTime());
        textView2.setText(alarmText);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return ;

    }



    public void ministries(View v) {
        Intent intent=new Intent(getApplicationContext(), Ministries.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.logout) {
            logout();
            return  true;
        } else if (item.getItemId() == R.id.youtube) {
            Intent intent = new Intent(getApplicationContext(), YouTube.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.profile) {

            Intent intent=new Intent(getApplicationContext(), profile.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.semSchedule) {
            startActivity(new Intent(getApplicationContext(), Schedules.class));
            return true;
        }else if(item.getItemId()==R.id.leadership){
            Intent intent=new Intent(getApplicationContext(), leadership.class);
            startActivity(intent);
            return true;
        }
        else if(item.getItemId()==R.id.give){
            return true;
        }else {

            return false;
        }

    }

    public void logout(){
        SQLitebatabase sqLitebatabase;
        sqLitebatabase=new SQLitebatabase(this);
        sqLitebatabase.logOut();
        Intent intent=new Intent(getApplicationContext(), Login.class);
        startActivity(intent);

    }

    public void registerForevent(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(eventDetails)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                eventRegistration();
                                homeLayout();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Do you want to Register for "+event+"?");
                alert.show();

    }

    public  void newEventForRegister(){
        int userid=UserDetails.INSTANCE.getUserId();
        int eventid=NewRegistration.INSTANCE.getEventId();
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    final String type="newEventForRegister";
                    String strings[]=new String[3];
                    strings[0]=type;
                    strings[1]= String.valueOf(eventid);
                    strings[2]= String.valueOf(userid);
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
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("status");
                    String message=jsonObject.getString("message");
                    JSONArray jsonArray = jsonObject.optJSONArray("data");

                    if(result==false && message.equals("Already Registered")){
                        registerForEvent.setText(null);
                    }else if(result==true){
                        JSONObject jsonObject1=null;
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject1 =jsonArray.getJSONObject(i);

                             eventId=jsonObject1.getInt("id");
                             event=jsonObject1.getString("event_name");
                             startingDate=jsonObject1.getString("start_date");
                             regEndDate=jsonObject1.getString("reg_end_date");
                             eventDetails=jsonObject1.getString("description");
                             NewRegistration.INSTANCE.setEventId(eventId);
                             NewRegistration.INSTANCE.setEvent(event);
                             NewRegistration.INSTANCE.setStartingDate(startingDate);
                             NewRegistration.INSTANCE.setRegEndDate(regEndDate);
                             NewRegistration.INSTANCE.setEventDetails(eventDetails);
                             if(!event.equals("")){
                                 if(UserDetails.INSTANCE.getExec()==2){
                                     registerForEvent.setText("See registered members for "+event);
                                 }else {
                                     registerForEvent.setText("We have "+ event+" on "+startingDate);
                                     clickToReg.setText("Click here to Register");
                                 }
                             }
                        }
                        if(jsonObject1==null){
                            return;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    setContentView(R.layout.error_layout);
                    Button retry=findViewById(R.id.retry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            homeLayout();
                        }
                    });

                }
            }

        }.execute();
    }

    public void eventRegistration(){
        String type="registerForEvent";
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void[] params) {
                String response = "";
                try {
                    String[] strings = new String[3];
                    strings[0] = type;
                    strings[1] =String.valueOf(NewRegistration.INSTANCE.getEventId());
                    strings[2] =  String.valueOf(UserDetails.INSTANCE.getUserId());
                    HttpProcesses httpProcesses = new HttpProcesses();
                    response = httpProcesses.sendRequest(strings);

                } catch (Exception e) {
                    response = e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String response) {
                //do something with response
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean result = jsonObject.getBoolean("status");
                    String message = jsonObject.getString("message");
                    if (result == true) {
                        Toast.makeText(getApplicationContext(),message , Toast.LENGTH_SHORT).show();;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setContentView(R.layout.error_layout);
                    Button retry=findViewById(R.id.retry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            homeLayout();
                        }
                    });

                    e.printStackTrace();
                }

            }
        }.execute();

    }

    public void home(View view) {
        homeLayout();
    }
}
