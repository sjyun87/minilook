package com.minilook.minilook.ui.splash;

import android.os.Handler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
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

    private Gson gson = new Gson();

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
        commonRequest = new CommonRequest();
    }

    @Override public void onCreate() {
        checkAppVersion();
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
        addDisposable(commonRequest.checkVersion("0.0.1")
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
        int userId = App.getInstance().getUserId();
        if (userId == -1) {
            App.getInstance().setLogin(false);
        } else {
            App.getInstance().setLogin(true);
        }
        isLoginChecked = true;
        checkToDo();
    }

    private void checkToDo() {
        if (isLoginChecked && isAnimationEnd && isCommonDataGet) {
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