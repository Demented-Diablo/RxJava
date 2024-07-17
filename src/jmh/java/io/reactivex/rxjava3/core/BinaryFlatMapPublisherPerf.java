package io.reactivex.rxjava3.core;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.reactivestreams.Publisher;
import io.reactivex.rxjava3.functions.Function;
import java.util.concurrent.TimeUnit;
import java.util.*;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class BinaryFlatMapPublisherPerf {
    @Param({ "1", "1000", "1000000" })
    public int times;

    Flowable<Integer> singleFlatMapPublisher;
    Flowable<Integer> singleFlatMapHidePublisher;
    Flowable<Integer> singleFlattenAsPublisher;
    Flowable<Integer> maybeFlatMapPublisher;
    Flowable<Integer> maybeFlatMapHidePublisher;
    Flowable<Integer> maybeFlattenAsPublisher;
    Flowable<Integer> completableFlatMapPublisher;
    Flowable<Integer> completableFlattenAsPublisher;

    @Setup
    public void setup() {
        final Integer[] array = new Integer[times];
        Arrays.fill(array, 777);

        final List<Integer> list = Arrays.asList(array);

        final Flowable<Integer> arrayFlowable = Flowable.fromArray(array);
        final Flowable<Integer> arrayFlowableHide = Flowable.fromArray(array).hide();
        final Flowable<Integer> listFlowable = Flowable.fromIterable(list);

        singleFlatMapPublisher = Single.just(1).flatMapPublisher(v -> arrayFlowable);
        singleFlatMapHidePublisher = Single.just(1).flatMapPublisher(v -> arrayFlowableHide);
        singleFlattenAsPublisher = Single.just(1).flattenAsFlowable(v -> list);
        maybeFlatMapPublisher = Maybe.just(1).flatMapPublisher(v -> arrayFlowable);
        maybeFlatMapHidePublisher = Maybe.just(1).flatMapPublisher(v -> arrayFlowableHide);
        maybeFlattenAsPublisher = Maybe.just(1).flattenAsFlowable(v -> list);
        completableFlatMapPublisher = Completable.complete().andThen(listFlowable);
        completableFlattenAsPublisher = Completable.complete().andThen(arrayFlowable);
    }

    @Benchmark
    public void singleFlatMapPublisher(Blackhole bh) {
        singleFlatMapPublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void singleFlatMapHidePublisher(Blackhole bh) {
        singleFlatMapHidePublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void singleFlattenAsPublisher(Blackhole bh) {
        singleFlattenAsPublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlatMapPublisher(Blackhole bh) {
        maybeFlatMapPublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlatMapHidePublisher(Blackhole bh) {
        maybeFlatMapHidePublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void maybeFlattenAsPublisher(Blackhole bh) {
        maybeFlattenAsPublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void completableFlatMapPublisher(Blackhole bh) {
        completableFlatMapPublisher.subscribe(new PerfConsumer(bh));
    }

    @Benchmark
    public void completableFlattenAsPublisher(Blackhole bh) {
        completableFlattenAsPublisher.subscribe(new PerfConsumer(bh));
    }
}

