package com.minilook.minilook.ui.question_write;

import com.google.gson.Gson;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.common.KeyDataModel;
import com.minilook.minilook.data.model.gallery.PhotoDataModel;
import com.minilook.minilook.data.model.question.QuestionWriteDataModel;
import com.minilook.minilook.data.network.common.CommonRequest;
import com.minilook.minilook.data.network.naver.NCloudRequest;
import com.minilook.minilook.data.network.question.QuestionRequest;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.gallery.GalleryPresenterImpl;
import com.minilook.minilook.ui.question_write.di.QuestionWriteArguments;
import com.minilook.minilook.ui.review_write.viewholder.PhotoContentItemVH;
import com.minilook.minilook.ui.review_write.viewholder.PhotoFooterItemVH;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class QuestionWritePresenterImpl extends BasePresenterImpl implements QuestionWritePresenter {

    private final View view;
    private final int productNo;
    private final BaseAdapterDataModel<PhotoDataModel> photoAdapter;
    private final QuestionRequest questionRequest;
    private final CommonRequest commonRequest;
    private final NCloudRequest nCloudRequest;
    private final Gson gson;

    private boolean isTypeBoxOpen = false;
    private String type;
    private String question;
    private List<PhotoDataModel> photos;
    private int uploadCount = 0;
    private boolean isSecret = false;

    public QuestionWritePresenterImpl(QuestionWriteArguments args) {
        view = args.getView();
        productNo = args.getProductNo();
        photoAdapter = args.getPhotoAdapter();
        questionRequest = new QuestionRequest();
        commonRequest = new CommonRequest();
        nCloudRequest = new NCloudRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupTypeRecyclerView();
        view.setupPhotoRecyclerView();
        view.setupQuestionEditText();
        view.setupGuideText();

        initPhotos();
    }

    private void initPhotos() {
        photoAdapter.add(new PhotoDataModel());
        view.photoRefresh();
        view.setSelectedPhotoCount(0);
    }

    @Override public void onTextChanged(String text) {
        question = text;
        if (question.length() >= 10) {
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
        view.showLoadingView();
        if (photos != null && photos.size() > 0) {
            getUploadKeys();
        } else {
            writeQuestion();
        }
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

    private void writeQuestion() {
        addDisposable(questionRequest.writeQuestion(getWriteData())
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (!code.equals(HttpCode.OK)) {
                    view.hideLoadingView();
                    view.showErrorDialog();
                }
                return code.equals(HttpCode.OK);
            })
            .subscribe(this::onResWriteQuestion, Timber::e));
    }

    private QuestionWriteDataModel getWriteData() {
        QuestionWriteDataModel model = new QuestionWriteDataModel();
        model.setProductNo(productNo);
        model.setType(type);
        model.setQuestion(question);
        model.setPhotos(parseToPhotoData());
        model.setSecret(isSecret);
        return model;
    }

    private List<String> parseToPhotoData() {
        List<String> items = new ArrayList<>();
        if (photos != null && photos.size() > 0) {
            for (PhotoDataModel photo : photos) {
                items.add(photo.getName());
            }
        }
        return items;
    }

    private void onResWriteQuestion(BaseDataModel data) {
        view.hideLoadingView();
        view.showQuestionWriteToast();
        RxBus.send(new RxEventQuestionWrite());
        view.finish();
    }

    private void getUploadKeys() {
        addDisposable(commonRequest.getKeys()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                return code.equals(HttpCode.OK);
            })
            .map(model -> gson.fromJson(model.getData(), KeyDataModel.class))
            .subscribe(this::onResUploadKeys, Timber::e)
        );
    }

    private void onResUploadKeys(KeyDataModel model) {
        for (PhotoDataModel photo : photos) {
            photo.setName(createObjectName());
            uploadImage(model, photo);
        }
    }

    private String createObjectName() {
        StringBuilder sb = new StringBuilder();
        sb.append("P");
        sb.append(productNo);
        sb.append("_");
        sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault()).format(new Date()));
        sb.append(".png");
        return sb.toString();
    }

    private void uploadImage(KeyDataModel keys, PhotoDataModel photo) {
        addDisposable(nCloudRequest.uploadImage(NCloudRequest.TYPE_QUESTION, keys, photo)
            .compose(Transformer.applySchedulers())
            .subscribe(this::onResPutImage, this::onUploadError));
    }

    private void onResPutImage(ResponseBody body) {
        uploadCount++;
        checkUploadCompleted();
    }

    private void onUploadError(Throwable e) {
        Timber.e(e);
        uploadCount = 0;
        view.hideLoadingView();
        view.showErrorDialog();
    }

    private void checkUploadCompleted() {
        if (photos.size() == uploadCount) {
            writeQuestion();
        }
    }

    private void setPhotos(List<PhotoDataModel> images) {
        photos = images;
        photoAdapter.set(photos);
        if (photos.size() < 4) photoAdapter.add(new PhotoDataModel());
        view.photoRefresh();

        view.setSelectedPhotoCount(photos.size());
    }

    private void removePhoto(PhotoDataModel data) {
        photos.remove(data);
        photoAdapter.remove(data);
        if (photos.size() == 3) photoAdapter.add(new PhotoDataModel());
        view.photoRefresh();

        view.setSelectedPhotoCount(photos.size());
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof GalleryPresenterImpl.RxEventGallerySelectedCompleted) {
                List<PhotoDataModel> images = ((GalleryPresenterImpl.RxEventGallerySelectedCompleted) o).getItems();
                setPhotos(images);
            } else if (o instanceof PhotoContentItemVH.RxEventReviewPhotoClick) {
                PhotoDataModel data = ((PhotoContentItemVH.RxEventReviewPhotoClick) o).getModel();
                removePhoto(data);
            } else if (o instanceof PhotoFooterItemVH.RxEventReviewPhotoFooterClick) {
                view.navigateToGallery(photos);
            }
        }, Timber::e));
    }

    @AllArgsConstructor @Getter public final static class RxEventQuestionWrite {
    }
}