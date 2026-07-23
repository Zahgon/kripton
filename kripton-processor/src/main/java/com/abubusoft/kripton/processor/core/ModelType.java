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

import javax.lang.model.type.TypeMirror;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class ModelType.
 */
public class ModelType {

    /**
     * The type name.
     */
    protected TypeName typeName;

    public void setTypeName(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getTypeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new model type.
     * @param entity
     *
     * @param type the type
     */
    public ModelType(TypeMirror type) {
        // super(type.toString());
        this.typeName = TypeUtility.typeName(type);
    }

    /**
     * Instantiates a new model type.
     *
     * @param containerClassName the container class name
     * @param argClassName the arg class name
     */
    public ModelType(String containerClassName, String argClassName) {
        this.typeName = ParameterizedTypeName.get(TypeUtility.className(containerClassName), TypeUtility.typeName(argClassName));
        // super(LiteralType.of(containerClassName, argClassName).getValue());
        // this.name= TypeUtility.typeName(LiteralType.of(containerClassName,
        // argClassName).getValue());
    }

    /**
     * Instantiates a new model type.
     *
     * @param typeName the type name
     */
    public ModelType(TypeName typeName) {
        // super(typeName.toString());
        this.typeName = typeName;
    }

    public boolean isEquals(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isPrimitive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getTypeParameter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isCollection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
