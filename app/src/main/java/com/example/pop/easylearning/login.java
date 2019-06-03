package com.example.pop.easylearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class login extends Activity {



    private EditText editTextName;
    private EditText editpass;
    private Button login;
    private Button register;
    private  String name,pass;
    private  String key_name="name";
    private  String key_pass="password";
    private static final String url = "http://192.168.0.104/login.php";
    private  int counter=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextName = (EditText) findViewById(R.id.nameedit);
        editpass = (EditText) findViewById(R.id.passedit);
        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
        login = (Button) findViewById(R.id.donereg);
        register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            /*    name = editTextName.getText().toString().trim().toLowerCase();
                pass = editpass.getText().toString().trim().toLowerCase();
               login();*/
Intent i=new Intent(login.this,OC.class);
               startActivity(i);

            }
        });
    }

    private void login()
    {
        final ProgressDialog pr=ProgressDialog.show(this, "pleasWait", "Loading", false, false);
        final StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {

            // response from server when connection request success
            @Override
            public void onResponse(String s) {
                //to progressdialog disappear
                pr.dismiss();



                if(s.equalsIgnoreCase("success")) {
                    Intent i = new Intent(login.this, OC.class);

                    startActivity(i);
                }
                else

                {    Toast.makeText(login.this, s, Toast.LENGTH_LONG).show();
                    counter--;

                    if(counter==0)
                        login.setEnabled(false);}



            }


        },

            // response when faild connection
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pr.dismiss();
                        Toast.makeText(login.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();

                    }


                }     ) {

                // to send parameter with request
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap map = new HashMap();


                            map.put(key_name, name);
                            map.put(key_pass, pass);

                            return map;
                        }
                    };

                    // to request done
                    RequestQueue requestQueue= Volley.newRequestQueue(this);

                    requestQueue.add(req);
                }
    }



























































