package com.minilook.minilook.data.network.brand;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.RequestBody;

public class BrandRequest extends BaseRequest<BrandService> {

    @Override protected Class<BrandService> getService() {
        return BrandService.class;
    }

    public Single<BaseDataModel> getBrands(List<String> styles) {
        return getApi().getBrands(createGetBrandsData(styles));
    }

    private RequestBody createGetBrandsData(List<String> styles) {
        Map<String, Object> jsonMap = new HashMap<>();
        if (App.getInstance().isLogin()) jsonMap.put("memberNo", App.getInstance().getMemberNo());
        jsonMap.put("styleCode", styles);
        return createRequestBody(jsonMap);
    }

    public Single<BaseDataModel> getBrandDetail(int brandNo) {
        if (App.getInstance().isLogin()) {
            int memberNo = App.getInstance().getMemberNo();
            return getApi().getBrandDetail(brandNo, memberNo);
        } else {
            return getApi().getBrandDetail(brandNo);
        }
    }

    public Single<BaseDataModel> getBrandInfo(int brandNo) {
        return getApi().getBrandInfo(brandNo);
    }
}
