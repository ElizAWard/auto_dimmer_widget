package net.bedesigns.autodimmerwidget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE;
import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL;
import static java.security.AccessController.getContext;

public class MainActivity extends Activity {

    private static final String TAG = "DimmerActivity";

    private int currentBrightness;
    private boolean isManualMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            currentBrightness = Settings.System.getInt(MainActivity.this.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
            Log.d(TAG, String.format("Brightness value: %s", currentBrightness));
        } catch (Settings.SettingNotFoundException e) {
            Log.d(TAG, "Brightness setting not found", e);
        }

        getManualMode();
        setOnClickListener();
    }

    private void getManualMode() {
        isManualMode = currentBrightness == SCREEN_BRIGHTNESS_MODE_MANUAL;
        Log.d(TAG, String.format("IsManualMode: %s", isManualMode));
    }

    private void setOnClickListener() {
        final ImageButton imageButton = (ImageButton) findViewById(R.id.brightness_toggle_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getManualMode();
                // if manual, put in auto
                if (isManualMode) {
                    Settings.System.putInt(MainActivity.this.getContentResolver(),
                            SCREEN_BRIGHTNESS_MODE, SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                    imageButton.setImageResource(R.drawable.ic_brightness_high_black_24dp);
                } else {
                    // if auto, put in manual
                    Settings.System.putInt(MainActivity.this.getContentResolver(),
                            SCREEN_BRIGHTNESS_MODE, SCREEN_BRIGHTNESS_MODE_MANUAL);
                    imageButton.setImageResource(R.drawable.ic_brightness_5_black_24dp);
                }
            }
        });
    }
}
