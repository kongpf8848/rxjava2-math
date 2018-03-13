package com.github.kongpf8848.rx.observables;

import com.github.kongpf8848.rx.math.operators.OperatorAverageDouble;
import com.github.kongpf8848.rx.math.operators.OperatorAverageFloat;
import com.github.kongpf8848.rx.math.operators.OperatorAverageInteger;
import com.github.kongpf8848.rx.math.operators.OperatorAverageLong;
import com.github.kongpf8848.rx.math.operators.OperatorMinMax;
import com.github.kongpf8848.rx.math.operators.OperatorSum;

import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

public class MathObservable<T>  {

    private final Observable<T> o;

    private MathObservable(Observable<T> o) {
        this.o = o;
    }

    public static <T> MathObservable<T> from(Observable<T> o) {
        return new MathObservable<T>(o);
    }

    private static Function INDENTITY = new Function<Object,Object>() {

        @Override
        public Object apply(@NonNull Object o) throws Exception {
            return o;
        }
    };

    private static <T> Function<T, T> identity() {
        return INDENTITY;
    }

    public final static Observable<Double> averageDouble(Observable<Double> source) {
        return source.lift(new OperatorAverageDouble<Double>(MathObservable.<Double>identity()));
    }
    public final static Observable<Float> averageFloat(Observable<Float> source) {
        return source.lift(new OperatorAverageFloat<Float>(MathObservable.<Float>identity()));
    }

    public final static Observable<Integer> averageInteger(Observable<Integer> source) {
        return source.lift(new OperatorAverageInteger<Integer>(MathObservable.<Integer>identity()));
    }
    public final static Observable<Long> averageLong(Observable<Long> source) {
        return source.lift(new OperatorAverageLong<Long>(MathObservable.<Long>identity()));
    }

    public final static <T extends Comparable<? super T>> Observable<T> max(Observable<T> source) {
        return OperatorMinMax.max(source);
    }
    public final static <T extends Comparable<? super T>> Observable<T> min(Observable<T> source) {
        return OperatorMinMax.min(source);
    }

    public final static Observable<Double> sumDouble(Observable<Double> source) {
        return OperatorSum.sumDoubles(source);
    }
    public final static Observable<Float> sumFloat(Observable<Float> source) {
        return OperatorSum.sumFloats(source);
    }

    public final static Observable<Integer> sumInteger(Observable<Integer> source) {
        return OperatorSum.sumIntegers(source);
    }
    public final static Observable<Long> sumLong(Observable<Long> source) {
        return OperatorSum.sumLongs(source);
    }

    public final Observable<Double> averageDouble(Function<? super T, Double> valueExtractor) {
        return o.lift(new OperatorAverageDouble<T>(valueExtractor));
    }

    public final Observable<Float> averageFloat(Function<? super T, Float> valueExtractor) {
        return o.lift(new OperatorAverageFloat<T>(valueExtractor));
    }

    public final Observable<Integer> averageInteger(Function<? super T, Integer> valueExtractor) {
        return o.lift(new OperatorAverageInteger<T>(valueExtractor));
    }
    public final Observable<Long> averageLong(Function<? super T, Long> valueExtractor) {
        return o.lift(new OperatorAverageLong<T>(valueExtractor));
    }


    public final Observable<T> max(Comparator<? super T> comparator) {
        return OperatorMinMax.max(o, comparator);
    }
    public final Observable<T> min(Comparator<? super T> comparator) {
        return OperatorMinMax.min(o, comparator);
    }

    public final Observable<Double> sumDouble(Function<? super T, Double> valueExtractor) {
        return OperatorSum.sumAtLeastOneDoubles(o.map(valueExtractor));
    }
    public final Observable<Float> sumFloat(Function<? super T, Float> valueExtractor) {
        return OperatorSum.sumAtLeastOneFloats(o.map(valueExtractor));
    }

    public final Observable<Integer> sumInteger(Function<? super T, Integer> valueExtractor) {
        return OperatorSum.sumAtLeastOneIntegers(o.map(valueExtractor));
    }
    public final Observable<Long> sumLong(Function<? super T, Long> valueExtractor) {
        return OperatorSum.sumAtLeastOneLongs(o.map(valueExtractor));
    }

}
