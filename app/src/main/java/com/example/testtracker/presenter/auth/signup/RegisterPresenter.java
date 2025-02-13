package com.example.testtracker.presenter.auth.signup;

import android.content.Intent;

public interface RegisterPresenter {
    public void register(String email, String pass);
    void handleGoogleSignInResult(Intent data);
}
