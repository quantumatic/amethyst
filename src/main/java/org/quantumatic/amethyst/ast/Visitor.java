package main.java.org.quantumatic.amethyst.ast;

public interface Visitor {
    default void visit(ArrayLiteralExpression expression) {
        for (Expression element: expression.elements()) {
            visit(element);
        }
    }

    default void visit(BinaryExpression expression) {
        visit(expression.lhs());
        visit(expression.rhs());
    }

    default void visit(Expression expression) {
        if (expression instanceof BinaryExpression) {
            visit((BinaryExpression) expression);
        } else if (expression instanceof ArrayLiteralExpression) {
            visit((ArrayLiteralExpression) expression);
        }
    }

    default void visit(BreakStatement statement) {}
    default void visit(ContinueStatement statement) {}

    default void visit(Statement statement) {
        if (statement instanceof BreakStatement) {
            visit((BreakStatement) statement);
        } else if (statement instanceof ContinueStatement) {
            visit((ContinueStatement) statement);
        }
    }
}
