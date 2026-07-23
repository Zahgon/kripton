/**
 */
package com.abubusoft.kripton.androidx.livedata;

import com.abubusoft.kripton.android.PageRequestExecutor;
import com.abubusoft.kripton.android.PagedResult;
import com.abubusoft.kripton.android.Paginator;

/**
 * The paged version of KriptonLiveData.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @param <T>
 *            the generic type
 */
public abstract class PagedLiveData<T> extends KriptonXLiveData<T> implements PagedResult {

    public PageRequestBuilder createPageRequestBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * This builder allows you to manipulate page request object, changing some
     * its attributes and invoke an unique update to live date.
     *
     * @author xcesco
     */
    public class PageRequestBuilder {

        private int originalOffset;

        private int originalPage;

        private int originalPageSize;

        private int offset;

        private int page;

        private int pageSize;

        private PageRequestBuilder() {
            originalOffset = pagedResult.getOffset();
            originalPage = pagedResult.getPageNumber();
            originalPageSize = pagedResult.getPageSize();
            offset = originalOffset;
            page = originalPage;
            pageSize = originalPageSize;
        }

        public PageRequestBuilder offset(int value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public PageRequestBuilder page(int value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public PageRequestBuilder pageSize(int value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void apply() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private final PagedResult pagedResult;

    private KriptonXPagedLiveDataHandlerImpl<T> handler;

    private PageRequestExecutor<T> pageRequestExecutor;

    public PagedLiveData(Paginator<T> pageRequest, KriptonXPagedLiveDataHandlerImpl<T> handler) {
        this.pagedResult = pageRequest;
        this.pageRequestExecutor = pageRequest;
        this.handler = handler;
    }

    public PageRequestExecutor<T> getExecutor() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getPageNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getPageSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setPage(int page) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void nextPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setOffset(int offset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void previousPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void firstPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setPageSize(int pageSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getTotalElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void lastPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getTotalPages() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isLast() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isFirst() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasPrevious() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
