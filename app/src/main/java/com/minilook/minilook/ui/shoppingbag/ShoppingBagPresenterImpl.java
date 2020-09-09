package com.minilook.minilook.ui.shoppingbag;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.OrderBrandDataModel;
import com.minilook.minilook.data.model.order.OrderGoodsDataModel;
import com.minilook.minilook.data.model.order.OrderOptionDataModel;
import com.minilook.minilook.data.model.order.OrderProductDataModel;
import com.minilook.minilook.data.network.order.OrderRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.DisplayCode;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shoppingbag.di.ShoppingBagArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ShoppingBagPresenterImpl extends BasePresenterImpl implements ShoppingBagPresenter {

    private final View view;
    private final BaseAdapterDataModel<OrderBrandDataModel> adapter;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();
    private List<OrderBrandDataModel> shoppingDatas;

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

    @Override public void onCheckClick() {
        isAllChecked = !isAllChecked;
        if (isAllChecked) {
            setupAllCheck();
            view.checkImageView();
        } else {
            setupAllUncheck();
            view.uncheckImageView();
        }
        view.refresh();
        setupCheckCount();
    }

    @Override public void onDeleteClick() {
        reqDeleteShoppingBag(getDeleteData());
        view.refresh();
    }

    @Override public void onOrderClick() {
        view.showTrialVersionDialog();
        //view.navigateToOrder(getSelectedData());
        //view.finish();
    }

    private void reqDeleteShoppingBag(List<Integer> deleteItem) {
        addDisposable(orderRequest.deleteShoppingBag(deleteItem)
            .subscribe());
    }

    private List<OrderBrandDataModel> getSelectedData() {
        List<OrderBrandDataModel> brandItems = new ArrayList<>();
        List<OrderGoodsDataModel> goodsItems = new ArrayList<>();

        for (OrderBrandDataModel brandData : shoppingDatas) {
            for (OrderProductDataModel productData : brandData.getProducts()) {
                if (productData.isSelected()) {
                    for (OrderOptionDataModel optionData : productData.getOptions()) {
                        OrderGoodsDataModel goodsModel = new OrderGoodsDataModel();
                        goodsModel.setProduct_id(productData.getProduct_id());
                        goodsModel.setProduct_name(productData.getProduct_name());
                        goodsModel.setPrice(productData.getPrice());
                        goodsModel.setPrice_add(optionData.getPrice_add());
                        goodsModel.setSize_code(optionData.getSize_code());
                        goodsModel.setSize_name(optionData.getSize_name());
                        goodsModel.setColor_code(optionData.getColor_code());
                        goodsModel.setColor_name(optionData.getColor_name());
                        goodsModel.setQuantity(optionData.getQuantity());
                        goodsItems.add(goodsModel);
                    }
                }
            }
            brandData.setGoods(goodsItems);
            brandItems.add(brandData);
        }
        return brandItems;
    }

    private List<Integer> getDeleteData() {
        List<OrderBrandDataModel> tempData = new ArrayList<>(shoppingDatas);
        List<Integer> items = new ArrayList<>();
        for (OrderBrandDataModel brandData : tempData) {
            for (OrderProductDataModel productData : brandData.getProducts()) {
                if (productData.isSelected()) {
                    if (brandData.getProducts().size() == 1) {
                        shoppingDatas.remove(brandData);
                    } else {
                        shoppingDatas.get(shoppingDatas.indexOf(brandData)).getProducts().remove(productData);
                    }

                    for (OrderOptionDataModel goodsData : productData.getOptions()) {
                        items.add(goodsData.getShoppingbag_id());
                    }
                }
            }
        }
        return items;
    }

    private void setupAllCheck() {
        for (OrderBrandDataModel brandData : adapter.get()) {
            for (OrderProductDataModel productData : brandData.getProducts()) {
                productData.setSelected(true);
            }
        }
    }

    private void setupAllUncheck() {
        for (OrderBrandDataModel brandData : adapter.get()) {
            for (OrderProductDataModel productData : brandData.getProducts()) {
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
                    // TODO empty panel 노출
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<OrderBrandDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<OrderBrandDataModel>>() {
                }.getType()))
            .subscribe(this::resShoppingBag, Timber::e));
    }

    private void resShoppingBag(List<OrderBrandDataModel> data) {
        shoppingDatas = data;
        adapter.set(calPrice(shoppingDatas));
        view.refresh();
        setupCheckCount();
    }

    private List<OrderBrandDataModel> calPrice(List<OrderBrandDataModel> data) {
        int totalProductPrice = 0;
        int totalShippingPrice = 0;
        int orderCount = 0;
        for (OrderBrandDataModel brandData : data) {
            int brandTotalPrice = 0;
            boolean isBillDisplay = false;
            for (OrderProductDataModel productData : brandData.getProducts()) {
                int displayCode = productData.getDisplay_code();
                if (displayCode != DisplayCode.DISPLAY.getValue()) continue;
                isBillDisplay = true;
                orderCount++;
                int productPrice = productData.getPrice();
                for (OrderOptionDataModel goodsData : productData.getOptions()) {
                    int goodsPrice = productPrice + goodsData.getPrice_add();
                    goodsData.setPrice(goodsPrice);
                    brandTotalPrice += (goodsPrice * goodsData.getQuantity());
                    brandData.setTotal_products_price(brandTotalPrice);
                    goodsData.setOrder_available_quantity(
                        Math.min(productData.getQuantity_limit(), goodsData.getStock()));
                }
                if (brandTotalPrice >= brandData.getFree_shipping_conditions()) {
                    brandData.setShippingFree(true);
                } else {
                    brandData.setShippingFree(false);
                    brandData.setFree_shipping_left(brandData.getFree_shipping_conditions() - brandTotalPrice);
                }
            }
            brandData.setTotal_products_price(brandTotalPrice);
            brandData.setBillDisplay(isBillDisplay);

            boolean isShippingFree = brandTotalPrice > brandData.getFree_shipping_conditions();
            brandData.setShippingFree(isShippingFree);
            totalProductPrice += brandTotalPrice;
            totalShippingPrice += isShippingFree ? 0 : brandData.getShipping_price();
        }

        view.setupTotalCount(orderCount);
        view.setupTotalProductPrice(totalProductPrice);
        view.setupTotalShippingPrice(totalShippingPrice);
        view.setupTotalPrice(totalProductPrice + totalShippingPrice);
        return data;
    }

    private void deleteData(OrderOptionDataModel targetGoodsData) {
        List<OrderBrandDataModel> brandDatas = adapter.get();
        List<OrderProductDataModel> productDatas = null;
        List<OrderOptionDataModel> goodsDatas = null;
        OrderBrandDataModel targetBrandData = null;
        OrderProductDataModel targetProductData = null;

        for (OrderBrandDataModel brandData : brandDatas) {
            for (OrderProductDataModel productData : brandData.getProducts()) {
                if (productData.getDisplay_code() != DisplayCode.DISPLAY.getValue()) continue;
                for (OrderOptionDataModel goodsData : productData.getOptions()) {
                    if (goodsData == targetGoodsData) {
                        productDatas = brandData.getProducts();
                        goodsDatas = productData.getOptions();
                        targetBrandData = brandData;
                        targetProductData = productData;
                        break;
                    }
                }
            }
        }
        if (goodsDatas != null) {
            if (goodsDatas.size() == 1) {
                if (productDatas.size() == 1) {
                    brandDatas.remove(targetBrandData);
                } else {
                    productDatas.remove(targetProductData);
                }
            } else {
                goodsDatas.remove(targetGoodsData);
            }

            calPrice(brandDatas);
            view.refresh();
            // TODO 삭제
        }
    }

    private void reqUpdateQuantity(OrderOptionDataModel data) {
        addDisposable(orderRequest.updateGoodsQuantity(data.getShoppingbag_id(), data.getQuantity())
            .subscribe());
    }

    private void setupCheckCount() {
        int total = 0;
        int count = 0;
        for (OrderBrandDataModel brandData : adapter.get()) {
            total += brandData.getProducts().size();
            for (OrderProductDataModel productData : brandData.getProducts()) {
                if (productData.isSelected()) count++;
            }
        }
        view.setupCheckCount(total, count);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventSelectedGoodsCount) {
                OrderOptionDataModel data = ((RxBusEventSelectedGoodsCount) o).getGoodsData();
                reqUpdateQuantity(data);
                calPrice(adapter.get());
                view.refresh();
            } else if (o instanceof RxBusEventSelectedGoodsDelete) {
                OrderOptionDataModel data = ((RxBusEventSelectedGoodsDelete) o).getGoodsData();
                deleteData(data);
            } else if (o instanceof RxBusEventSelectedChanged) {
                view.refresh();
                setupCheckCount();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSelectedGoodsCount {
        private OrderOptionDataModel goodsData;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSelectedGoodsDelete {
        private OrderOptionDataModel goodsData;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSelectedChanged {
    }
}