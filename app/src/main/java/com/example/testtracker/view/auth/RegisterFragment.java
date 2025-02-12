package com.example.testtracker.view.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtracker.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;


public class RegisterFragment extends Fragment {
    MaterialButton signup;
    EditText email, pass;
    private static final String TAG = "MainActivity";
    private FirebaseAuth myauth;
    private GoogleSignInClient googleSignInClient;
    MaterialButton Skip;
    TextView login;
    ImageButton google;
    public RegisterFragment() {
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signup = view.findViewById(R.id.btnSignUp);
        Skip = view.findViewById(R.id.gustRegister);
        email = view.findViewById(R.id.edtEmail);
        pass = view.findViewById(R.id.edtPass);
        login = view.findViewById(R.id.login);
        myauth = FirebaseAuth.getInstance();
        google = view.findViewById(R.id.googleLogin);
        googleSignInClient = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);
        //logic////////////////////////////////////////////
        login.setOnClickListener(v->{
            Navigation.findNavController(view)
                    .navigate(R.id.action_registerFragment_to_loginFragment);
        });
        Skip.setOnClickListener(v->{
            Navigation.findNavController(view)
                    .navigate(R.id.action_registerFragment_to_homeFragment);
        });
        signup.setOnClickListener(v->{
            signUp(v);
        });
        google.setOnClickListener(v->{
            signIn();
        });

    }


    private void signUp(View view) {
        myauth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Navigation.findNavController(view)
                        .navigate(R.id.action_registerFragment_to_homeFragment);
                Log.i(TAG, "signIn Success: ");
            } else {
                Log.i(TAG, "signIn Fail: "+task.getException().getMessage());
            }

        });
    }

    private void signout() {
        myauth.signOut();
    }



    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        myauth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.i(TAG, "signInWithGoogle Success: ");
            } else {
                Log.i(TAG, "signInWithGoogle Fail: "+task.getException().getMessage());
            }
        });
    }
}