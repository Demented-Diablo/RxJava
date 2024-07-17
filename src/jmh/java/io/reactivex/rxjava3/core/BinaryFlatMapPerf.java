package io.reactivex.rxjava3.core;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 1)
@State(Scope.Thread)
public class BinaryFlatMapPerf {
    @Param({ "1", "1000", "1000000" })
    public int times;

    BinaryFlatMapPublisherPerf publisherPerf = new BinaryFlatMapPublisherPerf();
    BinaryFlatMapObservablePerf observablePerf = new BinaryFlatMapObservablePerf();

    @Setup
    public void setup() {
        publisherPerf.times = this.times;
        observablePerf.times = this.times;
        publisherPerf.setup();
        observablePerf.setup();
    }

    @Benchmark
    public void singleFlatMapPublisher(Blackhole bh) {
        publisherPerf.singleFlatMapPublisher(bh);
    }

    @Benchmark
    public void singleFlatMapHidePublisher(Blackhole bh) {
        publisherPerf.singleFlatMapHidePublisher(bh);
    }

    @Benchmark
    public void singleFlattenAsPublisher(Blackhole bh) {
        publisherPerf.singleFlattenAsPublisher(bh);
    }

    @Benchmark
    public void maybeFlatMapPublisher(Blackhole bh) {
        publisherPerf.maybeFlatMapPublisher(bh);
    }

    @Benchmark
    public void maybeFlatMapHidePublisher(Blackhole bh) {
        publisherPerf.maybeFlatMapHidePublisher(bh);
    }

    @Benchmark
    public void maybeFlattenAsPublisher(Blackhole bh) {
        publisherPerf.maybeFlattenAsPublisher(bh);
    }

    @Benchmark
    public void completableFlatMapPublisher(Blackhole bh) {
        publisherPerf.completableFlatMapPublisher(bh);
    }

    @Benchmark
    public void completableFlattenAsPublisher(Blackhole bh) {
        publisherPerf.completableFlattenAsPublisher(bh);
    }

    @Benchmark
    public void singleFlatMapObservable(Blackhole bh) {
        observablePerf.singleFlatMapObservable(bh);
    }

    @Benchmark
    public void singleFlatMapHideObservable(Blackhole bh) {
        observablePerf.singleFlatMapHideObservable(bh);
    }

    @Benchmark
    public void singleFlattenAsObservable(Blackhole bh) {
        observablePerf.singleFlattenAsObservable(bh);
    }

    @Benchmark
    public void maybeFlatMapObservable(Blackhole bh) {
        observablePerf.maybeFlatMapObservable(bh);
    }

    @Benchmark
    public void maybeFlatMapHideObservable(Blackhole bh) {
        observablePerf.maybeFlatMapHideObservable(bh);
    }

    @Benchmark
    public void maybeFlattenAsObservable(Blackhole bh) {
        observablePerf.maybeFlattenAsObservable(bh);
    }

    @Benchmark
    public void completableFlatMapObservable(Blackhole bh) {
        observablePerf.completableFlatMapObservable(bh);
    }

    @Benchmark
    public void completableFlattenAsObservable(Blackhole bh) {
        observablePerf.completableFlattenAsObservable(bh);
    }
}
