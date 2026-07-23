package net.sqlcipher.database;

import android.content.Context;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase.CursorFactory;

public abstract class SQLiteOpenHelper {

    public SQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        throw new RuntimeException("Not implemented");
    }

    public SQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, SQLiteDatabaseHook hook) {
        throw new RuntimeException("Not implemented");
    }

    public SQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, SQLiteDatabaseHook hook, DatabaseErrorHandler errorHandler) {
    }

    public synchronized SQLiteDatabase getWritableDatabase(String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized SQLiteDatabase getWritableDatabase(char[] password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized SQLiteDatabase getWritableDatabase(byte[] password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized SQLiteDatabase getReadableDatabase(String password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized SQLiteDatabase getReadableDatabase(char[] password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized SQLiteDatabase getReadableDatabase(byte[] password) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public synchronized void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getDatabaseName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setWriteAheadLoggingEnabled(boolean enabled) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onConfigure(SQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    public abstract void onCreate(SQLiteDatabase db);

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     *
     * <p>The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     *
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    public void onOpen(SQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
