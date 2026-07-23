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
package com.abubusoft.kripton.processor.core;

import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.utils.LiteralType;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * The Class ModelProperty.
 */
@BindType
public class ModelProperty extends ModelEntity<Element> implements ModelElement, ModelWithAnnotation {

    /**
     * The Class TypeAdapter.
     */
    public class TypeAdapter {

        /**
         * The adapter clazz.
         */
        public String adapterClazz;

        /**
         * The data type.
         */
        public String dataType;

        public TypeName getAdapterTypeName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public TypeName getDataTypeTypename() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    protected void checkTypeAdapter(@SuppressWarnings("rawtypes") ModelEntity entity, TypeMirror propertyType, TypeAdapter typeAdapter, ModelAnnotation annotation) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The parent.
     */
    @SuppressWarnings("rawtypes")
    protected WeakReference<ModelEntity> parent;

    @SuppressWarnings("rawtypes")
    public ModelEntity getParent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new model property.
     *
     * @param entity the entity
     * @param element the element
     * @param modelAnnotations the model annotations
     */
    @SuppressWarnings("rawtypes")
    public ModelProperty(ModelEntity<?> entity, Element element, List<ModelAnnotation> modelAnnotations) {
        super((element != null) ? element.getSimpleName().toString() : null, element);
        this.parent = new WeakReference<ModelEntity>(entity);
        if (element != null) {
            TypeName temp1 = TypeName.get(element.asType());
            LiteralType temp2 = LiteralType.of(element.asType().toString());
            AssertKripton.fail((temp1 instanceof ClassName) && temp2.isCollection(), "In bean '%s' property '%s' can not use Object as parameter", entity.getElement().asType().toString(), element.getSimpleName().toString());
            propertyType = new ModelType(element.asType());
            publicField = element.getModifiers().contains(Modifier.PUBLIC);
        }
        this.annotations = new ArrayList<ModelAnnotation>();
        if (modelAnnotations != null) {
            this.annotations.addAll(modelAnnotations);
        }
        this.typeAdapter = new TypeAdapter();
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.core.ModelWithAnnotation#getAnnotation(java.lang.Class)
	 */
    @Override
    public ModelAnnotation getAnnotation(Class<? extends Annotation> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.core.ModelWithAnnotation#hasAnnotation(java.lang.Class)
	 */
    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The annotations.
     */
    protected List<ModelAnnotation> annotations;

    /**
     * The property type.
     */
    protected ModelType propertyType;

    /**
     * The type adapter.
     */
    public TypeAdapter typeAdapter;

    public ModelType getPropertyType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The public field.
     */
    protected boolean publicField;

    /**
     * The field with getter.
     */
    protected boolean fieldWithGetter;

    public boolean isFieldWithGetter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setFieldWithGetter(boolean fieldWithGetter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isFieldWithSetter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setFieldWithSetter(boolean fieldWithSetter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isFieldWithIs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setFieldWithIs(boolean fieldWithIs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The field with setter.
     */
    protected boolean fieldWithSetter;

    /**
     * The field with is.
     */
    protected boolean fieldWithIs;

    public boolean isProperty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isPublicField() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.core.ModelElement#accept(com.abubusoft.kripton.processor.core.ModelElementVisitor)
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void accept(@SuppressWarnings("rawtypes") ModelElementVisitor visitor) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isType(TypeName value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isType(Type... types) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasTypeAdapter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
