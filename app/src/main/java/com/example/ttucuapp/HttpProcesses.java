package com.example.ttucuapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HttpProcesses {
    public String sendRequest(String strings[]){
        String type = strings[0];

     // String ttucu="http://20.235.97.83/taita_taveta_university_cu/ttucu.php";
      //String ttucu="http://ttucu.c1.biz/church/TTUCU.php";
        //String ttucu="http://localhost/taita_taveta_university_christian_union/ttucu.php";
        try {

            URL url=null;
            String insert_data="";
            if (type.equals("reg")) {
                url = new URL(constants.register);
                String name = strings[1];
                String userName = strings[2];
                String address = strings[3];
                String myGender = strings[4];
                String phoneNo = strings[5];
                String course=strings[6];
                String reg = strings[7];
                String password = strings[8];
               String image=strings[9];
                insert_data=URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&&" + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
                        + "&&" + URLEncoder.encode("email_address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
                        + "&&" + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(myGender, "UTF-8")
                        + "&&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phoneNo, "UTF-8")
                        + "&&" + URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(course, "UTF-8")
                        + "&&" + URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(reg, "UTF-8")
                        + "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8")
                        + "&&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
            }  else if (type.equals("login")) {
                url = new URL(constants.login);
                String user_name = strings[1];
                String password = strings[2];
                insert_data =URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8")
                        + "&&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
            } else if (type.equals("joinMinistry")) {
                url = new URL(constants.joinMinistry);
                String myid = String.valueOf(UserDetails.INSTANCE.getUserId());
                int group_id = Integer.parseInt(strings[1]);
                String myGroup = Integer.toString(group_id);
                insert_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(myid, "UTF-8")
                        + "&&" + URLEncoder.encode("group_id", "UTF-8") + "=" + URLEncoder.encode(myGroup, "UTF-8");
            }else if(type.equals("ministryMembers")){
                url=new URL(constants.ministryMembers);
                int group_id=Integer.parseInt(strings[1]);
                insert_data =URLEncoder.encode("group_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(group_id), "UTF-8");

            }else if(type.equals("leadership")){
                url=new URL(constants.leadership);
                int exec=Integer.parseInt(strings[1]);
                int subcom=Integer.parseInt(strings[2]);
                int mission=Integer.parseInt(strings[3]);
                int hike=Integer.parseInt(strings[4]);
                int project=Integer.parseInt(strings[5]);
                int elders=Integer.parseInt(strings[6]);
                insert_data = URLEncoder.encode("exec", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(exec), "UTF-8")
                        + "&&" + URLEncoder.encode("subcom", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(subcom), "UTF-8")
                        + "&&" + URLEncoder.encode("mission", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(mission), "UTF-8")
                        + "&&" + URLEncoder.encode("hike", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(hike), "UTF-8")
                        + "&&" + URLEncoder.encode("project", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(project), "UTF-8")
                        + "&&" + URLEncoder.encode("elders", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(elders), "UTF-8");
            }else if (type.equals("churchUpdate")) {
                url = new URL(constants.churchUpdate);
                String event = strings[1];
                String startDate = strings[2];
                String startTime=strings[3];
                String expiryDate = strings[4];
                String description=strings[5];
                insert_data = URLEncoder.encode("sendTo", "UTF-8") + "=" + URLEncoder.encode("church", "UTF-8")
                        + "&&" + URLEncoder.encode("event", "UTF-8") + "=" + URLEncoder.encode(event, "UTF-8")
                        + "&&" + URLEncoder.encode("StartDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")
                        + "&&" + URLEncoder.encode("startTime", "UTF-8") + "=" + URLEncoder.encode(startTime, "UTF-8")
                        + "&&" + URLEncoder.encode("expiryDate", "UTF-8") + "=" + URLEncoder.encode(expiryDate, "UTF-8")
                        + "&&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
            } else if (type.equals("ministryUpdates")) {
                url = new URL(constants.ministryUpdates);
                int ministryId= Integer.parseInt(strings[1]);
                String event = strings[2];
                String startDate = strings[3];
                String startTime=strings[4];
                String expiryDate = strings[5];
                String description=strings[6];
                insert_data = URLEncoder.encode("sendTo", "UTF-8") + "=" + URLEncoder.encode("ministry", "UTF-8")
                        + "&&" + URLEncoder.encode("group_id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(ministryId), "UTF-8")
                        + "&&" + URLEncoder.encode("event", "UTF-8") + "=" + URLEncoder.encode(event, "UTF-8")
                        + "&&" + URLEncoder.encode("StartDate", "UTF-8") + "=" + URLEncoder.encode(startDate, "UTF-8")
                        + "&&" + URLEncoder.encode("startTime", "UTF-8") + "=" + URLEncoder.encode(startTime, "UTF-8")
                        + "&&" + URLEncoder.encode("expiryDate", "UTF-8") + "=" + URLEncoder.encode(expiryDate, "UTF-8")
                        + "&&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
            }else if(type.equals("aboutGroup")) {
                url = new URL(constants.aboutGroup);
                int groupId= Integer.parseInt(strings[1]);
                String aboutMinistry = strings[2];
                String mvision=strings[3];
                String mmission=strings[4];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("aboutGroup", "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(groupId), "UTF-8")
                        + "&&" + URLEncoder.encode("vision", "UTF-8") + "=" + URLEncoder.encode(mvision, "UTF-8")
                        + "&&" + URLEncoder.encode("mission", "UTF-8") + "=" + URLEncoder.encode(mmission, "UTF-8")
                        + "&&" + URLEncoder.encode("aboutMinistry", "UTF-8") + "=" + URLEncoder.encode(aboutMinistry, "UTF-8");
            }else if(type.equals("groupInformation")) {
                url = new URL(constants.groupInformation);
                int group_id = Integer.parseInt(strings[1]);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("groupInformation", "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(group_id), "UTF-8");
            }else if (type.equals("programme")) {
                url = new URL(constants.programme);
                String fell = strings[1];
                String desc = strings[2];
                String day=strings[3];
                String time = strings[4];
                String Venue=strings[5];
                insert_data = URLEncoder.encode("fellowship", "UTF-8") + "=" + URLEncoder.encode(fell, "UTF-8")
                        + "&&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(desc, "UTF-8")
                        + "&&" + URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8")
                        + "&&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8")
                        + "&&" + URLEncoder.encode("Venue", "UTF-8") + "=" + URLEncoder.encode(Venue, "UTF-8");
            } else if(type.equals("churchInfo")) {
                url = new URL(constants.churchInfo);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("churchInfo", "UTF-8");
            }else if(type.equals("churchProgramme")) {
                url = new URL(constants.churchProgramme);
                int  id= Integer.parseInt(strings[1]);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("churchProgramme", "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
            }else if(type.equals("cUpdates")) {
                url = new URL(constants.cUpdates);
                int userId= Integer.parseInt(strings[1]);
                insert_data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(userId), "UTF-8");
            }else if(type.equals("sunday")) {
                url = new URL(constants.sunday);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("schedule", "UTF-8")
                        + "&&" + URLEncoder.encode("selected", "UTF-8") + "=" + URLEncoder.encode("sunday", "UTF-8");
            }else if(type.equals("tuesday")) {
                url = new URL(constants.tuesday);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("schedule", "UTF-8")
                        + "&&" + URLEncoder.encode("selected", "UTF-8") + "=" + URLEncoder.encode("tuesday", "UTF-8");
            }else if(type.equals("userDetails")) {
                url = new URL(constants.userDetails);
                int  id= Integer.parseInt(strings[1]);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("userDetails", "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8");
            }else if (type.equals("updateAboutChurch")) {
                url = new URL(constants.updateAboutChurch);
                String vision = strings[1];
                String mission = strings[2];
                String aboutUs=strings[3];
                String history = strings[4];
                insert_data = URLEncoder.encode("vision", "UTF-8") + "=" + URLEncoder.encode(vision, "UTF-8")
                        + "&&" + URLEncoder.encode("mission", "UTF-8") + "=" + URLEncoder.encode(mission, "UTF-8")
                        + "&&" + URLEncoder.encode("aboutUs", "UTF-8") + "=" + URLEncoder.encode(aboutUs, "UTF-8")
                        + "&&" + URLEncoder.encode("history", "UTF-8") + "=" + URLEncoder.encode(history, "UTF-8");
            }else if (type.equals("updateUserProfile")) {
                url = new URL(constants.updateUserProfile);
                String name = strings[1];
                String userName = strings[2];
                String email_address = strings[3];
                String phone = strings[4];
                String regNo = strings[5];
                String course = strings[6];
                int id= Integer.parseInt(strings[7]);
                String image=strings[8];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("updateProfile", "UTF-8")
                        + "&&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")
                        + "&&" + URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8")
                        + "&&" + URLEncoder.encode("email_address", "UTF-8") + "=" + URLEncoder.encode(email_address, "UTF-8")
                        + "&&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
                        + "&&" + URLEncoder.encode("course", "UTF-8") + "=" + URLEncoder.encode(course, "UTF-8")
                        + "&&" + URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")
                        + "&&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(id), "UTF-8")
                        + "&&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(image, "UTF-8");
            }else if(type.equals("execRole")) {
                url = new URL(constants.execRole);
                String name=strings[1];
                name=name.substring(0,name.indexOf("\n"));
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("execRole", "UTF-8")
                        + "&&" + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            }else if (type.equals("semesterschedule")) {
                url = new URL(constants.semesterschedule);
                String date = strings[1];
                String speaker = strings[2];
                String progammer = strings[3];
                String topic = strings[4];
                int sunday = Integer.parseInt(strings[5]);
                int tuesday = Integer.parseInt(strings[6]);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("semesterschedule", "UTF-8")
                        + "&&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8")
                        + "&&" + URLEncoder.encode("speaker", "UTF-8") + "=" + URLEncoder.encode(speaker, "UTF-8")
                        + "&&" + URLEncoder.encode("progammer", "UTF-8") + "=" + URLEncoder.encode(progammer, "UTF-8")
                        + "&&" + URLEncoder.encode("topic", "UTF-8") + "=" + URLEncoder.encode(topic, "UTF-8")
                        + "&&" + URLEncoder.encode("sunday", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(sunday), "UTF-8")
                        + "&&" + URLEncoder.encode("tuesday", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(tuesday), "UTF-8");
            }else if (type.equals("eventRegistration")) {
                url = new URL(constants.eventRegistration);
                String event = strings[1];
                String eventDate = strings[2];
                String regEndDate = strings[3];
                String description=strings[4];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("eventRegistration", "UTF-8")
                        + "&&" + URLEncoder.encode("event_name", "UTF-8") + "=" + URLEncoder.encode(event, "UTF-8")
                        + "&&" + URLEncoder.encode("start_date", "UTF-8") + "=" + URLEncoder.encode(eventDate, "UTF-8")
                        + "&&" + URLEncoder.encode("reg_end_date", "UTF-8") + "=" + URLEncoder.encode(regEndDate, "UTF-8")
                        + "&&" + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8");
            }else if (type.equals("registerForEvent")) {
                url = new URL(constants.registerForEvent);
                String eventId = strings[1];
                String userId = strings[2];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("registerForEvent", "UTF-8")
                        + "&&" + URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(eventId, "UTF-8")
                        + "&&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");

            }else if(type.equals("newEventForRegister")){
                url = new URL(constants.newEventForRegister);
                String eventId = strings[1];
                String userId = strings[2];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("newEventForRegister", "UTF-8")
                        + "&&" + URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(eventId, "UTF-8")
                        + "&&" + URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8");;
            }else if(type.equals("registeredForEvent")){
                url=new URL(constants.registeredForEvent);
                String eventId = strings[1];
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("registeredForEvent", "UTF-8")
                        + "&&" + URLEncoder.encode("event_id", "UTF-8") + "=" + URLEncoder.encode(eventId, "UTF-8");

            }else if(type.equals("churchMembers")){
                url=new URL(constants.churchMembers);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("churchMembers", "UTF-8");

            }
           /* else if(type.equals("connection")) {
                url = new URL(ttucu);
                insert_data = URLEncoder.encode("operation", "UTF-8") + "=" + URLEncoder.encode("connection", "UTF-8");
            }*/
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(insert_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String result = "";
            String reg_line = "";

            StringBuilder stringBuilder = new StringBuilder();
            while ((reg_line = bufferedReader.readLine()) != null) {
                stringBuilder.append(reg_line).append("\n");
            }
            result = stringBuilder.toString();
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
