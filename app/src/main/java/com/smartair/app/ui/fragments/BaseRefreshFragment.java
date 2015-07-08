package com.smartair.app.ui.fragments;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.InjectView;
import com.smartair.app.R;
import com.smartair.app.ui.widgets.ToolbarWidget;

public abstract class BaseRefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @InjectView(R.id.toolbar)
    ToolbarWidget toolbar;

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void setListeners() {
        swipeRefreshLayout.setColorSchemeResources(
                R.color.holo_orange_light,
                R.color.holo_blue_light,
                R.color.holo_green_light,
                R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onViewCreated() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_launcher);
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}