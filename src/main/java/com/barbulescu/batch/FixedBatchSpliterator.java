package com.barbulescu.batch;
import static java.util.stream.StreamSupport.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class FixedBatchSpliterator<T> extends FixedBatchSpliteratorBase<T> {
    private final Spliterator<T> spliterator;

    private FixedBatchSpliterator(Spliterator<T> toWrap, int batchSize) {
        super(toWrap.characteristics(), batchSize, toWrap.estimateSize());
        this.spliterator = toWrap;
    }

    public static <T> Stream<T> withBatchSize(Stream<T> in, int batchSize) {
        FixedBatchSpliterator<T> spliterator = new FixedBatchSpliterator<>(in.spliterator(), batchSize);
        return stream(spliterator, true);
    }

    @Override public boolean tryAdvance(Consumer<? super T> action) {
        return spliterator.tryAdvance(action);
    }
    @Override public void forEachRemaining(Consumer<? super T> action) {
        spliterator.forEachRemaining(action);
    }
}
