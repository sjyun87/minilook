package com.minilook.minilook.ui.review;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.review.RatingDataModel;
import java.util.List;

public interface ReviewPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onLoadMore();

    interface View {

        void setupRecyclerView();

        void refresh();

        void refresh(int start, int rows);

        void showReviewRatingPanel();

        void setSatisfaction(String satisfaction);

        void setSizeRating(RatingDataModel sizeRating);

        void setSizeRatingDetail(List<RatingDataModel> sizeRatingDetail);

        void showEmptyPanel();
    }
}
