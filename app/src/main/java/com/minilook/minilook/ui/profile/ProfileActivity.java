package com.minilook.minilook.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.CustomToast;
import com.minilook.minilook.ui.profile.di.ProfileArguments;
import com.minilook.minilook.ui.verify.VerifyActivity;
import com.minilook.minilook.ui.shipping.ShippingActivity;
import com.minilook.minilook.util.KeyboardUtil;

public class ProfileActivity extends BaseActivity implements ProfilePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @BindView(R.id.edit_nick) EditText nickEditText;
    @BindView(R.id.img_nick_clear) ImageView nickClearImageView;
    @BindView(R.id.txt_nick_check_msg) TextView nickCheckMsgTextView;
    @BindView(R.id.txt_nick_save) TextView nickSaveTextView;
    @BindView(R.id.txt_phone) TextView phoneTextView;
    @BindView(R.id.txt_email) TextView emailTextView;

    @BindView(R.id.layout_shipping_panel) LinearLayout shippingPanel;
    @BindView(R.id.txt_shipping_name) TextView shippingNameTextView;
    @BindView(R.id.txt_shipping_phone) TextView shippingPhoneTextView;
    @BindView(R.id.txt_shipping_address) TextView shippingAddressTextView;
    @BindView(R.id.txt_shipping_empty) TextView emptyShippingTextView;

    @BindString(R.string.base_toast_update_completed) String str_toast_update_completed;
    @BindString(R.string.profile_nick_check_unavailable) String str_nick_update_error;
    @BindString(R.string.profile_shipping_address) String format_address;
    @BindString(R.string.base_pattern) String pattern;

    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;
    @BindColor(R.color.color_FFEEEFF5) int color_FFEEEFF5;

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

    @Override public void setupEditText() {
        InputFilter[] filters = new InputFilter[] {
            (source, start, end, dest, dstart, dend) -> source.toString().matches(pattern) ? source : ""
        };
        nickEditText.setFilters(filters);
        nickEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setupNick(String text) {
        nickEditText.setText(text);
    }

    @Override public void showCheckMessage(String message) {
        nickCheckMsgTextView.setText(message);
        nickCheckMsgTextView.setVisibility(View.VISIBLE);
    }

    @Override public void showCheckErrorMessage() {
        nickCheckMsgTextView.setText(str_nick_update_error);
        nickCheckMsgTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideCheckMessage() {
        nickCheckMsgTextView.setVisibility(View.GONE);
    }

    @Override public void setupPhone(String text) {
        phoneTextView.setText(text);
    }

    @Override public void setupEmail(String text) {
        emailTextView.setText(text);
    }

    @Override public void showShippingPanel() {
        shippingPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideShippingPanel() {
        shippingPanel.setVisibility(View.GONE);
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

    @Override public void showEmptyShippingText() {
        emptyShippingTextView.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyShippingText() {
        emptyShippingTextView.setVisibility(View.GONE);
    }

    @Override public void enableNickSaveButton() {
        nickSaveTextView.setBackgroundColor(color_FF8140E5);
        nickSaveTextView.setEnabled(true);
    }

    @Override public void disableNickSaveButton() {
        nickSaveTextView.setBackgroundColor(color_FFEEEFF5);
        nickSaveTextView.setEnabled(false);
    }

    @Override public void showNickClearButton() {
        nickClearImageView.setVisibility(View.VISIBLE);
    }

    @Override public void hideNickClearButton() {
        nickClearImageView.setVisibility(View.GONE);
    }

    @Override public void hideKeyboard() {
        KeyboardUtil.hide(nickEditText);
        nickEditText.clearFocus();
    }

    @Override public void showUpdateCompletedToast() {
        CustomToast.make(this, str_toast_update_completed).show();
    }

    @Override public void navigateToWebView(String url) {
        VerifyActivity.start(this);
    }

    @Override public void navigateToShipping() {
        ShippingActivity.start(this, ProfileActivity.class.getSimpleName());
    }

    @OnClick(R.id.img_nick_clear)
    void onNickClearClick() {
        presenter.onNickClearClick();
    }

    @OnClick(R.id.txt_nick_save)
    void onNickSaveClick() {
        presenter.onNickSaveClick();
        KeyboardUtil.hide(nickEditText);
    }

    @OnClick(R.id.txt_phone_edit)
    void onPhoneEditClick() {
        presenter.onPhoneEditClick();
    }

    @OnClick(R.id.txt_shipping_management)
    void onShippingManagementClick() {
        presenter.onShippingManagementClick();
    }
}
