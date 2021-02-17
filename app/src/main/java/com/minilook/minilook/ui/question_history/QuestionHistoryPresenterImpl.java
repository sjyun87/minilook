package com.minilook.minilook.ui.question_history;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.data.network.question.QuestionRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.question_history.di.QuestionHistoryArguments;
import com.minilook.minilook.ui.question_write.QuestionWritePresenterImpl;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class QuestionHistoryPresenterImpl extends BasePresenterImpl implements QuestionHistoryPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final BaseAdapterDataModel<QuestionDataModel> adapter;
    private final QuestionRequest questionRequest;
    private final Gson gson;

    private int lastQuestionNo;

    public QuestionHistoryPresenterImpl(QuestionHistoryArguments args) {
        view = args.getView();
        adapter = args.getAdapter();
        questionRequest = new QuestionRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupRecyclerView();

        getQuestionHistory();
    }

    @Override public void onLoadMore() {
        getMoreQuestionHistory();
    }

    private void getQuestionHistory() {
        addDisposable(questionRequest.getQuestionHistory(ROWS, lastQuestionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                } else if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<QuestionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<QuestionDataModel>>() {
                }.getType()))
            .subscribe(this::onResQuestionHistory, Timber::e)
        );
    }

    private void onResQuestionHistory(List<QuestionDataModel> data) {
        lastQuestionNo = data.get(data.size() - 1).getQuestionNo();
        adapter.set(data);
        view.refresh();
    }

    private void getMoreQuestionHistory() {
        addDisposable(questionRequest.getQuestionHistory(ROWS, lastQuestionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<QuestionDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<QuestionDataModel>>() {
                }.getType()))
            .subscribe(this::onResMoreQuestionHistory, Timber::e)
        );
    }

    private void onResMoreQuestionHistory(List<QuestionDataModel> data) {
        lastQuestionNo = data.get(data.size() - 1).getQuestionNo();
        int start = adapter.getSize();
        int row = data.size();
        adapter.addAll(data);
        view.refresh(start, row);
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof QuestionWritePresenterImpl.RxEventQuestionWrite) {
                lastQuestionNo = -1;
                getQuestionHistory();
            }
        }, Timber::e));
    }
}