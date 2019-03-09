package no.oivheg.DoroApp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static MainActivity ins;
    private static final String TAG = "MainActivity";
    public static FirebaseAuth mAuth;
    public boolean Freeze = false;
    public static TextView  battery;
    public TextView  token;
    static Context context; // COontext for getting aces to current widnow and who toasts

    //    static DocumentReference  db = FirebaseFirestore.getInstance().document("Users/user");
//END FIREBSAE
    public Button btn, btnFreeze;
    //FIREBASE
    private FirebaseAnalytics mFirebaseAnalytics;

    public void SetToken(String DeviceToken) {
//        CreateUser(DeviceToken);
         final String DvToken = DeviceToken;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    token.setText(String.valueOf(DvToken));
                } catch (Exception e) {
                    System.out.println("MAIN: ERROR Set Text MasterKey  " + e);
                }

            }
        });

    }



    @Override
    protected void onStop() {
        super.onStop();
        this.unregisterReceiver(BatteryChecker.mBatInfoREceiver);
    }
    public static MainActivity getInstace() {
        return ins;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ins = this;
        btnFreeze = this.findViewById(R.id.Freeze);
        battery = this.findViewById(R.id.battery);
        token = this.findViewById(R.id.Token);
        //token.setVisibility(View.GONE);
        btn = this.findViewById(R.id.Answere);

        battery.setText(String.valueOf(BatteryChecker.GetBattery()));
        context = getApplicationContext();

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "2");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                EditText DvName = findViewById(R.id.DeviceName);
                User.CreateUser(token.getText().toString(), DvName.getText().toString(), BatteryChecker.GetBattery());

            }
        });

        btnFreeze.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Freeze){
                    btnFreeze.setText("UnFreeze");
                    Freeze = false;
                }else{
                    Freeze = true;
                    btnFreeze.setText("Freeze");
                }



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
        this.registerReceiver(BatteryChecker.mBatInfoREceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
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
