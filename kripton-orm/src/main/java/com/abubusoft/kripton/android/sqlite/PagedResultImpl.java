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

import java.util.ArrayList;
import java.util.List;
import com.abubusoft.kripton.android.PageRequest;
import com.abubusoft.kripton.android.Paginator;

/**
 * <p>
 * Allow to manage SQL result pages with LIMIT clause.
 * </p>
 *
 * <p>
 * See <a href="https://www.sqlite.org/lang_select.html">here</a> for more info.
 * </p>
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @param <E>
 *            the element type
 */
/**
 * @author xcesco
 *
 * @param <E>
 */
public abstract class PagedResultImpl<E> implements Paginator<List<E>> {

    /**
     * if false, paged result does not contains valid paged result
     */
    protected boolean paged;

    /**
     * The first row.
     */
    protected int offset;

    /**
     * The list.
     */
    protected volatile List<E> list;

    /**
     * The page size.
     */
    protected int pageSize;

    /**
     * number of total element extracted by the query
     */
    protected volatile int totalElements = 0;

    /**
     * Instantiates a new paginated result.
     */
    protected PagedResultImpl() {
        reset();
    }

    /**
     * execute method
     *
     * @return result
     */
    public abstract List<E> execute();

    @Override
    public void firstPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<E> getList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getOffset() {
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
    public int getTotalElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasPrevious() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setTotalElements(int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void nextPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void previousPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setOffset(int offset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setPage(int page) {
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
    public void lastPage() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setPageSize(int pageSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public List<E> execute(int pageNumber, int pageSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
