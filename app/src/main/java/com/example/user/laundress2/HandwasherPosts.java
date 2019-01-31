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

public class HandwasherPosts extends Fragment {
    private String handwasher_name;
    private int handwasher_id;
    // newInstance constructor for creating fragment with arguments
    public static HandwasherPosts newInstance(int handwasher_id, String handwasher_name) {
        HandwasherPosts handwasherPosts = new HandwasherPosts();
        Bundle args = new Bundle();
        args.putInt("handwasher_id", handwasher_id);
        args.putString("handwasher_name", handwasher_name);
        handwasherPosts.setArguments(args);
        return handwasherPosts;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handwasher_id = getArguments().getInt("handwasher_id", 0);
        handwasher_name = getArguments().getString("handwasher_name");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.handwasher_posts, container, false);

        Button post = rootView.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("handwasher_name",handwasher_name);
                extras.putInt("handwasher_id", handwasher_id);
                Intent intent = new Intent(getActivity(), HandwasherMakePost.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
