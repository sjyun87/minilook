package com.minilook.minilook.ui.shoppingbag;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import java.util.List;

public interface ShoppingBagPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onCheckClick();

    void onDeleteClick();

    void onOrderClick();

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

        void navigateToOrder(List<OrderBrandDataModel> items);

        void finish();
    }
}
