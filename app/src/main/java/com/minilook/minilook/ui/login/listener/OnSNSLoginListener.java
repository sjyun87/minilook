package com.minilook.minilook.ui.login.listener;

public interface OnSNSLoginListener {
    void onSNSLogin(String snsId, String email, String type);

    void onSNSLogout();

    void onSNSError(int errorCode, String message);
}
