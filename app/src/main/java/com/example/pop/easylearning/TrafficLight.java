package com.example.pop.easylearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;


public class TrafficLight extends Activity implements View.OnClickListener {

    private ImageButton red, yellow, green;
     private String mail;
    public static final String SendUrl = "http://192.168.0.104/traffic.php";
    public static final String KEY_color = "color", KEY_mail = "email";
    public static final String SubUrl = "http://192.168.0.104/sub.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_light);

        SharedPreferences sharedPreferences =getSharedPreferences("myprefs", 0);

        mail=sharedPreferences.getString("m",null);

        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));


      //  mail = getIntent().getExtras().getString("mail");


        red = (ImageButton) findViewById(R.id.red);
        yellow = (ImageButton) findViewById(R.id.yellow);
        green = (ImageButton) findViewById(R.id.green);
        red.setOnClickListener(this);
        yellow.setOnClickListener(this);
        green.setOnClickListener(this);
        red.setEnabled(false);
        green.setEnabled(false);
        yellow.setEnabled(false);


        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(TrafficLight.this,s,Toast.LENGTH_LONG).show();

                        if (s.equals("dis")) {
                            red.setEnabled(false);
                            yellow.setEnabled(false);
                            green.setEnabled(false);


                        } else if (s.equals("enable")) {
                            red.setEnabled(true);
                            yellow.setEnabled(true);
                            green.setEnabled(true);


                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(TrafficLight.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }



    public void send( final String color) {


            final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);


            StringRequest stringRequest = new StringRequest(Request.Method.POST, SendUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            //Disimissing the progress dialog
                            loading.dismiss();
                            //Showing toast message of the response
                            Toast.makeText(TrafficLight.this, s, Toast.LENGTH_LONG).show();



                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            //Dismissing the progress dialog
                            loading.dismiss();

                            //Showing toast
                            Toast.makeText(TrafficLight.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();

                        }
                    }) {// to send parameter with request


                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    //Creating parameters
                    Map<String, String> params = new Hashtable<String, String>();

                    //Adding parameters
                    params.put(KEY_color, color);
                      // params.put(KEY_mail, mail);

                    //returning parameters
                    return params;
                }


            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            requestQueue.add(stringRequest);
        }




    @Override
    public void onClick(View v) {
        if (v==red)
        {update("red");
    }
        else if(v==green)
        {update("green");

}

    else if(v==yellow) {
            update("yellow");
        }}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_traffic_light, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.refresh:




                Intent i = new Intent(TrafficLight.this, TrafficLight.class);
                startActivity(i);
                finish();
                break;}

            return true;


}



public  void update( String color) {
final String c=color;
    StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {


                    //Showing toast message of the response
                    Toast.makeText(TrafficLight.this, s, Toast.LENGTH_LONG).show();

                  //  if (s.equals("dis")) {

                     //   red.setEnabled(false);
                       // yellow.setEnabled(false);
                       // green.setEnabled(false);


                   // }
                    if (s.equals("enable")) {

           send(c);

                    }
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {



                    //Showing toast
                    Toast.makeText(TrafficLight.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    requestQueue.add(stringRequest);

}
}


