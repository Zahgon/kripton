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

/**
 * Translates codepoints to their Unicode escaped value suitable for Java source.
 *
 * @since 3.2
 */
public class JavaUnicodeEscaper extends UnicodeEscaper {

    public static JavaUnicodeEscaper above(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static JavaUnicodeEscaper below(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static JavaUnicodeEscaper between(final int codepointLow, final int codepointHigh) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static JavaUnicodeEscaper outsideOf(final int codepointLow, final int codepointHigh) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * <p>
     * Constructs a <code>JavaUnicodeEscaper</code> for the specified range. This is the underlying method for the
     * other constructors/builders. The <code>below</code> and <code>above</code> boundaries are inclusive when
     * <code>between</code> is <code>true</code> and exclusive when it is <code>false</code>.
     * </p>
     *
     * @param below
     *            int value representing the lowest codepoint boundary
     * @param above
     *            int value representing the highest codepoint boundary
     * @param between
     *            whether to escape between the boundaries or outside them
     */
    public JavaUnicodeEscaper(final int below, final int above, final boolean between) {
        super(below, above, between);
    }

    @Override
    protected String toUtf16Escape(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
