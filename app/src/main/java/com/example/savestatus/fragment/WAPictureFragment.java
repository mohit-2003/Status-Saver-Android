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

import com.example.savestatus.adapter.WAPictureAdaptor;
import com.example.savestatus.model.StatusModel;
import com.example.savestatus.R;
import com.example.savestatus.utils.Config;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class WAPictureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ArrayList<StatusModel> data;
    RecyclerView rv;
    TextView textView;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public WAPictureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_pics, container, false);
        rv = view.findViewById(R.id.rv_status);
        mSwipeRefreshLayout = view.findViewById(R.id.contentView);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        textView = view.findViewById(R.id.textView);
        rv.setHasFixedSize(true);

        loadData();

        return view;

    }

    public void loadData() {
        data = new ArrayList<>();
        final String path = Config.WhatsAppDirectoryPath;
        File directory = new File(path);
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            final String[] paths = {""};
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    for (int i = 0; i < files.length; i++) {
                        Log.d("Files", "FileName:" + files[i].getName());
                        Log.d("Files", "FileName:" + files[i].getName().substring(0, files[i].getName().length() - 4));
                        if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith("gif")) {
                            paths[0] = path + "/" + files[i].getName();
                            StatusModel modelStatus = new StatusModel(paths[0], files[i].getName().substring(0, files[i].getName().length() - 4), 0);
                            data.add(modelStatus);
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (!(data.toArray().length > 0)) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("No Status Available \n Check Out some Status & come back again...");
                    }
                    WAPictureAdaptor adapter = new WAPictureAdaptor(requireActivity(), data);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new GridLayoutManager(requireActivity(), 2);
                    rv.setLayoutManager(llm);

                }
            }.execute();
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No Status Available \n Check Out some Status & come back again...");

            Snackbar.make(requireActivity().findViewById(android.R.id.content), "WhatsApp Not Installed",
                    Snackbar.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadData();
    }

}
