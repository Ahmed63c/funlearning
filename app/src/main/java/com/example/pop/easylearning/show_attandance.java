package com.example.pop.easylearning;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class show_attandance extends Activity{
   private   ListView l;
    private  String email="esraa";
   private ArrayList<String> posts;
   public static final String url="http://192.168.0.104/showatt.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_show_attandance);
        l = (ListView) findViewById(R.id.list);

         this.getActionBar().setTitle("StudentAttendance");
        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
       //to set divider height between items in listview
        l.setDividerHeight(5);


        StringRequest req=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("name");
                     posts = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject currentobject = array.getJSONObject(i);
                        String name = currentobject.getString("name");


                        posts.add(name);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter adapter = new ArrayAdapter<String>(show_attandance.this, android.R.layout.simple_list_item_1,posts);
                l.setAdapter(adapter);}







            },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Toast.makeText(show_attandance.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }









        ) ;
        Volley.newRequestQueue(this).add(req);


    }}