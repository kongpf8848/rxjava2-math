package com.github.kongpf8848.rx.math.operators;

import com.github.kongpf8848.rx.math.operators.OperatorMinMax;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.mockito.InOrder;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.functions.Function;


public class OperatorMinMaxTest {
    @Test
    public void testMin() {
        Observable<Integer> observable = OperatorMinMax.min(Observable.just(2, 3, 1, 4));

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onNext(1);
        inOrder.verify(observer, times(1)).onComplete();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinWithEmpty() {
        Observable<Integer> observable = OperatorMinMax.min(Observable.<Integer> empty());

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onError(isA(NoSuchElementException.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinWithComparator() {
        Observable<Integer> observable = OperatorMinMax.min(Observable.just(2, 3, 1, 4),
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onNext(4);
        inOrder.verify(observer, times(1)).onComplete();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinWithComparatorAndEmpty() {
        Observable<Integer> observable = OperatorMinMax.min(Observable.<Integer> empty(),
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onError(
                isA(NoSuchElementException.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinBy() {
        Single<List<String>> observable = OperatorMinMax.minBy(
                Observable.just("1", "2", "3", "4", "5", "6"),
                new Function<String, Comparable>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);
        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer,times(1)).onSuccess(Arrays.asList("2", "4", "6"));
        inOrder.verify(observer,never()).onError(any(Throwable.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinByWithEmpty() {
        Single<List<String>> observable =OperatorMinMax.minBy(
                Observable.<String> empty(), new Function<String, Comparable>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(new ArrayList<String>());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinByWithComparator() {
        Single<List<String>> observable = OperatorMinMax.minBy(
                Observable.just("1", "2", "3", "4", "5", "6"),
                new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                }, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(Arrays.asList("1", "3", "5"));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMinByWithComparatorAndEmpty() {
        Single<List<String>> observable = OperatorMinMax.minBy(
                Observable.<String> empty(), new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                }, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(new ArrayList<String>());
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void testMax() {
        Observable<Integer> observable = OperatorMinMax.max(Observable.just(2, 3, 1, 4));

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onNext(4);
        inOrder.verify(observer, times(1)).onComplete();
        inOrder.verifyNoMoreInteractions();
    }


    @Test
    public void testMaxWithEmpty() {
        Observable<Integer> observable = OperatorMinMax.max(Observable.<Integer> empty());

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onError(isA(NoSuchElementException.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxWithComparator() {
        Observable<Integer> observable = OperatorMinMax.max(Observable.just(2, 3, 1, 4),
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onNext(1);
        inOrder.verify(observer, times(1)).onComplete();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxWithComparatorAndEmpty() {
        Observable<Integer> observable = OperatorMinMax.max(Observable.<Integer> empty(),
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        Observer<Integer> observer = (Observer<Integer>) mock(Observer.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onError(
                isA(NoSuchElementException.class));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxBy() {
        Single<List<String>> observable = OperatorMinMax.maxBy(
                Observable.just("1", "2", "3", "4", "5", "6"),
                new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception{
                        return Integer.parseInt(t1) % 2;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(Arrays.asList("1", "3", "5"));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxByWithEmpty() {
        Single<List<String>> observable = OperatorMinMax.maxBy(
                Observable.<String> empty(), new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(new ArrayList<String>());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxByWithComparator() {
        Single<List<String>> observable = OperatorMinMax.maxBy(
                Observable.just("1", "2", "3", "4", "5", "6"),
                new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                }, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(Arrays.asList("2", "4", "6"));
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testMaxByWithComparatorAndEmpty() {
        Single<List<String>> observable = OperatorMinMax.maxBy(
                Observable.<String> empty(), new Function<String, Integer>() {
                    @Override
                    public Integer apply(String t1) throws Exception {
                        return Integer.parseInt(t1) % 2;
                    }
                }, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2 - o1;
                    }
                });

        @SuppressWarnings("unchecked")
        SingleObserver<List<String>> observer = (SingleObserver<List<String>>) mock(SingleObserver.class);

        observable.subscribe(observer);
        InOrder inOrder = inOrder(observer);
        inOrder.verify(observer, times(1)).onSuccess(new ArrayList<String>());
        inOrder.verifyNoMoreInteractions();
    }


}
