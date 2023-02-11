package com.example.ttucuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class profile extends AppCompatActivity {
    EditText name, uName, uEmail, uPhone, uReg, uCourse;
    TextView profileName, profileUName, profileEmail, profilePhone, profileReg, profileCourse;
    ImageView profilePic;
    public static ListView ministriesActive;
    TextView update;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        name=findViewById(R.id.name);
        uName=findViewById(R.id.uName);
        uEmail=findViewById(R.id.uEmail);
        uPhone=findViewById(R.id.uPhone);
        uReg=findViewById(R.id.uReg);
        uCourse=findViewById(R.id.uCourse);
        profileName=findViewById(R.id.profileName);
        profileUName=findViewById(R.id.profileUserName);
        profileEmail=findViewById(R.id.profileMail);
        profilePhone=findViewById(R.id.profilePhone);
        profileReg=findViewById(R.id.profileReg);
        profileCourse=findViewById(R.id.profileCourse);
        ministriesActive=findViewById(R.id.ministriesServing);
        profilePic=findViewById(R.id.myProfile);

        update=findViewById(R.id.updateProfile);
        userProfile();
    }

    public  void userProfile(){

        final String type="userDetails";
        final int id=UserDetails.INSTANCE.getUserId();
        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {

                    String strings[]=new String[2];
                    strings[0]=type;
                    strings[1]=String.valueOf(id);
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
                    Boolean hasMinistries =jsonObject.getBoolean("ministriesFound");

                    if(result ){
                        final ArrayList<String> ministries = new ArrayList<>();
                        if(hasMinistries) {
                            JSONArray dataArr=jsonObject.getJSONArray("data");
                            JSONObject jsonObject1=null;

                            for (int i = 0; i < dataArr.length(); i++) {
                                jsonObject1 = dataArr.getJSONObject(i);
                                ministries.add(jsonObject1.getString("ministryName"));
                                // List<String> list=ministries;
                                UserDetails.INSTANCE.setMinistries(ministries);
                            }
                        }
                      /*  if(jsonObject1==null){
                            return;
                        }*/
                        if(!ministries.isEmpty()){
                            final ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, ministries);
                            ministriesActive.setAdapter(adapter);
                        }else {
                            ministries.add("You have Not Joined Any Ministry");
                            final ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, ministries);
                            ministriesActive.setAdapter(adapter);
                        }


                        String myName=jsonObject.getString("name");
                        String myUserName=jsonObject.getString("userName");
                        String myEmail=jsonObject.getString("email");
                        String myPhone=jsonObject.getString("phone");
                        String myReg=jsonObject.getString("regNo");
                        String myCourse=jsonObject.getString("Course");
                        String myprofile=jsonObject.getString("image");

                        byte [] encodeByte= Base64.decode(myprofile,Base64.DEFAULT);

                        InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                        Bitmap profilePic  = BitmapFactory.decodeStream(inputStream);
                         //profilePic.setImageBitmap(profilePic);




                        profileName.setText(myName);
                        profileUName.setText(myUserName);
                        profileEmail.setText(myEmail);
                        profilePhone.setText(myPhone);
                        profileReg.setText(myReg);
                        profileCourse.setText(myCourse);
                    }
                } catch (JSONException e) {
                    setContentView(R.layout.error_layout);
                    Button retry=findViewById(R.id.retry);
                    retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getApplicationContext(), profile.class));
                        }
                    });

                    e.printStackTrace();
                }
            }
        }.execute();

    }

    public void getImage(){

    }
    public void updateProfile(View view) {
        final String type="updateUserProfile";
        final String myNam=name.getText().toString();
        final String userName = uName.getText().toString();
        final String email_address =uEmail.getText().toString();
        final String phone = uPhone.getText().toString();
        final String regNo = uReg.getText().toString();
        final String course = uCourse.getText().toString();
        final int id=UserDetails.INSTANCE.getUserId();

       // Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    String strings[]=new String[9];
                    strings[0]=type;
                    strings[1]=myNam;
                    strings[2]=userName;
                    strings[3]=email_address;
                    strings[4]=phone;
                    strings[5]=regNo;
                    strings[6]=course;
                    strings[7]=String.valueOf(id);
                    strings[8]=encodedImage;

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
                        Toast.makeText(getApplicationContext(), "User Details Updated", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
    public void choose(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePic.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}