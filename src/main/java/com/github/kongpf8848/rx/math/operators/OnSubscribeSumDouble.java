package com.github.kongpf8848.rx.math.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public final class OnSubscribeSumDouble implements ObservableOnSubscribe<Double> {

    final Observable<Double> source;

    final boolean zeroDefault;


    public OnSubscribeSumDouble(Observable<Double> source, boolean zeroDefault) {
        this.source = source;
        this.zeroDefault = zeroDefault;
    }


    @Override
    public void subscribe(ObservableEmitter<Double> e) throws Exception {

        new SumDoubleSubscriber(e, zeroDefault).subscribeTo(source);

    }

    static final class SumDoubleSubscriber extends ScalarDeferredSubscriber<Double, Double> {

        double sum;

        public SumDoubleSubscriber(ObservableEmitter<? super Double> actual, boolean zeroDefault) {
            super(actual);
            if (zeroDefault) {
                hasValue = true;
            }
        }

        @Override
        public void onNext(Double t) {
            if (!hasValue) {
                hasValue = true;
            }
            sum += t.doubleValue();
        }


        @Override
        public void onComplete() {
            if (hasValue) {
                complete(sum);
            } else {
                actual.onError(new IllegalArgumentException());
            }
        }

    }
}
