package com.artsam.aos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View data = inflater.inflate(R.layout.frag_data, container, false);

        RecyclerView recView = (RecyclerView) data.findViewById(R.id.rv_samples);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.setAdapter(new DataRecAdapter(mSamples));

        MyPlotView plotView = (MyPlotView) data.findViewById(R.id.my_plot_View);
        plotView.setSamples(mSamples);

        MainActivity.sFireBaseRef.addChildEventListener(new MyChildEventListener(recView, plotView));

        return data;
    }
}
