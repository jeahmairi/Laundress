package com.example.user.laundress2;

import android.content.Context;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HandwasherPosts extends Fragment {
    private String handwasher_name;
    private int handwasher_id;
    private Context context;
    ArrayList<ClientPostList> clientPostLists = new ArrayList<ClientPostList>();
    ClientPostAdapter clientPostAdapter;
    float time;
    ListView posts;
    //private static final String URL_ALLHW ="http://192.168.254.113/laundress/allclientpost.php";
    private static final String URL_ALLHW ="http://192.168.254.117/laundress/allclientpost.php";
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
        posts = rootView.findViewById(R.id.posts);
        Button post = rootView.findViewById(R.id.post);
        allClient();

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

    private void allClient() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALLHW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherpost");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray2.length();i++)
                                {
                                    String poster_name=jsonArray2.getJSONObject(i).getString("poster_name").toString();
                                    String post_message = jsonArray2.getJSONObject(i).getString("post_message").toString();
                                    String client_Contact = jsonArray2.getJSONObject(i).getString("client_Contact").toString();
                                    String client_Photo = jsonArray2.getJSONObject(i).getString("client_Photo").toString();
                                    int post_datetime = Integer.parseInt(jsonArray2.getJSONObject(i).getString("post_datetime").toString());;
                                    if(post_datetime >= 60)
                                    {
                                        time = post_datetime / 60;
                                    } else if(post_datetime < 60) {
                                        time = post_datetime;
                                    }
                                    float hour = post_datetime / 60;
                                    String address="";
                                    String post_showAddress = jsonArray2.getJSONObject(i).getString("post_showAddress").toString();
                                    if(post_showAddress.equals("Yes")){
                                        address = jsonArray2.getJSONObject(i).getString("client_Address").toString();
                                    }
                                    ClientPostList clientPostList = new ClientPostList();
                                    clientPostList.setPost_message(post_message);
                                    clientPostList.setPost_datetime((int) time);
                                    clientPostList.setContact(client_Contact);
                                    clientPostList.setImage(client_Photo);
                                    clientPostList.setPost_name(poster_name);
                                    clientPostList.setPost_showAddress(address);
                                    clientPostLists.add(clientPostList);
                                }
                                clientPostAdapter = new ClientPostAdapter(getActivity(),clientPostLists);
                                posts.setAdapter(clientPostAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
