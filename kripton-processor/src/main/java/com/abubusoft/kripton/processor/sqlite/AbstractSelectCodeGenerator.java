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
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.android.sqlite.OnReadBeanListener;
import com.abubusoft.kripton.android.sqlite.OnReadCursorListener;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.SelectCodeGenerator;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.SelectType;
import com.abubusoft.kripton.processor.sqlite.SqlSelectBuilder.SplittedSql;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplaceVariableStatementListenerImpl;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class AbstractSelectCodeGenerator.
 */
public abstract class AbstractSelectCodeGenerator implements SelectCodeGenerator {

    /**
     * The Constant LIVE_DATA_PREFIX.
     */
    public static final String LIVE_DATA_PREFIX = "ForLiveData";

    public static final String PAGINATED_RESULT = "paginatedResult";

    /**
     * The Enum JavadocPartType.
     */
    public enum JavadocPartType {

        /**
         * The add parameter.
         */
        ADD_PARAMETER,
        /**
         * The return.
         */
        RETURN
    }

    protected void generateSubQueries(Builder methodBuilder, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class JavadocPart.
     */
    public static class JavadocPart {

        /**
         * The javadoc part type.
         */
        public final JavadocPartType javadocPartType;

        /**
         * The name.
         */
        public final String name;

        /**
         * The description.
         */
        public final String description;

        /**
         * Instantiates a new javadoc part.
         *
         * @param javadocPartType
         *            the javadoc part type
         * @param name
         *            the name
         * @param description
         *            the description
         */
        JavadocPart(JavadocPartType javadocPartType, String name, String description) {
            this.javadocPartType = javadocPartType;
            this.name = name;
            this.description = description;
        }

        public static JavadocPart build(JavadocPartType javadocPartType, String name, String description) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Enum GenerationType.
     */
    public enum GenerationType {

        /**
         * The all.
         */
        ALL(true, true, true),
        /**
         * The no close cursor.
         */
        NO_CLOSE_CURSOR(true, true, false),
        /**
         * The no method sign.
         */
        NO_METHOD_SIGN(false, true, true),
        /**
         * The no content.
         */
        NO_CONTENT(true, false, true);

        /**
         * Instantiates a new generation type.
         *
         * @param generateMethodSign
         *            the generate method sign
         * @param generateMethodContent
         *            the generate method content
         * @param generateCloseableCursor
         *            the generate closeable cursor
         */
        GenerationType(boolean generateMethodSign, boolean generateMethodContent, boolean generateCloseableCursor) {
            this.generateMethodSign = generateMethodSign;
            this.generateMethodContent = generateMethodContent;
            this.generateCloseableCursor = generateCloseableCursor;
        }

        /**
         * The generate method sign.
         */
        public final boolean generateMethodSign;

        /**
         * The generate method content.
         */
        public final boolean generateMethodContent;

        /**
         * The generate closeable cursor.
         */
        public final boolean generateCloseableCursor;
    }

    /**
     * The select type.
     */
    SelectType selectType;

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.
	 * SelectCodeGenerator#generate(com.squareup.javapoet.TypeSpec.Builder,
	 * boolean, com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod)
	 */
    @Override
    public void generate(TypeSpec.Builder classBuilder, boolean mapFields, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void generateLiveData(TypeSpec.Builder classBuilder, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateCommonPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, Set<JQLProjection> fieldList, boolean generateSqlField) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateCommonPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, Set<JQLProjection> fieldList, GenerationType generationType, TypeName forcedReturnType, boolean generateSqlField, boolean countQuery, String pageRequestName, JavadocPart... javadocParts) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void buildSelectInner(SQLiteModelMethod method, List<String> paramNames, List<String> paramGetters, List<TypeName> paramTypeNames, List<String> usedBeanPropertyNames, Set<String> usedMethodParameters, One<String> whereJQL, One<String> havingJQL, SqlAnalyzer analyzer) {
        analyzer.execute(BaseProcessor.elementUtils, method, whereJQL.value0);
        paramGetters.addAll(analyzer.getParamGetters());
        paramNames.addAll(analyzer.getParamNames());
        paramTypeNames.addAll(analyzer.getParamTypeNames());
        usedBeanPropertyNames.addAll(analyzer.getUsedBeanPropertyNames());
        usedMethodParameters.addAll(analyzer.getUsedMethodParameters());
        analyzer.execute(BaseProcessor.elementUtils, method, havingJQL.value0);
        paramGetters.addAll(analyzer.getParamGetters());
        paramNames.addAll(analyzer.getParamNames());
        paramTypeNames.addAll(analyzer.getParamTypeNames());
        usedBeanPropertyNames.addAll(analyzer.getUsedBeanPropertyNames());
        usedMethodParameters.addAll(analyzer.getUsedMethodParameters());
    }

    private void generateRawWhereArg(SQLiteModelMethod method, MethodSpec.Builder methodBuilder, TypeName paramTypeName, boolean nullable, String item) {
        generateRawWhereArg(method, methodBuilder, paramTypeName, nullable, item, item);
    }

    /**
     * @param method
     * @param methodBuilder
     * @param paramTypeName
     * @param nullable
     * @param methodItem
     *            is the name of method's parameter
     * @param item
     *            is the name of used parameter
     */
    private void generateRawWhereArg(SQLiteModelMethod method, MethodSpec.Builder methodBuilder, TypeName paramTypeName, boolean nullable, String methodItem, String item) {
        if (nullable && !method.hasAdapterForParam(methodItem)) {
            methodBuilder.addCode("($L==null?\"\":", item);
        }
        // check for string conversion
        TypeUtility.beginStringConversion(methodBuilder, paramTypeName);
        SQLTransformer.javaMethodParam2WhereConditions(methodBuilder, method, methodItem, item, paramTypeName);
        // check for string conversion
        TypeUtility.endStringConversion(methodBuilder, paramTypeName);
        if (nullable && !method.hasAdapterForParam(methodItem)) {
            methodBuilder.addCode(")");
        }
    }

    protected MethodSpec.Builder generateMethodBuilder(SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void generateMethodSignature(SQLiteModelMethod method, MethodSpec.Builder methodBuilder, TypeName returnTypeName, ParameterSpec... additionalParameterSpec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate specialized part.
     *
     * @param method
     *            the method
     * @param classBuilder
     *            the class builder
     * @param methodBuilder
     *            the method builder
     * @param fieldList
     *            the field list
     * @param mapFields
     *            the map fields
     */
    public abstract void generateSpecializedPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, Set<JQLProjection> fieldList, boolean mapFields);

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.
	 * SelectCodeGenerator#setSelectResultTye(com.abubusoft.kripton.processor.
	 * sqlite.SelectBuilderUtility.SelectType)
	 */
    @Override
    public void setSelectResultTye(SelectType value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkUnusedParameters(SQLiteModelMethod method, Set<String> usedMethodParameters, TypeName excludedClasses) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate SQL build.
     *
     * @param method
     *            the method
     * @param methodBuilder
     *            the method builder
     * @param splittedSql
     *            the splitted sql
     * @param countQuery
     *            is true, the query manage the count query for paged result
     */
    private static void generateSQLBuild(SQLiteModelMethod method, MethodSpec.Builder methodBuilder, SplittedSql splittedSql, boolean countQuery, String pageRequestName) {
        methodBuilder.addStatement("$T _sqlBuilder=sqlBuilder()", StringBuilder.class);
        methodBuilder.addStatement("_sqlBuilder.append($S)", splittedSql.sqlBasic.trim());
        SqlModifyBuilder.generateInitForDynamicWhereVariables(method, methodBuilder, method.dynamicWhereParameterName, method.dynamicWhereArgsParameterName);
        if (method.jql.isOrderBy()) {
            methodBuilder.addStatement("String _sortOrder=$L", method.jql.paramOrderBy);
        }
        SqlBuilderHelper.generateWhereCondition(methodBuilder, method, false);
        SqlSelectBuilder.generateDynamicPartOfQuery(method, methodBuilder, splittedSql, countQuery, pageRequestName);
    }
}
