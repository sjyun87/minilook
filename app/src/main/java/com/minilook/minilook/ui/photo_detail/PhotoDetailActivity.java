package com.minilook.minilook.ui.photo_detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.StringRes;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.databinding.ActivityPhotoDetailBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.photo_detail.adapter.PhotoDetailAdapter;
import com.minilook.minilook.ui.photo_detail.di.PhotoDetailArguments;

public class PhotoDetailActivity extends BaseActivity implements PhotoDetailPresenter.View {

    public static void start(Context context, PhotoDetailDataModel data) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @StringRes int format_page = R.string.photo_page;

    @StringRes int str_expand = R.string.photo_expand;
    @StringRes int str_collapse = R.string.photo_collapse;

    private ActivityPhotoDetailBinding binding;
    private PhotoDetailPresenter presenter;

    private final PhotoDetailAdapter adapter = new PhotoDetailAdapter();
    private final BaseAdapterDataView<ImageDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityPhotoDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new PhotoDetailPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PhotoDetailArguments provideArguments() {
        return PhotoDetailArguments.builder()
            .view(this)
            .data((PhotoDetailDataModel) getIntent().getSerializableExtra("data"))
            .adapter(adapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.imgBack.setOnClickListener(this::onBackClick);
        binding.layoutMorePanel.setOnClickListener(view -> presenter.onMoreClick());
    }

    @Override public void setupViewPager() {
        binding.vpPhoto.setAdapter(adapter);
        binding.vpPhoto.setOffscreenPageLimit(2);
        binding.vpPhoto.registerOnPageChangeCallback(callback);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void setCurrentItem(int position) {
        binding.vpPhoto.setCurrentItem(position, false);
    }

    @Override public void setSelectedPage(int index, int totalPage) {
        binding.txtPageCount.setText(String.format(resources.getString(format_page), index, totalPage));
    }

    @Override public void setContents(String contents) {
        binding.txtContents.setText(contents);
        binding.txtContents.post(() -> {
            int lineCount = binding.txtContents.getLineCount();
            if (lineCount > binding.txtContents.getMaxLines()) {
                binding.layoutMorePanel.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override public void setExpandButton() {
        binding.txtMore.setText(resources.getString(str_collapse));
        binding.txtContents.setMaxLines(Integer.MAX_VALUE);

        binding.imgArrow.animate()
            .rotation(180)
            .setDuration(150)
            .withLayer()
            .start();
    }

    @Override public void collapseButton() {
        binding.txtMore.setText(resources.getString(str_expand));
        binding.txtContents.setMaxLines(4);

        binding.imgArrow.animate()
            .rotation(0)
            .setDuration(150)
            .withLayer()
            .start();
    }

    @Override public void showContentsPanel() {
        binding.layoutContentsPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideContentsPanel() {
        binding.layoutContentsPanel.setVisibility(View.GONE);
    }

    @Override public void clear() {
        binding.vpPhoto.unregisterOnPageChangeCallback(callback);
    }

    private void onBackClick(View view) {
        finish();
    }

    private final ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageSelected(int position) {
            presenter.onPageSelected(position);
        }
    };
}
