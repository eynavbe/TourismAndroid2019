package com.example.myprojectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

class VideoAdapter extends RecyclerView.Adapter <VideoAdapter.VideoAdapterHolder>{
    List<Video> videos;
    Context context;
    public VideoAdapter(List<Video> videos, Context context) {
        this.videos=videos;
        this.context=context;
    }

    @NonNull
    @Override
    public VideoAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.video_card_view,parent,false);
        return new VideoAdapterHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapterHolder holder, int position) {
        Video video = videos.get(position);
        holder.tvTitle.setText(video.getTitle());
        holder.tvLength.setText(video.getLength());
        holder.video = video;
        Picasso.get().load(video.getPic()).placeholder(R.drawable.ic_autorenew_black_24dp).
                error(R.drawable.ic_block_black_24dp).
                into(holder.ivPic);
    }
    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoAdapterHolder extends RecyclerView.ViewHolder{
        TextView tvLength;
        TextView tvTitle;
        ImageView ivPic;
        Video video;
        CheckBox cbFavourite;
        public VideoAdapterHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLength = itemView.findViewById(R.id.tvLength);
            ivPic = itemView.findViewById(R.id.ivPic);
            cbFavourite = itemView.findViewById(R.id.cbFavourite);
            itemView.setOnClickListener((v) -> {
                YouTubeFragment youTubeFragment = YouTubeFragment.newInstance(video.getId(), 0);
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.frame,youTubeFragment).addToBackStack(null).commit();
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
                        .child("video");

                int position = getAdapterPosition();
                video = videos.get(position);
                if (isChecked){
                    dbFavourite.child(video.getId()).setValue(video);
                }else {
                    dbFavourite.child(video.getId()).setValue(null);
                }
            });

        }
    }
}
