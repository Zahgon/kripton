/**
 * Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abubusoft.kripton.android.sqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration.Builder;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.abubusoft.kripton.android.KriptonLibrary;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * Base class for data source
 * </p>
 * .
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class AbstractDataSource implements AutoCloseable {

    /**
     * Interface for database operations.
     *
     * @param <E> the element type
     */
    public interface AbstractExecutable<E extends BindDaoFactory> {

        /**
         * Execute transation. Method need to return
         * {@link TransactionResult#COMMIT} to commit results or
         * {@link TransactionResult#ROLLBACK} to rollback. If exception is
         * thrown, a rollback will be done.
         *
         * @param daoFactory the dao factory
         * @return the transaction result
         */
        TransactionResult onExecute(@NonNull E daoFactory);
    }

    /**
     * The listener interface for receiving onError events. The class that is
     * interested in processing a onError event implements this interface, and
     * the object created with that class is registered with a component using
     * the component's <code>addOnErrorListener</code> method. When the onError
     * event occurs, that object's appropriate method is invoked.
     */
    public interface OnErrorListener {

        /**
         * Manages error situations.
         *
         * @param e exception
         */
        void onError(@NonNull Throwable e);
    }

    /**
     * The Enum TypeStatus.
     */
    public enum TypeStatus {

        /**
         * The closed.
         */
        CLOSED,
        /**
         * The read and write opened.
         */
        READ_AND_WRITE_OPENED,
        /**
         * The read only opened.
         */
        READ_ONLY_OPENED
    }

    /**
     * The context.
     */
    protected SQLContextImpl context;

    /**
     * database instance.
     */
    SupportSQLiteDatabase database;

    /**
     * <p>
     * True if dataSource is just created
     * </p>
     * .
     */
    protected boolean justCreated = false;

    /**
     * The lock access.
     */
    private final ReentrantReadWriteLock lockAccess = new ReentrantReadWriteLock();

    /**
     * The lock db.
     */
    private final ReentrantLock lockDb = new ReentrantLock();

    /**
     * The lock read access.
     */
    private final Lock lockReadAccess = lockAccess.readLock();

    /**
     * The lock read write access.
     */
    private final Lock lockReadWriteAccess = lockAccess.writeLock();

    /**
     * The log enabled.
     */
    protected boolean logEnabled;

    /**
     * <p>
     * file name used to save database,
     * </p>
     * .
     */
    protected final String name;

    /**
     * The on error listener.
     */
    protected OnErrorListener onErrorListener = (Throwable e) -> {
        throw (new KriptonRuntimeException(e));
    };

    /**
     * The open counter.
     */
    private final AtomicInteger openCounter = new AtomicInteger();

    /**
     * The options.
     */
    protected DataSourceOptions options;

    /**
     * The sqlite helper.
     */
    protected SupportSQLiteOpenHelper sqliteHelper;

    /**
     * The status. Do not replace with Initial, it does not work on Android.
     */
    protected ThreadLocal<TypeStatus> status = new ThreadLocal<TypeStatus>() {

        @Override
        protected TypeStatus initialValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * <p>
     * database version
     * </p>
     * .
     */
    protected int version;

    /**
     * if true, database was update during this application run.
     */
    protected boolean versionChanged;

    /**
     * Instantiates a new abstract data source.
     *
     * @param name    the name
     * @param version the version
     * @param options the options
     */
    protected AbstractDataSource(String name, int version, DataSourceOptions options) {
        DataSourceOptions optionsValue = (options != null) ? options : DataSourceOptions.builder().build();
        if (optionsValue.inMemory) {
            this.name = null;
        } else if (StringUtils.hasText(optionsValue.name)) {
            this.name = optionsValue.name;
        } else {
            this.name = name;
        }
        this.version = version;
        // create new SQLContext
        this.context = new SQLContextImpl(this);
        this.options = optionsValue;
        this.logEnabled = optionsValue.logEnabled;
        if (this.logEnabled) {
            Logger.debug("%s is created with %s", getClass().getName(), optionsValue.toString());
        }
    }

    protected void beginLock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected List<SQLiteUpdateTask> buildTaskList(int previousVersion, int currentVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * used to clear prepared statements.
     */
    public abstract void clearCompiledStatements();

    public SQLContext getContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * (non-Javadoc)
     *
     * @see android.database.sqlite.SQLiteOpenHelper#close()
     */
    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void closeThreadSafeMode(Pair<Boolean, SupportSQLiteDatabase> status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValues(SupportSQLiteStatement compiledStatement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValuesForContentProvider(ContentValues values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected KriptonContentValues contentValuesForUpdate(SupportSQLiteStatement compiledStatement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void createHelper() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void deleteDatabaseFile(String fileName) {
        if (fileName.equalsIgnoreCase(":memory:") || fileName.trim().length() == 0) {
            return;
        }
        if (this.logEnabled) {
            Logger.fatal("deleting the database file: " + fileName);
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                SQLiteDatabase.deleteDatabase(new File(fileName));
            } else {
                try {
                    final boolean deleted = new File(fileName).delete();
                    if (!deleted && this.logEnabled) {
                        Logger.fatal("Could not delete the database file " + fileName);
                    }
                } catch (Exception error) {
                    if (this.logEnabled) {
                        Logger.fatal("error while deleting corrupted database file " + error.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            if (this.logEnabled) {
                /* print warning and ignore exception */
                Logger.warn("delete failed: ", e);
            }
        }
    }

    protected void endLock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void forceClose() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteDatabase getDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OnErrorListener getOnErrorListener() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isJustCreated() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isLogEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isOpen() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isAnyPendingOperation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isOpenInWriteMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isUpgradedVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     */
    private void manageStatus() {
        switch(status.get()) {
            case READ_AND_WRITE_OPENED:
                if (database == null)
                    status.set(TypeStatus.CLOSED);
                lockReadWriteAccess.unlock();
                break;
            case READ_ONLY_OPENED:
                if (database == null)
                    status.set(TypeStatus.CLOSED);
                lockReadAccess.unlock();
                break;
            case CLOSED:
                // do nothing
                break;
        }
    }

    /**
     * Returns <code>true</code> if the database need foreign keys
     *
     * @return
     */
    public abstract boolean hasForeignKeys();

    protected void onConfigure(SupportSQLiteDatabase database) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onCorruption(@NonNull SupportSQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * On create.
     *
     * @param database the database
     */
    protected abstract void onCreate(SupportSQLiteDatabase database);

    protected void onDowngrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onOpen(SupportSQLiteDatabase db) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Set<Integer> onSessionClosed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onSessionOpened() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Pair<Boolean, SupportSQLiteDatabase> openDatabaseThreadSafeMode(boolean writeMode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteDatabase openReadOnlyDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected SupportSQLiteDatabase openReadOnlyDatabase(boolean lock) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteDatabase openWritableDatabase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected SupportSQLiteDatabase openWritableDatabase(boolean lock) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setOnErrorListener(OnErrorListener onErrorListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected StringBuilder sqlBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
