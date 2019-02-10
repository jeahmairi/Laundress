package com.example.user.laundress2;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FindLaundryShop extends Fragment{
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<String> arrmeter = new ArrayList<>();
    ArrayList<String> arrcontact = new ArrayList<>();
    ArrayList<String> arropenhour = new ArrayList<>();
    ArrayList<String> arrclosehour = new ArrayList<>();
    ArrayList<Integer> arrid = new ArrayList<>();
    ArrayList<Integer> arrlspid = new ArrayList<>();
    private Context context;
    private static final String URL_ALL ="http://192.168.254.117/laundress/alllaundryshop.php";
    private static final String URL_ALL_CLIENT ="http://192.168.254.117/laundress/client.php";
    private static final String URL_ALL_CHEAPEST="http://192.168.254.117/laundress/alllaundryshopcheap.php";
    private static final String URL_ALL_RECOMMENDED="http://192.168.254.117/laundress/alllaundryshoprecom.php";
    //private static final String URL_ALL ="http://192.168.1.12/laundress/alllaundryshop.php";
    // private static final String URL_ALL ="http://192.168.254.100/laundress/alllaundryshop.php";
    // private static final String URL_ALL ="http://192.168.1.2/laundress/alllaundryshop.php";
    ArrayList<LaundryShopList> laundryShopLists = new ArrayList<LaundryShopList>();
    LaundryShopAdapter laundryShopAdapter;
    private String client_name;
    String client_Address;
    ListView listView;
    private int client_id;
    ListView lvcheap, lvrecommended;
    Spinner spinner;
    private String filterby;
    double lat;
    double lng;
    double lat2;
    double lng2;

    // newInstance constructor for creating fragment with arguments
    public static FindLaundryShop newInstance(int client_id, String client_name) {
        FindLaundryShop findLaundryShop = new FindLaundryShop();
        Bundle args = new Bundle();
        args.putInt("client_id", client_id);
        args.putString("client_name", client_name);
        findLaundryShop.setArguments(args);
        return findLaundryShop;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client_id = getArguments().getInt("client_id", 0);
        client_name = getArguments().getString("client_name");
    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.findlaundryshop, container, false);
         listView = rootView.findViewById(R.id.lvlaundryshop);
        lvcheap = rootView.findViewById(R.id.lvcheap);
        lvrecommended = rootView.findViewById(R.id.lvrecommended);
        spinner = rootView.findViewById(R.id.spinner);
        Toast.makeText(getContext(),"Name MyLaundry" +client_name+ "ID " +client_id, Toast.LENGTH_SHORT).show();
        context = getActivity();
        allClient();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterby = parent.getItemAtPosition(position).toString();
                if(filterby.equals("Nearest Location")){
                    listView.setVisibility(View.VISIBLE);
                    allShop();
                } else if(filterby.equals("Cheapest Laundry Service")){
                    listView.setVisibility(View.GONE);
                    lvcheap.setVisibility(View.VISIBLE);
                    allShopCheapest();
                } else
                if(filterby.equals("Recommended Laundry Shop")){
                    listView.setVisibility(View.GONE);
                    lvcheap.setVisibility(View.GONE);
                    lvrecommended.setVisibility(View.VISIBLE);
                    allShopRecommended();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }
    private void allClient() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_CLIENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("client");
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    client_Address=jsonArray.getJSONObject(i).getString("client_Address").toString();
                                    Toast.makeText(getActivity(), "client_Address " +client_Address, Toast.LENGTH_SHORT).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    private void allShop() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("alllaundryshop");
                            if (success.equals("1")){
                                laundryShopLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id").toString());
                                    int lspid = Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    String openhours = jsonArray.getJSONObject(i).getString("openhours").toString();
                                    String closehours = jsonArray.getJSONObject(i).getString("closehours").toString();
                                    arrid.add(id);
                                    arrlspid.add(lspid);
                                    arrname.add(name);
                                    arrmeter.add(meter);
                                    arrcontact.add(contact);
                                    arropenhour.add(openhours);
                                    arrclosehour.add(closehours);
                                    LaundryShopList laundryShopList = new LaundryShopList();
                                    laundryShopList.setClient_id(client_id);
                                    laundryShopList.setId(id);
                                    laundryShopList.setLsp_id(lspid);
                                    laundryShopList.setName(name);
                                    laundryShopList.setClient_name(client_name);
                                    laundryShopList.setLocation(meter);
                                    laundryShopList.setContact(contact);
                                    laundryShopList.setOpenhours(openhours);
                                    laundryShopList.setClosehours(closehours);
                                    getLocationToAddress(meter);
                                    laundryShopList.setMeter(getDistance(lat, lng, lat2, lng2));
                                    laundryShopLists.add(laundryShopList);
                                }
                                laundryShopAdapter = new LaundryShopAdapter(context,laundryShopLists);
                                listView.setAdapter(laundryShopAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
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

    private void allShopCheapest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_CHEAPEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("alllaundryshop");
                            if (success.equals("1")){
                                laundryShopLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id").toString());
                                    int lspid = Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    String openhours = jsonArray.getJSONObject(i).getString("openhours").toString();
                                    String closehours = jsonArray.getJSONObject(i).getString("closehours").toString();
                                    arrid.add(id);
                                    arrlspid.add(lspid);
                                    arrname.add(name);
                                    arrmeter.add(meter);
                                    arrcontact.add(contact);
                                    arropenhour.add(openhours);
                                    arrclosehour.add(closehours);
                                    LaundryShopList laundryShopList = new LaundryShopList();
                                    laundryShopList.setClient_id(client_id);
                                    laundryShopList.setId(id);
                                    laundryShopList.setLsp_id(lspid);
                                    laundryShopList.setName(name);
                                    laundryShopList.setClient_name(client_name);
                                    laundryShopList.setLocation(meter);
                                    laundryShopList.setContact(contact);
                                    laundryShopList.setOpenhours(openhours);
                                    laundryShopList.setClosehours(closehours);
                                    getLocationToAddress(meter);
                                    laundryShopList.setMeter(getDistance(lat, lng, lat2, lng2));
                                    laundryShopLists.add(laundryShopList);
                                }
                                laundryShopAdapter = new LaundryShopAdapter(context,laundryShopLists);
                                lvcheap.setAdapter(laundryShopAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
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

    private void allShopRecommended() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_RECOMMENDED,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            //JSONArray jArray = json.getJSONArray("platform");
                            //JSONArray jsonArray = new JSONArray(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("alllaundryshop");
                            if (success.equals("1")){
                                laundryShopLists.clear();
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int id = Integer.parseInt(jsonArray.getJSONObject(i).getString("id").toString());
                                    int lspid = Integer.parseInt(jsonArray.getJSONObject(i).getString("lsp_ID").toString());
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    String meter = jsonArray.getJSONObject(i).getString("address").toString();
                                    String contact = jsonArray.getJSONObject(i).getString("contact").toString();
                                    String openhours = jsonArray.getJSONObject(i).getString("openhours").toString();
                                    String closehours = jsonArray.getJSONObject(i).getString("closehours").toString();
                                    arrid.add(id);
                                    arrlspid.add(lspid);
                                    arrname.add(name);
                                    arrmeter.add(meter);
                                    arrcontact.add(contact);
                                    arropenhour.add(openhours);
                                    arrclosehour.add(closehours);
                                    LaundryShopList laundryShopList = new LaundryShopList();
                                    laundryShopList.setClient_id(client_id);
                                    laundryShopList.setId(id);
                                    laundryShopList.setLsp_id(lspid);
                                    laundryShopList.setName(name);
                                    laundryShopList.setClient_name(client_name);
                                    laundryShopList.setLocation(meter);
                                    laundryShopList.setContact(contact);
                                    laundryShopList.setOpenhours(openhours);
                                    laundryShopList.setClosehours(closehours);
                                    getLocationToAddress(meter);
                                    laundryShopList.setMeter(getDistance(lat, lng, lat2, lng2));
                                    laundryShopLists.add(laundryShopList);
                                }
                                laundryShopAdapter = new LaundryShopAdapter(context,laundryShopLists);
                                lvrecommended.setAdapter(laundryShopAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
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

    public static String getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();
        return String.format("%.2f", new Float(distance * kmConvertion).floatValue()) + " km";
        // return String.format("%.2f", distance)+" m";
    }

    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat = location.getLatitude();
            lng = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }

    public String getLocationToAddress(String strAddress) {

        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            lat2 = location.getLatitude();
            lng2 = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }

}
