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

import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.exception.NoSuchMapperException;
import com.abubusoft.kripton.map.BindMapHelper;
import com.abubusoft.kripton.persistence.ParserWrapper;
import com.abubusoft.kripton.persistence.SerializerWrapper;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.io.SegmentedStringWriter;
import com.fasterxml.jackson.core.util.BufferRecycler;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract context. Contains basic method to work with persistence on different
 * data formats.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class AbstractContext implements BinderContext {

    /**
     * The Constant buffer.
     */
    static final ThreadLocal<BufferRecycler> buffer = new ThreadLocal<BufferRecycler>() {
    };

    /**
     * The Constant OBJECT_MAPPERS.
     */
    @SuppressWarnings("rawtypes")
    static final Map<Class, BinderMapper> OBJECT_MAPPERS = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    static <E, M extends BinderMapper<E>> M getMapper(Class<E> cls) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static <T, M extends BinderMapper<T>> M mapperFor(Class<T> cls) throws NoSuchMapperException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#canPersist(java.lang.Class)
   */
    public boolean canPersist(Class<?> cls) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a parser. It need to be public because it is used in generated
     * classes.
     *
     * @param data data
     * @return parser
     */
    public abstract ParserWrapper createParser(byte[] data);

    /**
     * Create a parser. It need to be public because it is used in generated
     * classes.
     *
     * @param file the file
     * @return parser
     */
    public abstract ParserWrapper createParser(File file);

    /**
     * Create a parser. It need to be public because it is used in generated
     * classes.
     *
     * @param in the in
     * @return parser
     */
    public abstract ParserWrapper createParser(InputStream in);

    /**
     * Create a parser. It need to be public because it is used in generated
     * classes.
     *
     * @param reader the reader
     * @return parser
     */
    public abstract ParserWrapper createParser(Reader reader);

    /**
     * Create a parser. It need to be public because it is used in generated
     * classes.
     *
     * @param content the content
     * @return parser
     */
    public abstract ParserWrapper createParser(String content);

    /**
     * Create a serializer. It need to be public because it is used in generated
     * classes.
     *
     * @param file the file
     * @return serializer
     */
    public abstract SerializerWrapper createSerializer(File file);

    /**
     * Create a serializer. It need to be public because it is used in generated
     * classes.
     *
     * @param file     the file
     * @param encoding the encoding
     * @return serializer
     */
    public abstract SerializerWrapper createSerializer(File file, JsonEncoding encoding);

    /**
     * Create a serializer. It need to be public because it is used in generated
     * classes.
     *
     * @param out the out
     * @return serializer
     */
    public abstract SerializerWrapper createSerializer(OutputStream out);

    /**
     * Create a serializer. It need to be public because it is used in generated
     * classes.
     *
     * @param out      the out
     * @param encoding the encoding
     * @return serializer
     */
    public abstract SerializerWrapper createSerializer(OutputStream out, JsonEncoding encoding);

    /**
     * Create a serializer. It need to be public because it is used in generated
     * classes.
     *
     * @param writer the writer
     * @return serializer
     */
    public abstract SerializerWrapper createSerializer(Writer writer);

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parse(byte[], java.lang.Class)
   */
    @Override
    public <E> E parse(byte[] source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parse(java.io.File, java.lang.Class)
   */
    @Override
    public <E> E parse(File source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parse(java.io.InputStream, java.lang.Class)
   */
    @Override
    public <E> E parse(InputStream source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parse(java.io.Reader, java.lang.Class)
   */
    @Override
    public <E> E parse(Reader source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private <E> E parse(ParserWrapper parser, Class<E> objectClazz) {
        try {
            return mapperFor(objectClazz).parse(this, parser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KriptonRuntimeException(e);
        } finally {
            if (parser != null)
                parser.close();
        }
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parse(java.lang.String, java.lang.Class)
   */
    @Override
    public <E> E parse(String source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseCollection(byte[], java.util.Collection, java.lang.Class)
   */
    @Override
    public <L extends Collection<E>, E> L parseCollection(byte[] source, L collection, Class<E> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private <L extends Collection<E>, E> L parseCollection(ParserWrapper parser, L collection, Class<E> type) {
        if (collection == null || type == null)
            return null;
        try {
            return mapperFor(type).parseCollection(this, parser, collection);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KriptonRuntimeException(e);
        } finally {
            if (parser != null)
                parser.close();
        }
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseCollection(java.io.InputStream, java.util.Collection, java.lang.Class)
   */
    @Override
    public <L extends Collection<E>, E> L parseCollection(InputStream source, L collection, Class<E> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseCollection(java.io.Reader, java.util.Collection, java.lang.Class)
   */
    @Override
    public <L extends Collection<E>, E> L parseCollection(Reader source, L collection, Class<E> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseCollection(java.lang.String, java.util.Collection, java.lang.Class)
   */
    @Override
    public <L extends Collection<E>, E> L parseCollection(String source, L collection, Class<E> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseList(byte[], java.lang.Class)
   */
    @Override
    public <E> List<E> parseList(byte[] source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseList(java.io.InputStream, java.lang.Class)
   */
    @Override
    public <E> List<E> parseList(InputStream source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseList(java.io.Reader, java.lang.Class)
   */
    @Override
    public <E> List<E> parseList(Reader source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseList(java.lang.String, java.lang.Class)
   */
    @Override
    public <E> List<E> parseList(String source, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseMap(java.io.InputStream)
   */
    @Override
    public Map<String, Object> parseMap(InputStream source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseMap(java.io.Reader)
   */
    @Override
    public Map<String, Object> parseMap(Reader source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Map<String, Object> parseMap(ParserWrapper parser) {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            return BindMapHelper.parseMap(this, parser, map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KriptonRuntimeException(e);
        } finally {
            if (parser != null)
                parser.close();
        }
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#parseMap(java.lang.String)
   */
    @Override
    public Map<String, Object> parseMap(String source) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serialize(java.lang.Object)
   */
    @Override
    public <E> String serialize(E object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serialize(java.lang.Object, java.io.File)
   */
    @Override
    public <E> void serialize(E object, File output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serialize(java.lang.Object, java.io.OutputStream)
   */
    @Override
    public <E> void serialize(E object, OutputStream output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serialize(java.lang.Object, java.io.Writer)
   */
    @Override
    public <E> void serialize(E object, Writer output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    private <E> void serialize(E object, SerializerWrapper serializer) {
        if (object == null)
            return;
        try {
            mapperFor((Class<E>) object.getClass()).serialize(this, serializer, object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KriptonRuntimeException(e);
        } finally {
            if (serializer != null)
                serializer.close();
        }
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serializeCollection(java.util.Collection, java.lang.Class)
   */
    @Override
    public <E> String serializeCollection(Collection<E> collection, Class<E> objectClazz) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serializeCollection(java.util.Collection, java.lang.Class, java.io.File)
   */
    @Override
    public <E> void serializeCollection(Collection<E> collection, Class<E> objectClazz, File output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private <E> void serializeCollection(Collection<E> collection, Class<E> objectClazz, SerializerWrapper serializer) {
        if (collection == null)
            return;
        try {
            mapperFor(objectClazz).serializeCollection(this, serializer, collection);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KriptonRuntimeException(e);
        } finally {
            if (serializer != null)
                serializer.close();
        }
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.BinderContext#serializeCollection(java.util.Collection, java.lang.Class, java.io.OutputStream)
   */
    @Override
    public <E> void serializeCollection(Collection<E> collection, Class<E> objectClazz, OutputStream output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <E> void serializeCollection(Collection<E> collection, Class<E> objectClazz, Writer output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
