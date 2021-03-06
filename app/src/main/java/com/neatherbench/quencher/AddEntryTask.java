package com.neatherbench.quencher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import libs.JSONParser;

class AddEntryTask extends AsyncTask<String, String, String> {

    private String TAG = "LogDebug";
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static String server_address;
    private static List<NameValuePair> new_entry_data = null;

    private static final String TAG_SUCCESS = "success";

    AddEntryTask(String server_address, List<NameValuePair> new_entry_data, Context context) {
        AddEntryTask.server_address = server_address;
        AddEntryTask.new_entry_data = new_entry_data;
        AddEntryTask.context = context;
    }



        @Override
        protected String doInBackground(String[] args){
            JSONParser jsonParser = new JSONParser(context);
            JSONObject json = jsonParser.makeHttpRequest(server_address, "GET", new_entry_data);
            try {
                Log.d(TAG, "JSON response: " + json.toString());
                try {
                    int success = json.getInt(TAG_SUCCESS);
                    return String.valueOf(success);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return e.toString();
                }
            }catch (NullPointerException e)
            {

                return e.getMessage();
            }

        }
    @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "New Entry created with code: " + result);
        }

    }