package com.example.pop.easylearning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WhiteSheet extends Activity implements OnClickListener {
    private DrawingView drawingView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn;
    private float mediumBrush, largeBrush;
    private Bitmap myBitmap;
    private ImageButton send;
    private String mail;
    public static final String url = "http://192.168.0.104/uploadimg.php";
    public static final String SubUrl = "http://192.168.0.104/sub.php";
    public static final String key_img = "image";
    public static final String key_mail = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_sheet);
        SharedPreferences sharedPreferences = getSharedPreferences("myprefs", 0);

        mail = sharedPreferences.getString("m", null);


        drawingView = (DrawingView) findViewById(R.id.drawing);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);

        currPaint = (ImageButton) paintLayout.getChildAt(3);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        send = (ImageButton) findViewById(R.id.send);

        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        drawBtn = (ImageButton) findViewById(R.id.draw_btn);

        drawBtn.setOnClickListener(this);


        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageButton) findViewById(R.id.new_btn);
        newBtn.setOnClickListener(this);
        send.setOnClickListener(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {

                        Toast.makeText(WhiteSheet.this, s, Toast.LENGTH_LONG).show();

                        if (s.equals("dis")) {
                            send.setEnabled(false);


                        } else if (s.equals("enable")) {
                            send.setEnabled(true);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        String s = volleyError.toString();
                        Toast.makeText(WhiteSheet.this, s, Toast.LENGTH_LONG).show();


                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);
    }


    public void paintClicked(View view) {

        drawingView.setErase(false);
        if (view != currPaint) {
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawingView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
            drawingView.setBrushSize(drawingView.getLastBrushSize());
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.draw_btn) {
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush size:");

            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(false);
                    drawingView.setBrushSize(2);

                    drawingView.setLastBrushSize(2);
                    brushDialog.dismiss();


                }
            });

            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(false);
                    drawingView.setBrushSize(mediumBrush);
                    drawingView.setLastBrushSize(mediumBrush);
                    brushDialog.dismiss();


                }
            });


            brushDialog.show();
        } else if (view.getId() == R.id.erase_btn) {


            drawingView.setErase(true);

            drawingView.setBrushSize(largeBrush);


        } else if (view.getId() == R.id.new_btn) {

            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New drawing");
            newDialog.setMessage("Start new drawing (you will lose the current drawing)?");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawingView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();


            //new button
        } else if (view.getId() == R.id.send) {


            update();


        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_mail:

                View v1 = getWindow().getDecorView().getRootView();

                v1.setDrawingCacheEnabled(true);
                myBitmap = v1.getDrawingCache();
                saveBitmap(myBitmap);
               return true;

           // default:
               // return super.onOptionsItemSelected(item);


            case R.id.refresh:


                Intent i = new Intent(WhiteSheet.this, WhiteSheet.class);
                startActivity(i);
                finish();
                break;}

            return true;



    }







    public void saveBitmap(Bitmap bitmap) {
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "Pictures/screenshot.png";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            sendMail(filePath);
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    public void sendMail(String path) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{"receiver@website.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Truiton Test Mail");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "This is an autogenerated mail from Truiton's InAppMail app");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
//to take screenShot
public  void Upload(){

// to take image to bitmap

   View v1 = getWindow().getDecorView().getRootView();
   v1.setDrawingCacheEnabled(true);
    myBitmap = v1.getDrawingCache();





    final ProgressDialog pr=ProgressDialog.show(this,"pleasWait","Loading",false,false);

    final StringRequest req=new StringRequest(Request.Method.POST,url,new Response.Listener<String>() {
// response from server when connection request success
        @Override
        public void onResponse(String s) {
            //to progressdialog disappear
            pr.dismiss();
            Toast.makeText(WhiteSheet.this, s, Toast.LENGTH_LONG).show();


        }

    },

// response when faild connection
    new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            pr.dismiss();
            Toast.makeText(WhiteSheet.this,error.getMessage().toString(), Toast.LENGTH_LONG).show();


        }


    }



    ) {// to send parameter with request
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            String img = getStringToImage(myBitmap);
            HashMap map = new HashMap();

            map.put(key_img, img);

           // map.put(key_mail, mail);

            return map;
        }
    };

    // to request done
    RequestQueue requestQueue=Volley.newRequestQueue(this);

    requestQueue.add(req);





}
// to convert bitmap to string
public String getStringToImage(Bitmap bm)
{//create byte ArrayOutputStream because compress method take Arrayoutputstream
    //create buffer in memory take size 32 byte
    ByteArrayOutputStream ba=new ByteArrayOutputStream();


    bm.compress(Bitmap.CompressFormat.JPEG, 50, ba);//write compressed bitmap to buffer
//convert byte ArrayOutputstream to ByteArray because encode method takes ByteArray
    byte[]imageByte=ba.toByteArray();
    String Encodeimag= Base64.encodeToString(imageByte, Base64.DEFAULT);//encoding method
    return Encodeimag;





}
public void update()
{
    StringRequest stringRequest = new StringRequest(Request.Method.POST, SubUrl,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    //Disimissing the progress dialog

                    //Showing toast message of the response
                    Toast.makeText(WhiteSheet.this, s, Toast.LENGTH_LONG).show();





                     if (s.equals("enable")) {

                        Upload();

                    }
                }
            },

            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    //Dismissing the progress dialog


                    //Showing toast
                    Toast.makeText(WhiteSheet.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
    RequestQueue requestQueue = Volley.newRequestQueue(this);

    requestQueue.add(stringRequest);

}







}


