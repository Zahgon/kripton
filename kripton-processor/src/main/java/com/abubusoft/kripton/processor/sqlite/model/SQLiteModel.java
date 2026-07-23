/**
 * ****************************************************************************
 *  Copyright 2015, 2016 Francesco Benincasa.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.abubusoft.kripton.processor.sqlite.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class SQLiteModel.
 */
public class SQLiteModel {

    /**
     * The schemas.
     */
    protected List<SQLiteDatabaseSchema> schemas = new ArrayList<SQLiteDatabaseSchema>();

    public void schemaAdd(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<SQLiteDatabaseSchema> getSchemas() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void schemaClear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int schemaCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
