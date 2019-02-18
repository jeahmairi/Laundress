package com.example.user.laundress2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddLaundryDetails extends AppCompatActivity {
    ArrayList<String> arritemtag = new ArrayList<>();
    ArrayList<String> arritembrand = new ArrayList<>();
    ArrayList<String> arritemcolor = new ArrayList<>();
    ArrayList<String> arritemdescription= new ArrayList<>();
    ArrayList<Integer> arritemnoofpieces = new ArrayList<>();
    ArrayList<Integer> arrclientid = new ArrayList<>();
    ArrayList<Integer> arrcategid = new ArrayList<>();
    ArrayList<Integer> arrcinvno = new ArrayList<>();
    private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.113/laundress/addlaundrydetails.php";
    private static final String URL_ALL ="http://192.168.254.113/laundress/alllaundrydetails.php";
    private static final String URL_DELETE ="http://192.168.254.113/laundress/deletelaundrydetails.php";
    private static final String URL_UPDATE ="http://192.168.254.113/laundress/updatelaundrydetails.php";

    /*private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.117/laundress/addlaundrydetails.php";
    private static final String URL_ALL ="http://192.168.254.117/laundress/alllaundrydetails.php";
    private static final String URL_DELETE ="http://192.168.254.117/laundress/deletelaundrydetails.php";
    private static final String URL_UPDATE ="http://1192.168.254.117/laundress/updatelaundrydetails.php";*/
    ArrayList<AddLaundryDetailList> addLaundryDetailLists = new ArrayList<AddLaundryDetailList>();
    AddLaundryDetailsAdapter addLaundryDetailsAdapter;
    Button btnaddclientinvent;
    ListView lvallclientinv;
    String itemtaga, itembranda, itemcolora, itemdescriptiona;
    int itemnoofpiecesa;
    private String category_Name, client_Name;
    private int categ_id, client_id, pos;
    String itemclr, itembrnd, itemtg;
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {

        // Write your code here

        super.onBackPressed();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gridviewmenu, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclientlaundrydet);
        btnaddclientinvent = findViewById(R.id.btnadddetails);
        lvallclientinv = findViewById(R.id.lldetails);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        category_Name =  extras.getString("categ_name");
        categ_id = extras.getInt("categ_id", 0);
        client_id = extras.getInt("client_id", 0);
        client_Name = extras.getString("client_name");
        //Toast.makeText(AddLaundryDetails.this, "categ_id: " +categ_id+ "client_id"+client_id+ "client_name" +client_Name, Toast.LENGTH_SHORT).show();
        allHandwasherServices();

        registerForContextMenu(lvallclientinv);

        btnaddclientinvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLangDialog();
            }
        });

    }

    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.update:
                pos = info.position;
                showChangeLangDialogUpdate();
                return true;
            case R.id.delete:
                pos = info.position;
                showChangeLangDialogDelete();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showChangeLangDialogUpdate() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatelaundryinv, null);
        dialogBuilder.setView(dialogView);
        final Spinner itemtag = dialogView.findViewById(R.id.itemtag);
        final Spinner itembrand = dialogView.findViewById(R.id.itembrand);
        final Spinner itemcolor = dialogView.findViewById(R.id.itemcolor);
        final EditText itemdescription= dialogView.findViewById(R.id.itemdescription);
        final EditText itemnoofpieces= dialogView.findViewById(R.id.itemnoofpieces);
        final Button btnupdate = dialogView.findViewById(R.id.btnupdate);

        for(int i= 0; i < itemtag.getAdapter().getCount(); i++)
        {
            if(itemtag.getAdapter().getItem(i).toString().contains(addLaundryDetailLists.get(pos).getItemTag()))
            {
                itemtag.setSelection(i);
            }
        }
        for(int i= 0; i < itembrand.getAdapter().getCount(); i++)
        {
            if(itembrand.getAdapter().getItem(i).toString().contains(addLaundryDetailLists.get(pos).getItemBrand()))
            {
                itembrand.setSelection(i);
            }
        }
        for(int i= 0; i < itemcolor.getAdapter().getCount(); i++)
        {
            if(itemcolor.getAdapter().getItem(i).toString().contains(addLaundryDetailLists.get(pos).getItemColor()))
            {
                itemcolor.setSelection(i);
            }
        }
        itemtag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemtg = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itembrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itembrnd = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itemcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               itemclr = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        itemdescription.setText(addLaundryDetailLists.get(pos).getItemDescription());
        itemnoofpieces.setText("" +addLaundryDetailLists.get(pos).getItemNoofPieces());
        dialogBuilder.setTitle(category_Name);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemdesc = itemdescription.getText().toString();
                int itemnoofpcs = Integer.parseInt(itemnoofpieces.getText().toString());
               // updateLaundryInventory();
                updateLaundryInventory(itemtg, itembrnd, itemclr, itemdesc, itemnoofpcs);
                AddLaundryDetails.this.recreate();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateLaundryInventory(final String itemtagas, final String itembrandas, final String itemcoloras, final String itemdescriptionas, final int itemnoofpiecesas) {
        final int cinv_no = addLaundryDetailLists.get(pos).getCinv_no();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(AddLaundryDetails.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();

                                /*Intent intent = new Intent(context, Login.class);
                                startActivity(intent);*/
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(AddLaundryDetails.this, "Update Failed " + e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddLaundryDetails.this, "Update Failed " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("itemTag", itemtagas);
                params.put("itemBrand", itembrandas);
                params.put("itemColor", itemcoloras);
                params.put("itemDescription", itemdescriptionas);
                params.put("itemNoofPieces", String.valueOf(itemnoofpiecesas));
                params.put("cinv_no", String.valueOf(cinv_no));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void showChangeLangDialogDelete() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you sure you want to delete item?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteLaundryInventory();
                AddLaundryDetails.this.recreate();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void deleteLaundryInventory() {
        final int cinv_no = addLaundryDetailLists.get(pos).getCinv_no();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(AddLaundryDetails.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddLaundryDetails.this, "Delete failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddLaundryDetails.this, "Delete failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("cinv_no", String.valueOf(cinv_no));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.addlaundryinv, null);
        dialogBuilder.setView(dialogView);
        final Spinner itemtag = dialogView.findViewById(R.id.itemtag);
        final Spinner itembrand = dialogView.findViewById(R.id.itembrand);
        final Spinner itemcolor = dialogView.findViewById(R.id.itemcolor);
        final EditText itemdescription= dialogView.findViewById(R.id.itemdescription);
        final EditText itemnoofpieces= dialogView.findViewById(R.id.itemnoofpieces);
        final Button btnadd = dialogView.findViewById(R.id.btnadd);
        itemtag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemtaga = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itembrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itembranda = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        itemcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemcolora = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialogBuilder.setTitle(category_Name);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemdescriptiona = itemdescription.getText().toString();
                itemnoofpiecesa = Integer.parseInt(itemnoofpieces.getText().toString());
                addInputtedLaundryDetails(itemtaga, itembranda, itemcolora, itemdescriptiona, itemnoofpiecesa);
                AddLaundryDetails.this.recreate();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    private void allHandwasherServices() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("alllaundrydetails");
                            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int cinv_id = Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_id"));
                                    String cinv_itemTag=jsonArray.getJSONObject(i).getString("cinv_itemTag").toString();
                                    String cinv_itemBrand=jsonArray.getJSONObject(i).getString("cinv_itemBrand").toString();
                                    String cinv_itemColor=jsonArray.getJSONObject(i).getString("cinv_itemColor").toString();
                                    String cinv_itemDescription=jsonArray.getJSONObject(i).getString("cinv_itemDescription").toString();
                                    int cinv_noOfPieces= Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_noOfPieces"));

                                    //Toast.makeText(AddLaundryDetails.this, "cinv_itemTag: " +cinv_itemTag+ "cinv_itemBrand: "+cinv_itemBrand+ "cinv_itemColor: " +cinv_itemColor+"cinv_noOfPieces: " +cinv_noOfPieces, Toast.LENGTH_SHORT).show();

                                    arritemtag.add(cinv_itemTag);
                                    arritembrand.add(cinv_itemBrand);
                                    arritemcolor.add(cinv_itemColor);
                                    arritemdescription.add(cinv_itemDescription);
                                    arritemnoofpieces.add(cinv_noOfPieces);
                                    arrclientid.add(client_id);
                                    arrcategid.add(categ_id);
                                    arrcinvno.add(cinv_id);

                                    AddLaundryDetailList addLaundryDetailList = new AddLaundryDetailList();
                                    addLaundryDetailList.setItemTag(cinv_itemTag);
                                    addLaundryDetailList.setItemBrand(cinv_itemBrand);
                                    addLaundryDetailList.setItemColor(cinv_itemColor);
                                    addLaundryDetailList.setItemDescription(cinv_itemDescription);
                                    addLaundryDetailList.setItemNoofPieces(cinv_noOfPieces);
                                    addLaundryDetailList.setClientId(client_id);
                                    addLaundryDetailList.setCategoryId(categ_id);
                                    addLaundryDetailList.setCinv_no(cinv_id);

                                    addLaundryDetailLists.add(addLaundryDetailList);

                                }
                                addLaundryDetailsAdapter = new AddLaundryDetailsAdapter(AddLaundryDetails.this,addLaundryDetailLists);
                                lvallclientinv.setAdapter(addLaundryDetailsAdapter);

                            } else if(success.equals("0")) {
                                Toast.makeText(AddLaundryDetails.this, "No Data " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddLaundryDetails.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddLaundryDetails.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                params.put("categ_id", String.valueOf(categ_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addInputtedLaundryDetails(final String itemTag, final String itemBrand, final String itemColor, final String itemDescription, final int itemNoofPieces) {
        final Context context = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URl_ADD_LAUNDRY_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(AddLaundryDetails.this, "Added Successfully ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();;
                            Toast.makeText(AddLaundryDetails.this, "Add Laundry Details Failed " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddLaundryDetails.this, "Add Failed " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("itemTag", itemTag);
                params.put("itemBrand", itemBrand);
                params.put("itemColor", itemColor);
                params.put("itemDescription", itemDescription);
                params.put("itemNoofPieces", String.valueOf(itemNoofPieces));
                params.put("categ_id", String.valueOf(categ_id));
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}
