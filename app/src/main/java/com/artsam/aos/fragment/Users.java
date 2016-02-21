package com.artsam.aos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artsam.aos.R;
import com.artsam.aos.adapter.UsersRecAdapter;
import com.artsam.aos.entity.User;

import java.util.ArrayList;
import java.util.List;

public class Users extends Fragment {

    private List<User> mUsers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_users, container, false);

        CardView cardView = (CardView) view.findViewById(R.id.card_view);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUsers.add(new User());

        RecyclerView recView = (RecyclerView) view.findViewById(R.id.rv_users);
        recView.setLayoutManager(new LinearLayoutManager(getContext()));
        recView.setAdapter(new UsersRecAdapter(mUsers));

        return view;
    }
}
