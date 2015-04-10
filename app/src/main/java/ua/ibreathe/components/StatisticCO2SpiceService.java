package ua.ibreathe.components;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import retrofit.RestAdapter;
import ua.ibreathe.constants.RestConstants;

public class StatisticCO2SpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(StatisticCO2Retrofit.class);
    }

    @Override
    protected String getServerUrl() {
        return RestConstants.BASE_URL;
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return RetrofitHelper.createStatisticCO2RestAdapter(super.createRestAdapterBuilder());
    }
}