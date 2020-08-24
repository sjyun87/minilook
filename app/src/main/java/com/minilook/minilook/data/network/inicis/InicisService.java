package com.minilook.minilook.data.network.inicis;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InicisService {

    @POST("/smart/payment/") Call<Void> callPaymentPage(
        @Body RequestBody body
    );
}
