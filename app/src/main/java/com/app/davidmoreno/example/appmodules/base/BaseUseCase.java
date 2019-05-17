package com.app.davidmoreno.example.appmodules.base;

import com.app.davidmoreno.example.util.Params;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Abstract class for a UseCase/Interactor
 * Will execute its job in a background thread and will post the result in the UI thread.
 * Will return the result using a {@link Disposable}
 */
public abstract class BaseUseCase {

    private CompositeDisposable disposables;

    public BaseUseCase() {
        this.disposables = new CompositeDisposable();
    }

    protected abstract Observable getObservable(Params params);

    /**
     * Executes the current UseCase.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * with {@link #getObservable(Params)}.
     */
    @SuppressWarnings("unchecked")
    public void execute(DisposableObserver observer, Params params) {
        final Observable<?> observable = this.getObservable(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        addDisposable(observable.subscribeWith(observer));
    }

    /**
     * Executes the current UseCase with delay.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * with {@link #getObservable(Params)}.
     */
    @SuppressWarnings("unchecked")
    public void execute(DisposableObserver observer, Params params, int delayMilliseconds) {
        final Observable<?> observable = this.getObservable(params)
                .delay(delayMilliseconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        addDisposable(observable.subscribeWith(observer));
    }


    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
        if (disposable != null) {
            getCompositeDisposable().add(disposable);
        }
    }

    private CompositeDisposable getCompositeDisposable() {
        if (disposables == null || disposables.isDisposed()) {
            disposables = new CompositeDisposable();
        }
        return disposables;
    }
}
