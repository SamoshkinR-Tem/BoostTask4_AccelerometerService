package com.artsam.aos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artsam.aos.MainActivity;
import com.artsam.aos.R;
import com.artsam.aos.adapter.DataRecAdapter;
import com.artsam.aos.entity.Sample;
import com.artsam.aos.listener.MyChildEventListener;
import com.artsam.aos.view.MyPlotView;

import java.util.ArrayList;
import java.util.List;

public class Data extends Fragment {

    private List<Sample> mSamples = new ArrayList<>();

    private RecyclerView mRecView;
    private MyPlotView mPlotView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(MainActivity.MAIN_TAG, "Data: onCreateView - " + MainActivity.sSamplesRef);

        View data = inflater.inflate(R.layout.frag_data, container, false);

        mRecView = (RecyclerView) data.findViewById(R.id.rv_samples);
        mRecView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecView.setAdapter(new DataRecAdapter(mSamples));

        mPlotView = (MyPlotView) data.findViewById(R.id.my_plot_View);
        mPlotView.setSamples(mSamples);

        if(MainActivity.sSamplesRef != null){
            MainActivity.sChildEventListener = MainActivity.sSamplesRef
                    .addChildEventListener(new MyChildEventListener(mRecView, mPlotView));
        }
        return data;
    }
}
