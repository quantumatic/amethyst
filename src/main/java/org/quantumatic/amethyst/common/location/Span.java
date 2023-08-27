package main.java.org.quantumatic.amethyst.common.location;

import main.java.org.quantumatic.amethyst.common.Range;

public record Span(String filepath, Range<Integer> range) {}
