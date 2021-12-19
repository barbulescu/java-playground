package com.barbulescu.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;

public class PairSpliterator implements Spliterator<Pair> {
    private final Spliterator<String> delegate;

    public PairSpliterator(Spliterator<String> delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Pair> action) {
        var builder = Pair.builder();
        if (delegate.tryAdvance(builder::name)
                && delegate.tryAdvance(builder::position)) {
            action.accept(builder.build());
            return true;
        }

        return false;
    }

    @Override
    public Spliterator<Pair> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return delegate.estimateSize() / 2;
    }

    @Override
    public int characteristics() {
        return delegate.characteristics();
    }
}
