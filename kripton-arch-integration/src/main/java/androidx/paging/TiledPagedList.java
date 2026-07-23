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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import java.util.concurrent.Executor;

class TiledPagedList<T> extends PagedList<T> implements PagedStorage.Callback {

    private final PositionalDataSource<T> mDataSource;

    private PageResult.Receiver<T> mReceiver = new PageResult.Receiver<T>() {

        // Creation thread for initial synchronous load, otherwise main thread
        // Safe to access main thread only state - no other thread has reference during construction
        @Override
        public void onPageResult(@PageResult.ResultType int type, @NonNull PageResult<T> pageResult) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    @WorkerThread
    TiledPagedList(@NonNull PositionalDataSource<T> dataSource, @NonNull Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @Nullable BoundaryCallback<T> boundaryCallback, @NonNull Config config, int position) {
        super(new PagedStorage<T>(), mainThreadExecutor, backgroundThreadExecutor, boundaryCallback, config);
        mDataSource = dataSource;
        final int pageSize = mConfig.pageSize;
        mLastLoad = position;
        if (mDataSource.isInvalid()) {
            detach();
        } else {
            final int firstLoadSize = (Math.max(Math.round(mConfig.initialLoadSizeHint / pageSize), 2)) * pageSize;
            final int idealStart = position - firstLoadSize / 2;
            final int roundedPageStart = Math.max(0, Math.round(idealStart / pageSize) * pageSize);
            mDataSource.dispatchLoadInitial(true, roundedPageStart, firstLoadSize, pageSize, mMainThreadExecutor, mReceiver);
        }
    }

    @Override
    boolean isContiguous() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public DataSource<?, T> getDataSource() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    @Override
    public Object getLastKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void dispatchUpdatesSinceSnapshot(@NonNull PagedList<T> pagedListSnapshot, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void loadAroundInternal(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onInitialized(int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onPagePrepended(int leadingNulls, int changed, int added) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onPageAppended(int endPosition, int changed, int added) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onPagePlaceholderInserted(final int pageIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onPageInserted(int start, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
