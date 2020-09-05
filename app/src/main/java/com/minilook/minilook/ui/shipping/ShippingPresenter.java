package com.minilook.minilook.ui.shipping;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;

public interface ShippingPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onDefaultShippingDialogOkClick();

    void onAddClick();

    interface View {

        void setupRecyclerView();

        void showEmptyPanel();

        void refresh();

        void showDefaultShippingDialog();

        void navigateToShippingAdd();

        void navigateToShippingEdit(ShippingDataModel data);

        void finish();
    }
}
