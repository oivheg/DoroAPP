package no.oivheg.DoroApp;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class User {

  public static  String  DeviceName;
    public static void CreateUser(String DeviceToken,String DvName, String battery) {
//TODO add devicename based on input from 1 run.
         DeviceName = DvName;
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("DeviceName", DeviceName);
        if (DeviceToken != null) {
            user.put("DeviceToken", DeviceToken);
        }
        user.put("DeviceMaster", "Master");
        if (battery.isEmpty()) {
            user.put("BatteryStatus", 0);
        } else {
            user.put("BatteryStatus", battery);
        }

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

//        db.collection("users")
//                .add(user)
//
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(TAG, "Error adding document", e);
//                    }
//                });

    }
}
