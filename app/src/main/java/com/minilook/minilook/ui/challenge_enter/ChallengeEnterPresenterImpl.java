package com.minilook.minilook.ui.challenge_enter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.URLKeys;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.member.InfoStatusDataModel;
import com.minilook.minilook.data.model.member.MemberDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge_detail.ChallengeDetailPresenterImpl;
import com.minilook.minilook.ui.challenge_enter.di.ChallengeEnterArguments;
import com.minilook.minilook.ui.verify.VerifyActivity;
import io.reactivex.rxjava3.functions.Function;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengeEnterPresenterImpl extends BasePresenterImpl implements ChallengeEnterPresenter {

    private final View view;
    private final int challengeNo;
    private final ChallengeRequest challengeRequest;
    private final MemberRequest memberRequest;
    private final Gson gson;

    private final MemberDataModel memberData = new MemberDataModel();
    private boolean isVerifyComplete;
    private boolean isAgreeTerms = false;

    public ChallengeEnterPresenterImpl(ChallengeEnterArguments args) {
        view = args.getView();
        challengeNo = args.getChallengeNo();
        challengeRequest = new ChallengeRequest();
        memberRequest = new MemberRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupInstargramEditText();
        view.setupBlogEditText();

        checkPhoneNumber();
    }

    private void checkPhoneNumber() {
        addDisposable(challengeRequest.checkPhoneNumber()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, MemberDataModel>)
                data -> gson.fromJson(data.getData(), MemberDataModel.class))
            .subscribe(this::onCheckPhoneNumber, Timber::e));
    }

    private void onCheckPhoneNumber(MemberDataModel data) {
        memberData.setPhone(data.getPhone());
        memberData.setCi(data.getCi());

        view.setPhoneNumber(data.getPhone());
        isVerifyComplete = true;
    }

    @Override public void onCertifyClick() {
        view.navigateToVerify();
    }

    @Override public void onInstagramTextChanged(String text) {
        memberData.setInstagram(text.trim());
        checkButtonEnable();
    }

    @Override public void onBlogTextChanged(String text) {
        memberData.setBlog(text.trim());
        checkButtonEnable();
    }

    @Override public void onTermsAgree() {
        isAgreeTerms = !isAgreeTerms;
        if (isAgreeTerms) {
            view.checkTerms();
        } else {
            view.uncheckTerms();
        }
        checkButtonEnable();
    }

    @Override public void onNoticeMoreClick() {
        view.navigateToWebView(URLKeys.URL_TERMS_OF_USE);
    }

    @Override public void onApplyClick() {
        enterChallenge();
    }

    @Override public void onDialogCloseClick() {
        view.finish();
        RxBus.send(new ChallengeDetailPresenterImpl.RxEventChallengeEnterFinish());
    }

    @Override public void onDialogLaterClick() {
        view.finish();
        RxBus.send(new ChallengeDetailPresenterImpl.RxEventChallengeEnterFinish());
    }

    @Override public void onDialogAgreeClick() {
        updateMarketingAgree(true);
    }

    private void parseData(String json) {
        JsonObject data = gson.fromJson(json, JsonObject.class);
        String phone = data.get("phoneNumber").getAsString();
        String name = data.get("name").getAsString();
        String ci = data.get("ci").getAsString();

        memberData.setName(name);
        memberData.setPhone(phone);
        memberData.setCi(ci);

        view.setPhoneNumber(phone);
        checkButtonEnable();
    }

    private void checkButtonEnable() {
        if (isVerifyComplete
            && (!TextUtils.isEmpty(memberData.getInstagram()) || !TextUtils.isEmpty(memberData.getBlog()))
            && isAgreeTerms) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    private void enterChallenge() {
        addDisposable(challengeRequest.enterChallenge(challengeNo, memberData)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::onEnterChallenge, Timber::e));
    }

    private void onEnterChallenge(BaseDataModel model) {
        RxBus.send(new RxEventEnterChallenge());

        getMarketingInfoStatus();
    }

    private void getMarketingInfoStatus() {
        addDisposable(memberRequest.getInfoStatus()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showFinishDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), InfoStatusDataModel.class))
            .subscribe(this::onMarketingInfoStatus, Timber::e));
    }

    private void onMarketingInfoStatus(InfoStatusDataModel data) {
        if (data.isMarketingInfo()) {
            view.showFinishDialog();
        } else {
            view.showFinishAndMarketingInfoDialog();
        }
    }

    private void updateMarketingAgree(boolean enable) {
        addDisposable(memberRequest.updateMarketingInfo(enable)
            .compose(Transformer.applySchedulers())
            .subscribe(this::onResUpdateMarketingAgree, Timber::e));
    }

    private void onResUpdateMarketingAgree(BaseDataModel data) {
        view.finish();
        RxBus.send(new ChallengeDetailPresenterImpl.RxEventChallengeEnterFinish());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof VerifyActivity.RxEventIdentityVerificationComplete) {
                isVerifyComplete = true;
                String json = ((VerifyActivity.RxEventIdentityVerificationComplete) o).getJson();
                parseData(json);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventEnterChallenge {
    }
}