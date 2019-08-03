package org.golde.android.carcontroller.voicecontrol;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import org.golde.android.carcontroller.MainActivity;
import org.golde.android.carcontroller.ui.BetterColor;
import org.golde.android.carcontroller.ui.ColorChangeCallback;

/*
Intercept IFTTT notifications to change the color of the lights
 */
public class NotificationService extends NotificationListenerService {

    private static final String tag = "NOTIFICATION SERVICE";
    private static final String IFTTT_PREFIX = "ECARLIGHT:";

    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    static ColorChangeCallback callback;

    public static void setCallback(ColorChangeCallback callback){
        NotificationService.callback = callback;
    }

    public static void checkPermission(MainActivity main){
        if(!isNotificationServiceEnabled(main)){
            buildNotificationServiceAlertDialog(main).show();
        }
    }

    private static boolean isNotificationServiceEnabled(MainActivity main){
        String pkgName = main.getPackageName();
        final String flat = Settings.Secure.getString(main.getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static AlertDialog buildNotificationServiceAlertDialog(MainActivity main){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(main);
        alertDialogBuilder.setTitle("IFTTT Notification Service");
        alertDialogBuilder.setMessage("Need to enable this to listen for IFTTT notifications. If yoj click no, voice control will not work");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        main.startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // If you choose to not enable the notification listener
                        // the app. will not work as expected
                    }
                });
        return(alertDialogBuilder.create());
    }

    @Override
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        if(isCarNotification(statusBarNotification)){
            cancelNotification(statusBarNotification.getKey());
        }

    }

    boolean isCarNotification(StatusBarNotification noti){

        if(noti.getPackageName().equalsIgnoreCase("com.ifttt.ifttt")){
            String text = noti.getNotification().extras.getCharSequence("android.text").toString();
            if(text.startsWith(IFTTT_PREFIX)){
                text = text.replace(IFTTT_PREFIX, ""); //Remove prefix
                Log.d(tag, "---------------------- Recieved Color: " + text);
                int color;
                try {
                    color = Color.parseColor(text);
                    if(callback != null){
                        callback.onColorChange(new BetterColor(Color.red(color), Color.green(color), Color.blue(color), 255));
                    }
                }
                catch(IllegalArgumentException  e){
                    return true; //Invalid color. No real way for us to respond
                }

                return true;
            }
        }

        return false;
    }

}
