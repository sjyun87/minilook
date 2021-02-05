package com.minilook.minilook.ui.review_write;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.GenderCode;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizes;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.KeyDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.network.naver.NCloudRequest;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.album.GalleryPresenterImpl;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;
import com.minilook.minilook.ui.review_write.viewholder.PhotoContentItemVH;
import com.minilook.minilook.ui.review_write.viewholder.PhotoFooterItemVH;
import java.util.List;
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

    private int satisfactionCode = -1;
    private int sizeRatingCode = -1;
    private int genderCode = -1;

    private String review;
    private List<PhotoDataModel> photos;

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

        view.setOrderNo(orderNo);
        view.setOrderDate(orderDate);
        view.setThumb(data.getThumbUrl());
        view.setBrandName(data.getBrandName());
        view.setProductName(data.getName());
        view.setOption(data.getColorName(), data.getSizeName());

        view.setupReviewEditText();
        view.setupRecyclerView();
    }

    @Override public void onSatisfactionClick(int code) {
        if (satisfactionCode != code) {
            if (satisfactionCode != -1) unselectSatisfactionButton(satisfactionCode);
            satisfactionCode = code;
            selectSatisfactionButton(satisfactionCode);
        }
    }

    @Override public void onSizeClick(int code) {
        if (sizeRatingCode != code) {
            if (sizeRatingCode != -1) unselectSizeRatingButton(sizeRatingCode);
            sizeRatingCode = code;
            selectSizeRatingButton(sizeRatingCode);
        }
    }

    @Override public void onGenderClick(int code) {
        if (genderCode != code) {
            if (genderCode != -1) unselectGenderButton(genderCode);
            genderCode = code;
            selectGenderButton(genderCode);
        }
    }

    @Override public void onAgeInputClick() {
        view.showAgePicker();
    }

    @Override public void onPhotoAddClick() {
        view.checkStoragePermission();
    }

    @Override public void onStoragePermissionGranted() {
        view.navigateToGallery(photos);
    }

    @Override public void onTextChanged(String text) {
        review = text;
        if (review.length() >= 5) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    @Override public void onApplyClick() {
        //reqWriteReview();
    }

    private void selectSatisfactionButton(int code) {
        if (code == ReviewSatisfactions.GOOD.getCode()) {
            view.selectGoodButton();
        } else if (code == ReviewSatisfactions.NORMAL.getCode()) {
            view.selectNormalButton();
        } else if (code == ReviewSatisfactions.BAD.getCode()) {
            view.selectBadButton();
        }
    }

    private void unselectSatisfactionButton(int code) {
        if (code == ReviewSatisfactions.GOOD.getCode()) {
            view.unselectGoodButton();
        } else if (code == ReviewSatisfactions.NORMAL.getCode()) {
            view.unselectNormalButton();
        } else if (code == ReviewSatisfactions.BAD.getCode()) {
            view.unselectBadButton();
        }
    }

    private void selectSizeRatingButton(int code) {
        if (code == ReviewSizes.VERY_BIG.getCode()) {
            view.selectVeryBigButton();
        } else if (code == ReviewSizes.LITTLE_BIG.getCode()) {
            view.selectLittleBigButton();
        } else if (code == ReviewSizes.PERFECTLY.getCode()) {
            view.selectPerfectlyButton();
        } else if (code == ReviewSizes.LITTLE_SMALL.getCode()) {
            view.selectLittleSmallButton();
        } else if (code == ReviewSizes.VERY_SMALL.getCode()) {
            view.selectVerySmallButton();
        }
    }

    private void unselectSizeRatingButton(int code) {
        if (code == ReviewSizes.VERY_BIG.getCode()) {
            view.unselectVeryBigButton();
        } else if (code == ReviewSizes.LITTLE_BIG.getCode()) {
            view.unselectLittleBigButton();
        } else if (code == ReviewSizes.PERFECTLY.getCode()) {
            view.unselectPerfectlyButton();
        } else if (code == ReviewSizes.LITTLE_SMALL.getCode()) {
            view.unselectLittleSmallButton();
        } else if (code == ReviewSizes.VERY_SMALL.getCode()) {
            view.unselectVerySmallButton();
        }
    }

    private void selectGenderButton(int code) {
        if (code == GenderCode.MALE.getCode()) {
            view.selectMaleButton();
        } else if (code == GenderCode.FEMALE.getCode()) {
            view.selectFemaleButton();
        }
    }

    private void unselectGenderButton(int code) {
        if (code == GenderCode.MALE.getCode()) {
            view.unselectMaleButton();
        } else if (code == GenderCode.FEMALE.getCode()) {
            view.unselectFemaleButton();
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

    private void reqWriteReview() {
        addDisposable(reviewRequest.writeReview(orderNo, data.getProductNo(), data.getOptionNo(), review)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resWriteReview, Timber::e));
    }

    private void resWriteReview(BaseDataModel data) {
        view.showReviewWriteToast();
        RxBus.send(new RxEventReviewWrite());
        view.finish();
    }

    private void reqGetKeys() {
        addDisposable(commonRequest.getKeys()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), KeyDataModel.class))
            .subscribe(this::resKeys, Timber::e)
        );
    }

    private void resKeys(KeyDataModel model) {
        reqPutImage(model, photos);
    }

    private void reqPutImage(KeyDataModel keys, List<PhotoDataModel> images) {
        addDisposable(nCloudRequest.putImage(NCloudRequest.TYPE_REVIEW, keys, data.getProductNo(), images.get(0))
            .subscribe(this::resPutImage, Timber::e));
    }

    private void resPutImage(ResponseBody body) {
        Timber.e(body.toString());
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