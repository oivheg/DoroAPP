package no.oivheg.DoroApp.FCM;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import no.oivheg.DoroApp.User;


public class Notificator extends Worker {
    public static final String DeviceName = "DeviceName";
    public static final String DeviceMaster = "DeviceMaster";
    public static final String Token = "Token";
    public static final String Battery = "Battery";
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

    public Notificator(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static int getBatteryPercentage(Context context) {

        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;

        return (int) (batteryPct * 100);
    }

    @NonNull
    @Override
    public Result doWork() {


//        String DeviceName = getInputData().getString(Notificator.DeviceName);
//        String DeviceMaster = getInputData().getString(Notificator.DeviceMaster);
//        String Token = getInputData().getString(Notificator.Token);
//        String Battery = getInputData().getString(Notificator.Battery);


        String DeviceName = preferences.getString("DeviceName", "defaultName");
        String DeviceMaster = preferences.getString("MasterId", "defaultMaster");
        String Token = preferences.getString("Token", "defaultToken");
        String Battery = preferences.getString("Battery", "defaulBatteryt");

        int IntBattery = getBatteryPercentage(getApplicationContext());

        Battery = String.valueOf(IntBattery);

        if (!DeviceName.equals("defaultName")) {
            User.CreateUser(Token, "Battery: " + Battery + "%", DeviceName, DeviceMaster);

            return Result.success();
        }
        return Result.failure();
    }
}
