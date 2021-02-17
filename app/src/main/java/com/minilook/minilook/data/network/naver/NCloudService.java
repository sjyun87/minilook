package com.minilook.minilook.data.network.naver;

import io.reactivex.rxjava3.core.Single;
import java.util.Map;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NCloudService {

    @PUT("/minilook/{type}/{memberNo}/{objectName}") Single<ResponseBody> putObject(
        @HeaderMap Map<String, String> headers,
        @Path("type") String type,
        @Path("memberNo") int memberNo,
        @Path("objectName") String objectName,
        @Body RequestBody body
    );
}
