package com.example.user.laundress2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


public class FindLaundryShop extends Fragment{
    Button btnchoose;
    String[] laundryDetailName = {
            "Laundry Shop Name", "Laundry Shop Name2", "Laundry Shop Name3", "Laundry Shop Name4", "Laundry Shop Name5"};
    String[] lslocation = {
            "Location", "Location 1", "Location 2", "Location 3", "Location 4" };
    String[] lsmeter = {
            "meter", "meter 2", "meter 3", "meter 4","meter 5" };

    int[] laundryDetailId = {
            R.drawable.laundryshop, R.drawable.buttondone, R.drawable.laundryshop, R.drawable.buttondone, R.drawable.handwasher,
    };
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findlaundryshop, container, false);
        ListView androidGridView = rootView.findViewById(R.id.lvlaundryshop);
       // LaundryDetailsAdapter laundryDetailsAdapter = new LaundryDetailsAdapter(getActivity(), laundryDetailName, laundryDetailId);
        LaundryShopAdapter laundryShopAdapter = new LaundryShopAdapter(getActivity(), laundryDetailName,lslocation,lsmeter,laundryDetailId);
        androidGridView.setAdapter(laundryShopAdapter);
        /*btnchoose = androidGridView.findViewById(R.id.btnchoose);
            btnchoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ChooseLaundryShop.class);
                    startActivity(intent);
                }
            });*/
        return rootView;
    }

}
