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
/**
 */
package com.abubusoft.kripton.processor.sqlite;

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.PageRequest;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.sqlite.PagedResultImpl;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.SQLTypeAdapterUtils;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class SelectPaginatedResultHelper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @param <ElementUtils>
 *            the generic type
 * @since 29/01/2017
 */
public class SelectPaginatedResultHelper extends AbstractSelectCodeGenerator {

    public static final String WITH_PAGE_REQUEST_PREFIX = "WithPageRequest";

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.AbstractSelectCodeGenerator#
	 * generate(com.squareup.javapoet.TypeSpec.Builder, boolean,
	 * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod)
	 */
    @Override
    public void generate(TypeSpec.Builder classBuilder, boolean mapFields, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void generateTotalCountUsage(SQLiteModelMethod method, Builder methodBuilder, String paginatedResultName, String selectTotalCountMethodName) {
        methodBuilder.addComment("total count - BEGIN");
        methodBuilder.addCode(paginatedResultName + ".setTotalElements(this." + selectTotalCountMethodName + "(");
        method.getParameters().forEach(p -> {
            methodBuilder.addCode(p.value0 + ", ");
        });
        methodBuilder.addCode(paginatedResultName + "));\n");
        methodBuilder.addComment("total count - END");
    }

    public static void createPagedResult(SQLiteModelMethod method, String pagedResultName, MethodSpec.Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Used to generate specialized Paged Result classes;.
     */
    static int pagedResultCounter;

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.sqlite.AbstractSelectCodeGenerator#
	 * generateSpecializedPart(com.abubusoft.kripton.processor.sqlite.model.
	 * SQLiteModelMethod, com.squareup.javapoet.TypeSpec.Builder,
	 * com.squareup.javapoet.MethodSpec.Builder, java.util.Set, boolean)
	 */
    @Override
    public void generateSpecializedPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, Set<JQLProjection> fieldList, boolean mapFields) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateTotalSpecializedPart(SQLiteModelMethod method, MethodSpec.Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String buildSpecializedPagedResultClass(TypeSpec.Builder classBuilder, SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getCurrentPagedResultClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
