package com.yourcompany.savestory.adaptor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.yourcompany.savestory.activity.DrawerActivity;
import com.bumptech.glide.Glide;
import com.yourcompany.savestory.model.ModelStatus;
import com.yourcompany.savestory.R;
import com.yourcompany.savestory.utils.Config;
import com.yourcompany.savestory.activity.ImageViewerActivity;
import com.yourcompany.savestory.activity.VIdeoViewerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WASavedAdaptor extends RecyclerView.Adapter<WASavedAdaptor.MyViewHolder> {

    private final String TAG = "SAVEAdaptor";
    public int count = Config.count;
    String listenerValue = "";
    DrawerActivity drawer = new DrawerActivity();
    private Context acontext;
    private ArrayList<ModelStatus> arrayList;

    public WASavedAdaptor(Context context, ArrayList<ModelStatus> arrayList) {
        this.arrayList = arrayList;
        acontext = context;
    }

    @Override
    public WASavedAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_pictures, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelStatus current = arrayList.get(position);
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

    public void allSharedPreference(int i) {
        SharedPreferences preferences = acontext.getSharedPreferences("PREFRENCE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ALL", String.valueOf(i));
        editor.commit();
    }

    public void sharePerAds() {
        int i;
        if (Config.getALLState(acontext).length() > 0) {
            i = Integer.parseInt(Config.getALLState(acontext));
            if (i > count) {

                allSharedPreference(0);
            } else {
                i++;
                allSharedPreference(i);
            }
        } else {
            i = 1;
            allSharedPreference(i);
        }
        listenerValue = String.valueOf(i);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public CardView mCardView;
        public ImageView imageView;
        public LinearLayout btn_delete, btn_share;
        public ImageButton img_btn_delete, img_btn_share;


        public MyViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.card_view_order_cancel);

            imageView = v.findViewById(R.id.imageView);

            btn_delete = v.findViewById(R.id.btn_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ModelStatus modelStatus = arrayList.get(getAdapterPosition());
                        deleteFile(modelStatus.getFull_path(), getAdapterPosition());
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }

                    sharePerAds();
                }
            });

            btn_share = v.findViewById(R.id.btn_share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelStatus modelStatus = arrayList.get(getAdapterPosition());

                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        shareVia("image/jpg", modelStatus.getFull_path());
                    } else if (modelStatus.getFull_path().endsWith(".mp4")) {
                        shareVia("video/mp4", modelStatus.getFull_path());
                    }

                    sharePerAds();
                }
            });
            img_btn_share = v.findViewById(R.id.img_btn_share);
            img_btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelStatus modelStatus = arrayList.get(getAdapterPosition());

                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        shareVia("image/jpg", modelStatus.getFull_path());
                    } else if (modelStatus.getFull_path().endsWith(".mp4")) {
                        shareVia("video/mp4", modelStatus.getFull_path());
                    }

                    sharePerAds();
                }
            });

            img_btn_delete = v.findViewById(R.id.img_btn_delete);
            img_btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        ModelStatus modelStatus = arrayList.get(getAdapterPosition());
                        deleteFile(modelStatus.getFull_path(), getAdapterPosition());
                    } catch (ArrayIndexOutOfBoundsException ex) {
                    }

                    sharePerAds();
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ModelStatus modelStatus = arrayList.get(getAdapterPosition());
                    if (modelStatus.getFull_path().endsWith(".jpg")) {
                        /*File file = new File(modelStatus.getFull_path());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "image/*");
                        acontext.startActivity(intent);*/

                        Intent intent = new Intent(acontext, ImageViewerActivity.class);
                        intent.putExtra("image", modelStatus.getFull_path());
                        intent.putExtra("type", "" + modelStatus.getType());
                        intent.putExtra("atype", "0");
                        acontext.startActivity(intent);
                    } else {
                       /* File file = new File(modelStatus.getFull_path());
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), "video/*");
                        acontext.startActivity(intent);*/
                        Intent intent = new Intent(acontext, VIdeoViewerActivity.class);
                        intent.putExtra("video", modelStatus.getFull_path());
                        intent.putExtra("type", "" + modelStatus.getType());
                        intent.putExtra("atype", "0");
                        acontext.startActivity(intent);
                    }
                }
            });
        }

    }
}