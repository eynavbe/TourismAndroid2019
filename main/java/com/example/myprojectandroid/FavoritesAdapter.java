package com.example.myprojectandroid;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import jp.wasabeef.picasso.transformations.MaskTransformation;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterHolder> {
    List<Object> favorites;
    Context context;
    final String imgFolder = "https://motwebmediastg01.blob.core.windows.net/nop-thumbs-images/";

    public FavoritesAdapter(List<Object> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.favorites_card_view,parent,false);
        return new FavoritesAdapter.FavoritesAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapterHolder holder, int position) {
        Object object = favorites.get(position);
        holder.object = object;

        Tour tour;
        City city;
        Video video;
        if (object instanceof Tour){
            tour = (Tour) object;
            holder.tvFavoritesObject.setText(tour.getName());
            Picasso.get().load(imgFolder+tour.getPicUrl()).transform(new MaskTransformation(context, R.drawable.ic_star))
                    .into(holder.ivFavoritesObject);
        }
        else if (object instanceof City){
            city = (City) object;
            holder.tvFavoritesObject.setText(city.getName());
            Picasso.get().load(imgFolder+city.getPicURL()).transform(new MaskTransformation(context, R.drawable.ic_star))
                    .into(holder.ivFavoritesObject);
        }
        else if (object instanceof Video){
            video = (Video) object;
            holder.tvFavoritesObject.setText(video.getTitle());
            Picasso.get().load(video.getPic()).transform(new MaskTransformation(context, R.drawable.ic_star))
                    .into(holder.ivFavoritesObject);
        }

//        MaskTransformation
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoritesAdapterHolder extends RecyclerView.ViewHolder{
        TextView tvFavoritesObject;
        ImageView ivFavoritesObject;
        Object object;

        public FavoritesAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvFavoritesObject = itemView.findViewById(R.id.tvFavoritesObject);
            ivFavoritesObject = itemView.findViewById(R.id.ivFavoritesObject);
            itemView.setOnClickListener((v)->{
                if (object instanceof Tour){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(TourInfo.EXTRA_TOUR, ((Tour)object));
                    Fragment fragment = new TourInfo();
                    fragment.setArguments(bundle);
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
                }
                else if (object instanceof City){
                    if (((City)object).getX() != 0){
                        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(TourRecyclerviewTabLayoutFragment.EXTRA_CITY, ((City)object));
                        Fragment fragment = new TourRecyclerviewTabLayoutFragment();
                        fragment.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();

                    }else {
                        Toast.makeText(context, "אין יותר מידע", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (object instanceof Video){
                    YouTubeFragment youTubeFragment = YouTubeFragment.newInstance(((Video)object).getId(), 0);
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    activity.getFragmentManager().beginTransaction().replace(R.id.frame,youTubeFragment).addToBackStack(null).commit();
                }
            });
        }
    }
}
