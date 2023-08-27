package main.java.org.quantumatic.amethyst.filesystem;

import main.java.org.quantumatic.amethyst.common.Range;
import main.java.org.quantumatic.amethyst.common.location.ByteLocation;
import main.java.org.quantumatic.amethyst.common.location.Location;
import main.java.org.quantumatic.amethyst.common.location.Span;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryFile {
    private final String path;
    private final String source;
    private final List<Integer> lineStarts;

    public InMemoryFile(String path, String source, int sourceLength) {
        this.path = path;
        this.source = source;
        this.lineStarts = Stream.concat(
                Stream.of(0),
                source
                        .lines()
                        .map(line -> line.length() - 1)
        ).collect(Collectors.toList());
    }

    public InMemoryFile(String path, String source, int sourceLength, List<Integer> lineStarts) {
        this.path = path;
        this.source = source;
        this.lineStarts = lineStarts;
    }

    public String getPath() {
        return path;
    }

    public String getSource() {
        return source;
    }

    public List<Integer> getLineStarts() {
        return lineStarts;
    }

    public String resolveSpan(Span span) {
        return source.substring(span.range().getFromInclusive(), span.range().getToInclusive());
    }

    public String resolveLocation(Location location) {
        return source.substring(location.start().offset(), location.end().offset());
    }

    public int lineStartByLineIndex(int lineIndex) throws TooLargeLineIndexException {
        if (lineIndex == lineStarts.size()) {
            return source.length();
        } else if (lineIndex < lineStarts.size()) {
            return lineStarts.get(lineIndex);
        } else {
            throw new TooLargeLineIndexException(lineIndex, lineStarts.size());
        }
    }

    public Range<Integer> lineRangeByLineIndex(int lineIndex) throws TooLargeLineIndexException {
        int givenLineStart = lineStartByLineIndex(lineIndex);
        int nextLineStart = lineStartByLineIndex(lineIndex + 1);

        return Range.between(givenLineStart, nextLineStart);
    }

    public int lineIndexByByteIndex(int byteIndex) {
        int lineIndex = Collections.binarySearch(lineStarts, byteIndex);

        if (lineIndex < 0) {
            lineIndex = -(lineIndex + 1) - 1;
        }

        return lineIndex;
    }

    private int columnIndex(Range<Integer> lineRange, int byteIndex) {
        int endIndex = Math.min(byteIndex, Math.min(lineRange.getToInclusive(), source.length()));

        int count = 0;

        for (int i = lineRange.getFromInclusive(); i < endIndex; i++) {
            if (Character.isHighSurrogate(source.charAt(i))) {
                i += 2;
            } else {
                i++;
            }
        }

        return count;
    }

    private int columnNumber(int lineIndex, int offset) throws TooLargeLineIndexException {
        Range<Integer> lineRange = lineRangeByLineIndex(lineIndex);
        int columnIndex = columnIndex(lineRange, offset);

        return columnIndex + 1;
    }

    public ByteLocation byteLocationFromOffset(int offset) throws TooLargeLineIndexException {
        int lineIndex = lineIndexByByteIndex(offset);

        return new ByteLocation(
                lineIndex + 1,
                columnNumber(lineIndex, offset),
                offset
        );
    }
}
