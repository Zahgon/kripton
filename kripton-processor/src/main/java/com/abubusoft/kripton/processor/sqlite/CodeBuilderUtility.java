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
package com.abubusoft.kripton.processor.sqlite;

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

/**
 * The Class CodeBuilderUtility.
 */
public abstract class CodeBuilderUtility {

    public static List<SQLProperty> extractUsedProperties(Builder methodBuilder, SQLiteModelMethod method, Class<? extends Annotation> annotationClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateContentValuesFromEntity(Elements elementUtils, SQLiteModelMethod method, Class<? extends Annotation> annotationClazz, Builder methodBuilder, List<String> alreadyUsedBeanPropertiesNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateContentValuesFromEntity(Elements elementUtils, SQLiteModelMethod method, String entityName, Class<? extends Annotation> annotationClazz, Builder methodBuilder, List<String> alreadyUsedBeanPropertiesNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
