package com.barbulescu.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

import static java.util.Collections.unmodifiableList;

public class BatchSpliterator<T> implements Spliterator<List<T>> {
    private final Spliterator<T> delegate;
    private final int batchSize;

    public BatchSpliterator(Spliterator<T> delegate, int batchSize) {
        this.delegate = delegate;
        this.batchSize = batchSize;
    }

    @Override
    public boolean tryAdvance(Consumer<? super List<T>> action) {
        ArrayList<T> buffer = new ArrayList<>();

        for (int i = 0; i < batchSize; i++) {
            boolean advance = delegate.tryAdvance(buffer::add);
            if (!advance) {
                if (!buffer.isEmpty()) {
                    action.accept(unmodifiableList(buffer));
                }
                return false;
            }
        }

        action.accept(unmodifiableList(buffer));
        return true;
    }

    @Override
    public Spliterator<List<T>> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return delegate.estimateSize() / batchSize;
    }

    @Override
    public int characteristics() {
        return IMMUTABLE | ORDERED | NONNULL;
    }

}
