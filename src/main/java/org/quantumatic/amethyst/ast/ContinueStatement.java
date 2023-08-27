package main.java.org.quantumatic.amethyst.ast;

import main.java.org.quantumatic.amethyst.common.location.Span;

public record ContinueStatement(Span span) implements Statement {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Span getSpan() {
        return span;
    }
}
