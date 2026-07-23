package com.abubusoft.kripton.android.sqlite;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;

/**
 * Default open helper for standard sqlite databases.
 *
 * @author Francesco Benincasa (xcesco@gmail.com)
 */
public class KriptonSQLiteHelperFactory implements SupportSQLiteOpenHelper.Factory {

    public KriptonSQLiteHelperFactory() {
    }

    public static KriptonSQLiteHelperFactory build() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SupportSQLiteOpenHelper create(Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
