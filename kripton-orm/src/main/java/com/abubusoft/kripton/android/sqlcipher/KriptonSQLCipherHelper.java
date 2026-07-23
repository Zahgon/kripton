/*
 * Copyright (C) 2016 The Android Open Source Project
 * Modifications Copyright (c) 2017 CommonsWare, LLC
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
package com.abubusoft.kripton.android.sqlcipher;

import com.abubusoft.kripton.android.Logger;
import android.content.Context;
import android.os.Build;
import net.sqlcipher.DatabaseErrorHandler;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteDatabaseHook;
import net.sqlcipher.database.SQLiteException;
import net.sqlcipher.database.SQLiteOpenHelper;
import androidx.annotation.RequiresApi;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * SupportSQLiteOpenHelper implementation that works with SQLCipher for Android
 */
class KriptonSQLCipherHelper implements SupportSQLiteOpenHelper {

    private final OpenHelper delegate;

    private final byte[] passphrase;

    private final boolean clearPassphrase;

    private final boolean requiredPassphrase;

    KriptonSQLCipherHelper(Context context, String name, Callback callback, byte[] passphrase, KriptonSQLCipherHelperFactory.Options options) {
        SQLiteDatabase.loadLibs(context);
        clearPassphrase = options.clearPassphrase;
        delegate = createDelegate(context, name, callback, options);
        this.passphrase = passphrase;
        this.requiredPassphrase = options.requiredPassphrase;
    }

    private OpenHelper createDelegate(Context context, String name, final Callback callback, KriptonSQLCipherHelperFactory.Options options) {
        final Database[] dbRef = new Database[1];
        return (new OpenHelper(context, name, dbRef, callback, options));
    }

    @Override
    synchronized public String getDatabaseName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    synchronized public void setWriteAheadLoggingEnabled(boolean enabled) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    synchronized public SupportSQLiteDatabase getWritableDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    synchronized public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static class OpenHelper extends SQLiteOpenHelper {

        private final Database[] dbRef;

        private volatile Callback callback;

        private volatile boolean migrated;

        OpenHelper(Context context, String name, Database[] dbRef, Callback callback, KriptonSQLCipherHelperFactory.Options options) {
            super(context, name, null, callback.version, new SQLiteDatabaseHook() {

                @Override
                public void preKey(SQLiteDatabase database) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }

                @Override
                public void postKey(SQLiteDatabase database) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            }, new DatabaseErrorHandler() {

                @Override
                public void onCorruption(SQLiteDatabase dbObj) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
            this.dbRef = dbRef;
            this.callback = callback;
        }

        synchronized SupportSQLiteDatabase getWritableSupportDatabase(byte[] passphrase) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        synchronized Database getWrappedDb(SQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onConfigure(SQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public synchronized void close() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
