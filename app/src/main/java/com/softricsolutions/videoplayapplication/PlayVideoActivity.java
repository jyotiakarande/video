package com.softricsolutions.videoplayapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

/**
 * Created by jyoti on 21-Aug-17.
 */

public abstract class PlayVideoActivity extends Activity implements MediaPlayer.OnCompletionListener  {
      private VideoView mVideoView;
    //  private LocalSingleHttpServer mServer;

    // to be implemented in concrete activities
    protected abstract Cipher getCipher() throws GeneralSecurityException;
    protected abstract String getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video_activity);

        mVideoView = (VideoView) findViewById(R.id.vwVideo);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setMediaController(new MediaController(this));  // is optional
        try {
            //mServer = new LocalSingleHttpServer();
            final Cipher c = getCipher();
            if (c != null) {  // null means a clear video ; no need to set a decryption processing
              //  mServer.setCipher(c);
            }
          //  mServer.start();
            String path = getPath();
          //  path = mServer.getURL(path);
            mVideoView.setVideoPath(path);
            mVideoView.start();
        } catch (Exception e) {  // exception management is not implemented in this demo code
            // Auto-generated catch block
            e.printStackTrace();
        }
    }


    /*@Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        // do some cleanup in case of paused or currently playing
        mVideoView.stopPlayback();	// doesn't hurt if even !isPlaying()
        if (mServer != null) {	// onCompletion() may not have be called
            mServer.stop();
            mServer = null;
        }
        super.onDestroy();
    }

    // MediaPlayer.OnCompletionListener interface
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");
        if (mServer != null) {
            mServer.stop();
            mServer = null;
        }
        finish();  // or design a method like playNext()
    }*/
}
