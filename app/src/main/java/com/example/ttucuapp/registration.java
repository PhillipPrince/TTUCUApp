package com.example.ttucuapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.net.Uri;
import android.provider.MediaStore;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.util.Map;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import java.io.UnsupportedEncodingException;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import javax.net.ssl.HttpsURLConnection;

public class registration extends AppCompatActivity {


    Bitmap bitmap;
    Button submit_Button;
    EditText name, userName, phone,  address, reg_no, password, confirm_password,  code;
    TextView login_instead, nameError,userNameError ,genderError ,emailError,phoneError ,regError,courseError, passError;
    ImageView profile;
    Spinner gender, course;
    String[] myGender;
    String[] courses;
    ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = findViewById(R.id.name);
        userName = findViewById(R.id.userName);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        reg_no = findViewById(R.id.regno);
        course = findViewById(R.id.course);
        submit_Button = findViewById(R.id.submit);
        login_instead = findViewById(R.id.loginInstead);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirmPassword);
        nameError = findViewById(R.id.nameError);
        userNameError = findViewById(R.id.userNameError);
        genderError = findViewById(R.id.genderError);
        emailError = findViewById(R.id.emailError);
        phoneError = findViewById(R.id.phoneError);
        regError = findViewById(R.id.regError);
        courseError=findViewById(R.id.courseError);
        profile=findViewById(R.id.profile);
        passError=findViewById(R.id.passError);
        code=findViewById(R.id.code);
         pb =  findViewById(R.id.progressBar);
         pb.setVisibility(View.INVISIBLE);






        submit_Button.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 register();
                                             }
                                         });

        login_instead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        myGender = new String[] {
                "Select Gender",
                "Male",
                "Female",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myGender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);


         courses = new String[] {
                "Select Course",
                "Bachelor of Business and Information Technology",
                "Information Technology",
                "Bachelor of Commerce",
                "BPS",
                 "Economics",
                 "Industrial Chemistry",
                 "Analytical Chemistry",
                 "Geo Informatics",
                 "Education",
                "BSC Mathematics and Computer Science",
                "Statistics",
                "Mining",

        };
        ArrayAdapter<String> courseadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course.setAdapter(courseadapter);

    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

    }

    public void register(){
        final String myName = name.getText().toString();
        final String uerName = userName.getText().toString();
        final String email = address.getText().toString();
        final String phoneNo =code.getText().toString()+phone.getText().toString();
        final String reg = reg_no.getText().toString();
        final String pass = password.getText().toString();
        final String confirmPass=confirm_password.getText().toString();
        final String type = "reg";



        if (name.getText().toString().equals(null)) {
            nameError.setText("Enter your name");
        } else if (userName.getText().toString().equals(null)) {
            userNameError.setText("Enter your Username");
        } else if (address.getText().toString().equals(null)) {
            emailError.setText("Enter your email Address");
        } else if (phone.getText().toString().equals(null)) {
            phoneError.setText("Enter your phone No");
        } else if (reg_no.getText().toString().equals(null)) {
            regError.setText("Enter your Registration No");
        } else if (password.getText().toString().equals(null)) {
            passError.setText("Enter your password");
        }else if(pass.length()<8){
            passError.setText("Enter a stronger Password more than 8 Characters");
        }else if(!pass.equals(confirmPass)){
            passError.setText("The Password does not Match");
        }else if(!email.contains("@")||!email.contains(".com")){
            emailError.setText("Enter a valid email ");
        }else  if(course.getSelectedItem().toString().equals("Select Course")){
            courseError.setText("Select Course");
        }else if(gender.getSelectedItem().toString().equals("Select Gender")){
            courseError.setText("Select Gender");
        } else{
            final String selectedGender=gender.getSelectedItem().toString();
            final String selectedCourse=course.getSelectedItem().toString();
            new android.os.AsyncTask<Void, Void, String>() {
                protected String doInBackground(Void[] params) {
                    String response = "";
                    try {

                        //image to string
                      /*  ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        final String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        if(encodedImage.equals(null)){
                            nameError.setText("Select profile Picture for easy identity");
                        }*/

                        String[] strings = new String[10];
                        strings[0] = type;
                        strings[1] = myName;
                        strings[2] = uerName;
                        strings[3] = email;
                        strings[4] = selectedGender;
                        strings[5] = phoneNo;
                        strings[6]= selectedCourse;
                        strings[7] = reg;
                        strings[8] = pass;
                        strings[9]="";
                        HttpProcesses httpProcesses = new HttpProcesses();
                        response = httpProcesses.sendRequest(strings);
                    } catch (Exception e) {
                        response = e.getMessage();
                    }
                    return response;
                }
                protected void onPostExecute(String response) {
                  //  profile.setImageResource(android.R.color.transparent);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Boolean result = jsonObject.getBoolean("status");
                        String message = jsonObject.getString("message");
                        if (result == true) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startPb(intent);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(),message , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                       setContentView(R.layout.error_layout);
                      Button retry=findViewById(R.id.retry);
                      retry.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              startActivity(new Intent(getApplicationContext(), registration.class));
                          }
                      });

                    }
                }
            }.execute();
        }
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void startPb(Intent intent) {
        pb.setVisibility(View.VISIBLE);


        pb.setIndeterminate(false);
        pb.setProgress(0);
        new Thread(new Runnable() {

            public void run() {
                for (int i = 0; i <= 100; i++)
                {
                    // Thread.sleep(100);
                    pb.setProgress(i);
                    System.out.println(i);

                }
                pb.post(new Runnable() {
                    public void run() {
                        pb.incrementProgressBy(1);
                        startActivity(intent);


                    }
                });

            }
        }).start();
    }



}
