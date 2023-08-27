package main.java.org.quantumatic.amethyst.ast;

public enum BinaryOperator {
    AMPERSAND("&"),
    ;

    private final String value;

    BinaryOperator(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
