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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.exceptions.MethodParameterNotFoundException;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class SqlUtility.
 */
public class SqlUtility {

    /**
     * The Constant PARAMETER.
     */
    private static final Pattern PARAMETER = Pattern.compile(SqlAnalyzer.PARAM_PATTERN);

    /**
     * The Constant WORD.
     */
    private static final Pattern WORD = Pattern.compile("([_a-zA-Z]\\w*)");

    public static Pair<String, List<Pair<String, TypeName>>> extractParametersFromString(String value, SQLiteModelMethod method, SQLiteEntity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName defineCollection(TypeName listClazzName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
