package com.minilook.minilook.data.network.scrap;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

public class ScrapRequest extends BaseRequest<ScrapService> {

    @Override protected Class<ScrapService> getService() {
        return ScrapService.class;
    }

    public Single<BaseDataModel> getScrapProducts(int page, int rows) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getScrapProducts(memberNo, createScrapbookData(page, rows));
    }

    public Single<BaseDataModel> getScrapBrands(int page, int rows) {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getScrapBrands(memberNo, createScrapbookData(page, rows));
    }

    private RequestBody createScrapbookData(int page, int rows) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("current", page);
        jsonMap.put("pageSize", rows);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updateProductScrap(boolean isScrap, int productNo) {
        if (isScrap) {
            return getApi().productScrapOn(createProductScrapData(productNo));
        } else {
            return getApi().productScrapOff(createProductScrapData(productNo));
        }
    }

    private RequestBody createProductScrapData(int productNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("productNo", productNo);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> updateBrandScrap(boolean isScrap, int brandNo) {
        if (isScrap) {
            return getApi().brandScrapOn(parseToBrandScrapJson(brandNo));
        } else {
            return getApi().brandScrapOff(parseToBrandScrapJson(brandNo));
        }
    }

    private RequestBody parseToBrandScrapJson(int brandNo) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("brandNo", brandNo);
        return createRequestBody(jsonMap);
    }
}
