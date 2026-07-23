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
package com.abubusoft.kripton.retrofit2;

import com.abubusoft.kripton.BinderContext;
import com.abubusoft.kripton.BinderType;
import com.abubusoft.kripton.KriptonBinder;
import com.abubusoft.kripton.annotation.BindType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

/**
 * A factory for creating KriptonBinderConverter objects. Default behaviour is support:
 * <pre>
 *       defaultCharset = StandardCharsets.UTF_8;
 *       errorAction = CodingErrorAction.IGNORE;
 *       binderType = BinderType.JSON;
 * </pre>
 * To change them, just use builder. Remember JSON specification requires UTF-8 charset.
 * <p>
 * If any invalid char is found in streams, it will be simply ignored. This avoid Runtime exception for
 * invalid charset.
 */
public final class KriptonBinderConverterFactory extends Converter.Factory {

    /**
     * charset used to decode stream when no charset is specified.
     */
    private final Charset charset;

    /**
     * action to manage error cases.
     */
    private final CodingErrorAction errorAction;

    /**
     * if true, charset is included in content type
     */
    private final boolean includeCharsetInContentType;

    /**
     * The binder context.
     */
    protected BinderContext binderContext;

    /**
     * Instantiates a new kripton binder converter factory.
     */
    private KriptonBinderConverterFactory(BinderType binderType, Charset charset, boolean includeCharsetInContentType, CodingErrorAction errorAction) {
        this.binderContext = KriptonBinder.bind(binderType);
        this.charset = charset;
        this.errorAction = errorAction;
        this.includeCharsetInContentType = includeCharsetInContentType;
    }

    public static KriptonBinderConverterFactory create() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KriptonBinderConverterFactory create(BinderType binderType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * (non-Javadoc)
   *
   * @see
   * retrofit2.Converter.Factory#responseBodyConverter(java.lang.reflect.Type,
   * java.lang.annotation.Annotation[], retrofit2.Retrofit)
   */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * (non-Javadoc)
   *
   * @see
   * retrofit2.Converter.Factory#requestBodyConverter(java.lang.reflect.Type,
   * java.lang.annotation.Annotation[], java.lang.annotation.Annotation[],
   * retrofit2.Retrofit)
   */
    @SuppressWarnings("rawtypes")
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static class Builder {

        private Charset defaultCharset;

        private CodingErrorAction errorAction;

        private BinderType binderType;

        private boolean includeCharsetInContentType;

        Builder() {
            defaultCharset = StandardCharsets.UTF_8;
            errorAction = CodingErrorAction.IGNORE;
            binderType = BinderType.JSON;
            includeCharsetInContentType = true;
        }

        public static Builder create() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setIncludeCharsetInContentType(boolean includeCharsetInContentType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setDefaultCharset(Charset defaultCharset) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setErrorAction(CodingErrorAction errorAction) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public Builder setBinderType(BinderType binderType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public KriptonBinderConverterFactory build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
