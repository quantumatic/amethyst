package main.java.org.quantumatic.amethyst.common;

import java.util.Comparator;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class Range<T> {
    private final T fromInclusive;
    private final T toInclusive;
    private final Comparator<T> comparator;

    private class ComparableComparator implements Comparator<T> {
        @Override
        public int compare(final Object o1, final Object o2) {
            return ((Comparable<T>) o1).compareTo((T) o2);
        }
    }

    public static <T extends Comparable<? super T>> Range<T> between(final T fromInclusive, final T toInclusive) {
        return new Range<>(fromInclusive, toInclusive, null);
    }

    public static <T extends Comparable<? super T>> Range<T> between(final T fromInclusive, final T toInclusive,
                                                                     final Comparator<T> comparator) {
        return new Range<>(fromInclusive, toInclusive, comparator);
    }

    Range(final T fromInclusive, final T toInclusive, final Comparator<T> comparator) {
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;

        this.comparator = Objects.requireNonNullElseGet(comparator, ComparableComparator::new);
    }

    public T getFromInclusive() {
        return fromInclusive;
    }

    public T getToInclusive() {
        return toInclusive;
    }

    public boolean contains(final T element) {
        if (element == null) {
            return false;
        }

        return comparator.compare(element, fromInclusive) > -1 && comparator.compare(element, toInclusive) < 1;
    }
}
