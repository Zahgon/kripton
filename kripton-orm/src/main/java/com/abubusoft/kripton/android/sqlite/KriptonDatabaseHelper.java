/**
 * ****************************************************************************
 *  Copyright 2016-2019 Francesco Benincasa (info@abubusoft.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * ****************************************************************************
 */
package com.abubusoft.kripton.android.sqlite;

import java.io.IOException;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * This class wrap the database call to do some operations.
 */
public abstract class KriptonDatabaseHelper {

    public static SupportSQLiteStatement compile(SQLContext context, String sql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long insert(SQLContext context, String sql, KriptonContentValues contentValues) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long insert(SupportSQLiteStatement ps, KriptonContentValues contentValues) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int updateDelete(SupportSQLiteStatement ps, KriptonContentValues contentValues) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int updateDelete(SQLContext context, String sql, KriptonContentValues contentValues) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
