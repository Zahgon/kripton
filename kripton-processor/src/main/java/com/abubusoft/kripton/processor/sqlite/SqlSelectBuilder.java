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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.android.sqlite.SqlUtils;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ModelMethod;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.SelectType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDynamicStatementType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLKeywords;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLPlaceHolder;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection.ProjectionType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplaceVariableStatementListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.uri.ContentUriPlaceHolder;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec.Builder;
import android.database.Cursor;
import android.net.Uri;

/**
 * The Class SqlSelectBuilder.
 */
public abstract class SqlSelectBuilder {

    public static String getNameParameterOfType(ModelMethod method, TypeName parameter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateSelect(Builder builder, SQLiteModelMethod method) throws ClassNotFoundException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class SplittedSql.
     */
    public static class SplittedSql {

        /**
         * The sql where statement.
         */
        public String sqlWhereStatement;

        /**
         * The sql order by statement.
         */
        public String sqlOrderByStatement;

        /**
         * The sql offset statement.
         */
        public String sqlOffsetStatement;

        /**
         * The sql limit statement.
         */
        public String sqlLimitStatement;

        /**
         * The sql having statement.
         */
        public String sqlHavingStatement;

        /**
         * The sql group statement.
         */
        public String sqlGroupStatement;

        /**
         * The sql basic.
         */
        public String sqlBasic;
    }

    /**
     * <p>
     * Generate select used in content provider class.
     * </p>
     *
     * @param builder
     *            the builder
     * @param method
     *            the method
     * @param selectResultType
     *            the select result type
     */
    private static void generateSelectForContentProvider(Builder builder, final SQLiteModelMethod method, SelectType selectResultType) {
        final SQLiteDaoDefinition daoDefinition = method.getParent();
        final SQLiteEntity entity = method.getEntity();
        final Set<String> columns = new LinkedHashSet<>();
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(method.contentProviderMethodName);
        if (!method.getParent().hasSamePackageOfSchema()) {
            methodBuilder.addModifiers(Modifier.PUBLIC);
        }
        // params
        methodBuilder.addParameter(ParameterSpec.builder(Uri.class, "uri").build());
        methodBuilder.addParameter(ParameterSpec.builder(ArrayTypeName.of(String.class), "projection").build());
        methodBuilder.addParameter(ParameterSpec.builder(String.class, "selection").build());
        methodBuilder.addParameter(ParameterSpec.builder(ArrayTypeName.of(String.class), "selectionArgs").build());
        methodBuilder.addParameter(ParameterSpec.builder(String.class, "sortOrder").build());
        methodBuilder.returns(Cursor.class);
        SqlBuilderHelper.generateLogForContentProviderBeginning(method, methodBuilder);
        JQLChecker jqlChecker = JQLChecker.getInstance();
        SplittedSql splittedSql = generateSQL(method, methodBuilder, true);
        List<JQLPlaceHolder> placeHolders = jqlChecker.extractFromVariableStatement(method, splittedSql.sqlWhereStatement);
        // remove placeholder for dynamic where, we are not interested here
        placeHolders = SqlBuilderHelper.removeDynamicPlaceHolder(placeHolders);
        AssertKripton.assertTrue(placeHolders.size() == method.contentProviderUriVariables.size(), "In '%s.%s' content provider URI path variables and variables in where conditions are different. If SQL uses parameters, they must be defined in URI path.", daoDefinition.getName(), method.getName());
        Set<JQLProjection> projectedColumns = jqlChecker.extractProjections(method, method.jql.value, entity);
        for (JQLProjection item : projectedColumns) {
            if (item.type == ProjectionType.COLUMN) {
                if (item.alias != null) {
                    columns.add(entity.get(item.alias.trim()).columnName);
                } else {
                    columns.add(entity.get(item.column.trim()).columnName);
                }
            } else {
                columns.add(item.expression.trim());
            }
        }
        methodBuilder.addStatement("$T _contentValues=contentValues()", KriptonContentValues.class);
        methodBuilder.addStatement("$T _sqlBuilder=sqlBuilder()", StringBuilder.class);
        SqlModifyBuilder.generateInitForDynamicWhereVariables(method, methodBuilder, "selection", "selectionArgs");
        methodBuilder.addStatement("$T _projectionBuffer=new $T()", StringBuilder.class, StringBuilder.class);
        if (method.jql.isOrderBy()) {
            methodBuilder.addStatement("String _sortOrder=sortOrder");
        }
        methodBuilder.addStatement("_sqlBuilder.append($S)", splittedSql.sqlBasic);
        SqlBuilderHelper.generateWhereCondition(methodBuilder, method, false);
        generateDynamicPartOfQuery(method, methodBuilder, splittedSql, false, "paginatedResult");
        // generate and check columns
        {
            methodBuilder.addCode("\n// manage projected columns\n");
            methodBuilder.addStatement("String _columnSeparator=\"\"");
            methodBuilder.beginControlFlow("if (projection!=null && projection.length>0)");
            // generate projected column check
            String columnCheckSetName = SqlBuilderHelper.generateColumnCheckSet(builder, method, columns);
            SqlBuilderHelper.forEachColumnInContentValue(methodBuilder, method, "projection", true, new OnColumnListener() {

                @Override
                public void onColumnCheck(MethodSpec.Builder methodBuilder, String projectedColumnVariable) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
            methodBuilder.nextControlFlow("else");
            methodBuilder.beginControlFlow("for (String column: $L)", columnCheckSetName);
            methodBuilder.addStatement("_projectionBuffer.append(_columnSeparator + column)");
            methodBuilder.addStatement("_columnSeparator=\", \"");
            methodBuilder.endControlFlow();
            methodBuilder.endControlFlow();
            int i = 0;
            // extract pathVariables
            // every controls was done in constructor of SQLiteModelMethod
            for (ContentUriPlaceHolder variable : method.contentProviderUriVariables) {
                AssertKripton.assertTrue(SqlBuilderHelper.validate(variable.value, placeHolders, i), "In '%s.%s' content provider URI path and where conditions must use same set of variables", daoDefinition.getName(), method.getName());
                SQLProperty entityProperty = entity.get(variable.value);
                TypeName methodParameterType = method.findParameterTypeByAliasOrName(variable.value);
                methodBuilder.addCode("// Add parameter $L at path segment $L\n", variable.value, variable.pathSegmentIndex);
                // methodBuilder.addStatement("_sqlWhereParams.add(uri.getPathSegments().get($L))",
                // variable.pathSegmentIndex);
                methodBuilder.addStatement("_contentValues.addWhereArgs(uri.getPathSegments().get($L))", variable.pathSegmentIndex);
                if (entityProperty != null) {
                    AssertKripton.assertTrue(TypeUtility.isTypeIncludedIn(entityProperty.getPropertyType().getTypeName(), String.class, Long.class, Long.TYPE), "In '%s.%s' content provider URI path variables %s must be String of Long type", daoDefinition.getName(), method.getName(), entityProperty.getName());
                } else if (methodParameterType != null) {
                    AssertKripton.assertTrue(TypeUtility.isTypeIncludedIn(methodParameterType, String.class, Long.class, Long.TYPE), "In '%s.%s' content provider URI path variables %s must be String of Long type", daoDefinition.getName(), method.getName(), method.findParameterNameByAlias(variable.value));
                }
                i++;
            }
        }
        // _sql must be always defined
        methodBuilder.addStatement("String _sql=String.format(_sqlBuilder.toString(), _projectionBuffer.toString())");
        SqlBuilderHelper.generateLogForSQL(method, methodBuilder);
        SqlBuilderHelper.generateLogForWhereParameters(method, methodBuilder);
        methodBuilder.addCode("\n// execute query\n");
        methodBuilder.addStatement("Cursor _result = getDatabase().query(_sql, _contentValues.whereArgsAsArray())");
        methodBuilder.addStatement("return _result");
        // we add at last javadoc, because need info is built at last.
        SqlBuilderHelper.generateJavaDocForContentProvider(method, methodBuilder);
        methodBuilder.addJavadoc("@param uri $S\n", method.contentProviderUriTemplate.replace("*", "[*]"));
        methodBuilder.addJavadoc("@param selection dynamic part of <code>where</code> statement $L\n", method.hasDynamicWhereConditions() ? "" : "<b>NOT USED</b>");
        methodBuilder.addJavadoc("@param selectionArgs arguments of dynamic part of <code>where</code> statement $L\n", method.hasDynamicWhereConditions() ? "" : "<b>NOT USED</b>");
        methodBuilder.addJavadoc("@return number of effected rows\n");
        builder.addMethod(methodBuilder.build());
    }

    public static void generateDynamicPartOfQuery(final SQLiteModelMethod method, MethodSpec.Builder methodBuilder, SplittedSql splittedSql, boolean countQuery, String pageRequestName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static SplittedSql generateSQL(final SQLiteModelMethod method, MethodSpec.Builder methodBuilder, final boolean replaceProjectedColumns) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String convertJQL2SQL(final SQLiteModelMethod method, final boolean replaceWithQuestion) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
