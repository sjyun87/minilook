package com.minilook.minilook.ui.shipping_update;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.shipping.ShippingRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_zip.SearchZipActivity;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;
import com.minilook.minilook.ui.shipping_update.di.ShippingUpdateArguments;
import timber.log.Timber;

public class ShippingUpdatePresenterImpl extends BasePresenterImpl implements ShippingUpdatePresenter {

    private final View view;
    private final ShippingDataModel shippingData;
    private final ShippingRequest shippingRequest;
    private final boolean isAddPage;

    private Gson gson = new Gson();

    private String name;
    private String phone;
    private String zip;
    private String address;
    private String address_detail;
    private boolean isDefault = false;

    public ShippingUpdatePresenterImpl(ShippingUpdateArguments args) {
        view = args.getView();
        shippingData = args.getShippingData();
        shippingRequest = new ShippingRequest();
        isAddPage = shippingData == null;
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupNameEditText();
        view.setupPhoneEditText();
        view.setupAddressDetailEditText();

        if (!isAddPage) {
            view.setupShippingEditTitleBar();
            name = shippingData.getName();
            phone = shippingData.getPhone();
            zip = shippingData.getZipcode();
            address = shippingData.getAddress();
            address_detail = shippingData.getAddressDetail();
            isDefault = shippingData.isDefault();

            view.setupName(shippingData.getName());
            view.setupPhone(shippingData.getPhone());
            view.setupZip(shippingData.getZipcode());
            view.setupAddress(shippingData.getAddress());
            view.setupAddressDetail(shippingData.getAddressDetail());
            if (shippingData.isDefault()) {
                view.checkDefault();
            } else {
                view.uncheckDefault();
            }
            checkButtonEnable();
        }
    }

    @Override public void onSearchZipClick() {
        view.navigateToSearchZip();
    }

    @Override public void onNameTextChanged(String text) {
        name = text;
        checkButtonEnable();
    }

    @Override public void onPhoneTextChanged(String text) {
        phone = text;
        checkButtonEnable();
    }

    @Override public void onAddressDetailTextChanged(String text) {
        address_detail = text;
        checkButtonEnable();
    }

    @Override public void onDefaultClick() {
        isDefault = !isDefault;
        if (isDefault) {
            view.checkDefault();
        } else {
            view.uncheckDefault();
        }
        checkButtonEnable();
    }

    @Override public void onSaveClick() {
        if (isAddPage) {
            reqAddShipping();
        } else {
            reqEditShipping();
        }
    }

    private void reqAddShipping() {
        addDisposable(shippingRequest.addShipping(getShippingModel())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe(this::resUpdateShipping, Timber::e));
    }

    private void reqEditShipping() {
        addDisposable(shippingRequest.updateShipping(getShippingModel())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe(this::resUpdateShipping, Timber::e));
    }

    private void resUpdateShipping(BaseDataModel data) {
        RxBus.send(new ShippingPresenterImpl.RxEventShippingUpdated());
        view.finish();
    }

    private ShippingDataModel getShippingModel() {
        ShippingDataModel model = new ShippingDataModel();
        if (!isAddPage) model.setAddressNo(shippingData.getAddressNo());
        model.setName(name);
        model.setPhone(phone);
        model.setZipcode(zip);
        model.setAddress(address);
        model.setAddressDetail(address_detail);
        model.setDefault(isDefault);
        return model;
    }

    private void checkButtonEnable() {
        if (name != null && !name.equals("")
            && phone != null && !phone.equals("")
            && zip != null && !zip.equals("")
            && address != null && !address.equals("")
            && address_detail != null && !address_detail.equals("")) {
            view.enableSaveButton();
        } else {
            view.disableSaveButton();
        }
    }

    private void updateZip(String json) {
        JsonObject data = gson.fromJson(json, JsonObject.class);
        zip = data.get("zonecode").getAsString();
        address = data.get("address").getAsString();

        view.setupZip(zip);
        view.setupAddress(address);
        checkButtonEnable();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof SearchZipActivity.RxEventSearchAddressComplete) {
                String json = ((SearchZipActivity.RxEventSearchAddressComplete) o).getJson();
                updateZip(json);
            }
        }, Timber::e));
    }
}