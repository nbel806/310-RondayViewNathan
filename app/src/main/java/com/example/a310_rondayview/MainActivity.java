package com.example.a310_rondayview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.example.a310_rondayview.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private static final String TAG = "FirestoreTest";
    private FirebaseFirestore firestore;

    // TODO ("replaceFragment(new CreateEventFragment());") replace CreateEventFragment with
    // the homepage once the homepage is made
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CreateEventFragment());

        // to add a new actiity to the fragment navbar, add an elseif statement here linking the
        // navbar button to the desired activity
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.create) {
                replaceFragment(new CreateEventFragment());
            }

            return true;
        });
      
        // example
        firestore = FirebaseFirestore.getInstance();

        // Data to be added to the new document
        Map<String, Object> newEventData = new HashMap<>();
        newEventData.put("eventName", "Dummy Test");
        newEventData.put("eventDate", "Test Date");

        addNewEvent(newEventData);

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}

    private void addNewEvent(Map<String, Object> eventData) {
        firestore.collection("events").add(eventData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "New event added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding new event", e);
                    }
                });
    }
}