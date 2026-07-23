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
package com.abubusoft.kripton.android.executor;

import java.util.concurrent.Executor;

/**
 * A static class that serves as a central point to execute common tasks.
 */
public class KriptonTaskExecutor extends TaskExecutor {

    /**
     * The s instance.
     */
    private static volatile KriptonTaskExecutor sInstance;

    /**
     * The m delegate.
     */
    private TaskExecutor mDelegate;

    /**
     * The m default task executor.
     */
    private TaskExecutor mDefaultTaskExecutor;

    /**
     * The Constant sMainThreadExecutor.
     */
    private static final Executor sMainThreadExecutor = new Executor() {

        @Override
        public void execute(Runnable command) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * The Constant sIOThreadExecutor.
     */
    private static final Executor sIOThreadExecutor = new Executor() {

        @Override
        public void execute(Runnable command) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Instantiates a new kripton task executor.
     */
    private KriptonTaskExecutor() {
        mDefaultTaskExecutor = new KriptonDefaultTaskExecutor();
        mDelegate = mDefaultTaskExecutor;
    }

    public static KriptonTaskExecutor getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setDelegate(TaskExecutor taskExecutor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.android.sqlite.executors.TaskExecutor#executeOnDiskIO(java.lang.Runnable)
	 */
    @Override
    public void executeOnDiskIO(Runnable runnable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.android.sqlite.executors.TaskExecutor#postToMainThread(java.lang.Runnable)
	 */
    @Override
    public void postToMainThread(Runnable runnable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Executor getMainThreadExecutor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Executor getIOThreadExecutor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.android.sqlite.executors.TaskExecutor#isMainThread()
	 */
    @Override
    public boolean isMainThread() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
