package com.artsam.aos.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artsam.aos.R;

public class Users extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View users = inflater.inflate(R.layout.frag_users, container, false);
        ((TextView) users.findViewById(R.id.tv_tabName)).setText(getString(R.string.users));
        return users;
    }
}
