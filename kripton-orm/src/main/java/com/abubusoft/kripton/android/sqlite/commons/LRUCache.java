/**
 * ****************************************************************************
 *  Copyright 2016-2019 Francesco Benincasa (info@abubusoft.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * ****************************************************************************
 */
package com.abubusoft.kripton.android.sqlite.commons;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * http://badalb.blogspot.it/2016/09/implement-lru-cache-in-java.html
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @param <K> the key type
 * @param <V> the value type
 */
public class LRUCache<K, V> {

    /**
     * The listener interface for receiving onRemove events.
     * The class that is interested in processing a onRemove
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnRemoveListener</code> method. When
     * the onRemove event occurs, that object's appropriate
     * method is invoked.
     *
     * @param <V> the value type
     */
    public interface OnRemoveListener<V> {

        /**
         * On remove.
         *
         * @param value the value
         */
        void onRemove(V value);
    }

    /**
     * The max size.
     */
    private final int maxSize;

    /**
     * The map.
     */
    private ConcurrentHashMap<K, V> map;

    /**
     * The queue.
     */
    private ConcurrentLinkedQueue<K> queue;

    /**
     * The listener.
     */
    private OnRemoveListener<V> listener;

    /**
     * Instantiates a new LRU cache.
     *
     * @param maxSize the max size
     * @param listener the listener
     */
    public LRUCache(final int maxSize, OnRemoveListener<V> listener) {
        this.maxSize = maxSize;
        this.listener = listener;
        map = new ConcurrentHashMap<K, V>(maxSize);
        queue = new ConcurrentLinkedQueue<K>();
    }

    public void put(final K key, final V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public V get(final K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
