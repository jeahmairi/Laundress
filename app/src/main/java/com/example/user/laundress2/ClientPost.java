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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClientPost extends Fragment {
    private String client_name;
    private int client_id;
    float time;
    ListView listView;
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<Integer> arrtime = new ArrayList<>();
    ArrayList<String> arrmeter = new ArrayList<>();
    ArrayList<String> arrmessage = new ArrayList<>();
    private Context context;

    //private static final String URL_ALLHW ="http://192.168.254.113/laundress/allhandwasherpost.php";
    private static final String URL_ALLHW ="http://192.168.254.117/laundress/allhandwasherpost.php";

    ArrayList<ClientPostList> clientPostLists = new ArrayList<ClientPostList>();
    ClientPostAdapter clientPostAdapter;

    // newInstance constructor for creating fragment with arguments
    public static ClientPost newInstance(int client_id, String client_name) {
        ClientPost clientPost = new ClientPost();
        Bundle args = new Bundle();
        args.putInt("client_id", client_id);
        args.putString("client_name", client_name);
        clientPost.setArguments(args);
        return clientPost;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_post, container, false);
        listView = rootView.findViewById(R.id.lvpost);
        context = getActivity();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALLHW,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            getJsonResponse(response);
                            System.out.println("RESPONSEesponse");
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
        catch (Exception e)
        {
            Toast.makeText(getActivity(), "error" +e.toString(), Toast.LENGTH_SHORT).show();
        }
        /*clientPostAdapter = new ClientPostAdapter(context,clientPostLists);
        listView.setAdapter(clientPostAdapter);*/

        /*Button post = rootView.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("client_name",client_name);
                extras.putInt("client_id", client_id);
                Intent intent = new Intent(getActivity(), ClientMakePost.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });*/
        return rootView;
    }

    private void getJsonResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray2 = jsonObject.getJSONArray("allhandwasherpost");
            if (success.equals("1")){
                for (int i =0;i<jsonArray2.length();i++)
                {   String address;
                    String poster_name=jsonArray2.getJSONObject(i).getString("poster_name").toString();
                    String post_message = jsonArray2.getJSONObject(i).getString("post_message").toString();
                    String handwasher_Photo = jsonArray2.getJSONObject(i).getString("handwasher_Photo").toString();
                    String handwasher_Contact = jsonArray2.getJSONObject(i).getString("handwasher_Contact").toString();
                    //int post_datetime = Integer.parseInt(jsonArray2.getJSONObject(i).getString("post_datetime").toString());;
                   /* if(post_datetime >= 60)
                    {
                        time = post_datetime / 60;
                    } else if(post_datetime < 60) {
                        time = post_datetime;
                    }
                    float hour = post_datetime / 60;*/
                    String post_showAddress = jsonArray2.getJSONObject(i).getString("post_showAddress").toString();
                    if(post_showAddress.equals("Yes")){
                        address = jsonArray2.getJSONObject(i).getString("address").toString();
                    } else {
                        address =" ";
                    }
                    arrname.add(poster_name);
                    arrmeter.add(post_showAddress);
                    arrmessage.add(post_message);
                    //arrtime.add(post_datetime);
                    ClientPostList clientPostList = new ClientPostList();
                    clientPostList.setPost_message(post_message);
                    //clientPostList.setPost_datetime((int) time);
                    clientPostList.setContact(handwasher_Contact);
                    clientPostList.setImage(handwasher_Photo);
                    clientPostList.setPost_name(poster_name);
                    clientPostList.setPost_showAddress(address);
                    clientPostLists.add(clientPostList);
                }
                clientPostAdapter = new ClientPostAdapter(context,clientPostLists);
                listView.setAdapter(clientPostAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
