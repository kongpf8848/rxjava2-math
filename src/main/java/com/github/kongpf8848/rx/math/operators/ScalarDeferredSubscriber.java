package com.github.kongpf8848.rx.math.operators;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;


public abstract class ScalarDeferredSubscriber<T, R> implements Observer<T> {

    final ObservableEmitter<? super R> actual;

    protected boolean hasValue;

    protected R value;

    protected Disposable disposable;

    final AtomicInteger state = new AtomicInteger();

    static final int NO_REQUEST_NO_VALUE = 0;
    static final int HAS_REQUEST_NO_VALUE = 1;
    static final int NO_REQUEST_HAS_VALUE = 2;
    static final int HAS_REQUEST_HAS_VALUE = 3;

    public ScalarDeferredSubscriber(ObservableEmitter<? super R> actual) {
            this.actual = actual;

    }


    @Override
    public final void onError(Throwable e) {
        value = null;
        actual.onError(e);
    }

    @Override
    public void onComplete() {
        if (hasValue) {
            complete(value);
        } else {
            actual.onComplete();
        }
    }


    protected final void complete(R v) {
        actual.onNext(v);
        actual.onComplete();
        if (disposable != null && !disposable.isDisposed()) {

        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    public final void subscribeTo(final Observable<T> source) {
        source.subscribe(this);
    }


}
