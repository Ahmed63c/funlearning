package com.example.pop.easylearning;

import android.app.Activity;
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

public class register extends Activity {


    private EditText inst1,inst2,inst3;
    private String instName,password,email;
    public static final String key_instName="name";
    public static final  String key_email="email";
    public static final  String key_password="password" ;


     private Button registerMe;
     public static final String registerurl ="http://192.168.0.104/reg.php ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeration);



         // to set backgroundColor of actionbar
        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));




        inst1 = (EditText) findViewById(R.id.name);
        inst2 = (EditText) findViewById(R.id.pass);
        inst3 = (EditText) findViewById(R.id.mail);

        registerMe = (Button) findViewById(R.id.regbtn);

        registerMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {

        instName = inst1.getText().toString().trim().toLowerCase();
        password = inst2.getText().toString().trim().toLowerCase();
        email = inst3.getText().toString().trim().toLowerCase();

        StringRequest strRequest = new StringRequest(Request.Method.POST, registerurl,

                new Response.Listener<String>()

                { @Override
                  public void onResponse(String s) {

                        Toast.makeText(register.this,
                                s, Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(register.this,login.class);

                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(register.this,
                                error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override

            protected Map<String,String> getParams() throws AuthFailureError {

                HashMap map = new HashMap();

                map.put(key_instName,instName);
                map.put(key_password,password);
                map.put(key_email, email);

                return map;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(strRequest);
    }




}
