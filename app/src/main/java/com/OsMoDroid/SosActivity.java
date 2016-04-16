package com.OsMoDroid;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
public class SosActivity extends Activity
    {
        Ringtone r;
        Vibrator vibrator;
        private final BroadcastReceiver finisReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        @Override
        protected void onCreate(Bundle savedInstanceState)
            {
                super.onCreate(savedInstanceState);
                registerReceiver(finisReceiver,new IntentFilter("closesos"));
                vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                final Window win = getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                win.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
                setContentView(R.layout.activity_sos);
                TextView txt = (TextView) findViewById(R.id.textViewSOS);
                if (getIntent().hasExtra("message"))
                    {
                        txt.setText(getIntent().getStringExtra("message"));
                    }
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.setStreamType(AudioManager.STREAM_ALARM);
                r.play();
                long[] pattern = {0, 100, 100, 200, 100, 300};
                vibrator.vibrate(pattern, 0);

            }
        @Override
        protected void onDestroy()
            {
                unregisterReceiver(finisReceiver);
                r.stop();
                vibrator.cancel();

                super.onDestroy();
            }
    }
