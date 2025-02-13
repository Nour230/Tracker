package com.example.testtracker.view.auth;

public interface LoginView {
    public void loginSuccess() ;
    public void loginFailure(String errorMessage);
    void googleSignInSuccess();


}
