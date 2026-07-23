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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.annotation.BindSqlDelete;
import com.abubusoft.kripton.android.annotation.BindSqlUpdate;
import com.abubusoft.kripton.android.sqlite.ConflictAlgorithmType;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.sqlite.GenericSQLHelper.SubjectType;
import com.abubusoft.kripton.processor.sqlite.core.GenerationPartMarks;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDynamicStatementType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker.JQLParameterName;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLPlaceHolder;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplaceVariableStatementListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmtContext;
import com.abubusoft.kripton.processor.sqlite.grammars.uri.ContentUriPlaceHolder;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import android.content.ContentValues;
import android.net.Uri;

/**
 * The Class SqlModifyBuilder.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @since 05/mag/2016
 */
public abstract class SqlModifyBuilder {

    /**
     * The Enum ModifyType.
     */
    public enum ModifyType {

        /**
         * The update bean.
         */
        UPDATE_BEAN(ModifyBeanHelper.class, true),
        /**
         * The update raw.
         */
        UPDATE_RAW(ModifyRawHelper.class, true),
        /**
         * The delete bean.
         */
        DELETE_BEAN(ModifyBeanHelper.class, false),
        /**
         * The delete raw.
         */
        DELETE_RAW(ModifyRawHelper.class, false);

        /**
         * The code generator.
         */
        private ModifyCodeGenerator codeGenerator;

        /**
         * The update.
         */
        private boolean update;

        public boolean isUpdate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Instantiates a new modify type.
         *
         * @param codeGenerator
         *            the code generator
         * @param updateValue
         *            the update value
         */
        private ModifyType(Class<? extends ModifyCodeGenerator> codeGenerator, boolean updateValue) {
            try {
                this.update = updateValue;
                this.codeGenerator = codeGenerator.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                throw new KriptonRuntimeException(e);
            }
        }

        public void generate(TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, SQLiteModelMethod method, TypeName returnType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Interface ModifyCodeGenerator.
     */
    public interface ModifyCodeGenerator {

        /**
         * Generate.
         *
         * @param classBuilder
         *            the class builder
         * @param methodBuilder
         *            the method builder
         * @param mapFields
         *            the map fields
         * @param method
         *            the method
         * @param returnType
         *            the return type
         */
        void generate(TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, boolean mapFields, SQLiteModelMethod method, TypeName returnType);
    }

    public static void generate(TypeSpec.Builder classBuilder, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ModifyType detectModifyType(SQLiteModelMethod method, JQLType jqlType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>
     * Generate update and delete used in content provider class.
     * </p>
     *
     * @param elementUtils
     *            the element utils
     * @param builder
     *            the builder
     * @param method
     *            the method
     * @param updateResultType
     *            the update result type
     */
    private static void generateModifierForContentProvider(Elements elementUtils, Builder builder, final SQLiteModelMethod method, ModifyType updateResultType) {
        final SQLiteDaoDefinition daoDefinition = method.getParent();
        final SQLiteEntity entity = method.getEntity();
        final Set<String> columns = new LinkedHashSet<>();
        JQLChecker jqlChecker = JQLChecker.getInstance();
        // parameters extracted from query
        final One<String> whereStatement = new One<>();
        if (method.jql.isWhereConditions()) {
            // parameters extracted from query
            final One<Boolean> alreadyFoundWhereStatement = new One<>(false);
            // put in whereStatement value of where statement.
            jqlChecker.replaceVariableStatements(method, method.jql.value, new JQLReplaceVariableStatementListenerImpl() {

                @Override
                public String onWhere(String statement) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
        }
        List<JQLPlaceHolder> placeHolders = jqlChecker.extractFromVariableStatement(method, whereStatement.value0);
        // remove placeholder for dynamic where, we are not interested here
        placeHolders = SqlBuilderHelper.removeDynamicPlaceHolder(placeHolders);
        checkContentProviderVarsAndArguments(method, placeHolders);
        // detect column used for content value
        jqlChecker.replace(method, method.jql, new JQLReplacerListenerImpl(method) {

            @Override
            public String onColumnNameToUpdate(String columnName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(method.contentProviderMethodName);
        if (!method.getParent().hasSamePackageOfSchema()) {
            methodBuilder.addModifiers(Modifier.PUBLIC);
        }
        // params
        methodBuilder.addParameter(ParameterSpec.builder(Uri.class, "uri").build());
        if (updateResultType == ModifyType.UPDATE_BEAN || updateResultType == ModifyType.UPDATE_RAW) {
            methodBuilder.addParameter(ParameterSpec.builder(ContentValues.class, "contentValues").build());
        }
        methodBuilder.addParameter(ParameterSpec.builder(String.class, "selection").build());
        methodBuilder.addParameter(ParameterSpec.builder(ArrayTypeName.of(String.class), "selectionArgs").build());
        methodBuilder.returns(Integer.TYPE);
        // retrieve content values
        if (updateResultType == ModifyType.UPDATE_BEAN || updateResultType == ModifyType.UPDATE_RAW) {
            methodBuilder.addStatement("$T _contentValues=contentValuesForContentProvider(contentValues)", KriptonContentValues.class);
        } else {
            methodBuilder.addStatement("$T _contentValues=contentValues()", KriptonContentValues.class);
        }
        SqlBuilderHelper.generateLogForContentProviderBeginning(method, methodBuilder);
        // query builder
        methodBuilder.addStatement("$T _sqlBuilder=sqlBuilder()", StringBuilder.class);
        generateInitForDynamicWhereVariables(method, methodBuilder, "selection", "selectionArgs");
        SqlBuilderHelper.generateWhereCondition(methodBuilder, method, false);
        int i = 0;
        // extract pathVariables
        // every controls was done in constructor of SQLiteModelMethod
        for (ContentUriPlaceHolder variable : method.contentProviderUriVariables) {
            AssertKripton.assertTrue(SqlBuilderHelper.validate(variable.value, placeHolders, i), "In '%s.%s' content provider URI path variables and variables in where conditions are different. If SQL uses parameters, they must be defined in URI path.", daoDefinition.getName(), method.getName());
            JQLParameterName paramName = JQLParameterName.parse(variable.value);
            SQLProperty entityProperty = entity.get(paramName.getValue());
            if (entityProperty != null) {
                methodBuilder.addCode("// Add parameter $L at path segment $L\n", variable.value, variable.pathSegmentIndex);
                // methodBuilder.addStatement("_sqlWhereParams.add(uri.getPathSegments().get($L))",
                // variable.pathSegmentIndex);
                methodBuilder.addStatement("_contentValues.addWhereArgs(uri.getPathSegments().get($L))", variable.pathSegmentIndex);
                AssertKripton.assertTrue(TypeUtility.isTypeIncludedIn(entityProperty.getPropertyType().getTypeName(), String.class, Long.class, Long.TYPE), "In '%s.%s' content provider URI path variables %s must be String of Long type", daoDefinition.getName(), method.getName(), entityProperty.getName());
            }
            i++;
        }
        if (method.hasDynamicWhereConditions() && method.hasDynamicWhereArgs()) {
            // ASSERT: only with dynamic where conditions
            methodBuilder.beginControlFlow("if ($T.hasText(_sqlDynamicWhere) && _sqlDynamicWhereArgs!=null)", StringUtils.class);
            if (method.hasDynamicWhereConditions()) {
                methodBuilder.beginControlFlow("for (String _arg: _sqlDynamicWhereArgs)");
                // methodBuilder.addStatement("_sqlWhereParams.add(_arg)");
                methodBuilder.addStatement("_contentValues.addWhereArgs(_arg)");
                methodBuilder.endControlFlow();
            }
            methodBuilder.endControlFlow();
        }
        // column checj
        switch(updateResultType) {
            case UPDATE_BEAN:
            case UPDATE_RAW:
                SqlBuilderHelper.generateColumnCheckSet(builder, method, columns);
                SqlBuilderHelper.forEachColumnInContentValue(methodBuilder, method, "_contentValues.values().keySet()", true, null);
                break;
            default:
                break;
        }
        // generate log section - BEGIN
        methodBuilder.addComment("log section BEGIN");
        methodBuilder.beginControlFlow("if (_context.isLogEnabled())");
        generateLogForModifiers(method, methodBuilder);
        if (method.jql.operationType == JQLType.UPDATE) {
            // generate log for content values
            SqlBuilderHelper.generateLogForContentValues(method, methodBuilder);
        }
        // log for where parames
        SqlBuilderHelper.generateLogForWhereParameters(method, methodBuilder);
        // generate log section - END
        methodBuilder.endControlFlow();
        methodBuilder.addComment("log section END");
        methodBuilder.addCode("\n// execute SQL\n");
        switch(updateResultType) {
            case DELETE_BEAN:
            case DELETE_RAW:
                methodBuilder.addStatement("int result = getDatabase().delete($S, _sqlWhereStatement, _contentValues.whereArgsAsArray())", entity.getTableName());
                if (method.getParent().getParent().generateRx) {
                    GenericSQLHelper.generateSubjectNext(entity, methodBuilder, SubjectType.DELETE, "result");
                }
                break;
            case UPDATE_BEAN:
            case UPDATE_RAW:
                methodBuilder.addCode("// conflict algorithm $L\n", method.jql.conflictAlgorithmType);
                methodBuilder.addStatement("int result = getDatabase().update($S, $L, _contentValues.values(), _sqlWhereStatement, _contentValues.whereArgsAsArray())", entity.getTableName(), method.jql.conflictAlgorithmType.getConflictAlgorithm());
                //}
                if (method.getParent().getParent().generateRx) {
                    GenericSQLHelper.generateSubjectNext(entity, methodBuilder, SubjectType.UPDATE, "result");
                }
                break;
        }
        // support for livedata
        if (daoDefinition.hasLiveData()) {
            methodBuilder.addComment("support for livedata");
            methodBuilder.addStatement(BindDaoBuilder.METHOD_NAME_REGISTRY_EVENT + "(result)");
        }
        methodBuilder.addStatement("return result");
        // we add at last javadoc, because need info is built at last.
        SqlBuilderHelper.generateJavaDocForContentProvider(method, methodBuilder);
        methodBuilder.addJavadoc("@param uri $S\n", method.contentProviderUriTemplate.replace("*", "[*]"));
        switch(updateResultType) {
            case UPDATE_BEAN:
            case UPDATE_RAW:
                methodBuilder.addJavadoc("@param contentValues content values\n");
                break;
            default:
                break;
        }
        methodBuilder.addJavadoc("@param selection dynamic part of <code>where</code> statement $L\n", method.hasDynamicWhereConditions() ? "" : "<b>NOT USED</b>");
        methodBuilder.addJavadoc("@param selectionArgs arguments of dynamic part of <code>where</code> statement $L\n", method.hasDynamicWhereConditions() ? "" : "<b>NOT USED</b>");
        methodBuilder.addJavadoc("@return number of effected rows\n");
        builder.addMethod(methodBuilder.build());
    }

    public static void checkContentProviderVarsAndArguments(final SQLiteModelMethod method, List<JQLPlaceHolder> placeHolders) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void generateInitForDynamicWhereVariables(SQLiteModelMethod method, MethodSpec.Builder methodBuilder, String dynamiWhereName, String dynamicWhereArgsName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateLogForModifiers(final SQLiteModelMethod method, MethodSpec.Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateSQL(final SQLiteModelMethod method, MethodSpec.Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
