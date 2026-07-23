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
package com.abubusoft.kripton.processor.sqlite;

import java.util.ArrayList;
import java.util.List;
import com.abubusoft.kripton.android.sqlite.SQLiteEvent;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.PropertyNotFoundException;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmtContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

/**
 * The Class GenericSQLHelper.
 */
public abstract class GenericSQLHelper {

    /**
     * The Enum SubjectType.
     */
    public enum SubjectType {

        /**
         * The insert.
         */
        INSERT("Insert"),
        /**
         * The update.
         */
        UPDATE("Update"),
        /**
         * The delete.
         */
        DELETE("Delete");

        /**
         * Instantiates a new subject type.
         *
         * @param value
         *            the value
         */
        private SubjectType(String value) {
            this.value = value;
        }

        /**
         * The value.
         */
        private String value;

        public String value() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static void generateSubjectNext(SQLiteEntity entity, MethodSpec.Builder methodBuilder, SubjectType subjectType, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateGenericExecSQL(MethodSpec.Builder methodBuilder, final SQLiteModelMethod method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
