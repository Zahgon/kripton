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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.MethodParameterNotFoundException;
import com.abubusoft.kripton.processor.exceptions.PropertyInAnnotationNotFoundException;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker.JQLParameterName;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.TypeName;

/**
 * Analyze an SQL statement, extract parameter and replace with ?.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class SqlAnalyzer {

    public static String PARAM_PREFIX = ":";

    public static String PARAM_SUFFIX = "";

    public static String PARAM_PATTERN = "(\\$\\{\\s*([\\w._]*)\\s*\\})|(\\:\\{\\s*([\\w._]*)\\s*\\})|(\\:\\s*([\\w._]*))";

    public static String extractParamName(Matcher matcher) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The parameter.
     */
    private final Pattern PARAMETER = Pattern.compile(PARAM_PATTERN);

    /**
     * The word.
     */
    private final Pattern WORD = Pattern.compile("([_a-zA-Z]\\w*)");

    /**
     * The property converter.
     */
    Converter<String, String> propertyConverter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL);

    /**
     * The param names.
     */
    private List<String> paramNames;

    /**
     * The param type names.
     */
    private List<TypeName> paramTypeNames;

    public List<TypeName> getParamTypeNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * bean properties typeName used into statement.
     */
    private List<String> usedBeanPropertyNames;

    public List<String> getParamNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<String> getParamGetters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The param getters.
     */
    private List<String> paramGetters;

    /**
     * The sql statement.
     */
    private String sqlStatement;

    /**
     * used method parameter.
     */
    private Set<String> usedMethodParameters;

    public Set<String> getUsedMethodParameters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void execute(Elements elementUtils, SQLiteModelMethod method, String sqlStatement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<String> getUsedBeanPropertyNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getter(ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String setter(ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getSQLStatement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
