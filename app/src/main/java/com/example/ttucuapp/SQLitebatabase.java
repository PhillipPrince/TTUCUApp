package com.example.ttucuapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SQLitebatabase extends SQLiteOpenHelper  {
    public static final String dbName = "NamesDB";
    public static final String tableName = "church_users_session";
    public static final String userId = "id";
    public static final String userName = "name";
    public static final String groupId = "GroupId";
    public static final String executive = "Executive";
    public static final String subcom = "subcom";
    public static final String columnStatus = "status";
    public static final String mission = "mission";
    public static final String hike = "hike";
    public static final String project = "project";
    public static final String elders = "eldersCommittee";
    public static final int dbVersion = 20;
    public static final String tableUpdates="Updates";
    public static final String event="Event";
    public static final String description="Description";
    public static final String timeToStart="Time";
    public static final String dateToStart="StartingDate";
    public static final String dateToEnd="EndingDate";
    public static final  String eventId="eventId";

    public static final String schedule="schedule";
    public static final String sid="scheduleId";
    public static final String date="date";
    public static final String speaker="speaker";
    public static final String programmer="programmer";
    public static final String topic="topic";


    public SQLitebatabase(Context context) {
        super(context, dbName, null, dbVersion);
         }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + tableName + "(" + userId + " INTEGER," + userName +
                " VARCHAR,"+groupId+" INTEGER," +executive+" INTEGER,"+subcom+" INTEGER," +mission+" INTEGER,"
                +hike+" INTEGER,"+project+" INTEGER,"+elders+" INTEGER,"+ columnStatus + " TINYINT, logged INTEGER); ";
        String updates="CREATE TABLE " + tableUpdates + "(" + eventId + " INTEGER," + event + " VARCHAR," + description +
                " VARCHAR,"+timeToStart+" TIME," +dateToStart+" DATE,"+dateToEnd+" DATE,"+ columnStatus + " TINYINT, read INTEGER); ";
        String sunday="CREATE TABLE " + schedule + "(" + sid + " INTEGER," + date + " DATE," + speaker +
                " VARCHAR,"+programmer+" VARCHAR," +topic+" VARCHAR,"+dateToEnd+" DATE,"+ columnStatus + " TINYINT, read INTEGER); ";
       db.execSQL(sql);
       db.execSQL(updates);
       db.execSQL(sunday);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+tableName;
        String updates = "DROP TABLE IF EXISTS "+tableUpdates;
        String sunday = "DROP TABLE IF EXISTS "+schedule;
        db.execSQL(sql);
        db.execSQL(updates);
        db.execSQL(sunday);
        onCreate(db);

    }

    public boolean insertData(  int id, String name, int GroupId, int executiveInd, int subcommittee, int missionCommittee, int hikeCommittee, int projectCommittee, int eldersCommittee ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(userId, id);
        contentValues.put(userName, name);
        contentValues.put(groupId, GroupId);
        contentValues.put(executive, executiveInd);
        contentValues.put(subcom, subcommittee);
        contentValues.put(mission,missionCommittee);
        contentValues.put(hike, hikeCommittee);
        contentValues.put(project, projectCommittee);
        contentValues.put(elders, eldersCommittee);
        contentValues.put("logged", 1);
        long result=db.insertOrThrow(tableName, null, contentValues);
        db.close();
        if(result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean insertUpdate(int evId, String activity, String des, String startTime, String startDate,String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(eventId, evId);
        contentValues.put(event, activity);
        contentValues.put(description, des);
        contentValues.put(timeToStart, startTime);
        contentValues.put(dateToStart, startDate);
        contentValues.put(dateToEnd, endDate);
        contentValues.put("read", 1);
        long result=db.insertOrThrow(tableUpdates, null, contentValues);
        db.close();
        if(result==-1){
            return false;
        }else {
            return true;
        }

    }




   public Cursor syncDetails() {
       SQLiteDatabase db=this.getReadableDatabase();
       Cursor cursor=null;
        try {
            String sql= ("SELECT * FROM church_users_session where logged=1");
             cursor= db.rawQuery(sql, null);
            if (cursor.getCount()>0) {
                cursor.moveToFirst();
                UserDetails.INSTANCE.setUserId(cursor.getInt(0));
                UserDetails.INSTANCE.setName(cursor.getString(1));
                UserDetails.INSTANCE.setGroupId(cursor.getInt(2));
                UserDetails.INSTANCE.setExec(cursor.getInt(3));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
       db.close();
        return cursor;
    }
   public  boolean logOut(){
       SQLiteDatabase db = this.getReadableDatabase();
       String sql=("DELETE FROM church_users_session");
       db.execSQL(sql);
       db.close();
       return  true;
   }
   public  List<newNotifications> viewUpdates(){
       SQLiteDatabase db=this.getReadableDatabase();
       String sql="SELECT * FROM "+tableUpdates;

      Cursor cursor=null;
       List<newNotifications> notification=new ArrayList<>();
       try {
           cursor= db.rawQuery(sql, null);
           cursor.moveToFirst();
           while(cursor.moveToNext()){
               newNotifications nots=new newNotifications();
               nots.setEventId(cursor.getInt(0));
               nots.setEvent(cursor.getString(1));
               nots.setDescription(cursor.getString(2));
               nots.setTimeToStart(cursor.getString(3));
               nots.setDateToStart(cursor.getString(4));
               nots.setDateToEnd(cursor.getString(5));
               notification.add(nots);
           }

       } catch (Exception e) {
           e.printStackTrace();
       }
       cursor.close();
       db.close();
      return notification;
   }


    public boolean insertSchedule(int id,String dat, String preac, String prog, String top) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sid, id);
        contentValues.put(date, dat);
        contentValues.put(speaker, preac);
        contentValues.put(programmer, prog);
        contentValues.put(topic, top);

        contentValues.put("read", 1);
        long result=db.insertOrThrow(schedule, null, contentValues);
        db.close();
        if(result==-1){
            return false;
        }else {
            return true;
        }

    }
    public  List<newNotifications> viewSundaySchedule(){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="SELECT * FROM "+schedule;
        Cursor cursor=null;
        List<newNotifications> notification=new ArrayList<>();
        try {
            cursor= db.rawQuery(sql, null);
            cursor.moveToFirst();
            while(cursor.moveToNext()){
                newNotifications nots=new newNotifications();
                nots.setSid(cursor.getInt(0));
                nots.setDate(cursor.getString(1));
                nots.setSpeaker(cursor.getString(2));
                nots.setProgrammer(cursor.getString(3));
                nots.setTopic(cursor.getString(4));

                notification.add(nots);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        db.close();
        return notification;
    }

   public  boolean delete(){
       SQLiteDatabase db = this.getReadableDatabase();
       String sql=("DELETE FROM Updates WHERE EndingDate<datetime('now')");
       db.execSQL(sql);
       db.close();
       return  true;

   }
}