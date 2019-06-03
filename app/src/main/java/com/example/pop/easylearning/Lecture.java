package com.example.pop.easylearning;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Lecture extends Activity {
    public  String Video,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        WebView video = (WebView) findViewById(R.id.webview);
       // video.setWebChromeClient(new WebViewClient());

        video.getSettings().setJavaScriptEnabled(true);
        video.getSettings().setPluginState(WebSettings.PluginState.ON);
        video.loadUrl("http://192.168.1.9/web");*/
        Uri videoUri = Uri.parse("rtsp://192.168.0.105:1935/live/test");
        Intent i = new Intent(Intent.ACTION_VIEW);
        // i.putExtra( EXTRA_DECODE_MODE, (byte)2 );
        i.setPackage( "com.mxtech.videoplayer.ad" );
        i.setData(videoUri);
        i.setClassName(  "com.mxtech.videoplayer.ad" , "com.mxtech.videoplayer.ad.ActivityScreen" );
        startActivity(i);



    }

}


