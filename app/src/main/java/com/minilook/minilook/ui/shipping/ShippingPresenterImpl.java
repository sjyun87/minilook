package com.minilook.minilook.ui.shipping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.shipping.ShippingRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.profile.ProfileActivity;
import com.minilook.minilook.ui.profile.ProfilePresenterImpl;
import com.minilook.minilook.ui.shipping.di.ShippingArguments;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ShippingPresenterImpl extends BasePresenterImpl implements ShippingPresenter {

    private final View view;
    private final String route;
    private final BaseAdapterDataModel<ShippingDataModel> adapter;
    private final ShippingRequest shippingRequest;

    private Gson gson = new Gson();
    private ShippingDataModel selectedShipping;

    public ShippingPresenterImpl(ShippingArguments args) {
        view = args.getView();
        route = args.getRoute();
        adapter = args.getAdapter();
        shippingRequest = new ShippingRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        reqShippings();
    }

    @Override public void onUpdateDefaultOkClick() {
        selectedShipping.setDefault(true);
        reqUpdateDefault();
    }

    private void reqShippings() {
        addDisposable(shippingRequest.getShippings()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<ShippingDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<ShippingDataModel>>() {
                }.getType()))
            .subscribe(this::resShippings, Timber::e));
    }

    private void resShippings(List<ShippingDataModel> data) {
        adapter.set(data);
        view.refresh();
    }

    private void reqDeleteShipping(int address_id) {
        addDisposable(shippingRequest.deleteShipping(address_id)
            .subscribe());
    }

    private void removeShipping(ShippingDataModel data) {
        adapter.remove(data);
        view.refresh();

        if (adapter.getSize() == 0) view.showEmptyPanel();
    }

    private void handleData(ShippingDataModel data) {
        if (route.equals(ProfileActivity.class.getSimpleName())) {
            if (data.isDefault()) {
                selectShipping();
            } else {
                selectedShipping = data;
                view.showUpdateShippingDialog();
            }
        } else {
                // TODO 주문서에서 들어올때
        }
    }

    private void selectShipping() {
        RxBus.send(new ProfilePresenterImpl.RxEventShippingUpdated());
        view.finish();
    }

    private void reqUpdateDefault() {
        addDisposable(shippingRequest.updateShipping(selectedShipping)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                Timber.e(data.toString());
                return data.getCode().equals(HttpCode.OK);
            })
            .subscribe(this::resUpdateDefault, Timber::e));
    }

    private void resUpdateDefault(BaseDataModel data) {
        selectShipping();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventShippingSelect) {
                ShippingDataModel data = ((RxEventShippingSelect) o).data;
                handleData(data);
            } else if (o instanceof RxEventShippingDelete) {
                ShippingDataModel data = ((RxEventShippingDelete) o).data;
                removeShipping(data);
                reqDeleteShipping(data.getAddress_id());
            } else if (o instanceof RxEventShippingAdd) {
                reqShippings();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingSelect {
        private ShippingDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingAdd {
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingEdit {
        private ShippingDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingDelete {
        private ShippingDataModel data;
    }
}