package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LookBookRequest extends BaseRequest<LookBookService> {

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<BaseDataModel> getLookbookModules(int row, List<Integer> usedItems) {
        return getApi().getLookBookModule(createRequestBody(parseToJson(row, usedItems)));
    }

    private Map<String, Object> parseToJson(int row, List<Integer> usedItems) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberId());
        jsonMap.put("pageSize", row);
        jsonMap.put("usedLookbooks", usedItems);
        return jsonMap;
    }
}
