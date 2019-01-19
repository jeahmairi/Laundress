package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Toast;

public class ClientMyLaundry extends Fragment {
    Button laundrydetails, findlsp;
    private String client_name;
    private int client_id;

    // newInstance constructor for creating fragment with arguments
    public static ClientMyLaundry newInstance(int client_id, String client_name) {
        ClientMyLaundry clientMyLaundry = new ClientMyLaundry();
        Bundle args = new Bundle();
        args.putInt("client_id", client_id);
        args.putString("client_name", client_name);
        clientMyLaundry.setArguments(args);
        return clientMyLaundry;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client_id = getArguments().getInt("client_id", 0);
        client_name = getArguments().getString("client_name");
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_mylaundry, container, false);
        laundrydetails = rootView.findViewById(R.id.laundrydetails);
        Toast.makeText(getContext(),"Name MyLaundry" +client_name+ "ID " +client_id, Toast.LENGTH_SHORT).show();
        laundrydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("client_name",client_name);
                extras.putInt("client_id", client_id);

                Intent intent = new Intent(getActivity(), ClientLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        findlsp = rootView.findViewById(R.id.findlsp);
        findlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("client_name",client_name);
                extras.putInt("client_id", client_id);
                Intent intent = new Intent(getContext(), FindLaundryServiceProv.class);
               /* intent.putExtra("client_name", getClientName());
                intent.putExtra("client_id", getClientId());*/
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
