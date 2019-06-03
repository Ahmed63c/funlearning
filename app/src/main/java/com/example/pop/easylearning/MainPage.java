package com.example.pop.easylearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainPage extends Activity {


   private ListView l;

    String[]type={"Teacher","Student"};
    Integer[]imageid={R.drawable.teacher,R.drawable.students};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);



               //to set image and text in each row of listview
                CustomList adapter = new
                CustomList(this, type, imageid);
                 l = (ListView) findViewById(R.id.list);
                 // to connect adapter to listview
                 l.setAdapter(adapter);


                   // action of each row in ListView
                   l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   if (position == 0) {
                    Intent intent = new Intent(MainPage.this, login.class);
                    startActivity(intent);

                } else if (position == 1) {
                    Intent intent = new Intent(MainPage.this, StudentLog.class);
                    startActivity(intent);
                }
            }
        });






    }


}
