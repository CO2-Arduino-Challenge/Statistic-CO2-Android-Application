package com.smartair.app.ui.fragments;

import android.annotation.SuppressLint;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.InjectView;
import com.smartair.app.R;
import com.smartair.app.components.provider.SmartAirProvider;
import com.smartair.app.constants.LoaderConstants;
import com.smartair.app.models.entities.Indication;

public class TemperatureStatisticFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    protected static final String DEVICE_ID_ARG = "deviceId";
    protected String deviceId;

    @InjectView(R.id.chart)
    LineChart chart;

    private ContentObserver contentObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            Log.d("TEST_TAG", "contentObserver onChange" + this);
            forceStart();
        }
    };

    public static TemperatureStatisticFragment getInstance(String deviceId) {
        Bundle args = new Bundle();
        args.putString(DEVICE_ID_ARG, deviceId);
        TemperatureStatisticFragment fragment = new TemperatureStatisticFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deviceId = getArguments().getString(DEVICE_ID_ARG);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chart_statistic;
    }

    @Override
    protected void onViewCreated() {
        Log.d("TEST_TAG", "onViewCreated" + this);
        initView();
        initializeLoader();
        getActivity().getContentResolver().registerContentObserver(SmartAirProvider.INDICATION_CONTENT_URI, true, contentObserver);
    }


    @Override
    public void onDestroyView() {
        Log.d("TEST_TAG", "onDestroyView" + this);
        getActivity().getContentResolver().unregisterContentObserver(contentObserver);
        getActivity().getSupportLoaderManager().destroyLoader(getLoaderId());
        super.onDestroyView();
    }

    @Override
    protected void setListeners() {
    }

    private void initializeLoader() {
        Loader loader = getActivity().getSupportLoaderManager().getLoader(getLoaderId());
        if (loader == null)
            getActivity().getSupportLoaderManager().initLoader(getLoaderId(), null, this);
        else
            restartCursorLoader();
    }

    protected void restartCursorLoader() {
        getActivity().getSupportLoaderManager().restartLoader(getLoaderId(), null, this);
    }

    protected void forceStart() {
        getActivity().getSupportLoaderManager().getLoader(getLoaderId()).forceLoad();
    }

    protected void initView() {

        // no description text
        chart.setDescription("");

        // enable value highlighting
        chart.setHighlightEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawGridBackground(false);

        XAxis x = chart.getXAxis();
        x.setEnabled(true);

        YAxis y = chart.getAxisLeft();
        y.setLabelCount(5);
        y.setEnabled(true);

        chart.getAxisRight().setEnabled(false);
    }

    protected LineDataSet createLineDataSet(ArrayList<Entry> entries, String description) {
        // create a dataSet and give it a type
        LineDataSet dataSet = new LineDataSet(entries, description);
        dataSet.setDrawCubic(true);
        dataSet.setCubicIntensity(0.2f);

        //dataSet.setDrawFilled(true);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(2f);
        dataSet.setCircleSize(5f);
        dataSet.setHighLightColor(Color.rgb(244, 117, 117));
        dataSet.setColor(Color.rgb(104, 241, 175));
        dataSet.setFillColor(ColorTemplate.getHoloBlue());
        return dataSet;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d("TEST_TAG", "onCreateLoader " + this);
        return new CursorLoader(getActivity(),
                SmartAirProvider.INDICATION_CONTENT_URI,
                new String[] {Indication.Contract.DATE, indicationColumn()},
                Indication.Contract.DEVICE_ID + "=?", new String[] {deviceId},
                Indication.Contract.DATE);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("TEST_TAG", "onLoadFinished " + data.getCount() + " || " + this);
        SimpleDateFormat formatDate = new SimpleDateFormat(getString(R.string.chart_time_format));
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();
        while (data.moveToNext()) {
            dates.add(formatDate.format(new Date(data.getLong(0))));
            entries.add(toEntry(data, 1, data.getPosition()));
        }
        LineDataSet dataSet = createLineDataSet(entries, indicationColumn());
        LineData lineData = new LineData(dates, dataSet);
        lineData.setValueTextSize(9f);
        lineData.setDrawValues(false);

        chart.setData(lineData);
        chart.getLegend().setEnabled(false);
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    protected Entry toEntry(Cursor cursor, int column, int position) {
        return new Entry(cursor.getFloat(column), position);
    }

    protected String indicationColumn() {
        return Indication.Contract.TEMPERATURE;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    protected int getLoaderId() {
        return LoaderConstants.INDICATE_TEMPERATURE_LOADER;
    }
}
