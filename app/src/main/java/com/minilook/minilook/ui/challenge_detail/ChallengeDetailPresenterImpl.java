package com.minilook.minilook.ui.challenge_detail;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.code.ChallengeType;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import com.minilook.minilook.ui.challenge_enter.ChallengeEnterPresenterImpl;
import com.minilook.minilook.util.DynamicLinkUtil;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class ChallengeDetailPresenterImpl extends BasePresenterImpl implements ChallengeDetailPresenter {

    private final View view;
    private final int challengeNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final BaseAdapterDataModel<ProductDataModel> relationProductAdapter;
    private final ChallengeRequest challengeRequest;
    private final DynamicLinkUtil dynamicLinkUtil;
    private final Gson gson;

    private ChallengeDataModel data;
    private int status;
    private Timer timer;
    private TimerTask timerTask;

    public ChallengeDetailPresenterImpl(ChallengeDetailArguments args) {
        view = args.getView();
        challengeNo = args.getChallengeNo();
        imageAdapter = args.getImageAdapter();
        relationProductAdapter = args.getRelationProductAdapter();
        challengeRequest = new ChallengeRequest();
        dynamicLinkUtil = new DynamicLinkUtil();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupImageViewPager();
        view.setupRelationProductViewPager();

        getChallengeDetail();
    }

    @Override public void onResume() {
        if (data != null && status != ChallengeType.END.getValue()) {
            startTimer();
        }
    }

    @Override public void onPause() {
        stopTimer();
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onLogin() {
        view.scrollToTop();
        getChallengeDetail();
    }

    @Override public void onLogout() {
        view.scrollToTop();
        getChallengeDetail();
    }

    @Override public void onEnterClick() {
        if (App.getInstance().isLogin()) {
            view.navigateToChallengeEnter(challengeNo);
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onShareClick() {
        String title = data.getProductName()
            + " ("
            + parseToDate(data.getStartDate())
            + "~"
            + parseToDate(data.getEndDate())
            + ")";
        dynamicLinkUtil.createLink(DynamicLinkUtil.TYPE_CHALLENGE, challengeNo, title,
            data.getImages().get(0),
            new DynamicLinkUtil.OnDynamicLinkListener() {
                @Override public void onSuccess(String link) {
                    view.sendDynamicLink(link);
                }

                @Override public void onError() {
                    view.showErrorDialog();
                }
            });
    }

    private String parseToDate(long date) {
        Date endDate = new Date(date);
        SimpleDateFormat format = new SimpleDateFormat("MM/dd", Locale.KOREA);
        return format.format(endDate);
    }

    private void getChallengeDetail() {
        addDisposable(challengeRequest.getChallengeDetail(challengeNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), ChallengeDataModel.class))
            .subscribe(this::onResChallengeDetail, Timber::e));
    }

    private void onResChallengeDetail(ChallengeDataModel data) {
        this.data = data;
        this.status = getStatus(data.getStartDate(), data.getEndDate());

        imageAdapter.set(data.getImages());
        view.imageRefresh();

        view.setBrandName(data.getBrandName());
        view.setProductName(data.getProductName());

        view.setTermDate(data.getStartDateName(), data.getEndDateName());
        view.setWinDate(data.getWinDate());
        view.setReviewDate(data.getReviewDate());
        view.setWinnerCount(data.getWinnerCount());

        view.setDetailContent(data.getDetailUrl());
        view.setChallengeContent(data.getChallengeUrl());

        handleButton();
        if (status != ChallengeType.END.getValue()) {
            startTimer();
        } else {
            view.showEndTime();
            view.setLabel(status);
        }

        List<ProductDataModel> relationProducts = data.getRelationProducts();
        if (relationProducts != null && relationProducts.size() > 0) {
            relationProductAdapter.set(relationProducts);
            view.relationProductRefresh();
            view.showRelationProductPanel();
        }
    }

    private void handleButton() {
        switch (ChallengeType.toType(status)) {
            case OPEN:
                if (App.getInstance().isLogin() && data.isEnter()) {
                    view.showEnterCompletedButton();
                } else {
                    view.showEnterButton();
                }
                break;
            case COMING:
                view.showComingButton();
                break;
            case END:
                view.showEndButton();
                break;
        }
    }

    private int getStatus(long startTime, long endTime) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startTime);
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endTime);
        endCal.add(Calendar.DATE, 1);

        Calendar currentCal = Calendar.getInstance();

        if (currentCal.before(startCal)) {
            return ChallengeType.COMING.getValue();
        } else if (currentCal.after(endCal)) {
            return ChallengeType.END.getValue();
        } else {
            return ChallengeType.OPEN.getValue();
        }
    }

    private void handleRemainTime(int day, int hour, int minute) {
        if (day >= 0) {
            view.showRemainTime(day, hour, minute);
        } else {
            view.showEndTime();
            stopTimer();
        }
        view.setLabel(status);
        handleButton();
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override public void run() {
                Calendar targetCal = Calendar.getInstance();
                if (status == ChallengeType.COMING.getValue()) {
                    targetCal.setTimeInMillis(data.getStartDate());
                } else {
                    targetCal.setTimeInMillis(data.getEndDate());
                    targetCal.add(Calendar.DATE, 1);
                }

                long currentTime = Calendar.getInstance().getTimeInMillis();
                long remainTime = targetCal.getTimeInMillis() - currentTime;

                Calendar remainCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
                remainCal.setTimeInMillis(remainTime);

                int day = ((int) remainTime / (24 * 60 * 60 * 1000));
                int hour = remainCal.get(Calendar.HOUR_OF_DAY);
                int minute = (int) remainCal.get(Calendar.MINUTE);

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new TimerTask() {
                    @Override public void run() {
                        handleRemainTime(day, hour, minute);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timerTask.cancel();
            timerTask = null;
            timer.cancel();
            timer = null;
        }
    }



    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof ChallengeEnterPresenterImpl.RxEventEnterChallenge) {
                view.scrollToTop();
                getChallengeDetail();
            } else if (o instanceof RxEventChallengeEnterFinish) {
                view.finish();
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventChallengeEnterFinish {
    }
}