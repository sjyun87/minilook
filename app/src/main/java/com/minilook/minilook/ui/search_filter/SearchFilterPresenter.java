package com.minilook.minilook.ui.search_filter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import java.util.List;

public interface SearchFilterPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onGenderSelected(GenderDataModel data);

    void onAgeChanged(float value);

    void onAgeResetClick();

    void onAttributeDiscountClick();

    void onAttributeStockClick();

    void onCategorySelected(CodeDataModel data);

    void onPriceChanged(List<Float> values);

    void onColorSelected(ColorDataModel data);

    void onStyleSelected(CodeDataModel data);

    void onResetClick();

    void onSearchClick();

    interface View {

        void setupGenderRecyclerView();

        void genderRefresh();

        void setupAgeSlider();

        void resetAgeSlider();

        void setupAgeText(int age, boolean isMonth);

        void resetAgeText();

        void enableAgeSlider();

        void disableAgeSlider();

        void showAgeResetButton();

        void hideAgeResetButton();

        void setupCategoryRecyclerView();

        void categoryRefresh();

        void setupSelectedDiscount();

        void setupUnselectedDiscount();

        void setupSelectedStock();

        void setupUnselectedStock();

        void setupPriceSlider();

        void initPriceSlider(int min, int max, int step);

        void setupPriceValue(int currentMinStep, int currentMaxStep);

        void resetPriceSlider();

        void setupPriceText(int minPrice, int maxPrice, int type);

        void setupColorRecyclerView();

        void colorRefresh();

        void addStyleItem(CodeDataModel model);

        void selectedStyleView(int position);

        void unselectedStyleView(int position);

        void resetStyleView();

        void navigateToProductBridge(SearchOptionDataModel model);

        void scrollToTop();

        void finish();
    }
}
