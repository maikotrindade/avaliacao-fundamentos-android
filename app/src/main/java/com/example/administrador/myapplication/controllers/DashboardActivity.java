package com.example.administrador.myapplication.controllers;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrador.myapplication.R;

public class DashboardActivity extends ActionBarActivity implements View.OnClickListener {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final Button btnServiceOrder = (Button) findViewById(R.id.btnServiceOrder);
        btnServiceOrder.setOnClickListener(this);

        final Button btnReports = (Button) findViewById(R.id.btnReports);
        btnReports.setOnClickListener(this);

        final Button btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);

        mediaPlayer = new MediaPlayer();
        playSexySax();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnServiceOrder: startActivity(new Intent(DashboardActivity.this, ServiceOrderListActivity.class)); break;
            case R.id.btnReports: startActivity(new Intent(DashboardActivity.this, ReportsActivity.class)); break;
            case R.id.btnAbout: startActivity(new Intent(DashboardActivity.this, AboutActivity.class)); break;

        }
    }

    public void playSexySax() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }

            AssetFileDescriptor descriptor = getAssets().openFd("sexysax.mp3");
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();
            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e(DashboardActivity.class.getName(), e.toString());
        }
    }

    @Override
    public void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onPause();
    }
}