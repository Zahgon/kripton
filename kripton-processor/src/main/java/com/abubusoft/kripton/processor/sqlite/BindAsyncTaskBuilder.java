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

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.className;
import java.io.IOException;
import java.util.concurrent.Executor;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.android.BindAsyncTaskType;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.processor.bind.JavaWriterHelper;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.utils.AnnotationProcessorUtilis;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import com.squareup.javapoet.TypeVariableName;
import android.os.AsyncTask;

/**
 * Utility class to generate async task for database operations.
 */
public class BindAsyncTaskBuilder {

    /**
     * The Constant PREFIX.
     */
    public static final String PREFIX = "Bind";

    /**
     * The Constant SUFFIX.
     */
    public static final String SUFFIX = "AsyncTask";

    /**
     * The builder.
     */
    private static Builder builder;

    public static void generate(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
