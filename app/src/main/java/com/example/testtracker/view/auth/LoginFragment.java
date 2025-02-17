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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.testtracker.R;
import com.example.testtracker.presenter.auth.login.LoginPresenter;
import com.example.testtracker.presenter.auth.login.LoginPresrnterImpl;
import com.example.testtracker.view.forms.Dialog;
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
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                // Show the alert dialog if fields are empty
                Dialog.showAlertDialog(requireContext(), getString(R.string.please_fill_in_all_the_fields));
            } else {
                // Proceed with login
                presenter.login(email, password);
            }
        });

        googleLoginButton.setOnClickListener(v -> {
            signIn();
        });
    }

    @Override
    public void loginSuccess() {
        String id = presenter.getid();
        sharedPreferences.edit().putString("email", emailEditText.getText().toString()).apply();
        sharedPreferences.edit().putString("pass", passwordEditText.getText().toString()).apply();
        sharedPreferences.edit().putString("id", id).apply();
        sharedPreferences.edit().putBoolean("isLogged", true).apply();
        Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @Override
    public void loginFailure(String errorMessage) {
        Dialog.showAlertDialog(requireContext(), getString(R.string.wrong_email_or_password));
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