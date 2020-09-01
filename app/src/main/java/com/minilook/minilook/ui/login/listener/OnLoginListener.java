package com.minilook.minilook.ui.login.listener;

public interface OnLoginListener {
    void onLogin(String sns_id, String email, String type);

    void onLogout();

    void onError(int errorCode, String message);
}
