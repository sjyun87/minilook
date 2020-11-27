package com.minilook.minilook.ui.splash;

import android.net.Uri;
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
    private final Gson gson;

    private boolean isAnimationEnd = false;
    private boolean isCommonDataGet = false;
    private boolean isTokenUpdate = false;
    private boolean isDynamicLinkCheck = false;

    public SplashPresenterImpl(SplashArguments args) {
        view = args.getView();
        commonRequest = new CommonRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupLottieView();
        view.setupDynamicLink();
        view.setupPushToken();

        if (BuildConfig.DEBUG) {
            startApp();
        } else {
            checkVersion();
        }
    }

    @Override public void onAnimationEnd() {
        isAnimationEnd = true;
        checkToDo();
    }

    @Override public void onDynamicLink(Task<PendingDynamicLinkData> task) {
        if (task.getResult() != null) {
            Uri link = task.getResult().getLink();
            if (link != null) {
                String type = link.getQueryParameter("type");
                String id = link.getQueryParameter("id");
                App.getInstance().setDynamicLink(type, id);
            }
        }
        isDynamicLinkCheck = true;
        checkToDo();
    }

    @Override public void onPushToken(Task<String> task) {
        if (task.isSuccessful()) {
            String token = task.getResult();
            App.getInstance().setPushToken(token);
            updateToken(token);
        } else {
            Timber.e(task.getException());
            view.showErrorDialog();
        }
    }

    @Override public void onUpdateDialogOkClick() {
        view.navigateToPlayStore();
        view.finish();
    }

    private void checkVersion() {
        addDisposable(commonRequest.checkVersion()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), VersionDataModel.class))
            .subscribe(this::onResCheckVersion, Timber::e));
    }

    private void onResCheckVersion(VersionDataModel data) {
        if (data.getStatus() == VersionStatus.FORCE.getValue()) {
            view.showUpdateDialog();
        } else {
            startApp();
        }
    }

    private void startApp() {
        getCommonData();
    }

    private void updateToken(String token) {
        addDisposable(commonRequest.updateToken(token)
            .subscribe(this::onResUpdateToken));
    }

    private void onResUpdateToken(BaseDataModel data) {
        isTokenUpdate = true;
        checkToDo();
    }

    private void getCommonData() {
        addDisposable(commonRequest.getSortCode()
            .compose(Transformer.applySchedulers())
            .map((Function<BaseDataModel, List<CodeDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<CodeDataModel>>() {
                }.getType()))
            .subscribe(this::onResCommonData, Timber::e));
    }

    private void onResCommonData(List<CodeDataModel> data) {
        App.getInstance().setSortCodes(data);
        isCommonDataGet = true;
        checkToDo();
    }

    private void checkToDo() {
        if (isAnimationEnd && isCommonDataGet && isTokenUpdate && isDynamicLinkCheck) {
            navigateToPage();
        }
    }

    private void navigateToPage() {
        int visibleCount = Prefs.getInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, 0);
        if (visibleCount < 3) {
            Prefs.putInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, visibleCount + 1);
            view.navigateToGuide();
        } else {
            view.navigateToMain();
        }
        view.finish();
    }
}