/**
 * ****************************************************************************
 *  Copyright 2016-2019 Francesco Benincasa (info@abubusoft.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * ****************************************************************************
 */
package com.abubusoft.kripton;

import java.io.IOException;
import java.util.Collection;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.persistence.JacksonWrapperParser;
import com.abubusoft.kripton.persistence.JacksonWrapperSerializer;
import com.abubusoft.kripton.persistence.ParserWrapper;
import com.abubusoft.kripton.persistence.SerializerWrapper;
import com.abubusoft.kripton.persistence.XmlWrapperParser;
import com.abubusoft.kripton.persistence.XmlWrapperSerializer;
import com.abubusoft.kripton.xml.EventType;
import com.abubusoft.kripton.xml.XMLSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * The Class AbstractMapper.
 *
 * @param <E> the element type
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class AbstractMapper<E> implements BinderMapper<E> {

    /* (non-Javadoc)
     * @see com.abubusoft.kripton.BinderMapper#parse(com.abubusoft.kripton.BinderContext, com.abubusoft.kripton.persistence.ParserWrapper)
     */
    @Override
    public E parse(BinderContext context, ParserWrapper parserWrapper) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see com.abubusoft.kripton.BinderMapper#parseCollection(com.abubusoft.kripton.BinderContext, com.abubusoft.kripton.persistence.ParserWrapper, java.util.Collection)
     */
    @Override
    public <L extends Collection<E>> L parseCollection(BinderContext context, ParserWrapper parserWrapper, L collection) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void serialize(BinderContext context, E object, SerializerWrapper serializerWrapper, boolean writeStartAndEnd) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see com.abubusoft.kripton.BinderMapper#serialize(com.abubusoft.kripton.BinderContext, com.abubusoft.kripton.persistence.SerializerWrapper, java.lang.Object)
     */
    @Override
    public void serialize(BinderContext context, SerializerWrapper serializerWrapper, E object) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see com.abubusoft.kripton.BinderMapper#serializeCollection(com.abubusoft.kripton.BinderContext, com.abubusoft.kripton.persistence.SerializerWrapper, java.util.Collection)
     */
    @Override
    public void serializeCollection(BinderContext context, SerializerWrapper serializerWrapper, Collection<E> collection) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
