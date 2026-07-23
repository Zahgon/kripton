/**
 * ****************************************************************************
 *  Copyright 2015, 2016 Francesco Benincasa.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.abubusoft.kripton.android;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.abubusoft.kripton.KriptonVersion;
import android.content.Context;

/**
 * Used to initialize library
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public final class KriptonLibrary {

    private KriptonLibrary() {
    }

    public static final String VERSION = KriptonVersion.VERSION;

    public static Context getContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ExecutorService getExecutorService() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The context.
     */
    private static Context context;

    /**
     * The executer service.
     */
    private static ExecutorService executerService;

    /**
     * The Constant THREAD_POOL_SIZE_DEFAULT.
     */
    public static final int THREAD_POOL_SIZE_DEFAULT = 3;

    public static void init(Context contextValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void init(Context contextValue, ExecutorService service) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
