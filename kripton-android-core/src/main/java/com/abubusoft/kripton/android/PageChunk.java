package com.abubusoft.kripton.android;

import java.util.List;

public class PageChunk<E> implements Comparable<PageChunk<E>> {

    private int pageNumber;

    public int getPageNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<E> getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<E> data;

    public PageChunk(int pageNumber, List<E> data) {
        this.pageNumber = pageNumber;
        this.data = data;
    }

    @Override
    public int compareTo(PageChunk<E> o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
