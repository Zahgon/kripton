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

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.isTypeIncludedIn;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;
import java.util.List;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.annotation.BindSqlDelete;
import com.abubusoft.kripton.android.annotation.BindSqlUpdate;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.android.sqlite.KriptonDatabaseHelper;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.sqlite.GenericSQLHelper.SubjectType;
import com.abubusoft.kripton.processor.sqlite.SqlModifyBuilder.ModifyCodeGenerator;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDynamicStatementType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmtContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class ModifyBeanHelper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class ModifyBeanHelper implements ModifyCodeGenerator {

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.SqlModifyBuilder.
	 * ModifyCodeGenerator#generate(com.squareup.javapoet.TypeSpec.Builder,
	 * com.squareup.javapoet.MethodSpec.Builder, boolean,
	 * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod,
	 * com.squareup.javapoet.TypeName)
	 */
    @Override
    public void generate(TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, boolean updateMode, SQLiteModelMethod method, TypeName returnType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void generateModifyQueryCommonPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateWhereCondition(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, SqlAnalyzer analyzer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void buildReturnCode(MethodSpec.Builder methodBuilder, boolean updateMode, SQLiteModelMethod method, TypeName returnType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String buildJavadoc(MethodSpec.Builder methodBuilder, boolean updateMode, final SQLiteModelMethod method, String beanNameParameter, String whereCondition, List<SQLProperty> listUsedProperty, List<String> attributesUsedInWhereConditions) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Extract SQL for java doc.
     *
     * @param method
     *            the method
     * @return the string
     */
    private String extractSQLForJavaDoc(final SQLiteModelMethod method) {
        final One<Boolean> usedInWhere = new One<>(false);
        String sqlForJavaDoc = JQLChecker.getInstance().replace(method, method.jql, new JQLReplacerListenerImpl(method) {

            @Override
            public String onColumnNameToUpdate(String columnName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public String onColumnName(String columnName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public String onBindParameter(String bindParameterName, boolean inStatement) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onWhereStatementBegin(Where_stmtContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onWhereStatementEnd(Where_stmtContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        return sqlForJavaDoc;
    }
}
