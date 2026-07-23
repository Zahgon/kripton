/**
 * Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 */
package com.abubusoft.kripton.processor.sqlite;

import com.abubusoft.kripton.common.SQLTypeAdapterUtils;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLProjection;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.Optional;
import java.util.Set;
import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;

/**
 * The Class SelectBeanHelper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @since 17/mag/2016
 */
public class SelectBeanHelper extends AbstractSelectCodeGenerator {

    /*
   * (non-Javadoc)
   *
   * @see com.abubusoft.kripton.processor.sqlite.AbstractSelectCodeGenerator#generateSpecializedPart(com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod,
   * com.squareup.javapoet.TypeSpec.Builder, com.squareup.javapoet.MethodSpec.Builder, java.util.Set, boolean)
   */
    @Override
    public void generateSpecializedPart(SQLiteModelMethod method, TypeSpec.Builder classBuilder, MethodSpec.Builder methodBuilder, Set<JQLProjection> fieldList, boolean mapFields) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
