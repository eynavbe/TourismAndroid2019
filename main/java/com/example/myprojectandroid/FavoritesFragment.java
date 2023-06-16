package com.example.myprojectandroid;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {
    LinearLayout llFavoritesCardView;
    public Context contect;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.contect = context;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
        llFavoritesCardView = view.findViewById(R.id.llFavoritesCardView);



    }

    private FirebaseAuth.AuthStateListener mAuthStateListener = firebaseAuth -> {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            if (contect == null){
                contect = getContext();
            }

            Intent intent = new Intent(contect, LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);

        }else {
            User user1 = new User();
            user1.setEmail(user.getEmail());
            user1.setuId(user.getUid());
            user1.setLastSeen(new Date().toString());
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userNameRef = rootRef.child("users").child(user1.getuId());
            userNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.exists()) {
                        FirebaseDatabase.getInstance().getReference("users").child(user1.getuId()).setValue(user1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });


            //בודק אם יש מועדפים של סרטונים
            DatabaseReference rootRefInfo = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourite").child("video");
            rootRefInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        RecyclerView recyclerView= new RecyclerView(contect);
                        recyclerView.setHasFixedSize(true);
                        GetAllDataVideo(recyclerView,llFavoritesCardView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(contect, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setPadding(16,16,16,32);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //בודק אם יש מועדפים של טיולים
            rootRefInfo = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourite").child("tour");
            rootRefInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        RecyclerView recyclerView= new RecyclerView(contect);
                        recyclerView.setHasFixedSize(true);
                        GetAllDataTour(recyclerView,llFavoritesCardView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(contect, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setPadding(16,16,16,32);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            //בודק אם יש מועדפים של ערים
            rootRefInfo = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("favourite").child("city");
            rootRefInfo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {

                        RecyclerView recyclerView= new RecyclerView(contect);
                        recyclerView.setHasFixedSize(true);
                        GetAllDataCity(recyclerView,llFavoritesCardView);
                        recyclerView.setLayoutManager(new LinearLayoutManager(contect, LinearLayoutManager.HORIZONTAL, false));
                        recyclerView.setPadding(16,16,16,32);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    };

    private void GetAllDataVideo(RecyclerView recyclerView, LinearLayout llFavoritesCardView){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourite")
                .child("video");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Object> videoList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userNode : dataSnapshot.getChildren()) {
                    String id = userNode.getKey();
                    String title = userNode.child("title").getValue(String.class);
                    String city = userNode.child("city").getValue(String.class);
                    String length = userNode.child("length").getValue(String.class);
                    String pic = userNode.child("pic").getValue(String.class);
                    Video video = new Video(title,id,city,length,pic);
                    videoList.add(video);
                }
                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(videoList,contect);
                recyclerView.setAdapter(favoritesAdapter);
                TextView textView = new TextView(contect);
                textView.setText("סרטונים: ");
                textView.setPadding(16,32,16,0);
                llFavoritesCardView.addView(textView);
                llFavoritesCardView.addView(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GetAllDataTour(RecyclerView recyclerView, LinearLayout llFavoritesCardView){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourite")
                .child("tour");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Object> tourList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userNode : dataSnapshot.getChildren()) {

                        int Id = Objects.requireNonNull(userNode.child("id").getValue(int.class));
                    double X = Objects.requireNonNull(userNode.child("x").getValue(double.class));
                    double Y = Objects.requireNonNull(userNode.child("y").getValue(double.class));
                    String Type = userNode.child("type").getValue(String.class);
                    String DifficultyLevel = userNode.child("difficultyLevel").getValue(String.class);
                    String TrackType = userNode.child("trackType").getValue(String.class);
                    String TrackDuration = userNode.child("trackDuration").getValue(String.class);
                    String TrackLength = userNode.child("trackLength").getValue(String.class);
                    String AdmissionCharge = userNode.child("admissionCharge").getValue(String.class);
                    String BestSeason = userNode.child("bestSeason").getValue(String.class);
                    String Precautions = userNode.child("precautions").getValue(String.class);
                    String ByAppointment = userNode.child("byAppointment").getValue(String.class);
                    String Name = userNode.child("name").getValue(String.class);
                    String TypeRecreation = userNode.child("typeRecreation").getValue(String.class);
                    String City = userNode.child("city").getValue(String.class);
                    String NearTo = userNode.child("nearTo").getValue(String.class);
                    String OpeningHours = userNode.child("openingHours").getValue(String.class);
                    String Parking = userNode.child("parking").getValue(String.class);
                    String Phone = userNode.child("phone").getValue(String.class);
                    String Region = userNode.child("region").getValue(String.class);
                    String SuitableForChildren = userNode.child("suitableForChildren").getValue(String.class);
                    String URL = userNode.child("URL").getValue(String.class);
                    String FullDescription = userNode.child("fullDescription").getValue(String.class);
                    String ProductUrl = userNode.child("productUrl").getValue(String.class);
                    String PicUrl = userNode.child("picUrl").getValue(String.class);
                    String Accessibility = userNode.child("accessibility").getValue(String.class);
                    String Address = userNode.child("address").getValue(String.class);
                    Tour tour = new Tour(Id, X,Y,Type,DifficultyLevel,TrackType,TrackDuration,TrackLength,
                            AdmissionCharge,BestSeason,Precautions,ByAppointment,Name,TypeRecreation ,
                            City,NearTo,OpeningHours,Parking,Phone,Region,SuitableForChildren,URL,
                            FullDescription,ProductUrl,PicUrl,Accessibility,Address);
                    tourList.add(tour);
                }
                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(tourList,contect);
                recyclerView.setAdapter(favoritesAdapter);
                TextView textView = new TextView(contect);
                textView.setText("טיולים: ");
                textView.setPadding(16,32,16,0);
                llFavoritesCardView.addView(textView);
                llFavoritesCardView.addView(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GetAllDataCity(RecyclerView recyclerView, LinearLayout llFavoritesCardView){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("favourite")
                .child("city");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Object> cityList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userNode : dataSnapshot.getChildren()) {
                    Long id = Objects.requireNonNull(userNode.child("id").getValue(long.class));
                    System.out.println(id);
                    String name = userNode.child("name").getValue(String.class);
                    String fullDescription = userNode.child("fullDescription").getValue(String.class);
                    String VendorName = userNode.child("VendorName").getValue(String.class);
                    String productURL = userNode.child("productURL").getValue(String.class);
                    String picURL = userNode.child("picURL").getValue(String.class);
                    String faq = userNode.child("faq").getValue(String.class);
                    String region = userNode.child("region").getValue(String.class);
                    double x = Objects.requireNonNull(userNode.child("x").getValue(Double.class));
                    double y = Objects.requireNonNull(userNode.child("y").getValue(Double.class));
                    String youtube = userNode.child("youtube").getValue(String.class);


                    City city = new City(1,name,fullDescription,VendorName,productURL,picURL,faq,region,x,y,youtube);
                    cityList.add(city);
                }
                FavoritesAdapter favoritesAdapter = new FavoritesAdapter(cityList,contect);
                recyclerView.setAdapter(favoritesAdapter);
                TextView textView = new TextView(contect);
                textView.setText("ערים: ");
                textView.setPadding(16,32,16,0);
                llFavoritesCardView.addView(textView);
                llFavoritesCardView.addView(recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void GetAllDataUsersInRealTime(){
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userNode : dataSnapshot.getChildren()) {
                    System.out.println(userNode.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
