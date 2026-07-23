/**
 */
package com.abubusoft.kripton.processor.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * @author xcesco
 */
public abstract class ImmutableUtility {

    public static String IMMUTABLE_PREFIX = "__";

    public static void buildConstructors(Elements elementUtils, ModelClass<?> entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateImmutableVariableReset(ModelClass<?> entity, Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateImmutableVariableInit(ModelClass<?> entity, Builder methodBuilder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateImmutableVariableCopyFromEntity(ModelClass<?> entity, Builder methodBuilder, String entityName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void generateImmutableVariableInternal(ModelClass<?> entity, Builder methodBuilder, boolean declare) {
        methodBuilder.addComment("immutable object: initialize temporary variables for properties");
        for (Pair<String, TypeName> property : entity.getImmutableConstructors()) {
            if (declare) {
                methodBuilder.addCode("$T ", property.value1);
            }
            methodBuilder.addCode("$L$L=$L;\n", IMMUTABLE_PREFIX, property.value0, TypeUtility.getDefaultValue(property.value1));
        }
    }

    public static void generateImmutableCollectionIfPossible(ModelClass<?> entity, Builder methodBuilder, String name, TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateImmutableEntityCreation(ModelClass<?> entity, Builder methodBuilder, String instanceName, boolean createInstance) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
