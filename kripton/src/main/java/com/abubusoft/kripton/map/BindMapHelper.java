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
package com.abubusoft.kripton.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.abubusoft.kripton.AbstractContext;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.persistence.JacksonWrapperParser;
import com.abubusoft.kripton.persistence.ParserWrapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Bind map helper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class BindMapHelper {

    public static Map<String, Object> parseMap(AbstractContext context, ParserWrapper parserWrapper, Map<String, Object> map) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static Map<String, Object> parseMap(AbstractContext context, JsonParser parser, Map<String, Object> map, boolean skipRead) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static List<Object> parseList(AbstractContext context, JsonParser parser, List<Object> list, boolean skipRead) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
