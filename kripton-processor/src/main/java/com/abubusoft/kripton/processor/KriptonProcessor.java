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
package com.abubusoft.kripton.processor;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.common.StringUtils;

//import com.sun.source.util.Trees;
/**
 * Annotation processor for json/xml/etc.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class KriptonProcessor extends BaseProcessor {

    @Override
    public Set<String> getSupportedOptions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The many 2 many processor.
     */
    private BindMany2ManySubProcessor many2ManyProcessor = new BindMany2ManySubProcessor();

    /**
     * The shared preferences processor.
     */
    private BindSharedPreferencesSubProcessor sharedPreferencesProcessor = new BindSharedPreferencesSubProcessor();

    /**
     * The data source processor.
     */
    private BindDataSourceSubProcessor dataSourceProcessor = new BindDataSourceSubProcessor();

    /**
     * The type processor.
     */
    private BindTypeSubProcessor typeProcessor = new BindTypeSubProcessor();

    //public static Trees trees;
    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.BaseProcessor#getSupportedAnnotationClasses()
	 */
    protected Set<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Set<Class<? extends Annotation>> getAllSupportedAnnotationClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.BaseProcessor#init(javax.annotation.processing.ProcessingEnvironment)
	 */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
