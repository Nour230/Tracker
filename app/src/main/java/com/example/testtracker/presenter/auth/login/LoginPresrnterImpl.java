package com.example.testtracker.presenter.auth.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.EditText;

import com.example.testtracker.view.auth.LoginView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresrnterImpl implements LoginPresenter {
    private FirebaseAuth myauth;

    private LoginView view;

    public LoginPresrnterImpl(LoginView view) {
        this.view = view;
        myauth = FirebaseAuth.getInstance();
    }


    @Override
    public void login(String email, String pass) {

        myauth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(
                        task -> {
                            if (task.isSuccessful()) {
                                view.loginSuccess();
                            } else {
                                view.loginFailure(task.getException().getMessage());
                            }
                        });
    }

    @Override
    public String getid() {
        FirebaseUser currentUser = myauth.getCurrentUser();
        String id = currentUser.getUid();
        return  id;
    }

    @Override
    public void handleGoogleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null && account.getIdToken() != null) {
                signInWithGoogle(account.getIdToken());
            } else {
                view.loginFailure("ID token is null");
            }
        } catch (ApiException e) {
            Log.e("GoogleSignIn", "Google Sign-In failed", e);
            view.loginFailure("Google Sign-In failed");
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        myauth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.googleSignInSuccess();
            } else {
                view.loginFailure(task.getException().getMessage());
            }
        });
    }
}

