package com.minilook.minilook.ui.profile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.ipage.IpagePresenterImpl;
import com.minilook.minilook.ui.profile.di.ProfileArguments;
import com.minilook.minilook.ui.shipping.ShippingPresenterImpl;
import com.minilook.minilook.ui.verify.VerifyActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ProfilePresenterImpl extends BasePresenterImpl implements ProfilePresenter {

    private final View view;
    private final MemberRequest memberRequest;

    private Gson gson = new Gson();
    private String nick;

    public ProfilePresenterImpl(ProfileArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupEditText();
        reqProfile();
    }

    @Override public void onTextChanged(String text) {
        if (text.trim().length() > 0) {
            nick = text.trim();
            view.enableNickSaveButton();
            view.showNickClearButton();
        } else {
            view.disableNickSaveButton();
            view.hideNickClearButton();
        }
    }

    @Override public void onNickClearClick() {
        view.setupNick("");
    }

    @Override public void onNickSaveClick() {
        view.hideKeyboard();
        view.disableNickSaveButton();

        reqUpdateNick();
    }

    @Override public void onPhoneEditClick() {
        view.navigateToWebView(URLKeys.URL_VERIFY);
    }

    @Override public void onShippingManagementClick() {
        view.navigateToShipping();
    }

    private void reqProfile() {
        addDisposable(memberRequest.getProfile()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), MemberDataModel.class))
            .subscribe(this::resProfile, Timber::e));
    }

    private void resProfile(MemberDataModel data) {
        view.setupNick(data.getNick());
        view.disableNickSaveButton();
        view.setupPhone(data.getPhone());
        view.setupEmail(data.getEmail());

        if (data.getAddressNo() != 0) {
            view.setupShippingName(data.getShippingName());
            view.setupShippingPhone(data.getShippingPhone());
            view.setupShippingAddress(
                data.getShippingZipcode(),
                data.getShippingAddress(),
                data.getShippingAddressDetail());

            showShippingPanel();
        } else {
            hideShippingPanel();
        }
    }

    private void reqUpdateNick() {
        addDisposable(memberRequest.updateNick(nick)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.ALREADY_USED)) {
                    view.showCheckMessage(data.getMessage());
                } else if (code.equals(HttpCode.INTERNAL_SERVER_ERROR)) {
                    view.showCheckErrorMessage();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resUpdateNick, Timber::e));
    }

    private void resUpdateNick(BaseDataModel data) {
        view.showUpdateCompletedToast();
        view.hideCheckMessage();
        RxBus.send(new IpagePresenterImpl.RxBusEventNickChanged(nick));
    }

    private void updatePhone(String json) {
        JsonObject data = gson.fromJson(json, JsonObject.class);
        String phone = data.get("phoneNumber").getAsString();
        String name = data.get("name").getAsString();
        String ci = data.get("ci").getAsString();
        reqUpdatePhone(phone, name, ci);
        view.setupPhone(phone);
    }

    private void reqUpdatePhone(String phone, String name, String ci) {
        addDisposable(memberRequest.updatePhone(phone, name, ci)
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .subscribe());
    }

    private void showShippingPanel() {
        view.hideEmptyShippingText();
        view.showShippingPanel();
    }

    private void hideShippingPanel() {
        view.showEmptyShippingText();
        view.hideShippingPanel();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof VerifyActivity.RxEventIdentityVerificationComplete) {
                String json = ((VerifyActivity.RxEventIdentityVerificationComplete) o).getJson();
                updatePhone(json);
            } else if (o instanceof RxEventShippingUpdated) {
                reqProfile();
            } else if (o instanceof ShippingPresenterImpl.RxEventShippingDeleteClick) {
                reqProfile();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventShippingUpdated {
    }
}