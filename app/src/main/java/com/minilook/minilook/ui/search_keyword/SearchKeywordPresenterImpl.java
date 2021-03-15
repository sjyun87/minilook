package com.minilook.minilook.ui.search_keyword;

import com.minilook.minilook.data.network.search.SearchRequest;
import com.minilook.minilook.data.room.keyword.KeywordDB;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.search_keyword.di.SearchKeywordArguments;
import java.util.List;

public class SearchKeywordPresenterImpl extends BasePresenterImpl implements SearchKeywordPresenter {

    private final View view;
    private final SearchRequest searchRequest;
    private KeywordDB keywordDB;

    public SearchKeywordPresenterImpl(SearchKeywordArguments args) {
        view = args.getView();
        searchRequest = new SearchRequest();
        keywordDB =  KeywordDB.
    }

    @Override public void onCreate() {
        view.setupEditText();
        getRecentKeyword();
        //reqSearchModules();
    }

    @Override
    public void onSearchEnterClick(String keyword) {
        saveKeyword(keyword);
        view.navigateToBridge(keyword);
    }

    @Override public void onKeywordClick(String keyword) {
        saveKeyword(keyword);
    }

    @Override public void onDeleteClick(String keyword) {
        //deleteKeyword(keyword);
    }

    @Override public void removeAllClick() {
        //deleteAllKeyword();
        view.removeAllKeywordView();
        view.hideRecentPanel();
    }

    private void getRecentKeyword() {
        List<String> keywordList = loadKeyword();
        if (keywordList.size() == 0) {
            view.hideRecentPanel();
        } else {
            view.showRecentPanel();
            view.removeAllKeywordView();
            for (String keyword : keywordList) {
                view.addKeywordView(keyword);
            }
        }
    }

    private void saveKeyword(String keyword) {
        //if (recentKeywordDB.getDAO().hasKeyword(keyword) > 0) {
        //    deleteKeyword(keyword);
        //}
        //recentKeywordDB.getDAO().insertKeyword(keyword);
        //if (loadKeyword().size() > 10) {
        //    recentKeywordDB.getDAO().deleteOldKeyword();
        //    view.removeOldKeywordView();
        //}
        //reqRecentKeyword();
    }

    private List<String> loadKeyword() {
        return recentKeywordDB.getDAO().getRecentKeywordList();
    }

    private void deleteKeyword(String keyword) {
        recentKeywordDB.getDAO().deleteKeyword(keyword);
    }

    private void deleteAllKeyword() {
        recentKeywordDB.getDAO().deleteAllKeyword();
    }

}