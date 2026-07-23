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
/**
 */
package com.abubusoft.kripton.processor.bind.transform;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import com.abubusoft.kripton.common.CollectionUtils;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.bind.BindTypeContext;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelClass;
import com.abubusoft.kripton.processor.core.ModelEntity;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.KriptonClassNotFoundException;
import com.abubusoft.kripton.xml.XmlAttributeUtils;
import com.abubusoft.kripton.xml.XmlPullParser;
import com.abubusoft.kripton.xml.EventType;
import com.fasterxml.jackson.core.JsonToken;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class AbstractCollectionBindTransform.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class AbstractCollectionBindTransform extends AbstractBindTransform {

    public static final String COLLECTION_ADD_ITEM = "collection.add(item)";

    public static final String COLLECTION_NEW = "$T<$T> collection=new $T<>()";

    public static final String ITEM_ASSIGN = "item=$L";

    public static final String ITEM_DEFINE = "$T item";

    public static final String INT_N_SIZE = "int n=$L.size()";

    public static final String FOR_INT_I_N = "for (int i=0; i<n; i++)";

    /**
     * The Enum CollectionType.
     */
    public enum CollectionType {

        /**
         * The array.
         */
        ARRAY,
        /**
         * The list.
         */
        LIST,
        /**
         * The set.
         */
        SET
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#
	 * isTypeAdapterSupported()
	 */
    @Override
    public boolean isTypeAdapterSupported() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The collection type.
     */
    protected CollectionType collectionType;

    /**
     * Instantiates a new abstract collection bind transform.
     *
     * @param clazz
     *            the clazz
     * @param collectionType
     *            the collection type
     */
    protected AbstractCollectionBindTransform(ParameterizedTypeName clazz, CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * Only for arrays.
     *
     * @param clazz
     *            the clazz
     * @param collectionType
     *            the collection type
     */
    protected AbstractCollectionBindTransform(TypeName clazz, CollectionType collectionType) {
        this.collectionType = collectionType;
    }

    /**
     * The collection clazz.
     */
    protected Class<?> collectionClazz = List.class;

    /**
     * The default clazz.
     */
    protected Class<?> defaultClazz = ArrayList.class;

    protected Class<?> defineCollectionClass(ParameterizedTypeName collectionTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#generateParseOnJackson(com.abubusoft.kripton.processor.bind.BindTypeContext, com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateParseOnJackson(BindTypeContext context, MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
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

    private void define(Builder methodBuilder, TypeName beanClass, String beanName, BindProperty property, TypeName elementTypeName) {
        methodBuilder.endControlFlow();
        methodBuilder.addStatement(COLLECTION_ADD_ITEM);
        methodBuilder.endControlFlow();
        if (collectionType == CollectionType.ARRAY) {
            if (TypeUtility.isTypePrimitive(elementTypeName)) {
                methodBuilder.addStatement(setter(beanClass, beanName, property, "$T.as$TTypeArray(collection)"), CollectionUtils.class, elementTypeName.box());
            } else if (TypeUtility.isTypeWrappedPrimitive(elementTypeName)) {
                methodBuilder.addStatement(setter(beanClass, beanName, property, "$T.as$TArray(collection)"), CollectionUtils.class, elementTypeName);
            } else {
                methodBuilder.addStatement(setter(beanClass, beanName, property, "$T.asArray(collection, new $T[collection.size()])"), CollectionUtils.class, elementTypeName);
            }
        } else {
            methodBuilder.addStatement(setter(beanClass, beanName, property, "collection"));
        }
    }

    protected TypeName convert(@SuppressWarnings("rawtypes") ModelEntity modelEntity, ClassName elementTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#
	 * generateParseOnXml(com.abubusoft.kripton.processor.bind.BindTypeContext,
	 * com.squareup.javapoet.MethodSpec.Builder, java.lang.String,
	 * com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateParseOnXml(BindTypeContext context, MethodSpec.Builder methodBuilder, String parserName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#
	 * generateSerializeOnJackson(com.abubusoft.kripton.processor.bind.
	 * BindTypeContext, com.squareup.javapoet.MethodSpec.Builder,
	 * java.lang.String, com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnJackson(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#
	 * generateSerializeOnJacksonAsString(com.abubusoft.kripton.processor.bind.
	 * BindTypeContext, com.squareup.javapoet.MethodSpec.Builder,
	 * java.lang.String, com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnJacksonAsString(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void generateSerializeOnJacksonInternal(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property, boolean onString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extract type parameter name.
     *
     * @param property
     *            the property
     * @return the type name
     */
    private TypeName extractTypeParameterName(BindProperty property) {
        return property.getPropertyType().getTypeParameter();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.bind.transform.BindTransform#
	 * generateSerializeOnXml(com.abubusoft.kripton.processor.bind.
	 * BindTypeContext, com.squareup.javapoet.MethodSpec.Builder,
	 * java.lang.String, com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.bind.model.BindProperty)
	 */
    @Override
    public void generateSerializeOnXml(BindTypeContext context, MethodSpec.Builder methodBuilder, String serializerName, TypeName beanClass, String beanName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
