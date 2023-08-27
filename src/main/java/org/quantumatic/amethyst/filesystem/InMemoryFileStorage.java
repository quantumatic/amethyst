package main.java.org.quantumatic.amethyst.filesystem;

import main.java.org.quantumatic.amethyst.common.location.Location;
import main.java.org.quantumatic.amethyst.common.location.Span;

import java.util.HashMap;
import java.util.Optional;

public class InMemoryFileStorage {
    private HashMap<String, InMemoryFile> storage;

    public InMemoryFileStorage() {}

    public InMemoryFileStorage(HashMap<String, InMemoryFile> storage) {
        this.storage = storage;
    }

    public InMemoryFileStorage empty() {
        return new InMemoryFileStorage();
    }

    public void addFile(String path, InMemoryFile file) {
        storage.put(path, file);
    }

    public void addFileIfDoesNotExist(String path, InMemoryFile file) {
        if (!storage.containsKey(path)) {
            addFile(path, file);
        }
    }

    public InMemoryFile resolve(String filepath) {
        return storage.get(filepath);
    }

    public Optional<InMemoryFile> checkedResolve(String filepath) {
        if (!contains(filepath)) {
            return Optional.empty();
        } else {
            return Optional.of(resolve(filepath));
        }
    }

    public boolean contains(String filepath) {
        return storage.containsKey(filepath);
    }

    public void removeFile(String filepath) {
        storage.remove(filepath);
    }

    public String resolveSpan(Span span) {
        return storage.get(span.filepath()).resolveSpan(span);
    }

    public String resolveLocation(Location location) {
        return storage.get(location.filepath()).resolveLocation(location);
    }

    public Location locationFromSpan(Span span) throws TooLargeLineIndexException {
        InMemoryFile file = storage.get(span.filepath());

        return new Location(
                file.getPath(),
                file.byteLocationFromOffset(span.range().getFromInclusive()),
                file.byteLocationFromOffset(span.range().getToInclusive())
        );
    }
}
