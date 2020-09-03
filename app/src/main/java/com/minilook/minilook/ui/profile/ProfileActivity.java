package com.minilook.minilook.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
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

    @BindView(R.id.edit_nick) EditText nickEditText;
    @BindView(R.id.txt_phone) TextView phoneTextView;
    @BindView(R.id.txt_email) TextView emailTextView;

    @BindView(R.id.txt_shipping_name) TextView shippingNameTextView;
    @BindView(R.id.txt_shipping_phone) TextView shippingPhoneTextView;
    @BindView(R.id.txt_shipping_address) TextView shippingAddressTextView;

    @BindString(R.string.profile_shipping_address) String format_address;

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

    @Override public void setupNick(String text) {
        nickEditText.setText(text);
    }

    @Override public void setupPhone(String text) {
        phoneTextView.setText(text);
    }

    @Override public void setupEmail(String text) {
        emailTextView.setText(text);
    }

    @Override public void setupShippingName(String text) {
        shippingNameTextView.setText(text);
    }

    @Override public void setupShippingPhone(String text) {
        shippingPhoneTextView.setText(text);
    }

    @Override public void setupShippingAddress(String zipcode, String address, String address_detil) {
        shippingAddressTextView.setText(String.format(format_address, zipcode, address, address_detil));
    }

    @OnClick(R.id.txt_shipping_edit)
    void onShippingEditClick() {
        ShippingActivity.start(this);
    }
}
