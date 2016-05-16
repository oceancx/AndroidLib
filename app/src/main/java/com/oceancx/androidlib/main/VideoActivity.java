package com.oceancx.androidlib.main;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.oceancx.androidlib.R;

/**
 * Created by oceancx on 16/4/19.
 */
public class VideoActivity extends AppCompatActivity {
    SurfaceView surfaceView;
    MediaPlayer mediaPlayer;
    Button trigger_bt;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final LinearLayout ll = new LinearLayout(this);


        trigger_bt = new Button(this);
        LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(trigger_bt, ll_params);

        final VideoView videoView = new VideoView(this);
        ll.addView(videoView);
//        surfaceView = new SurfaceView(this);
//        ll.addView(surfaceView);
        setContentView(ll);


        trigger_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


        /*
         * Alternatively, you can use mVideoView.setVideoPath(<path>);
         */
                videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() +
                        "/" + R.raw.video));
                videoView.setMediaController(new MediaController(v.getContext()));
                videoView.requestFocus();
                


            }
        });


//        mediaPlayer = MediaPlayer.create(this, R.raw.video);
//        surfaceHolder= surfaceView.getHolder();
//        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                DebugLog.e("create");
//
//
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                DebugLog.e("destroyp");
//                mediaPlayer.stop();
//            }
//        });
//        trigger_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DebugLog.e("start to play");
//                mediaPlayer.setDisplay(surfaceHolder);
//                mediaPlayer.start();
//                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mediaPlayer.stop();
//                    }
//                });
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
