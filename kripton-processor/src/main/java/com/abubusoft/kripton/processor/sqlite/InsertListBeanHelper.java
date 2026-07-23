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
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.ColumnType;
import com.abubusoft.kripton.android.annotation.BindSqlInsert;
import com.abubusoft.kripton.android.sqlite.ConflictAlgorithmType;
import com.abubusoft.kripton.android.sqlite.KriptonContentValues;
import com.abubusoft.kripton.android.sqlite.KriptonDatabaseHelper;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.GenericSQLHelper.SubjectType;
import com.abubusoft.kripton.processor.sqlite.SqlInsertBuilder.InsertCodeGenerator;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class InsertBeanHelper.
 */
public class InsertListBeanHelper implements InsertCodeGenerator {

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.SqlInsertBuilder. InsertCodeGenerator#generate(com.squareup.javapoet.TypeSpec.Builder, com.squareup.javapoet.MethodSpec.Builder,
	 * boolean, com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod, com.squareup.javapoet.TypeName)
	 */
    @Override
    public void generate(TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, boolean mapFields, SQLiteModelMethod method, TypeName returnType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateJavaDoc(MethodSpec.Builder methodBuilder, final SQLiteModelMethod method, TypeName returnType, List<SQLProperty> listUsedProperty, ModelProperty primaryKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ConflictAlgorithmType getConflictAlgorithmType(SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
