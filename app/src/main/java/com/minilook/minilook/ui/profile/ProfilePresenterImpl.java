package com.minilook.minilook.ui.profile;

import com.google.gson.Gson;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.ipage.IpagePresenterImpl;
import com.minilook.minilook.ui.profile.di.ProfileArguments;
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
        view.setupEditText();
        reqProfile();
    }

    @Override public void onTextChanged(String text) {
        if (text.trim().length() > 0) {
            nick = text;
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


        addDisposable(memberRequest.updateNick(nick)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.ALREADY_USED)) {
                    view.showCheckMessage(data.getMessage());
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resUpdateKick, Timber::e));
    }

    private void resUpdateKick(BaseDataModel data) {
        view.hideCheckMessage();
        RxBus.send(new IpagePresenterImpl.RxBusEventNickChanged(nick));
    }

    private void reqProfile() {
        addDisposable(memberRequest.getProfile()
            .compose(Transformer.applySchedulers())
            .filter(data -> data.getCode().equals(HttpCode.OK))
            .map(data -> gson.fromJson(data.getData(), UserDataModel.class))
            .subscribe(this::resProfile, Timber::e));
    }

    private void resProfile(UserDataModel data) {
        view.setupNick(data.getNick());
        view.disableNickSaveButton();
        view.setupPhone(data.getPhone());
        view.setupEmail(data.getEmail());

        if (data.isShipping()) {
            view.setupShippingName(data.getShipping_name());
            view.setupShippingPhone(data.getShipping_phone());
            view.setupShippingAddress(
                data.getShipping_zipcode(),
                data.getShipping_address(),
                data.getShipping_address_detail());

            showShippingPanel();
        } else {
            hideShippingPanel();
        }
    }

    private void showShippingPanel() {
        view.hideEmptyShippingText();
        view.showShippingPanel();
        view.hideShippingAddButton();
        view.showShippingEditButton();
    }

    private void hideShippingPanel() {
        view.showEmptyShippingText();
        view.hideShippingPanel();
        view.showShippingAddButton();
        view.hideShippingEditButton();
    }
}