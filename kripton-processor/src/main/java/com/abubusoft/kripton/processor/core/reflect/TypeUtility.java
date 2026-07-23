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
package com.abubusoft.kripton.processor.core.reflect;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor7;
import javax.lang.model.util.Types;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.BindTypeSubProcessor;
import com.abubusoft.kripton.processor.core.ModelClass;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransform;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.abubusoft.kripton.processor.utils.LiteralType;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class TypeUtility.
 */
public abstract class TypeUtility {

    public static boolean isTypeIncludedIn(TypeName value, Type... types) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isTypeEquals(TypeName value, TypeName value2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isTypePrimitive(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isTypeWrappedPrimitive(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isEquals(TypeName value, String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isByteArray(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isString(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isNullable(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isEquals(TypeName value, ModelClass<?> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isEquals(TypeName value, TypeName kindOfParameter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName classNameWithSuffix(String packageName, String className, String suffix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName className(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName className(Class<?> clazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName typeName(Type type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName typeName(TypeMirror typeMirror) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName typeName(String packageName, String typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName mergeTypeNameWithSuffix(TypeName typeName, String typeNameSuffix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName typeName(String typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName typeName(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isNullable(ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkTypeCompatibility(SQLiteModelMethod method, Pair<String, TypeName> item, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void beginStringConversion(Builder methodBuilder, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void beginStringConversion(Builder methodBuilder, TypeName typeMirror) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void endStringConversion(Builder methodBuilder, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void endStringConversion(Builder methodBuilder, TypeName typeMirror) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isArray(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if is array.
     *
     * @param typeName
     *            the type name
     * @return true, if is array
     */
    //	public static boolean isArrayOfType(TypeName typeName, TypeName elementTypeName) {
    //		if (typeName instanceof ArrayTypeName && isEquals(((ArrayTypeName) typeName).componentType, elementTypeName)) {
    //			return true;
    //		}
    //
    //		return false;
    //	}
    public static TypeName typeName(TypeElement element, String suffix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName className(String packageName, String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName parameterizedTypeName(ClassName rawClass, TypeName paramClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String simpleName(TypeName clazzName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ArrayTypeName arrayTypeName(Type type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isEnum(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Constant JAVA_LANG_ENUM.
     */
    private static final String JAVA_LANG_ENUM = "java.lang.Enum<?>";

    public static boolean isEnum(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert.
     *
     * @param input
     *            the input
     * @return the list
     */
    private static List<TypeName> convert(List<? extends TypeMirror> input) {
        List<TypeName> result = new ArrayList<TypeName>();
        for (TypeMirror item : input) {
            result.add(TypeUtility.typeName(item));
        }
        return result;
    }

    public static List<TypeName> getTypeArguments(TypeElement element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a string with type parameters replaced with wildcards. This is slightly different from {@link Types#erasure(javax.lang.model.type.TypeMirror)}, which removes all
     * type parameter data.
     *
     * For instance, if there is a field with type List&lt;String&gt;, this returns a string List&lt;?&gt;.
     *
     * @param declaredType
     *            the declared type
     * @return the canonical type name
     */
    private static String getCanonicalTypeName(DeclaredType declaredType) {
        List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
        if (!typeArguments.isEmpty()) {
            StringBuilder typeString = new StringBuilder(declaredType.asElement().toString());
            typeString.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                if (i > 0) {
                    typeString.append(',');
                }
                typeString.append('?');
            }
            typeString.append('>');
            return typeString.toString();
        } else {
            return declaredType.toString();
        }
    }

    public static boolean isCollection(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isCollectionOfType(TypeName typeName, TypeName elementTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isList(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isSet(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isMap(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isAssignable(TypeName typeName, Class<?> assignableClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String extractPackageName(TypeElement element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getDefaultValue(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
