package com.example.viewpagerexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
    }

    public static PageFragment newInstance() {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.FRAGMENT_TAG_ARG, "_" + fragment.hashCode());
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.page, container, false);
        return view;
    }
}
