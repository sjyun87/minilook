package com.minilook.minilook.data.network.lookbook;

import com.google.gson.JsonObject;
import com.minilook.minilook.data.model.lookbook.LookBookDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.List;

public class LookBookRequest extends BaseRequest<LookBookService> {

    private static final int ROWS = 10;

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<List<LookBookDataModel>> getLookbookModules(int page) {
        return getApi().getLookBookModule(createRequestBody(parseToJson(page)));
    }

    private JsonObject parseToJson(int page) {
        JsonObject json = new JsonObject();
        json.addProperty("start", page);
        json.addProperty("rows", page * ROWS);
        return json;
    }
}
