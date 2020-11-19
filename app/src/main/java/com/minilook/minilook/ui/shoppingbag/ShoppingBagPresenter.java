package com.minilook.minilook.ui.shoppingbag;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import java.util.List;

public interface ShoppingBagPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume();

    void onAllCheckClick();

    void onDeleteClick();

    void onOrderClick();

    void onEmptyClick();

    void onTrialVersionDialogGoClick();

    interface View {

        void setupRecyclerView();

        void refresh();

        void setupCheckCount(int total, int count);

        void setupTotalCount(int count);

        void setupTotalProductPrice(int price);

        void setupTotalShippingPrice(int price);

        void setupTotalPrice(int price);

        void checkImageView();

        void uncheckImageView();

        void enableOrderButton();

        void disableOrderButton();

        void showEmptyPanel();

        void showTrialVersionDialog();

        void navigateToMain();

        void navigateToOrder(List<ShoppingBrandDataModel> items);

        void navigateToEventDetail();

        void finish();
    }
}
