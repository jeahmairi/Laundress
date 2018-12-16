package com.example.user.laundress2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class FindLaundryShop extends Fragment{
    String[] laundryDetailName = {
            "T-shirt and Polo", "Sleeveless"
    };
    String[] lslocation = {
            "T-shirt and Polo", "Sleeveless"
    };
    String[] lsmeter = {
            "T-shirt and Polo", "Sleeveless"
    };

    int[] laundryDetailId = {
            R.drawable.laundryshop,
            R.drawable.buttondone

    };
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findlaundryshop, container, false);
        ListView androidGridView = rootView.findViewById(R.id.lvlaundryshop);
       // LaundryDetailsAdapter laundryDetailsAdapter = new LaundryDetailsAdapter(getActivity(), laundryDetailName, laundryDetailId);
        LaundryShopAdapter laundryShopAdapter = new LaundryShopAdapter(getActivity(), laundryDetailName,laundryDetailId);
        androidGridView.setAdapter(laundryShopAdapter);
        return rootView;
    }

}
