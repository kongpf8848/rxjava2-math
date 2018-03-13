package com.github.kongpf8848.rx.math.operators;

import com.github.kongpf8848.rx.observables.MathObservable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;


import java.io.FileNotFoundException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;


public class OperatorAverageTest {

    @SuppressWarnings("unchecked")
    Observer<Integer> w = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Long> wl = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Float> wf = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Double> wd = mock(Observer.class);

    @Test
    public void testAverageOfAFewInts() throws Throwable {
        Observable<Integer> src = Observable.just(1, 2, 3, 4, 6);
        MathObservable.averageInteger(src).subscribe(w);

        verify(w, times(1)).onNext(anyInt());
        verify(w).onNext(3);
        verify(w, never()).onError(any(Throwable.class));
        verify(w, times(1)).onComplete();
    }


    @Test
    public void testEmptyAverage() throws Throwable {
        Observable<Integer> src = Observable.empty();
        MathObservable.averageInteger(src).subscribe(w);

        verify(w, never()).onNext(anyInt());
        verify(w, times(1)).onError(isA(IllegalArgumentException.class));
        verify(w, never()).onComplete();
    }

    @Test
    public void testAverageOfAFewLongs() throws Throwable {
        Observable<Long> src = Observable.just(1L, 2L, 3L, 4L, 6L);
        MathObservable.averageLong(src).subscribe(wl);

        verify(wl, times(1)).onNext(anyLong());
        verify(wl).onNext(3L);
        verify(wl, never()).onError(any(Throwable.class));
        verify(wl, times(1)).onComplete();
    }

    @Test
    public void testEmptyAverageLongs() throws Throwable {
        Observable<Long> src = Observable.empty();
        MathObservable.averageLong(src).subscribe(wl);

        verify(wl, never()).onNext(anyLong());
        verify(wl, times(1)).onError(isA(IllegalArgumentException.class));
        verify(wl, never()).onComplete();
    }

    @Test
    public void testAverageOfAFewFloats() throws Throwable {
        Observable<Float> src = Observable.just(1.0f, 2.0f);
        MathObservable.averageFloat(src).subscribe(wf);

        verify(wf, times(1)).onNext(anyFloat());
        verify(wf).onNext(1.5f);
        verify(wf, never()).onError(any(Throwable.class));
        verify(wf, times(1)).onComplete();
    }

    @Test
    public void testEmptyAverageFloats() throws Throwable {
        Observable<Float> src = Observable.empty();
        MathObservable.averageFloat(src).subscribe(wf);

        verify(wf, never()).onNext(anyFloat());
        verify(wf, times(1)).onError(isA(IllegalArgumentException.class));
        verify(wf, never()).onComplete();
    }

    @Test
    public void testAverageOfAFewDoubles() throws Throwable {
        Observable<Double> src = Observable.just(1.0d, 2.0d);
        MathObservable.averageDouble(src).subscribe(wd);

        verify(wd, times(1)).onNext(anyDouble());
        verify(wd).onNext(1.5d);
        verify(wd, never()).onError(any(Throwable.class));
        verify(wd, times(1)).onComplete();
    }

    @Test
    public void testEmptyAverageDoubles() throws Throwable {
        Observable<Double> src = Observable.empty();
        MathObservable.averageDouble(src).subscribe(wd);

        verify(wd, never()).onNext(anyDouble());
        verify(wd, times(1)).onError(isA(IllegalArgumentException.class));
        verify(wd, never()).onComplete();
    }

    void testThrows(Observer<Object> o, Class<? extends Throwable> errorClass) {
        verify(o, never()).onNext(any());
        verify(o, never()).onComplete();
        verify(o, times(1)).onError(any(errorClass));
    }

    <N extends Number> void testValue(Observer<Object> o, N value) {
        verify(o, times(1)).onNext(value);
        verify(o, times(1)).onComplete();
        verify(o, never()).onError(any(Throwable.class));
    }

    @Test
    public void testIntegerAverageSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Integer> length = new Function<String,Integer> (){
            @Override
            public Integer apply(String t1) throws Exception {
                return t1.length();
            }

        };

        Observable<Integer> result = MathObservable.from(source).averageInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);
        testValue(o, 2);
    }


    @Test
    public void testLongAverageSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                return (long) t1.length();
            }
        };

        Observable<Long> result = MathObservable.from(source).averageLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 2L);
    }

    @Test
    public void testFloatAverageSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception {
                return (float) t1.length();
            }
        };

        Observable<Float> result = MathObservable.from(source).averageFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 2.5f);
    }

    @Test
    public void testDoubleAverageSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception{
                return (double) t1.length();
            }
        };

        Observable<Double> result = MathObservable.from(source).averageDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 2.5d);
    }

    @Test
    public void testIntegerAverageSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Integer> length = new Function<String, Integer>() {
            @Override
            public Integer apply(String t1) throws Exception {
                return t1.length();
            }
        };

        Observable<Integer> result = MathObservable.from(source).averageInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testLongAverageSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                return (long) t1.length();
            }
        };

        Observable<Long> result = MathObservable.from(source).averageLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testFloatAverageSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception  {
                return (float) t1.length();
            }
        };

        Observable<Float> result = MathObservable.from(source).averageFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testDoubleAverageSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception {
                return (double) t1.length();
            }
        };

        Observable<Double> result = MathObservable.from(source).averageDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testIntegerAverageSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Integer> length = new Function<String, Integer>() {
            @Override
            public Integer apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Integer> result = MathObservable.from(source).averageInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testLongAverageSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Long> result = MathObservable.from(source).averageLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testFloatAverageSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Float> result = MathObservable.from(source).averageFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testDoubleAverageSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Double> result = MathObservable.from(source).averageDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    static class CustomException extends RuntimeException {
        private static final long serialVersionUID = 6873927510089089979L;
    }

}
