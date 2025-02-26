package com.example.a310_rondayview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a310_rondayview.databinding.ActivityMainBinding;
import com.example.a310_rondayview.ui.account.ProfileFragment;
import com.example.a310_rondayview.ui.createevent.CreateEventFragment;
import com.example.a310_rondayview.ui.friends.FriendsFragment;
import com.example.a310_rondayview.ui.home.FragmentHome;
import com.example.a310_rondayview.ui.interestedevents.InterestedEventsFragment;
import com.example.a310_rondayview.ui.popular.PopularEventsFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User is not signed in, redirect to login
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish(); // Prevents users from coming back to this activity when back button is pressed
            return;
        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FragmentHome());
        ImageButton createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(v -> replaceFragment(new CreateEventFragment()));

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.friends) {
                replaceFragment(new FriendsFragment());
            } else if (item.getItemId() == R.id.browse) {
                replaceFragment(new FragmentHome());
            } else if (item.getItemId() == R.id.account) {
                replaceFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.interested) {
                replaceFragment(new InterestedEventsFragment());
            } else if (item.getItemId() == R.id.popular) {
                replaceFragment(new PopularEventsFragment());
            }


            return true;
        });
        binding.bottomNavigationView.getMenu().findItem(R.id.browse).setChecked(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
