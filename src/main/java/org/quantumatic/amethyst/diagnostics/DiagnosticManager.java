package main.java.org.quantumatic.amethyst.diagnostics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DiagnosticManager {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private List<Diagnostic> diagnostics = new ArrayList<>();

    public DiagnosticManager() {}

    public DiagnosticManager(List<Diagnostic> diagnostics) {
        this.diagnostics = diagnostics;
    }

    public void add(Diagnostic diagnostic) {
        lock.writeLock().lock();

        try {
            diagnostics.add(diagnostic);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Diagnostic> get() {
        lock.readLock().lock();

        try {
            return diagnostics;
        } finally {
            lock.readLock().unlock();
        }
    }
}
