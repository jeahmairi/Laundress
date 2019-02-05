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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Map;

public class ClientMyPost extends AppCompatActivity {
    ListView clientpost;
    MyPostAdapter myPostAdapter;
    ArrayList<ClientPostList> clientPostLists = new ArrayList<ClientPostList>();
    String client_name, showlocation;
    int client_id;
    int pos =0;

    private static final String URL_ALL ="http://192.168.254.117/laundress/allclientmypost.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updateclientmypost.php";
    private static final String URL_DELETE ="http://192.168.254.117/laundress/deleteclientmypost.php";
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gridviewmenu, menu);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypost);
        clientpost = findViewById(R.id.clientpost);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        client_name = extras.getString("client_name");
        client_id = extras.getInt("client_id");
        registerForContextMenu(clientpost);
        allClientPost();
    }

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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.yes:
                if(checked)
                    showlocation = "Yes";
                break;
            case R.id.no:
                if(checked)
                    showlocation = "No";
                break;
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
                int post_no = clientPostLists.get(pos).getPost_no();
                deletePost(post_no);
                //ClientMyPost.this.recreate();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void deletePost(final int post_no) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                ClientMyPost.this.recreate();
                                Toast.makeText(ClientMyPost.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientMyPost.this, "Post Delete failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientMyPost.this, "Post Delete failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("post_no", String.valueOf(post_no));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.updatemypost, null);
        dialogBuilder.setView(dialogView);
        final TextView name = dialogView.findViewById(R.id.name);
        final EditText message = dialogView.findViewById(R.id.message);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.selection) ;

        name.setText(clientPostLists.get(pos).getPost_name());
        message.setText(clientPostLists.get(pos).getPost_message());
        final String show = clientPostLists.get(pos).getPost_showAddress();
        if(show.equals("Yes")) {
            radioGroup.check(R.id.yes);
        } else if(show.equals("No")) {
            radioGroup.check(R.id.no);
        }
        dialogBuilder.setTitle("Update Post");
        dialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String updatemessage = message.getText().toString().trim();
                String location = showlocation.toString().trim();
                int post_no = clientPostLists.get(pos).getPost_no();
                updatePost(updatemessage, location, post_no);
                ClientMyPost.this.recreate();
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

    private void updatePost(final String updatemessage, final String location, final int post_no) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(ClientMyPost.this, "Post Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientMyPost.this, "Post Update failed " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientMyPost.this, "Post Update failed. No connection." +error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("post_message", updatemessage);
                params.put("post_show_location", location);
                params.put("post_no", String.valueOf(post_no));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void allClientPost() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("allclientpost");
                            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            if (success.equals("1")){
                                for (int i =0;i<jsonArray.length();i++)
                                {
                                    int post_no = Integer.parseInt(jsonArray.getJSONObject(i).getString("post_no"));
                                    String post_message=jsonArray.getJSONObject(i).getString("post_message").toString();
                                    String post_datetime=jsonArray.getJSONObject(i).getString("post_datetime").toString();
                                    String post_show_location=jsonArray.getJSONObject(i).getString("post_show_location").toString();
                                    String name=jsonArray.getJSONObject(i).getString("name").toString();
                                    ClientPostList clientPostList = new ClientPostList();
                                    clientPostList.setPost_no(post_no);
                                    clientPostList.setPost_message(post_message);
                                    clientPostList.setPost_name(name);
                                    clientPostList.setPost_showAddress(post_show_location);
                                    clientPostList.setDate(post_datetime);
                                    clientPostLists.add(clientPostList);
                                }
                                myPostAdapter = new MyPostAdapter(ClientMyPost.this,clientPostLists);
                                clientpost.setAdapter(myPostAdapter);
                            } else if(success.equals("0")) {
                                Toast.makeText(ClientMyPost.this, "error " , Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ClientMyPost.this, "failedddd" +e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ClientMyPost.this, "Failed. No Connection. " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
