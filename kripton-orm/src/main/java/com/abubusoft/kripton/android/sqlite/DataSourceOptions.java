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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.abubusoft.kripton.android.KriptonLibrary;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.common.Pair;
import android.content.Context;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Factory;

/**
 * Options to build a data source.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class DataSourceOptions {

    /**
     * The log enabled.
     */
    public final boolean logEnabled;

    /**
     * The database lifecycle handler.
     */
    public final DatabaseLifecycleHandler databaseLifecycleHandler;

    /**
     * The update tasks.
     */
    public final List<Pair<Integer, ? extends SQLiteUpdateTask>> updateTasks;

    /**
     * The populator.
     */
    public final SQLitePopulator populator;

    /**
     * The in memory.
     */
    public final boolean inMemory;

    /**
     * if true, datasource is closed if unused
     */
    public final boolean neverClose;

    /**
     * datasource file name
     */
    public final String name;

    /**
     * Factory used to create the helper object.
     */
    public final Factory openHelperFactory;

    /**
     * If database is builded with the build method, forces the instance to be
     * created
     */
    public final boolean forceBuild;

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Builder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class Builder.
     */
    public static class Builder {

        /**
         * The log enabled.
         */
        private boolean logEnabled = true;

        /**
         * The database lifecycle handler.
         */
        private DatabaseLifecycleHandler databaseLifecycleHandler;

        /**
         * The update tasks.
         */
        private List<Pair<Integer, ? extends SQLiteUpdateTask>> updateTasks = new ArrayList<>();

        /**
         * The populator.
         */
        private SQLitePopulator populator;

        /**
         * The in memory.
         */
        private boolean inMemory;

        /**
         * datasource filename
         */
        private String name;

        /**
         * check if datasource need to be closed (true) or it need to be always opened
         */
        private boolean neverClose = false;

        /**
         * If <code>true</code> force instance to be created. Default is
         * <code>false</code>.
         */
        private boolean forceBuild = false;

        /**
         * OpenHelper factory. Default is provided with kripton
         */
        private SupportSQLiteOpenHelper.Factory openHelperFactory = new KriptonSQLiteHelperFactory();

        public Builder openHelperFactory(SupportSQLiteOpenHelper.Factory openHelperFactory) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder name(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder log(boolean value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder forceBuild(boolean value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder databaseLifecycleHandler(DatabaseLifecycleHandler value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder populator(SQLitePopulator populator) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder inMemory(boolean inMemory) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder addUpdateTask(int targetVersion, Context context, int resRawId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder addUpdateTask(int targetVersion, int resRawId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder addUpdateTask(final int targetVersion, final List<String> sqlCommandList) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder addUpdateTask(int targetVersion, SQLiteUpdateTask task) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder addUpdateTask(int targetVersion, InputStream inputStream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public DataSourceOptions build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder createFrom(DataSourceOptions source) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder neverClose(boolean neverClose) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Instantiates a new data source options.
     *
     * @param name                     datasource filename
     * @param databaseLifecycleHandler the database lifecycle handler
     * @param updateTasks              the update tasks
     * @param log                      the log
     * @param populator                the populator
     * @param inMemory                 the in memory
     * @param openHelperFactory
     * @param forceBuild               force the build method to rebuild the instance
     */
    private DataSourceOptions(String name, DatabaseLifecycleHandler databaseLifecycleHandler, List<Pair<Integer, ? extends SQLiteUpdateTask>> updateTasks, boolean log, SQLitePopulator populator, boolean inMemory, Factory openHelperFactory, boolean forceBuild, boolean neverClose) {
        this.logEnabled = log;
        this.databaseLifecycleHandler = databaseLifecycleHandler;
        this.updateTasks = updateTasks;
        this.populator = populator;
        this.inMemory = inMemory;
        this.openHelperFactory = openHelperFactory;
        this.forceBuild = forceBuild;
        this.name = name;
        this.neverClose = neverClose;
    }
}
