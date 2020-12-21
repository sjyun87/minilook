package com.minilook.minilook.data.network.ipage;

import com.minilook.minilook.App;
import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.network.base.BaseRequest;
import io.reactivex.rxjava3.core.Single;

public class IpageRequest extends BaseRequest<IpageService> {

    @Override protected Class<IpageService> getService() {
        return IpageService.class;
    }

    public Single<BaseDataModel> getIpage() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getIpage(memberNo);
    }

    public Single<BaseDataModel> getPointHistory() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getPointHistory(memberNo);
    }

    public Single<BaseDataModel> getCoupons() {
        int memberNo = App.getInstance().getMemberNo();
        return getApi().getCoupons(memberNo);
    }
}
