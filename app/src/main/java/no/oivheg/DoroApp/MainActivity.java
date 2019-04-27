package no.oivheg.DoroApp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;

import no.oivheg.DoroApp.FCM.Notificator;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "ANDROID_TUTORIALS_HUB_PREFERENCES";
    public static Context context; // Context for getting aces to current widnow and who toasts

    private static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth;
    public static EditText DeviceName, MasterID;

    public static TextView token, battery;
    private static String FCMToken;
    SharedPreferences mPreferences;
    //    static DocumentReference  db = FirebaseFirestore.getInstance().document("Users/user");
//END FIREBSAE
    public Button btn;
    //FIREBASE
    private FirebaseAnalytics mFirebaseAnalytics;
    SharedPreferences.Editor mEditor;

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(BatteryChecker.mBatInfoREceiver);
    }

    public static void SetToken(String DeviceToken) {
//       CreateUser(DeviceToken);
        // CreateOrUpdateUser();
        FCMToken = DeviceToken;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        battery = this.findViewById(R.id.battery);
        btn = this.findViewById(R.id.Register);
        DeviceName = this.findViewById(R.id.DeviceName);
        MasterID = this.findViewById(R.id.MasterId);





        context = getApplicationContext();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        this.registerReceiver(BatteryChecker.mBatInfoREceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        battery.setText(String.valueOf(BatteryChecker.GetBattery()));
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                logInUser();
                // TODO Auto-generated method stub

                btn.setEnabled(false);



            }
        });

        // Access a Cloud Firestore instance from your Activity
//        if (true){{
//            logInUser();
//        }}

        //      CreateUser();
    }

    private void CreateOrUpdateUser() {
        token = this.findViewById(R.id.Token);
        battery = this.findViewById(R.id.battery);
        btn = this.findViewById(R.id.Register);
        DeviceName = this.findViewById(R.id.DeviceName);
        MasterID = this.findViewById(R.id.MasterId);


        mEditor.putString("MasterId", MasterID.getText().toString()); // s
        mEditor.putString("DeviceName", DeviceName.getText().toString()); // s
        mEditor.putString("Token", token.getText().toString()); // s
        mEditor.putString("Battery", BatteryChecker.GetBattery()); // s

        mEditor.commit();

        token.setText("Token");
        User.CreateUser(token.getText().toString(), BatteryChecker.GetBattery(), DeviceName.getText().toString(), MasterID.getText().toString());

//        Data data = new Data.Builder()
//                .putString(Notificator.DeviceMaster, MasterID.getText().toString())
//                .putString(Notificator.DeviceName, DeviceName.getText().toString())
//                .putString(Notificator.Battery, BatteryChecker.GetBattery())
//                .putString(Notificator.Token,token.getText().toString())
//                .build();

        PeriodicWorkRequest.Builder myWorkBuilder =
//                new PeriodicWorkRequest.Builder(Notificator.class, 15, TimeUnit.MINUTES).setInputData(data);
                new PeriodicWorkRequest.Builder(Notificator.class, 15, TimeUnit.MINUTES);
//        new PeriodicWorkRequest.Builder(Notificator.class, 1, TimeUnit.HOURS);


        PeriodicWorkRequest myWork = myWorkBuilder.build();
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.REPLACE, myWork);

        WorkManager.getInstance().enqueue(myWork);
    }
    // [END on_start_check_user]

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (mAuth != null) {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            // updateUI(currentUser); Code for doing stuff with the UI,

        }


    }



    private void logInUser() {
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            Toast.makeText(MainActivity.context, "Authentication + Success.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            CreateOrUpdateUser();


                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  updateUI(null);
                        }

                        // ...
                    }
                });


    }


}
