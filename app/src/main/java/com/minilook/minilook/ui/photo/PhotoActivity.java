package com.minilook.minilook.ui.photo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.StringRes;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.image.ImageDataModel;
import com.minilook.minilook.databinding.ActivityPhotoBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.photo.adapter.PhotoAdapter;
import com.minilook.minilook.ui.photo.di.PhotoArguments;
import java.util.ArrayList;
import java.util.List;

public class PhotoActivity extends BaseActivity implements PhotoPresenter.View {

    public static void start(Context context, List<ImageDataModel> photos, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putParcelableArrayListExtra("photos", new ArrayList<>(photos));
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @StringRes int format_page = R.string.photo_page;

    private ActivityPhotoBinding binding;
    private PhotoPresenter presenter;

    private final PhotoAdapter adapter = new PhotoAdapter();
    private final BaseAdapterDataView<ImageDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new PhotoPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PhotoArguments provideArguments() {
        return PhotoArguments.builder()
            .view(this)
            .photos(getIntent().getParcelableArrayListExtra("photos"))
            .position(getIntent().getIntExtra("position", 0))
            .adapter(adapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.imgBack.setOnClickListener(this::onBackClick);
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
        binding.txtPage.setText(String.format(resources.getString(format_page), index, totalPage));
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
