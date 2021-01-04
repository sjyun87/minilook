package com.minilook.minilook.ui.album;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.minilook.minilook.databinding.ActivityAlbumBinding;
import com.minilook.minilook.ui.album.di.AlbumArguments;
import com.minilook.minilook.ui.base.BaseActivity;
import java.io.IOException;
import java.io.InputStream;
import timber.log.Timber;

public class AlbumActivity extends BaseActivity implements AlbumPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, AlbumActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    private ActivityAlbumBinding binding;
    private AlbumPresenter presenter;

    @Override protected View getBindingView() {
        binding = ActivityAlbumBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new AlbumPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private AlbumArguments provideArguments() {
        return AlbumArguments.builder()
            .view(this)
            .contentResolver(getContentResolver())
            .build();
    }

    @Override public void setupAlbum() {

    }
}
