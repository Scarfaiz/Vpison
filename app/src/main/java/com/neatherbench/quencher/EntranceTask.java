package com.neatherbench.quencher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import libs.JSONParser;

class EntranceTask extends AsyncTask<String, List<String>, List<String>> {

    private String TAG = "LogDebug";
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static String server_address;
    private static List<NameValuePair> username_data = null;
    private AsyncResponse delegate = null;

    private static final String TAG_SUCCESS = "success";

    EntranceTask(String server_address, List<NameValuePair> username_data, Context context, AsyncResponse delegate) {
        EntranceTask.server_address = server_address;
        EntranceTask.username_data = username_data;
        EntranceTask.context = context;
        this.delegate = delegate;
    }



    @Override
    protected List<String> doInBackground(String[] args){
        JSONParser jsonParser = new JSONParser(context);
        JSONObject json = jsonParser.makeHttpRequest(server_address, "GET", username_data);
        List<String> result;
        result = new ArrayList<>();
        try {
            Log.d(TAG, "JSON response: " + json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                result.add(0, String.valueOf(success));
            } catch (JSONException e) {
                e.printStackTrace();
                result.add(0, e.toString());
            }
        }catch (NullPointerException e)
        {
            result.add(0, e.toString());
        }
        try {
            Log.d(TAG, "JSON response: " + json.toString());
            try {
                result.add(1, json.getString("trial_date"));
            } catch (JSONException e) {
                e.printStackTrace();
                result.add(1, e.toString());
            }
        }catch (NullPointerException e)
        {
            result.add(1, e.toString());
        }
        return result;
    }
    @Override
    protected void onPostExecute(List<String> result) {
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(List<String> output);
    }
}