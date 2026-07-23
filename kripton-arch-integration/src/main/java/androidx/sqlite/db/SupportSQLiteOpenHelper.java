/*
 * Copyright (C) 2016 The Android Open Source Project
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
package androidx.sqlite.db;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * An interface to map the behavior of {@link android.database.sqlite.SQLiteOpenHelper}.
 * Note that since that class requires overriding certain methods, support implementation
 * uses {@link Factory#create(Configuration)} to create this and {@link Callback} to implement
 * the methods that should be overridden.
 */
public interface SupportSQLiteOpenHelper extends Closeable {

    /**
     * Return the name of the SQLite database being opened, as given to
     * the constructor. {@code null} indicates an in-memory database.
     */
    @Nullable
    String getDatabaseName();

    /**
     * Enables or disables the use of write-ahead logging for the database.
     *
     * Write-ahead logging cannot be used with read-only databases so the value of
     * this flag is ignored if the database is opened read-only.
     *
     * @param enabled True if write-ahead logging should be enabled, false if it
     *                should be disabled.
     * @see SupportSQLiteDatabase#enableWriteAheadLogging()
     */
    void setWriteAheadLoggingEnabled(boolean enabled);

    /**
     * Create and/or open a database that will be used for reading and writing.
     * The first time this is called, the database will be opened and
     * {@link Callback#onCreate}, {@link Callback#onUpgrade} and/or {@link Callback#onOpen} will be
     * called.
     *
     * <p>Once opened successfully, the database is cached, so you can
     * call this method every time you need to write to the database.
     * (Make sure to call {@link #close} when you no longer need the database.)
     * Errors such as bad permissions or a full disk may cause this method
     * to fail, but future attempts may succeed if the problem is fixed.</p>
     *
     * <p class="caution">Database upgrade may take a long time, you
     * should not call this method from the application main thread, including
     * from {@link android.content.ContentProvider#onCreate ContentProvider.onCreate()}.
     *
     * @return a read/write database object valid until {@link #close} is called
     * @throws SQLiteException if the database cannot be opened for writing
     */
    SupportSQLiteDatabase getWritableDatabase();

    /**
     * Create and/or open a database.  This will be the same object returned by
     * {@link #getWritableDatabase} unless some problem, such as a full disk,
     * requires the database to be opened read-only.  In that case, a read-only
     * database object will be returned.  If the problem is fixed, a future call
     * to {@link #getWritableDatabase} may succeed, in which case the read-only
     * database object will be closed and the read/write object will be returned
     * in the future.
     *
     * <p class="caution">Like {@link #getWritableDatabase}, this method may
     * take a long time to return, so you should not call it from the
     * application main thread, including from
     * {@link android.content.ContentProvider#onCreate ContentProvider.onCreate()}.
     *
     * @return a database object valid until {@link #getWritableDatabase}
     * or {@link #close} is called.
     * @throws SQLiteException if the database cannot be opened
     */
    SupportSQLiteDatabase getReadableDatabase();

    /**
     * Close any open database object.
     */
    @Override
    void close();

    /**
     * Handles various lifecycle events for the SQLite connection, similar to
     * {@link android.database.sqlite.SQLiteOpenHelper}.
     */
    abstract class Callback {

        private static final String TAG = "SupportSQLite";

        /**
         * Version number of the database (starting at 1); if the database is older,
         * {@link SupportSQLiteOpenHelper.Callback#onUpgrade(SupportSQLiteDatabase, int, int)}
         * will be used to upgrade the database; if the database is newer,
         * {@link SupportSQLiteOpenHelper.Callback#onDowngrade(SupportSQLiteDatabase, int, int)}
         * will be used to downgrade the database.
         */
        public final int version;

        /**
         * Creates a new Callback to get database lifecycle events.
         * @param version The version for the database instance. See {@link #version}.
         */
        public Callback(int version) {
            this.version = version;
        }

        public void onConfigure(@NonNull SupportSQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        public abstract void onCreate(@NonNull SupportSQLiteDatabase db);

        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         *
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
         * you can use ALTER TABLE to rename the old table, then create the new table and then
         * populate the new table with the contents of the old table.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        public abstract void onUpgrade(@NonNull SupportSQLiteDatabase db, int oldVersion, int newVersion);

        public void onDowngrade(@NonNull SupportSQLiteDatabase db, int oldVersion, int newVersion) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onCorruption(@NonNull SupportSQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void deleteDatabaseFile(String fileName) {
            if (fileName.equalsIgnoreCase(":memory:") || fileName.trim().length() == 0) {
                return;
            }
            Log.w(TAG, "deleting the database file: " + fileName);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    SQLiteDatabase.deleteDatabase(new File(fileName));
                } else {
                    try {
                        final boolean deleted = new File(fileName).delete();
                        if (!deleted) {
                            Log.e(TAG, "Could not delete the database file " + fileName);
                        }
                    } catch (Exception error) {
                        Log.e(TAG, "error while deleting corrupted database file", error);
                    }
                }
            } catch (Exception e) {
                /* print warning and ignore exception */
                Log.w(TAG, "delete failed: ", e);
            }
        }
    }

    /**
     * The configuration to create an SQLite open helper object using {@link Factory}.
     */
    class Configuration {

        /**
         * Context to use to open or create the database.
         */
        @NonNull
        public final Context context;

        /**
         * Name of the database file, or null for an in-memory database.
         */
        @Nullable
        public final String name;

        /**
         * The callback class to handle creation, upgrade and downgrade.
         */
        @NonNull
        public final SupportSQLiteOpenHelper.Callback callback;

        Configuration(@NonNull Context context, @Nullable String name, @NonNull Callback callback) {
            this.context = context;
            this.name = name;
            this.callback = callback;
        }

        @NonNull
        public static Builder builder(@NonNull Context context) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Builder class for {@link Configuration}.
         */
        public static class Builder {

            Context mContext;

            String mName;

            SupportSQLiteOpenHelper.Callback mCallback;

            @NonNull
            public Configuration build() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            Builder(@NonNull Context context) {
                mContext = context;
            }

            @NonNull
            public Builder name(@Nullable String name) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @NonNull
            public Builder callback(@NonNull Callback callback) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }
    }

    /**
     * Factory class to create instances of {@link SupportSQLiteOpenHelper} using
     * {@link Configuration}.
     */
    interface Factory {

        /**
         * Creates an instance of {@link SupportSQLiteOpenHelper} using the given configuration.
         *
         * @param configuration The configuration to use while creating the open helper.
         *
         * @return A SupportSQLiteOpenHelper which can be used to open a database.
         */
        @NonNull
        SupportSQLiteOpenHelper create(@NonNull Configuration configuration);
    }
}
