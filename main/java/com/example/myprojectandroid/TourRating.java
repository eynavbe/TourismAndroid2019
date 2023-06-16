package com.example.myprojectandroid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TourRating  {



    protected List<WeatherJson> doInBackground() {
        String link = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=31.77671890000001,35.23450849999999&rankby=distance&type=point_of_interest&key=AIzaSyDNGt0CbB_y1-UIl0DQ_CWPcROrr2EE9QI";
        List<WeatherJson> weatherJsons = new ArrayList<>();
        try{
            URL url1 = new URL(link);
            URLConnection yc = url1.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
//            String inputLine;
//            InputStream is = url1.openConnection().getInputStream();
            StringBuffer buffer = new StringBuffer();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line+"\n");
            }
            System.out.println("buffer.toString() " +buffer.toString());

        }catch (IOException e){
            e.printStackTrace();
        }
        return weatherJsons;
    }


}
