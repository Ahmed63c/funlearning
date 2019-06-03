package com.example.pop.easylearning;

/**
 * Created by pop on 05/05/2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by pop on 04/05/2016.
 */
public class GridViewAdapter extends BaseAdapter {

    private ImageLoader imageLoader;

    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    // ArrayList<String> images;
   public String[]item=new String[200];
    public GridViewAdapter (Context context,String [] images){
        //Getting all the values
        this.context = context;
       // this.images = images;

        this.item=images;
    }

    public String getItem(int position)
    {
        return this.item[position];
    }

    @Override
    public int getCount() {
        return item.length;
    }




    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creating a linear layout
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        //NetworkImageView
        NetworkImageView networkImageView = new NetworkImageView(context);

        //Initializing ImageLoader
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();


        // Set the URL of the image that should be loaded into this view, and
// specify the ImageLoader that will be used to make the request.
        networkImageView.setImageUrl(item[position], imageLoader);





        //Scaling the imageview
        networkImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        networkImageView.setLayoutParams(new GridView.LayoutParams(400,400));

        //Adding views to the layout

        linearLayout.addView(networkImageView);

        //Returnint the layout
        return linearLayout;
    }














}