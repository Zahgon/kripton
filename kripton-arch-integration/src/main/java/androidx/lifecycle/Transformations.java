/*
 * Copyright (C) 2017 The Android Open Source Project
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
package androidx.lifecycle;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;

/**
 * Transformations for a {@link LiveData} class.
 * <p>
 * You can use transformation methods to carry information across the observer's lifecycle. The
 * transformations aren't calculated unless an observer is observing the returned LiveData object.
 * <p>
 * Because the transformations are calculated lazily, lifecycle-related behavior is implicitly
 * passed down without requiring additional explicit calls or dependencies.
 */
@SuppressWarnings("WeakerAccess")
public class Transformations {

    /**
     * Instantiates a new transformations.
     */
    private Transformations() {
    }

    @MainThread
    public static <X, Y> LiveData<Y> map(@NonNull LiveData<X> source, @NonNull final Function<X, Y> func) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    public static <X, Y> LiveData<Y> switchMap(@NonNull LiveData<X> trigger, @NonNull final Function<X, LiveData<Y>> func) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
