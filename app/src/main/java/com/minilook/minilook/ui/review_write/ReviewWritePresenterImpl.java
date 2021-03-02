package com.minilook.minilook.ui.review_write;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.GenderCode;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizeRatings;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.common.KeyDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.model.review.ReviewWriteCompletedDataModel;
import com.minilook.minilook.data.model.review.ReviewWriteDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.network.naver.NCloudRequest;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.gallery.GalleryPresenterImpl;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;
import com.minilook.minilook.ui.review_write.viewholder.PhotoContentItemVH;
import com.minilook.minilook.ui.review_write.viewholder.PhotoFooterItemVH;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class ReviewWritePresenterImpl extends BasePresenterImpl implements ReviewWritePresenter {

    private final View view;
    private final String orderNo;
    private final String orderDate;
    private final OrderProductDataModel data;
    private final BaseAdapterDataModel<PhotoDataModel> adapter;
    private final CommonRequest commonRequest;
    private final ReviewRequest reviewRequest;
    private final NCloudRequest nCloudRequest;
    private final Gson gson;

    private String satisfactionCode = "";
    private String sizeRatingCode = "";
    private String genderCode = "";
    private int age = -1;
    private int height = -1;
    private int weight = -1;

    private String review;
    private List<PhotoDataModel> photos;
    private int uploadCount = 0;

    public ReviewWritePresenterImpl(ReviewWriteArguments args) {
        view = args.getView();
        orderNo = args.getOrderNo();
        orderDate = args.getOrderDate();
        data = args.getData();
        adapter = args.getAdapter();
        commonRequest = new CommonRequest();
        reviewRequest = new ReviewRequest();
        nCloudRequest = new NCloudRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupAgePicker(getData(1, 16, 1));
        view.setupHeightPicker(getData(50, 180, 1));
        view.setupWeightPicker(getData(3, 70, 1));

        view.setOrderNo(orderNo);
        view.setOrderDate(orderDate);
        view.setThumb(data.getThumbUrl());
        view.setBrandName(data.getBrandName());
        view.setProductName(data.getProductName());
        view.setOption(data.getColorName(), data.getSizeName());

        view.setupReviewEditText();
        view.setupRecyclerView();
    }

    @Override public void onSatisfactionClick(String code) {
        if (!satisfactionCode.equals(code)) {
            if (!TextUtils.isEmpty(satisfactionCode)) unselectSatisfactionButton(satisfactionCode);
            satisfactionCode = code;
            selectSatisfactionButton(satisfactionCode);
            handleApplyButton();
        }
    }

    @Override public void onSizeClick(String code) {
        if (!sizeRatingCode.equals(code)) {
            if (!TextUtils.isEmpty(sizeRatingCode)) unselectSizeRatingButton(sizeRatingCode);
            sizeRatingCode = code;
            selectSizeRatingButton(sizeRatingCode);
            handleApplyButton();
        }
    }

    @Override public void onGenderClick(String code) {
        if (!genderCode.equals(code)) {
            if (!TextUtils.isEmpty(genderCode)) unselectGenderButton(genderCode);
            genderCode = code;
            selectGenderButton(genderCode);
        }
    }

    @Override public void onAgeInputClick() {
        view.setAgePicker(age != -1 ? age : 6);
        view.showAgePicker();
    }

    @Override public void onHeightInputClick() {
        view.setHeightPicker(height != -1 ? height : 110);
        view.showHeightPicker();
    }

    @Override public void onWeightInputClick() {
        view.setWeightPicker(weight != -1 ? weight : 20);
        view.showWeightPicker();
    }

    @Override public void onPhotoAddClick() {
        view.checkStoragePermission();
    }

    @Override public void onStoragePermissionGranted() {
        view.navigateToGallery(photos);
    }

    @Override public void onAgePick(int age) {
        this.age = age;
        view.setAge(age);
    }

    @Override public void onHeightPick(int height) {
        this.height = height;
        view.setHeight(height);
    }

    @Override public void onWeightPick(int weight) {
        this.weight = weight;
        view.setWeight(weight);
    }

    @Override public void onTextChanged(String text) {
        review = text;
        handleApplyButton();
    }

    @Override public void onApplyClick() {
        view.showLoadingView();
        if (photos != null && photos.size() > 0) {
            getUploadKeys();
        } else {
            writeReview();
        }
    }

    @Override public void onReviewCompletedDialogOk() {
        view.finish();
    }

    private List<Integer> getData(int minValue, int maxValue, int step) {
        List<Integer> items = new ArrayList<>();
        for (int i = minValue; i <= maxValue; i += step) {
            items.add(i);
        }
        return items;
    }

    private void selectSatisfactionButton(String code) {
        switch (ReviewSatisfactions.toType(code)) {
            case GOOD:
                view.selectGoodButton();
                break;
            case NORMAL:
                view.selectNormalButton();
                break;
            case BAD:
                view.selectBadButton();
                break;
        }
    }

    private void unselectSatisfactionButton(String code) {
        switch (ReviewSatisfactions.toType(code)) {
            case GOOD:
                view.unselectGoodButton();
                break;
            case NORMAL:
                view.unselectNormalButton();
                break;
            case BAD:
                view.unselectBadButton();
                break;
        }
    }

    private void selectSizeRatingButton(String code) {
        switch (ReviewSizeRatings.toType(code)) {
            case VERY_BIG:
                view.selectVeryBigButton();
                break;
            case LITTLE_BIG:
                view.selectLittleBigButton();
                break;
            case PERFECTLY:
                view.selectPerfectlyButton();
                break;
            case LITTLE_SMALL:
                view.selectLittleSmallButton();
                break;
            case VERY_SMALL:
                view.selectVerySmallButton();
                break;
        }
    }

    private void unselectSizeRatingButton(String code) {
        switch (ReviewSizeRatings.toType(code)) {
            case VERY_BIG:
                view.unselectVeryBigButton();
                break;
            case LITTLE_BIG:
                view.unselectLittleBigButton();
                break;
            case PERFECTLY:
                view.unselectPerfectlyButton();
                break;
            case LITTLE_SMALL:
                view.unselectLittleSmallButton();
                break;
            case VERY_SMALL:
                view.unselectVerySmallButton();
                break;
        }
    }

    private void selectGenderButton(String code) {
        switch (GenderCode.toType(code)) {
            case MALE:
                view.selectMaleButton();
                break;
            case FEMALE:
                view.selectFemaleButton();
                break;
        }
    }

    private void unselectGenderButton(String code) {
        switch (GenderCode.toType(code)) {
            case MALE:
                view.unselectMaleButton();
                break;
            case FEMALE:
                view.unselectFemaleButton();
                break;
        }
    }

    private void setPhotos(List<PhotoDataModel> images) {
        photos = images;
        adapter.set(photos);
        if (photos.size() < 4) adapter.add(new PhotoDataModel());
        view.refresh();

        view.setSelectedPhotoCount(photos.size());
        view.showPhotoPanel();
    }

    private void removePhoto(PhotoDataModel data) {
        photos.remove(data);
        adapter.remove(data);
        if (photos.size() == 3) adapter.add(new PhotoDataModel());
        view.refresh();

        if (photos.size() == 0) view.hidePhotoPanel();
        view.setSelectedPhotoCount(photos.size());
    }

    private void handleApplyButton() {
        if (!TextUtils.isEmpty(satisfactionCode)
            && !TextUtils.isEmpty(sizeRatingCode)
            && !TextUtils.isEmpty(review)
            && review.length() >= 10) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    private void writeReview() {
        addDisposable(reviewRequest.writeReview(getWriteData())
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.hideLoadingView();
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), ReviewWriteCompletedDataModel.class))
            .subscribe(this::onResWriteReview, Timber::e));
    }

    private ReviewWriteDataModel getWriteData() {
        ReviewWriteDataModel model = new ReviewWriteDataModel();
        model.setMid(orderNo);
        model.setProductNo(data.getProductNo());
        model.setOptionNo(data.getOptionNo());
        model.setSatisfactionCode(satisfactionCode);
        model.setSizeRatingCode(sizeRatingCode);
        model.setGenderCode(genderCode);
        model.setAge(age);
        model.setHeight(height);
        model.setWeight(weight);
        model.setPhotos(parseToPhotoData());
        model.setReview(review);
        return model;
    }

    private List<String> parseToPhotoData() {
        List<String> items = new ArrayList<>();
        if (photos != null && photos.size() > 0) {
            for (PhotoDataModel photo : photos) {
                items.add(photo.getName());
            }
        }
        return items;
    }

    private void onResWriteReview(ReviewWriteCompletedDataModel data) {
        view.hideLoadingView();
        view.showReviewCompletedDialog(data.isPhotoReview(), data.getPoint());
        RxBus.send(new RxEventReviewWrite());
    }

    private void getUploadKeys() {
        addDisposable(commonRequest.getKeys()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), KeyDataModel.class))
            .subscribe(this::onResUploadKeys, Timber::e)
        );
    }

    private void onResUploadKeys(KeyDataModel model) {
        for (PhotoDataModel photo : photos) {
            photo.setName(createObjectName());
            uploadImage(model, photo);
        }
    }

    private String createObjectName() {
        StringBuilder sb = new StringBuilder();
        sb.append("P");
        sb.append(data.getProductNo());
        sb.append("_");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault()).format(new Date()));
        sb.append(".png");
        return sb.toString();
    }

    private void uploadImage(KeyDataModel keys, PhotoDataModel photo) {
        addDisposable(nCloudRequest.uploadImage(NCloudRequest.TYPE_REVIEW, keys, photo)
            .compose(Transformer.applySchedulers())
            .subscribe(this::onResUploadImage, this::onUploadError));
    }

    private void onResUploadImage(ResponseBody body) {
        uploadCount++;
        checkUploadCompleted();
    }

    private void onUploadError(Throwable e) {
        Timber.e(e);
        uploadCount = 0;
        view.hideLoadingView();
        view.showErrorDialog();
    }

    private void checkUploadCompleted() {
        if (photos.size() == uploadCount) {
            writeReview();
        }
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof GalleryPresenterImpl.RxEventGallerySelectedCompleted) {
                List<PhotoDataModel> images = ((GalleryPresenterImpl.RxEventGallerySelectedCompleted) o).getItems();
                setPhotos(images);
            } else if (o instanceof PhotoContentItemVH.RxEventReviewPhotoClick) {
                PhotoDataModel data = ((PhotoContentItemVH.RxEventReviewPhotoClick) o).getModel();
                removePhoto(data);
            } else if (o instanceof PhotoFooterItemVH.RxEventReviewPhotoFooterClick) {
                view.navigateToGallery(photos);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventReviewWrite {
    }
}