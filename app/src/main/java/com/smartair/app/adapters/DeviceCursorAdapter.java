package com.smartair.app.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.smartair.app.R;
import com.smartair.app.models.entities.Device;
import com.smartair.app.ui.holders.DeviceItemHolder;

public class DeviceCursorAdapter extends CursorAdapter {
    DeviceItemHolder.ViewCreator creator;
    Context context;

    public DeviceCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        creator = DeviceItemHolder.initCreator(context);
        this.context = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return creator.create(parent);
    }

    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        DeviceItemHolder holder = DeviceItemHolder.retrieve(convertView);

        Device device = Device.fromCursor(cursor);
        holder.getTvDeviceAlias().setText(device.getDeviceName());

        holder.getTvCO2().setText(formatString(R.string.current_co2, device.getCurrentCO2()));
        int deltaCO2 = device.getDeltaCO2();
        holder.getTvCO2Changed().setText(formatString(R.string.change_co2, deltaCO2));
        holder.getTvCO2Changed().setTextColor(getColor(deltaCO2, R.color.increase_co2, R.color.decrease_co2));

        holder.getTvHumidity().setText(formatString(R.string.current_humidity, device.getCurrentHumidity()));
        float deltaHumidity = device.getDeltaHumidity();
        holder.getTvHumidityChanged().setText(formatString(R.string.change_humidity, deltaHumidity));
        holder.getTvHumidityChanged().setTextColor(getColor(deltaHumidity, R.color.increase_humidity, R.color.decrease_humidity));

        holder.getTvTemperature().setText(formatString(R.string.current_temperature, device.getCurrentTemperature()));
        float deltaTemp = device.getDeltaTemperature();
        holder.getTvTemperatureChanged().setTextColor(getColor(deltaTemp, R.color.increase_temperature, R.color.decrease_temperature));
        holder.getTvTemperatureChanged().setText(formatString(R.string.change_temperature, deltaTemp));

    }

    private String formatString(int id, Object... format){
        return context.getString(id, format);
    }

    private int getColor(float values, int inc, int dec) {
        return  context.getResources().getColor(values == 0.0f ? R.color.no_change : (values < 0 ? dec : inc));
    }
}
