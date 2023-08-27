package main.java.org.quantumatic.amethyst.ast;

import main.java.org.quantumatic.amethyst.common.Range;
import main.java.org.quantumatic.amethyst.common.location.Span;

public record BinaryExpression(Expression lhs, BinaryOperator operator, Expression rhs) implements Expression {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Span getSpan() {
        return new Span(lhs.getSpan().filepath(),
                        Range.between(lhs.getSpan().range().getFromInclusive(),
                                      rhs.getSpan().range().getToInclusive())
        );
    }
}
