package com.minilook.minilook.ui.shipping_add;

import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.shipping.ShippingDataModel;
import com.minilook.minilook.data.network.shipping.ShippingRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;
import com.minilook.minilook.ui.shipping_add.di.ShippingAddArguments;
import timber.log.Timber;

public class ShippingAddPresenterImpl extends BasePresenterImpl implements ShippingAddPresenter {

    private final View view;
    private final ShippingRequest shippingRequest;

    private String name;
    private String phone;
    private String address_detail;
    private boolean isDefault = false;

    public ShippingAddPresenterImpl(ShippingAddArguments args) {
        view = args.getView();
        shippingRequest = new ShippingRequest();
    }

    @Override public void onCreate() {
        view.setupNameEditText();
        view.setupPhoneEditText();
        view.setupAddressDetailEditText();
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
        reqAddShipping();
    }

    private void reqAddShipping() {
        addDisposable(shippingRequest.addShipping(getShippingModel())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe(this::resAddShipping, Timber::e));
    }

    private void resAddShipping(BaseDataModel dataModel) {
        RxBus.send(new ShippingPresenterImpl.RxEventShippingAdd());
    }

    private ShippingDataModel getShippingModel() {
        ShippingDataModel model = new ShippingDataModel();
        model.setName(name);
        model.setPhone(phone);
        // zip
        // address
        model.setAddress_detail(address_detail);
        model.setDefault(isDefault);
        return model;
    }

    private void checkButtonEnable() {
        if (name != null && !name.equals("")
            && phone != null && !phone.equals("")
            && address_detail != null && !address_detail.equals("")) {
            view.enableSaveButton();
        } else {
            view.disableSaveButton();
        }
    }
}