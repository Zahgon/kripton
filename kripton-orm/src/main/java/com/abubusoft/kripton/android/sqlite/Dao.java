/**
 * ****************************************************************************
 *  Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.abubusoft.kripton.android.sqlite;

import com.abubusoft.kripton.android.annotation.BindDao;
import android.content.ContentValues;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * This class become the parent class for every Dao generated. Every Dao have to
 * be defined by an interface with {@link BindDao} annotation.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class Dao implements AutoCloseable {

    /**
     * Instantiates a new dao.
     *
     * @param context the context
     */
    public Dao(SQLContext context) {
        this._context = context;
    }

    /**
     * This attribute is with _ prefix, because it's used on Dao's method and we
     * won't to mix it with method's parameters (that usually does not have _
     * prefix).
     */
    protected SQLContext _context;

    protected SupportSQLiteDatabase getDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValues() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValuesForUpdate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValuesForUpdate(SupportSQLiteStatement compiledStatement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValuesForContentProvider(ContentValues values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected StringBuilder sqlBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onSessionOpened() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onEvent(SQLiteEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The latest event.
     */
    protected SQLiteEvent latestEvent;

    protected SQLiteEvent getLatestEvent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean hasLatestEvent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void clearEvents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onSessionClosed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
