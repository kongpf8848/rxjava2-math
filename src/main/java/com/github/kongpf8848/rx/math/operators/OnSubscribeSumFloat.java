package com.github.kongpf8848.rx.math.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public final class OnSubscribeSumFloat implements ObservableOnSubscribe<Float> {

    final Observable<Float> source;

    final boolean zeroDefault;


    public OnSubscribeSumFloat(Observable<Float> source, boolean zeroDefault) {
        this.source = source;
        this.zeroDefault = zeroDefault;
    }


    @Override
    public void subscribe(ObservableEmitter<Float> e) throws Exception {

        new SumFloatSubscriber(e, zeroDefault).subscribeTo(source);

    }

    static final class SumFloatSubscriber extends ScalarDeferredSubscriber<Float, Float> {

        float sum;

        public SumFloatSubscriber(ObservableEmitter<? super Float> actual, boolean zeroDefault) {
            super(actual);
            if (zeroDefault) {
                hasValue = true;
            }
        }

        @Override
        public void onNext(Float t) {
            if (!hasValue) {
                hasValue = true;
            }
            sum += t.floatValue();
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
