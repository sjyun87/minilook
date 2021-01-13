package com.minilook.minilook.ui.challenge_detail;

import android.os.Handler;
import android.os.Looper;
import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.data.network.challenge.ChallengeRequest;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.challenge_detail.di.ChallengeDetailArguments;
import com.minilook.minilook.util.DynamicLinkUtil;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import timber.log.Timber;

public class ChallengeDetailPresenterImpl extends BasePresenterImpl implements ChallengeDetailPresenter {

    private final View view;
    private final int challengeNo;
    private final BaseAdapterDataModel<String> imageAdapter;
    private final ChallengeRequest challengeRequest;
    private final DynamicLinkUtil dynamicLinkUtil;
    private final Gson gson;

    private ChallengeDataModel data;
    private Timer timer;
    private TimerTask timerTask;

    public ChallengeDetailPresenterImpl(ChallengeDetailArguments args) {
        view = args.getView();
        challengeNo = args.getChallengeNo();
        imageAdapter = args.getImageAdapter();
        challengeRequest = new ChallengeRequest();
        dynamicLinkUtil = new DynamicLinkUtil();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        view.setupClickAction();
        view.setupViewPager();

        getChallengeDetail();
    }

    @Override public void onResume() {
        if (data != null) startTimer();
    }

    @Override public void onPause() {
        stopTimer();
    }

    @Override public void onDestroy() {
        view.clear();
    }

    @Override public void onLogin() {
        getChallengeDetail();
    }

    @Override public void onLogout() {
        getChallengeDetail();
    }

    @Override public void onEnterClick() {
        if (App.getInstance().isLogin()) {
            view.navigateToChallengeEnter();
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onShareClick() {
        dynamicLinkUtil.createLink(DynamicLinkUtil.TYPE_CHALLENGE, challengeNo, data.getProductName(),
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

        handleEnterButton();
        startTimer();
    }

    private void handleEnterButton() {
        if (isEnd(data.getEndDate())) {
            view.showEndButton();
        } else {
            if (App.getInstance().isLogin() && data.isEnter()) {
                view.showEnterCompletedButton();
            } else {
                view.showEnterButton();
            }
        }
    }

    private boolean isEnd(long endTime) {
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endTime);
        endCal.add(Calendar.DATE, 1);
        Calendar currentCal = Calendar.getInstance();
        return currentCal.after(endCal);
    }

    private void handleRemainTime(int day, int hour, int minute) {
        if (day > 0) {
            view.showRemainTime(day, hour, minute);
            view.showLabel();
        } else if (day == 0) {
            if (hour > 0) {
                view.showRemainTime(hour, minute);
            } else {
                view.showRemainTime(minute);
            }
            view.showLabel();
        } else {
            view.showEndTime();
            view.hideLabel();
        }
        handleEnterButton();
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override public void run() {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                long remainTime = data.getEndDate() - currentTime;

                Calendar remainCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault());
                remainCal.setTimeInMillis(remainTime);

                int day = (int) remainTime / (24 * 60 * 60 * 1000);
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
}