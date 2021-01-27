package com.minilook.minilook.ui.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.DimenRes;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.gun0912.tedpermission.PermissionListener;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.databinding.ActivityGalleryBinding;
import com.minilook.minilook.ui.album.adapter.AlbumAdapter;
import com.minilook.minilook.ui.album.adapter.GalleryAdapter;
import com.minilook.minilook.ui.album.di.GalleryArguments;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.cropper.CropperActivity;
import com.minilook.minilook.util.PermissionUtil;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import timber.log.Timber;

public class GalleryActivity extends BaseActivity implements GalleryPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @DimenRes int dp_4 = R.dimen.dp_4;

    private ActivityGalleryBinding binding;
    private GalleryPresenter presenter;

    private final GalleryAdapter galleryAdapter = new GalleryAdapter();
    private final BaseAdapterDataView<String> galleryAdapterView = galleryAdapter;
    private final AlbumAdapter albumAdapter = new AlbumAdapter();
    private final BaseAdapterDataView<AlbumDataModel> albumAdapterView = albumAdapter;

    private String cache_path;

    @Override protected View getBindingView() {
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new GalleryPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private GalleryArguments provideArguments() {
        return GalleryArguments.builder()
            .view(this)
            .contentResolver(getContentResolver())
            .galleryAdapter(galleryAdapter)
            .albumAdapter(albumAdapter)
            .build();
    }

    @Override public void setupClickAction() {
        binding.layoutTitlePanel.setOnClickListener(view -> presenter.onSelectAlbumClick());
        binding.txtCancel.setOnClickListener(view -> presenter.onCancelClick());
    }

    @Override public void setupGalleryRecyclerView() {
        binding.rcvGallery.setHasFixedSize(true);
        binding.rcvGallery.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rcvGallery.setAdapter(galleryAdapter);
        DividerDecoration.builder(this)
            .size(resources.getDimen(dp_4))
            .asSpace()
            .build()
            .addTo(binding.rcvGallery);
    }

    @Override public void galleryRefresh() {
        galleryAdapterView.refresh();
    }

    @Override public void setupAlbumRecyclerView() {
        binding.rcvAlbum.setHasFixedSize(true);
        binding.rcvAlbum.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvAlbum.setAdapter(albumAdapter);
    }

    @Override public void albumRefresh() {
        albumAdapterView.refresh();
    }

    @Override public void setTitle(String name) {
        binding.txtTitle.setText(name);
    }

    @Override public void openAlbumSelectPanel() {
        YoYo.with(Techniques.SlideInDown)
            .duration(150)
            .onStart(animator -> binding.rcvAlbum.setVisibility(View.VISIBLE))
            .playOn(binding.rcvAlbum);

        binding.imgArrow.animate()
            .rotation(180)
            .setDuration(150)
            .withLayer()
            .start();
    }

    @Override public void closeAlbumSelectPanel() {
        YoYo.with(Techniques.SlideOutUp)
            .duration(150)
            .onEnd(animator -> binding.rcvAlbum.setVisibility(View.INVISIBLE))
            .playOn(binding.rcvAlbum);

        binding.imgArrow.animate()
            .rotation(0)
            .setDuration(150)
            .withLayer()
            .start();
    }

    @Override public void checkCameraPermission() {
        PermissionUtil.checkCameraPermission(this, new PermissionListener() {
            @Override public void onPermissionGranted() {
                presenter.onCameraPermissionGranted();
            }

            @Override public void onPermissionDenied(List<String> deniedPermissions) {
            }
        });
    }

    @Override public void navigateToCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = createCacheFile();
            if (file != null) {
                this.cache_path = file.getAbsolutePath();
                Uri uri = FileProvider.getUriForFile(this, resources.getString(R.string.file_provider), file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                launcher.launch(intent);
            }
        }
    }

    private File createCacheFile() {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            return File.createTempFile(timeStamp, ".jpg", getCacheDir());
        } catch (IOException e) {
            Timber.e(e);
        }
        return null;
    }

    @Override public void navigateToCropper(File file) {
        CropperActivity.start(this, file);
    }

    @Override public void clear() {
        launcher.unregister();
    }

    private final ActivityResultLauncher<Intent> launcher =
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    File file = new File(cache_path);
                    presenter.onCameraCallback(file);
                }
            });
}
