package com.minilook.minilook.data.network.base;

import com.minilook.minilook.BuildConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRequest<T> {

    public T getApi() {
        return createRetrofit().create(getService());
    }

    protected abstract Class<T> getService();

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();
    }

    private String getBaseUrl() {
        return EndPoint.BASE_URL.getValue();
    }

    @AllArgsConstructor @Getter protected enum EndPoint {
        BASE_URL(BuildConfig.DEBUG ?
            "http://lookbook.minilook.co.kr:8080/" :
            "http://lookbook.minilook.co.kr/");

        private String value;
    }
}
