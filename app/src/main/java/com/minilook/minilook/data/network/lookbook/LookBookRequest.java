package com.minilook.minilook.data.network.lookbook;

import com.google.gson.Gson;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

public class LookBookRequest extends BaseRequest<LookBookService> {

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<BaseDataModel> getLookbookModules(int row, List<Integer> usedItems) {
        return getApi().getLookBookModule(row, createRequestBody(new Gson().toJson(usedItems)));
    }
}
