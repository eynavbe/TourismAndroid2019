package com.example.myprojectandroid;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesAdapterHolder>{
    Context context;
    List<City> cities;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";
    public CitiesAdapter(Context context, List<City> cities) {
        this.context = context;
        this.cities = cities;
    }

    @NonNull
    @Override
    public CitiesAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cities_card_view,parent,false);
        return new CitiesAdapter.CitiesAdapterHolder(v);    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapterHolder holder, int position) {
        City city = cities.get(position);
        holder.tvName.setText(city.getName());
//        String string = city.getFullDescription();
//        if (string != null){
//            Document doc = Jsoup.parse(string);
//            Elements element = doc.getElementsByTag("p");
//            Element e = element.get(0);
//            String stringtoParts = (e.text());
//            int index = stringtoParts.indexOf(".");
//            String subString= stringtoParts.substring(0 , index+1);
//            holder.tvDescription.setText(subString);
//
//        }
        int color1  = Color.rgb((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
        holder.ivColor.setColorFilter(color1);

        try {
            URL url = new URL((imgFolder+city.getPicURL()));
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Bitmap myImage = getBitmapFromURL(imgFolder+city.getPicURL());
//        Drawable dr = new BitmapDrawable(myImage);
//        holder.ivCity.setBackgroundDrawable(dr);

        URL url = null;
        try {
            url = new URL(imgFolder+city.getPicURL());
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable background = new BitmapDrawable(Resources.getSystem(), bitmap);
            holder.ivCity.setBackgroundDrawable(background);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        holder.city = city;
    }
//    public Bitmap getBitmapFromURL(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class CitiesAdapterHolder extends RecyclerView.ViewHolder {
        ImageView ivColor;
        TextView tvName;
//        TextView tvDescription;
        ImageView ivCity;
        City city;
        CheckBox cbFavourite;
        public CitiesAdapterHolder(@NonNull View itemView) {
            super(itemView);
            ivCity = itemView.findViewById(R.id.ivCity);
//            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvName = itemView.findViewById(R.id.tvName);
            ivColor = itemView.findViewById(R.id.ivColor);
            cbFavourite = itemView.findViewById(R.id.cbFavourite);
            itemView.setOnClickListener((v)->{
//                Bundle bundle = new Bundle();
//                bundle.putParcelable(TourRecyclerviewFragment.EXTRA_CITY, city);
//                Fragment fragment = new TourRecyclerviewFragment();
//                fragment.setArguments(bundle);
                if (city.getX() != 0){
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();

                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelable(TourRecyclerviewTabLayoutFragment.EXTRA_CITY, city);
                    Fragment fragment2 = new TourRecyclerviewTabLayoutFragment();
                    fragment2.setArguments(bundle2);


                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment2).addToBackStack(null).commit();

                }else {
                    Toast.makeText(context, "אין יותר מידע", Toast.LENGTH_SHORT).show();
                }
            });

            //להוסיף למועדפים
            cbFavourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (FirebaseAuth.getInstance().getCurrentUser() == null){
                    new AlertDialog.Builder(context).setTitle("להוסיף למועדפים").setMessage("נא להירשם לפני").
                            setPositiveButton("בסדר", (dialog, which) -> { }).show();
                    buttonView.setChecked(false);
                    return;
                }
                DatabaseReference dbFavourite = FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child("favourite")
                        .child("city");

                int position = getAdapterPosition();
                city = cities.get(position);
                if (isChecked){
                    dbFavourite.child(String.valueOf(city.getId())).setValue(city);
                }else {
                    dbFavourite.child(String.valueOf(city.getId())).setValue(null);
                }
            });
        }

    }
}
























