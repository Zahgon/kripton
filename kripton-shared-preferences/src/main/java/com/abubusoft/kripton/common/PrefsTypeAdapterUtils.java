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
package com.abubusoft.kripton.common;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import com.abubusoft.kripton.android.sharedprefs.PreferenceTypeAdapter;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * The Class PrefsTypeAdapterUtils.
 */
public abstract class PrefsTypeAdapterUtils {

    /**
     * The lock.
     */
    static ReentrantLock lock = new ReentrantLock();

    /**
     * The cache.
     */
    @SuppressWarnings("rawtypes")
    private static HashMap<Class<? extends PreferenceTypeAdapter>, PreferenceTypeAdapter> cache = new HashMap<>();

    public static <D, J> J toJava(Class<? extends PreferenceTypeAdapter<J, D>> clazz, D value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends PreferenceTypeAdapter<?, ?>> E getAdapter(Class<E> clazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate adapter.
     *
     * @param <E> the element type
     * @param clazz the clazz
     * @return the e
     */
    private static <E extends PreferenceTypeAdapter<?, ?>> E generateAdapter(Class<E> clazz) {
        E adapter;
        try {
            lock.lock();
            adapter = clazz.newInstance();
            cache.put(clazz, adapter);
        } catch (Throwable e) {
            throw (new KriptonRuntimeException(e));
        } finally {
            lock.unlock();
        }
        return adapter;
    }

    @SuppressWarnings("unchecked")
    public static <D, J> D toData(Class<? extends PreferenceTypeAdapter<J, D>> clazz, J javaValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
