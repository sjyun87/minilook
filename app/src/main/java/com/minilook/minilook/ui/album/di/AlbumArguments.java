package com.minilook.minilook.ui.album.di;

import android.content.ContentResolver;
import com.minilook.minilook.ui.album.AlbumPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class AlbumArguments {
    private final AlbumPresenter.View view;
    private final ContentResolver contentResolver;
}