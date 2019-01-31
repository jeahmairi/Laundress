package com.example.user.laundress2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TabHost;
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

public class ClientLaundryDetails extends AppCompatActivity {
    ArrayList<String> arrname = new ArrayList<>();
    ArrayList<Integer> arrid = new ArrayList<>();
    String client_name; int client_id;
    int pos =0;
    GridView androidGridView;
    Button btnaddcategory;
    //  ListView listview;
    private Context context;
    private static final String URL_ALL ="http://192.168.254.117/laundress/detailscategory.php";
    private static final String URL_DELETE ="http://192.168.254.117/laundress/deletecategory.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updatecategory.php";
    //private static final String URL_ALL ="http://192.168.1.12/laundress/detailscategory.php";
    //private static final String URL_ALL ="http://192.168.254.100/laundress/detailscategory.php";
    //private static final String URL_ALL ="http://192.168.1.2/laundress/detailscategory.php";
    ArrayList<LaundryDetailList> laundryDetailLists = new ArrayList<LaundryDetailList>();
    LaundryDetailsAdapter laundryDetailsAdapter;

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
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientlaundrydet);
        btnaddcategory = findViewById(R.id.btnaddcategory);
        androidGridView = findViewById(R.id.gridview);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");

        registerForContextMenu(androidGridView);

/*
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
*/
        btnaddcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                //extras.putString("client_name",client_name);
                extras.putInt("client_id", client_id);
                Intent intent = new Intent(ClientLaundryDetails.this, AddCategoryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle extras = new Bundle();
                extras.putString("categ_name",laundryDetailLists.get(position).getName());
                extras.putInt("categ_id",laundryDetailLists.get(position).getId());
                extras.putInt("client_id",client_id);
                extras.putString("client_name",client_name);

                Intent intent = new Intent(ClientLaundryDetails.this,AddLaundryDetails.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
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
                            Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(ClientLaundryDetails.this);
            requestQueue.add(stringRequest);
        }
        catch (Exception e)
        {

        }
        /*laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this,laundryDetailLists);
        androidGridView.setAdapter(laundryDetailsAdapter);*/
    }
    @Override

    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.update:
                pos = info.position;
                showChangeLangDialog();
                return true;
            case R.id.delete:
                pos = info.position;
                showChangeLangDialogDelete();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void showChangeLangDialogDelete() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        //inal View dialogView = inflater.inflate(R.layout.updatecategory, null);
        //dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Delete");
        dialogBuilder.setMessage("Are you sure you want to delete category?");
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                deleteCategory();
                ClientLaundryDetails.this.recreate();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatecategory, null);
        dialogBuilder.setView(dialogView);

        final EditText name = (EditText) dialogView.findViewById(R.id.edit1);
        name.setText(laundryDetailLists.get(pos).getName());

        dialogBuilder.setTitle("Update Category");
        dialogBuilder.setMessage("Category Name");
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String updatedname = name.getText().toString().trim();
                int categ_id = laundryDetailLists.get(pos).getId();
                if(!updatedname.isEmpty()){
                updateCategory(updatedname, categ_id);
                    ClientLaundryDetails.this.recreate();
                }
                else
                    name.setError("Please put name");
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateCategory(final String name, final int categ_id) {
        //final int categ_id = laundryDetailLists.get(pos).getId();
       // Toast.makeText(ClientLaundryDetails.this, "id and name: "+name+" "+categ_id, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(ClientLaundryDetails.this, "Category Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientLaundryDetails.this, "Category Update failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientLaundryDetails.this, "Category Update failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("category_name", name);
                params.put("categ_id", String.valueOf(categ_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void deleteCategory() {
        final int categ_id = laundryDetailLists.get(pos).getId();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(ClientLaundryDetails.this, "Category Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientLaundryDetails.this, "Category Delete failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientLaundryDetails.this, "Category Delete failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("categ_id", String.valueOf(categ_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getJsonResponse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.getString("success");
            //JSONArray jArray = json.getJSONArray("platform");
            //JSONArray jsonArray = new JSONArray(response);
            JSONArray jsonArray = jsonObject.getJSONArray("category");
            if (success.equals("1")){
                for (int i =0;i<jsonArray.length();i++)
                {
                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                    int id= Integer.parseInt(jsonArray.getJSONObject(i).getString("id").toString());
                    //Toast.makeText(ClientLaundryDetails.this, " " +name, Toast.LENGTH_LONG).show();
                    arrname.add(name);
                    arrid.add(id);
                    LaundryDetailList laundryDetailList = new LaundryDetailList();
                    laundryDetailList.setName(name);
                    laundryDetailList.setId(id);
                    laundryDetailLists.add(laundryDetailList);
                }
                laundryDetailsAdapter = new LaundryDetailsAdapter(ClientLaundryDetails.this,laundryDetailLists);
                androidGridView.setAdapter(laundryDetailsAdapter);

            }
            laundryDetailsAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ClientLaundryDetails.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}