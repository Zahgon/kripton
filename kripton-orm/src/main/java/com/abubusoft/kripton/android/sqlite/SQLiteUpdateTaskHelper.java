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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.sqlite.commons.IOUtils;
import com.abubusoft.kripton.common.StringUtils;
import android.content.Context;
import android.database.Cursor;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * SQLiteUpdateTask Helper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class SQLiteUpdateTaskHelper {

    /**
     * The Enum QueryType.
     */
    public enum QueryType {

        /**
         * The table.
         */
        TABLE,
        /**
         * The index.
         */
        INDEX
    }

    /**
     * The listener interface for receiving onResult events. The class that is
     * interested in processing a onResult event implements this interface, and
     * the object created with that class is registered with a component using
     * the component's <code>addOnResultListener</code> method. When the
     * onResult event occurs, that object's appropriate method is invoked.
     */
    public interface OnResultListener {

        /**
         * On row.
         *
         * @param db
         *            the db
         * @param name
         *            the name
         * @param sql
         *            the sql
         */
        void onRow(SupportSQLiteDatabase db, String name, String sql);
    }

    static void query(SupportSQLiteDatabase db, String conditions, QueryType type, OnResultListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Drop all entity of particular type (table or index). If prefix is
     * specified, the drop operation is applied only to entity with prefix.
     *
     * @param db
     *            the db
     * @param type
     *            the type
     * @param prefix
     *            the prefix
     */
    private static void drop(SupportSQLiteDatabase db, final QueryType type, String prefix) {
        String dropSQL = StringUtils.hasText(prefix) ? "name like '" + prefix + "' || '%'" : null;
        query(db, dropSQL, type, new OnResultListener() {

            @Override
            public void onRow(SupportSQLiteDatabase db, String name, String sql) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    public static Map<String, String> getAllTables(SupportSQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void renameTablesWithPrefix(SupportSQLiteDatabase db, final String prefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void dropTablesWithPrefix(SupportSQLiteDatabase db, String prefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void dropTablesAndIndices(SupportSQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Map<String, String> getAllIndexes(SupportSQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void executeSQL(final SupportSQLiteDatabase database, Context context, int rawResourceId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> readSQLFromFile(String fileName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> readSQLFromFile(InputStream fileInputStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void executeSQL(final SupportSQLiteDatabase database, InputStream fileInputStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void executeSQL(final SupportSQLiteDatabase database, List<String> commands) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void executeSQL(final SupportSQLiteDatabase database, String command) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
