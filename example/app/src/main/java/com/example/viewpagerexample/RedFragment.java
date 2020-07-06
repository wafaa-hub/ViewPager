package com.example.viewpagerexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class RedFragment extends Fragment {

    private String title;

    public static RedFragment newInstance(int page, String title) {
        RedFragment redFragment = new RedFragment ();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        redFragment.setArguments(args);
        return redFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_red, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView);
        tvLabel.setText(title);
        return view;
    }
}
