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

import java.util.List;
import java.util.Map;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;

/**
 * The Class ModelAnnotation.
 */
public class ModelAnnotation {

    /**
     * The name.
     */
    protected String name;

    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The attributes.
     */
    protected Map<String, String> attributes;

    /**
     * Instantiates a new model annotation.
     *
     * @param name the name
     * @param attributes the attributes
     */
    public ModelAnnotation(String name, Map<String, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getAttribute(AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getAttributeCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getSimpleName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getAttributeAsClassName(AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean getAttributeAsBoolean(AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<String> getAttributeAsArray(AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getAttributeAsInt(AnnotationAttributeType attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
