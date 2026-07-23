package com.abubusoft.kripton.processor.bind;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.squareup.javapoet.TypeName;

/**
 * Binding Map Registry
 * @author xcesco
 */
public class BinderMapRegistry {

    /* ordered set of binder needed for model */
    private Map<TypeName, Set<TypeName>> registry = new HashMap<TypeName, Set<TypeName>>();

    private final static BinderMapRegistry instance = new BinderMapRegistry();

    public static BinderMapRegistry getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void registry(TypeName binderTypeName, TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Pair<String, TypeName> getMapperNames(TypeName typeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<TypeName> getEntityEntries(TypeName entityType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
