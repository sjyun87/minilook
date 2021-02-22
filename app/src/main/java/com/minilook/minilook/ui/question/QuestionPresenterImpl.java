package com.minilook.minilook.ui.question;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.question.QuestionDataModel;
import com.minilook.minilook.data.model.question.QuestionHistoryDataModel;
import com.minilook.minilook.data.network.question.QuestionRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.question.di.QuestionArguments;
import com.minilook.minilook.ui.question.viewholder.QuestionItemVH;
import com.minilook.minilook.ui.question_edit.QuestionEditPresenterImpl;
import com.minilook.minilook.ui.question_history.viewholder.QuestionHistoryItemVH;
import com.minilook.minilook.ui.question_write.QuestionWritePresenterImpl;
import timber.log.Timber;

public class QuestionPresenterImpl extends BasePresenterImpl implements QuestionPresenter {

    private static final int ROWS = 30;

    private final View view;
    private final int productNo;
    private final BaseAdapterDataModel<QuestionDataModel> adapter;
    private final QuestionRequest questionRequest;
    private final Gson gson;

    private int lastQuestionNo = 0;

    public QuestionPresenterImpl(QuestionArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        adapter = args.getAdapter();
        questionRequest = new QuestionRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupRecyclerView();

        reqQuestions();
    }

    @Override public void onWriteClick() {
        view.navigateToQuestionWrite(productNo);
    }

    @Override public void onLoadMore() {
        reqLoadMoreQuestions();
    }

    @Override public void onEmptyClick() {
        if (App.getInstance().isLogin()) {
            view.navigateToQuestionWrite(productNo);
        } else {
            view.navigateToLogin();
        }
    }

    @Override public void onQuestionDelete(int productNo, int questionNo) {
        deleteQuestion(productNo, questionNo);
    }

    private void deleteQuestion(int productNo, int questionNo) {
        addDisposable(questionRequest.deleteQuestion(productNo, questionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::onResDeleteQuestion, Timber::e)
        );
    }

    private void onResDeleteQuestion(BaseDataModel model) {
        RxBus.send(new QuestionWritePresenterImpl.RxEventQuestionWrite());
    }

    private void reqQuestions() {
        addDisposable(questionRequest.getQuestions(productNo, ROWS, lastQuestionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.showEmptyPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), QuestionHistoryDataModel.class))
            .subscribe(this::resQuestions, Timber::e));
    }

    private void resQuestions(QuestionHistoryDataModel data) {
        view.hideEmptyPanel();
        view.setTotalCount(data.getQuestionCount());

        adapter.set(data.getQuestions());
        view.refresh();
        lastQuestionNo = adapter.get(adapter.getSize() - 1).getQuestionNo();
        view.scrollToTop();
    }

    private void reqLoadMoreQuestions() {
        addDisposable(questionRequest.getQuestions(productNo, ROWS, lastQuestionNo)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(data -> gson.fromJson(data.getData(), QuestionHistoryDataModel.class))
            .subscribe(this::resLoadMoreQuestions, Timber::e));
    }

    private void resLoadMoreQuestions(QuestionHistoryDataModel data) {
        int start = adapter.getSize();
        adapter.addAll(data.getQuestions());
        view.refresh(start, data.getQuestions().size());
        lastQuestionNo = adapter.get(adapter.getSize() - 1).getQuestionNo();
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof QuestionWritePresenterImpl.RxEventQuestionWrite
                || o instanceof QuestionEditPresenterImpl.RxEventQuestionEdit) {
                lastQuestionNo = 0;
                reqQuestions();
            } else if (o instanceof QuestionItemVH.RxEventQuestionDeleteClick) {
                int productNo = ((QuestionHistoryItemVH.RxEventQuestionDeleteClick) o).getProductNo();
                int questionNo = ((QuestionHistoryItemVH.RxEventQuestionDeleteClick) o).getQuestionNo();

                view.showQuestionDeleteDialog(productNo, questionNo);
            }
        }, Timber::e));
    }
}