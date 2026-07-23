/*
 * Copyright (C) 2017 The Android Open Source Project
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

import java.util.regex.Pattern;

/**
 * A simple query builder to create SQL SELECT queries.
 */
public final class SupportSQLiteQueryBuilder {

    private static final Pattern sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");

    private boolean mDistinct = false;

    private final String mTable;

    private String[] mColumns = null;

    private String mSelection;

    private Object[] mBindArgs;

    private String mGroupBy = null;

    private String mHaving = null;

    private String mOrderBy = null;

    private String mLimit = null;

    public static SupportSQLiteQueryBuilder builder(String tableName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private SupportSQLiteQueryBuilder(String table) {
        mTable = table;
    }

    public SupportSQLiteQueryBuilder distinct() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder columns(String[] columns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder selection(String selection, Object[] bindArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder groupBy(String groupBy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder having(String having) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder orderBy(String orderBy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQueryBuilder limit(String limit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteQuery create() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void appendClause(StringBuilder s, String name, String clause) {
        if (!isEmpty(clause)) {
            s.append(name);
            s.append(clause);
        }
    }

    /**
     * Add the names that are non-null in columns to s, separating
     * them with commas.
     */
    private static void appendColumns(StringBuilder s, String[] columns) {
        int n = columns.length;
        for (int i = 0; i < n; i++) {
            String column = columns[i];
            if (i > 0) {
                s.append(", ");
            }
            s.append(column);
        }
        s.append(' ');
    }

    private static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }
}
