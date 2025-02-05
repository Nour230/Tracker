package com.example.testtracker.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testtracker.R;
import com.google.android.material.button.MaterialButton;

public class LoginFragment extends Fragment {
MaterialButton login;
MaterialButton Skip;
TextView signup;


    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = view.findViewById(R.id.btnLogin);
        Skip = view.findViewById(R.id.gustLogin);
        signup = view.findViewById(R.id.signup);
        //logic////////////////////////////////////////////
        signup.setOnClickListener(v->{
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_registerFragment);
        });
        Skip.setOnClickListener(v->{
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_homeFragment);
        });
        login.setOnClickListener(v->{
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment_to_homeFragment);
        });
    }
}