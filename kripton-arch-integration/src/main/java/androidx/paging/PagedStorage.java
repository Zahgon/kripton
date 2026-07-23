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
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

final class PagedStorage<T> extends AbstractList<T> {

    /**
     * Lists instances are compared (with instance equality) to PLACEHOLDER_LIST to check if an item
     * in that position is already loading. We use a singleton placeholder list that is distinct
     * from Collections.EMPTY_LIST for safety.
     */
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final List PLACEHOLDER_LIST = new ArrayList();

    // Always set
    private int mLeadingNullCount;

    /**
     * List of pages in storage.
     *
     * Two storage modes:
     *
     * Contiguous - all content in mPages is valid and loaded, but may return false from isTiled().
     *     Safe to access any item in any page.
     *
     * Non-contiguous - mPages may have nulls or a placeholder page, isTiled() always returns true.
     *     mPages may have nulls, or placeholder (empty) pages while content is loading.
     */
    private final ArrayList<List<T>> mPages;

    private int mTrailingNullCount;

    private int mPositionOffset;

    /**
     * Number of items represented by {@link #mPages}. If tiling is enabled, unloaded items in
     * {@link #mPages} may be null, but this value still counts them.
     */
    private int mStorageCount;

    // If mPageSize > 0, tiling is enabled, 'mPages' may have gaps, and leadingPages is set
    private int mPageSize;

    private int mNumberPrepended;

    private int mNumberAppended;

    PagedStorage() {
        mLeadingNullCount = 0;
        mPages = new ArrayList<>();
        mTrailingNullCount = 0;
        mPositionOffset = 0;
        mStorageCount = 0;
        mPageSize = 1;
        mNumberPrepended = 0;
        mNumberAppended = 0;
    }

    PagedStorage(int leadingNulls, List<T> page, int trailingNulls) {
        this();
        init(leadingNulls, page, trailingNulls, 0);
    }

    private PagedStorage(PagedStorage<T> other) {
        mLeadingNullCount = other.mLeadingNullCount;
        mPages = new ArrayList<>(other.mPages);
        mTrailingNullCount = other.mTrailingNullCount;
        mPositionOffset = other.mPositionOffset;
        mStorageCount = other.mStorageCount;
        mPageSize = other.mPageSize;
        mNumberPrepended = other.mNumberPrepended;
        mNumberAppended = other.mNumberAppended;
    }

    PagedStorage<T> snapshot() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void init(int leadingNulls, List<T> page, int trailingNulls, int positionOffset) {
        mLeadingNullCount = leadingNulls;
        mPages.clear();
        mPages.add(page);
        mTrailingNullCount = trailingNulls;
        mPositionOffset = positionOffset;
        mStorageCount = page.size();
        // initialized as tiled. There may be 3 nulls, 2 items, but we still call this tiled
        // even if it will break if nulls convert.
        mPageSize = page.size();
        mNumberPrepended = 0;
        mNumberAppended = 0;
    }

    void init(int leadingNulls, @NonNull List<T> page, int trailingNulls, int positionOffset, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public T get(int i) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isTiled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getLeadingNullCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getTrailingNullCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getStorageCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getNumberAppended() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getNumberPrepended() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getPageCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    interface Callback {

        void onInitialized(int count);

        void onPagePrepended(int leadingNulls, int changed, int added);

        void onPageAppended(int endPosition, int changed, int added);

        void onPagePlaceholderInserted(int pageIndex);

        void onPageInserted(int start, int count);
    }

    int getPositionOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int computeLeadingNulls() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int computeTrailingNulls() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // ---------------- Contiguous API -------------------
    T getFirstLoadedItem() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    T getLastLoadedItem() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void prependPage(@NonNull List<T> page, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void appendPage(@NonNull List<T> page, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // ------------------ Non-Contiguous API (tiling required) ----------------------
    void initAndSplit(int leadingNulls, @NonNull List<T> multiPageList, int trailingNulls, int positionOffset, int pageSize, @NonNull Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void insertPage(int position, @NonNull List<T> page, @Nullable Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void allocatePageRange(final int minimumPage, final int maximumPage) {
        int leadingNullPages = mLeadingNullCount / mPageSize;
        if (minimumPage < leadingNullPages) {
            for (int i = 0; i < leadingNullPages - minimumPage; i++) {
                mPages.add(0, null);
            }
            int newStorageAllocated = (leadingNullPages - minimumPage) * mPageSize;
            mStorageCount += newStorageAllocated;
            mLeadingNullCount -= newStorageAllocated;
            leadingNullPages = minimumPage;
        }
        if (maximumPage >= leadingNullPages + mPages.size()) {
            int newStorageAllocated = Math.min(mTrailingNullCount, (maximumPage + 1 - (leadingNullPages + mPages.size())) * mPageSize);
            for (int i = mPages.size(); i <= maximumPage - leadingNullPages; i++) {
                mPages.add(mPages.size(), null);
            }
            mStorageCount += newStorageAllocated;
            mTrailingNullCount -= newStorageAllocated;
        }
    }

    public void allocatePlaceholders(int index, int prefetchDistance, int pageSize, Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasPage(int pageSize, int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
