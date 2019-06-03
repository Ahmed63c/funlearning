package com.example.pop.easylearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class OC extends Activity implements View.OnClickListener {

    private ImageButton ws, lec, TL, ques, slides,att;
   public static final String  en_url="http://192.168.0.104/setEnable.php";
   public static final String  dis_url="http://192.168.0.104/dis.php";


    public  static  final String clearimg="http://192.168.0.104/clearimg.php";
    public  static  final String clearatt="http://192.168.0.104/clearatt.php";
    public  static  final String cleartraffic="http://192.168.0.104/clear.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oc);




        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
        this.getActionBar().setTitle("OnlineClassroom");

        ws = (ImageButton) findViewById(R.id.WhiteSheet);
        lec = (ImageButton) findViewById(R.id.lec);
        att=(ImageButton)findViewById(R.id.Attendance);
        TL = (ImageButton) findViewById(R.id.trafficLight);
        ques = (ImageButton) findViewById(R.id.ques);
        slides = (ImageButton) findViewById(R.id.slide);
        ws.setOnClickListener(this);
        lec.setOnClickListener(this);
        TL.setOnClickListener(this);
        att.setOnClickListener(this);
        ques.setOnClickListener(this);
        slides.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.WhiteSheet) {

            Intent i = new Intent(OC.this, WhiteSheet.class);

            startActivity(i);

        }

        else if (v.getId() == R.id.trafficLight)
        {    Intent i = new Intent(OC.this, ShowTrafficLight.class);
            startActivity(i);
    }

    else if (v.getId() == R.id.slide)
    {    Intent i = new Intent(OC.this, PdfFiles.class);
        startActivity(i);
    }
        else if (v.getId() == R.id.ques)
        {    Intent i = new Intent(OC.this, load_image.class);

            startActivity(i);
        }
else  if(v.getId()==R.id.Attendance)
        {Intent i=new Intent(OC.this,show_attandance.class);
            startActivity(i);
}

    else  if(v.getId()==R.id.lec)
    {Intent i=new Intent(OC.this,Streaming.class);
        startActivity(i);

    }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_oc, menu);
        return super.onCreateOptionsMenu(menu);
    }

@Override
public boolean onOptionsItemSelected(MenuItem item) {


    super.onOptionsItemSelected(item);

    switch (item.getItemId()) {
        case R.id.allow:
connect(en_url);

            break;




    case R.id.refuse:
connect(dis_url);


    break;



    case R.id.clearimg:
    connect(clearimg);
    break;
        case R.id.clearatt:
            connect(clearatt);
break;
        case R.id.cleartraffic:
            connect(cleartraffic);
break;


    }
    return true;
}
    public void connect(String url){


        String URL=url;

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading...", "Please wait...", false, false);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(OC.this, s, Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(OC.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);



    }
}