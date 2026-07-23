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

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;
import java.util.Set;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.PrefsTypeAdapterUtils;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class SetPrefsTransformation.
 */
public class SetPrefsTransformation extends AbstractGeneratedPrefsTransform {

    /**
     * Instantiates a new sets the prefs transformation.
     */
    public SetPrefsTransformation() {
        super(true);
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sharedprefs.transform.AbstractGeneratedPrefsTransform#generateReadProperty(com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty, boolean)
	 */
    @Override
    public void generateReadProperty(Builder methodBuilder, String preferenceName, TypeName beanClass, String beanName, PrefsProperty property, boolean readAll, ReadType readType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sharedprefs.transform.AbstractGeneratedPrefsTransform#generateWriteProperty(com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty)
	 */
    @Override
    public void generateWriteProperty(Builder methodBuilder, String editorName, TypeName beanClass, String beanName, PrefsProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isStringSet(PrefsProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
