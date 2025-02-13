package com.example.testtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testtracker.databinding.ActivityMainBinding;
import com.example.testtracker.view.fav.FavFragment;
import com.example.testtracker.view.home.HomeFragment;
import com.example.testtracker.view.plan.PlanFragment;
import com.example.testtracker.view.search.SearchFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    static ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Initialize binding and set content view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up BottomNavigationView listener
        binding.BTNNAVView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            Log.d(TAG, "Selected item ID: " + itemId);
            if (itemId == R.id.home) {
                replacefregment(new HomeFragment());
            } else if (itemId == R.id.search) {
                replacefregment(new SearchFragment());
            } else if (itemId == R.id.fav) {
                replacefregment(new FavFragment());
            } else if (itemId == R.id.plan) {
                replacefregment(new PlanFragment());
            }
            return true;
        });

    }

    private void replacefregment(Fragment fragment) {
        if (fragment instanceof HomeFragment || fragment instanceof SearchFragment ||
                fragment instanceof FavFragment || fragment instanceof PlanFragment) {
            binding.BTNNAVView.setVisibility(View.VISIBLE);
        } else {
            binding.BTNNAVView.setVisibility(View.GONE);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
        fragmentTransaction.commit();
    }


}