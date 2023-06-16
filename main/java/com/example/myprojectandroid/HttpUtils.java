//package com.example.myprojectandroid;
//
//import android.content.Context;
//
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//
//import cz.msebera.android.httpclient.Header;
//import cz.msebera.android.httpclient.entity.StringEntity;
//
//public class HttpUtils {
//    private static  String BASE_URL = "https://api.traveltimeapp.com";
//
//    private static AsyncHttpClient client = new AsyncHttpClient();
//
//    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
////        BASE_URL = "/v4/geocoding/search?query=Parliament square&focus.lat=51.512539&focus.lng=-0.097541";
//        client.get(getAbsoluteUrl(url), params, responseHandler);
//    }
//
//    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(getAbsoluteUrl(url), params , responseHandler);
//    }
//    public static void post2(Context contect, String url, Header [] headers, StringEntity entity, String contectType, AsyncHttpResponseHandler responseHandler) {
//        client.post(contect,getAbsoluteUrl(url),headers,entity,contectType,responseHandler);
//    }
//    public static void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.get(url, params, responseHandler);
//    }
//
//    public static void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//        client.post(url, params, responseHandler);
//    }
//
//    private static String getAbsoluteUrl(String relativeUrl) {
//        return BASE_URL + relativeUrl;
//    }
//}
