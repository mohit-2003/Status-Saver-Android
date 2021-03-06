package com.example.savestatus.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.savestatus.model.StatusModel;
import com.example.savestatus.R;
import com.example.savestatus.activity.ImageViewerActivity;
import com.example.savestatus.activity.VideoViewerActivity;

import java.io.File;
import java.util.ArrayList;

public class WASavedAdapter extends RecyclerView.Adapter<WASavedAdapter.MyViewHolder> {

    private Context acontext;
    private ArrayList<StatusModel> arrayList;

    public WASavedAdapter(Context context, ArrayList<StatusModel> arrayList) {
        this.arrayList = arrayList;
        acontext = context;
    }

    @Override
    public WASavedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StatusModel current = arrayList.get(position);
        Glide.with(acontext).load(current.getFull_path())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void shareVia(String type, String path) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(type);
        File fileToShare = new File(path);
        Uri uri = Uri.fromFile(fileToShare);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        acontext.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public void deleteFile(String path, int position) {
        File fdelete = new File(path);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                removeAt(position);
                Toast.makeText(acontext, "Delete Success", Toast.LENGTH_SHORT).show();
                System.out.println("file Deleted :" + path);
            } else {
                Toast.makeText(acontext, "Delete Failed", Toast.LENGTH_SHORT).show();
                System.out.println("file not Deleted :" + path);
            }
        }
    }

    public void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public ImageView imageView;
        public LinearLayout btn_delete, btn_share, btn_download;
        public ImageButton img_btn_delete, img_btn_share;


        public MyViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.card_view_order_cancel);

            imageView = v.findViewById(R.id.imageView);

            btn_download = v.findViewById(R.id.btn_download);
            btn_download.setVisibility(View.GONE);

            btn_delete = v.findViewById(R.id.btn_delete);
            btn_delete.setVisibility(View.VISIBLE);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        StatusModel modelStatus = arrayList.get(getAdapterPosition());
                        deleteFile(modelStatus.getFull_path(), getAdapterPosition());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    
                }
            });

            btn_share = v.findViewById(R.id.btn_share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel modelStatus = arrayList.get(getAdapterPosition());

                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        shareVia("image/jpg", modelStatus.getFull_path());
                    } else if (modelStatus.getFull_path().endsWith(".mp4")) {
                        shareVia("video/mp4", modelStatus.getFull_path());
                    }

                    
                }
            });
            img_btn_share = v.findViewById(R.id.img_btn_share);
            img_btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel modelStatus = arrayList.get(getAdapterPosition());

                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        shareVia("image/jpg", modelStatus.getFull_path());
                    } else if (modelStatus.getFull_path().endsWith(".mp4")) {
                        shareVia("video/mp4", modelStatus.getFull_path());
                    }
                }
            });

            img_btn_delete = v.findViewById(R.id.img_btn_delete);
            img_btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        StatusModel modelStatus = arrayList.get(getAdapterPosition());
                        deleteFile(modelStatus.getFull_path(), getAdapterPosition());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StatusModel modelStatus = arrayList.get(getAbsoluteAdapterPosition());
                    Intent intent;
                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        intent = new Intent(acontext, ImageViewerActivity.class);
                        intent.putExtra("image", modelStatus.getFull_path());
                    } else {
                        intent = new Intent(acontext, VideoViewerActivity.class);
                        intent.putExtra("video", modelStatus.getFull_path());
                    }
                    intent.putExtra("isSaved", true); // for only this fragment
                    intent.putExtra("type", "" + modelStatus.getType());
                    intent.putExtra("atype", "0");
                    intent.putExtra("position", getAbsoluteAdapterPosition());
                    acontext.startActivity(intent);
                }
            });
        }

    }
}