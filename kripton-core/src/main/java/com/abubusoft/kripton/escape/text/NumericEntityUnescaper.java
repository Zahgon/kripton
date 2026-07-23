/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abubusoft.kripton.escape.text;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Translate XML numeric entities of the form &amp;#[xX]?\d+;? to
 * the specific codepoint.
 *
 * Note that the semi-colon is optional.
 *
 * @since 3.0
 */
public class NumericEntityUnescaper extends CharSequenceTranslator {

    /**
     * The Enum OPTION.
     */
    public static enum OPTION {

        /**
         * The semi colon required.
         */
        semiColonRequired,
        /**
         * The semi colon optional.
         */
        semiColonOptional,
        /**
         * The error if no semi colon.
         */
        errorIfNoSemiColon
    }

    /**
     * The options.
     */
    // TODO?: Create an OptionsSet class to hide some of the conditional logic below
    private final EnumSet<OPTION> options;

    /**
     * Create a UnicodeUnescaper.
     *
     * The constructor takes a list of options, only one type of which is currently
     * available (whether to allow, error or ignore the semi-colon on the end of a
     * numeric entity to being missing).
     *
     * For example, to support numeric entities without a ';':
     *    new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.semiColonOptional)
     * and to throw an IllegalArgumentException when they're missing:
     *    new NumericEntityUnescaper(NumericEntityUnescaper.OPTION.errorIfNoSemiColon)
     *
     * Note that the default behaviour is to ignore them.
     *
     * @param options to apply to this unescaper
     */
    public NumericEntityUnescaper(final OPTION... options) {
        if (options.length > 0) {
            this.options = EnumSet.copyOf(Arrays.asList(options));
        } else {
            this.options = EnumSet.copyOf(Arrays.asList(new OPTION[] { OPTION.semiColonRequired }));
        }
    }

    public boolean isSet(final OPTION option) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int translate(final CharSequence input, final int index, final Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
