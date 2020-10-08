package com.minilook.minilook.ui.question_write;

import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.question.QuestionRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.question_write.di.QuestionWriteArguments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import timber.log.Timber;

public class QuestionWritePresenterImpl extends BasePresenterImpl implements QuestionWritePresenter {

    private final View view;
    private final int productNo;
    private final QuestionRequest questionRequest;

    private boolean isTypeBoxOpen = false;
    private String type;
    private String question;
    private boolean isSecret = false;

    public QuestionWritePresenterImpl(QuestionWriteArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        questionRequest = new QuestionRequest();
    }

    @Override public void onCreate() {
        view.setupRecyclerView();
        view.setupQuestionEditText();
    }

    @Override public void onTextChanged(String text) {
        question = text;
        if (question.length() >= 5) {
            view.enableApplyButton();
        } else {
            view.disableApplyButton();
        }
    }

    @Override public void onTypeBoxClick() {
        isTypeBoxOpen = !isTypeBoxOpen;
        if (isTypeBoxOpen) {
            view.showTypeBox();
        } else {
            view.hideTypeBox();
        }
    }

    @Override public void onApplyClick() {
        reqWriteQuestion();
    }

    @Override public void onTypeSelected(String data) {
        type = data;
        view.setSelectedType(type);

        isTypeBoxOpen = false;
        view.hideTypeBox();
    }

    @Override public void onSecretClick() {
        isSecret = !isSecret;
        if (isSecret) {
            view.checkSecretCheckBox();
        } else {
            view.uncheckSecretCheckBox();
        }
    }

    private void reqWriteQuestion() {
        addDisposable(questionRequest.writeQuestion(productNo, question, type, isSecret)
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::resWriteQuestion, Timber::e));
    }

    private void resWriteQuestion(BaseDataModel data) {
        view.showQuestionWriteToast();
        RxBus.send(new RxEventQuestionWrite());
        view.finish();
    }

    @AllArgsConstructor @Getter public final static class RxEventQuestionWrite {
    }
}