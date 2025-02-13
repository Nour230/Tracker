package com.example.testtracker.presenter.auth.signup;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testtracker.view.auth.LoginView;
import com.example.testtracker.view.auth.RegisterView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterPresenterImpl implements RegisterPresenter{
    private FirebaseAuth myauth;

    private RegisterView view;
    public RegisterPresenterImpl(RegisterView view) {
        this.view = view;
        myauth = FirebaseAuth.getInstance();
    }
    @Override
    public void register(String email, String pass) {
        myauth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(item ->{
                   if(item.isSuccessful()){
                       view.registerSuccess();
                   }else{
                       view.registerFailure(item.getException().getMessage());
                   }
                });
    }



    @Override
    public void handleGoogleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null && account.getIdToken() != null) {
                signInWithGoogle(account.getIdToken());
            } else {
                view.googleSignInFailure("ID token is null");
            }
        } catch (ApiException e) {
            Log.e("GoogleSignIn", "Google Sign-In failed", e);
            view.googleSignInFailure("Google Sign-In failed");
        }
    }

    private void signInWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        myauth.signInWithCredential(authCredential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                view.googleSignInSuccess();
            } else {
                view.registerFailure(task.getException().getMessage());
            }
        });
    }
}
