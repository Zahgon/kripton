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
package com.abubusoft.kripton.androidx.livedata;

import java.util.concurrent.atomic.AtomicBoolean;
import com.abubusoft.kripton.android.LiveDataHandler;
import com.abubusoft.kripton.android.Paginator;
import com.abubusoft.kripton.android.executor.KriptonTaskExecutor;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;

/**
 * A PLiveData class that can be invalidated and computed on demand.
 * <p>
 * This is an internal class for now, might be public if we see the necessity.
 *
 * @param <T>
 *            The type of the live data
 */
public abstract class KriptonXPagedLiveDataHandlerImpl<T> implements LiveDataHandler {

    /**
     * The m live data.
     */
    private final PagedLiveData<T> mLiveData;

    /**
     * The m invalid.
     */
    private AtomicBoolean mInvalid = new AtomicBoolean(true);

    /**
     * The m computing.
     */
    private AtomicBoolean mComputing = new AtomicBoolean(false);

    /**
     * Creates a computable live data which is computed when there are active
     * observers.
     * <p>
     * It can also be invalidated via {@link #invalidate()} which will result in
     * a call to {@link #compute()} if there are active observers (or when they
     * start observing)
     */
    public KriptonXPagedLiveDataHandlerImpl(Paginator<T> pagedResult) {
        mLiveData = new PagedLiveData<T>(pagedResult, this) {

            @Override
            protected void onActive() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
    }

    @NonNull
    public PagedLiveData<T> getLiveData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The m refresh runnable.
     */
    @VisibleForTesting
    final Runnable mRefreshRunnable = new Runnable() {

        @WorkerThread
        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * The m invalidation runnable.
     */
    // invalidation check always happens on the main thread
    @VisibleForTesting
    final Runnable mInvalidationRunnable = new Runnable() {

        @MainThread
        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Compute.
     *
     * @return the t
     */
    @WorkerThread
    protected abstract T compute();
}
