package com.minilook.minilook.ui.main;

import com.minilook.minilook.App;
import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.member.MemberRequest;
import com.minilook.minilook.data.network.scrap.ScrapRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.RxBusEvent;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.lookbook.LookBookPresenterImpl;
import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailPresenterImpl;
import com.minilook.minilook.ui.main.di.MainArguments;
import com.minilook.minilook.util.DynamicLinkUtil;
import com.pixplicity.easyprefs.library.Prefs;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class MainPresenterImpl extends BasePresenterImpl implements MainPresenter {

    private final View view;
    private final MemberRequest memberRequest;
    private final ScrapRequest scrapRequest;

    private int step = 0;

    public MainPresenterImpl(MainArguments args) {
        view = args.getView();
        memberRequest = new MemberRequest();
        scrapRequest = new ScrapRequest();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupViewPager();
        view.setupBottomBar();

        checkAction(step);
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onMarketingAgree() {
        updateMarketingAgree(true);
    }

    @Override public void onMarketingDisagree() {
        updateMarketingAgree(false);
    }

    @Override public void onCoachMarkEnd() {
        Prefs.putBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, true);
        checkAction(++step);
    }

    @Override public void onBottomBarClick(int position) {
        if (position != 0) {
            RxBus.send(new LookBookPresenterImpl.RxEventScrollToPreview(false));
            RxBus.send(new LookBookDetailPresenterImpl.RxEventLookBookDetailScrollToTop());
        } else {
            checkCoachMark();
        }
        view.setCurrentPage(position);
    }

    private void checkAction(int step) {
        switch (step) {
            case 0:
                checkMarketingDialog();
                break;
            case 1:
                checkDeepLink();
                break;
        }
    }

    private void checkMarketingDialog() {
        if (!Prefs.getBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, false)) {
            view.showMarketingDialog();
            Prefs.putBoolean(PrefsKey.KEY_MARKETING_DIALOG_VISIBLE, true);
        } else {
            checkAction(++step);
        }
    }

    private void checkCoachMark() {
        if (!Prefs.getBoolean(PrefsKey.KEY_LOOKBOOK_COACH_VISIBLE, false)) {
            view.showLookBookCoachMark();
        }
    }

    private void checkDeepLink() {
        if (App.getInstance().isDeepLink()) {
            Map<String, String> deepLinkData = App.getInstance().getDeepLinkData();
            String type = deepLinkData.get("type");
            int itemNo = Integer.parseInt(deepLinkData.get("id"));

            switch (type) {
                case DynamicLinkUtil.TYPE_PROMOTION:
                    view.navigateToPromotionDetail(itemNo);
                    break;
                case DynamicLinkUtil.TYPE_EVENT:
                    view.navigateToEventDetail(itemNo);
                    break;
                case DynamicLinkUtil.TYPE_PRODUCT:
                    view.navigateToProductDetail(itemNo);
                    break;
                case DynamicLinkUtil.TYPE_BRAND:
                    view.navigateToBrandDetail(itemNo);
                    break;
                case DynamicLinkUtil.TYPE_PREORDER:
                    view.navigateToPreorderDetail(itemNo);
                    break;
            }
        }
    }

    private void updateMarketingAgree(boolean enable) {
        addDisposable(memberRequest.updateMarketingInfo(enable)
            .compose(Transformer.applySchedulers())
            .subscribe(model -> onResUpdateMarketingAgree(enable), Timber::e));
    }

    private void onResUpdateMarketingAgree(boolean enable) {
        view.updateMarketingAgreeToast(enable);
        checkAction(++step);
    }

    private void updateProductScrap(ProductDataModel data) {
        addDisposable(scrapRequest.updateProductScrap(data.isScrap(), data.getProductNo())
            .subscribe(model -> onResUpdateProductScrap(data), Timber::e));
    }

    private void onResUpdateProductScrap(ProductDataModel data) {
        RxBus.send(new RxBusEvent.RxBusEventProductScrap(data));
    }

    private void updateBrandScrap(BrandDataModel data) {
        addDisposable(scrapRequest.updateBrandScrap(data.isScrap(), data.getBrandNo())
            .subscribe(model -> onResUpdateBrandScrap(data), Timber::e));
    }

    private void onResUpdateBrandScrap(BrandDataModel data) {
        RxBus.send(new RxBusEvent.RxBusEventBrandScrap(data));
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RxEventChangeBottomBarTheme) {
                boolean flag = ((RxEventChangeBottomBarTheme) o).isFlag();
                view.setBottomBarTheme(flag);
            } else if (o instanceof RxEventNavigateToPage) {
                int position = ((RxEventNavigateToPage) o).getPosition();
                view.setCurrentPage(position);
            } else if (o instanceof RxBusEventUpdateProductScrap) {
                ProductDataModel data = ((RxBusEventUpdateProductScrap) o).getData();
                updateProductScrap(data);
            } else if (o instanceof RxBusEventUpdateBrandScrap) {
                BrandDataModel data = ((RxBusEventUpdateBrandScrap) o).getData();
                updateBrandScrap(data);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventChangeBottomBarTheme {
        private final boolean flag;
    }

    @AllArgsConstructor @Getter public final static class RxEventNavigateToPage {
        private final int position;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventUpdateProductScrap {
        private final ProductDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventUpdateBrandScrap {
        private final BrandDataModel data;
    }
}