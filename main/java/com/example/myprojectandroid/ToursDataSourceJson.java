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

public class ToursDataSourceJson extends AsyncTask<Void, Void, List<Tour>> {
//    private WeakReference<RecyclerView> rvTour;
//
//    public ToursDataSourceJson(WeakReference<RecyclerView> rvTour) {
//        this.rvTour = rvTour;
//    }

    @Override
    protected List<Tour> doInBackground(Void... voids) {
        List<Tour> tours = new ArrayList<>();
        String link = "https://firebasestorage.googleapis.com/v0/b/myproject-a56a3.appspot.com/o/tours.json?alt=media&token=5044c07e-df74-42d9-8c03-669c27e95483";
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(link).build();
            Call call = client.newCall(request);
            try (Response response = call.execute()) {
                if (response.body() == null) return tours;
                String json = response.body().string();
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tourObject = jsonArray.getJSONObject(i);
                    double x = tourObject.getDouble("X");
                    double y = tourObject.getDouble("Y");
                    String Type = tourObject.getString("Type");
                    String DifficultyLevel = tourObject.getString("DifficultyLevel");
                    String TrackType = tourObject.getString("TrackType");
                    String TrackDuration = tourObject.getString("TrackDuration");
                    String TrackLength = tourObject.getString("TrackLength");
                    String AdmissionCharge = tourObject.getString("AdmissionCharge");
                    String Bestseason = tourObject.getString("Bestseason");
                    String Precautions = tourObject.getString("Precautions");
                    String ByAppointment = tourObject.getString("ByAppointment");
                    String Name = tourObject.getString("Name");
                    String TypeRecreation = tourObject.getString("TypeRecreation");
                    String City = tourObject.getString("City");
                    String NearTo = tourObject.getString("NearTo");
                    String OpeningHours = tourObject.getString("OpeningHours");
                    String Parking = tourObject.getString("Parking");
                    String Phone = tourObject.getString("Phone");
                    String Region = tourObject.getString("Region");
                    String SuitableForChildren = tourObject.getString("SuitableForChildren");
                    String URL = tourObject.getString("URL");
                    String FullDescription = tourObject.getString("FullDescription");
                    String ProductUrl = tourObject.getString("ProductUrl");
                    String PicUrl = tourObject.getString("PicUrl");
                    String Accessibility = tourObject.getString("Accessibility");
                    String Address = tourObject.getString("Address");
                    Tour t = new Tour(x,y,Type,DifficultyLevel,TrackType,TrackDuration,TrackLength,AdmissionCharge,Bestseason,Precautions,ByAppointment,Name,TypeRecreation,City,NearTo,OpeningHours,Parking,Phone,Region,SuitableForChildren,URL,FullDescription,ProductUrl,PicUrl,Accessibility,Address);
                    tours.add(t);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
            return tours;
        }
//    protected void onPostExecute(List<Tour> tours) {
//        super.onPostExecute(tours);
//
//        RecyclerView recyclerView = rvTour.get();
//        if (recyclerView == null)return;
//
//        Context context = recyclerView.getContext();
//        TourAdapter adapter = new TourAdapter(tours,context);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//    }
    }

