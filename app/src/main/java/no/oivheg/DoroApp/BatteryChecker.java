package no.oivheg.DoroApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

public class BatteryChecker {
    public static String battery;
    public static BroadcastReceiver mBatInfoREceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            battery = String.valueOf(level) + '%';

            MainActivity.battery.setText(battery);
        }
    };

    public static String GetBattery() {

        return battery;
    }


}

