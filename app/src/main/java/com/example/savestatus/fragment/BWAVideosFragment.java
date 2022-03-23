package com.example.savestatus.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.savestatus.adapter.WAVideosAdaptor;
import com.example.savestatus.model.ModelStatus;
import com.example.savestatus.R;
import com.example.savestatus.utils.Config;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BWAVideosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView rv;
    TextView textView;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public BWAVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_videos, container, false);
        rv = view.findViewById(R.id.rv_status);
        mSwipeRefreshLayout = view.findViewById(R.id.contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        textView = view.findViewById(R.id.textView);
        rv.setHasFixedSize(true);


        loadData();

        return view;
    }

    public void loadData() {
        Config.videoList.clear();
        final String path = Config.WhatsAppBusinessDirectoryPath;
        File directory = new File(path);
        if (directory.exists()) {

            final File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            final String[] paths = {""};

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (int i = 0; i < files.length; i++) {
                        Log.d("Files", "FileName:" + files[i].getName());
                        Log.d("Files", "FileName:" + files[i].getName().substring(0, files[i].getName().length() - 4));
                        if (files[i].getName().endsWith(".mp4")) {
                            paths[0] = path + "" + files[i].getName();
                            ModelStatus modelStatus = new ModelStatus(paths[0], files[i].getName().substring(0, files[i].getName().length() - 4), 2);
                            Config.videoList.add(modelStatus);
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (!(Config.videoList.toArray().length > 0)) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No Status Available \n Check Out some Status & come back again...");
                    }
                    WAVideosAdaptor adapter = new WAVideosAdaptor(getActivity(), Config.videoList);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new GridLayoutManager(getActivity(), 2);
                    rv.setLayoutManager(llm);
                }
            }.execute();


        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Status Available \n Check Out some Status & come back again...");

            Snackbar.make(getActivity().findViewById(android.R.id.content), "WhatsApp Business Not Installed",
                    Snackbar.LENGTH_SHORT).show();
        }
        refreshItems();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public void refreshItems() {
        onItemsLoadComplete();
    }

    public void onItemsLoadComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


}
