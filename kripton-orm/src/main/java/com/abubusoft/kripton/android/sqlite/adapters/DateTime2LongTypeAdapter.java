package com.abubusoft.kripton.android.sqlite.adapters;

import java.util.Date;
import com.abubusoft.kripton.android.SqlTypeAdapter;

/**
 * <p>Convert a Long into a util.Date and vice-versa.</p>
 *
 * @author Francesco Benincasa
 */
public class DateTime2LongTypeAdapter implements SqlTypeAdapter<Date, Long> {

    @Override
    public Date toJava(Long dataValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Long toData(Date javaValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString(Date javaValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
