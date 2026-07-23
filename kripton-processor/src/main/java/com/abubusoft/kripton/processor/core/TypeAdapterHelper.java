/**
 * ****************************************************************************
 *  Copyright 2016-2019 Francesco Benincasa (info@abubusoft.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * ****************************************************************************
 */
package com.abubusoft.kripton.processor.core;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.TypeName;

/**
 * The Class TypeAdapterHelper.
 */
public class TypeAdapterHelper {

    public static String detectSourceType(String adapterClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String detectSourceType(Element element, String adapterClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String detectSourceType(SQLiteModelMethod method, TypeName adapterTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String detectDestinationType(Element element, String adapterClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
