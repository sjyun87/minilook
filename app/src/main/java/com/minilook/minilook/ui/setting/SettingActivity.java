package com.minilook.minilook.ui.setting;

import android.content.Context;
import android.content.Intent;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.setting.di.SettingArguments;
import com.suke.widget.SwitchButton;

public class SettingActivity extends BaseActivity implements SettingPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.sb_info) SwitchButton infoNotifySwitchButton;
    @BindView(R.id.sb_marketing) SwitchButton marketingSwitchButton;

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

    @Override public void setupInfoSwitchButton() {
        infoNotifySwitchButton.setOnCheckedChangeListener(
            (view, isChecked) -> presenter.onInfoNotifyChecked(isChecked));
    }

    @Override public void setupMarketingSwitchButton() {
        marketingSwitchButton.setOnCheckedChangeListener(
            (view, isChecked) -> presenter.onMarketingChecked(isChecked));
    }

    @OnClick(R.id.txt_logout)
    void onLogoutClick() {
        presenter.onLogoutClick();
    }
}
