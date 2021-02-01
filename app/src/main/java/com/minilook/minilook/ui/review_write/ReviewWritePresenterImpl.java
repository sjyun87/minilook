package com.minilook.minilook.ui.review_write;

import com.minilook.minilook.data.code.GenderCode;
import com.minilook.minilook.data.code.ReviewSatisfactions;
import com.minilook.minilook.data.code.ReviewSizes;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.gallery.GalleryDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.album.GalleryPresenterImpl;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.review_write.di.ReviewWriteArguments;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ReviewWritePresenterImpl extends BasePresenterImpl implements ReviewWritePresenter {

    private final View view;
    private final String orderNo;
    private final String orderDate;
    private final OrderProductDataModel data;
    private final ReviewRequest reviewRequest;

    private int satisfactionCode = -1;
    private int sizeRatingCode = -1;
    private int genderCode = -1;

    private String review;

    public ReviewWritePresenterImpl(ReviewWriteArguments args) {
        view = args.getView();
        orderNo = args.getOrderNo();
        orderDate = args.getOrderDate();
        data = args.getData();
        reviewRequest = new ReviewRequest();
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
        view.navigateToAlbum();
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
        reqWriteReview();
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

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof GalleryPresenterImpl.RxEventGallerySelectedCompleted) {
                List<GalleryDataModel> images = ((GalleryPresenterImpl.RxEventGallerySelectedCompleted) o).getItems();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventReviewWrite {
    }
}