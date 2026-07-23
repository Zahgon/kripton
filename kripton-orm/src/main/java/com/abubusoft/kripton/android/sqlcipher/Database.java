/**
 * Copyright (c) 2017 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain	a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * Covered in detail in the book _The Busy Coder's Guide to Android Development_
 * https://commonsware.com/Android
 */
package com.abubusoft.kripton.android.sqlcipher;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Pair;
import net.sqlcipher.database.SQLiteCursor;
import net.sqlcipher.database.SQLiteCursorDriver;
import net.sqlcipher.database.SQLiteQuery;
import java.util.List;
import java.util.Locale;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * A SupportSQLiteDatabase implementation that delegates to a SQLCipher for
 * Android implementation of SQLiteDatabase
 */
class Database implements SupportSQLiteDatabase {

    private static final String[] CONFLICT_VALUES = new String[] { "", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE " };

    private final net.sqlcipher.database.SQLiteDatabase safeDb;

    Database(net.sqlcipher.database.SQLiteDatabase safeDb) {
        this.safeDb = safeDb;
    }

    @Override
    public SupportSQLiteStatement compileStatement(String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransactionNonExclusive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransactionWithListener(SQLiteTransactionListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void endTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setTransactionSuccessful() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean inTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isDbLockedByCurrentThread() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean yieldIfContendedSafely() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setVersion(int version) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getMaximumSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long setMaximumSize(long numBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long getPageSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setPageSize(long numBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Cursor query(String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Cursor query(String sql, Object[] selectionArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Cursor query(final SupportSQLiteQuery supportQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Cursor query(final SupportSQLiteQuery supportQuery, CancellationSignal signal) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long insert(String table, int conflictAlgorithm, ContentValues values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int delete(String table, String whereClause, Object[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int update(String table, int conflictAlgorithm, ContentValues values, String whereClause, Object[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void execSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isReadOnly() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean needUpgrade(int newVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setLocale(Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setMaxSqlCacheSize(int cacheSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setForeignKeyConstraintsEnabled(boolean enable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean enableWriteAheadLogging() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void disableWriteAheadLogging() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isWriteAheadLoggingEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<Pair<String, String>> getAttachedDbs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isDatabaseIntegrityOk() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void rekey(char[] passphrase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void rekey(Editable editor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
