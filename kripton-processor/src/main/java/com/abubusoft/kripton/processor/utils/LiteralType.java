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
/**
 */
package com.abubusoft.kripton.processor.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeVisitor;

/**
 * The Class LiteralType.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class LiteralType {

    /**
     * The Constant cached.
     */
    final static Map<String, LiteralType> cached = new HashMap<String, LiteralType>();

    /**
     * The Constant GROUP_TYPE_GENERIC_INDEX.
     */
    private static final int GROUP_TYPE_GENERIC_INDEX = 1;

    /**
     * The Constant GROUP_TYPE_PARAMETER_INDEX.
     */
    private static final int GROUP_TYPE_PARAMETER_INDEX = 2;

    /**
     * The Constant GROUP_ARRAY_INDEX.
     */
    private static final int GROUP_ARRAY_INDEX = 3;

    /**
     * The Constant GROUP_SIMPLE_INDEX.
     */
    private static final int GROUP_SIMPLE_INDEX = 4;

    /**
     * The value.
     */
    private String value;

    protected boolean primitive;

    private static final Pattern CLASS_PATTERN = Pattern.compile("([\\w\\.]+)\\<(.*)\\>|([\\w\\.]+)\\[\\]|([\\w\\.]+)");

    /**
     * The raw type.
     */
    public String rawType;

    /**
     * The resolved raw type.
     */
    protected Class<?> resolvedRawType;

    /**
     * The array.
     */
    private boolean array;

    /**
     * The type parameter.
     */
    private String typeParameter;

    public String getTypeParameter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isParametrizedType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new literal type.
     */
    LiteralType() {
    }

    //	protected LiteralType(String input) {
    //		parse(this, input);
    //	}
    public static LiteralType parse(String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static LiteralType parse(LiteralType result, String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static LiteralType of(String clazzString) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //	public static LiteralType of(String rawType, String parametrizedType) {
    //		String clazzName=rawType+"<"+parametrizedType+">";
    //		LiteralType newValue;
    //		if (cached.containsKey(clazzName))
    //		{
    //			return cached.get(clazzName);
    //		} else {
    //
    //			newValue=LiteralType.parse(clazzName);
    //			cached.put(clazzName, newValue);
    //
    //			return newValue;
    //		}
    //
    //	}
    //	public static LiteralType of(Class<?> rawType, TypeElement parametrizedType) {
    //		return of(rawType.getCanonicalName(), parametrizedType.getQualifiedName().toString());
    //	}
    public boolean isCollection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isResolved() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getRawType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeKind getKind() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <R, P> R accept(TypeVisitor<R, P> v, P p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
