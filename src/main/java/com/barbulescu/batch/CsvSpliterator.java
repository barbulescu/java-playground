package com.barbulescu.batch;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CsvSpliterator extends FixedBatchSpliteratorBase<String[]> {
    private final CSVReader cr;

    CsvSpliterator(CSVReader cr, int batchSize) {
        super(IMMUTABLE | ORDERED | NONNULL, batchSize);
        if (cr == null) throw new NullPointerException("CSVReader is null");
        this.cr = cr;
    }

    public CsvSpliterator(CSVReader cr) {
        this(cr, 128);
    }

    @Override
    public boolean tryAdvance(Consumer<? super String[]> action) {
        if (action == null) throw new NullPointerException();
        try {
            final String[] row = cr.readNext();
            if (row == null) return false;
            action.accept(row);
            return true;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void forEachRemaining(Consumer<? super String[]> action) {
        if (action == null) throw new NullPointerException();
        try {
            for (String[] row; (row = cr.readNext()) != null; ) action.accept(row);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String[]> csvStream(InputStream in) {
        final CSVReader cr = new CSVReader(new InputStreamReader(in));
        return StreamSupport.stream(new CsvSpliterator(cr), false)
                .onClose(cr::close);
    }

}
