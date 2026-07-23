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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.annotation.BindTypeVariables;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.BindTypeSubProcessor;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * <p>Resolver for type variables used in class or interface hierarchy.</p>
 *
 * <p>Usually it is used in <code>BindType</code> bean and <code>Dao</code> definitions.</p>
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class TypeVariableResolver {

    /**
     * The declared type argument list.
     */
    List<TypeName> declaredTypeArgumentList;

    /**
     * The type variable map.
     */
    Map<String, TypeName> typeVariableMap;

    /**
     * The element.
     */
    TypeElement element;

    /**
     * The active.
     */
    boolean active;

    /**
     * Instantiates a new type variable resolver.
     *
     * @param element the element
     * @param typeArgs the type args
     * @param typeVariableMap the type variable map
     */
    public TypeVariableResolver(TypeElement element, List<TypeName> typeArgs, Map<String, TypeName> typeVariableMap) {
        this.element = element;
        this.declaredTypeArgumentList = typeArgs;
        this.typeVariableMap = typeVariableMap;
        this.active = (typeArgs != null && typeArgs.size() > 0) || (typeVariableMap.size() > 0);
    }

    public boolean hasTypeVariables() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeVariableResolver build(TypeElement element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName resolve(TypeName inputType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
