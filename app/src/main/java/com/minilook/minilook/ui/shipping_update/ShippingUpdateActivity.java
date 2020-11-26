package com.minilook.minilook.ui.shipping_update;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.ui.base._BaseActivity;
import com.minilook.minilook.ui.base.widget.TitleBar;
import com.minilook.minilook.ui.search_zip.SearchZipActivity;
import com.minilook.minilook.ui.shipping_update.di.ShippingUpdateArguments;

public class ShippingUpdateActivity extends _BaseActivity implements ShippingUpdatePresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, ShippingUpdateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public static void start(Context context, ShippingDataModel data) {
        Intent intent = new Intent(context, ShippingUpdateActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("shipping", data);
        context.startActivity(intent);
    }

    @BindView(R.id.titlebar) TitleBar titleBar;
    @BindView(R.id.edit_name) EditText nameEditText;
    @BindView(R.id.edit_phone) EditText phoneEditText;
    @BindView(R.id.txt_zip) TextView zipTextView;
    @BindView(R.id.txt_address) TextView addressTextView;
    @BindView(R.id.edit_address_detail) EditText addressDetailEditText;
    @BindView(R.id.img_checkbox) ImageView checkImageView;
    @BindView(R.id.txt_save) TextView saveTextView;

    @BindString(R.string.shipping_edit_title) String str_shipping_edit_title;

    @BindDrawable(R.drawable.ic_checkbox1_off) Drawable img_check_off;
    @BindDrawable(R.drawable.ic_checkbox1_on) Drawable img_check_on;

    @BindColor(R.color.color_FFF5F5F5) int color_FFF5F5F5;
    @BindColor(R.color.color_FF8140E5) int color_FF8140E5;

    private ShippingUpdatePresenter presenter;

    @Override protected int getLayoutID() {
        return R.layout.activity_shipping_update;
    }

    @Override protected void createPresenter() {
        presenter = new ShippingUpdatePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ShippingUpdateArguments provideArguments() {
        return ShippingUpdateArguments.builder()
            .view(this)
            .shippingData((ShippingDataModel) getIntent().getSerializableExtra("shipping"))
            .build();
    }

    @Override public void setupShippingEditTitleBar() {
        titleBar.setTitle(str_shipping_edit_title);
    }

    @Override public void setupNameEditText() {
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onNameTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setupPhoneEditText() {
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onPhoneTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setupAddressDetailEditText() {
        addressDetailEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start == 0 && s.toString().equals(" ")) {
                    addressDetailEditText.setText("");
                    addressDetailEditText.setSelection(0);
                } else {
                    presenter.onAddressDetailTextChanged(s.toString());
                }
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void checkDefault() {
        checkImageView.setImageDrawable(img_check_on);
    }

    @Override public void uncheckDefault() {
        checkImageView.setImageDrawable(img_check_off);
    }

    @Override public void enableSaveButton() {
        saveTextView.setEnabled(true);
        saveTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableSaveButton() {
        saveTextView.setEnabled(false);
        saveTextView.setBackgroundColor(color_FFF5F5F5);
    }

    @Override public void navigateToSearchZip() {
        SearchZipActivity.start(this);
    }

    @Override public void setupName(String text) {
        nameEditText.setText(text);
    }

    @Override public void setupPhone(String text) {
        phoneEditText.setText(text);
    }

    @Override public void setupZip(String text) {
        zipTextView.setText(text);
    }

    @Override public void setupAddress(String text) {
        addressTextView.setText(text);
    }

    @Override public void setupAddressDetail(String text) {
        addressDetailEditText.setText(text);
    }

    @OnClick(R.id.txt_search_zip)
    void onSearchZipClick() {
        presenter.onSearchZipClick();
    }

    @OnClick({ R.id.img_checkbox, R.id.txt_default })
    void onDefaultClick() {
        presenter.onDefaultClick();
    }

    @OnClick(R.id.txt_save)
    void onSaveClick() {
        presenter.onSaveClick();
    }
}
