package com.minilook.minilook.ui.profile;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.user.UserDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.profile.di.ProfileArguments;
import timber.log.Timber;

public class ProfilePresenterImpl extends BasePresenterImpl implements ProfilePresenter {

    private final View view;
    private final MemberRequest memberRequest;

    private Gson gson = new Gson();

    public ProfilePresenterImpl(ProfileArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        reqProfile();
    }

    private void reqProfile() {
        addDisposable(memberRequest.getProfile()
            .map(data -> gson.fromJson(data.getData(), UserDataModel.class))
            .compose(Transformer.applySchedulers())
            .subscribe(this::resProfile, Timber::e));
    }

    private void resProfile(UserDataModel data) {
        Timber.e(data.toString());
        view.setupNick(data.getNick());
        view.setupPhone(data.getPhone());
        view.setupEmail(data.getEmail());

        view.setupShippingName(data.getShipping_name());
        view.setupShippingPhone(data.getShipping_phone());
        view.setupShippingAddress(
            data.getShipping_zipcode(),
            data.getShipping_address(),
            data.getShipping_address_detail());
    }
}