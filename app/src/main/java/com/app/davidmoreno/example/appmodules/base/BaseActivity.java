package com.app.davidmoreno.example.appmodules.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.app.davidmoreno.example.dependencyinjection.modules.BaseActivityModule;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String LOADING_FRAGMENT_TAG =  "LOADING_FRAGMENT";
    public Handler mDelayHandler;
    public static final int DELAY = 3000;

    @Inject
    @Named(BaseActivityModule.ACTIVITY_FRAGMENT_MANAGER)
    FragmentManager fragmentManager;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        mDelayHandler = new Handler();
    }

    @Override
    public final AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    protected final void addFragment(@IdRes int containerViewId, Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(containerViewId, fragment)
                .commit();
    }

    protected final void showDialogFragment(DialogFragment dialogFragment, String tag) {
        dialogFragment.show(fragmentManager, tag);
    }

    protected final void showLoadingDialog() {
        Fragment prev = fragmentManager.findFragmentByTag(LOADING_FRAGMENT_TAG);
        LoadingFragmentDialog newFragment;

        if (prev == null || !(prev instanceof LoadingFragmentDialog)) {
            newFragment = LoadingFragmentDialog.newInstance();
        } else
            newFragment = (LoadingFragmentDialog) prev;

        if (!newFragment.isAdded()) {
            newFragment.setCancelable(false);
            newFragment.show(fragmentManager, LOADING_FRAGMENT_TAG);
        }
    }

    protected final void hideLoadingDialog() {
        Fragment prev = fragmentManager.findFragmentByTag(LOADING_FRAGMENT_TAG);

        if (prev != null && prev instanceof LoadingFragmentDialog) {
            ((LoadingFragmentDialog) prev).dismiss();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

}

