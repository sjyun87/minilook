package com.minilook.minilook.ui.search_filter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.CategoryDataModel;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import java.util.List;

public interface SearchFilterPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onGenderSelected(GenderDataModel data);

    void onAgeChanged(float value);

    void onAttributeDiscountClick();

    void onAttributeStockClick();

    void onCategorySelected(CategoryDataModel data);

    void onPriceChanged(List<Float> values);

    void onColorSelected(ColorDataModel data);

    interface View {

        void setupGenderRecyclerView();

        void genderRefresh();

        void setupAgeSlider();

        void setupAgeText(int age, boolean isMonth);

        void setupCategoryRecyclerView();

        void categoryRefresh();

        void setupSelectedDiscount();

        void setupUnselectedDiscount();

        void setupSelectedStock();

        void setupUnselectedStock();

        void setupPriceSlider();

        void initPriceSlider(int min, int max, int step);

        void setupPriceText(int minPrice, int maxPrice, boolean isMinPriceLimit, boolean isMaxPriceLimit);

        void setupColorRecyclerView();

        void colorRefresh();
    }
}
