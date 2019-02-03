package com.example.user.laundress2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HandwasherBookings extends Fragment {
    ListView lvallbookings;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.handwasher_bookings, container, false);
        lvallbookings = rootView.findViewById(R.id.lvallbookings);
        return rootView;
    }
}
