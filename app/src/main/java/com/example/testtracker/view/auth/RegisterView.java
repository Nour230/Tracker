package com.example.testtracker.view.auth;

public interface RegisterView {
    public void registerSuccess();
    public void registerFailure(String errorMessage);
    void googleSignInSuccess();
    void googleSignInFailure(String errorMessage);
}
