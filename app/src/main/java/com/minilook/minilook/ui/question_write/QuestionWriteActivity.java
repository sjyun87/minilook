package com.minilook.minilook.ui.question_write;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.gun0912.tedpermission.PermissionListener;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.databinding.ActivityQuestionWriteBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.gallery.GalleryActivity;
import com.minilook.minilook.ui.question_write.adapter.QuestionTypeAdapter;
import com.minilook.minilook.ui.question_write.di.QuestionWriteArguments;
import com.minilook.minilook.ui.review_write.adapter.PhotoAdapter;
import com.minilook.minilook.util.DimenUtil;
import com.minilook.minilook.util.PermissionUtil;
import com.minilook.minilook.util.SpannableUtil;
import java.util.Arrays;
import java.util.List;

public class QuestionWriteActivity extends BaseActivity implements QuestionWritePresenter.View {

    public static void start(Context context, int productNo) {
        Intent intent = new Intent(context, QuestionWriteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("productNo", productNo);
        context.startActivity(intent);
    }

    @ArrayRes int types = R.array.question_type;

    @StringRes int str_selected_count = R.string.review_write_photo_selected_count;
    @StringRes int str_guide1 = R.string.question_write_guide1;
    @StringRes int str_guide1_bold = R.string.question_write_guide1_b;
    @StringRes int str_guide2 = R.string.question_write_guide2;
    @StringRes int str_guide2_bold = R.string.question_write_guide2_b;
    @StringRes int str_question_write = R.string.toast_question_write;

    @ColorRes int color_FFA9A9A9 = R.color.color_FFA9A9A9;
    @ColorRes int color_FFF5F5F5 = R.color.color_FFF5F5F5;
    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;

    @DrawableRes int img_arrow_down = R.drawable.ic_arrow_down_black;
    @DrawableRes int img_arrow_up = R.drawable.ic_arrow_up_black;
    @DrawableRes int img_checkbox_off = R.drawable.ic_checkbox1_off;
    @DrawableRes int img_checkbox_on = R.drawable.ic_checkbox1_on;

    @FontRes int font_bold = R.font.nanum_square_b;

    private ActivityQuestionWriteBinding binding;
    private QuestionWritePresenter presenter;
    private QuestionTypeAdapter typeAdapter;

    private final PhotoAdapter photoAdapter = new PhotoAdapter();
    private final BaseAdapterDataView<PhotoDataModel> photoAdapterView = photoAdapter;

    @Override protected View getBindingView() {
        binding = ActivityQuestionWriteBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new QuestionWritePresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private QuestionWriteArguments provideArguments() {
        return QuestionWriteArguments.builder()
            .view(this)
            .productNo(getIntent().getIntExtra("productNo", -1))
            .photoAdapter(photoAdapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.layoutTypeBoxPanel.setOnClickListener(view -> presenter.onTypeBoxClick());
        binding.layoutSecretPanel.setOnClickListener(view -> presenter.onSecretClick());
        binding.txtApply.setOnClickListener(view -> presenter.onApplyClick());
    }

    @Override public void setupTypeRecyclerView() {
        binding.rcvType.setLayoutManager(new LinearLayoutManager(this));
        typeAdapter = new QuestionTypeAdapter();
        typeAdapter.setOnTypeSelectedListener(data -> presenter.onTypeSelected(data));
        binding.rcvType.setAdapter(typeAdapter);
        DividerDecoration.builder(this)
            .size(DimenUtil.dpToPx(this, 1))
            .color(resources.getColor(color_FFA9A9A9))
            .showLastDivider()
            .build()
            .addTo(binding.rcvType);

        typeAdapter.set(Arrays.asList(resources.getStringArray(types)));
        typeAdapter.refresh();
        presenter.onTypeSelected(typeAdapter.get(0));
    }

    @Override public void setupPhotoRecyclerView() {
        binding.rcvPhoto.setHasFixedSize(true);
        binding.rcvPhoto.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rcvPhoto.setAdapter(photoAdapter);
    }

    @Override public void photoRefresh() {
        photoAdapterView.refresh();
    }

    @Override public void showTypeBox() {
        binding.rcvType.setVisibility(View.VISIBLE);
        binding.imgTypeArrow.setImageDrawable(resources.getDrawable(img_arrow_up));
    }

    @Override public void hideTypeBox() {
        binding.rcvType.setVisibility(View.GONE);
        binding.imgTypeArrow.setImageDrawable(resources.getDrawable(img_arrow_down));
    }

    @Override public void setSelectedType(String type) {
        binding.txtSelectedType.setText(type);
    }

    @Override public void setupQuestionEditText() {
        binding.editQuestion.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.onTextChanged(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override public void setSelectedPhotoCount(int size) {
        String count = String.valueOf(size);
        String total = String.format(resources.getString(str_selected_count), count);
        if (size < 4) {
            binding.txtSelectedCount.setTextColor(resources.getColor(color_FFA9A9A9));
            binding.txtSelectedCount.setText(SpannableUtil.foregroundColorSpan(total, count, color_FF8140E5));
        } else {
            binding.txtSelectedCount.setTextColor(resources.getColor(color_FF8140E5));
            binding.txtSelectedCount.setText(total);
        }
    }

    @Override public void checkSecretCheckBox() {
        binding.imgSecretCheck.setImageDrawable(resources.getDrawable(img_checkbox_on));
    }

    @Override public void uncheckSecretCheckBox() {
        binding.imgSecretCheck.setImageDrawable(resources.getDrawable(img_checkbox_off));
    }

    @Override public void enableApplyButton() {
        binding.txtApply.setEnabled(true);
        binding.txtApply.setBackgroundColor(resources.getColor(color_FF8140E5));
    }

    @Override public void disableApplyButton() {
        binding.txtApply.setEnabled(false);
        binding.txtApply.setBackgroundColor(resources.getColor(color_FFF5F5F5));
    }

    @Override public void setupGuideText() {
        binding.txtGuide1.setText(
            SpannableUtil.fontSpan(resources.getString(str_guide1), resources.getString(str_guide1_bold),
                resources.getFont(font_bold)));

        binding.txtGuide2.setText(
            SpannableUtil.fontSpan(resources.getString(str_guide2), resources.getString(str_guide2_bold),
                resources.getFont(font_bold)));
    }

    @Override public void showQuestionWriteToast() {
        Toast.makeText(this, str_question_write, Toast.LENGTH_SHORT).show();
    }

    @Override public void showLoadingView() {
        binding.loadingView.show();
    }

    @Override public void hideLoadingView() {
        binding.loadingView.hide();
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void navigateToGallery(List<PhotoDataModel> photos) {
        GalleryActivity.start(this, photos);
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
}
