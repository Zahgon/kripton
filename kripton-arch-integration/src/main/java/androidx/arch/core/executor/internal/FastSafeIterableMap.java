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
package androidx.arch.core.executor.internal;

import java.util.HashMap;
import java.util.Map;
import androidx.annotation.NonNull;

/**
 * Poor's man LinkedHashMap, which supports modifications during iterations.
 * Takes more memory that SafeIterableMap
 * It is NOT thread safe.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class FastSafeIterableMap<K, V> extends SafeIterableMap<K, V> {

    /**
     * The m hash map.
     */
    private HashMap<K, Entry<K, V>> mHashMap = new HashMap<>();

    /* (non-Javadoc)
     * @see android.arch.core.internal.SafeIterableMap#get(java.lang.Object)
     */
    @Override
    protected Entry<K, V> get(K k) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see android.arch.core.internal.SafeIterableMap#putIfAbsent(java.lang.Object, java.lang.Object)
     */
    @Override
    public V putIfAbsent(@NonNull K key, @NonNull V v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see android.arch.core.internal.SafeIterableMap#remove(java.lang.Object)
     */
    @Override
    public V remove(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean contains(K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map.Entry<K, V> ceil(K k) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
