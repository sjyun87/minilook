package com.minilook.minilook.ui.gallery;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.minilook.minilook.data.model.gallery.AlbumDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.cropper.CropperPresenterImpl;
import com.minilook.minilook.ui.gallery.di.GalleryArguments;
import com.minilook.minilook.ui.gallery.viewholder.AlbumItemVH;
import com.minilook.minilook.ui.gallery.viewholder.GalleryContentsItemVH;
import com.minilook.minilook.ui.gallery.viewholder.GalleryHeaderItemVH;
import com.minilook.minilook.ui.gallery.viewholder.SelectedItemVH;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class GalleryPresenterImpl extends BasePresenterImpl implements GalleryPresenter {

    private static final Uri URI_EXTERNAL_STORAGE = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    private final View view;
    private final List<PhotoDataModel> selectedPhotos;
    private final ContentResolver contentResolver;
    private final BaseAdapterDataModel<AlbumDataModel> albumAdapter;
    private final BaseAdapterDataModel<PhotoDataModel> galleryAdapter;
    private final BaseAdapterDataModel<PhotoDataModel> selectedAdapter;

    private String folder = "";
    private List<AlbumDataModel> albums;
    private List<PhotoDataModel> photos;

    private boolean isAlbumSelectOpen = false;

    public GalleryPresenterImpl(GalleryArguments args) {
        view = args.getView();
        contentResolver = args.getContentResolver();
        albumAdapter = args.getAlbumAdapter();
        galleryAdapter = args.getGalleryAdapter();
        selectedAdapter = args.getSelectedAdapter();
        selectedPhotos = args.getPhotos();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupGalleryRecyclerView();
        view.setupAlbumRecyclerView();
        view.setupSelectPhotoRecyclerView();

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

    @Override public void onOkClick() {
        RxBus.send(new RxEventGallerySelectedCompleted(selectedAdapter.get()));
        view.finish();
    }

    private void setInit() {
        setupSelectedPhotos();
        setupAlbums();
        setupPhotos();
    }

    private void setupSelectedPhotos() {
        if (selectedPhotos != null && selectedPhotos.size() > 0) {
            for (int i = 0; i < selectedPhotos.size(); i++) {
                selectedPhotos.get(i).setSelectPosition(i + 1);
            }
            selectedAdapter.set(selectedPhotos);
            view.selectedPhotoRefresh();
            if (selectedAdapter.getSize() > 0) {
                view.showSelectedPanel();
            } else {
                view.hideSelectedPanel();
            }
            handleApplyButton();
        }
    }

    private void setupAlbums() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            albums = getAlbums();
            if (albums != null && !albums.isEmpty()) {
                albumAdapter.set(albums);
                view.albumRefresh();
                view.setTitle(albums.get(0).getName());
            }
        }, 30);
    }

    private void setupPhotos() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            photos = getPhotos();
            if (photos != null && !photos.isEmpty()) {
                galleryAdapter.set(photos);
                view.galleryRefresh();
            }
        }, 30);
    }

    private void handleAlbumPanel() {
        if (isAlbumSelectOpen) {
            view.closeAlbumSelectPanel();
        } else {
            view.openAlbumSelectPanel();
        }
        isAlbumSelectOpen = !isAlbumSelectOpen;
    }

    private List<PhotoDataModel> getPhotos() {
        List<PhotoDataModel> gallery = new ArrayList<>();
        gallery.add(new PhotoDataModel());

        String[] projection = new String[] {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.MIME_TYPE
        };
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(folder)) {
            selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";
            selectionArgs = new String[] { folder };
        }
        String order = MediaStore.Images.ImageColumns.DATE_MODIFIED;

        Cursor cursor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, new String[] { order });
            bundle.putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING);
            bundle.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection);
            bundle.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs);
            cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, bundle, null);
        } else {
            cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, selection, selectionArgs, order + " DESC");
        }

        if (cursor == null || !cursor.moveToFirst()) {
            Timber.e("cursor null or cursor is empty");
            return null;
        }

        int idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        int nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
        int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

        do {
            String id = cursor.getString(idColumn);
            String name = cursor.getString(nameColumn);
            String path = cursor.getString(dataColumn);

            PhotoDataModel model = new PhotoDataModel();
            model.setName(name);
            model.setUriPath(Uri.withAppendedPath(URI_EXTERNAL_STORAGE, id).toString());
            model.setFilePath(path);
            model.setSelect(false);

            for (int i = 0; i < selectedAdapter.getSize(); i++) {
                PhotoDataModel selectItem = selectedAdapter.get(i);
                if (selectItem.getName().equals(name)) {
                    model.setSelect(true);
                    model.setSelectPosition(i + 1);
                    break;
                }
            }
            gallery.add(model);
        } while (cursor.moveToNext());

        cursor.close();
        return gallery;
    }

    private List<AlbumDataModel> getAlbums() {
        Map<String, AlbumDataModel> albumMap = new HashMap<>();
        List<AlbumDataModel> albums = new ArrayList<>();

        String[] projection = new String[] {
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID
        };
        String order = MediaStore.Images.ImageColumns.DATE_MODIFIED;

        Cursor cursor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Bundle bundle = new Bundle();
            bundle.putString(ContentResolver.QUERY_ARG_SQL_GROUP_BY, MediaStore.Images.Media.BUCKET_ID);
            bundle.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, new String[] { order });
            bundle.putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING);
            cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, bundle, null);
        } else {
            cursor = contentResolver.query(URI_EXTERNAL_STORAGE, projection, null, null,
                order + " DESC");
        }

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
                model.setCount(getCount(bucketId));
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

    private void handleSelectedPhoto(PhotoDataModel data) {
        if (data.isSelect()) {
            selectedAdapter.remove(data);

            data.setSelect(false);
            for (int i = 0; i < selectedAdapter.getSize(); i++) {
                PhotoDataModel model = selectedAdapter.get(i);
                model.setSelectPosition(i + 1);
            }
        } else {
            if (selectedAdapter.getSize() >= 4) {
                view.showLimitToast();
                return;
            }

            selectedAdapter.add(data);
            data.setSelect(true);
            data.setSelectPosition(selectedAdapter.getSize());
        }

        if (selectedAdapter.getSize() > 0) {
            view.showSelectedPanel();
        } else {
            view.hideSelectedPanel();
        }
        view.selectedPhotoRefresh();
        view.galleryRefresh();

        handleApplyButton();
    }

    private void handleApplyButton() {
        if (selectedAdapter.getSize() > 0) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof AlbumItemVH.RxBusEventAlbumSelected) {
                AlbumDataModel data = ((AlbumItemVH.RxBusEventAlbumSelected) o).getData();
                folder = data.getFolder();
                setupPhotos();
                view.setTitle(parseToKr(data.getName()));
                handleAlbumPanel();
            } else if (o instanceof GalleryHeaderItemVH.RxBusEventNavigateToCamera) {
                view.checkCameraPermission();
            } else if (o instanceof CropperPresenterImpl.RxEventCropCompleted) {
                String fileName = ((CropperPresenterImpl.RxEventCropCompleted) o).getFileName();
                Bitmap cropImage = ((CropperPresenterImpl.RxEventCropCompleted) o).getCropImage();

                insertImage(fileName, cropImage);
                setInit();
            } else if (o instanceof GalleryContentsItemVH.RxEventGalleryImageClick) {
                PhotoDataModel data = ((GalleryContentsItemVH.RxEventGalleryImageClick) o).getModel();
                handleSelectedPhoto(data);
            } else if (o instanceof SelectedItemVH.RxEventGallerySelectedImageClick) {
                PhotoDataModel data = ((SelectedItemVH.RxEventGallerySelectedImageClick) o).getModel();
                handleSelectedPhoto(data);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventGallerySelectedCompleted {
        private final List<PhotoDataModel> items;
    }
}