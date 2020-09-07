package com.minilook.minilook.ui.shoppingbag;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.order.ShoppingBagDataModel;
import com.minilook.minilook.data.model.product.GoodsDataModel;
import com.minilook.minilook.data.model.product.ShoppingProductDataModel;
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
    private final BaseAdapterDataModel<ShoppingBagDataModel> adapter;
    private final OrderRequest orderRequest;

    private Gson gson = new Gson();
    private List<ShoppingBagDataModel> selectedItems;

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
            .map((Function<BaseDataModel, List<ShoppingBagDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ShoppingBagDataModel>>() {
                }.getType()))
            .subscribe(this::resShoppingBag, Timber::e));
    }

    private void resShoppingBag(List<ShoppingBagDataModel> data) {
        selectedItems = data;
        adapter.set(calPrice(selectedItems));
        view.refresh();
    }

    private List<ShoppingBagDataModel> calPrice(List<ShoppingBagDataModel> data) {
        for (ShoppingBagDataModel brandData : data) {
            int totalPrice = 0;
            for (ShoppingProductDataModel productData : brandData.getProducts()) {
                int displayCode = productData.getDisplay_code();
                if (displayCode != DisplayCode.DISPLAY.getValue()) continue;
                int productPrice = productData.getPrice();
                for (GoodsDataModel goodsData : productData.getGoods()) {
                    int goodsPrice = productPrice + goodsData.getPrice_add();
                    goodsData.setPrice(goodsPrice);
                    totalPrice += (goodsPrice * goodsData.getSelected_quantity());
                    brandData.setProductsPrice(totalPrice);
                    goodsData.setOrder_available_quantity(Math.min(productData.getQuantity_limit(), goodsData.getGoods_stock()));
                }
                if (totalPrice >= brandData.getShipping_limit()) {
                    brandData.setShippingFree(true);
                } else {
                    brandData.setShippingFree(false);
                    brandData.setRemainShippingFree(brandData.getShipping_limit() - totalPrice);
                }
            }
            brandData.setProductsPrice(totalPrice);

            boolean isShippingFree = totalPrice > brandData.getShipping_limit();
            brandData.setShippingFree(isShippingFree);
        }
        return data;
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxBusEventSelectedGoodsCount) {
                GoodsDataModel data = ((RxBusEventSelectedGoodsCount) o).getGoodsData();
                calPrice(adapter.get());
                view.refresh();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxBusEventSelectedGoodsCount {
        private GoodsDataModel goodsData;
    }
}