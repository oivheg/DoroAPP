package no.oivheg.DoroApp.FCM;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import no.oivheg.DoroApp.BatteryChecker;
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

    @NonNull
    @Override
    public Result doWork() {


//
//        String DeviceName = getInputData().getString(Notificator.DeviceName);
//        String DeviceMaster = getInputData().getString(Notificator.DeviceMaster);
//        String Token = getInputData().getString(Notificator.Token);
//        String Battery = getInputData().getString(Notificator.Battery);


        String DeviceName = preferences.getString("DeviceName", "defaultName");
        String DeviceMaster = preferences.getString("MasterId", "defaultMaster");
        String Token = preferences.getString("Token", "defaultToken");
        String Battery = preferences.getString("Battery", "defaulBatteryt");


        if (!DeviceName.equals("defaultName")) {
            User.CreateUser(Token, BatteryChecker.GetBattery(), DeviceName, DeviceMaster);

            return Result.success();
        }
        return Result.failure();
    }
}
