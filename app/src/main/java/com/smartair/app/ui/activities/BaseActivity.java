package com.smartair.app.ui.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        findView();
        onViewCreated();
        setListeners();
    }

    protected abstract @LayoutRes int getLayoutId();
    protected void findView() {}
    protected void onViewCreated() {}
    protected void setListeners() {}
    protected abstract @IdRes int getFragmentContainerId();

    protected void customizeSwitchFragments(FragmentTransaction fragmentTransaction) {
    }

    protected void updateFragment(@NotNull Fragment fragment, boolean saveInBackStack, boolean add) {

        final String fragmentTag = ((Object) fragment).getClass().getName();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        customizeSwitchFragments(ft);

        if (add) {
            ft.add(getFragmentContainerId(), fragment, fragmentTag);
        } else {
            ft.replace(getFragmentContainerId(), fragment, fragmentTag);
        }

        if (saveInBackStack) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();
    }

    public void switchFragment(@NotNull Fragment fragment, boolean saveInBackStack) {
        updateFragment(fragment, saveInBackStack, false);
    }

    public void addFragment(@NotNull Fragment fragment, boolean saveInBackStack) {
        updateFragment(fragment, saveInBackStack, true);
    }
}
