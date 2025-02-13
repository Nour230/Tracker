package com.example.testtracker.presenter.auth.login;

import android.content.Intent;

public interface LoginPresenter {
    public void login(String email, String pass);
    void handleGoogleSignInResult(Intent data);
}
