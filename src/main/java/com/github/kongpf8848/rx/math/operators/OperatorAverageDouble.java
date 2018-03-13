package com.github.kongpf8848.rx.math.operators;

import io.reactivex.ObservableOperator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public final class OperatorAverageDouble<T> implements ObservableOperator<Double,T> {

    final Function<? super T, Double> valueExtractor;

    public OperatorAverageDouble(Function<? super T, Double> valueExtractor) {
        this.valueExtractor = valueExtractor;
    }


    @Override
    public Observer<? super T> apply(@NonNull Observer<? super Double> child) throws Exception {
        return new AverageObserver(child);
    }

    private final class AverageObserver implements Observer<T> {

        final Observer<? super Double> child;
        double sum;
        int count;

        public AverageObserver(Observer<? super Double> observer) {
            this.child = observer;
        }

        @Override
        public void onSubscribe(@NonNull Disposable disposable) {

        }

        @Override
        public void onNext(@NonNull T t) {

            try {
                sum+=valueExtractor.apply(t);
                count++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            child.onError(e);
        }

        @Override
        public void onComplete() {
            if (count > 0) {
                try
                {
                    child.onNext(sum/count);
                }
                catch (Throwable t)
                {
                    child.onError(t);
                    return;
                }
                child.onComplete();
            }
            else {
                child.onError(new IllegalArgumentException("Sequence contains no elements"));
            }
        }
    }
}
