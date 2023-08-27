package main.java.org.quantumatic.amethyst.ast;

import main.java.org.quantumatic.amethyst.common.location.Span;

import java.util.List;

public record ArrayLiteralExpression(Span span, List<Expression> elements) implements Expression {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Span getSpan() {
        return span;
    }
}
