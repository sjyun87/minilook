package com.minilook.minilook.ui.splash;

import android.os.Handler;
import com.minilook.minilook.App;
import com.minilook.minilook.data.prefs_key.PrefsKey;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.splash.di.SplashArguments;
import com.pixplicity.easyprefs.library.Prefs;

public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private final View view;

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        checkLogin();
        navigateToMain();
    }

    private void checkLogin() {
        int memberNo = Prefs.getInt(PrefsKey.KEY_MEMBER_NO, -1);
        if (memberNo == -1) {
            App.getInstance().setLogin(false);
        } else {
            App.getInstance().setLogin(true);
            App.getInstance().setMemberNo(memberNo);
        }
    }

    private void navigateToMain() {
        new Handler().postDelayed(() -> {
            view.navigateToMain();
            view.finish();
        }, 3000);
    }
}