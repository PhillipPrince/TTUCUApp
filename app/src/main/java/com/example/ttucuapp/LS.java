package com.example.ttucuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LS extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    ListView updatesOn;
    Button    sendMinistry, sendProgramme, sendInfo;
    ScrollView info;
    LinearLayout  createEvent, programme,  updatesemScedule, tpc;
    EditText startDate, startTime, endDate;
    TextView sdate;
    Button sendSchedule, back;
    Button sendUpdate;
    EditText eventType, eventDescription,  fellowship, dayOfTheProgramme, timeToMeet,
            editProgramme,venue, mission, vision, aboutUs, history, speaker, programmer, topic;
    Spinner chooseMinistry;
    int i=0;
    int assignId=0;
    String dateType="";
    String evDate="";
    int sunday=0;
    int tuesday=0;
    EditText regFor, evendate, regEnd, regDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_s);
        updatesOn=findViewById(R.id.updateson);
       // createEvent=findViewById(R.id.sendMessage);
        chooseMinistry=findViewById(R.id.selectMinistry);
        sendMinistry=findViewById(R.id.sendToMinistry);
        sendProgramme=findViewById(R.id.sendProgramme);
        programme=findViewById(R.id.programme);
        fellowship=findViewById(R.id.fellowship);
        dayOfTheProgramme=findViewById(R.id.dayOfProgramme);
        timeToMeet=findViewById(R.id.timeToMeet);
        venue=findViewById(R.id.venue);
        editProgramme=findViewById(R.id.editProgramme);
        info=findViewById(R.id.updatechurchInfo);
        vision=findViewById(R.id.churchVision);
        mission=findViewById(R.id.churchMission);
        aboutUs=findViewById(R.id.aboutUs);
        history=findViewById(R.id.history);
        sendInfo=findViewById(R.id.sendInfo);
        tpc=findViewById(R.id.tpc);

        sdate=findViewById(R.id.date);
        speaker=findViewById(R.id.speaker);
        programmer=findViewById(R.id.programmer);
        topic=findViewById(R.id.topic);
        sendSchedule=findViewById(R.id.sendSchedule);
        updatesemScedule=findViewById(R.id.updateSemSchedule);


        sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
                dateType="date";
            }
        });

        ArrayList<String> chooseUpdate=new ArrayList<>();
        chooseUpdate.add("Church Updates");
        chooseUpdate.add("About Us");
        chooseUpdate.add("Church Programme");
        chooseUpdate.add("About Ministry");
        chooseUpdate.add("Semester's Schedule");
        chooseUpdate.add("Create Registration");
        chooseUpdate.add("Update leaders");

        ArrayAdapter ar=new ArrayAdapter(this, android.R.layout.simple_list_item_1, chooseUpdate);
        updatesOn.setAdapter(ar);
        updatesOn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updatesOn.setVisibility(View.INVISIBLE);
                //linearLayout.setVisibility(View.VISIBLE);
                if(position==0){
                    updates();
                    i=1;
                }else if(position==1){
                    i=2;
                    info.setVisibility(View.VISIBLE);
                    chooseMinistry.setVisibility(View.INVISIBLE);
                    sendMinistry.setVisibility(View.INVISIBLE);
                }else if(position==2){
                    programme.setVisibility(View.VISIBLE);
                }else if(position==3){
                    i=4;
                    info.setVisibility(View.VISIBLE);
                    history.setVisibility(View.INVISIBLE);
                    sendInfo.setVisibility(View.INVISIBLE);
                }else if(position==4){
                    i=5;
                    updateSchedule();
                }else if(position==5){
                    createRegistration();

                }else if(position==6){
                    startActivity(new Intent(getApplicationContext(), updateLeaders.class));
                }
            }
        });
        String[] arraySpinner = new String[] {
                "Select Ministry",
                "Music Ministry",
                "Hospitality Ministry",
                "Media Ministry",
                "Lighters Ministry",
                "Evangelism Ministry",
                "Hands Of Compassion Ministry",
                "Ushering Ministry",
                "Sunday School Ministry",
                "Nurturing Ministry",
                "Intercessory Ministry"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseMinistry.setAdapter(adapter);
        chooseMinistry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    assignId=0;
                }else if(position==1){
                    assignId=1;
                }else if(position==2){
                    assignId=2;
                }else if(position==3){
                    assignId=3;
                }else if(position==4){
                    assignId=4;
                }else if(position==5){
                    assignId=5;
                }else if(position==6){
                    assignId=6;
                }else if(position==7){
                    assignId=7;
                }else if(position==8){
                    assignId=8;
                }else if(position==9){
                    assignId=9;
                }else if(position==10){
                    assignId=10;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendMinistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAboutMinistry();
            }
        });
        sendProgramme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programme();
            }
        });


        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChurchInfo();
            }
        });

    }

    public void datePicker(){
        Calendar cal=Calendar.getInstance();
        Date date=cal.getTime();
        SimpleDateFormat format=new SimpleDateFormat("yyyy");
        int year=Integer.valueOf(format.format(date));
        format=new SimpleDateFormat("MM");
        int month=Integer.valueOf(format.format(date));
        format=new SimpleDateFormat("dd");
        int day=Integer.valueOf(format.format(date));

        DatePickerDialog datePickerDialog=new DatePickerDialog(this,
                this, year,
                month,
                day
        );
        datePickerDialog.show();
    }

public void timePicker(){
    TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startTime.setText(hourOfDay+":"+minute);
        }
    }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this));

    timePickerDialog.show();
}


public void updates(){
        setContentView(R.layout.craete_update);
        startDate=findViewById(R.id.dateToStart);
        startTime=findViewById(R.id.timeToStart);
        sendUpdate=findViewById(R.id.sendMessage);
        eventType=findViewById(R.id.event);
        eventDescription=findViewById(R.id.description);
        endDate=findViewById(R.id.dateToEnd);
        back=findViewById(R.id.backToLs);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_l_s);
            }
        });
         String type="churchUpdate";

    startDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker();
            dateType="startDate";
        }
    });
    endDate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            datePicker();
            dateType="endDate";
        }
    });
    startTime.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            timePicker();
        }
    });

    sendUpdate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String event=eventType.getText().toString();
            String start=startDate.getText().toString();
            String time=startTime.getText().toString();
            String end=endDate.getText().toString();
            String description=eventDescription.getText().toString();
            new AsyncTask<Void, Void, String>() {
                protected String doInBackground(Void[] params) {
                    String response = "";
                    try {
                        String[] strings = new String[6];
                        strings[0] = type;
                        strings[1]= event;
                        strings[2]=start;
                        strings[3]=time;
                        strings[4]=end;
                        strings[5]=description;
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
                            startActivity(new Intent(getApplicationContext(), LS.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }.execute();
        }
    });


}


public void UpdateAboutMinistry(){
        final String type="aboutGroup";
        final int groupId=assignId;
        final String vis=vision.getText().toString();
        final String mis=mission.getText().toString();
        final String aboutMinistry=aboutUs.getText().toString();

    new android.os.AsyncTask<Void, Void, String>() {
        protected String doInBackground(Void[] params) {
            String response = "";
            try {
                String[] strings = new String[5];
                strings[0] = type;
                strings[1] = String.valueOf(groupId);
                strings[2] = aboutMinistry;
                strings[3]=vis;
                strings[4]=mis;

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
                //  JSONArray      jsonArray = jsonObject.optJSONArray("data");
                String message = jsonObject.getString("message");
                if (result == true) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),message , Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }.execute();
}
public void programme(){
    final String type="programme";
    final String fell = fellowship.getText().toString();
    final String desc = editProgramme.getText().toString();
    final String day=dayOfTheProgramme.getText().toString();
    final String time = timeToMeet.getText().toString();
    final String Venue=venue.getText().toString();
    new AsyncTask<Void, Void, String>() {
        protected String doInBackground(Void[] params) {
            String response = "";
            try {
                String[] strings = new String[6];
                strings[0] = type;
                strings[1] = fell;
                strings[2] = desc;
                strings[3] = day;
                strings[4] = time;
                strings[5] = Venue;

                 ;
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
            }

        }
    }.execute();
}

    public void updateChurchInfo(){
        final String type="updateAboutChurch";
        final String cVision = vision.getText().toString();
        final String cMission = mission.getText().toString();
        final String cAbout=aboutUs.getText().toString();
        final String cHistory = history.getText().toString();
        new AsyncTask<Void, Void, String>() {
            protected String doInBackground(Void[] params) {
                String response = "";
                try {
                    String[] strings = new String[5];
                    strings[0] = type;
                    strings[1] = cVision;
                    strings[2] = cMission;
                    strings[3] = cAbout;
                    strings[4] = cHistory;

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
                }

            }
        }.execute();
    }

    public void updateSchedule(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to Update Sunday or Tuesday Schedule??");
        final String[]  day= {"SUNDAY SERVICE", "TUESDAY FELLOWSHIP"};
        builder.setItems(day, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        tuesday=1;
                        tpc.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        sunday=1;
                        break;
                }
                updatesemScedule.setVisibility(View.VISIBLE);
                sendSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String type="semesterschedule";
                        final String Sdate=sdate.getText().toString();
                        final String sSpeaker = speaker.getText().toString();
                        final String sProgrammer =programmer.getText().toString();
                        final String sTopic = topic.getText().toString();

                        new android.os.AsyncTask<Void, Void, String>(){
                            protected String doInBackground(Void[] params) {
                                String response="";
                                try {
                                    String strings[]=new String[7];
                                    strings[0]=type;
                                    strings[1]=Sdate;
                                    strings[2]=sSpeaker;
                                    strings[3]=sProgrammer;
                                    strings[4]=sTopic;
                                    strings[5]= String.valueOf(sunday);
                                    strings[6]= String.valueOf(tuesday);

                                    HttpProcesses httpProcesses=new HttpProcesses();
                                    response = httpProcesses.sendRequest(strings);


                                } catch (Exception e) {
                                    response=e.getMessage();
                                }
                                return response;
                            }
                            protected void onPostExecute(String response) {

                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    Boolean result=jsonObject.getBoolean("status");
                                    if(result==true){
                                        Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), LS.class));

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Failed! Something went Wrong,\n Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }

                        }.execute();

                    }
                });
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createRegistration(){
        setContentView(R.layout.create_registration);

        Button send;
        send=findViewById(R.id.sendReg);
        regFor=findViewById(R.id.regFor);
        evendate=findViewById(R.id.date);
        regEnd=findViewById(R.id.regEnd);
        regDesc=findViewById(R.id.regEventDesc);
        back=findViewById(R.id.backToLs);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_l_s);
            }
        });

        evendate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evDate="start";
                datePicker();
            }
        });
        regEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evDate="end";
                datePicker();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... voids) {
                        String response="";
                        String type="eventRegistration";
                        String eveFor=regFor.getText().toString();
                        String eventDate=evendate.getText().toString();
                        String end=regEnd.getText().toString();
                        String desc=regDesc.getText().toString();


                        try {
                            String strings[]=new String[5];
                            strings[0]=type;
                            strings[1]=eveFor;
                            strings[2]=eventDate;
                            strings[3]=end;
                            strings[4]=desc;
                            HttpProcesses httpProcesses=new HttpProcesses();
                            response=httpProcesses.sendRequest(strings);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return response;
                    }

                    @Override
                    protected void onPostExecute(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Boolean status=jsonObject.getBoolean("status");
                            String message=jsonObject.getString("message");
                            if(status==true){
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date=year+"/"+month+"/"+dayOfMonth;

        if(dateType.equals("startDate")){
            startDate.setText(date);
        }else  if(dateType.equals("endDate")){
            endDate.setText(date);
        }else if(dateType.equals("date")){
            sdate.setText(date);
        }else if(evDate.equals("start")){
            evendate.setText(date);
        }else if(evDate.equals("end")){
            regEnd.setText(date);
        }
    }


}
