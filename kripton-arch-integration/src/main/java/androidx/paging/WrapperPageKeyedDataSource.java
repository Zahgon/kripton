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
import androidx.arch.core.util.Function;
import java.util.List;

class WrapperPageKeyedDataSource<K, A, B> extends PageKeyedDataSource<K, B> {

    private final PageKeyedDataSource<K, A> mSource;

    private final Function<List<A>, List<B>> mListFunction;

    WrapperPageKeyedDataSource(PageKeyedDataSource<K, A> source, Function<List<A>, List<B>> listFunction) {
        mSource = source;
        mListFunction = listFunction;
    }

    @Override
    public void addInvalidatedCallback(@NonNull InvalidatedCallback onInvalidatedCallback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void removeInvalidatedCallback(@NonNull InvalidatedCallback onInvalidatedCallback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void invalidate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isInvalid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<K> params, @NonNull final LoadInitialCallback<K, B> callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void loadBefore(@NonNull LoadParams<K> params, @NonNull final LoadCallback<K, B> callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void loadAfter(@NonNull LoadParams<K> params, @NonNull final LoadCallback<K, B> callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
