package net.sqlcipher.database;

import androidx.sqlite.db.SupportSQLiteProgram;

public class SQLiteProgram implements SupportSQLiteProgram {

    public void bindNull(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bindLong(int index, long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bindDouble(int index, double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bindString(int index, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bindBlob(int index, byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clearBindings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
