package main.java.org.quantumatic.amethyst.ast;

import main.java.org.quantumatic.amethyst.common.location.Spanned;

public interface Node extends Spanned {
    void accept(Visitor visitor);
}
