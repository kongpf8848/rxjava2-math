package com.github.kongpf8848.rx.math.operators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;


public class OperatorMinMax {

    private OperatorMinMax() {
        throw new IllegalStateException("No instances!");
    }

    public static <T extends Comparable<? super T>> Observable<T> min(Observable<T> source) {
        return minMax(source, 1);
    }

    public static <T> Observable<T> min(Observable<T> source, final Comparator<? super T> comparator) {
        return minMax(source, comparator, 1);
    }

    public static <T, R extends Comparable<? super R>> Single<List<T>> minBy(Observable<T> source, final Function<T, R> selector) {
        return minMaxBy(source, selector, -1);
    }

    public static <T, R> Single<List<T>> minBy(Observable<T> source, final Function<T, R> selector, final Comparator<? super R> comparator) {
        return minMaxBy(source, selector, comparator, -1);
    }


    public static <T extends Comparable<? super T>> Observable<T> max(Observable<T> source) {
        return minMax(source, -1);
    }

    public static <T> Observable<T> max(Observable<T> source, final Comparator<? super T> comparator) {
        return minMax(source, comparator, -1);
    }

    public static <T, R extends Comparable<? super R>> Single<List<T>> maxBy(Observable<T> source, final Function<T, R> selector) {
        return minMaxBy(source, selector, 1);
    }

    public static <T, R> Single<List<T>> maxBy(Observable<T> source, final Function<T, R> selector, final Comparator<? super R> comparator) {
        return minMaxBy(source, selector, comparator, 1);
    }


    private static <T extends Comparable<? super T>> Observable<T> minMax(Observable<T> source, final int flag) {
        return minMax(source, OnSubscribeMinMax.COMPARABLE_MIN, flag);
    }

    private static <T> Observable<T> minMax(Observable<T> source, final Comparator<? super T> comparator, final int flag) {
        return Observable.create(new OnSubscribeMinMax<T>(source, comparator, flag));
    }

    private static <T, R extends Comparable<? super R>> Single<List<T>> minMaxBy(Observable<T> source, final Function<T, R> selector, final int flag) {
        return minMaxBy(source, selector, OnSubscribeMinMax.COMPARABLE_MIN, flag);
    }

    private static <T, R> Single<List<T>> minMaxBy(Observable<T> source, final Function<T, R> selector, final Comparator<? super R> comparator, final int flag) {


        return source.collect(new Callable<List<T>>() {
                                  @Override
                                  public List<T> call() throws Exception {
                                      return new ArrayList<T>();
                                  }
                              },
                new BiConsumer<List<T>, T>() {

                    @Override
                    public void accept(List<T> acc, T value) {
                        if (acc.isEmpty()) {
                            acc.add(value);
                        } else {
                            R t1 = null;
                            R t2 = null;
                            try {
                                t1 = selector.apply(acc.get(0));
                                t2 = selector.apply(value);
                            } catch (Exception e) {

                            }
                            int compareResult = comparator.compare(t1, t2);
                            if (compareResult == 0) {
                                acc.add(value);
                            } else if (flag * compareResult < 0) {
                                acc.clear();
                                acc.add(value);
                            }
                        }
                    }
                });
    }

}
