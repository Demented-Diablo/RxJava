package io.reactivex.rxjava3.core;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.TimeUnit;
import java.util.*;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class BinaryFlatMapObservablePerf {
    @Param({ "1", "1000", "1000000" })
    public int times;

    Observable<Integer> singleFlatMapObservable;
    Observable<Integer> singleFlatMapHideObservable;
    Observable<Integer> singleFlattenAsObservable;
    Observable<Integer> maybeFlatMapObservable;
    Observable<Integer> maybeFlatMapHideObservable;
    Observable<Integer> maybeFlattenAsObservable;
    Observable<Integer> completableFlatMapObservable;
    Observable<Integer> completableFlattenAsObservable;

    @Setup
    public void setup() {
        final Integer[] array = new Integer[times];
        Arrays.fill(array, 777);

        final List<Integer> list = Arrays.asList(array);

        final Observable<Integer> arrayObservable = Observable.fromArray(array);
        final Observable<Integer> arrayObservableHide = Observable.fromArray(array).hide();
        final Observable<Integer> listObservable = Observable.fromIterable(list);

        singleFlatMapObservable = Single.just(1).flatMapObservable(v -> arrayObservable);
        singleFlatMapHideObservable = Single.just(1).flatMapObservable(v -> arrayObservableHide);
        singleFlattenAsObservable = Single.just(1).flattenAsObservable(v -> list);
        maybeFlatMapObservable = Maybe.just(1).flatMapObservable(v -> arrayObservable);
        maybeFlatMapHideObservable = Maybe.just(1).flatMapObservable(v -> arrayObservableHide);
        maybeFlattenAsObservable = Maybe.just(1).flattenAsObservable(v -> list);
        completableFlatMapObservable = Completable.complete().andThen(listObservable);
        completableFlattenAsObservable = Completable.complete().andThen(arrayObservable);
    }

    @Benchmark
    public void singleFlatMapObservable(Blackhole bh) {
        singleFlatMapObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void singleFlatMapHideObservable(Blackhole bh) {
        singleFlatMapHideObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void singleFlattenAsObservable(Blackhole bh) {
        singleFlattenAsObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlatMapObservable(Blackhole bh) {
        maybeFlatMapObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlatMapHideObservable(Blackhole bh) {
        maybeFlatMapHideObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlattenAsObservable(Blackhole bh) {
        maybeFlattenAsObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void completableFlatMapObservable(Blackhole bh) {
        completableFlatMapObservable.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void completableFlattenAsObservable(Blackhole bh) {
        completableFlattenAsObservable.subscribe(new PerfConsumer(bh));
    }
}
