package com.abubusoft.kripton.android.sqlite.database;

import android.database.sqlite.SQLiteProgram;
import androidx.sqlite.db.SupportSQLiteProgram;

/**
 * SupportSQLiteProgram implementation that wraps SQLite for Android's
 * implementation
 */
class KriptonSQLiteProgram implements SupportSQLiteProgram {

    private final SQLiteProgram delegate;

    KriptonSQLiteProgram(SQLiteProgram delegate) {
        this.delegate = delegate;
    }

    @Override
    public void bindNull(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void bindLong(int index, long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void bindDouble(int index, double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void bindString(int index, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void bindBlob(int index, byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void clearBindings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
