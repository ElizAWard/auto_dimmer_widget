package net.bedesigns.autodimmerwidget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by softi on 7/23/2020.
 */

public class WidgetProvider extends AppWidgetProvider {
    private static final String DIMMER_ACTION_CLICK = "DIMMER_ACTION_CLICK";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() == null) {
            context.startService(new Intent(context, ToggleService.class));
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        context.startService(new Intent(context, ToggleService.class));
    }

    public static class ToggleService extends IntentService
    {

        public ToggleService() {
            super("AppWidget$ToggleService");
        }

        @Override
        protected void onHandleIntent(Intent intent)
        {
            ComponentName componentName =new ComponentName(this, WidgetProvider.class);
            AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
            widgetManager.updateAppWidget(componentName, buildUpdate(this));
        }

        private RemoteViews buildUpdate(Context context) {
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // TODO this is where you change the icon based on brightness
//            AudioManager audioManager = (AudioManager)context.getSystemService(Activity.AUDIO_SERVICE);
//            if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT)
//            {
//                updateViews.setImageViewResource(R.id.phoneState, R.drawable.phone_state_normal);
//                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//            } else {
//                updateViews.setImageViewResource(R.id.phoneState, R.drawable.phone_state_silent);
//                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//            }

            Intent intent = new Intent(this, WidgetProvider.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            updateViews.setOnClickPendingIntent(R.id.phoneState, pendingIntent);

            return updateViews;

        }
    }
}
