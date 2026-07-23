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

import net.sqlcipher.database.SQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * SupportSQLiteStatement implementation that wraps SQLCipher for Android's
 * SQLiteStatement
 */
class Statement extends Program implements SupportSQLiteStatement {

    private final SQLiteStatement safeStatement;

    Statement(SQLiteStatement safeStatement) {
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
