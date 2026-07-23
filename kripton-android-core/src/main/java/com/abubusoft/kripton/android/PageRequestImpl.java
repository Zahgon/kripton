package com.abubusoft.kripton.android;

public class PageRequestImpl implements PageRequest {

    private int pageNumber;

    private int pageSize;

    PageRequestImpl(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
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
}
