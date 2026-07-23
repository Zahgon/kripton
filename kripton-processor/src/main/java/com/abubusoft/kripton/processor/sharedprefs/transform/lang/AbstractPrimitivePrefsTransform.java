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
package com.abubusoft.kripton.processor.sharedprefs.transform.lang;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;
import com.abubusoft.kripton.common.PrefsTypeAdapterUtils;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty;
import com.abubusoft.kripton.processor.sharedprefs.transform.AbstractPrefsTransform;
import com.abubusoft.kripton.processor.sharedprefs.transform.ReadType;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

/**
 * Transformer between a string and a Java Byte object.
 *
 * @author xcesco
 */
abstract class AbstractPrimitivePrefsTransform extends AbstractPrefsTransform {

    /**
     * Instantiates a new abstract primitive prefs transform.
     *
     * @param nullable
     *            the nullable
     */
    protected AbstractPrimitivePrefsTransform(boolean nullable) {
        super(true);
        this.nullable = nullable;
    }

    /**
     * Instantiates a new abstract primitive prefs transform.
     *
     * @param nullable
     *            the nullable
     * @param typeAware
     *            the type aware
     */
    protected AbstractPrimitivePrefsTransform(boolean nullable, boolean typeAware) {
        super(typeAware);
        this.nullable = nullable;
    }

    /**
     * The nullable.
     */
    protected boolean nullable;

    /**
     * The simple type.
     */
    protected String SIMPLE_TYPE;

    /**
     * The prefs convert.
     */
    protected String PREFS_CONVERT;

    /**
     * The prefs type.
     */
    protected String PREFS_TYPE;

    /**
     * The prefs default value.
     */
    protected String PREFS_DEFAULT_VALUE;

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abubusoft.kripton.processor.sharedprefs.transform.PrefsTransform#
	 * generateReadProperty(com.squareup.javapoet.MethodSpec.Builder,
	 * java.lang.String, com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty, boolean)
	 */
    @Override
    public void generateReadProperty(Builder methodBuilder, String preferenceName, TypeName beanClass, String beanName, PrefsProperty property, boolean readAll, ReadType readType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abubusoft.kripton.processor.sharedprefs.transform.PrefsTransform#
	 * generateWriteProperty(com.squareup.javapoet.MethodSpec.Builder,
	 * java.lang.String, com.squareup.javapoet.TypeName, java.lang.String,
	 * com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty)
	 */
    @Override
    public void generateWriteProperty(Builder methodBuilder, String editorName, TypeName beanClass, String beanName, PrefsProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
