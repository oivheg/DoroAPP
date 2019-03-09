package no.oivheg.DoroApp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth;

    public static TextView token, battery;
    static Context context; // COontext for getting aces to current widnow and who toasts

    //    static DocumentReference  db = FirebaseFirestore.getInstance().document("Users/user");
//END FIREBSAE
    public Button btn;
    //FIREBASE
    private FirebaseAnalytics mFirebaseAnalytics;

    public static void SetToken(String DeviceToken) {
//        CreateUser(DeviceToken);

//        token.setText(DeviceToken);
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(BatteryChecker.mBatInfoREceiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        battery = this.findViewById(R.id.battery);
        btn = this.findViewById(R.id.Answere);

        battery.setText(String.valueOf(BatteryChecker.GetBattery()));
        context = getApplicationContext();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        this.registerReceiver(BatteryChecker.mBatInfoREceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                User.CreateUser(token.getText().toString(), BatteryChecker.GetBattery());

            }
        });

        // Access a Cloud Firestore instance from your Activity
        logInUser();
        //      CreateUser();
    }
    // [END on_start_check_user]

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser); Code for doing stuff with the UI,
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
                            FirebaseUser user = mAuth.getCurrentUser();
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
