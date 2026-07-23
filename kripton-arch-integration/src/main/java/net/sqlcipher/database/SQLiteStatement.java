package net.sqlcipher.database;

import androidx.sqlite.db.SupportSQLiteStatement;

public class SQLiteStatement extends SQLiteProgram implements SupportSQLiteStatement {

    public void execute() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int executeUpdateDelete() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long executeInsert() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long simpleQueryForLong() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String simpleQueryForString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bindString(int i, String absolutePath) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
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
    public void bindBlob(int index, byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void clearBindings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
