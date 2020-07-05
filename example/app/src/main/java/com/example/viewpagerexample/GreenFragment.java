package com.example.viewpagerexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GreenFragment extends Fragment {

    private String title;

    public static GreenFragment newInstance(int page, String title) {
        GreenFragment blueFragment = new GreenFragment ();
        Bundle args = new Bundle();
        args.putString("someTitle", title);
        blueFragment.setArguments(args);
        return blueFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_green, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView);
        tvLabel.setText(title);
        return view;
    }
}
