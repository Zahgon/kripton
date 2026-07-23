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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * The Class BindMapVisitor.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class BindMapVisitor {

    /**
     * The Enum VisitorStatusType.
     *
     * @author Francesco Benincasa (info@abubusoft.com)
     */
    public static enum VisitorStatusType {

        /**
         * The run.
         */
        RUN,
        /**
         * The stop.
         */
        STOP
    }

    @SuppressWarnings("unchecked")
    static VisitorStatusType visitMap(String name, Map<String, Object> map, BindMapListener listener, VisitorStatusType status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    static VisitorStatusType visitList(String name, List<Object> list, BindMapListener listener, VisitorStatusType status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void visit(String name, String value, BindMapListener listener, VisitorStatusType status) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    public static void execute(Map<String, Object> map, BindMapListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
