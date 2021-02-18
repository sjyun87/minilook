package com.minilook.minilook.ui.photo_review_detail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.ImageDataModel;
import com.minilook.minilook.data.model.common.PhotoDetailDataModel;
import com.minilook.minilook.data.network.review.ReviewRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.photo_review_detail.di.PhotoReviewDetailArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class PhotoReviewDetailPresenterImpl extends BasePresenterImpl implements PhotoReviewDetailPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final int productNo;
    private final PhotoDetailDataModel data;
    private final BaseAdapterDataModel<ImageDataModel> adapter;
    private final ReviewRequest reviewRequest;
    private final Gson gson;

    private boolean isExpand = false;
    private int lastPhotoReviewNo;

    public PhotoReviewDetailPresenterImpl(PhotoReviewDetailArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        data = args.getData();
        adapter = args.getAdapter();
        reviewRequest = new ReviewRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupViewPager();

        lastPhotoReviewNo = data.getPhotos().get(data.getPhotos().size() - 1).getItemNo();
        adapter.set(data.getPhotos());
        view.refresh();

        view.setCurrentItem(data.getPosition());
        setSelectedPage(data.getPosition());

        //view.setContents(data.getContents());
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onPageSelected(int position) {
        setSelectedPage(position);
    }

    @Override public void onMoreClick() {
        isExpand = !isExpand;
        if (isExpand) {
            view.setExpandButton();
        } else {
            view.collapseButton();
        }
    }

    @Override public void onLoadMore() {
        addDisposable(reviewRequest.getPhotoReviews(ROWS, productNo, lastPhotoReviewNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<ImageDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ImageDataModel>>() {
                }.getType()))
            .subscribe(this::onResMorePhotoReviews, Timber::e)
        );
    }

    private void onResMorePhotoReviews(List<ImageDataModel> data) {
        lastPhotoReviewNo = data.get(data.size() - 1).getItemNo();
        int start = adapter.getSize();
        int row = data.size();
        adapter.addAll(data);
        view.refresh(start, row);
    }

    private void setSelectedPage(int index) {
        //view.setSelectedPage(index + 1, data.getPhotos().size());
    }
}