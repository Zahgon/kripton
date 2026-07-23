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
package com.abubusoft.kripton;

import java.lang.reflect.ParameterizedType;
import com.abubusoft.kripton.exception.NoSuchMapperException;

/**
 * Binder Utils.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class BinderUtils {

    public static <T, M extends BinderMapper<T>> M mapperFor(Class<T> cls) throws NoSuchMapperException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E, M extends BinderMapper<E>> M mapperFor(ParameterizedType type) throws NoSuchMapperException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
