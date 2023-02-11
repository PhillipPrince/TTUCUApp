package com.example.ttucuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Schedules extends AppCompatActivity {
    ScrollView scrollView1;
    TextView textView, textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedules);
        scrollView1=findViewById(R.id.schedule);
        textView=findViewById(R.id.day);
        textView1=findViewById(R.id.daysAndschedule);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("These are Our Fellowships.....");
        final String[]  day= {"SUNDAY SERVICE", "TUESDAY FELLOWSHIP"};
        builder.setItems(day, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:

                        schedule("sunday", "Sunday Schedule");
                        break;
                    case 1:
                        schedule("tuesday","Tuesday Fellowships");
                        break;
                }
                // scrollView1.setVisibility(View.VISIBLE);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void schedule(final String type, final String heading){


        textView.setText(heading);


        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    String strings[]=new String[1];
                    strings[0]= type;
                    HttpProcesses httpProcesses=new HttpProcesses();
                    response = httpProcesses.sendRequest(strings);
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String response){
                //do something with response
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Boolean result=jsonObject.getBoolean("status");
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    String message=jsonObject.getString("message");
                    if(result==true){
                        JSONObject jsonObject1=null;

                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject1 =jsonArray.getJSONObject(i);

                            String date=jsonObject1.getString("sdate");
                            String speaker=jsonObject1.getString("speaker");
                            String programmer=jsonObject1.getString("progammer");

                            if(heading.equals("Sunday Schedule")){
                                textView1.setText("Date\t\t:"+date+"\nSpeaker\t\t:"+speaker+"\nProgrammer\t\t:"+programmer);
                            }else if(heading.equals("Tuesday Fellowships")){
                                String topic=jsonObject1.getString("topic");
                                textView1.setText("\n\nDate\t\t:"+date+"\nSpeaker\t\t:"+speaker+"\nProgrammer\t\t:"+programmer+"\nTopic\t:"+topic);

                            }

                        }
                        if(jsonObject1==null){
                            return;
                        }

                    }else {
                        textView1.setText(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }
}