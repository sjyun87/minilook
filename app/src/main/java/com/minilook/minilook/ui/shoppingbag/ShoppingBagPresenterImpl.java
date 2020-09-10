package com.minilook.minilook.ui.shoppingbag;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.pick.PickBrandDataModel;
import com.minilook.minilook.data.model.pick.PickOptionDataModel;
import com.minilook.minilook.data.model.pick.PickProductDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.DisplayCode;
import com.minilook.minilook.data.type.ShippingCode;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shoppingbag.di.ShoppingBagArguments;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ShoppingBagPresenterImpl extends BasePresenterImpl implements ShoppingBagPresenter {

    private final View view;
    private final BaseAdapterDataModel<PickBrandDataModel> adapter;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();
    private List<PickBrandDataModel> shoppingbagItems;

    private boolean isAllChecked = true;

    public ShoppingBagPresenterImpl(ShoppingBagArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        orderRequest = new OrderRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqShoppingBag();
    }

    @Override public void onAllCheckClick() {
        isAllChecked = !isAllChecked;
        if (isAllChecked) {
            setupAllCheck();
            view.checkImageView();
        } else {
            setupAllUncheck();
            view.uncheckImageView();
        }
        view.refresh();
        for (PickBrandDataModel brandData : adapter.get()) {
            calBrandPrice(brandData);
        }
        setupTotalPrice();
    }

    @Override public void onDeleteClick() {
        reqDeleteShoppingBag(getDeleteOptions());
        deleteProduct();
        view.refresh();
    }

    @Override public void onOrderClick() {
        view.showTrialVersionDialog();
        //view.navigateToOrder(getSelectedData());
        //view.finish();
    }

    @Override public void onEmptyClick() {
        view.navigateToMain();
    }

    @Override public void onTrialVersionDialogGoClick() {
        view.navigateToEventDetail();
    }

    private void setupAllCheck() {
        for (PickBrandDataModel brandData : adapter.get()) {
            for (PickProductDataModel productData : brandData.getProducts()) {
                productData.setSelected(true);
            }
        }
    }

    private void setupAllUncheck() {
        for (PickBrandDataModel brandData : adapter.get()) {
            for (PickProductDataModel productData : brandData.getProducts()) {
                productData.setSelected(false);
            }
        }
    }

    private void reqShoppingBag() {
        addDisposable(orderRequest.getShoppingBag()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<PickBrandDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<PickBrandDataModel>>() {
                }.getType()))
            .subscribe(this::resShoppingBag, Timber::e));
    }

    private void resShoppingBag(List<PickBrandDataModel> data) {
        shoppingbagItems = data;
        for (PickBrandDataModel brandData : data) {
            adapter.add(calBrandPrice(brandData));
        }
        view.refresh();
        setupTotalPrice();
    }

    private void setupTotalPrice() {
        int totalProductPrice = 0;
        int totalShippingPrice = 0;
        int totalOptionCount = 0;
        int totalProductCount = 0;
        int totalSelectedProductCount = 0;
        for (PickBrandDataModel brandData : adapter.get()) {
            totalProductCount += brandData.getProducts().size();
            totalSelectedProductCount += brandData.getTotal_selected_product();
            totalProductPrice += brandData.getTotal_products_price();
            totalShippingPrice += brandData.getFinal_shipping_price();
            totalOptionCount += brandData.getTotal_option_count();
        }

        view.setupTotalCount(totalOptionCount);
        view.setupTotalProductPrice(totalProductPrice);
        view.setupTotalShippingPrice(totalShippingPrice);
        view.setupTotalPrice(totalProductPrice + totalShippingPrice);

        view.setupCheckCount(totalProductCount, totalSelectedProductCount);
        if (totalSelectedProductCount != 0) {
            view.enableOrderButton();
        } else {
            view.disableOrderButton();
        }

        if (totalProductCount > totalSelectedProductCount) {
            isAllChecked = false;
            view.uncheckImageView();
        } else {
            isAllChecked = true;
            view.checkImageView();
        }
    }

    private PickBrandDataModel calBrandPrice(PickBrandDataModel brandData) {
        int totalSelectedProductCount = 0;
        int totalProductsPrice = 0;
        int totalOptionCount = 0;

        for (PickProductDataModel productData : brandData.getProducts()) {
            productData.setBrand_id(brandData.getBrand_id());

            if (!productData.isSelected()) {
                brandData.setBillVisible(false);
                continue;
            }
            totalSelectedProductCount++;

            int displayCode = productData.getDisplay_code();
            if (displayCode != DisplayCode.DISPLAY.getValue()) {
                brandData.setBillVisible(false);
                continue;
            }
            brandData.setBillVisible(true);

            int price_basic = productData.getPrice();

            for (PickOptionDataModel optionData : productData.getOptions()) {
                optionData.setBrand_id(brandData.getBrand_id());

                int price = price_basic + optionData.getPrice_add();
                optionData.setPrice_sum(price);
                int quantity = optionData.getQuantity();
                totalOptionCount += quantity;
                totalProductsPrice += (price * quantity);

                optionData.setOrder_available_quantity(
                    Math.min(productData.getQuantity_limit(), optionData.getStock()));
            }
        }

        brandData.setTotal_products_price(totalProductsPrice);
        brandData.setTotal_option_count(totalOptionCount);
        brandData.setTotal_selected_product(totalSelectedProductCount);

        if (totalOptionCount > 0) {
            boolean isFreeShipping;
            int finalShippingPrice;
            int shippingCode = brandData.getShipping_type_code();
            if (shippingCode == ShippingCode.FREE.getValue()) {
                isFreeShipping = true;
                finalShippingPrice = 0;
            } else if (shippingCode == ShippingCode.CONDITIONAL.getValue()) {
                isFreeShipping = brandData.getTotal_products_price() >= brandData.getCondition_free_shipping();
                if (isFreeShipping) {
                    finalShippingPrice = 0;
                } else {
                    finalShippingPrice = brandData.getCondition_shipping_price();
                }
            } else {
                isFreeShipping = false;
                finalShippingPrice = brandData.getShipping_price();
            }

            brandData.setFreeShipping(isFreeShipping);
            brandData.setFinal_shipping_price(finalShippingPrice);
        } else {
            brandData.setFreeShipping(false);
            brandData.setFinal_shipping_price(0);
        }
        return brandData;
    }

    private PickBrandDataModel getBrandModel(int brand_id) {
        return Observable.fromIterable(shoppingbagItems)
            .filter(data -> data.getBrand_id() == brand_id)
            .blockingFirst();
    }

    private void deleteProduct() {
        List<PickBrandDataModel> tempBrandData = new ArrayList<>(adapter.get());
        for (PickBrandDataModel brandData : tempBrandData) {
            for (PickProductDataModel productData : brandData.getProducts()) {
                if (productData.isSelected()) {
                    brandData.getProducts().remove(productData);
                    if (brandData.getProducts().size() == 0) adapter.remove(brandData);
                    break;
                }
            }
        }
        view.refresh();
        if (adapter.getSize() > 0) {
            for (PickBrandDataModel brandData : adapter.get()) {
                calBrandPrice(brandData);
            }
            setupTotalPrice();
        } else {
            view.showEmptyPanel();
        }
    }

    private void deleteOption(PickOptionDataModel target) {
        PickBrandDataModel brandData = getBrandModel(target.getBrand_id());
        PickBrandDataModel tempBrandData = brandData;
        for (PickProductDataModel productData : tempBrandData.getProducts()) {
            if (productData.getOptions().remove(target)) {
                if (productData.getOptions().size() == 0) brandData.getProducts().remove(productData);
                if (brandData.getProducts().size() == 0) adapter.remove(brandData);
            }
        }
        view.refresh();
        if (adapter.getSize() > 0) {
            calBrandPrice(brandData);
            setupTotalPrice();
        } else {
            view.showEmptyPanel();
        }
    }

    private List<Integer> getDeleteOptions() {
        List<Integer> items = new ArrayList<>();
        for (PickBrandDataModel brandData : adapter.get()) {
            for (PickProductDataModel productData : brandData.getProducts()) {
                if (productData.isSelected()) {
                    for (PickOptionDataModel optionData : productData.getOptions()) {
                        items.add(optionData.getShoppingbag_id());
                    }
                }
            }
        }
        return items;
    }

    private void reqUpdateQuantity(PickOptionDataModel data) {
        addDisposable(orderRequest.updateGoodsQuantity(data.getShoppingbag_id(), data.getQuantity())
            .subscribe());
    }

    private void reqDeleteShoppingBag(int shoppingbag_id) {
        List<Integer> deleteItems = new ArrayList<>();
        deleteItems.add(shoppingbag_id);
        reqDeleteShoppingBag(deleteItems);
    }

    private void reqDeleteShoppingBag(List<Integer> deleteItem) {
        addDisposable(orderRequest.deleteShoppingBag(deleteItem)
            .subscribe());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventOptionCountChanged) {
                PickOptionDataModel data = ((RxBusEventOptionCountChanged) o).getOptionData();
                view.refresh();
                reqUpdateQuantity(data);
                calBrandPrice(getBrandModel(data.getBrand_id()));
                setupTotalPrice();
            } else if (o instanceof RxBusEventProductCheckedChanged) {
                int brand_id = ((RxBusEventProductCheckedChanged) o).getBrand_id();
                calBrandPrice(getBrandModel(brand_id));
                view.refresh();
                setupTotalPrice();
            } else if (o instanceof RxBusEventOptionDeleted) {
                PickOptionDataModel data = ((RxBusEventOptionDeleted) o).getOptionData();
                reqDeleteShoppingBag(data.getShoppingbag_id());
                deleteOption(data);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventOptionCountChanged {
        private PickOptionDataModel optionData;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventOptionDeleted {
        private PickOptionDataModel optionData;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventProductCheckedChanged {
        private int brand_id;
    }
}