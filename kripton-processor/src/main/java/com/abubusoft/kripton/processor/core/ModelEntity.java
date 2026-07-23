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

import javax.lang.model.element.Element;

/**
 * The Class ModelEntity.
 *
 * @param <E> the element type
 */
public class ModelEntity<E extends Element> {

    /**
     * The element.
     */
    protected E element;

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

    public E getElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The name.
     */
    protected String name;

    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new model entity.
     *
     * @param name the name
     * @param element the element
     */
    public ModelEntity(String name, E element) {
        this.element = element;
        this.name = name;
    }
}
