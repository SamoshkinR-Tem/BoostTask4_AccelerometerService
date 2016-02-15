package com.artsam.aos.listener;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.artsam.aos.MainActivity;
import com.artsam.aos.adapter.MyRecAdapter;
import com.artsam.aos.entity.Sample;
import com.artsam.aos.view.MyPlotView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class MyChildEventListener implements ChildEventListener {

    private MyPlotView mPlotView;
    private RecyclerView mRecView;
    private MyRecAdapter mRecAdapter;

    public MyChildEventListener(RecyclerView recView, MyPlotView plotView) {
        this.mPlotView = plotView;
        this.mRecView = recView;
        this.mRecAdapter = (MyRecAdapter) recView.getAdapter();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Log.d(MainActivity.MAIN_TAG, "MyChildEventListener: onChildAdded");

        mRecAdapter.add(dataSnapshot.getValue(Sample.class));
        mRecView.scrollToPosition(MainActivity.FIRST_SAMPLE_POS);
        mPlotView.onPlotDataChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(FirebaseError firebaseError) {

    }
}
