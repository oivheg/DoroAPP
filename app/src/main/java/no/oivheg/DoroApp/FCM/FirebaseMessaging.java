package no.oivheg.DoroApp.FCM;

import android.bluetooth.BluetoothClass;
import android.content.DialogInterface;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import no.oivheg.DoroApp.BatteryChecker;
import no.oivheg.DoroApp.MainActivity;
import no.oivheg.DoroApp.User;

import static android.content.ContentValues.TAG;

public class FirebaseMessaging extends FirebaseMessagingService {

    private static String DeviceToken;


    public static String getDeviceToken() {
        return DeviceToken;
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        DeviceToken = token;

        MainActivity.getInstace().SetToken(DeviceToken);


     //   MainActivity.SetToken(DeviceToken);
       // User.CreateUser(DeviceToken, BatteryChecker.GetBattery());
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        // sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
if (MainActivity.getInstace().Freeze){
    return;

}
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            String tmp = BatteryChecker.GetBattery();
            User.CreateUser(DeviceToken,User.DeviceName ,BatteryChecker.GetBattery(), true);
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


}
