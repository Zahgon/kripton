/**
 * ****************************************************************************
 *  Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.abubusoft.kripton.processor.bind.transform;

import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.bind.transform.lang.*;
import com.abubusoft.kripton.processor.bind.transform.math.MathTransformations;
import com.abubusoft.kripton.processor.bind.transform.net.NetTransformations;
import com.abubusoft.kripton.processor.bind.transform.sql.SQLTransformations;
import com.abubusoft.kripton.processor.bind.transform.time.TimeTransformations;
import com.abubusoft.kripton.processor.bind.transform.util.UtilsTransformations;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.UnsupportedFieldTypeException;
import com.google.common.collect.Lists;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import static com.abubusoft.kripton.common.Pair.of;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;

/**
 * Transformer for java primitive types and frequently used java types.
 */
public abstract class BindTransformer {

    private static final List<Pair<String, List<Pair<Class<?>, Class<? extends BindTransform>>>>> transformations = Lists.newArrayList(of("java.lang", LangTransformations.transformations), of("java.util", UtilsTransformations.transformations), of("java.math", MathTransformations.transformations), of("java.net", NetTransformations.transformations), of("java.sql", SQLTransformations.transformations), of("java.time", TimeTransformations.transformations));

    static final Set<String> unsupportedPackage = new HashSet<>(Arrays.asList("java.", "javax.", "android.", "androidx."));

    /**
     * cache for transform.
     */
    private static final Map<TypeName, BindTransform> cache = new ConcurrentHashMap<TypeName, BindTransform>();

    public static BindTransform lookup(BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBindedObject(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isBindedObject(BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isInUnsupportedPackage(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static BindTransform lookup(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkIfIsInUnsupportedPackage(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static BindTransform getTransform(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static BindTransform getSupportedTransformations(TypeName typeName, List<Pair<Class<?>, Class<? extends BindTransform>>> transformations) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static BindTransform getPrimitiveTransform(TypeName type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
