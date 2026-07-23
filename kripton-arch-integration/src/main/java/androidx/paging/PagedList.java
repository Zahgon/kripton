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
import androidx.annotation.WorkerThread;
import java.lang.ref.WeakReference;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Lazy loading list that pages in immutable content from a {@link DataSource}.
 * <p>
 * A PagedList is a {@link List} which loads its data in chunks (pages) from a {@link DataSource}.
 * Items can be accessed with {@link #get(int)}, and further loading can be triggered with
 * {@link #loadAround(int)}. To display a PagedList, see {@link PagedListAdapter}, which enables the
 * binding of a PagedList to a {@link androidx.recyclerview.widget.RecyclerView}.
 * <h4>Loading Data</h4>
 * <p>
 * All data in a PagedList is loaded from its {@link DataSource}. Creating a PagedList loads the
 * first chunk of data from the DataSource immediately, and should for this reason be done on a
 * background thread. The constructed PagedList may then be passed to and used on the UI thread.
 * This is done to prevent passing a list with no loaded content to the UI thread, which should
 * generally not be presented to the user.
 * <p>
 * A PagedList initially presents this first partial load as its content, and expands over time as
 * content is loaded in. When {@link #loadAround} is called, items will be loaded in near the passed
 * list index. If placeholder {@code null}s are present in the list, they will be replaced as
 * content is loaded. If not, newly loaded items will be inserted at the beginning or end of the
 * list.
 * <p>
 * PagedList can present data for an unbounded, infinite scrolling list, or a very large but
 * countable list. Use {@link Config} to control how many items a PagedList loads, and when.
 * <p>
 * If you use {@link LivePagedListBuilder} to get a
 * {@link androidx.lifecycle.LiveData}&lt;PagedList>, it will initialize PagedLists on a
 * background thread for you.
 * <h4>Placeholders</h4>
 * <p>
 * There are two ways that PagedList can represent its not-yet-loaded data - with or without
 * {@code null} placeholders.
 * <p>
 * With placeholders, the PagedList is always the full size of the data set. {@code get(N)} returns
 * the {@code N}th item in the data set, or {@code null} if its not yet loaded.
 * <p>
 * Without {@code null} placeholders, the PagedList is the sublist of data that has already been
 * loaded. The size of the PagedList is the number of currently loaded items, and {@code get(N)}
 * returns the {@code N}th <em>loaded</em> item. This is not necessarily the {@code N}th item in the
 * data set.
 * <p>
 * Placeholders have several benefits:
 * <ul>
 *     <li>They express the full sized list to the presentation layer (often a
 *     {@link PagedListAdapter}), and so can support scrollbars (without jumping as pages are
 *     loaded) and fast-scrolling to any position, whether loaded or not.
 *     <li>They avoid the need for a loading spinner at the end of the loaded list, since the list
 *     is always full sized.
 * </ul>
 * <p>
 * They also have drawbacks:
 * <ul>
 *     <li>Your Adapter (or other presentation mechanism) needs to account for {@code null} items.
 *     This often means providing default values in data you bind to a
 *     {@link androidx.recyclerview.widget.RecyclerView.ViewHolder}.
 *     <li>They don't work well if your item views are of different sizes, as this will prevent
 *     loading items from cross-fading nicely.
 *     <li>They require you to count your data set, which can be expensive or impossible, depending
 *     on where your data comes from.
 * </ul>
 * <p>
 * Placeholders are enabled by default, but can be disabled in two ways. They are disabled if the
 * DataSource does not count its data set in its initial load, or if  {@code false} is passed to
 * {@link Config.Builder#setEnablePlaceholders(boolean)} when building a {@link Config}.
 * <h4>Mutability and Snapshots</h4>
 * A PagedList is <em>mutable</em> while loading, or ready to load from its DataSource.
 * As loads succeed, a mutable PagedList will be updated via Runnables on the main thread. You can
 * listen to these updates with a {@link Callback}. (Note that {@link PagedListAdapter} will listen
 * to these to signal RecyclerView about the updates/changes).
 * <p>
 * If a PagedList attempts to load from an invalid DataSource, it will {@link #detach()}
 * from the DataSource, meaning that it will no longer attempt to load data. It will return true
 * from {@link #isImmutable()}, and a new DataSource / PagedList pair must be created to load
 * further data. See {@link DataSource} and {@link LivePagedListBuilder} for how new PagedLists are
 * created to represent changed data.
 * <p>
 * A PagedList snapshot is simply an immutable shallow copy of the current state of the PagedList as
 * a {@code List}. It will reference the same inner items, and contain the same {@code null}
 * placeholders, if present.
 *
 * @param <T> The type of the entries in the list.
 */
public abstract class PagedList<T> extends AbstractList<T> {

    public class LoadStateListener {
    }

    @NonNull
    final Executor mMainThreadExecutor;

    @NonNull
    final Executor mBackgroundThreadExecutor;

    @Nullable
    final BoundaryCallback<T> mBoundaryCallback;

    @NonNull
    final Config mConfig;

    @NonNull
    final PagedStorage<T> mStorage;

    int mLastLoad = 0;

    T mLastItem = null;

    // if set to true, mBoundaryCallback is non-null, and should
    // be dispatched when nearby load has occurred
    private boolean mBoundaryCallbackBeginDeferred = false;

    private boolean mBoundaryCallbackEndDeferred = false;

    // lowest and highest index accessed by loadAround. Used to
    // decide when mBoundaryCallback should be dispatched
    private int mLowestIndexAccessed = Integer.MAX_VALUE;

    private int mHighestIndexAccessed = Integer.MIN_VALUE;

    private final AtomicBoolean mDetached = new AtomicBoolean(false);

    private final ArrayList<WeakReference<Callback>> mCallbacks = new ArrayList<>();

    PagedList(@NonNull PagedStorage<T> storage, @NonNull Executor mainThreadExecutor, @NonNull Executor backgroundThreadExecutor, @Nullable BoundaryCallback<T> boundaryCallback, @NonNull Config config) {
        mStorage = storage;
        mMainThreadExecutor = mainThreadExecutor;
        mBackgroundThreadExecutor = backgroundThreadExecutor;
        mBoundaryCallback = boundaryCallback;
        mConfig = config;
    }

    /**
     * Create a PagedList which loads data from the provided data source on a background thread,
     * posting updates to the main thread.
     *
     * @param dataSource DataSource providing data to the PagedList
     * @param notifyExecutor Thread that will use and consume data from the PagedList.
     *                       Generally, this is the UI/main thread.
     * @param fetchExecutor Data loading will be done via this executor -
     *                      should be a background thread.
     * @param boundaryCallback Optional boundary callback to attach to the list.
     * @param config PagedList Config, which defines how the PagedList will load data.
     * @param <K> Key type that indicates to the DataSource what data to load.
     * @param <T> Type of items to be held and loaded by the PagedList.
     *
     * @return Newly created PagedList, which will page in data from the DataSource as needed.
     */
    @NonNull
    private static <K, T> PagedList<T> create(@NonNull DataSource<K, T> dataSource, @NonNull Executor notifyExecutor, @NonNull Executor fetchExecutor, @Nullable BoundaryCallback<T> boundaryCallback, @NonNull Config config, @Nullable K key) {
        if (dataSource.isContiguous() || !config.enablePlaceholders) {
            int lastLoad = ContiguousPagedList.LAST_LOAD_UNSPECIFIED;
            if (!dataSource.isContiguous()) {
                //noinspection unchecked
                dataSource = (DataSource<K, T>) ((PositionalDataSource<T>) dataSource).wrapAsContiguousWithoutPlaceholders();
                if (key != null) {
                    lastLoad = (Integer) key;
                }
            }
            ContiguousDataSource<K, T> contigDataSource = (ContiguousDataSource<K, T>) dataSource;
            return new ContiguousPagedList<>(contigDataSource, notifyExecutor, fetchExecutor, boundaryCallback, config, key, lastLoad);
        } else {
            return new TiledPagedList<>((PositionalDataSource<T>) dataSource, notifyExecutor, fetchExecutor, boundaryCallback, config, (key != null) ? (Integer) key : 0);
        }
    }

    /**
     * Builder class for PagedList.
     * <p>
     * DataSource, Config, main thread and background executor must all be provided.
     * <p>
     * A PagedList queries initial data from its DataSource during construction, to avoid empty
     * PagedLists being presented to the UI when possible. It's preferred to present initial data,
     * so that the UI doesn't show an empty list, or placeholders for a few frames, just before
     * showing initial content.
     * <p>
     * {@link LivePagedListBuilder} does this creation on a background thread automatically, if you
     * want to receive a {@code LiveData<PagedList<...>>}.
     *
     * @param <Key> Type of key used to load data from the DataSource.
     * @param <Value> Type of items held and loaded by the PagedList.
     */
    @SuppressWarnings("WeakerAccess")
    public static final class Builder<Key, Value> {

        private final DataSource<Key, Value> mDataSource;

        private final Config mConfig;

        private Executor mNotifyExecutor;

        private Executor mFetchExecutor;

        private BoundaryCallback mBoundaryCallback;

        private Key mInitialKey;

        /**
         * Create a PagedList.Builder with the provided {@link DataSource} and {@link Config}.
         *
         * @param dataSource DataSource the PagedList will load from.
         * @param config Config that defines how the PagedList loads data from its DataSource.
         */
        public Builder(@NonNull DataSource<Key, Value> dataSource, @NonNull Config config) {
            //noinspection ConstantConditions
            if (dataSource == null) {
                throw new IllegalArgumentException("DataSource may not be null");
            }
            //noinspection ConstantConditions
            if (config == null) {
                throw new IllegalArgumentException("Config may not be null");
            }
            mDataSource = dataSource;
            mConfig = config;
        }

        /**
         * Create a PagedList.Builder with the provided {@link DataSource} and page size.
         * <p>
         * This method is a convenience for:
         * <pre>
         * PagedList.Builder(dataSource,
         *         new PagedList.Config.Builder().setPageSize(pageSize).build());
         * </pre>
         *
         * @param dataSource DataSource the PagedList will load from.
         * @param pageSize Config that defines how the PagedList loads data from its DataSource.
         */
        public Builder(@NonNull DataSource<Key, Value> dataSource, int pageSize) {
            this(dataSource, new PagedList.Config.Builder().setPageSize(pageSize).build());
        }

        @NonNull
        public Builder<Key, Value> setNotifyExecutor(@NonNull Executor notifyExecutor) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @NonNull
        public Builder<Key, Value> setFetchExecutor(@NonNull Executor fetchExecutor) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @SuppressWarnings("unused")
        @NonNull
        public Builder<Key, Value> setBoundaryCallback(@Nullable BoundaryCallback boundaryCallback) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @NonNull
        public Builder<Key, Value> setInitialKey(@Nullable Key initialKey) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @WorkerThread
        @NonNull
        public PagedList<Value> build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    @Override
    @Nullable
    public T get(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void loadAround(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Creation thread for initial synchronous load, otherwise main thread
    // Safe to access main thread only state - no other thread has reference during construction
    void deferBoundaryCallbacks(final boolean deferEmpty, final boolean deferBegin, final boolean deferEnd) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Call this when mLowest/HighestIndexAccessed are changed, or
     * mBoundaryCallbackBegin/EndDeferred is set.
     */
    private void tryDispatchBoundaryCallbacks(boolean post) {
        final boolean dispatchBegin = mBoundaryCallbackBeginDeferred && mLowestIndexAccessed <= mConfig.prefetchDistance;
        final boolean dispatchEnd = mBoundaryCallbackEndDeferred && mHighestIndexAccessed >= size() - 1 - mConfig.prefetchDistance;
        if (!dispatchBegin && !dispatchEnd) {
            return;
        }
        if (dispatchBegin) {
            mBoundaryCallbackBeginDeferred = false;
        }
        if (dispatchEnd) {
            mBoundaryCallbackEndDeferred = false;
        }
        if (post) {
            mMainThreadExecutor.execute(new Runnable() {

                @Override
                public void run() {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
        } else {
            dispatchBoundaryCallbacks(dispatchBegin, dispatchEnd);
        }
    }

    private void dispatchBoundaryCallbacks(boolean begin, boolean end) {
        // safe to deref mBoundaryCallback here, since we only defer if mBoundaryCallback present
        if (begin) {
            //noinspection ConstantConditions
            mBoundaryCallback.onItemAtFrontLoaded(mStorage.getFirstLoadedItem());
        }
        if (end) {
            //noinspection ConstantConditions
            mBoundaryCallback.onItemAtEndLoaded(mStorage.getLastLoadedItem());
        }
    }

    void offsetBoundaryAccessIndices(int offset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public boolean isImmutable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    @NonNull
    public List<T> snapshot() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract boolean isContiguous();

    @NonNull
    public Config getConfig() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the DataSource that provides data to this PagedList.
     *
     * @return the DataSource of this PagedList.
     */
    @NonNull
    public abstract DataSource<?, T> getDataSource();

    /**
     * Return the key for the position passed most recently to {@link #loadAround(int)}.
     * <p>
     * When a PagedList is invalidated, you can pass the key returned by this function to initialize
     * the next PagedList. This ensures (depending on load times) that the next PagedList that
     * arrives will have data that overlaps. If you use {@link LivePagedListBuilder}, it will do
     * this for you.
     *
     * @return Key of position most recently passed to {@link #loadAround(int)}.
     */
    @Nullable
    public abstract Object getLastKey();

    @SuppressWarnings("WeakerAccess")
    public boolean isDetached() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public void detach() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getPositionOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public void addWeakCallback(@Nullable List<T> previousSnapshot, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public void removeWeakCallback(@NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void notifyInserted(int position, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void notifyChanged(int position, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Dispatch updates since the non-empty snapshot was taken.
     *
     * @param snapshot Non-empty snapshot.
     * @param callback Callback for updates that have occurred since snapshot.
     */
    abstract void dispatchUpdatesSinceSnapshot(@NonNull PagedList<T> snapshot, @NonNull Callback callback);

    abstract void loadAroundInternal(int index);

    /**
     * Callback signaling when content is loaded into the list.
     * <p>
     * Can be used to listen to items being paged in and out. These calls will be dispatched on
     * the executor defined by {@link Builder#setNotifyExecutor(Executor)}, which is generally
     * the main/UI thread.
     */
    public abstract static class Callback {

        /**
         * Called when null padding items have been loaded to signal newly available data, or when
         * data that hasn't been used in a while has been dropped, and swapped back to null.
         *
         * @param position Position of first newly loaded items, out of total number of items
         *                 (including padded nulls).
         * @param count    Number of items loaded.
         */
        public abstract void onChanged(int position, int count);

        /**
         * Called when new items have been loaded at the end or beginning of the list.
         *
         * @param position Position of the first newly loaded item (in practice, either
         *                 <code>0</code> or <code>size - 1</code>.
         * @param count    Number of items loaded.
         */
        public abstract void onInserted(int position, int count);

        /**
         * Called when items have been removed at the end or beginning of the list, and have not
         * been replaced by padded nulls.
         *
         * @param position Position of the first newly loaded item (in practice, either
         *                 <code>0</code> or <code>size - 1</code>.
         * @param count    Number of items loaded.
         */
        @SuppressWarnings("unused")
        public abstract void onRemoved(int position, int count);
    }

    /**
     * Configures how a PagedList loads content from its DataSource.
     * <p>
     * Use a Config {@link Builder} to construct and define custom loading behavior, such as
     * {@link Builder#setPageSize(int)}, which defines number of items loaded at a time}.
     */
    public static class Config {

        /**
         * Size of each page loaded by the PagedList.
         */
        public final int pageSize;

        /**
         * Prefetch distance which defines how far ahead to load.
         * <p>
         * If this value is set to 50, the paged list will attempt to load 50 items in advance of
         * data that's already been accessed.
         *
         * @see PagedList#loadAround(int)
         */
        @SuppressWarnings("WeakerAccess")
        public final int prefetchDistance;

        /**
         * Defines whether the PagedList may display null placeholders, if the DataSource provides
         * them.
         */
        @SuppressWarnings("WeakerAccess")
        public final boolean enablePlaceholders;

        /**
         * Size hint for initial load of PagedList, often larger than a regular page.
         */
        @SuppressWarnings("WeakerAccess")
        public final int initialLoadSizeHint;

        private Config(int pageSize, int prefetchDistance, boolean enablePlaceholders, int initialLoadSizeHint) {
            this.pageSize = pageSize;
            this.prefetchDistance = prefetchDistance;
            this.enablePlaceholders = enablePlaceholders;
            this.initialLoadSizeHint = initialLoadSizeHint;
        }

        /**
         * Builder class for {@link Config}.
         * <p>
         * You must at minimum specify page size with {@link #setPageSize(int)}.
         */
        public static final class Builder {

            private int mPageSize = -1;

            private int mPrefetchDistance = -1;

            private int mInitialLoadSizeHint = -1;

            private boolean mEnablePlaceholders = true;

            public Builder setPageSize(int pageSize) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Builder setPrefetchDistance(int prefetchDistance) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @SuppressWarnings("SameParameterValue")
            public Builder setEnablePlaceholders(boolean enablePlaceholders) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @SuppressWarnings("WeakerAccess")
            public Builder setInitialLoadSizeHint(int initialLoadSizeHint) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Config build() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }
    }

    /**
     * Signals when a PagedList has reached the end of available data.
     * <p>
     * When local storage is a cache of network data, it's common to set up a streaming pipeline:
     * Network data is paged into the database, database is paged into UI. Paging from the database
     * to UI can be done with a {@code LiveData<PagedList>}, but it's still necessary to know when
     * to trigger network loads.
     * <p>
     * BoundaryCallback does this signaling - when a DataSource runs out of data at the end of
     * the list, {@link #onItemAtEndLoaded(Object)} is called, and you can start an async network
     * load that will write the result directly to the database. Because the database is being
     * observed, the UI bound to the {@code LiveData<PagedList>} will update automatically to
     * account for the new items.
     * <p>
     * Note that a BoundaryCallback instance shared across multiple PagedLists (e.g. when passed to
     * {@link LivePagedListBuilder#setBoundaryCallback}), the callbacks may be issued multiple
     * times. If for example {@link #onItemAtEndLoaded(Object)} triggers a network load, it should
     * avoid triggering it again while the load is ongoing.
     * <p>
     * BoundaryCallback only passes the item at front or end of the list. Number of items is not
     * passed, since it may not be fully computed by the DataSource if placeholders are not
     * supplied. Keys are not known because the BoundaryCallback is independent of the
     * DataSource-specific keys, which may be different for local vs remote storage.
     * <p>
     * The database + network Repository in the
     * <a href="https://github.com/googlesamples/android-architecture-components/blob/master/PagingWithNetworkSample/README.md">PagingWithNetworkSample</a>
     * shows how to implement a network BoundaryCallback using
     * <a href="https://square.github.io/retrofit/">Retrofit</a>, while
     * handling swipe-to-refresh, network errors, and retry.
     *
     * @param <T> Type loaded by the PagedList.
     */
    @MainThread
    public abstract static class BoundaryCallback<T> {

        public void onZeroItemsLoaded() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemAtFrontLoaded(@NonNull T itemAtFront) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemAtEndLoaded(@NonNull T itemAtEnd) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
