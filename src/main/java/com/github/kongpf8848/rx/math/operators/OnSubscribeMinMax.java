package com.github.kongpf8848.rx.math.operators;

import java.util.Comparator;
import java.util.NoSuchElementException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by pengf on 2018/3/7.
 */

public final class OnSubscribeMinMax<T> implements ObservableOnSubscribe<T> {

    final Observable<T> source;

    final Comparator<? super T> comparator;

    final int compensator;

    @SuppressWarnings("rawtypes")
    public static final Comparator<Comparable> COMPARABLE_MIN = new Comparator<Comparable>() {
        @SuppressWarnings("unchecked")
        @Override
        public int compare(Comparable a, Comparable b) {
            return a.compareTo(b);
        }
    };

    public OnSubscribeMinMax(Observable<T> source, Comparator<? super T> comparator, int compensator) {
        this.source = source;
        this.comparator = comparator;
        this.compensator = compensator;
    }

    @Override
    public void subscribe(ObservableEmitter<T> e) throws Exception {
        new MinMaxSubscriber<T>(e, comparator, compensator).subscribeTo(source);
    }

    static final class MinMaxSubscriber<T> extends ScalarDeferredSubscriber<T, T> {

        final Comparator<? super T> comparator;

        final int compensator;

        public MinMaxSubscriber(ObservableEmitter<? super T> actual, Comparator<? super T> comparator, int compensator) {
            super(actual);
            this.comparator = comparator;
            this.compensator = compensator;
        }

        @Override
        public void onNext(T t) {
            T v = value;
            if (hasValue) {
                if (comparator.compare(v, t) * compensator > 0) {
                    value = t;
                }
            } else {
                value = t;
                hasValue = true;
            }
        }

        @Override
        public void onComplete() {
            if (hasValue) {
                complete(value);
            } else {
                actual.onError(new NoSuchElementException());
            }
        }
    }
}
