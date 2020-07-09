package com.example.viewpagerexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class PageFragment extends Fragment {

    public static final String FRAGMENT_TAG = "MainFragment";

    public static PageFragment newInstance(String text) {
        return newInstance(text, FRAGMENT_TAG);
    }

    public static PageFragment newInstance(String text, String tag) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putString(MainActivity.FRAGMENT_TAG_ARG, tag + "_" + fragment.hashCode());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.textView);
        textView.setText(getArguments().getString("text"));
        return rootView;
    }

}
