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
package com.abubusoft.kripton.processor.sqlite.grammars.jql;

import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;

/**
 * The Class JQLProjection.
 */
public class JQLProjection {

    /**
     * The Enum ProjectionType.
     */
    public enum ProjectionType {

        /**
         * The complex.
         */
        COMPLEX,
        /**
         * The column.
         */
        COLUMN,
        /**
         * The star.
         */
        STAR
    }

    /**
     * The Class ProjectionBuilder.
     */
    public static class ProjectionBuilder {

        /**
         * The type.
         */
        ProjectionType type;

        /**
         * The alias.
         */
        String alias;

        /**
         * The column.
         */
        String column;

        /**
         * The table.
         */
        String table;

        /**
         * The expression.
         */
        String expression;

        /**
         * The property.
         */
        SQLProperty property;

        public static ProjectionBuilder create() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder type(ProjectionType value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder alias(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder table(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder column(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder expression(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public ProjectionBuilder property(SQLProperty property) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public JQLProjection build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The property.
     */
    public SQLProperty property;

    /**
     * Instantiates a new JQL projection.
     *
     * @param type
     *            the type
     * @param table
     *            the table
     * @param column
     *            the column
     * @param alias
     *            the alias
     * @param expression
     *            the expression
     * @param property
     *            the property
     */
    public JQLProjection(ProjectionType type, String table, String column, String alias, String expression, SQLProperty property) {
        this.type = type;
        this.table = table;
        this.column = column;
        this.alias = alias;
        this.expression = expression;
        this.property = property;
    }

    /**
     * The type.
     */
    public ProjectionType type;

    /**
     * The alias.
     */
    public String alias;

    /**
     * The column.
     */
    public String column;

    /**
     * The table.
     */
    public String table;

    /**
     * The expression.
     */
    public String expression;
}
