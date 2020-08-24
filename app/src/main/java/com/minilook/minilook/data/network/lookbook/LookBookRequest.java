package com.minilook.minilook.data.network.lookbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

public class LookBookRequest extends BaseRequest<LookBookService> {

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<BaseDataModel> getLookbookModules(int row, List<Integer> usedItems) {
        return getApi().getLookBookModule(createRequestBody(parseToJson(row, usedItems)));
    }

    private JsonObject parseToJson(int row, List<Integer> usedItems) {
        JsonObject json = new JsonObject();
        json.addProperty("pageSize", row);
        json.addProperty("usedLookbooks", new Gson().toJson(usedItems));
        return json;
    }
}
