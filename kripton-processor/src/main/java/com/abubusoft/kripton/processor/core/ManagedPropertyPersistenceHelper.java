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

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.className;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;
import java.util.List;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.KriptonBinder;
import com.abubusoft.kripton.KriptonJsonContext;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.KriptonByteArrayOutputStream;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.persistence.JacksonWrapperParser;
import com.abubusoft.kripton.persistence.JacksonWrapperSerializer;
import com.abubusoft.kripton.processor.bind.BindTypeContext;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.bind.transform.BindTransform;
import com.abubusoft.kripton.processor.bind.transform.BindTransformer;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

/**
 * The Class ManagedPropertyPersistenceHelper.
 */
public abstract class ManagedPropertyPersistenceHelper {

    /**
     * The Enum PersistType.
     */
    public enum PersistType {

        /**
         * The string.
         */
        STRING,
        /**
         * The byte.
         */
        BYTE
    }

    /**
     * The default field name.
     */
    public static String DEFAULT_FIELD_NAME = "element";

    public static void generateFieldPersistance(BindTypeContext context, List<? extends ManagedModelProperty> collection, PersistType persistType, boolean forceName, Modifier... modifiers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateFieldSerialize(BindTypeContext context, PersistType persistType, BindProperty property, Modifier... modifiers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateFieldParser(BindTypeContext context, PersistType persistType, BindProperty property, Modifier... modifiers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateParamSerializer(BindTypeContext context, String propertyName, TypeName parameterTypeName, PersistType persistType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateParamParser(BindTypeContext context, String methodName, TypeName parameterTypeName, PersistType persistType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
