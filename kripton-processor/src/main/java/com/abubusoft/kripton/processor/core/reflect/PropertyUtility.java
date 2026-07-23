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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelClass;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.AnnotationFilter;
import com.abubusoft.kripton.processor.exceptions.PropertyVisibilityException;
import com.squareup.javapoet.TypeName;

/**
 * The Class PropertyUtility.
 */
public abstract class PropertyUtility {

    /**
     * The converter field 2 method.
     */
    static Converter<String, String> converterField2Method = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL);

    /**
     * The logger.
     */
    static Logger logger = Logger.getGlobal();

    /**
     * The Constant SET_PREFIX.
     */
    private static final String SET_PREFIX = "set";

    /**
     * The Constant IS_PREFIX.
     */
    private static final String IS_PREFIX = "is";

    /**
     * The Constant GET_PREFIX.
     */
    private static final String GET_PREFIX = "get";

    /**
     * The listener interface for receiving propertyCreated events. The class
     * that is interested in processing a propertyCreated event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's <code>addPropertyCreatedListener</code>
     * method. When the propertyCreated event occurs, that object's appropriate
     * method is invoked.
     *
     * @param <T>
     *            the generic type
     * @param <E>
     *            the element type
     */
    public interface PropertyCreatedListener<T extends ModelClass<? extends E>, E extends ModelProperty> {

        /**
         * If true, the property will be included in the class. If return false,
         * the property will be ignored
         *
         * @param entity
         *            the entity
         * @param property
         *            the property
         * @return true to include property, false otherwise
         */
        boolean onProperty(T entity, E property);
    }

    public static <P extends ModelProperty, T extends ModelClass<P>> void buildProperties(Elements elementUtils, T entity, PropertyFactory<T, P> factoryProperty, AnnotationFilter propertyAnnotationFilter, PropertyCreatedListener<T, P> listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @param entity
     * @param factoryProperty
     * @param list
     * @param propertyMap
     */
    private static <P extends ModelProperty, T extends ModelClass<P>> void extractFields(T entity, PropertyFactory<T, P> factoryProperty, List<Element> list, Map<String, P> propertyMap) {
        P field;
        // fill property map from current class
        for (Element item : list) {
            if (item.getKind() == ElementKind.FIELD && modifierIsAcceptable(item)) {
                // insert only if it does not already exists
                if (!propertyMap.containsKey(item.getSimpleName().toString())) {
                    field = factoryProperty.createProperty(entity, item);
                    // TreePath treePath = KriptonProcessor.trees.getPath(item);
                    // AnnotationVisitor visitor = new AnnotationVisitor();
                    // TypeMirror recognizerType = visitor. (treePath, null);
                    // put properties in a map
                    propertyMap.put(field.getName(), field);
                }
            }
        }
    }

    static boolean modifierIsAcceptable(Element item) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getter(ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String getter(String beanName, TypeName beanClass, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String setter(TypeName beanClass, String beanName, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String setter(TypeName beanClass, String beanName, ModelProperty property, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Setter.
     *
     * @param beanClass
     *            the bean class
     * @param property
     *            the property
     * @param value
     *            the value
     * @return the string
     */
    private static String setter(ModelProperty property, String value) {
        if (property.getParent() != null && ((ModelClass<?>) property.getParent()).isImmutablePojo()) {
            return ImmutableUtility.IMMUTABLE_PREFIX + property.getName() + "=" + value;
        } else {
            if (property.isPublicField())
                return property.getName() + "=" + value;
            else if (property.isFieldWithSetter()) {
                return "set" + converterField2Method.convert(property.getName()) + "(" + value + ")";
            } else {
                throw new PropertyVisibilityException(String.format("property '%s' of class '%s' can not be modify", property.getName(), property.getParent().getElement().asType()));
            }
        }
    }
}
