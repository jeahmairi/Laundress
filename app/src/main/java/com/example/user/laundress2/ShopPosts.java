package com.example.user.laundress2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ShopPosts extends Fragment {

    ArrayList<String> arrName = new ArrayList<>();
    ArrayList<String> arrMeters = new ArrayList<>();
    ArrayList<String> arrPostDate = new ArrayList<>();
    ArrayList<String> arrMessage = new ArrayList<>();
    ArrayList<String> arrContact = new ArrayList<>();
    private Context context;
    ListView listView;
    private static final String URL_ALL = "http://192.168.254.113/laundress/shop_posts.php";
    ArrayList<ShopPostsList> shopPostsLists = new ArrayList<>();
    ShopPostsAdapter shopPostsAdapter;
    private RequestQueue requestQueue;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.shop_post, container, false);
        listView = rootView.findViewById(R.id.lv_post);
        shopPostsAdapter = new ShopPostsAdapter(context,shopPostsLists);
        listView.setAdapter(shopPostsAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        requestQueue = Volley.newRequestQueue(getContext());

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
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

            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {

        }

    }
    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("shopPosts");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String name = jsonArray.getJSONObject(i).getString("postName");
                    String meters = jsonArray.getJSONObject(i).getString("postMeters");
                    String postDate = jsonArray.getJSONObject(i).getString("postDate");
                    String message = jsonArray.getJSONObject(i).getString("postMessage");
                    String contact = jsonArray.getJSONObject(i).getString("postContact");
                    arrName.add(name);
                    arrMeters.add(meters);
                    arrMessage.add(message);
                    arrPostDate.add(postDate);
                    arrContact.add(contact);
                    ShopPostsList shopPostsList = new ShopPostsList();
                    shopPostsList.setName(name);
                    shopPostsList.setMeters(meters);
                    shopPostsList.setMessage(message);
                    shopPostsList.setPostDate(postDate);
                    shopPostsList.setContact(contact);
                    shopPostsLists.add(shopPostsList);
                }
                shopPostsAdapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}