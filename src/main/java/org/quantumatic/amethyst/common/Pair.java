package main.java.org.quantumatic.amethyst.common;

public class Pair<L, R> {
    private final L first;
    private final R second;

    private Pair(L first, R second) {
        this.first = first;
        this.second = second;
    }

    public L getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    public static <L, R> Pair<L, R> of(L first, R second) {
        return new Pair<>(first, second);
    }
}
