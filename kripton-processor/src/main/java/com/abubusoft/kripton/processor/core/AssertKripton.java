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
package com.abubusoft.kripton.processor.core;

import java.lang.annotation.Annotation;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import com.abubusoft.kripton.android.annotation.BindSqlType;
import com.abubusoft.kripton.android.annotation.BindSqlTransaction;
import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.ForeignKeyNotFoundException;
import com.abubusoft.kripton.processor.exceptions.IncompatibleAttributesInAnnotationException;
import com.abubusoft.kripton.processor.exceptions.InvalidDefinition;
import com.abubusoft.kripton.processor.exceptions.InvalidKindForAnnotationException;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.exceptions.InvalidPropertyToColumnConversion;
import com.abubusoft.kripton.processor.exceptions.InvalidTypeForAnnotationException;
import com.abubusoft.kripton.processor.exceptions.KriptonProcessorException;
import com.abubusoft.kripton.processor.exceptions.MissedAnnotationOnClass;
import com.abubusoft.kripton.processor.exceptions.PropertyVisibilityException;
import com.abubusoft.kripton.processor.exceptions.UnknownClassInJQLException;
import com.abubusoft.kripton.processor.exceptions.UnknownParamUsedInJQLException;
import com.abubusoft.kripton.processor.exceptions.UnknownPropertyInJQLException;
import com.abubusoft.kripton.processor.exceptions.UnsupportedFieldTypeException;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * The Class AssertKripton.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class AssertKripton {

    public static void assertTrue(boolean expression, String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidMethodSignException(boolean expression, SQLiteModelMethod method, String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidMethodSignException(boolean expression, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void failWithInvalidMethodSignException(boolean expression, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void failWithInvalidMethodSignException(boolean expression, SQLiteModelMethod method, String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void fail(String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void failIncompatibleAttributesInAnnotationException(String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void fail(boolean expression, String messageFormat, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrUnsupportedFieldTypeException(boolean expression, TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidKindForAnnotationException(boolean expression, Element element, Class<? extends Annotation> annotationClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidTypeForAnnotationMethodParameterException(boolean expression, Element classElement, ExecutableElement methodElement, VariableElement parameterElement, Class<? extends Annotation> annotationClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertNotNull(Object value, KriptonProcessorException exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrUnknownPropertyInJQLException(boolean expression, JQLContext method, String columnName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrUnknownClassInJQLException(boolean expression, SQLiteModelMethod method, String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrUnknownParamInJQLException(boolean expression, SQLiteModelMethod method, String paramName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void failUnknownPropertyInJQLException(SQLiteModelMethod method, Class<? extends Annotation> annotationClazz, AnnotationAttributeType attribute, String fieldName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidPropertyName(boolean expression, SQLProperty item1, SQLProperty item2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void asserTrueOrForeignKeyNotFound(boolean expression, SQLiteEntity currentEntity, ClassName entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void asserTrueOrMissedAnnotationOnClassException(boolean expression, TypeElement daoElement, String entityName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void asserTrueOrUnspecifiedBeanException(boolean expression, SQLiteDatabaseSchema schema, SQLiteEntity entity, String foreignClassName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOfInvalidDefinition(boolean expression, ModelProperty property, String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOfInvalidDefinition(boolean expression, SQLProperty property, String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidGlobalTypeApdaterException(boolean expression, SQLiteDatabaseSchema sqLiteDatabaseSchema, String typeAdapter, String typeAdapter2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOfInvalidConstructor(boolean expression, ModelClass<?> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOfInvalidConstructorProperty(boolean expression, ModelClass<?> entity, String fieldName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOfInvalidWritable(boolean expression, ModelClass<?> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void assertTrueOrInvalidMethodSignException(boolean expression, SQLiteDatabaseSchema sqLiteDatabaseSchema, ExecutableElement methodElement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
