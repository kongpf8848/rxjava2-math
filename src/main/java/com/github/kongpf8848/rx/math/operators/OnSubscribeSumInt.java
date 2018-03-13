package com.github.kongpf8848.rx.math.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public final class OnSubscribeSumInt implements ObservableOnSubscribe<Integer> {

    final Observable<Integer> source;

    final boolean zeroDefault;


    public OnSubscribeSumInt(Observable<Integer> source, boolean zeroDefault) {
        this.source = source;
        this.zeroDefault = zeroDefault;
    }


    @Override
    public void subscribe(ObservableEmitter<Integer> e) throws Exception {

        new SumIntSubscriber(e, zeroDefault).subscribeTo(source);

    }

    static final class SumIntSubscriber extends ScalarDeferredSubscriber<Integer, Integer> {

        int sum;

        public SumIntSubscriber(ObservableEmitter<? super Integer> actual, boolean zeroDefault) {
            super(actual);
            if (zeroDefault) {
                hasValue = true;
            }
        }

        @Override
        public void onNext(Integer t) {
            if (!hasValue) {
                hasValue = true;
            }
            sum += t.intValue();
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
