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

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.android.sqlite.BindDaoFactory;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.bind.JavaWriterHelper;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.utils.AnnotationProcessorUtilis;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * Generates database class.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindDaoFactoryBuilder extends AbstractBuilder {

    /**
     * The Constant PREFIX.
     */
    public static final String PREFIX = "Bind";

    /**
     * The Constant SUFFIX.
     */
    public static final String SUFFIX = "DaoFactory";

    /**
     * Instantiates a new bind dao factory builder.
     *
     * @param elementUtils the element utils
     * @param filer the filer
     * @param model the model
     */
    public BindDaoFactoryBuilder(Elements elementUtils, Filer filer, SQLiteDatabaseSchema model) {
        super(elementUtils, filer, model);
    }

    public static String generateDaoFactoryName(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName generateDaoFactoryClassName(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String buildDaoFactoryInterface(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeSpec.Builder buildDaoFactoryInterfaceInternal(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String buildDaoFactoryName(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
