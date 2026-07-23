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
package com.abubusoft.kripton.processor.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.squareup.javapoet.ClassName;

/**
 * The Class AnnotationProcessorUtilis.
 */
public abstract class AnnotationProcessorUtilis {

    public static void infoOnGeneratedClasses(Class<? extends Annotation> annotation, ClassName className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void infoOnGeneratedClasses(Class<? extends Annotation> annotation, String packageName, String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Prints the message.
     *
     * @param msg the msg
     */
    private static void printMessage(String msg) {
        BaseProcessor.info(msg);
    }

    public static void infoOnGeneratedFile(Class<BindDataSource> annotation, File schemaCreateFile) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
