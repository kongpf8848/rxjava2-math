package com.github.kongpf8848.rx.math.operators;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;

public final class OperatorSum {
    private OperatorSum() { throw new IllegalStateException("No instances!"); }

    public static Observable<Integer> sumIntegers(Observable<Integer> source) {
        return Observable.create(new OnSubscribeSumInt(source, true));

    }

    public static Observable<Long> sumLongs(Observable<Long> source) {
        return Observable.create(new OnSubscribeSumLong(source, true));
    }

    public static Observable<Float> sumFloats(Observable<Float> source) {
        return Observable.create(new OnSubscribeSumFloat(source, true));
    }

    public static Observable<Double> sumDoubles(Observable<Double> source) {
        return Observable.create(new OnSubscribeSumDouble(source, true));
    }

    public static Observable<Integer> sumAtLeastOneIntegers(Observable<Integer> source) {
        return Observable.create(new OnSubscribeSumInt(source, false));
    }

    public static Observable<Long> sumAtLeastOneLongs(Observable<Long> source) {
        return Observable.create(new OnSubscribeSumLong(source, false));
    }

    public static Observable<Float> sumAtLeastOneFloats(Observable<Float> source) {
        return Observable.create(new OnSubscribeSumFloat(source, false));
    }

    public static Observable<Double> sumAtLeastOneDoubles(Observable<Double> source) {
        return Observable.create(new OnSubscribeSumDouble(source, false));
    }


}
