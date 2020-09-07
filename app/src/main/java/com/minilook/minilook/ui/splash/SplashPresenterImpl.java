package com.minilook.minilook.ui.splash;

import android.os.Handler;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import com.minilook.minilook.data.model.common.VersionDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.data.type.VersionStatus;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.splash.di.SplashArguments;
import com.pixplicity.easyprefs.library.Prefs;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private static final int TIME_ANIMATION = 2000;

    private final View view;
    private final CommonRequest commonRequest;

    private boolean isAnimationEnd = false;
    private boolean isLoginChecked = false;
    private boolean isCommonDataGet = false;
    private boolean isTokenChecked = false;

    private Gson gson = new Gson();

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
        commonRequest = new CommonRequest();
    }

    @Override public void onCreate() {
        if (BuildConfig.DEBUG) {
            startApp();
        } else {
            checkAppVersion();
        }
    }

    @Override public void onUpdateDialogOkClick() {
        view.navigateToPlatStore();
    }

    @Override public void onUpdateDialogCancelClick() {
        view.finish();
    }

    private void checkAppVersion() {
        reqCheckAppVersion();
    }

    private void reqCheckAppVersion() {
        addDisposable(commonRequest.checkVersion(BuildConfig.VERSION_NAME)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                // TODO 예외 처리
                return data.getCode().equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), VersionDataModel.class))
            .subscribe(this::resCheckAppVersion, Timber::e));
    }

    private void resCheckAppVersion(VersionDataModel data) {
        if (data.getStatus() == VersionStatus.FORCE.getValue()) {
            view.showUpdateDialog();
        } else {
            startApp();
        }
    }

    private void startApp() {
        reqSortCode();
        checkAnimation();
        checkLogin();
        checkToken();
    }

    private void reqSortCode() {
        addDisposable(commonRequest.getSortCode()
            .map((Function<BaseDataModel, List<SortDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<SortDataModel>>() {
                }.getType()))
            .subscribe(this::resSortCode, Timber::e));
    }

    private void resSortCode(List<SortDataModel> data) {
        App.getInstance().setSortCodes(data);
        isCommonDataGet = true;
        checkToDo();
    }

    private void checkAnimation() {
        new Handler().postDelayed(() -> {
            isAnimationEnd = true;
            checkToDo();
        }, TIME_ANIMATION);
    }

    private void checkLogin() {
        App.getInstance().checkLogin();
        isLoginChecked = true;
        checkToDo();
    }

    private void checkToken() {
        String token = App.getInstance().getPushToken();
        if (token.isEmpty()) {
            getCurrentToken();
        } else {
            isTokenChecked = true;
            checkToDo();
        }
    }

    private void getCurrentToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String pushToken = task.getResult().getToken();
                if (!pushToken.isEmpty()) {
                    isTokenChecked = true;
                    checkToDo();
                }
            }
        });
    }

    private void checkToDo() {
        if (isLoginChecked && isAnimationEnd && isCommonDataGet && isTokenChecked) {
            int visibleCount = Prefs.getInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, 0);
            if (visibleCount >= 3) {
                view.navigateToMain();
            } else {
                Prefs.putInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, ++visibleCount);
                view.navigateToGuide();
            }
        }
    }
}