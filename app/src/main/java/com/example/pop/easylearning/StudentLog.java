package com.example.pop.easylearning;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StudentLog extends Activity {
    private   Button log;
    private EditText name,mail,url;

    public static final String MyPREFERENCES = "myprefs" ;
    public static SharedPreferences sharedpreferences;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_log);

        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));



        log=(Button)findViewById(R.id.logbtn);
        name=(EditText)findViewById(R.id.name);
        mail=(EditText)findViewById(R.id.email);
        url=(EditText)findViewById(R.id.url);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n  = name.getText().toString();
                String m  = mail.getText().toString();
                String u  = url.getText().toString();

                SharedPreferences.Editor editor=sharedpreferences.edit();
                editor.putString("n", n);
                editor.putString("m", m);
                editor.putString("u", u);
                editor.apply();
                Intent i=new Intent(StudentLog.this,StudentApp.class);
                startActivity(i);



            }
        });

    }


}
