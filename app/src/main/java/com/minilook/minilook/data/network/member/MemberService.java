package com.minilook.minilook.data.network.member;

import com.minilook.minilook.data.model.base.BaseDataModel;
import com.minilook.minilook.data.model.product.ProductColorDataModel;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MemberService {

    @POST("/api/members/existing") Single<BaseDataModel> checkUser(
        @Body RequestBody body
    );
}
