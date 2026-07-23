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
package com.abubusoft.kripton.processor.sqlite.transform;

import com.abubusoft.kripton.android.ColumnAffinityType;
import com.abubusoft.kripton.android.annotation.BindSqlParam;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.SQLTypeAdapterUtils;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.TypeAdapterHelper;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.lang.*;
import com.abubusoft.kripton.processor.sqlite.transform.math.MathTransformations;
import com.abubusoft.kripton.processor.sqlite.transform.net.NetTransformations;
import com.abubusoft.kripton.processor.sqlite.transform.net.UrlSQLTransform;
import com.abubusoft.kripton.processor.sqlite.transform.sql.SQLDateSQLTransform;
import com.abubusoft.kripton.processor.sqlite.transform.sql.SQLTimeSQLTransform;
import com.abubusoft.kripton.processor.sqlite.transform.sql.SQLTransformations;
import com.abubusoft.kripton.processor.sqlite.transform.time.TimeTransformations;
import com.abubusoft.kripton.processor.sqlite.transform.util.UtilsTransformations;
import com.google.common.collect.Lists;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static com.abubusoft.kripton.common.Pair.of;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;

/**
 * Transformer for java primitive types and frequently used java types.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class SQLTransformer {

    /**
     * cache for transform.
     */
    private static final Map<TypeName, SQLTransform> cache = new ConcurrentHashMap<TypeName, SQLTransform>();

    private static final List<Pair<String, List<Pair<Class<?>, Class<? extends SQLTransform>>>>> transformations = Lists.newArrayList(of("java.lang", LangTransformations.transformations), of("java.util", UtilsTransformations.transformations), of("java.math", MathTransformations.transformations), of("java.net", NetTransformations.transformations), of("java.sql", SQLTransformations.transformations), of("java.time", TimeTransformations.transformations));

    static SQLTransform getSupportedTransformations(TypeName typeName, List<Pair<Class<?>, Class<? extends SQLTransform>>> transformations) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void cursor2Java(SQLiteEntity tableEntity, MethodSpec.Builder methodBuilder, TypeName beanClass, ModelProperty property, String beanName, String cursorName, String indexName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void javaProperty2ContentValues(MethodSpec.Builder methodBuilder, TypeName beanClass, String beanName, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void javaProperty2WhereCondition(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, String paramName, TypeName paramType, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void javaMethodParam2ContentValues(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, String paramName, TypeName paramType, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void checkTypeAdapterForParam(SQLiteModelMethod method, String methodParamName, Class<? extends Annotation> annotation) {
        TypeName paramType = method.findParameterType(methodParamName);
        if (method.isSpreadParameter(methodParamName)) {
            if (paramType instanceof ArrayTypeName) {
                paramType = ((ArrayTypeName) paramType).componentType;
            } else if (paramType instanceof ParameterizedTypeName) {
                paramType = ((ParameterizedTypeName) paramType).typeArguments.get(0);
            }
        }
        TypeName adapterType = method.getAdapterForParam(methodParamName);
        TypeName sourceType = TypeUtility.typeName(TypeAdapterHelper.detectSourceType(method, adapterType));
        TypeName uboxSourceType = sourceType;
        if (TypeUtility.isTypeWrappedPrimitive(sourceType)) {
            uboxSourceType = sourceType.unbox();
        }
        boolean expr = uboxSourceType.toString().equals(paramType.toString()) || sourceType.toString().equals(paramType.toString());
        AssertKripton.fail(!expr, "In DAO '%s', method '%s' has parameter '%s' uses @%s that manages type '%s' instead of '%s'", method.getParent().getName(), method.getName(), methodParamName, annotation.getSimpleName(), sourceType, paramType);
    }

    public static void javaMethodParam2WhereConditions(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, String methodParamName, String paramName, TypeName paramType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void javaMethodParam2WhereConditions(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, String paramName, TypeName paramType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLTransform lookup(TypeMirror typeMirror) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SQLTransform lookup(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the transform.
     *
     * @param typeName
     *            the type name
     * @return the transform
     */
    private static SQLTransform getTransform(TypeName typeName) {
        if (typeName.isPrimitive()) {
            return getPrimitiveTransform(typeName);
        }
        if (typeName instanceof ArrayTypeName) {
            ArrayTypeName typeNameArray = (ArrayTypeName) typeName;
            TypeName componentTypeName = typeNameArray.componentType;
            if (TypeUtility.isEquals(componentTypeName, Byte.TYPE.toString())) {
                return new ByteArraySQLTransform();
            } else {
                return new ArraySQLTransform();
            }
        } else if (typeName instanceof ParameterizedTypeName) {
            ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
            if (TypeUtility.isList(parameterizedTypeName)) {
                return new ListSQLTransformation();
            } else if (TypeUtility.isSet(parameterizedTypeName)) {
                return new SetSQLTransformation();
            } else if (TypeUtility.isMap(parameterizedTypeName)) {
                return new MapSQLTransformation();
            }
        }
        if (TypeUtility.isEnum(typeName)) {
            return new EnumSQLTransform(typeName);
        }
        // for default is treated as object
        String name = typeName.toString();
        List<Pair<Class<?>, Class<? extends SQLTransform>>> values = getSupportedTransformations(name);
        if (values != null) {
            return getSupportedTransformations(typeName, values);
        } else {
            return new ObjectSQLTransform();
        }
    }

    private static List<Pair<Class<?>, Class<? extends SQLTransform>>> getSupportedTransformations(String name) {
        List<Pair<Class<?>, Class<? extends SQLTransform>>> values = transformations.stream().filter(item -> name.startsWith(item.value0)).findFirst().map(item -> item.value1).orElse(null);
        return values;
    }

    static SQLTransform getSqlTransform(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static SQLTransform getNetTransform(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isSupportedJDKType(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static SQLTransform getPrimitiveTransform(TypeName type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void resetBean(MethodSpec.Builder methodBuilder, TypeName beanClass, String beanName, ModelProperty property, String cursorName, String indexName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String columnTypeAsString(SQLProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
