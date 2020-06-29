package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;

import io.reactivex.rxjava3.core.Single;

public class LookBookRequest extends BaseRequest<LookBookService> {

    private static final int ROWS = 10;

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<LookBookDataModel> getLookbookModules(int page) {
        return getApi().getLookBookModule("Lookbook", page * ROWS, ROWS);
    }
}
