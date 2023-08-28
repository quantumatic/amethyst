package main.java.org.quantumatic.amethyst.ast;

import main.java.org.quantumatic.amethyst.common.location.Span;
import main.java.org.quantumatic.amethyst.common.location.Spanned;

public record Token(TokenKind kind, Span span, String value) implements Spanned {
    @Override
    public Span getSpan() {
        return this.span;
    }
}
