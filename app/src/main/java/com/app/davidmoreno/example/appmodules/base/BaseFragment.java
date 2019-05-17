package com.app.davidmoreno.example.appmodules.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDialogFragment;

import com.app.davidmoreno.example.dependencyinjection.modules.BaseFragmentModule;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class BaseFragment extends AppCompatDialogFragment
        implements HasSupportFragmentInjector {
    protected static final int LOADING_DEFAULT_POST_DELAY =  3000;
    private static final String LOADING_FRAGMENT_TAG =  "LOADING_FRAGMENT";
    protected Handler mLoadingHandler;
    @Inject
    protected Context activityContext;
    @Inject
    @Named(BaseFragmentModule.CHILD_FRAGMENT_MANAGER)
    protected FragmentManager childFragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadingHandler = new Handler();
    }

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        // This is called even for API levels below 23.
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        ButterKnife.bind(this, getView());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public final AndroidInjector<Fragment> supportFragmentInjector() {
        return childFragmentInjector;
    }

    protected final void addChildFragment(@IdRes int containerViewId, Fragment fragment) {
        childFragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    protected final void addChildFragment(@IdRes int containerViewId, Fragment fragment, String tag) {
        childFragmentManager.beginTransaction()
                .add(containerViewId, fragment, tag)
                .commit();
    }

    protected final void showLoadingDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(LOADING_FRAGMENT_TAG);
        LoadingFragmentDialog newFragment;

        if (prev == null || !(prev instanceof LoadingFragmentDialog)) {
            newFragment = LoadingFragmentDialog.newInstance();
        } else
            newFragment = (LoadingFragmentDialog) prev;

        if (!newFragment.isAdded()) {
            newFragment.setCancelable(false);
            newFragment.show(ft, LOADING_FRAGMENT_TAG);
        }
    }

    protected final void hideLoadingDialog() {
        Fragment prev = getFragmentManager().findFragmentByTag(LOADING_FRAGMENT_TAG);

        if (prev != null && prev instanceof LoadingFragmentDialog) {
            ((LoadingFragmentDialog) prev).dismiss();
        }
    }

}

