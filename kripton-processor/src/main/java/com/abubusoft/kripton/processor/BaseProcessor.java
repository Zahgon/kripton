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
package com.abubusoft.kripton.processor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.processor.core.AssertKripton;

/**
 * The Class BaseProcessor.
 */
public abstract class BaseProcessor extends AbstractProcessor {

    /**
     * if we want to display debug info.
     */
    public static boolean DEBUG_MODE = false;

    /**
     * if we want to display debug info.
     */
    public static boolean LOG_GENERATION_ENABLED_MODE = true;

    /**
     * The element utils.
     */
    public static Elements elementUtils;

    /**
     * if true we are in a test.
     */
    public static boolean JUNIT_TEST_MODE = false;

    /**
     * The logger.
     */
    protected static Logger logger = Logger.getGlobal();

    /**
     * The count.
     */
    protected int count;

    /**
     * The excluded methods.
     */
    protected HashSet<String> excludedMethods;

    /**
     * The filer.
     */
    protected Filer filer;

    /**
     * define which annotation the annotation processor is interested in.
     */
    protected final Map<String, TypeElement> globalBeanElements = new HashMap<String, TypeElement>();

    /**
     * The messager.
     */
    protected static Messager messager;

    /**
     * The type utils.
     */
    protected Types typeUtils;

    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void error(Element e, String msg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<Element> filter(RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the supported annotation classes.
     *
     * @return the supported annotation classes
     */
    protected abstract Set<Class<? extends Annotation>> getSupportedAnnotationClasses();

    /* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#getSupportedAnnotationTypes()
	 */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.annotation.processing.AbstractProcessor#getSupportedSourceVersion()
	 */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasWorkInThisRound(RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void info(String msg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#init(javax.annotation.processing.ProcessingEnvironment)
	 */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void parseBindType(RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
