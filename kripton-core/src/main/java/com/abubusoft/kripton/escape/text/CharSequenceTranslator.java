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
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * An API for translating text.
 * Its core use is to escape and unescape text. Because escaping and unescaping
 * is completely contextual, the API does not present two separate signatures.
 *
 * @since 3.0
 */
public abstract class CharSequenceTranslator {

    /**
     * The Constant HEX_DIGITS.
     */
    static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * Translate a set of codepoints, represented by an int index into a CharSequence,
     * into another set of codepoints. The number of codepoints consumed must be returned,
     * and the only IOExceptions thrown must be from interacting with the Writer so that
     * the top level API may reliably ignore StringWriter IOExceptions.
     *
     * @param input CharSequence that is being translated
     * @param index int representing the current point of translation
     * @param out Writer to translate the text to
     * @return int count of codepoints consumed
     * @throws IOException if and only if the Writer produces an IOException
     */
    public abstract int translate(CharSequence input, int index, Writer out) throws IOException;

    public final String translate(final CharSequence input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public final void translate(final CharSequence input, final Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public final CharSequenceTranslator with(final CharSequenceTranslator... translators) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String hex(final int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
