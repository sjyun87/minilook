package com.minilook.minilook.data.network.inicis;

import com.google.gson.JsonObject;
import com.minilook.minilook.data.network.base.BaseRequest;

public class InicisRequest extends BaseRequest<InicisService> {

    @Override protected Class<InicisService> getService() {
        return InicisService.class;
    }

    @Override protected String getBaseUrl() {
        return EndPoint.INICIS_URL.getValue();
    }

    public void callPaymentPage() {
        getApi().callPaymentPage(createRequestBody(parseToJson()));
    }

    private JsonObject parseToJson() {
        JsonObject json = new JsonObject();
        json.addProperty("P_INI_PAYME NT", "CARD");
        json.addProperty("P_MID", "INIpayTest");
        json.addProperty("P_OID", "minilook1234");
        json.addProperty("P_AMT", "10000");
        json.addProperty("P_UNAME", "미니룩");
        json.addProperty("P_GOODS", "결제 테스트");
        return json;
    }
}
