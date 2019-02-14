package com.example.user.laundress2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
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

public class HandwasherMyLaundry extends Fragment {
    private String handwasher_name, comments;
    private int handwasher_id;
    private int handwasher_lspid, trans_No, client_ID;
    TextView section_label, section_label2, aname, number, address, textView31, datetime, statusname, timeleft, time, timename;
    ImageView picture, bgtime;
    Button finish;
    String trans_Status;
    float rating;
    private static final String URL_ALL ="http://192.168.254.117/laundress/allbookinghandwasher.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updatetransactionfinish.php";
    private static String URL_ADDPOST = "http://192.168.254.117/laundress/addrateclient.php";
    public static HandwasherMyLaundry newInstance(int handwasher_id, String handwasher_name, int handwasher_lspid) {
        HandwasherMyLaundry handwasherMyLaundry = new HandwasherMyLaundry();
        Bundle args = new Bundle();
        args.putInt("handwasher_id", handwasher_id);
        args.putInt("handwasher_lspid", handwasher_lspid);
        args.putString("handwasher_name", handwasher_name);
        handwasherMyLaundry.setArguments(args);
        return handwasherMyLaundry;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handwasher_id = getArguments().getInt("handwasher_id", 0);
        handwasher_lspid = getArguments().getInt("handwasher_lspid", 0);
        handwasher_name = getArguments().getString("handwasher_name");
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.handwasher_mylaundry, container, false);
        section_label = rootView.findViewById(R.id.sectionlabel1);
        section_label2 = rootView.findViewById(R.id.sectionlabel2);
        picture = rootView.findViewById(R.id.picture);
        bgtime = rootView.findViewById(R.id.bgtime);
        aname = rootView.findViewById(R.id.name);
        address = rootView.findViewById(R.id.address);
        number = rootView.findViewById(R.id.number);
        textView31 = rootView.findViewById(R.id.textView31);
        time = rootView.findViewById(R.id.time);
        datetime = rootView.findViewById(R.id.datetime);
        finish = rootView.findViewById(R.id.finish);
        allbooking();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateTransactionFinish();
                showChangeLangDialog();
            }
        });

        return rootView;
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rate, null);
        final RatingBar rate = dialogView.findViewById(R.id.ratings);
        final EditText comment = dialogView.findViewById(R.id.comment);
        rate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rate.setRating(rating);
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                rating = rate.getRating();
                comments = comment.getText().toString().trim();
                addRating(client_ID, handwasher_lspid, trans_No, rating, comments);
               // Toast.makeText(getActivity(), "rating " + rating+" comments "+comments+ " client_ID "+client_ID+ " handwasher_lspid "+handwasher_lspid + " trans_No "+trans_No , Toast.LENGTH_LONG).show();
/*                String updatemessage = message.getText().toString().trim();
                String location = showlocation.toString().trim();
                int post_no = clientPostLists.get(pos).getPost_no();
                updatePost(updatemessage, location, post_no);
                ClientMyPost.this.recreate();*/
            }
        });
        dialogBuilder.setNegativeButton("Skip", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                updateTransactionFinish();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void addRating(final int client_ID, final int handwasher_lspid, final int trans_No, final float rating, final String comments) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADDPOST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                updateTransactionFinish();
                                //getActivity().recreate();
                               // Toast.makeText(getActivity(), "Added Successfully ", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(getActivity(), "Add Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Add Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                       /* load.setVisibility(View.GONE);
                        login.setVisibility(View.VISIBLE);*/
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_ID", String.valueOf(HandwasherMyLaundry.this.client_ID));
                params.put("handwasher_lspid", String.valueOf(HandwasherMyLaundry.this.handwasher_lspid));
                params.put("trans_No", String.valueOf(HandwasherMyLaundry.this.trans_No));
                params.put("rating", String.valueOf(HandwasherMyLaundry.this.rating));
                params.put("comments", HandwasherMyLaundry.this.comments);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void updateTransactionFinish() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                // Toast.makeText(ClientM.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().recreate();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("trans_No", String.valueOf(trans_No));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void allbooking(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allbooking");

                            if (success.equals("1")) {
                                //Toast.makeText(getActivity(), "sud" + success, Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String caddress = object.getString("client_Address").trim();
                                    String cnumber = object.getString("client_Contact").trim();
                                    String cdatetime = object.getString("trans_EstDateTime").trim();
                                    String client_Photo = object.getString("client_Photo").trim();
                                    trans_Status = object.getString("trans_Status").trim();
                                    trans_No = Integer.parseInt(object.getString("trans_No"));
                                    client_ID = Integer.parseInt(object.getString("client_ID"));

                                    //Toast.makeText(getActivity(), "sud" + trans_Status, Toast.LENGTH_SHORT).show();
                                    if(trans_Status.equals("Confirmed")){
                                        Picasso.get().load(client_Photo).into(picture);
                                        aname.setText(name);
                                        address.setText(caddress);
                                        number.setText(cnumber);
                                        datetime.setText(cdatetime);
                                        time.setText(trans_Status);
                                        number.setVisibility(View.VISIBLE);
                                        textView31.setVisibility(View.VISIBLE);
                                        bgtime.setVisibility(View.VISIBLE);
                                        picture.setVisibility(View.VISIBLE);
                                        aname.setVisibility(View.VISIBLE);
                                        address.setVisibility(View.VISIBLE);
                                        number.setVisibility(View.VISIBLE);
                                        datetime.setVisibility(View.VISIBLE);
                                        time.setVisibility(View.VISIBLE);
                                        finish.setVisibility(View.VISIBLE);

                                        section_label.setVisibility(View.GONE);
                                        section_label2.setVisibility(View.GONE);
                                    } else {
                                        number.setVisibility(View.GONE);
                                        textView31.setVisibility(View.GONE);
                                        bgtime.setVisibility(View.GONE);
                                        picture.setVisibility(View.GONE);
                                        aname.setVisibility(View.GONE);
                                        address.setVisibility(View.GONE);
                                        number.setVisibility(View.GONE);
                                        datetime.setVisibility(View.GONE);
                                        time.setVisibility(View.GONE);
                                        finish.setVisibility(View.GONE);
                                        //Toast.makeText(getActivity(), "sud" + trans_Status, Toast.LENGTH_SHORT).show();
                                        section_label.setVisibility(View.VISIBLE);
                                        section_label2.setVisibility(View.VISIBLE);
                                    }

                                }
                            } else if (success.equals("0")) {
                                Toast.makeText(getActivity(), "failed: ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Login failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("handwasher_lspid", String.valueOf(handwasher_lspid));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }
}
