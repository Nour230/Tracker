package com.example.testtracker.view.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testtracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
MaterialButton login;
MaterialButton Skip;
TextView signup;
    EditText email, pass;

    ImageButton google;

    private static final String TAG = "MainActivity";
    private FirebaseAuth myauth;
    private GoogleSignInClient googleSignInClient;

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
        email = view.findViewById(R.id.edtEmail);
        pass = view.findViewById(R.id.edtPass);
        signup = view.findViewById(R.id.signup);
        myauth = FirebaseAuth.getInstance();
        google = view.findViewById(R.id.googleLogin);
        googleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);

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
            login(v);
        });
    }

    private void login(View view) {
        myauth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_homeFragment);

                Log.i(TAG, "signInWithGoogle Success: ");
            } else {
                Log.i(TAG, "signInWithGoogle Fail: "+task.getException().getMessage());
            }
        });
    }
}