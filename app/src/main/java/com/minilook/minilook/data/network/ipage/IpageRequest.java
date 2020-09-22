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
        int user_id = App.getInstance().getMemberNo();
        return getApi().getIpage(user_id);
    }

    public Single<BaseDataModel> getPointHistory() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getPointHistory(user_id);
    }

    public Single<BaseDataModel> getCoupons() {
        int user_id = App.getInstance().getMemberNo();
        return getApi().getCoupons(user_id);
    }
}
