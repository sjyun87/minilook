package com.minilook.minilook.ui.main;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.pixplicity.easyprefs.library.Prefs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private final View view;
    private final MemberRequest memberRequest;

    private boolean isLoginVisible = false;
    private boolean isMainVisible = true;

    public MainPresenterImpl(MainArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();
        view.setupBottomBar();

        reqUpdateToken();
        checkLogin();
    }

    @Override public void onResume() {
        if (!isMainVisible) {
            isMainVisible = true;
            isLoginVisible = false;
        }

        if (!isLoginVisible) {
            checkMarketingNotify();
        }
    }

    @Override public void onPause() {
        isMainVisible = false;
    }

    private void reqUpdateToken() {
        addDisposable(memberRequest.updateToken()
            .compose(Transformer.applySchedulers())
            .subscribe());
    }

    private void checkLogin() {
        if (!App.getInstance().isLogin()) {
            int visibleCount = Prefs.getInt(PrefsKey.KEY_LOGIN_VISIBLE_COUNT, 0);
            if (visibleCount < 3) {
                view.navigateToLogin();
                Prefs.putInt(PrefsKey.KEY_LOGIN_VISIBLE_COUNT, ++visibleCount);
                isLoginVisible = true;
            }
        }
    }

    private void checkMarketingNotify() {
        if (!App.getInstance().isLogin()) {
            boolean isVisible = Prefs.getBoolean(PrefsKey.KEY_MAIN_MARKETING_VISIBLE, false);
            if (!isVisible) {
                view.showMarketingDialog();
                Prefs.putBoolean(PrefsKey.KEY_MAIN_MARKETING_VISIBLE, true);
            }
        }
    }

    @Override public void onTabChanged(int position) {
        if (position != 0) {
            RxBus.send(new LookBookPresenterImpl.RxEventNavigateToPreview(false));
            RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailScrollToTop());
        }
        view.setupCurrentPage(position);
    }

    @Override public void onMarketingAgree() {
        reqUpdateNonUserMarketingAgree();
    }

    private void reqUpdateNonUserMarketingAgree() {
        addDisposable(memberRequest.updateMarketing(true)
            .compose(Transformer.applySchedulers())
            .subscribe());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof LookBookPresenterImpl.RxEventLookBookPageChanged) {
                int position = ((LookBookPresenterImpl.RxEventLookBookPageChanged) o).getPosition();
                view.setupBottomBarTheme(position != 0);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToBrandDetail {
        private int brandId;
    }
}