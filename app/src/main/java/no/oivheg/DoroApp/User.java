package no.oivheg.DoroApp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class User {


    public static void CreateUser(String DeviceToken, String battery, String DeviceName, String MasterId) {


//TODO add devicename based on input from 1 run.
//        String DeviceName = "FredagDevice";
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("DeviceName", DeviceName);
        if (DeviceToken != null) {
            user.put("DeviceToken", DeviceToken);
        }
        user.put("DeviceMaster", MasterId);
        if (battery.isEmpty()) {
            user.put("BatteryStatus", 0);
        } else {
            user.put("BatteryStatus", battery);
        }
        user.put("Responding", true);
        user.put("RespTime", new Date());

        DocumentReference db = FirebaseFirestore.getInstance().document("Users/" + DeviceName);

        db.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.v(TAG, "Document has ben saved");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Document has NOT saved");
                    }
                })


        ;


    }
}
