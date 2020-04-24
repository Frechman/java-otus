package ru.gavrilov.unsafe;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class UnsafeIntListTest {

    @State(Scope.Thread)
    public static class BenchState {
        @Param("10")
        int DEFAULT_SIZE;

        List<Integer> list = new ArrayList<>(DEFAULT_SIZE);
        UnsafeIntList unsafeList = new UnsafeIntList(DEFAULT_SIZE);

        @Setup(Level.Iteration)
        public void setUp() {
            list.clear();
            unsafeList.clear();
        }

        @TearDown(Level.Trial)  //???
        public void tearDown() {
            unsafeList.close();
        }
    }

    @State(Scope.Thread)
    public static class YetState {
        int DEFAULT_SIZE = 10;
        int MAX_SIZE = 1_000_000;

        List<Integer> list;
        UnsafeIntList unsafeList;

        @Setup(Level.Trial)
        public void setup() {
            list = new ArrayList<>(DEFAULT_SIZE);
            unsafeList = new UnsafeIntList(DEFAULT_SIZE);
        }

        @TearDown(Level.Trial)
        public void tearDown() {
            unsafeList.close();
        }
    }

    @Benchmark
    @Warmup(iterations = 5, batchSize = 5000)
    @Measurement(iterations = 5, batchSize = 5000)
    @BenchmarkMode(Mode.SingleShotTime)
    public List<Integer> testAddAtArrayList(BenchState state) {
        state.list.add(state.list.size() / 2);
        return state.list;
    }

    @Benchmark
    @Warmup(iterations = 5, batchSize = 5000)
    @Measurement(iterations = 5, batchSize = 5000)
    @BenchmarkMode(Mode.SingleShotTime)
    public UnsafeIntList testAddAtUnsafeList(BenchState state) {
        state.unsafeList.add(state.list.size() / 2);
        return state.unsafeList;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public long unsafeListTest(YetState state) {
        for (int idx = 0; idx < state.MAX_SIZE; idx++) {
            state.unsafeList.add(idx);
        }

        long summ = 0;
        for (int idx = 0; idx < state.MAX_SIZE; idx++) {
            summ += state.unsafeList.get(idx);
        }
        return summ;
    }

    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    public long arrayListTest(YetState state) {
        for (int idx = 0; idx < state.MAX_SIZE; idx++) {
            state.list.add(idx, idx);
        }

        long summ = 0;
        for (int idx = 0; idx < state.MAX_SIZE; idx++) {
            summ += state.list.get(idx);
        }
        return summ;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + UnsafeIntListTest.class.getSimpleName() + ".*")
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}