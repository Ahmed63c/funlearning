package com.example.pop.easylearning;

import android.app.Activity;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;

public class pdf_render extends Activity {


    private int currpage =0;
    private ImageButton  next, pre,brush,eraser;

     private DrawingView drawingView;
     private float  largeBrush ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render);
eraser=(ImageButton)findViewById(R.id.eraser);
        brush=(ImageButton)findViewById(R.id.brush);
        largeBrush = getResources().getInteger(R.integer.large_size);
eraser.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        drawingView.setErase(true);
        drawingView.setBrushSize(largeBrush);
    }
});



brush.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        drawingView.setErase(false);
        drawingView.setBrushSize(2);

        drawingView.setLastBrushSize(2);

    }
});


        next = (ImageButton) findViewById(R.id.next);
        pre = (ImageButton) findViewById(R.id.previous);

        drawingView = (DrawingView) findViewById(R.id.drawing);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currpage++;
                render();
            }
        });
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currpage--;
                render();

            }

        });


     //   zoom.setOnZoomInClickListener(new View.OnClickListener() {

          //  @Override
           // public void onClick(View v) {
                // TODO Auto-generated method stub

             //   float x = drawingView.getScaleX();
              //  float y = drawingView.getScaleY();

               // drawingView.setScaleX((float) (x + 1));
               // drawingView.setScaleY((float) (y + 1));
           // }
      //  });


      //  zoom.setOnZoomOutClickListener(new View.OnClickListener() {

         //   @Override
         //   public void onClick(View v) {
                // TODO Auto-generated method stub

               // float x = drawingView.getScaleX();
               // float y = drawingView.getScaleY();

               // drawingView.setScaleX((float) (x - 1));
               // drawingView.setScaleY((float) (y - 1));
           // }
       // });





    }

    private void render() {
        try {


            // to take value from fileChooser
            // getExtras reutrn bundle that hold all value type that pass between activities
            // get string retuen value that associated with given key such as path
            String data = getIntent().getExtras().getString("path");
            File file = new File(data);
            // FileDescriptor for file, it allows you to close file when you are done with it

            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));


            if (currpage < 0) {
                currpage = 0;
            } else if (currpage > renderer.getPageCount()) {
                currpage = renderer.getPageCount() - 1;
            }


            // Open page with specified index and Pdf page is rendered on Bitmap
            renderer.openPage(currpage).render(drawingView.canvasBitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            drawingView.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }}



