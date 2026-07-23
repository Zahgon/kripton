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
package com.abubusoft.kripton.processor.bind.model;

import java.util.List;
import javax.lang.model.element.Element;
import com.abubusoft.kripton.android.annotation.BindSqlAdapter;
import com.abubusoft.kripton.annotation.BindAdapter;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.ModelType;
import com.abubusoft.kripton.processor.core.TypeAdapterHelper;
import com.abubusoft.kripton.xml.MapEntryType;
import com.abubusoft.kripton.xml.XmlType;
import com.squareup.javapoet.TypeName;

/**
 * The Class BindProperty.
 */
public class BindProperty extends ModelProperty {

    /**
     * The Class BindPropertyBuilder.
     */
    public static class BindPropertyBuilder {

        /**
         * The parent property.
         */
        protected BindProperty parentProperty;

        /**
         * The raw type name.
         */
        protected TypeName rawTypeName;

        /**
         * The xml type.
         */
        protected XmlType xmlType;

        /**
         * element's label, or collection's label (for xml binding).
         */
        private String label;

        /**
         * The nullable.
         */
        private boolean nullable;

        /**
         * The in collection.
         */
        protected boolean inCollection;

        public BindPropertyBuilder inCollection(boolean inCollection) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Instantiates a new bind property builder.
         *
         * @param rawTypeName the raw type name
         * @param property the property
         */
        public BindPropertyBuilder(TypeName rawTypeName, BindProperty property) {
            this.rawTypeName = rawTypeName;
            if (property != null) {
                this.parentProperty = property;
                //	this.parent=property.getParent();
                this.nullable = property.nullable;
                this.xmlType = property.xmlInfo.xmlType;
                this.label = property.label;
            }
            this.inCollection = true;
        }

        /**
         * Instantiates a new bind property builder.
         *
         * @param parameterTypeName the parameter type name
         */
        public BindPropertyBuilder(TypeName parameterTypeName) {
            this.rawTypeName = parameterTypeName;
            this.parentProperty = null;
            this.nullable = true;
            this.inCollection = true;
        }

        public BindProperty build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public BindPropertyBuilder xmlType(XmlType xmlType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public BindPropertyBuilder elementName(String label) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public BindPropertyBuilder label(String elementTag) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public BindPropertyBuilder nullable(boolean value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class JacksonInfo.
     */
    public class JacksonInfo {
        // public String jacksonName;
    }

    /**
     * The Class XmlInfo.
     */
    public class XmlInfo {

        /**
         * The map entry type.
         */
        public MapEntryType mapEntryType = MapEntryType.TAG;

        /**
         * tag typeName for collection's item
         */
        public String labelItem;

        /**
         * The wrapped collection.
         */
        public boolean wrappedCollection;

        /**
         * The xml type.
         */
        public XmlType xmlType = XmlType.TAG;

        /**
         * Namespace used for element
         */
        public String namespace;

        public boolean isWrappedCollection() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static BindPropertyBuilder builder(TypeName rawTypeName, BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static BindPropertyBuilder builder(TypeName parameterTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * if true, means property is to write into a collection.
     */
    public boolean inCollection;

    /**
     * The nullable.
     */
    public boolean nullable;

    /**
     * The binded object.
     */
    public boolean bindedObject;

    /**
     * The order.
     */
    public int order;

    /**
     * The xml info.
     */
    public XmlInfo xmlInfo;

    /**
     * The jackson info.
     */
    public JacksonInfo jacksonInfo;

    /**
     * The map key name.
     */
    public String mapKeyName;

    /**
     * property's label.
     */
    public String label;

    /**
     * The map value name.
     */
    public String mapValueName;

    /**
     * Instantiates a new bind property.
     *
     * @param entity the entity
     * @param element the element
     * @param modelAnnotations the model annotations
     */
    public BindProperty(BindEntity entity, Element element, List<ModelAnnotation> modelAnnotations) {
        super(entity, element, modelAnnotations);
        nullable = true;
        inCollection = false;
        xmlInfo = new XmlInfo();
        jacksonInfo = new JacksonInfo();
        ModelAnnotation annotationBindAdapter = this.getAnnotation(BindAdapter.class);
        // this is needed for table generation in sqlite
        ModelAnnotation annotationBindSqlAdapter = this.getAnnotation(BindSqlAdapter.class);
        AssertKripton.assertTrueOfInvalidDefinition((annotationBindAdapter == null && annotationBindSqlAdapter == null) || (annotationBindAdapter == null) != (annotationBindSqlAdapter == null), this, "@BindAdapter and @BindSqlAdapter can not be used together");
        if (annotationBindAdapter == null) {
            annotationBindAdapter = annotationBindSqlAdapter;
        }
        if (annotationBindAdapter != null) {
            typeAdapter.adapterClazz = annotationBindAdapter.getAttributeAsClassName(AnnotationAttributeType.ADAPTER);
            typeAdapter.dataType = TypeAdapterHelper.detectDestinationType(entity.getElement(), typeAdapter.adapterClazz);
            // check type adapter
            checkTypeAdapter(entity, element.asType(), typeAdapter, annotationBindAdapter);
        }
    }

    public boolean isInCollection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isNullable() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String xmlName(BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isBindedObject() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isBindedCollection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isBindedArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isBindedMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String xmlNameForItem(BindProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
