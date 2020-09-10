package com.minilook.minilook.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import butterknife.BindString;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.guide.GuideActivity;
import com.minilook.minilook.ui.main.MainActivity;
import com.minilook.minilook.ui.splash.di.SplashArguments;
import com.minilook.minilook.util.HashKeyUtil;
import java.util.List;

public class SplashActivity extends BaseActivity implements SplashPresenter.View {

    @BindString(R.string.base_permission) String str_permission;

    private SplashPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_splash;
    }

    @Override protected void createPresenter() {
        //HashKeyUtil.getHashKey(this);
        presenter = new SplashPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SplashArguments provideArguments() {
        return SplashArguments.builder()
            .view(this)
            .build();
    }

    @Override public void checkPermission() {
        TedPermission.with(this)
            .setPermissionListener(new PermissionListener() {
                @Override public void onPermissionGranted() {
                    presenter.onPermissionGranted();
                }

                @Override public void onPermissionDenied(List<String> deniedPermissions) {
                }
            })
            .setDeniedMessage(str_permission)
            .setPermissions(Manifest.permission.READ_PHONE_STATE)
            .check();
    }

    @Override public void showUpdateDialog() {
        DialogManager.showUpdateDialog(this, presenter::onUpdateDialogOkClick, presenter::onUpdateDialogCancelClick);
    }

    @Override public void navigateToPlatStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(intent);
        finish();
    }

    @Override public void navigateToGuide() {
        GuideActivity.start(this);
    }

    @Override public void navigateToMain() {
        MainActivity.start(this);
    }

    @Override public void onBackPressed() {
    }
}
