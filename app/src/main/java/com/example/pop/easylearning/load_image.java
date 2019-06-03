package com.example.pop.easylearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class load_image extends Activity  {

    public static final String DATA_URL = "http://192.168.0.104/getimg.php";

    //Tag values to read from json

    public static final String TAG_image_URL = "image";
    public static final String key_mail="email";
    private GridView gridView;

    //Array for Storing image urls


     private String [] images =new String[200];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_image);
        gridView = (GridView) findViewById(R.id.gridView);
        getData();


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //  to Send image position that clicked  to view_stu_screenshot Activity
                Intent i = new Intent(load_image.this, view_stu_screenshot.class);

                Bundle b=new Bundle();
                b.putStringArray("array",images);
                i.putExtras(b);
                b.putInt("position",position);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }






    private void getData() {
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...", "Fetching data...", false, false);

        //Creating a json array request to get the json from our api
        JsonArrayRequest request = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();
                        showGrid(response);



                    }   },



                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(load_image.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(request);

    }

    private void showGrid(JSONArray jsonArray) {
        //Looping through all the elements of json array




        for (int i = 0; i < jsonArray.length(); i++) {
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url
                //images.add(obj.getString(TAG_image_URL));
                String url =obj.getString(TAG_image_URL);
                images[i]=url;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, images);
        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);



    }




}