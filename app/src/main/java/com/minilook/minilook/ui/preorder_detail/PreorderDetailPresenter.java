package com.minilook.minilook.ui.preorder_detail;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.minilook.minilook.data.model.product.OptionDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingBrandDataModel;
import com.minilook.minilook.data.model.shopping.ShoppingProductDataModel;
import java.util.List;

public interface PreorderDetailPresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate();

    void onTabClick(int position);

    void onBuyClick();

    void onOptionSelectorBuyClick(List<ShoppingProductDataModel> shoppingProductData);

    interface View {

        void setupViewPager();

        void imageRefresh();

        void setupTabLayout();

        void setDetailImage(String url);

        void setupRecyclerView();

        void productRefresh();

        void setBrandName(String name);

        void setTitle(String title);

        void setRemainDate(int count);

        void setTermDate(String date);

        void setDeliveryDate(String date);

        void setEnableLabelBackground();

        void setDisableLabelBackground();

        void setLabel(String label);

        void enableBuyButton();

        void disableBuyButton(String name);

        void showCloseTextView();

        void scrollToPreorderInfo();

        void scrollToProduct();

        void scrollToShippingNRefund();

        void navigateToPreorderProductDetail(String title, int preorderNo, int productNo);

        void setupOptionSelector(OptionDataModel options);

        void showOptionSelector();

        void hideOptionSelector();

        void navigateToLogin();

        void navigateToOrder(List<ShoppingBrandDataModel> brandData);
    }
}
