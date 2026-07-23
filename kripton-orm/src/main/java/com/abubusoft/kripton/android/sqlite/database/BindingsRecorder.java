package com.abubusoft.kripton.android.sqlite.database;

import android.util.SparseArray;
import androidx.sqlite.db.SupportSQLiteProgram;

class BindingsRecorder implements SupportSQLiteProgram {

    private SparseArray<Object> bindings = new SparseArray<>();

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

    String[] getBindings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
