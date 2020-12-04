package com.minilook.minilook.ui.brand;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;

public interface BrandPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onBrandScrap(BrandDataModel data);

    void onResetClick();

    void onStyleClick(CodeDataModel data);

    interface View {

        void setupClickAction();

        void setupStyleRecyclerView();

        void styleRefresh();

        void setupBrandRecyclerView();

        void brandRefresh();

        void setupSelectedStyleCount(int count, int total);

        void showEmptyPanel();

        void hideEmptyPanel();
    }
}
