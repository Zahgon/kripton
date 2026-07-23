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

/**
 * Translates codepoints to their Unicode escaped value.
 *
 * @since 3.0
 */
public class UnicodeEscaper extends CodePointTranslator {

    /**
     * The below.
     */
    private final int below;

    /**
     * The above.
     */
    private final int above;

    /**
     * The between.
     */
    private final boolean between;

    /**
     * <p>Constructs a <code>UnicodeEscaper</code> for all characters. </p>
     */
    public UnicodeEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    /**
     * <p>Constructs a <code>UnicodeEscaper</code> for the specified range. This is
     * the underlying method for the other constructors/builders. The <code>below</code>
     * and <code>above</code> boundaries are inclusive when <code>between</code> is
     * <code>true</code> and exclusive when it is <code>false</code>. </p>
     *
     * @param below int value representing the lowest codepoint boundary
     * @param above int value representing the highest codepoint boundary
     * @param between whether to escape between the boundaries or outside them
     */
    protected UnicodeEscaper(final int below, final int above, final boolean between) {
        this.below = below;
        this.above = above;
        this.between = between;
    }

    public static UnicodeEscaper below(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static UnicodeEscaper above(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static UnicodeEscaper outsideOf(final int codepointLow, final int codepointHigh) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static UnicodeEscaper between(final int codepointLow, final int codepointHigh) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean translate(final int codepoint, final Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected String toUtf16Escape(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
