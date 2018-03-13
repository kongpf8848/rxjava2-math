package com.github.kongpf8848.rx.math.operators;

import com.github.kongpf8848.rx.math.operators.OperatorSum;
import com.github.kongpf8848.rx.observables.MathObservable;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by pengf on 2018/3/6.
 */

public class OperatorSumTest {
    @SuppressWarnings("unchecked")
    Observer<Integer> w = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Long> wl = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Float> wf = mock(Observer.class);
    @SuppressWarnings("unchecked")
    Observer<Double> wd = mock(Observer.class);

    @Test
    public void testSumOfAFewInts() throws Throwable {
        Observable<Integer> src = Observable.just(1, 2, 3, 4, 5);
        OperatorSum.sumIntegers(src).subscribe(w);

        verify(w, times(1)).onNext(anyInt());
        verify(w).onNext(15);
        verify(w, never()).onError(any(Throwable.class));
        verify(w, times(1)).onComplete();
    }

    @Test
    public void testEmptySum() throws Throwable {
        Observable<Integer> src = Observable.empty();
        OperatorSum.sumIntegers(src).subscribe(w);

        verify(w, times(1)).onNext(anyInt());
        verify(w).onNext(0);
        verify(w, never()).onError(any(Throwable.class));
        verify(w, times(1)).onComplete();
    }

    @Test
    public void testSumOfAFewLongs() throws Throwable {
        Observable<Long> src = Observable.just(1L, 2L, 3L, 4L, 5L);
        OperatorSum.sumLongs(src).subscribe(wl);

        verify(wl, times(1)).onNext(anyLong());
        verify(wl).onNext(15L);
        verify(wl, never()).onError(any(Throwable.class));
        verify(wl, times(1)).onComplete();
    }

    @Test
    public void testEmptySumLongs() throws Throwable {
        Observable<Long> src = Observable.empty();
        OperatorSum.sumLongs(src).subscribe(wl);

        verify(wl, times(1)).onNext(anyLong());
        verify(wl).onNext(0L);
        verify(wl, never()).onError(any(Throwable.class));
        verify(wl, times(1)).onComplete();
    }

    @Test
    public void testSumOfAFewFloats() throws Throwable {
        Observable<Float> src = Observable.just(1.0f);
        OperatorSum.sumFloats(src).subscribe(wf);

        verify(wf, times(1)).onNext(anyFloat());
        verify(wf).onNext(1.0f);
        verify(wf, never()).onError(any(Throwable.class));
        verify(wf, times(1)).onComplete();
    }

    @Test
    public void testEmptySumFloats() throws Throwable {
        Observable<Float> src = Observable.empty();
        OperatorSum.sumFloats(src).subscribe(wf);

        verify(wf, times(1)).onNext(anyFloat());
        verify(wf).onNext(0.0f);
        verify(wf, never()).onError(any(Throwable.class));
        verify(wf, times(1)).onComplete();
    }

    @Test
    public void testSumOfAFewDoubles() throws Throwable {
        Observable<Double> src = Observable.just(0.0d, 1.0d, 0.5d);
        OperatorSum.sumDoubles(src).subscribe(wd);

        verify(wd, times(1)).onNext(anyDouble());
        verify(wd).onNext(1.5d);
        verify(wd, never()).onError(any(Throwable.class));
        verify(wd, times(1)).onComplete();
    }

    @Test
    public void testEmptySumDoubles() throws Throwable {
        Observable<Double> src = Observable.empty();
        OperatorSum.sumDoubles(src).subscribe(wd);

        verify(wd, times(1)).onNext(anyDouble());
        verify(wd).onNext(0.0d);
        verify(wd, never()).onError(any(Throwable.class));
        verify(wd, times(1)).onComplete();
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
    public void testIntegerSumSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Integer> length = new Function<String, Integer>() {
            @Override
            public Integer apply(String t1) throws Exception {
                return t1.length();
            }
        };

        Observable<Integer> result = MathObservable.from(source).sumInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 10);
    }

    @Test
    public void testLongSumSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                return (long) t1.length();
            }
        };

        Observable<Long> result = MathObservable.from(source).sumLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 10L);
    }

    @Test
    public void testFloatSumSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception{
                return (float) t1.length();
            }
        };

        Observable<Float> result = MathObservable.from(source).sumFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 10f);
    }

    @Test
    public void testDoubleSumSelector() {
        Observable<String> source = Observable.just("a", "bb", "ccc", "dddd");
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception{
                return (double) t1.length();
            }
        };

        Observable<Double> result = MathObservable.from(source).sumDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testValue(o, 10d);
    }

    @Test
    public void testIntegerSumSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Integer> length = new Function<String, Integer>() {
            @Override
            public Integer apply(String t1) throws Exception {
                return t1.length();
            }
        };

        Observable<Integer> result = MathObservable.from(source).sumInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testLongSumSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                return (long) t1.length();
            }
        };

        Observable<Long> result = MathObservable.from(source).sumLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testFloatSumSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception {
                return (float) t1.length();
            }
        };

        Observable<Float> result = MathObservable.from(source).sumFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testDoubleSumSelectorEmpty() {
        Observable<String> source = Observable.empty();
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception {
                return (double) t1.length();
            }
        };

        Observable<Double> result = MathObservable.from(source).sumDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, IllegalArgumentException.class);
    }

    @Test
    public void testIntegerSumSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Integer> length = new Function<String, Integer>() {
            @Override
            public Integer apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Integer> result = MathObservable.from(source).sumInteger(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testLongSumSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Long> length = new Function<String, Long>() {
            @Override
            public Long apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Long> result = MathObservable.from(source).sumLong(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testFloatSumSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Float> length = new Function<String, Float>() {
            @Override
            public Float apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Float> result = MathObservable.from(source).sumFloat(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    @Test
    public void testDoubleSumSelectorThrows() {
        Observable<String> source = Observable.just("a");
        Function<String, Double> length = new Function<String, Double>() {
            @Override
            public Double apply(String t1) throws Exception {
                throw new CustomException();
            }
        };

        Observable<Double> result = MathObservable.from(source).sumDouble(length);
        @SuppressWarnings("unchecked")
        Observer<Object> o = mock(Observer.class);
        result.subscribe(o);

        testThrows(o, CustomException.class);
    }

    static class CustomException extends RuntimeException {
        private static final long serialVersionUID = 8825937249852675778L;
    }



}
