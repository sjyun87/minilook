package com.minilook.minilook.ui.review_write;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.minilook.minilook.R;
import com.minilook.minilook.data.code.GenderCode;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizes;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.databinding.ActivityReviewWriteBinding;
import com.minilook.minilook.ui.album.GalleryActivity;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;
import com.minilook.minilook.util.PermissionUtil;
import java.util.List;

public class ReviewWriteActivity extends BaseActivity implements ReviewWritePresenter.View {

    public static void start(Context context, String orderNo, String orderDate, OrderProductDataModel data) {
        Intent intent = new Intent(context, ReviewWriteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("orderNo", orderNo);
        intent.putExtra("orderDate", orderDate);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @StringRes int str_format_option = R.string.review_write_option;
    @StringRes int str_review_write = R.string.toast_review_write;

    @ColorRes int color_FFA9A9A9 = R.color.color_FFA9A9A9;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    @DrawableRes int ph_square = R.drawable.ph_square;
    @DrawableRes int img_good_on = R.drawable.ic_review_good_on;
    @DrawableRes int img_good_off = R.drawable.ic_review_good_off;
    @DrawableRes int img_normal_on = R.drawable.ic_review_normal_on;
    @DrawableRes int img_normal_off = R.drawable.ic_review_normal_off;
    @DrawableRes int img_bad_on = R.drawable.ic_review_bad_on;
    @DrawableRes int img_bad_off = R.drawable.ic_review_bad_off;

    @DrawableRes int img_button_on = R.drawable.bg_border_round_purple;
    @DrawableRes int img_button_off = R.drawable.bg_border_round_gray;

    @FontRes int font_regular = R.font.nanum_square_r;
    @FontRes int font_bold = R.font.nanum_square_b;

    private ActivityReviewWriteBinding binding;
    private ReviewWritePresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityReviewWriteBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ReviewWritePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private ReviewWriteArguments provideArguments() {
        return ReviewWriteArguments.builder()
            .view(this)
            .orderNo(getIntent().getStringExtra("orderNo"))
            .orderDate(getIntent().getStringExtra("orderDate"))
            .data((OrderProductDataModel) getIntent().getSerializableExtra("data"))
            .build();
    }

    @Override public void setupClickAction() {
        binding.imgGood.setOnClickListener(
            view -> presenter.onSatisfactionClick(ReviewSatisfactions.GOOD.getCode()));
        binding.imgNormal.setOnClickListener(
            view -> presenter.onSatisfactionClick(ReviewSatisfactions.NORMAL.getCode()));
        binding.imgBad.setOnClickListener(
            view -> presenter.onSatisfactionClick(ReviewSatisfactions.BAD.getCode()));

        binding.txtVeryBig.setOnClickListener(view -> presenter.onSizeClick(ReviewSizes.VERY_BIG.getCode()));
        binding.txtLittleBig.setOnClickListener(view -> presenter.onSizeClick(ReviewSizes.LITTLE_BIG.getCode()));
        binding.txtPerfectly.setOnClickListener(view -> presenter.onSizeClick(ReviewSizes.PERFECTLY.getCode()));
        binding.txtLittleSmall.setOnClickListener(view -> presenter.onSizeClick(ReviewSizes.LITTLE_SMALL.getCode()));
        binding.txtVerySmall.setOnClickListener(view -> presenter.onSizeClick(ReviewSizes.VERY_SMALL.getCode()));

        binding.txtGenderMale.setOnClickListener(view -> presenter.onGenderClick(GenderCode.MALE.getCode()));
        binding.txtGenderFemale.setOnClickListener(view -> presenter.onGenderClick(GenderCode.FEMALE.getCode()));

        binding.txtAge.setOnClickListener(view -> presenter.onAgeInputClick());

        binding.layoutPhotoEmptyPanel.setOnClickListener(view -> presenter.onPhotoAddClick());
    }

    @Override public void setOrderNo(String orderNo) {
        binding.txtOrderNo.setText(orderNo);
    }

    @Override public void setOrderDate(String orderDate) {
        binding.txtOrderDate.setText(orderDate);
    }

    @Override public void setThumb(String url) {
        Glide.with(this)
            .load(url)
            .placeholder(ph_square)
            .error(ph_square)
            .transition(new DrawableTransitionOptions().crossFade())
            .into(binding.imgProductThumb);
    }

    @Override public void setBrandName(String name) {
        binding.txtBrandName.setText(name);
    }

    @Override public void setProductName(String name) {
        binding.txtProductName.setText(name);
    }

    @Override public void setOption(String color, String size) {
        binding.txtOption.setText(String.format(resources.getString(str_format_option), color, size));
    }

    @Override public void selectGoodButton() {
        binding.imgGood.setImageResource(img_good_on);
        binding.txtGood.setTypeface(resources.getFont(font_bold));
        binding.txtGood.setTextColor(resources.getColor(color_FF8140E5));
    }

    @Override public void unselectGoodButton() {
        binding.imgGood.setImageResource(img_good_off);
        binding.txtGood.setTypeface(resources.getFont(font_regular));
        binding.txtGood.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void selectNormalButton() {
        binding.imgNormal.setImageResource(img_normal_on);
        binding.txtNormal.setTypeface(resources.getFont(font_bold));
        binding.txtNormal.setTextColor(resources.getColor(color_FF8140E5));
    }

    @Override public void unselectNormalButton() {
        binding.imgNormal.setImageResource(img_normal_off);
        binding.txtNormal.setTypeface(resources.getFont(font_regular));
        binding.txtNormal.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void selectBadButton() {
        binding.imgBad.setImageResource(img_bad_on);
        binding.txtBad.setTypeface(resources.getFont(font_bold));
        binding.txtBad.setTextColor(resources.getColor(color_FF8140E5));
    }

    @Override public void unselectBadButton() {
        binding.imgBad.setImageResource(img_bad_off);
        binding.txtBad.setTypeface(resources.getFont(font_regular));
        binding.txtBad.setTextColor(resources.getColor(color_FFA9A9A9));
    }

    @Override public void selectVeryBigButton() {
        binding.txtVeryBig.setTypeface(resources.getFont(font_bold));
        binding.txtVeryBig.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtVeryBig.setBackgroundResource(img_button_on);
    }

    @Override public void unselectVeryBigButton() {
        binding.txtVeryBig.setTypeface(resources.getFont(font_regular));
        binding.txtVeryBig.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtVeryBig.setBackgroundResource(img_button_off);
    }

    @Override public void selectLittleBigButton() {
        binding.txtLittleBig.setTypeface(resources.getFont(font_bold));
        binding.txtLittleBig.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtLittleBig.setBackgroundResource(img_button_on);
    }

    @Override public void unselectLittleBigButton() {
        binding.txtLittleBig.setTypeface(resources.getFont(font_regular));
        binding.txtLittleBig.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtLittleBig.setBackgroundResource(img_button_off);
    }

    @Override public void selectPerfectlyButton() {
        binding.txtPerfectly.setTypeface(resources.getFont(font_bold));
        binding.txtPerfectly.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtPerfectly.setBackgroundResource(img_button_on);
    }

    @Override public void unselectPerfectlyButton() {
        binding.txtPerfectly.setTypeface(resources.getFont(font_regular));
        binding.txtPerfectly.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtPerfectly.setBackgroundResource(img_button_off);
    }

    @Override public void selectLittleSmallButton() {
        binding.txtLittleSmall.setTypeface(resources.getFont(font_bold));
        binding.txtLittleSmall.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtLittleSmall.setBackgroundResource(img_button_on);
    }

    @Override public void unselectLittleSmallButton() {
        binding.txtLittleSmall.setTypeface(resources.getFont(font_regular));
        binding.txtLittleSmall.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtLittleSmall.setBackgroundResource(img_button_off);
    }

    @Override public void selectVerySmallButton() {
        binding.txtVerySmall.setTypeface(resources.getFont(font_bold));
        binding.txtVerySmall.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtVerySmall.setBackgroundResource(img_button_on);
    }

    @Override public void unselectVerySmallButton() {
        binding.txtVerySmall.setTypeface(resources.getFont(font_regular));
        binding.txtVerySmall.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtVerySmall.setBackgroundResource(img_button_off);
    }

    @Override public void selectMaleButton() {
        binding.txtGenderMale.setTypeface(resources.getFont(font_bold));
        binding.txtGenderMale.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtGenderMale.setBackgroundResource(img_button_on);
    }

    @Override public void unselectMaleButton() {
        binding.txtGenderMale.setTypeface(resources.getFont(font_regular));
        binding.txtGenderMale.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtGenderMale.setBackgroundResource(img_button_off);
    }

    @Override public void selectFemaleButton() {
        binding.txtGenderFemale.setTypeface(resources.getFont(font_bold));
        binding.txtGenderFemale.setTextColor(resources.getColor(color_FF8140E5));
        binding.txtGenderFemale.setBackgroundResource(img_button_on);
    }

    @Override public void unselectFemaleButton() {
        binding.txtGenderFemale.setTypeface(resources.getFont(font_regular));
        binding.txtGenderFemale.setTextColor(resources.getColor(color_FFA9A9A9));
        binding.txtGenderFemale.setBackgroundResource(img_button_off);
    }

    @Override public void showAgePicker() {
        binding.pickerAge.show();
    }

    @Override public void hideAgePicker() {
        binding.pickerAge.hide();
    }

    @Override public void checkStoragePermission() {
        PermissionUtil.checkStoragePermission(this, new PermissionListener() {
            @Override public void onPermissionGranted() {
                presenter.onStoragePermissionGranted();
            }

            @Override public void onPermissionDenied(List<String> deniedPermissions) {
            }
        });
    }

    @Override public void setupReviewEditText() {
        //reviewEditText.addTextChangedListener(new TextWatcher() {
        //    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //    }
        //
        //    @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
        //        presenter.onTextChanged(s.toString());
        //    }
        //
        //    @Override public void afterTextChanged(Editable s) {
        //    }
        //});
    }

    @Override public void enableApplyButton() {
        //applyTextView.setEnabled(true);
        //applyTextView.setBackgroundColor(color_FF8140E5);
    }

    @Override public void disableApplyButton() {
        //applyTextView.setEnabled(false);
        //applyTextView.setBackgroundColor(color_FFF5F5F5);
    }

    @Override public void showReviewWriteToast() {
        Toast.makeText(this, str_review_write, Toast.LENGTH_SHORT).show();
    }

    @Override public void navigateToAlbum() {
        GalleryActivity.start(this);
    }

    @OnClick(R.id.txt_apply)
    void onApplyClick() {
        presenter.onApplyClick();
    }
}
