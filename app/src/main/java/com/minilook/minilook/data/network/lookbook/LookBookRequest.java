package com.minilook.minilook.data.network.lookbook;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;

public class LookBookRequest extends BaseRequest<LookBookService> {

    @Override protected Class<LookBookService> getService() {
        return LookBookService.class;
    }

    public Single<BaseDataModel> getLookbookModules(int rows, List<Integer> usedItems) {
        return getApi().getLookBookModule(createLookBookModulesData(rows, usedItems));
    }

    private RequestBody createLookBookModulesData(int rows, List<Integer> usedItems) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("pageSize", rows);
        jsonMap.put("usedLookbooks", usedItems);
        return createRequestBody(jsonMap);
    }
}
