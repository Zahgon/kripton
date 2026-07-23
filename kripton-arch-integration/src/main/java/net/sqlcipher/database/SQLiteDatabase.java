package net.sqlcipher.database;

/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;
import android.content.ContentValues;
import android.content.Context;
import android.os.CancellationSignal;
import android.util.Pair;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteQuery;
import net.sqlcipher.Cursor;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.SQLException;

public class SQLiteDatabase extends SQLiteClosable implements SupportSQLiteDatabase {

    private static final String TAG = "Database";

    private static final int EVENT_DB_OPERATION = 52000;

    private static final int EVENT_DB_CORRUPT = 75004;

    private static final String KEY_ENCODING = "UTF-8";

    public static final String SQLCIPHER_ANDROID_VERSION = "dummy";

    // Stores reference to all databases opened in the current process.
    // (The referent Object is not used at this time.)
    // INVARIANT: Guarded by sActiveDatabases.
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases = new WeakHashMap<SQLiteDatabase, Object>();

    public int status(int operation, boolean reset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void changePassword(String password) throws SQLiteException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void changePassword(char[] password) throws SQLiteException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Implement this interface to provide custom strategy for loading jni
     * libraries.
     */
    public interface LibraryLoader {

        /**
         * Load jni libraries by given names. Straightforward implementation
         * will be calling {@link System#loadLibrary(String name)} for every
         * provided library name.
         *
         * @param libNames
         *            library names that sqlcipher need to load
         */
        void loadLibraries(String... libNames);
    }

    public static synchronized void loadLibs(Context context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static synchronized void loadLibs(Context context, File workingDir) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static synchronized void loadLibs(Context context, LibraryLoader libraryLoader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static synchronized void loadLibs(Context context, File workingDir, LibraryLoader libraryLoader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Algorithms used in ON CONFLICT clause
     * http://www.sqlite.org/lang_conflict.html
     */
    /**
     * When a constraint violation occurs, an immediate ROLLBACK occurs, thus
     * ending the current transaction, and the command aborts with a return code
     * of SQLITE_CONSTRAINT. If no transaction is active (other than the implied
     * transaction that is created on every command) then this algorithm works
     * the same as ABORT.
     */
    public static final int CONFLICT_ROLLBACK = 1;

    /**
     * When a constraint violation occurs,no ROLLBACK is executed so changes
     * from prior commands within the same transaction are preserved. This is
     * the default behavior.
     */
    public static final int CONFLICT_ABORT = 2;

    /**
     * When a constraint violation occurs, the command aborts with a return code
     * SQLITE_CONSTRAINT. But any changes to the database that the command made
     * prior to encountering the constraint violation are preserved and are not
     * backed out.
     */
    public static final int CONFLICT_FAIL = 3;

    /**
     * When a constraint violation occurs, the one row that contains the
     * constraint violation is not inserted or changed. But the command
     * continues executing normally. Other rows before and after the row that
     * contained the constraint violation continue to be inserted or updated
     * normally. No error is returned.
     */
    public static final int CONFLICT_IGNORE = 4;

    /**
     * When a UNIQUE constraint violation occurs, the pre-existing rows that are
     * causing the constraint violation are removed prior to inserting or
     * updating the current row. Thus the insert or update always occurs. The
     * command continues executing normally. No error is returned. If a NOT NULL
     * constraint violation occurs, the NULL value is replaced by the default
     * value for that column. If the column has no default value, then the ABORT
     * algorithm is used. If a CHECK constraint violation occurs then the IGNORE
     * algorithm is used. When this conflict resolution strategy deletes rows in
     * order to satisfy a constraint, it does not invoke delete triggers on
     * those rows. This behavior might change in a future release.
     */
    public static final int CONFLICT_REPLACE = 5;

    /**
     * use the following when no conflict action is specified.
     */
    public static final int CONFLICT_NONE = 0;

    private static final String[] CONFLICT_VALUES = new String[] { "", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE " };

    /**
     * Maximum Length Of A LIKE Or GLOB Pattern The pattern matching algorithm
     * used in the default LIKE and GLOB implementation of SQLite can exhibit
     * O(N^2) performance (where N is the number of characters in the pattern)
     * for certain pathological cases. To avoid denial-of-service attacks the
     * length of the LIKE or GLOB pattern is limited to
     * SQLITE_MAX_LIKE_PATTERN_LENGTH bytes. The default value of this limit is
     * 50000. A modern workstation can evaluate even a pathological LIKE or GLOB
     * pattern of 50000 bytes relatively quickly. The denial of service problem
     * only comes into play when the pattern length gets into millions of bytes.
     * Nevertheless, since most useful LIKE or GLOB patterns are at most a few
     * dozen bytes in length, paranoid application developers may want to reduce
     * this parameter to something in the range of a few hundred if they know
     * that external users are able to generate arbitrary patterns.
     */
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;

    /**
     * Flag for {@link #openDatabase} to open the database for reading and
     * writing. If the disk is full, this may fail even before you actually
     * write anything.
     *
     * {@more} Note that the value of this flag is 0, so it is the default.
     */
    // update native code
    public static final int OPEN_READWRITE = 0x00000000;

    // if changing
    /**
     * Flag for {@link #openDatabase} to open the database for reading only.
     * This is the only reliable way to open a database if the disk may be full.
     */
    // update native code if
    public static final int OPEN_READONLY = 0x00000001;

    // changing
    // update native code
    private static final int OPEN_READ_MASK = 0x00000001;

    // if changing
    /**
     * Flag for {@link #openDatabase} to open the database without support for
     * localized collators.
     *
     * {@more} This causes the collator <code>LOCALIZED</code> not to be
     * created. You must be consistent when using this flag to use the setting
     * the database was created with. If this is set, {@link #setLocale} will do
     * nothing.
     */
    // update
    public static final int NO_LOCALIZED_COLLATORS = 0x00000010;

    // native
    // code if
    // changing
    /**
     * Flag for {@link #openDatabase} to create the database file if it does not
     * already exist.
     */
    // update native
    public static final int CREATE_IF_NECESSARY = 0x10000000;

    // code if
    // changing
    /**
     * SQLite memory database name
     */
    public static final String MEMORY = ":memory:";

    public static final int MAX_SQL_CACHE_SIZE = 250;

    /**
     * Attempts to release memory that SQLite holds but does not require to
     * operate properly. Typically this memory will come from the page cache.
     *
     * @return the number of bytes actually released
     */
    static public native int releaseMemory();

    public void setLockingEnabled(boolean lockingEnabled) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If set then the SQLiteDatabase is made thread-safe by using locks around
     * critical sections
     */
    private boolean mLockingEnabled = true;

    /**
     * Releases the database lock. This is a no-op if mLockingEnabled is false.
     *
     * @see #unlock()
     */
    /* package */
    void unlock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Releases the database lock.
     *
     * @see #unlockForced()
     */
    private void unlockForced() {
        throw (new RuntimeException("Not implements"));
    }

    public boolean isDatabaseIntegrityOk() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<Pair<String, String>> getAttachedDbs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean enableWriteAheadLogging() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void disableWriteAheadLogging() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isWriteAheadLoggingEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setForeignKeyConstraintsEnabled(boolean enable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void beginTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void beginTransactionWithListener(SQLiteTransactionListener transactionListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void beginTransactionNonExclusive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener transactionListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void endTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setTransactionSuccessful() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean inTransaction() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isDbLockedByCurrentThread() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isDbLockedByOtherThreads() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Temporarily end the transaction to let other threads run. The transaction
     * is assumed to be successful so far. Do not call setTransactionSuccessful
     * before calling this. When this returns a new transaction will have been
     * created but not marked as successful.
     *
     * @return true if the transaction was yielded
     *
     * @deprecated if the db is locked more than once (becuase of nested
     *             transactions) then the lock will not be yielded. Use
     *             yieldIfContendedSafely instead.
     */
    @Deprecated
    public boolean yieldIfContended() {
        throw (new RuntimeException("Not implements"));
    }

    public boolean yieldIfContendedSafely() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean yieldIfContendedSafely(long sleepAfterYieldDelay) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map<String, String> getSyncedTables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Used to allow returning sub-classes of {@link Cursor} when calling query.
     */
    public interface CursorFactory {

        /**
         * See
         * {@link SQLiteCursor#SQLiteCursor(SQLiteDatabase, SQLiteCursorDriver, String, SQLiteQuery)}.
         */
        public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query);
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, String password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openDatabase(String path, byte[] password, CursorFactory factory, int flags, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory, SQLiteDatabaseHook databaseHook, DatabaseErrorHandler errorHandler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, String password, CursorFactory factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, String password, CursorFactory factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, char[] password, CursorFactory factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase openOrCreateDatabase(String path, byte[] password, CursorFactory factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase create(CursorFactory factory, String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLiteDatabase create(CursorFactory factory, char[] password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setVersion(int version) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getMaximumSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long setMaximumSize(long numBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getPageSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setPageSize(long numBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void markTableSyncable(String table, String deletedTable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void markTableSyncable(String table, String foreignKey, String updateTable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String findEditTable(String tables) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SQLiteStatement compileStatement(String sql) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor rawQuery(String sql, Object[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String sql, String[] selectionArgs, String editTable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Cursor rawQuery(String sql, String[] selectionArgs, int initialRead, int maxRead) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int delete(String table, String whereClause, Object[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void execSQL(String sql) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void rawExecSQL(String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Public constructor which attempts to open the database. See
     * {@link #create} and {@link #openDatabase}.
     *
     * <p>
     * Sets the locale of the database to the system's current locale. Call
     * {@link #setLocale} if you would like something else.
     * </p>
     *
     * @param path
     *            The full path to the database
     * @param password
     *            to use to open and/or create a database file (char array)
     * @param factory
     *            The factory to use when creating cursors, may be NULL.
     * @param flags
     *            0 or {@link #NO_LOCALIZED_COLLATORS}. If the database file
     *            already exists, mFlags will be updated appropriately.
     *
     * @throws SQLiteException
     *             if the database cannot be opened
     * @throws IllegalArgumentException
     *             if the database path is null
     */
    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags) {
        throw (new RuntimeException("Not implements"));
    }

    /**
     * Public constructor which attempts to open the database. See
     * {@link #create} and {@link #openDatabase}.
     *
     * <p>
     * Sets the locale of the database to the system's current locale. Call
     * {@link #setLocale} if you would like something else.
     * </p>
     *
     * @param path
     *            The full path to the database
     * @param password
     *            to use to open and/or create a database file (char array)
     * @param factory
     *            The factory to use when creating cursors, may be NULL.
     * @param flags
     *            0 or {@link #NO_LOCALIZED_COLLATORS}. If the database file
     *            already exists, mFlags will be updated appropriately.
     * @param databaseHook
     *            to run on pre/post key events
     *
     * @throws SQLiteException
     *             if the database cannot be opened
     * @throws IllegalArgumentException
     *             if the database path is null
     */
    public SQLiteDatabase(String path, char[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        throw (new RuntimeException("Not implements"));
    }

    public SQLiteDatabase(String path, byte[] password, CursorFactory factory, int flags, SQLiteDatabaseHook databaseHook) {
        throw (new RuntimeException("Not implements"));
    }

    public boolean isReadOnly() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean needUpgrade(int newVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public final String getPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setLocale(Locale locale) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isInCompiledSqlCache(String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void purgeFromCompiledSqlCache(String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void resetCompiledSqlCache() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized int getMaxSqlCacheSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized void setMaxSqlCacheSize(int cacheSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] getBytes(char[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] getChars(byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* begin SQLiteSupportDatabase methods */
    @Override
    public android.database.Cursor query(String query) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public android.database.Cursor query(String query, Object[] bindArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public android.database.Cursor query(SupportSQLiteQuery query) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public android.database.Cursor query(final SupportSQLiteQuery supportQuery, CancellationSignal cancellationSignal) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public long insert(String table, int conflictAlgorithm, ContentValues values) throws android.database.SQLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int update(String table, int conflictAlgorithm, ContentValues values, String whereClause, Object[] whereArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransactionWithListener(final android.database.sqlite.SQLiteTransactionListener transactionListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void beginTransactionWithListenerNonExclusive(final android.database.sqlite.SQLiteTransactionListener transactionListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the root directory to search for the ICU data file
     */
    public static native void setICURoot(String path);

    @Override
    protected void onAllReferencesReleased() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
