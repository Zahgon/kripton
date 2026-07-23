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

import android.database.Cursor;
import com.abubusoft.kripton.android.annotation.BindSqlPageSize;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.sqlite.OnReadBeanListener;
import com.abubusoft.kripton.android.sqlite.OnReadCursorListener;
import com.abubusoft.kripton.android.sqlite.PagedResultImpl;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.KriptonClassNotFoundException;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import java.util.Collection;

/**
 * The Class SelectBuilderUtility.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 * @since 05/mag/2016
 */
public abstract class SelectBuilderUtility {

    /**
     * The Interface SelectCodeGenerator.
     */
    public interface SelectCodeGenerator {

        /**
         * Generate.
         *
         * @param builder   the builder
         * @param mapFields the map fields
         * @param method    the method
         */
        void generate(TypeSpec.Builder builder, boolean mapFields, SQLiteModelMethod method);

        /**
         * Generate live data.
         *
         * @param classBuilder the class builder
         * @param method       the method
         */
        void generateLiveData(TypeSpec.Builder classBuilder, SQLiteModelMethod method);

        /**
         * Sets the select result tye.
         *
         * @param selectResultType the new select result tye
         */
        void setSelectResultTye(SelectType selectResultType);
    }

    /**
     * The Enum SelectType.
     */
    public enum SelectType {

        /**
         * The bean.
         */
        BEAN(SelectBeanHelper.class, true),
        /**
         * The cursor.
         */
        CURSOR(SelectRawHelper.class, false),
        /**
         * The list bean.
         */
        LIST_BEAN(SelectBeanListHelper.class, true),
        /**
         * The list scalar.
         */
        LIST_SCALAR(SelectScalarListHelper.class, false),
        /**
         * The listener bean.
         */
        LISTENER_BEAN(SelectBeanListenerHelper.class, true),
        /**
         * The listener cursor.
         */
        LISTENER_CURSOR(SelectRawListenerHelper.class, false),
        /**
         * The paged result.
         */
        PAGED_RESULT(SelectPaginatedResultHelper.class, true),
        /**
         * The scalar.
         */
        SCALAR(SelectScalarHelper.class, false);

        /**
         * The code generator.
         */
        private SelectCodeGenerator codeGenerator;

        /**
         * The map fields.
         */
        private boolean mapFields;

        /**
         * Instantiates a new select type.
         *
         * @param codeGenerator the code generator
         * @param mapFields     the map fields
         */
        private SelectType(Class<? extends AbstractSelectCodeGenerator> codeGenerator, boolean mapFields) {
            try {
                this.mapFields = mapFields;
                this.codeGenerator = codeGenerator.newInstance();
                this.codeGenerator.setSelectResultTye(this);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public void generate(TypeSpec.Builder builder, SQLiteModelMethod method) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Checks if is map fields.
         *
         * @return the codeGenerator
         */
        // public SelectCodeGenerator getCodeGenerator() {
        // return codeGenerator;
        // }
        public boolean isMapFields() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void generateLiveData(Builder builder, SQLiteModelMethod method) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static TypeName extractReturnType(final SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static SelectType detectSelectType(final SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static Class<?> getClassType(ClassName returnParameterizedClassName) {
        Class<?> wrapperClazz;
        try {
            wrapperClazz = Class.forName(returnParameterizedClassName.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw (new KriptonClassNotFoundException(e));
        }
        return wrapperClazz;
    }
}
