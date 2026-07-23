/*
 * Copyright 2016-2019 The Android Open Source Project
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
package androidx.paging;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import java.util.concurrent.Executor;

class ContiguousPagedList<K, V> extends PagedList<V> implements PagedStorage.Callback {

    private final ContiguousDataSource<K, V> mDataSource;

    private boolean mPrependWorkerRunning = false;

    private boolean mAppendWorkerRunning = false;

    private int mPrependItemsRequested = 0;

    private int mAppendItemsRequested = 0;

    private PageResult.Receiver<V> mReceiver = new PageResult.Receiver<V>() {

        // Creation thread for initial synchronous load, otherwise main thread
        // Safe to access main thread only state - no other thread has reference during construction
        @Override
        public void onPageResult(@PageResult.ResultType int resultType, @NonNull PageResult<V> pageResult) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    static final int LAST_LOAD_UNSPECIFIED = -1;

    ContiguousPagedList(@NonNull ContiguousDataSource<K, V> dataSource, @NonNull Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @Nullable BoundaryCallback<V> boundaryCallback, @NonNull Config config, @Nullable final K key, int lastLoad) {
        super(new PagedStorage<V>(), mainThreadExecutor, backgroundThreadExecutor, boundaryCallback, config);
        mDataSource = dataSource;
        mLastLoad = lastLoad;
        if (mDataSource.isInvalid()) {
            detach();
        } else {
            mDataSource.dispatchLoadInitial(key, mConfig.initialLoadSizeHint, mConfig.pageSize, mConfig.enablePlaceholders, mMainThreadExecutor, mReceiver);
        }
    }

    @MainThread
    @Override
    void dispatchUpdatesSinceSnapshot(@NonNull PagedList<V> pagedListSnapshot, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    protected void loadAroundInternal(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    private void schedulePrepend() {
        if (mPrependWorkerRunning) {
            return;
        }
        mPrependWorkerRunning = true;
        final int position = mStorage.getLeadingNullCount() + mStorage.getPositionOffset();
        // safe to access first item here - mStorage can't be empty if we're prepending
        final V item = mStorage.getFirstLoadedItem();
        mBackgroundThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    @MainThread
    private void scheduleAppend() {
        if (mAppendWorkerRunning) {
            return;
        }
        mAppendWorkerRunning = true;
        final int position = mStorage.getLeadingNullCount() + mStorage.getStorageCount() - 1 + mStorage.getPositionOffset();
        // safe to access first item here - mStorage can't be empty if we're appending
        final V item = mStorage.getLastLoadedItem();
        mBackgroundThreadExecutor.execute(new Runnable() {

            @Override
            public void run() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    @Override
    boolean isContiguous() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public DataSource<?, V> getDataSource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    @Override
    public Object getLastKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    public void onInitialized(int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    public void onPagePrepended(int leadingNulls, int changedCount, int addedCount) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    public void onPageAppended(int endPosition, int changedCount, int addedCount) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    public void onPagePlaceholderInserted(int pageIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    @Override
    public void onPageInserted(int start, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
