package com.example.ttucuapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class updateLeaders extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_leaders);
        listView=findViewById(R.id.churchMembers);
        allMembers();
    }

    public void allMembers(){
        new android.os.AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                try {
                    final String type="churchMembers";
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
                    JSONArray jsonArray = jsonObject.optJSONArray("data");
                    if(result==true){
                        JSONObject jsonObject1=null;
                        final ArrayList<String> members = new ArrayList<>();
                        members.add("CHURCH MEMBERS");
                        for(int i=0;i<jsonArray.length();i++){
                            jsonObject1 =jsonArray.getJSONObject(i);
                            members.add(jsonObject1.getString("name")+"\n"+jsonObject1.getString("phone"));
                        }
                        if(jsonObject1==null){
                            return;
                        }
                        final ArrayAdapter grpAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, members);
                        listView.setAdapter(grpAdapter);
                        makeLeader(members);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }

    public Boolean makeLeader(ArrayList<String> arrayList){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               AlertDialog.Builder builder=new AlertDialog.Builder(getApplicationContext());
               builder.setTitle("Choose Role");
               String[] leadershipRoles={"Executive Committee",
                "Sub Committee",
                "Missions Committee",
                "Hike Committee",
                "Project Committee",
                "Elders Committee" };
               builder.setItems(leadershipRoles, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });
               AlertDialog dialog=builder.create();
               dialog.show();

            }
        });
        return false;

    }

}