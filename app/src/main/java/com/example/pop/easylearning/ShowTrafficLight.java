package com.example.pop.easylearning;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ShowTrafficLight extends Activity{
     private TextView txt;
     public static final   String url="http://192.168.0.104/trafficResult.php";
private String email="esraa";
    @Override
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_traffic_light);
        this.getActionBar().setTitle("Result");
        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
        txt = (TextView) findViewById(R.id.txt);



            final StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    txt.setText(response);

                }

            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                            Toast.makeText(ShowTrafficLight.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });




            Volley.newRequestQueue(this).add(req);




    }}




