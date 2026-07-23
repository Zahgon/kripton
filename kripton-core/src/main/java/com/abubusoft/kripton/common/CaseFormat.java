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
package com.abubusoft.kripton.common;

import java.io.Serializable;
import static com.abubusoft.kripton.common.Preconditions.checkNotNull;

/**
 * Utility class for converting between various ASCII case formats. Behavior is undefined for
 * non-ASCII input.
 *
 * @author Mike Bostock
 * @since 1.0
 */
public enum CaseFormat {

    /**
     * Hyphenated variable naming convention, e.g., "lower-hyphen".
     */
    LOWER_HYPHEN(CharMatcher.is('-'), "-") {

        @Override
        String normalizeWord(String word) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        String convert(CaseFormat format, String s) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /**
     * C++ variable naming convention, e.g., "lower_underscore".
     */
    LOWER_UNDERSCORE(CharMatcher.is('_'), "_") {

        @Override
        String normalizeWord(String word) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        String convert(CaseFormat format, String s) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /**
     * Java variable naming convention, e.g., "lowerCamel".
     */
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {

        @Override
        String normalizeWord(String word) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /**
     * Java and C++ class naming convention, e.g., "UpperCamel".
     */
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {

        @Override
        String normalizeWord(String word) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    /**
     * Java and C++ constant naming convention, e.g., "UPPER_UNDERSCORE".
     */
    UPPER_UNDERSCORE(CharMatcher.is('_'), "_") {

        @Override
        String normalizeWord(String word) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        String convert(CaseFormat format, String s) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ;

    /**
     * The word boundary.
     */
    private final CharMatcher wordBoundary;

    /**
     * The word separator.
     */
    private final String wordSeparator;

    /**
     * Instantiates a new case format.
     *
     * @param wordBoundary the word boundary
     * @param wordSeparator the word separator
     */
    CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
        this.wordBoundary = wordBoundary;
        this.wordSeparator = wordSeparator;
    }

    public final String to(CaseFormat format, String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String convert(CaseFormat format, String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Converter<String, String> converterTo(CaseFormat targetFormat) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class StringConverter.
     */
    private static final class StringConverter extends Converter<String, String> implements Serializable {

        /**
         * The source format.
         */
        private final CaseFormat sourceFormat;

        /**
         * The target format.
         */
        private final CaseFormat targetFormat;

        /**
         * Instantiates a new string converter.
         *
         * @param sourceFormat the source format
         * @param targetFormat the target format
         */
        StringConverter(CaseFormat sourceFormat, CaseFormat targetFormat) {
            this.sourceFormat = checkNotNull(sourceFormat);
            this.targetFormat = checkNotNull(targetFormat);
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.Converter#doForward(java.lang.Object)
     */
        @Override
        protected String doForward(String s) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.Converter#doBackward(java.lang.Object)
     */
        @Override
        protected String doBackward(String s) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.Converter#equals(java.lang.Object)
     */
        @Override
        public boolean equals(Object object) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = 0L;
    }

    /**
     * Normalize word.
     *
     * @param word the word
     * @return the string
     */
    abstract String normalizeWord(String word);

    /**
     * Normalize first word.
     *
     * @param word the word
     * @return the string
     */
    private String normalizeFirstWord(String word) {
        return (this == LOWER_CAMEL) ? Ascii.toLowerCase(word) : normalizeWord(word);
    }

    /**
     * First char only to upper.
     *
     * @param word the word
     * @return the string
     */
    private static String firstCharOnlyToUpper(String word) {
        return (word.isEmpty()) ? word : new StringBuilder(word.length()).append(Ascii.toUpperCase(word.charAt(0))).append(Ascii.toLowerCase(word.substring(1))).toString();
    }
}
