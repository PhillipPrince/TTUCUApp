package com.example.ttucuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class aboutMinistry extends AppCompatActivity {
    TextView vision, mission, aboutG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ministry);

        vision=findViewById(R.id.vis);
        mission=findViewById(R.id.mis);
        aboutG=findViewById(R.id.ab);
        TextView textView=findViewById(R.id.hist);
        textView.setVisibility(View.INVISIBLE);
    }
    public void aboutMinistry(final String ministryId){
        //setContentView(R.layout.church_info);


        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    final String type="groupInformation";
                    String strings[]=new String[2];
                    strings[0]=type;
                    strings[1]=ministryId;
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
                        String vis=data.getString("vision");
                        String mis=data.getString("mission");
                        String about=data.getString("aboutMinistry");


                        mission.setText(vis);
                        vision.setText(mis);
                        aboutG.setText(about);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();

    }
}