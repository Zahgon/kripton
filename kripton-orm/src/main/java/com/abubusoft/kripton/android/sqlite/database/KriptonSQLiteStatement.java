package com.abubusoft.kripton.android.sqlite.database;

import android.database.sqlite.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * SupportSQLiteStatement implementation that wraps SQLite for Android's
 * SQLiteStatement
 */
class KriptonSQLiteStatement extends KriptonSQLiteProgram implements SupportSQLiteStatement {

    private final SQLiteStatement safeStatement;

    KriptonSQLiteStatement(SQLiteStatement safeStatement) {
        super(safeStatement);
        this.safeStatement = safeStatement;
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int executeUpdateDelete() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long executeInsert() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long simpleQueryForLong() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String simpleQueryForString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
