package com.minilook.minilook.ui.album;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.album.di.GalleryArguments;
import com.minilook.minilook.ui.album.viewholder.AlbumItemVH;
import com.minilook.minilook.ui.album.viewholder.GalleryHeaderItemVH;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.cropper.CropperPresenterImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import timber.log.Timber;

public class GalleryPresenterImpl extends BasePresenterImpl implements GalleryPresenter {

    private static final Uri URI_EXTERNAL_STORAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private final View view;
    private final ContentResolver contentResolver;
    private final BaseAdapterDataModel<String> galleryAdapter;
    private final BaseAdapterDataModel<AlbumDataModel> albumAdapter;

    private String folder = "";
    private List<AlbumDataModel> albums;
    private List<String> images;

    private boolean isAlbumSelectOpen = false;

    public GalleryPresenterImpl(GalleryArguments args) {
        view = args.getView();
        contentResolver = args.getContentResolver();
        galleryAdapter = args.getGalleryAdapter();
        albumAdapter = args.getAlbumAdapter();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupGalleryRecyclerView();
        view.setupAlbumRecyclerView();

        setInit();
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onSelectAlbumClick() {
        handleAlbumPanel();
    }

    @Override public void onCancelClick() {
        view.finish();
    }

    @Override public void onCameraPermissionGranted() {
        view.navigateToCamera();
    }

    @Override public void onCameraCallback(File file) {
        view.navigateToCropper(file);
    }

    private void setInit() {
        setupAlbums();
        setupGallery();
    }

    private void setupAlbums() {
        albums = getAlbums();
        albumAdapter.set(albums);
        view.albumRefresh();
        view.setTitle(albums.get(0).getName());
    }

    private void setupGallery() {
        images = getGallery();
        galleryAdapter.set(images);
        view.galleryRefresh();
    }

    private void handleAlbumPanel() {
        if (isAlbumSelectOpen) {
            view.closeAlbumSelectPanel();
        } else {
            view.openAlbumSelectPanel();
        }
        isAlbumSelectOpen = !isAlbumSelectOpen;
    }

    private List<String> getGallery() {
        List<String> images = new ArrayList<>();
        images.add("header");

        String[] projection = new String[] {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE
        };
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(folder)) {
            selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";
            selectionArgs = new String[] { folder };
        }
        String order = MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";

        Cursor cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, selection, selectionArgs, order);

        if (cursor == null || !cursor.moveToFirst()) {
            Timber.e("cursor null or cursor is empty");
            return null;
        }

        int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);

        do {
            String id = cursor.getString(idColumn);
            images.add(Uri.withAppendedPath(URI_EXTERNAL_STORAGE, id).toString());
        } while (cursor.moveToNext());

        cursor.close();
        return images;
    }

    private List<AlbumDataModel> getAlbums() {
        Map<String, AlbumDataModel> albumMap = new HashMap<>();
        List<AlbumDataModel> albums = new ArrayList<>();

        String[] projection = new String[] {
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
        };
        String order = MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";

        Cursor cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, null, null, order);

        if (cursor == null || !cursor.moveToFirst()) {
            Timber.e("cursor null or cursor is empty");
            return null;
        }

        int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
        int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);

        do {
            String bucketId = cursor.getString(bucketIdColumn);
            String bucketName = cursor.getString(bucketNameColumn);
            int count = getCount(bucketId);
            String id = cursor.getString(idColumn);

            if (cursor.isFirst()) {
                // add header
                AlbumDataModel model = new AlbumDataModel();
                model.setName("최근 항목");
                model.setFolder("");
                model.setCount(getCount(""));
                model.setRecentImage(Uri.withAppendedPath(URI_EXTERNAL_STORAGE, id).toString());
                albums.add(model);
            }

            if (!albumMap.containsKey(bucketId)) {
                AlbumDataModel model = new AlbumDataModel();
                model.setName(parseToKr(bucketName));
                model.setFolder(bucketName);
                model.setCount(count);
                model.setRecentImage(Uri.withAppendedPath(URI_EXTERNAL_STORAGE, id).toString());
                albumMap.put(bucketId, model);
            }
        } while (cursor.moveToNext());

        cursor.close();

        // add Contents
        for (String key : albumMap.keySet()) {
            albums.add(albumMap.get(key));
        }
        return albums;
    }

    private String parseToKr(String name) {
        switch (name) {
            case "Camera":
                return "카메라";
            case "Screenshots":
                return "스크린샷";
            case "Download":
                return "디운로드";
        }
        return name;
    }

    private int getCount(String bucketId) {
        int count = 0;

        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(bucketId)) {
            selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
            selectionArgs = new String[] { bucketId };
        }

        Cursor cursor = contentResolver.query(URI_EXTERNAL_STORAGE, null, selection, selectionArgs, null);

        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    private void insertImage(String fileName, Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/미니룩");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }

        Uri target = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(target, "w", null);

            if (pdf == null) {
                Timber.e("ParcelFileDescriptor is null");
                return;
            }

            FileOutputStream fos = new FileOutputStream(pdf.getFileDescriptor());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                contentResolver.update(target, values, null, null);
            }
        } catch (IOException e) {
            Timber.e(e);
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof AlbumItemVH.RxBusEventAlbumSelected) {
                AlbumDataModel data = ((AlbumItemVH.RxBusEventAlbumSelected) o).getData();
                folder = data.getFolder();
                setupGallery();
                view.setTitle(parseToKr(data.getName()));
                handleAlbumPanel();
            } else if (o instanceof GalleryHeaderItemVH.RxBusEventNavigateToCamera) {
                view.checkCameraPermission();
            } else if (o instanceof CropperPresenterImpl.RxEventCropCompleted) {
                String fileName = ((CropperPresenterImpl.RxEventCropCompleted) o).getFileName();
                Bitmap cropImage = ((CropperPresenterImpl.RxEventCropCompleted) o).getCropImage();

                insertImage(fileName, cropImage);
                setInit();
            }
        }, Timber::e));
    }
}