package com.example.user.laundress2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
     /*private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.113/laundress/addlaundrydetails.php";
    private static final String URL_ALL ="http://192.168.254.113/laundress/alllaundrydetails.php";
    private static final String URL_DELETE ="http://192.168.254.113/laundress/deletelaundrydetails.php";
    private static final String URL_UPDATE ="http://192.168.254.113/laundress/updatelaundrydetails.php";*/

    private static final String URl_ADD_LAUNDRY_DETAILS ="http://192.168.254.117/laundress/addlaundrydetails.php";
    private static final String URL_ALL ="http://192.168.254.117/laundress/alllaundrydetails.php";
    private static final String URL_DELETE ="http://192.168.254.117/laundress/deletelaundrydetails.php";
    private static final String URL_UPDATE ="http://192.168.254.117/laundress/updatelaundrydetails.php";
    ArrayList<AddLaundryDetailList> addLaundryDetailLists = new ArrayList<AddLaundryDetailList>();
    AddLaundryDetailsAdapter addLaundryDetailsAdapter;
    Button btnaddclientinvent;
    ListView lvallclientinv;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Uri fileUri;
    String picturePath;
    Uri selectedImage;
    String itemtaga, itembranda, itemcolora, itemdescriptiona;
    int itemnoofpiecesa;
    Bitmap bitmap;
    ImageView photo;
    private String category_Name, client_Name;
    private int categ_id, client_id, pos;
    String itemclr, itembrnd, itemtg;
    private Bitmap thumbnail;
    private String description;
    private String itemdesc;
    private int itemnoofpcs;

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
        final EditText itemdescription= dialogView.findViewById(R.id.itemdescription);
        final EditText itemnoofpieces= dialogView.findViewById(R.id.itemnoofpieces);
        photo= dialogView.findViewById(R.id.photo);
        final Button btnupdate = dialogView.findViewById(R.id.btnupdate);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        itemdescription.setText(addLaundryDetailLists.get(pos).getItemDescription());
        itemnoofpieces.setText("" +addLaundryDetailLists.get(pos).getItemNoofPieces());
        Picasso.get().load(addLaundryDetailLists.get(pos).getPhoto()).into(photo, new Callback() {
            @Override
            public void onSuccess() {
                thumbnail = ((BitmapDrawable)photo.getDrawable()).getBitmap();
            }

            @Override
            public void onError(Exception e) {

            }
        });
        //new DownloadImageTask(photo).execute(addLaundryDetailLists.get(pos).getPhoto());
        dialogBuilder.setTitle(category_Name);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemdesc = itemdescription.getText().toString();
                itemnoofpcs = Integer.parseInt(itemnoofpieces.getText().toString());

               // updateLaundryInventory();
                updateLaundryInventory();

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void updateLaundryInventory() {
        final int cinv_no = addLaundryDetailLists.get(pos).getCinv_no();
        final String photos =imageToString(thumbnail);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(AddLaundryDetails.this, "Updated Successfully ", Toast.LENGTH_SHORT).show();
                                AddLaundryDetails.this.recreate();
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
                params.put("photo", photos);
                params.put("itemDescription", itemdesc);
                params.put("itemNoofPieces", String.valueOf(itemnoofpcs));
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
        photo = dialogView.findViewById(R.id.photo);
        final EditText itemdescription= dialogView.findViewById(R.id.itemdescription);
        final EditText itemnoofpieces= dialogView.findViewById(R.id.itemnoofpieces);
        final Button btnadd = dialogView.findViewById(R.id.btnadd);
        dialogBuilder.setTitle(category_Name);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description = itemdescription.getText().toString();
                itemnoofpiecesa = Integer.parseInt(itemnoofpieces.getText().toString());
                //addInputtedLaundryDetails(itemtaga, itembranda, itemcolora, itemdescriptiona, itemnoofpiecesa);
                addInputtedLaundryDetails();
                AddLaundryDetails.this.recreate();
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddLaundryDetails.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(AddLaundryDetails.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        photo.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        thumbnail=null;
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        photo.setImageBitmap(thumbnail);
    }
    /*static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }*/
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
                                    String cinv_itemDescription=jsonArray.getJSONObject(i).getString("cinv_itemDescription").toString();
                                    String cinv_Photo=jsonArray.getJSONObject(i).getString("cinv_Photo").toString();
                                    int cinv_noOfPieces= Integer.parseInt(jsonArray.getJSONObject(i).getString("cinv_noOfPieces"));

                                    //Toast.makeText(AddLaundryDetails.this, "cinv_itemTag: " +cinv_itemTag+ "cinv_itemBrand: "+cinv_itemBrand+ "cinv_itemColor: " +cinv_itemColor+"cinv_noOfPieces: " +cinv_noOfPieces, Toast.LENGTH_SHORT).show();

                                    AddLaundryDetailList addLaundryDetailList = new AddLaundryDetailList();
                                    addLaundryDetailList.setItemDescription(cinv_itemDescription);
                                    addLaundryDetailList.setItemNoofPieces(cinv_noOfPieces);
                                    addLaundryDetailList.setPhoto(cinv_Photo);
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

    private void addInputtedLaundryDetails() {
        final String photoe = imageToString(thumbnail);
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
                                AddLaundryDetails.this.recreate();
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
                params.put("client_Name", client_Name);
                params.put("itemDescription", description);
                params.put("picture", photoe);
                params.put("itemNoofPieces", String.valueOf(itemnoofpiecesa));
                params.put("categ_id", String.valueOf(categ_id));
                params.put("client_id", String.valueOf(client_id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }
}
