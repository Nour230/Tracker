package com.example.testtracker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    FragmentContainerView home;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    // Network monitoring variables
    private ConnectivityManager.NetworkCallback networkCallback;
    private ConnectivityManager connectivityManager;
    private MutableLiveData<Boolean> isNetworkAvailable = new MutableLiveData<>();

    // Lottie Animation View
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.BTN_NAV_View);
        lottieAnimationView = findViewById(R.id.lottieanimation);
        home = findViewById(R.id.fragmentContainerView);

        // Find the NavHostFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragmentContainerView);

        // Get the NavController from the NavHostFragment
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Set up BottomNavigationView with NavController
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
                @Override
                public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                    // Update bottom navigation visibility
                    if (navDestination.getId() == R.id.homeFragment ||
                            navDestination.getId() == R.id.searchFragment ||
                            navDestination.getId() == R.id.planFragment ||
                            navDestination.getId() == R.id.favFragment) {
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    } else {
                        bottomNavigationView.setVisibility(View.GONE);
                    }

                    // Update FragmentContainerView and Lottie animation visibility based on network status
                    updateFragmentVisibility(navDestination.getId());
                }
            });
        } else {
            Log.e(TAG, "NavHostFragment is null. Check your layout and navigation setup.");
        }

        // Register network callback
        registerNetworkCallback();

        // Observe network status
        isNetworkAvailable.observe(this, isAvailable -> {
            if (navController != null) {
                NavDestination currentDestination = navController.getCurrentDestination();
                if (currentDestination != null) {
                    // Update FragmentContainerView and Lottie animation visibility based on network status
                    updateFragmentVisibility(currentDestination.getId());
                }
            }
        });
    }

    /**
     * Updates the visibility of FragmentContainerView and Lottie animation based on the current fragment and network status.
     *
     * @param fragmentId The ID of the current fragment.
     */
    private void updateFragmentVisibility(int fragmentId) {
        Boolean isAvailable = isNetworkAvailable.getValue();
        if (isAvailable != null && isAvailable) {
            // Network is available
            home.setVisibility(View.VISIBLE); // Show the FragmentContainerView
            lottieAnimationView.setVisibility(View.GONE); // Hide Lottie animation
            lottieAnimationView.pauseAnimation(); // Pause animation
            Log.d(TAG, "Network is available: FragmentContainerView is visible, Lottie animation is hidden.");
        } else {
            // Network is lost
            Log.d(TAG, "Network is lost: Checking current fragment...");

            if (fragmentId == R.id.favFragment || fragmentId == R.id.planFragment) {
                home.setVisibility(View.VISIBLE); // Ensure these fragments stay visible
                lottieAnimationView.setVisibility(View.GONE); // Hide animation
                lottieAnimationView.pauseAnimation();
                Log.d(TAG, "In favFragment or planFragment: FragmentContainerView is visible, Lottie animation is hidden.");
            } else {
                // If not in favFragment or planFragment, hide the home view and show animation
                home.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                Log.d(TAG, "Not in favFragment or planFragment: FragmentContainerView is hidden, Lottie animation is visible.");
            }
        }
    }

    /**
     * Helper method to map fragment ID to fragment name.
     */
    private String getFragmentName(int fragmentId) {
        if (fragmentId == R.id.homeFragment) {
            return "HomeFragment";
        } else if (fragmentId == R.id.searchFragment) {
            return "SearchFragment";
        } else if (fragmentId == R.id.planFragment) {
            return "PlanFragment";
        } else if (fragmentId == R.id.favFragment) {
            return "FavFragment";
        } else {
            return "UnknownFragment";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister network callback to avoid memory leaks
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    /**
     * Registers a network callback to monitor network connectivity changes.
     */
    private void registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                runOnUiThread(() -> {
                    isNetworkAvailable.postValue(true); // Update LiveData
                    Toast.makeText(MainActivity.this, "Network connected", Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onLost(@NonNull Network network) {
                runOnUiThread(() -> {
                    isNetworkAvailable.postValue(false); // Update LiveData
                    Toast.makeText(MainActivity.this, "Network disconnected", Toast.LENGTH_SHORT).show();
                });
            }
        };

        // Build a network request to monitor internet capability
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        // Register the network callback
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    /**
     * Exposes the network status LiveData to Fragments.
     *
     * @return LiveData<Boolean> representing network availability.
     */
    public MutableLiveData<Boolean> getIsNetworkAvailable() {
        return isNetworkAvailable;
    }
}