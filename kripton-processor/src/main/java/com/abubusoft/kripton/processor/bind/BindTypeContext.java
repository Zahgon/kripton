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
package com.abubusoft.kripton.processor.bind;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import com.abubusoft.kripton.BinderUtils;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.ModelEntity;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class BindTypeContext.
 */
public class BindTypeContext {

    /**
     * The builder.
     */
    public TypeSpec.Builder builder;

    public MethodSpec.Builder initBuilder;

    /**
     * The already generated methods.
     */
    public Set<String> alreadyGeneratedMethods;

    /**
     * The modifiers.
     */
    public Modifier[] modifiers;

    /**
     * The bean type name.
     */
    private TypeName beanTypeName;

    /**
     * Instantiates a new bind type context.
     *
     * @param builder
     *            the builder
     * @param beanTypeName
     *            the bean type name
     * @param modifiers
     *            the modifiers
     */
    public BindTypeContext(TypeSpec.Builder builder, TypeName beanTypeName, Modifier... modifiers) {
        this.builder = builder;
        this.initBuilder = MethodSpec.methodBuilder("init").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class);
        this.beanTypeName = beanTypeName;
        this.alreadyGeneratedMethods = new HashSet<>();
        this.modifiers = modifiers;
    }

    public String getBindMapperName(BindTypeContext context, TypeName typeName, ModelEntity<?> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
