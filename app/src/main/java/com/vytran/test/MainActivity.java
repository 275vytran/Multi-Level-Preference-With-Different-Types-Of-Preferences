package com.vytran.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button happyButton, sadButton, prefsButton;
    boolean all_sounds_loaded;
    int num_sounds_loaded;
    int happySound, sadSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        happyButton = (Button)findViewById(R.id.happyButton);
        sadButton = (Button)findViewById(R.id.sadButton);
        prefsButton = (Button)findViewById(R.id.prefsButton);

        happyButton.setOnClickListener(this);
        sadButton.setOnClickListener(this);
        prefsButton.setOnClickListener(this);


        //Set up SoundPool
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //maximum number of sound effects, type stream, srcQuality
            Assets.sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }
        else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            SoundPool sounds = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        //Keep track of number of loaded sound
        //Make sure every sounds is loaded
        Assets.sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                num_sounds_loaded++;
                if (num_sounds_loaded == 2) //there are 2 sounds effect which are happy and sad
                    all_sounds_loaded = true;
            }
        });

        //Load sound effect
        happySound = Assets.sp.load(this, R.raw.happy, 1);
        sadSound = Assets.sp.load(this, R.raw.sad, 1);

        //textView
        Assets.textView = (TextView)findViewById(R.id.textView);
        final SharedPreferences textPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String d = textPrefs.getString("key_list", "Item One");
        if (d != null) {
            Assets.textView.setText(Assets.text);
        }

        //username
        Assets.userTextView = (TextView)findViewById(R.id.userTextView);
        final SharedPreferences userPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String e = userPrefs.getString("key_username", "Username");
        if (e != null) {
            Assets.userTextView.setText(e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Assets.mp = null;

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean b = prefs.getBoolean("key_music_off", false);
        if (b==true) {
            Assets.mp = MediaPlayer.create(this, R.raw.background);
            if (Assets.mp != null) {
                Assets.mp.setLooping(true);
                Assets.mp.start();
            }
        }
    }

    @Override
    protected void onPause() {
        if (Assets.mp != null) {
            Assets.mp.pause();
            Assets.mp.release();
            Assets.mp = null;
        }

        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.happyButton:
                final SharedPreferences happyPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean c1 = happyPrefs.getBoolean("key_sound_on", true);
                if (c1==true)
                    Assets.sp.play(happySound, 1,1,0,0,1);
                break;
            case R.id.sadButton:
                final SharedPreferences sadPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean c2 = sadPrefs.getBoolean("key_sound_on", true);
                if (c2==true)
                    Assets.sp.play(sadSound, 1, 1, 0, 0, 1);
                break;
            case R.id.prefsButton:
                Intent intent = new Intent(MainActivity.this, PrefsActivity.class);
                startActivity(intent);
        }
    }
}
