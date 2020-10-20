package com.minilook.minilook.ui.splash;

import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.BuildConfig;
import com.minilook.minilook.data.code.VersionStatus;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.common.VersionDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.splash.di.SplashArguments;
import com.pixplicity.easyprefs.library.Prefs;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class SplashPresenterImpl extends BasePresenterImpl implements SplashPresenter {

    private final View view;
    private final CommonRequest commonRequest;

    private boolean isAnimationEnd = false;
    private boolean isCommonDataGet = false;

    private Gson gson = new Gson();

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
        commonRequest = new CommonRequest();
    }

    @Override public void onCreate() {
        view.setupLottieView();
        reqCheckAppVersion();
    }

    @Override public void onAnimationEnd() {
        isAnimationEnd = true;
        checkToDo();
    }

    @Override public void onUpdateDialogOkClick() {
        view.navigateToPlayStore();
    }

    @Override public void onUpdateDialogCancelClick() {
        view.finish();
    }

    @Override public void onErrorDialogOkClick() {
        view.finish();
    }

    @Override public void onDynamicLinkCheckComplete(Task<PendingDynamicLinkData> task) {
        if (task.getResult() != null) {

        }
    }

    private void reqCheckAppVersion() {
        if (BuildConfig.DEBUG) {
            startApp();
        } else {
            addDisposable(commonRequest.checkVersion(BuildConfig.VERSION_NAME)
                .compose(Transformer.applySchedulers())
                .filter(data -> {
                    String code = data.getCode();
                    if (!code.equals(HttpCode.OK)) {
                        view.showErrorDialog();
                    }
                    return code.equals(HttpCode.OK);
                })
                .map(data -> gson.fromJson(data.getData(), VersionDataModel.class))
                .subscribe(this::resCheckAppVersion, Timber::e));
        }
    }

    private void resCheckAppVersion(VersionDataModel data) {
        if (data.getStatus() == VersionStatus.FORCE.getValue()) {
            view.showUpdateDialog();
        } else {
            startApp();
        }
    }

    private void startApp() {
        view.checkDynamicLink();
        reqSortCode();
    }

    private void reqSortCode() {
        addDisposable(commonRequest.getSortCode()
            .compose(Transformer.applySchedulers())
            .map((Function<BaseDataModel, List<CodeDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<CodeDataModel>>() {
                }.getType()))
            .subscribe(this::resSortCode, Timber::e));
    }

    private void resSortCode(List<CodeDataModel> data) {
        App.getInstance().setSortCodes(data);
        isCommonDataGet = true;
        checkToDo();
    }

    private void checkToDo() {
        if (isAnimationEnd && isCommonDataGet) {
            checkGuide();
        }
    }

    private void checkGuide() {
        int visibleCount = Prefs.getInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, 0);
        if (visibleCount >= 3) {
            view.navigateToMain();
        } else {
            Prefs.putInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, visibleCount + 1);
            view.navigateToGuide();
        }
    }
}