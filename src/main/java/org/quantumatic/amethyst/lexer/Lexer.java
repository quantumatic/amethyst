package main.java.org.quantumatic.amethyst.lexer;

import main.java.org.quantumatic.amethyst.ast.Token;
import main.java.org.quantumatic.amethyst.ast.TokenKind;
import main.java.org.quantumatic.amethyst.common.Pair;
import main.java.org.quantumatic.amethyst.common.Range;
import main.java.org.quantumatic.amethyst.common.location.Span;
import main.java.org.quantumatic.amethyst.filesystem.InMemoryFile;

import java.util.PrimitiveIterator;
import java.util.function.Function;

public class Lexer {
    private final InMemoryFile file;
    private final PrimitiveIterator.OfInt codePointIterator;
    private int offset = 0;
    private int currentCodePoint;
    private int nextCodePoint;

    public Lexer(String filepath, String contents) {
        this.file = new InMemoryFile(filepath, contents);
        this.codePointIterator = file.getSource().codePoints().iterator();
        this.currentCodePoint = codePointIterator.next();
        this.nextCodePoint = codePointIterator.next();
    }

    /**
     * Calculates the number of bytes required to encode a given Unicode code point.
     *
     * @param  codepoint The Unicode code point to be encoded.
     * @return           The number of bytes required to encode the code point.
     */
    private static int bytesInCodePoint(int codepoint) {
        if (codepoint <= 0x7F) {
            return 1;
        } else if (codepoint <= 0x7FF) {
            return 2;
        } else if (codepoint <= 0xFFFF) {
            return 3;
        } else if (codepoint <= 0x10FFFF) {
            return 4;
        } else {
            return 1;
        }
    }

    /**
     * Advances the iterator to the next code point in the contents.
     */
    private void advance() {
        int previous = currentCodePoint;

        currentCodePoint = nextCodePoint;
        nextCodePoint = codePointIterator.hasNext() ? codePointIterator.next() : 0;

        offset += bytesInCodePoint(previous);
    }

    /**
     * Advances the iterator to the next code point twice.
     */
    private void advanceTwice() {
        advance();
        advance();
    }

    /**
     * Returns a span from the given start offset.
     *
     * @param  startOffset    the start offset of the span
     * @return                the generated span
     */
    private Span spanFrom(int startOffset) {
        return new Span(file.getPath(), Range.between(startOffset, offset));
    }

    /**
     * Advances the code points while the given checker function returns true.
     *
     * @param  startOffset  the starting offset for advancing
     * @param  checker      the function used to check if the code points should be advanced
     * @return              the resolved span of the file after advancing
     */
    private String advanceWhile(int startOffset,
                                Function<Pair<Integer, Integer>, Boolean> checker) {
        while (checker.apply(Pair.of(currentCodePoint, nextCodePoint))) {
            advance();
        }

        return file.resolveSpan(spanFrom(startOffset));
    }

    /**
     * Retrieves the next identifier or keyword token.
     *
     * @return  a Token object representing an identifier or keyword
     */
    private Token nextIdentifierOrKeyword() {
        int startOffset = offset;
        String name = advanceWhile(startOffset, pair -> Character.isAlphabetic(pair.getFirst()));

        return new Token(TokenKind.IDENTIFIER, spanFrom(startOffset), name);
    }
}
