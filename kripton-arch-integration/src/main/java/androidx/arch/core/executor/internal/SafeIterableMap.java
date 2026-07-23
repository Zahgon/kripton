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

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import androidx.annotation.NonNull;

/**
 * LinkedList, which pretends to be a map and supports modifications during iterations.
 * It is NOT thread safe.
 *
 * @param <K> Key type
 * @param <V> Value type
 */
public class SafeIterableMap<K, V> implements Iterable<Map.Entry<K, V>> {

    /**
     * The m start.
     */
    private Entry<K, V> mStart;

    /**
     * The m end.
     */
    private Entry<K, V> mEnd;

    // using WeakHashMap over List<WeakReference>, so we don't have to manually remove
    /**
     * The m iterators.
     */
    // WeakReferences that have null in them.
    private WeakHashMap<SupportRemove<K, V>, Boolean> mIterators = new WeakHashMap<>();

    /**
     * The m size.
     */
    private int mSize = 0;

    protected Entry<K, V> get(K k) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public V putIfAbsent(@NonNull K key, @NonNull V v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected Entry<K, V> put(@NonNull K key, @NonNull V v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public V remove(@NonNull K key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Iterator<Map.Entry<K, V>> descendingIterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public IteratorWithAdditions iteratorWithAdditions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map.Entry<K, V> eldest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map.Entry<K, V> newest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class ListIterator.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    private abstract static class ListIterator<K, V> implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {

        /**
         * The m expected end.
         */
        Entry<K, V> mExpectedEnd;

        /**
         * The m next.
         */
        Entry<K, V> mNext;

        /**
         * Instantiates a new list iterator.
         *
         * @param start the start
         * @param expectedEnd the expected end
         */
        ListIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            this.mExpectedEnd = expectedEnd;
            this.mNext = start;
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.SupportRemove#supportRemove(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        public void supportRemove(@NonNull Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Next node.
         *
         * @return the entry
         */
        private Entry<K, V> nextNode() {
            if (mNext == mExpectedEnd || mExpectedEnd == null) {
                return null;
            }
            return forward(mNext);
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        @Override
        public Map.Entry<K, V> next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Forward.
         *
         * @param entry the entry
         * @return the entry
         */
        abstract Entry<K, V> forward(Entry<K, V> entry);

        /**
         * Backward.
         *
         * @param entry the entry
         * @return the entry
         */
        abstract Entry<K, V> backward(Entry<K, V> entry);
    }

    /**
     * The Class AscendingIterator.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    static class AscendingIterator<K, V> extends ListIterator<K, V> {

        /**
         * Instantiates a new ascending iterator.
         *
         * @param start the start
         * @param expectedEnd the expected end
         */
        AscendingIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            super(start, expectedEnd);
        }

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.ListIterator#forward(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        Entry<K, V> forward(Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.ListIterator#backward(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        Entry<K, V> backward(Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class DescendingIterator.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    private static class DescendingIterator<K, V> extends ListIterator<K, V> {

        /**
         * Instantiates a new descending iterator.
         *
         * @param start the start
         * @param expectedEnd the expected end
         */
        DescendingIterator(Entry<K, V> start, Entry<K, V> expectedEnd) {
            super(start, expectedEnd);
        }

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.ListIterator#forward(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        Entry<K, V> forward(Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.ListIterator#backward(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        Entry<K, V> backward(Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class IteratorWithAdditions.
     */
    private class IteratorWithAdditions implements Iterator<Map.Entry<K, V>>, SupportRemove<K, V> {

        /**
         * The m current.
         */
        private Entry<K, V> mCurrent;

        /**
         * The m before start.
         */
        private boolean mBeforeStart = true;

        /* (non-Javadoc)
         * @see android.arch.core.internal.SafeIterableMap.SupportRemove#supportRemove(android.arch.core.internal.SafeIterableMap.Entry)
         */
        @Override
        public void supportRemove(@NonNull Entry<K, V> entry) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.util.Iterator#next()
         */
        @Override
        public Map.Entry<K, V> next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Interface SupportRemove.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    interface SupportRemove<K, V> {

        /**
         * Support remove.
         *
         * @param entry the entry
         */
        void supportRemove(@NonNull Entry<K, V> entry);
    }

    /**
     * The Class Entry.
     *
     * @param <K> the key type
     * @param <V> the value type
     */
    static class Entry<K, V> implements Map.Entry<K, V> {

        /**
         * The m key.
         */
        @NonNull
        final K mKey;

        /**
         * The m value.
         */
        @NonNull
        final V mValue;

        /**
         * The m next.
         */
        Entry<K, V> mNext;

        /**
         * The m previous.
         */
        Entry<K, V> mPrevious;

        /**
         * Instantiates a new entry.
         *
         * @param key the key
         * @param value the value
         */
        Entry(@NonNull K key, @NonNull V value) {
            mKey = key;
            this.mValue = value;
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#getKey()
         */
        @NonNull
        @Override
        public K getKey() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#getValue()
         */
        @NonNull
        @Override
        public V getValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.util.Map.Entry#setValue(java.lang.Object)
         */
        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
