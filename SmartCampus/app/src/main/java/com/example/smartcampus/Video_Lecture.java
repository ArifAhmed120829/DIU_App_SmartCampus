package com.example.smartcampus;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Video_Lecture extends AppCompatActivity {

    VideoView videoView;
    VideoView videoView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lecture);


        videoView = findViewById(R.id.videoview);
        videoView2 = findViewById(R.id.videoView2);

        // Display Videos From local Storage
        videoView.setVideoPath(
                "android.resource://"+
                        getPackageName()+"/"+R.raw.basicblock);

        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);

        videoView2.setVideoPath(
                "android.resource://"+
                        getPackageName()+"/"+R.raw.compilerdesign);

        MediaController mc2 = new MediaController(this);
        mc2.setAnchorView(videoView2);
        videoView2.setMediaController(mc2);




    }
}