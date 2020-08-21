package com.minilook.minilook.ui.profile;

import android.content.Context;
import android.content.Intent;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.profile.di.ProfileArguments;
import com.minilook.minilook.ui.shipping.ShippingActivity;

public class ProfileActivity extends BaseActivity implements ProfilePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ProfilePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_profile;
    }

    @Override protected void createPresenter() {
        presenter = new ProfilePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ProfileArguments provideArguments() {
        return ProfileArguments.builder()
            .view(this)
            .build();
    }

    @OnClick(R.id.txt_shipping_edit)
    void onShippingEditClick() {
        ShippingActivity.start(this);
    }
}