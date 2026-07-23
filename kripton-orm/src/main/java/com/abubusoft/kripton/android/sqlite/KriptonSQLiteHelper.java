package com.abubusoft.kripton.android.sqlite;

import com.abubusoft.kripton.android.sqlite.database.KriptonSQLiteDatabaseWrapperImpl;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

public class KriptonSQLiteHelper implements SupportSQLiteOpenHelper {

    final KriptonSQLiteDatabaseWrapperImpl[] dbRef = new KriptonSQLiteDatabaseWrapperImpl[1];

    private SQLiteOpenHelper delegate;

    public KriptonSQLiteHelper(Configuration configuration) {
        delegate = new SQLiteOpenHelper(configuration.context, configuration.name, null, configuration.callback.version, new DatabaseErrorHandler() {

            @Override
            public void onCorruption(SQLiteDatabase db) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }) {

            @Override
            public void onConfigure(SQLiteDatabase database) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onCreate(SQLiteDatabase database) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
    }

    @Override
    public String getDatabaseName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setWriteAheadLoggingEnabled(boolean enabled) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SupportSQLiteDatabase getWritableDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public SupportSQLiteDatabase getReadableDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    synchronized SupportSQLiteDatabase getWrappedDb(SQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
