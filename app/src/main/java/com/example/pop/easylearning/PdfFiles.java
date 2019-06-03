package com.example.pop.easylearning;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;

public class PdfFiles extends ListActivity {

   private String[] pdflist;
   private File[] Filelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_show_slide);


        this.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));
        this.getListView().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BCD4E6")));

        //Return the primary shared/external storage directory.
        final File files = Environment.getExternalStorageDirectory();
        Filelist = files.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return ((name.endsWith(".pdf")));

            }
        });
        pdflist = new String[Filelist.length];
        for (int i = 0; i < Filelist.length; i++) {
            pdflist[i] = Filelist[i].getName();
        }
        this.setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, pdflist));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String path = Filelist[(int) id].getAbsolutePath();
        openPdfIntent(path);

    }



    private void openPdfIntent(String path) {
        try {
            final Intent intent = new Intent(PdfFiles.this, pdf_render.class);
            // to send path of file to pdf_render Activity
            intent.putExtra("path", path);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }}
