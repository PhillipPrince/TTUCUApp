package com.example.ttucuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class leadership extends AppCompatActivity {
    ListView list, leaders_list;

    TextView textView, leadersRole;
    ScrollView scrollView;
    ImageView leadersImage;
    Button back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadership);
        list=findViewById(R.id.list);



        back=findViewById(R.id.backToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        ArrayList<String> arrayList=new ArrayList<>();
       arrayList.add("Executive Committee");
       arrayList.add("Sub Committee");
       arrayList.add("Missions Committee");
       arrayList.add("Hike Committee");
       arrayList.add("Project Committee");
       arrayList.add("Elders Committee");
       ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
       list.setAdapter(arrayAdapter);

       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentView(R.layout.level_leaderships);
                leaders_list=findViewById(R.id.leaders);
                back=findViewById(R.id.backToLeaders);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.activity_leadership);
                    }
                });
                if(position==0){
                    leaders("Executive Committee","1","0","0","0","0","0");

                }else if(position==1){
                   leaders("Sub Committee", "0","1","0","0","0","0");

                }else if(position==2){
                   leaders("Missions Committee", "0","0","1","0","0","0");

                }else if(position==3){
                    leaders("Hike Committee","0","0","0","1","0","0");

                }else if(position==4){
                   leaders("Project Committee","0","0","0","0","1","0");

                }else if(position==5){
                    leaders("Elders Committee","0","0","0","0","0","1");

                }
            }
        });
     }
     public void leaders(final String title, final String exec, final String subcom, final String mission, final String hike, final String project, final String elders){
         textView=findViewById(R.id.heading);
         textView.setText(title);
         new AsyncTask<Void, Void, String>(){
             protected String doInBackground(Void[] params) {
                 String response="";
                 try {
                     final String type="leadership";
                     String strings[]=new String[7];
                     strings[0]=type;
                     strings[1]=exec;
                     strings[2]=subcom;
                     strings[3]=mission;
                     strings[4]=hike;
                     strings[5]=project;
                     strings[6]=elders;
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
                     JSONArray jsonArray = jsonObject.optJSONArray("data");
                     if(result==true){
                         JSONObject jsonObject1=null;
                         final ArrayList<String> arrayList = new ArrayList<>();
                         for(int i=0;i<jsonArray.length();i++){
                                // UserDetails.INSTANCE.getUserId();
                                 jsonObject1 = jsonArray.getJSONObject(i);
                                 arrayList.add(jsonObject1.getString("name")+"\n"+jsonObject1.getString("phone"));
                         }
                         if(jsonObject1==null){
                             return;
                         }
                         final ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, arrayList);
                         leaders_list.setAdapter(arrayAdapter);
                         if(title.equals("Executive Committee")){
                             leaders_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                     setContentView(R.layout.leaders_details);
                                     leadersRole=findViewById(R.id.leadersRole);
                                     leadersImage=findViewById(R.id.leadersImage);
                                     new android.os.AsyncTask<Void, Void, String>(){
                                         protected String doInBackground(Void[] params) {
                                             String response="";
                                             try {
                                                 final String type="execRole";
                                                 String strings[]=new String[2];
                                                 strings[0]=type;
                                                 strings[1]=arrayList.get(position);
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
                                                     String lRole=data.getString("role");
                                                     String picture=data.getString("image");

                                                     byte [] encodeByte= Base64.decode(picture,Base64.DEFAULT);

                                                     InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                                                     Bitmap lImage  = BitmapFactory.decodeStream(inputStream);
                                                     leadersImage.setImageBitmap(lImage);

                                                     leadersRole.setText(lRole);

                                                 }
                                             } catch (JSONException e) {
                                                 e.printStackTrace();
                                             }
                                         }

                                     }.execute();
                                 }
                             });
                         }
                     }

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

         }.execute();
     }
}
