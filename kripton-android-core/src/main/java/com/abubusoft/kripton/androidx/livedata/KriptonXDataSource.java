package com.abubusoft.kripton.androidx.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.PositionalDataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A {@link PositionalDataSource} that loads entities based on an ObjectBox
 * {@link Query} using offset and limit to implement paging support. The data
 * source is invalidated if the query results change.
 */
public class KriptonXDataSource<T> extends PositionalDataSource<T> {

    private final PagedLiveData<T> query;

    private final Observer<T> observer;

    public static class Factory<Item> extends DataSource.Factory<Integer, Item> {

        private final PagedLiveData<Item> query;

        public Factory(PagedLiveData<Item> query) {
            this.query = query;
        }

        @Override
        public KriptonXDataSource<Item> create() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public KriptonXDataSource(PagedLiveData<T> query) {
        this.query = query;
        observer = new Observer<T>() {

            @Override
            public void onChanged(T t) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
        query.observeForever(observer);
        // observer will be automatically removed once GC'ed
        // query. subscribe().onlyChanges().weak().observer(observer);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull LoadInitialCallback<T> callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull LoadRangeCallback<T> callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private List<T> loadRange(int startPosition, int loadCount) {
        /* private void loadRange(int startPosition, int loadCount) { */
        // note: find interprets loadCount 0 as no limit
        //query.moveTo(startPosition, loadCount);
        query.createPageRequestBuilder().offset(startPosition).pageSize(loadCount).apply();
        return new ArrayList<>();
    }
}
