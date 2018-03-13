package com.github.kongpf8848.rx.math.operators;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public final class OnSubscribeSumLong implements ObservableOnSubscribe<Long> {

    final Observable<Long> source;

    final boolean zeroDefault;


    public OnSubscribeSumLong(Observable<Long> source, boolean zeroDefault) {
        this.source = source;
        this.zeroDefault = zeroDefault;
    }


    @Override
    public void subscribe(ObservableEmitter<Long> e) throws Exception {

        new SumLongSubscriber(e, zeroDefault).subscribeTo(source);

    }

    static final class SumLongSubscriber extends ScalarDeferredSubscriber<Long, Long> {

        long sum;

        public SumLongSubscriber(ObservableEmitter<? super Long> actual, boolean zeroDefault) {
            super(actual);
            if (zeroDefault) {
                hasValue = true;
            }
        }

        @Override
        public void onNext(Long t) {
            if (!hasValue) {
                hasValue = true;
            }
            sum += t.longValue();
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
