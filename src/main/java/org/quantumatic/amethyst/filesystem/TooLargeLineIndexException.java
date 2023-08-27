package main.java.org.quantumatic.amethyst.filesystem;

public class TooLargeLineIndexException extends Exception {
    public TooLargeLineIndexException(int given, int limit) {
        super(String.format("Line index %d is too large. The limit is %d.", given, limit));
    }
}
