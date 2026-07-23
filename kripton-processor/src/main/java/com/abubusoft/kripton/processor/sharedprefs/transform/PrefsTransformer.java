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
package com.abubusoft.kripton.processor.sharedprefs.transform;

import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.UnsupportedFieldTypeException;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty;
import com.abubusoft.kripton.processor.sharedprefs.transform.lang.*;
import com.abubusoft.kripton.processor.sharedprefs.transform.math.MathTransformations;
import com.abubusoft.kripton.processor.sharedprefs.transform.net.NetTransformations;
import com.abubusoft.kripton.processor.sharedprefs.transform.sql.SQLTransformations;
import com.abubusoft.kripton.processor.sharedprefs.transform.time.TimeTransformations;
import com.abubusoft.kripton.processor.sharedprefs.transform.util.UtilsTransformations;
import com.google.common.collect.Lists;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.abubusoft.kripton.common.Pair.of;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;

/**
 * Transformer for java primitive types and frequently used java types.
 *
 * @author xcesco
 */
public abstract class PrefsTransformer {

    private static final List<Pair<String, List<Pair<Class<?>, Class<? extends PrefsTransform>>>>> transformations = Lists.newArrayList(of("java.lang", LangTransformations.transformations), of("java.util", UtilsTransformations.transformations), of("java.math", MathTransformations.transformations), of("java.net", NetTransformations.transformations), of("java.sql", SQLTransformations.transformations), of("java.time", TimeTransformations.transformations));

    /**
     * cache for transform.
     */
    private static final Map<TypeName, PrefsTransform> cache = new ConcurrentHashMap<TypeName, PrefsTransform>();

    public static void register(TypeName type, PrefsTransform transform) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static PrefsTransform lookup(PrefsProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static PrefsTransform getSupportedTransformations(TypeName typeName, List<Pair<Class<?>, Class<? extends PrefsTransform>>> transformations) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static PrefsTransform lookup(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the transform.
     *
     * @param typeName the type name
     * @return the transform
     */
    private static PrefsTransform getTransform(TypeName typeName) {
        if (typeName.isPrimitive()) {
            return getPrimitiveTransform(typeName);
        }
        if (typeName instanceof ArrayTypeName) {
            ArrayTypeName typeNameArray = (ArrayTypeName) typeName;
            TypeName componentTypeName = typeNameArray.componentType;
            if (TypeUtility.isEquals(componentTypeName, Byte.TYPE.toString())) {
                return new ByteArrayPrefsTransform();
            } else {
                return new ArrayPrefsTransform();
            }
        } else if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            if (TypeUtility.isList(parameterizedTypeName)) {
                return new ListPrefsTransformation();
            } else if (TypeUtility.isSet(parameterizedTypeName)) {
                return new SetPrefsTransformation();
            } else if (TypeUtility.isMap(parameterizedTypeName)) {
                return new MapPrefsTransformation();
            }
        }
        if (TypeUtility.isEnum(typeName)) {
            return new EnumPrefsTransform(typeName);
        }
        // for default is treated as object
        String name = typeName.toString();
        List<Pair<Class<?>, Class<? extends PrefsTransform>>> values = transformations.stream().filter(item -> name.startsWith(item.value0)).findFirst().map(item -> item.value1).orElse(null);
        if (values != null) {
            return getSupportedTransformations(typeName, values);
        } else {
            return new ObjectPrefsTransform();
        }
    }

    static PrefsTransform getPrimitiveTransform(TypeName type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
