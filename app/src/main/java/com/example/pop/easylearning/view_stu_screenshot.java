package com.example.pop.easylearning;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class view_stu_screenshot extends Activity {
     NetworkImageView image;
    load_image img;
    private ImageLoader imageLoader1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_stu_screenshot);

        image= (NetworkImageView)findViewById(R.id.fullimg);



        String[] imgg =getIntent().getExtras().getStringArray("array");
int position=getIntent().getExtras().getInt("position");

imageLoader1=CustomVolleyRequest.getInstance(this).getImageLoader();
image.setImageUrl(imgg[position],imageLoader1);







    }

    }


