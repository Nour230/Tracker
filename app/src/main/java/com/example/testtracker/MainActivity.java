package com.example.testtracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.BTN_NAV_View);

        // Find the NavHostFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragmentContainerView);

        // Get the NavController from the NavHostFragment
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Set up BottomNavigationView with NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {

                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    if (navDestination.getId() == R.id.homeFragment ||
                            navDestination.getId() == R.id.searchFragment ||
                            navDestination.getId() == R.id.planFragment ||
                            navDestination.getId() == R.id.favFragment) {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    } else {
                        bottomNavigationView.setVisibility(View.GONE);
                    }
                }
            });
        } else {
            Log.e(TAG, "NavHostFragment is null. Check your layout and navigation setup.");


        }
    }
}