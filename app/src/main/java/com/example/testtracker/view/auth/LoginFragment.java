package com.example.testtracker.view.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.testtracker.R;
import com.example.testtracker.presenter.auth.login.LoginPresenter;
import com.example.testtracker.presenter.auth.login.LoginPresrnterImpl;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements LoginView {
    private static final String TAG = "MainActivity";
    private MaterialButton loginButton, skipButton;
    private TextView signupTextView;
    private EditText emailEditText, passwordEditText;

    private FirebaseAuth myauth;

    SharedPreferences sharedPreferences;
    private GoogleSignInClient googleSignInClient;

    private ImageButton googleLoginButton;
    LoginPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginButton = view.findViewById(R.id.btnLogin);
        skipButton = view.findViewById(R.id.gustLogin);
        emailEditText = view.findViewById(R.id.edtEmail);
        passwordEditText = view.findViewById(R.id.edtPass);
        signupTextView = view.findViewById(R.id.signup);
        googleLoginButton = view.findViewById(R.id.googleLogin);
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        myauth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        presenter = new LoginPresrnterImpl(this);

        signupTextView.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment));

        skipButton.setOnClickListener(v -> Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_homeFragment));

        loginButton.setOnClickListener(v -> {
                    presenter.login(
                            emailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                    sharedPreferences.edit().putString("email", emailEditText.getText().toString()).apply();
                    sharedPreferences.edit().putString("pass", passwordEditText.getText().toString()).apply();
                    sharedPreferences.edit().putBoolean("isLogged", true).apply();
        }
        );
        googleLoginButton.setOnClickListener(v->{
            signIn();
        });
    }

    @Override
    public void loginSuccess() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void loginFailure(String errorMessage) {
        Log.i(TAG, "loginFailure: "+errorMessage.toString());
    }
    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 123);
    }
    @Override
    public void googleSignInSuccess() {
        Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_homeFragment);
    }
}