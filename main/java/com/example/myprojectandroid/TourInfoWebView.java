package com.example.myprojectandroid;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * A simple {@link Fragment} subclass.
 */
public class TourInfoWebView extends Fragment {

    public static TourInfoWebView newInstance(Tour model) {
        Bundle args = new Bundle();
        args.putParcelable("model", model);
        TourInfoWebView fragment = new TourInfoWebView();
        fragment.setArguments(args);
        return fragment;
    }
    public TourInfoWebView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_info_web_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Tour model = getArguments().getParcelable("model");
        WebView webView = view.findViewById(R.id.TourInfoWebView);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                String url = request.getUrl();
//                //use the webView for all url loadings
//                webView.loadUrl(url);
//                return true;
//            }

        });

        System.out.println("model.getURL(): " + model.getURL());
        webView.loadUrl(model.getURL());
    }
}
