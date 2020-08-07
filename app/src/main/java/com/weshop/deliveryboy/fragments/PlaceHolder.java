package com.weshop.deliveryboy.fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weshop.deliveryboy.MainActivity;
import com.weshop.deliveryboy.R;


public class PlaceHolder extends Fragment {


    public PlaceHolder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.refreshbutton.setVisibility(View.GONE);
        MainActivity.statusLayout.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_holder, container, false);

        return view;
    }

}
