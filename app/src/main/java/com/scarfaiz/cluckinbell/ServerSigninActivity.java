package com.scarfaiz.cluckinbell;

/**
 * Created by USER on 17.11.2017.
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class ServerSigninActivity  extends AsyncTask<String, Void, String>{
    String TAG = "LogDebug";
    //flag 0 means get and 1 means post.(By default it is get.)
    /*public ServerSigninActivity(Context context,TextView statusField,TextView roleField,int flag) {
        this.context = context;
        this.statusField = statusField;
        this.roleField = roleField;
        byGetOrPost = flag;
    }*/

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
                    try{
                String username = (String)arg0[0];
                String password = (String)arg0[1];

                String link="http://178.162.41.115/db_connect.php";
                String data  = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            } catch(Exception e){
                        Log.d(TAG, "An error was accured during connection to the server::" + e.getMessage());
                return new String("Exception: " + e.getMessage());
            }
        }

    @Override
    protected void onPostExecute(String result){
        Log.d(TAG, "Connection resolved with result:" + result);
    }
}