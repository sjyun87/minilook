package com.minilook.minilook.ui.login.listener;

public interface OnSNSLoginListener {
    void onSNSLogin(String sns_id, String email, String type);

    void onSNSLogout();

    void onSNSError(int errorCode, String message);
}
