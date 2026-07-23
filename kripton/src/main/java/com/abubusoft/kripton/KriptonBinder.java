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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * The Class KriptonBinder.
 *
 * @author xcesco
 */
public abstract class KriptonBinder {

    /**
     * work costant.
     */
    public static final String MAPPER_CLASS_SUFFIX = "BindMap";

    /**
     * The Constant jsonBinderContext.
     */
    private static final KriptonJsonContext jsonBinderContext = new KriptonJsonContext();

    /**
     * The Constant xmlBinderContext.
     */
    private static final KriptonXmlContext xmlBinderContext = new KriptonXmlContext();

    public static void registryBinder(BinderContext factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * map of registered binder. JSON and XML are automatically registered.
     */
    private static final Map<BinderType, BinderContext> binders = new HashMap<>();

    static {
        registryBinder(jsonBinderContext);
        registryBinder(xmlBinderContext);
        ServiceLoader<BinderContext> codecSetLoader = ServiceLoader.load(BinderContext.class);
        for (BinderContext context : codecSetLoader) {
            registryBinder(context);
        }
    }

    public static KriptonJsonContext jsonBind() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KriptonXmlContext xmlBind() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static BinderContext bind(BinderType format) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
