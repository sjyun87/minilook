package com.minilook.minilook.ui.album;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import com.minilook.minilook.ui.album.di.AlbumArguments;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import java.io.IOException;
import java.io.InputStream;
import timber.log.Timber;

public class AlbumPresenterImpl extends BasePresenterImpl implements AlbumPresenter {

    private final View view;
    private final ContentResolver contentResolver;

    public AlbumPresenterImpl(AlbumArguments args) {
        view = args.getView();
        contentResolver = args.getContentResolver();
    }

    @Override public void onCreate() {
        readFile();
    }

    private void readFile() {
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[] {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE
        };

        Cursor cursor = contentResolver.query(externalUri, projection, null, null, null);

        if (cursor == null || !cursor.moveToFirst()) {
            Timber.e("cursor null or cursor is empty");
            return;
        }

        do {
            String contentUrl = externalUri.toString() + "/" + cursor.getString(0);
            Timber.e("Image :: " + contentUrl);

            try {
                InputStream is = contentResolver.openInputStream(Uri.parse(contentUrl));

                if (is != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (cursor.moveToNext());
    }
}