package main.java.org.quantumatic.amethyst.lexer;

import java.util.PrimitiveIterator;

public class Lexer {
    private final String filename;
    private final PrimitiveIterator.OfInt contents;
    private int offset = 0;

    public Lexer(String filename, String contents) {
        this.filename = filename;
        this.contents = contents.codePoints().iterator();
    }

}
