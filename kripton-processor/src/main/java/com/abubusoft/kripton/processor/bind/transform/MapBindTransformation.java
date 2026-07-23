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

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.processor.bind.BindTypeContext;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.xml.EventType;
import com.abubusoft.kripton.xml.MapEntryType;
import com.fasterxml.jackson.core.JsonToken;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class MapBindTransformation.
 */
public class MapBindTransformation extends AbstractBindTransform {

    /**
     * The nc.
     */
    static Converter<String, String> nc = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL);

    /**
     * Instantiates a new map bind transformation.
     *
     * @param clazz the clazz
     */
    public MapBindTransformation(ParameterizedTypeName clazz) {
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#isTypeAdapterSupported()
	 */
    public boolean isTypeAdapterSupported() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Define map class.
     *
     * @param mapTypeName the map type name
     * @return the class
     */
    private Class<?> defineMapClass(ParameterizedTypeName mapTypeName) {
        if (mapTypeName.rawType.toString().startsWith(Map.class.getCanonicalName())) {
            // it's a list
            return HashMap.class;
        } else if (mapTypeName.rawType.toString().startsWith(SortedMap.class.getCanonicalName())) {
            return TreeMap.class;
        }
        try {
            return Class.forName(mapTypeName.rawType.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateParseOnXml(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateParseOnXml(BindTypeContext context, MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateSerializeOnXml(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnXml(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateSerializeOnJackson(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnJackson(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateSerializeOnJacksonAsString(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnJacksonAsString(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void generateSerializeOnJacksonInternal(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property, boolean onString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateParseOnJackson(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateParseOnJackson(BindTypeContext context, Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateParseOnJacksonAsString(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateParseOnJacksonAsString(BindTypeContext context, MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateParseOnJacksonInternal(BindTypeContext context, Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property, boolean onString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
