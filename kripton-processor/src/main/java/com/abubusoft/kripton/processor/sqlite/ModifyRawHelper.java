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

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.isNullable;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.android.sqlite.KriptonDatabaseHelper;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.InvalidMethodSignException;
import com.abubusoft.kripton.processor.exceptions.PropertyNotFoundException;
import com.abubusoft.kripton.processor.sqlite.SqlModifyBuilder.ModifyCodeGenerator;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDynamicStatementType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplaceVariableStatementListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmtContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class ModifyRawHelper.
 */
public class ModifyRawHelper implements ModifyCodeGenerator {

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

    /**
     * Generate java doc.
     *
     * @param method
     *            the method
     * @param methodBuilder
     *            the method builder
     * @param updateMode
     *            the update mode
     */
    private void generateJavaDoc(final SQLiteModelMethod method, Builder methodBuilder, boolean updateMode) {
        List<Pair<String, TypeName>> methodParams = method.getParameters();
        final List<SQLProperty> updatedProperties = new ArrayList<>();
        final List<Pair<String, TypeName>> methodParamsUsedAsParameter = new ArrayList<>();
        // new
        String sqlModify = JQLChecker.getInstance().replace(method, method.jql, new JQLReplacerListenerImpl(method) {

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
        });
        if (updateMode) {
            methodBuilder.addJavadoc("<h2>SQL update</h2>\n");
            methodBuilder.addJavadoc("<pre>$L</pre>\n", sqlModify);
            methodBuilder.addJavadoc("\n");
            // list of updated fields
            methodBuilder.addJavadoc("<h2>Updated columns:</h2>\n");
            methodBuilder.addJavadoc("<ul>\n");
            for (SQLProperty property : updatedProperties) {
                methodBuilder.addJavadoc("\t<li>$L</li>\n", property.columnName);
            }
            methodBuilder.addJavadoc("</ul>");
            methodBuilder.addJavadoc("\n\n");
        } else {
            methodBuilder.addJavadoc("<h2>SQL delete</h2>\n");
            methodBuilder.addJavadoc("<pre>$L</pre>\n", sqlModify);
            methodBuilder.addJavadoc("\n\n");
        }
        // list of where parameter
        methodBuilder.addJavadoc("<h2>Parameters:</h2>\n");
        methodBuilder.addJavadoc("<dl>\n");
        for (Pair<String, TypeName> property : methodParamsUsedAsParameter) {
            String rawName = method.findParameterNameByAlias(property.value0);
            methodBuilder.addJavadoc("\t<dt>$L</dt>", SqlAnalyzer.PARAM_PREFIX + property.value0 + SqlAnalyzer.PARAM_SUFFIX);
            methodBuilder.addJavadoc("<dd>is mapped to method's parameter <strong>$L</strong></dd>\n", rawName);
        }
        methodBuilder.addJavadoc("</dl>");
        methodBuilder.addJavadoc("\n\n");
        if (method.hasDynamicWhereConditions()) {
            methodBuilder.addJavadoc("<dl>\n");
            methodBuilder.addJavadoc("<dt>$L</dt><dd>is part of where conditions resolved at runtime. In above SQL it is displayed as #{$L}</dd>", method.dynamicWhereParameterName, JQLDynamicStatementType.DYNAMIC_WHERE);
            methodBuilder.addJavadoc("\n</dl>");
            methodBuilder.addJavadoc("\n\n");
        }
        // dynamic conditions
        if (method.hasDynamicWhereConditions()) {
            methodBuilder.addJavadoc("<h2>Method's parameters and associated dynamic parts:</h2>\n");
            methodBuilder.addJavadoc("<dl>\n");
            if (method.hasDynamicWhereConditions()) {
                methodBuilder.addJavadoc("<dt>$L</dt><dd>is part of where conditions resolved at runtime. In above SQL it is displayed as #{$L}</dd>", method.dynamicWhereParameterName, JQLDynamicStatementType.DYNAMIC_WHERE);
            }
            methodBuilder.addJavadoc("</dl>");
            methodBuilder.addJavadoc("\n\n");
        }
        // method parameters
        if (methodParams.size() > 0) {
            for (Pair<String, TypeName> param : methodParams) {
                String resolvedName = method.findParameterAliasByName(param.value0);
                methodBuilder.addJavadoc("@param $L", param.value0);
                if (method.isThisDynamicWhereConditionsName(param.value0)) {
                    methodBuilder.addJavadoc("\n\tis used as dynamic where conditions\n");
                } else {
                    methodBuilder.addJavadoc("\n\tis used as for parameter <strong>$L</strong>\n", resolvedName);
                }
            }
        }
        // if true, field must be associate to ben attributes
        TypeName returnType = method.getReturnClass();
        // define return value
        if (returnType == TypeName.VOID) {
        } else {
            methodBuilder.addJavadoc("\n");
            if (isIn(returnType, Boolean.TYPE, Boolean.class)) {
                if (updateMode) {
                    methodBuilder.addJavadoc("@return <code>true</code> if record is updated, <code>false</code> otherwise");
                } else {
                    methodBuilder.addJavadoc("@return <code>true</code> if record is deleted, <code>false</code> otherwise");
                }
                methodBuilder.addCode("return result!=0;\n");
            } else if (isIn(returnType, Long.TYPE, Long.class, Integer.TYPE, Integer.class, Short.TYPE, Short.class)) {
                if (updateMode) {
                    methodBuilder.addJavadoc("@return number of updated records");
                } else {
                    methodBuilder.addJavadoc("@return number of deleted records");
                }
                // methodBuilder.addCode("return result;\n");
            } else {
                // more than one listener found
                throw (new InvalidMethodSignException(method, "invalid return type"));
            }
            methodBuilder.addJavadoc("\n");
        }
    }

    static String extractWhereConditions(boolean updateMode, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate java doc.
     *
     * @param method
     *            the method
     * @param methodBuilder
     *            the method builder
     * @param updateMode
     *            the update mode
     * @param whereCondition
     *            the where condition
     * @param where
     *            the where
     * @param methodParams
     *            the method params
     */
    private void generateJavaDoc(final SQLiteModelMethod method, MethodSpec.Builder methodBuilder, boolean updateMode, String whereCondition, Pair<String, List<Pair<String, TypeName>>> where, List<Pair<String, TypeName>> methodParams) {
        final List<SQLProperty> updatedProperties = new ArrayList<>();
        final One<Boolean> onWhereStatement = new One<Boolean>(false);
        String sqlModify = JQLChecker.getInstance().replace(method, method.jql, new JQLReplacerListenerImpl(method) {

            @Override
            public void onWhereStatementBegin(Where_stmtContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void onWhereStatementEnd(Where_stmtContext ctx) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

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
        });
        if (updateMode) {
            methodBuilder.addJavadoc("<h2>SQL update</h2>\n");
            methodBuilder.addJavadoc("<pre>$L</pre>\n", sqlModify);
            methodBuilder.addJavadoc("\n");
            // list of updated fields
            methodBuilder.addJavadoc("<h2>Updated columns:</h2>\n");
            methodBuilder.addJavadoc("<ul>\n");
            for (SQLProperty property : updatedProperties) {
                methodBuilder.addJavadoc("\t<li>$L</li>\n", property.columnName);
            }
            methodBuilder.addJavadoc("</ul>");
            methodBuilder.addJavadoc("\n\n");
        } else {
            methodBuilder.addJavadoc("<h2>SQL delete</h2>\n");
            methodBuilder.addJavadoc("<pre>$L</pre>", sqlModify);
            methodBuilder.addJavadoc("\n\n");
        }
        // list of where parameter
        if (where.value1.size() > 0) {
            methodBuilder.addJavadoc("<h2>Where parameters:</h2>\n");
            methodBuilder.addJavadoc("<dl>\n");
            for (Pair<String, TypeName> property : where.value1) {
                String rawName = method.findParameterNameByAlias(property.value0);
                methodBuilder.addJavadoc("\t<dt>$L</dt>", SqlAnalyzer.PARAM_PREFIX + property.value0 + SqlAnalyzer.PARAM_SUFFIX);
                methodBuilder.addJavadoc("<dd>is mapped to method's parameter <strong>$L</strong></dd>\n", rawName);
            }
            methodBuilder.addJavadoc("</dl>");
        } else {
            methodBuilder.addJavadoc("<p>No where parameters were found.</p>");
        }
        methodBuilder.addJavadoc("\n\n");
        if (method.hasDynamicWhereConditions()) {
            methodBuilder.addJavadoc("<dl>\n");
            methodBuilder.addJavadoc("<dt>$L</dt><dd>is part of where conditions resolved at runtime. In above SQL it is displayed as #{$L}</dd>", method.dynamicWhereParameterName, JQLDynamicStatementType.DYNAMIC_WHERE);
            methodBuilder.addJavadoc("\n</dl>");
            methodBuilder.addJavadoc("\n\n");
        }
        // dynamic conditions
        if (method.hasDynamicWhereConditions()) {
            methodBuilder.addJavadoc("<h2>Method's parameters and associated dynamic parts:</h2>\n");
            methodBuilder.addJavadoc("<dl>\n");
            if (method.hasDynamicWhereConditions()) {
                methodBuilder.addJavadoc("<dt>$L</dt><dd>is part of where conditions resolved at runtime. In above SQL it is displayed as #{$L}</dd>", method.dynamicWhereParameterName, JQLDynamicStatementType.DYNAMIC_WHERE);
            }
            methodBuilder.addJavadoc("</dl>");
            methodBuilder.addJavadoc("\n\n");
        }
        // method parameters
        if (methodParams.size() > 0) {
            for (Pair<String, TypeName> param : methodParams) {
                String resolvedName = method.findParameterAliasByName(param.value0);
                methodBuilder.addJavadoc("@param $L", param.value0);
                if (method.isThisDynamicWhereConditionsName(param.value0)) {
                    methodBuilder.addJavadoc("\n\tis used as dynamic where conditions\n");
                } else if (where.value1.contains(new Pair<>(resolvedName, param.value1))) {
                    methodBuilder.addJavadoc("\n\tis used as where parameter <strong>$L</strong>\n", SqlAnalyzer.PARAM_PREFIX + resolvedName + SqlAnalyzer.PARAM_SUFFIX);
                } else {
                    methodBuilder.addJavadoc("\n\tis used as updated field <strong>$L</strong>\n", resolvedName);
                }
            }
        }
        // if true, field must be associate to ben attributes
        TypeName returnType = method.getReturnClass();
        // define return value
        if (returnType == TypeName.VOID) {
        } else {
            methodBuilder.addJavadoc("\n");
            if (isIn(returnType, Boolean.TYPE, Boolean.class)) {
                if (updateMode) {
                    methodBuilder.addJavadoc("@return <code>true</code> if record is updated, <code>false</code> otherwise");
                } else {
                    methodBuilder.addJavadoc("@return <code>true</code> if record is deleted, <code>false</code> otherwise");
                }
                // methodBuilder.addCode("return result!=0;\n");
            } else if (isIn(returnType, Long.TYPE, Long.class, Integer.TYPE, Integer.class, Short.TYPE, Short.class)) {
                if (updateMode) {
                    methodBuilder.addJavadoc("@return number of updated records");
                } else {
                    methodBuilder.addJavadoc("@return number of deleted records");
                }
                // methodBuilder.addCode("return result;\n");
            } else {
                // more than one listener found
                throw (new InvalidMethodSignException(method, "invalid return type"));
            }
            methodBuilder.addJavadoc("\n");
        }
    }

    public static void generateWhereCondition(MethodSpec.Builder methodBuilder, SQLiteModelMethod method, Pair<String, List<Pair<String, TypeName>>> where) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isIn(TypeName value, Class<?>... classes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
