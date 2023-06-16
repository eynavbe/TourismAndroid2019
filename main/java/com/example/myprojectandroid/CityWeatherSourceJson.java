package com.example.myprojectandroid;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityWeatherSourceJson extends AsyncTask<Void, Void, List<WeatherJson>> {

    @Override
    protected List<WeatherJson> doInBackground(Void... voids) {
        String link = "https://firebasestorage.googleapis.com/v0/b/myproject-a56a3.appspot.com/o/WeaderApi.json?alt=media&token=0775cef9-f276-46d2-bc9c-3e2011df9ef8";
        List<WeatherJson> weatherJsons = new ArrayList<>();
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(link).build();
            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                if (response.body() == null) return weatherJsons;
                String json = response.body().string();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject weatherObject = jsonArray.getJSONObject(i);
                    String id = weatherObject.getString("Id");
                    String name = weatherObject.getString("Name");
                    String api = weatherObject.getString("Api");
                    WeatherJson weatherJson = new WeatherJson(id,name,api);
                    weatherJsons.add(weatherJson);
                }
            }catch (JSONException e) {
                    e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return weatherJsons;
    }
}
