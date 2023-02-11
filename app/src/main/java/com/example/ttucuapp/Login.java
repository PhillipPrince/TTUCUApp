package com.example.ttucuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText login_user_name, login_password;
    TextView error;
    SQLitebatabase sqLitebatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        login_user_name=findViewById(R.id.login_userName);
        login_password=findViewById(R.id.login_password);
        error=findViewById(R.id.errorDetails);
       // linearLayout=findViewById(R.id.loginLayout);



        sqLitebatabase=new SQLitebatabase(getApplicationContext());

        Button login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName=login_user_name.getText().toString();
                final String password=login_password.getText().toString();
                final String type="login";
                if(userName.matches("") || password.matches("")){
                    login_user_name.setText("");
                    login_user_name.setHint("Enter UserName");
                    login_password.setText("");
                    login_password.setHint("Enter Password");


                    Toast.makeText(getApplicationContext(),"Enter username and Password.", Toast.LENGTH_LONG);
                }else {
                   new android.os.AsyncTask<Void, Void, String>(){
                        protected String doInBackground(Void[] params) {
                            String response="";
                            try {
                                String strings[]=new String[3];
                                strings[0]=type;
                                strings[1]=userName;
                                strings[2]=password;
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
                                    String message=jsonObject.getString("message");
                                    int  userId=jsonObject.getInt("id");
                                    int group_role=jsonObject.getInt("group_role_leader");
                                    int executive=jsonObject.getInt("Executive");
                                    int subcom=jsonObject.getInt("Subcom");
                                    int mission=jsonObject.getInt("Missions_committee");
                                    int hike=jsonObject.getInt("hike_Committee");
                                    int project=jsonObject.getInt("project_committee");
                                    int elders=jsonObject.getInt("elders");
                                    UserDetails.INSTANCE.setUserId(userId);
                                    UserDetails.INSTANCE.setGroupId(group_role);
                                    UserDetails.INSTANCE.setExec(executive);
                                    UserDetails.INSTANCE.setSubcom(subcom);
                                    UserDetails.INSTANCE.setMission(mission);
                                    UserDetails.INSTANCE.setHike(hike);
                                    UserDetails.INSTANCE.setProject(project);
                                    UserDetails.INSTANCE.setElders(elders);
                                    UserDetails.INSTANCE.setPhone(jsonObject.getInt("Phone"));
                                    UserDetails.INSTANCE.setName(jsonObject.getString("Name"));
                                    sqLitebatabase.insertData(userId,userName,group_role,executive,subcom, mission, hike, project, elders);


                                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                    Toast.makeText(getApplicationContext(), message+"\n \tWelcome", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    error.setText("Invalid UserName Or Password");
                                }
                            } catch (JSONException e) {
                                setContentView(R.layout.error_layout);
                                Button retry=findViewById(R.id.retry);
                                retry.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    }
                                });
                                e.printStackTrace();
                            }


                        }

                    }.execute();

                }

            }
        });

        TextView signUp=findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), registration.class);
                startActivity(intent);
            }
        });

    }

}