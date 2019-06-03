package com.example.pop.easylearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class StudentAttendance extends Activity {
    private TextView txt;
    private Button btn;
    private String name1,mail;
   private String address,value;

    public static final String Keyname="name";
   public static final String Keyemail="email";
    public  static final String  SendUrl="http://192.168.0.104/attname.php";
    public static final String SubUrl = "http://192.168.0.104/sub.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

         this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
         this.getActionBar().setTitle("Attendance");
         SharedPreferences sharedPreferences =getSharedPreferences("myprefs", 0);
         name1 = sharedPreferences.getString("n", null);
         mail=sharedPreferences.getString("m", null);

        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
         address = info.getMacAddress();
         value=address+name1;

        txt=(TextView)findViewById(R.id.atttxt);
        txt.setText(address + name1);
        btn=(Button)findViewById(R.id.btn);
        btn.setEnabled(false);
        btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        update(value);
    }
});

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(StudentAttendance.this, s, Toast.LENGTH_LONG).show();

                        if (s.equals("dis")) {
                            btn.setEnabled(false);


                        } else if (s.equals("enable")) {
                           btn.setEnabled(true);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(StudentAttendance.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


private void send(final String name){

    final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);

    StringRequest stringRequest = new StringRequest(Request.Method.POST, SendUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //Disimissing the progress dialog
                    loading.dismiss();
                    //Showing toast message of the response
                    Toast.makeText(StudentAttendance.this, s, Toast.LENGTH_LONG).show();


                }},
                        new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(StudentAttendance.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                })


    {// to send parameter with request

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
       // String name=txt.getText().toString();

        //Creating parameters
        Map<String,String> params = new Hashtable<String, String>();

        //Adding parameters
       params.put(Keyname,name);
      // params.put(Keyemail, mail);



        //returning parameters
        return params;
    }




};
RequestQueue requestQueue= Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);







}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_attendance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.refresh:




                Intent i = new Intent(StudentAttendance.this, StudentAttendance.class);
                startActivity(i);
                finish();
                break;}

        return true;


    }
    public  void update(  final String name) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {


                        //Showing toast message of the response
                        Toast.makeText(StudentAttendance.this, s, Toast.LENGTH_LONG).show();



                        // }
                        if (s.equals("enable")) {

                            send(name);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {



                        //Showing toast
                        Toast.makeText(StudentAttendance.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }

}
