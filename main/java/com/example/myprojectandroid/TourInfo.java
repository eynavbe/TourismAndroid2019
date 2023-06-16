package com.example.myprojectandroid;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class TourInfo extends Fragment {
    public static final String EXTRA_TOUR = "tour1";
    FloatingActionButton fabLocation,fabMoovit,fabWaze, fabInfoWeb;
    TextView tvNameTour,tvInformationTour;
    LinearLayout llTourInfo;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    ImageView ivInfoTour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tour_info, container, false);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//
//            case 123:
//                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    onCall("123");
//                } else {
//                    Log.d("TAG", "Call Permission Not Granted");
//                }
//                break;
//
//            default:
//                break;
//        }
//    }

    private void onCall(String numPhone) {
        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    Integer.parseInt("123"));
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+numPhone)));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        Tour tour = null;
        if (bundle != null) {
            tour = bundle.getParcelable(EXTRA_TOUR);
        }
        tvNameTour = view.findViewById(R.id.tvNameTourInfo);
        tvNameTour.setText(tour.getName());
        ivInfoTour = view.findViewById(R.id.ivInfoTour);
        Picasso.get().load(imgFolder+tour.getPicUrl()).placeholder(R.drawable.ic_home_black_24dp).
                error(R.drawable.ic_dashboard_black_24dp).
                into(ivInfoTour);
        tvInformationTour = view.findViewById(R.id.tvInformationTour);
        llTourInfo = view.findViewById(R.id.llTourInfo);
        fabLocation = view.findViewById(R.id.fabLocation);
        fabMoovit = view.findViewById(R.id.fabMoovit);
        fabWaze = view.findViewById(R.id.fabWaze);
        fabInfoWeb = view.findViewById(R.id.fabInfoWeb);


        TextView llTourInfoTextType= new TextView(getContext());
        llTourInfoTextType.setText("סוג: "+tour.getType());
        llTourInfo.addView(llTourInfoTextType);
        if (tour.getAccessibility().length() > 1){
            TextView llTourInfoTextAccessibility = new TextView(getContext());
            llTourInfoTextAccessibility.setText("נגישות: "+tour.getAccessibility());
            llTourInfo.addView(llTourInfoTextAccessibility);
        }
        if (tour.getAddress().length() > 1){
            TextView llTourInfoTextAddress= new TextView(getContext());
            System.out.println(("כתובת: "+tour.getAddress()));
            llTourInfoTextAddress.setText("כתובת: "+tour.getAddress());
            llTourInfo.addView(llTourInfoTextAddress);
        }
        if (tour.getTypeRecreation().length() > 1){
            TextView llTourInfoTextTypeRecreatio = new TextView(getContext());
            llTourInfoTextTypeRecreatio.setText("סוג אטרקציה: "+tour.getTypeRecreation());
            llTourInfo.addView(llTourInfoTextTypeRecreatio);
        }
        if (tour.getCity().length() > 1){
            TextView llTourInfoTextCity = new TextView(getContext());
            llTourInfoTextCity.setText("עיר: "+tour.getCity());
            llTourInfo.addView(llTourInfoTextCity);
        }
        if (tour.getNearTo().length() > 1){
            TextView llTourInfoTextNearTo = new TextView(getContext());
            llTourInfoTextNearTo.setText("קרוב ל: "+tour.getNearTo());
            llTourInfo.addView(llTourInfoTextNearTo);
        }
        if (tour.getOpeningHours().length() > 1){
            TextView llTourInfoTextOpeningHours = new TextView(getContext());
            llTourInfoTextOpeningHours.setText("שעות פתיחה: "+tour.getOpeningHours());
            llTourInfo.addView(llTourInfoTextOpeningHours);
        }
        if (tour.getParking().length() > 1){
            TextView llTourInfoTextParking = new TextView(getContext());
            llTourInfoTextParking.setText("חנייה: "+tour.getParking());
            llTourInfo.addView(llTourInfoTextParking);
        }
        if (tour.getPhone().length() > 1){
            TextView llTourInfoTextPhone = new TextView(getContext());
            String string = tour.getPhone();
            Document doc = Jsoup.parse(string);
            Elements element = doc.getElementsByTag("a");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < element.size(); i++) {
                Element e = element.get(i);
                stringBuilder.append(e.text());
            }
            SpannableString text = new SpannableString("טלפון:  "+stringBuilder.toString());
            text.setSpan(new ImageSpan(getActivity(), R.drawable.ic_call_black_24dp), 7, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            llTourInfoTextPhone.setText(text);
            llTourInfoTextPhone.setOnClickListener((v)->{
                onCall(stringBuilder.toString());
            });
            llTourInfo.addView(llTourInfoTextPhone);
        }
        if (tour.getRegion().length() > 1){
            TextView llTourInfoTextRegion = new TextView(getContext());
            llTourInfoTextRegion.setText("אזור: "+tour.getRegion());
            llTourInfo.addView(llTourInfoTextRegion);
        }
        if (tour.getSuitableForChildren().length() > 1){
            TextView llTourInfoTextSuitableForChildren = new TextView(getContext());
            llTourInfoTextSuitableForChildren.setText("מתאים לילדים: "+tour.getSuitableForChildren());
            llTourInfo.addView(llTourInfoTextSuitableForChildren);
        }
        if (tour.getDifficultyLevel().length() > 1){
            TextView llTourInfoTextDifficultyLevel = new TextView(getContext());
            llTourInfoTextDifficultyLevel.setText("רמת קושי: "+tour.getDifficultyLevel());
            llTourInfo.addView(llTourInfoTextDifficultyLevel);
        }
        if (tour.getTrackType().length() > 1){
            TextView llTourInfoTextTrackType = new TextView(getContext());
            llTourInfoTextTrackType.setText("סוג מסלול: "+tour.getTrackType());
            llTourInfo.addView(llTourInfoTextTrackType);
        }
        if (tour.getTrackDuration().length() > 1){
            TextView llTourInfoTextTrackDuration= new TextView(getContext());
            llTourInfoTextTrackDuration.setText("משך מסלול: "+tour.getTrackDuration());
            llTourInfo.addView(llTourInfoTextTrackDuration);
        }
        if (tour.getTrackLength().length() > 1){
            TextView llTourInfoTextTrackLength = new TextView(getContext());
            llTourInfoTextTrackLength.setText("אורך מסלול: "+tour.getTrackLength());
            llTourInfo.addView(llTourInfoTextTrackLength);
        }
        if (tour.getAdmissionCharge().length() > 1){
            TextView llTourInfoTextAdmissionCharge = new TextView(getContext());
            llTourInfoTextAdmissionCharge.setText("דמי כניסה: "+tour.getAdmissionCharge());
            llTourInfo.addView(llTourInfoTextAdmissionCharge);
        }
        if (tour.getBestSeason().length() > 1){
            TextView llTourInfoTextBestSeason = new TextView(getContext());
            llTourInfoTextBestSeason.setText("עונה טובה לטייל שם: "+tour.getBestSeason());
            llTourInfo.addView(llTourInfoTextBestSeason);
        }
        if (tour.getPrecautions().length() > 1){
            TextView llTourInfoTextPrecautions = new TextView(getContext());
            llTourInfoTextPrecautions.setText("אמצעי זהירות: "+tour.getPrecautions());
            llTourInfo.addView(llTourInfoTextPrecautions);
        }
        if (tour.getByAppointment().length() > 1){
            TextView llTourInfoTextByAppointment = new TextView(getContext());
            llTourInfoTextByAppointment.setText("בתיאום מראש: "+tour.getByAppointment());
            llTourInfo.addView(llTourInfoTextByAppointment);
        }
        String string = tour.getFullDescription();
        Document doc = Jsoup.parse(string);
        Elements element = doc.getElementsByTag("p");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < element.size(); i++) {
            Element e = element.get(i);
            stringBuilder.append(e.text() + "\n");
            tvInformationTour.setText(stringBuilder.toString());
        }

        Tour finalTour = tour;
        fabLocation.setOnClickListener((v)->{
            Fragment fragment = new ToursMapFragment();
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            List<Tour> list = new ArrayList<>();
            list.add(finalTour);
            System.out.println(finalTour);
            Bundle bundle1 = new Bundle();
            bundle1.putParcelableArrayList(ToursMapFragment.EXTRA_LIST_TOURS, (ArrayList<? extends Parcelable>) list);
            fragment.setArguments(bundle1);

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();

        });
        fabMoovit.setOnClickListener((v)->{
            try {
                PackageManager pm = getContext().getPackageManager();
                pm.getPackageInfo("com.tranzmate", PackageManager.GET_ACTIVITIES);
                String uri = "moovit://nearby?lat=<lat>&lon=<lon>&partner_id=       <YOUR_APP_NAME>";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
                getActivity().finish();

            } catch (PackageManager.NameNotFoundException e) {
                String url = "http://app.appsflyer.com/com.tranzmate?pid=DL&c=<YOUR_APP_NAME>";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        Tour finalTour1 = tour;
        fabWaze.setOnClickListener((v)->{
            try
            {
                String url = "https://waze.com/ul?q=66%20Acacia%20Avenue&ll="+ finalTour1.getY()+","+ finalTour1.getY()+"&navigate=yes";
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( url ) );
                startActivity( intent );
                getActivity().finish();
            }
            catch ( ActivityNotFoundException ex  )
            {
                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
                startActivity(intent);
            }
        });
        Tour finalTour2 = tour;
        fabInfoWeb.setOnClickListener((v)->{
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            activity.getSupportFragmentManager().
                    beginTransaction().
                    addToBackStack("details").
                    replace(R.id.frame, TourInfoWebView.newInstance(finalTour2)).
                    commit();
        });
    }

}
