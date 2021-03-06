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

class AddCommentTask extends AsyncTask<String, String, String> {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private String TAG = "LogDebug";

    private static String server_address;
    private static List<NameValuePair> new_comment_data = null;
    private static final String TAG_SUCCESS = "success";

    AddCommentTask(String server_address, List<NameValuePair> new_comment_data, Context context) {
        AddCommentTask.server_address = server_address;
        AddCommentTask.new_comment_data = new_comment_data;
        AddCommentTask.context = context;
    }



    @Override
    protected String doInBackground(String[] args){
        JSONParser jsonParser = new JSONParser(context);
        JSONObject json = jsonParser.makeHttpRequest(server_address, "GET", new_comment_data);
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
        Log.d(TAG, "New comments created with code: " + result);
    }

}