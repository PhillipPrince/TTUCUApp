package com.example.ttucuapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class notifications extends AppCompatActivity {
    TextView textView, textView1,textView2, textView3, textView4, updateType;
    SQLitebatabase sqLitebatabase;
    ScrollView scrollView1;
    LinearLayout linearLayout;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        scrollView1=findViewById(R.id.update);
         linearLayout=findViewById(R.id.events);
         listView=findViewById(R.id.eventsList);
        updateType=findViewById(R.id.updateType);
         textView=findViewById(R.id.newEvent);
         textView1=findViewById(R.id.startsOn);
        textView2=findViewById(R.id.at);
        textView3=findViewById(R.id.endsOn);
         textView4=findViewById(R.id.eventDetails);
        sqLitebatabase=new SQLitebatabase(getApplicationContext());

        getEvents();
    }

    public void getEvents(){
        final List<newNotifications> list=sqLitebatabase.viewUpdates();
        final ArrayList<String> arrayList=new ArrayList();
        linearLayout.setVisibility(View.VISIBLE);
        updateType.setText("Upcoming Events");
        for(int i=0; i<list.size(); i++){
            arrayList.add(list.get(i).getEvent());
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                linearLayout.setVisibility(View.INVISIBLE);
                scrollView1.setVisibility(View.VISIBLE);
                textView.setText(list.get(position).getEvent());
                textView1.setText(list.get(position).getDateToStart());
                textView2.setText(list.get(position).getTimeToStart());
                textView3.setText(list.get(position).getDateToEnd());
                textView4.setText(list.get(position).getDescription());
            }
        });
    }
}