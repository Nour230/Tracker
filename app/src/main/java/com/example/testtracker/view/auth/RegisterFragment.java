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
import com.example.testtracker.presenter.auth.signup.RegisterPresenter;
import com.example.testtracker.presenter.auth.signup.RegisterPresenterImpl;
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

import java.util.Objects;


public class RegisterFragment extends Fragment implements RegisterView {
    MaterialButton signup;
    EditText email, pass,confirmpass;
    private static final String TAG = "MainActivity";
    private FirebaseAuth myauth;
    private GoogleSignInClient googleSignInClient;
    MaterialButton Skip;
    TextView login;
    RegisterPresenter presenter;
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
        confirmpass = view.findViewById(R.id.confirmpass);
        google = view.findViewById(R.id.googleregister);
        login = view.findViewById(R.id.login);

        myauth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
        presenter = new RegisterPresenterImpl(this);
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
            signUp(email.getText().toString(),
                    pass.getText().toString(),
                    confirmpass.getText().toString()
                    );
        });
        google.setOnClickListener(v->{
            signIn();
        });

    }


    private void signUp(String email, String pass, String confirmpass) {
        if(pass.equals(confirmpass)){
           presenter.register(email,pass);
        }
        else {
            //display error
        }
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
            presenter.handleGoogleSignInResult(data);
        }
    }
    @Override
    public void registerSuccess() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_registerFragment_to_homeFragment);
    }

    @Override
    public void registerFailure(String errorMessage) {
        Log.i(TAG, "registerFailure: "+errorMessage);
    }
    @Override
    public void googleSignInSuccess() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_registerFragment_to_homeFragment);
    }

    @Override
    public void googleSignInFailure(String errorMessage) {
        Log.i(TAG, "registerFailure: "+errorMessage);
    }


}