package com.artsam.aos;

import android.support.v7.widget.RecyclerView;

import com.artsam.aos.entity.Sample;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

public class MyChildEventListener implements ChildEventListener {

    private RecyclerView mRecView;
    private MyRecAdapter mRecAdapter;

    public MyChildEventListener(RecyclerView recView) {
        this.mRecView = recView;
        mRecAdapter = (MyRecAdapter) mRecView.getAdapter();
    }
    
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        mRecAdapter.add(dataSnapshot.getValue(Sample.class));
        mRecView.scrollToPosition(MainActivity.FIRST_SAMPLE_POS);
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
