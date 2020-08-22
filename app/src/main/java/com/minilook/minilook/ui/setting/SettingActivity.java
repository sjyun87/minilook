package com.minilook.minilook.ui.setting;

import android.content.Context;
import android.content.Intent;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.setting.di.SettingArguments;

public class SettingActivity extends BaseActivity implements SettingPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private SettingPresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_setting;
    }

    @Override protected void createPresenter() {
        presenter = new SettingPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private SettingArguments provideArguments() {
        return SettingArguments.builder()
            .view(this)
            .build();
    }
}
