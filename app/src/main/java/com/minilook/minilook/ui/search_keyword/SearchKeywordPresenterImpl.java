package com.minilook.minilook.ui.search_keyword;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minilook.minilook.App;
import com.minilook.minilook.data.common.HttpCode;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.search.KeywordDataModel;
import com.minilook.minilook.data.model.search.SearchOptionDataModel;
import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.room.keyword.KeywordDB;
import com.minilook.minilook.data.rx.RxBus;
import com.minilook.minilook.data.rx.Transformer;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_keyword.di.SearchKeywordArguments;
import com.minilook.minilook.ui.search_keyword.viewholder.RecentKeywordVH;
import io.reactivex.rxjava3.functions.Function;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class SearchKeywordPresenterImpl extends BasePresenterImpl implements SearchKeywordPresenter {

    private final View view;
    private final BaseAdapterDataModel<String> recentKeywordAdapter;
    private final KeywordDB keywordDB;
    private final SearchRequest searchRequest;
    private final Gson gson;

    public SearchKeywordPresenterImpl(SearchKeywordArguments args) {
        view = args.getView();
        recentKeywordAdapter = args.getRecentKeywordAdapter();
        keywordDB = args.getKeywordDB();
        searchRequest = new SearchRequest();
        gson = App.getInstance().getGson();
    }

    @Override public void onCreate() {
        toRxObservable();
        view.setupClickAction();
        view.setupEditText();
        view.setupRecentKeywordRecyclerView();

        setRecentKeyword();
        getRecommendKeyword();
    }

    @Override
    public void onSearchEnterClick(String keyword) {
        if (!TextUtils.isEmpty(keyword)) {
            saveKeyword(keyword);
            view.navigateToBridge(makeOptions(keyword));
            view.finish();
        }
    }

    @Override public void onRecentClearClick() {
        clearKeywords();
        setRecentKeyword();
    }

    @Override public void onRecommendKeywordClick(String keyword) {
        saveKeyword(keyword);
        view.navigateToBridge(makeOptions(keyword));
        view.finish();
    }

    private void setRecentKeyword() {
        List<String> recentKeywords = loadKeyword();
        if (recentKeywords.size() == 0) {
            view.hideRecentPanel();
        } else {
            if (recentKeywords.size() > 10) {
                keywordDB.getDAO().deleteOldKeyword();
                recentKeywords.remove(recentKeywords.size() - 1);
            }
            recentKeywordAdapter.set(recentKeywords);
            view.recentKeywordRefresh();
            view.showRecentPanel();
        }
    }

    private void saveKeyword(String keyword) {
        if (keywordDB.getDAO().hasKeyword(keyword) > 0) {
            deleteKeyword(keyword);
        }
        keywordDB.getDAO().insertKeyword(keyword);

        setRecentKeyword();
    }

    private List<String> loadKeyword() {
        return keywordDB.getDAO().getRecentKeywords();
    }

    private void deleteKeyword(String keyword) {
        keywordDB.getDAO().deleteKeyword(keyword);
        setRecentKeyword();
    }

    private void clearKeywords() {
        keywordDB.getDAO().clearKeyword();
        setRecentKeyword();
    }

    private void getRecommendKeyword() {
        addDisposable(searchRequest.getRecommendKeywords()
            .compose(Transformer.applySchedulers())
            .filter(data -> {
                String code = data.getCode();
                if (code.equals(HttpCode.NO_DATA)) {
                    view.hideRecommendPanel();
                }
                return code.equals(HttpCode.OK);
            })
            .map((Function<BaseDataModel, List<KeywordDataModel>>)
                data -> gson.fromJson(data.getData(), new TypeToken<ArrayList<KeywordDataModel>>() {
                }.getType()))
            .subscribe(this::onResRecommendKeywords, Timber::e)
        );
    }

    private void onResRecommendKeywords(List<KeywordDataModel> data) {
        for (KeywordDataModel model : data) {
            view.addRecommendKeyword(model.getKeyword());
        }
        view.showRecommendPanel();
    }

    private SearchOptionDataModel makeOptions(String keyword) {
        SearchOptionDataModel data = new SearchOptionDataModel();
        data.setKeyword(keyword);
        data.setFilerSearch(false);
        return data;
    }

    private void toRxObservable() {
        addDisposable(RxBus.toObservable().subscribe(o -> {
            if (o instanceof RecentKeywordVH.RxEventRecentKeywordClick) {
                String keyword = ((RecentKeywordVH.RxEventRecentKeywordClick) o).getKeyword();
                saveKeyword(keyword);
                view.navigateToBridge(makeOptions(keyword));
                view.finish();
            } else if (o instanceof RecentKeywordVH.RxEventRecentKeywordDeleteClick) {
                String keyword = ((RecentKeywordVH.RxEventRecentKeywordDeleteClick) o).getKeyword();
                deleteKeyword(keyword);
            }
        }, Timber::e));
    }
}