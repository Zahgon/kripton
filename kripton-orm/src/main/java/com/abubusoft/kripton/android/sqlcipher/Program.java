/**
 * Copyright (c) 2017-2018 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain	a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.abubusoft.kripton.android.sqlcipher;

import net.sqlcipher.database.SQLiteProgram;
import androidx.sqlite.db.SupportSQLiteProgram;

/**
 * SupportSQLiteProgram implementation that wraps SQLCipher for Android's
 * implementation
 */
class Program implements SupportSQLiteProgram {

    private final SQLiteProgram delegate;

    Program(SQLiteProgram delegate) {
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
