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
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.squareup.javapoet.TypeName;

/**
 * The Class ModelMethod.
 */
public class ModelMethod extends ModelEntity<ExecutableElement> implements ModelWithAnnotation {

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.core.ModelWithAnnotation#getAnnotation(java.lang.Class)
	 */
    public ModelAnnotation getAnnotation(Class<? extends Annotation> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new model method.
     *
     * @param element the element
     */
    public ModelMethod(ExecutableElement element) {
        super(element.getSimpleName().toString(), element);
        this.parameters = new ArrayList<Pair<String, TypeName>>();
        this.annotations = new ArrayList<ModelAnnotation>();
        for (VariableElement p : element.getParameters()) {
            parameters.add(new Pair<String, TypeName>(p.getSimpleName().toString(), TypeUtility.typeName(p.asType())));
        }
        returnClass = TypeUtility.typeName(element.getReturnType());
    }

    /**
     * The annotations.
     */
    protected List<ModelAnnotation> annotations;

    public List<Pair<String, TypeName>> getParameters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getReturnClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setReturnClass(TypeName returnClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The parameters.
     */
    protected List<Pair<String, TypeName>> parameters;

    /**
     * The return class.
     */
    protected TypeName returnClass;

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.core.ModelWithAnnotation#addAnnotation(com.abubusoft.kripton.processor.core.ModelAnnotation)
	 */
    public void addAnnotation(ModelAnnotation annotation) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName findParameterType(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if method contains a parameter with value as typeName.
     *
     * @param annotationClazz the annotation clazz
     * @return true if there is parameter with specified typeName
     */
    //	public boolean hasParameter(String typeName) {
    //		for (Pair<String, TypeMirror> item : parameters) {
    //			if (item.value0.equals(typeName)) {
    //				return true;
    //			}
    //		}
    //		return false;
    //	}
    @Override
    public boolean hasAnnotation(Class<? extends Annotation> annotationClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
