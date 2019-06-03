package com.example.pop.easylearning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pop on 26/02/2016.
 */
public class CustomList extends ArrayAdapter<String> {


    private final Context context;
    private final String[] content;
    private final Integer[] imageId;
    public CustomList(Context context,
                      String[] content, Integer[] imageId) {
        super(context, R.layout.list_single, content);
        this.context = context;
        this.content = content;
        this.imageId = imageId;





}


    @Override
    public View getView(int position, View view, ViewGroup parent) {



        LayoutInflater inflater=(LayoutInflater)   getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        //  to set text in textView based on its position in its array


        txtTitle.setText(content[position]);
        txtTitle.setTextColor(Color.parseColor("#21ABCD"));
        txtTitle.setTextSize(60);
        //to set ShadowLayer
        txtTitle.setShadowLayer(1.3f, 4.0f, 4.0f, Color.parseColor("#21ABCD"));


      //to set font format
        Typeface face= Typeface.createFromAsset(getContext().getAssets(), "font/JLSDataGothicR_NC.otf");
        txtTitle.setTypeface(face);


// to set image in imageview based on its position in its array
        imageView.setImageResource(imageId[position]);
        return rowView;}



    }





