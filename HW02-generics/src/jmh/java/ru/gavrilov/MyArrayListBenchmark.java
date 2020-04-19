package ru.gavrilov;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class MyArrayListBenchmark {

    static int SIZE = 10_000;

    @State(Scope.Thread)
    public static class MyState {

        @Param("10") //for example
        int SIZE_INIT;

        List<String> arrayList;
        List<String> myList;

        @Setup(Level.Trial)
        public void setup() {
            this.myList = new MyArrayList<>(SIZE_INIT);
            this.arrayList = new ArrayList<>(SIZE_INIT);

            for (int i = 0; i < SIZE_INIT; i++) {
                myList.add(i, "I: " + i);
                arrayList.add(i, "I: " + i);
            }
        }
    }

    @State(Scope.Thread)
    public static class StateForInt {
        @Param("10") //for example
        int SIZE_INIT;

        List<Integer> intMyList;
        List<Integer> intArrayList;

        @Setup //default Trial
        public void setup() {
            this.intMyList = new ArrayList<>(SIZE_INIT);
            this.intArrayList = new MyArrayList<>(SIZE_INIT);
        }
    }

    /**
     * ADD
     */
    @Benchmark
    public List<String> addAtArrayList(MyState state) {
        for (int idx = 0; idx < SIZE; idx++) {
            state.arrayList.add(idx, String.valueOf(idx));
        }
        return state.arrayList;
    }

    @Benchmark
    public List<String> addAtMyList(MyState state) {
        for (int idx = 0; idx < SIZE; idx++) {
            state.myList.add(idx, String.valueOf(idx));
        }
        return state.myList;
    }

    /**
     * GET
     */
    @Benchmark
    public void getFromArrayList(MyState state, Blackhole bh) {
        String s = state.arrayList.get(5);
        bh.consume(s);
    }

    @Benchmark
    public void getFromMyList(MyState state, Blackhole bh) {
        String s = state.arrayList.get(5);
        bh.consume(s);
    }

    /**
     * CONTAINS
     */
    @Benchmark
    public void containsInArrayList(MyState state, Blackhole bh) {
        boolean s = state.arrayList.contains("I: 5");
        bh.consume(s);
    }

    @Benchmark
    public void containsInMyList(MyState state, Blackhole bh) {
        boolean s = state.arrayList.contains("I: 5");
        bh.consume(s);
    }

    /**
     * REMOVE
     */
    @Benchmark
    public boolean removeFromArrayList(MyState state) {
        boolean res = state.arrayList.remove("I: 5");
        return res;
    }

    @Benchmark
    public boolean removeFromMyList(MyState state) {
        boolean res = state.arrayList.remove("I: 5");
        return res;
    }

    @Benchmark
    public long testAddMyList(StateForInt state) {
        for (int idx = 0; idx < SIZE; idx++) {
            state.intMyList.add(idx);
        }

        long sum = 0;
        for (int idx = 0; idx < SIZE; idx++) {
            sum += state.intMyList.get(idx);
        }
        return sum;
    }

    @Benchmark
    public long testAddArrayList(StateForInt state) {
        for (int idx = 0; idx < SIZE; idx++) {
            state.intArrayList.add(idx);
        }

        long sum = 0;
        for (int idx = 0; idx < SIZE; idx++) {
            sum += state.intArrayList.get(idx);
        }
        return sum;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + MyArrayListBenchmark.class.getSimpleName() + ".*")
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}

