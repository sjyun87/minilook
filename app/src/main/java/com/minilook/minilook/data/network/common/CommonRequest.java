package com.minilook.minilook.data.network.common;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class CommonRequest extends BaseRequest<CommonService> {

    @Override protected Class<CommonService> getService() {
        return CommonService.class;
    }

    public Single<BaseDataModel> getSortCode() {
        return getApi().getSortCode();
    }
}
