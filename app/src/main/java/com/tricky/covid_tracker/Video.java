package com.tricky.covid_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import static com.tricky.covid_tracker.R.*;

public class Video extends AppCompatActivity {
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_video_view);

        videoView=findViewById(id.videoView);
        mediaController=new MediaController(this);
//
//        Uri uri=Uri.parse("https://www.youtube.com/watch?time_continue=83&v=1APwq1df6Mw&feature=emb_logo");
//        videoView.setMediaController(mediaController);
//        videoView.setVideoURI(uri);
//
//        videoView.start();

//        String videoPath="android.resource://com.tricky.covid_tracker/"+ R.raw.corona;
//        Uri uri=Uri.parse(videoPath);
//        videoView.setVideoURI(uri);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//        videoView.start();
    }

}
