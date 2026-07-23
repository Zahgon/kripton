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
package com.abubusoft.kripton.processor.core.reflect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import org.apache.commons.lang3.StringEscapeUtils;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.ModelEntity;
import com.abubusoft.kripton.processor.core.ModelMethod;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.ModelWithAnnotation;

/**
 * The Class AnnotationUtility.
 */
public abstract class AnnotationUtility {

    /**
     * The Constant classPattern.
     */
    private static final Pattern classPattern = Pattern.compile("([\\w.]*).class");

    /**
     * The Constant arrayPattern.
     */
    private static final Pattern arrayPattern = Pattern.compile("\"([^\"]*)\"");

    static List<String> extractAsArrayOfClassName(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> extractAsArrayOfString(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The listener interface for receiving annotationFound events.
     * The class that is interested in processing a annotationFound
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addAnnotationFoundListener</code> method. When
     * the annotationFound event occurs, that object's appropriate
     * method is invoked.
     */
    public interface AnnotationFoundListener {

        /**
         * If true, annotation is accepted.
         *
         * @param executableMethod the executable method
         * @param annotationClassName the annotation class name
         * @param attributes the attributes
         */
        void onAcceptAnnotation(Element executableMethod, final String annotationClassName, final Map<String, String> attributes);
    }

    /**
     * The listener interface for receiving methodFound events.
     * The class that is interested in processing a methodFound
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addMethodFoundListener</code> method. When
     * the methodFound event occurs, that object's appropriate
     * method is invoked.
     */
    public interface MethodFoundListener {

        /**
         * On method.
         *
         * @param executableMethod the executable method
         */
        void onMethod(ExecutableElement executableMethod);
    }

    public static void forEachAnnotations(Element currentElement, AnnotationFilter filter, AnnotationFoundListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void forEachAnnotations(Element currentElement, AnnotationFoundListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> extractAsClassNameArray(Elements elementUtils, Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attributeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String extractAsClassName(Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attributeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String extractAsString(final Element item, final Class<? extends Annotation> annotationClass, final AnnotationAttributeType attributeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> extractAsStringArray(Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attributeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String extractAsEnumerationValue(Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The listener interface for receiving onAttributeFound events.
     * The class that is interested in processing a onAttributeFound
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnAttributeFoundListener<code> method. When
     * the onAttributeFound event occurs, that object's appropriate
     * method is invoked.
     *
     * @see OnAttributeFoundEvent
     */
    interface OnAttributeFoundListener {

        /**
         * On found.
         *
         * @param value the value
         */
        void onFound(String value);
    }

    static void extractString(Elements elementUtils, Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attribute, OnAttributeFoundListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void extractAttributeValue(Elements elementUtils, Element item, String annotationName, AnnotationAttributeType attribute, OnAttributeFoundListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> extractAsStringArray(ModelMethod method, ModelAnnotation annotationClass, AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String extractAsEnumerationValue(ModelProperty property, ModelAnnotation annotationClass, AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<ModelAnnotation> buildAnnotationList(final Element element, final AnnotationFilter filter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<ModelAnnotation> buildAnnotationList(final Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class AnnotationFilter.
     */
    public static class AnnotationFilter {

        /**
         * Instantiates a new annotation filter.
         *
         * @param annotations the annotations
         */
        AnnotationFilter(Set<String> annotations) {
            annotationNames = annotations;
        }

        /**
         * The annotation names.
         */
        Set<String> annotationNames;

        public boolean isAccepted(String annotationName) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public static AnnotationFilterBuilder builder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class AnnotationFilterBuilder.
     */
    public static class AnnotationFilterBuilder {

        /**
         * Instantiates a new annotation filter builder.
         */
        AnnotationFilterBuilder() {
            set = new HashSet<String>();
        }

        public AnnotationFilterBuilder add(Class<? extends Annotation> annotation) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * The set.
         */
        Set<String> set;

        public AnnotationFilter build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static int extractAsInt(Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attributeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean extractAsBoolean(Element item, Class<? extends Annotation> annotationClass, AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends ModelEntity<?>> boolean extractAsBoolean(E item, ModelAnnotation annotation, AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Boolean getAnnotationAttributeAsBoolean(ModelWithAnnotation model, Class<? extends Annotation> annotation, AnnotationAttributeType attribute, Boolean defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static <T> T getAnnotationAttribute(ModelWithAnnotation model, Class<? extends Annotation> annotation, AnnotationAttributeType attribute, T defaultValue, OnAnnotationAttributeListener<T> listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The listener interface for receiving onAnnotationAttribute events.
     * The class that is interested in processing a onAnnotationAttribute
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnAnnotationAttributeListener<code> method. When
     * the onAnnotationAttribute event occurs, that object's appropriate
     * method is invoked.
     *
     * @param <T> the generic type
     * @see OnAnnotationAttributeEvent
     */
    interface OnAnnotationAttributeListener<T> {

        /**
         * On found.
         *
         * @param value the value
         * @return the t
         */
        T onFound(String value);
    }
}
