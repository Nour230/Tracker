package com.example.testtracker.view.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testtracker.R;

public class SplashFragment extends Fragment {
    SharedPreferences sharedPreferences;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLogged = sharedPreferences.getBoolean("isLogged", false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isLogged){
                    Navigation.findNavController(view)
                            .navigate(R.id.action_splashFragment_to_homeFragment);
                }else {
                    Navigation.findNavController(view)
                            .navigate(R.id.action_splashFragment_to_loginFragment);
                }

            }
        },3000);
    }
}