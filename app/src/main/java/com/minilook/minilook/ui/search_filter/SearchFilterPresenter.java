package com.minilook.minilook.ui.search_filter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.GenderDataModel;

public interface SearchFilterPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onGenderSelected(GenderDataModel data);

    void onAgeChanged(float value);

    interface View {

        void setupGenderRecyclerView();

        void genderRefresh();

        void setupAgeSlider();

        void setupAge(int age, boolean isMonth);
    }
}
