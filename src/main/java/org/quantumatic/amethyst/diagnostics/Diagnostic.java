package main.java.org.quantumatic.amethyst.diagnostics;

import main.java.org.quantumatic.amethyst.common.location.Location;

public record Diagnostic(String filepath, Location location, String message) {}
