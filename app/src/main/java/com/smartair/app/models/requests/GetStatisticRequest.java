package com.smartair.app.models.requests;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import com.smartair.app.components.SmartAirRetrofit;
import com.smartair.app.models.responses.GetStatisticResponse;

public class GetStatisticRequest extends RetrofitSpiceRequest<GetStatisticResponse, SmartAirRetrofit> {

    private String deviceId;

    public GetStatisticRequest(String deviceId) {
        super(GetStatisticResponse.class, SmartAirRetrofit.class);
        this.deviceId = deviceId;
    }

    @Override
    public GetStatisticResponse loadDataFromNetwork() throws Exception {
        long time = System.currentTimeMillis();
        return getService().getStatistic(deviceId, time - 60*60*1000 * 3, time);
    }
}
