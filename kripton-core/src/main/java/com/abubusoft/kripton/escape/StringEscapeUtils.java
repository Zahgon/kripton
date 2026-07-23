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
package com.abubusoft.kripton.escape;

import com.abubusoft.kripton.escape.text.AggregateTranslator;
import com.abubusoft.kripton.escape.text.CharSequenceTranslator;
import com.abubusoft.kripton.escape.text.EntityArrays;
import com.abubusoft.kripton.escape.text.JavaUnicodeEscaper;
import com.abubusoft.kripton.escape.text.LookupTranslator;
import com.abubusoft.kripton.escape.text.NumericEntityEscaper;
import com.abubusoft.kripton.escape.text.NumericEntityUnescaper;
import com.abubusoft.kripton.escape.text.OctalUnescaper;
import com.abubusoft.kripton.escape.text.StringUtils;
import com.abubusoft.kripton.escape.text.UnicodeUnescaper;
import com.abubusoft.kripton.escape.text.UnicodeUnpairedSurrogateRemover;

/**
 * <p>Escapes and unescapes {@code String}s for
 * Java, Java Script, HTML and XML.</p>
 *
 * <p>#ThreadSafe#</p>
 * @since 2.0
 */
public abstract class StringEscapeUtils {

    /* ESCAPE TRANSLATORS */
    /**
     * Translator object for escaping Java.
     *
     * While {@link #escapeJava(String)} is the expected method of use, this
     * object allows the Java escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator ESCAPE_JAVA = new LookupTranslator(new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" } }).with(new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE())).with(JavaUnicodeEscaper.outsideOf(32, 0x7f));

    /**
     * Translator object for escaping EcmaScript/JavaScript.
     *
     * While {@link #escapeEcmaScript(String)} is the expected method of use, this
     * object allows the EcmaScript escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator ESCAPE_ECMASCRIPT = new AggregateTranslator(new LookupTranslator(new String[][] { { "'", "\\'" }, { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), JavaUnicodeEscaper.outsideOf(32, 0x7f));

    /**
     * Translator object for escaping Json.
     *
     * While {@link #escapeJson(String)} is the expected method of use, this
     * object allows the Json escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.2
     */
    public static final CharSequenceTranslator ESCAPE_JSON = new AggregateTranslator(new LookupTranslator(new String[][] { { "\"", "\\\"" }, { "\\", "\\\\" }, { "/", "\\/" } }), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_ESCAPE()), JavaUnicodeEscaper.outsideOf(32, 0x7f));

    /**
     * Translator object for escaping XML 1.0.
     *
     * While {@link #escapeXml10(String)} is the expected method of use, this
     * object allows the XML escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.3
     */
    public static final CharSequenceTranslator ESCAPE_XML10 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()), new LookupTranslator(new String[][] { { "\u0000", StringUtils.EMPTY }, { "\u0001", StringUtils.EMPTY }, { "\u0002", StringUtils.EMPTY }, { "\u0003", StringUtils.EMPTY }, { "\u0004", StringUtils.EMPTY }, { "\u0005", StringUtils.EMPTY }, { "\u0006", StringUtils.EMPTY }, { "\u0007", StringUtils.EMPTY }, { "\u0008", StringUtils.EMPTY }, { "\u000b", StringUtils.EMPTY }, { "\u000c", StringUtils.EMPTY }, { "\u000e", StringUtils.EMPTY }, { "\u000f", StringUtils.EMPTY }, { "\u0010", StringUtils.EMPTY }, { "\u0011", StringUtils.EMPTY }, { "\u0012", StringUtils.EMPTY }, { "\u0013", StringUtils.EMPTY }, { "\u0014", StringUtils.EMPTY }, { "\u0015", StringUtils.EMPTY }, { "\u0016", StringUtils.EMPTY }, { "\u0017", StringUtils.EMPTY }, { "\u0018", StringUtils.EMPTY }, { "\u0019", StringUtils.EMPTY }, { "\u001a", StringUtils.EMPTY }, { "\u001b", StringUtils.EMPTY }, { "\u001c", StringUtils.EMPTY }, { "\u001d", StringUtils.EMPTY }, { "\u001e", StringUtils.EMPTY }, { "\u001f", StringUtils.EMPTY }, { "\ufffe", StringUtils.EMPTY }, { "\uffff", StringUtils.EMPTY } }), NumericEntityEscaper.between(0x7f, 0x84), NumericEntityEscaper.between(0x86, 0x9f), new UnicodeUnpairedSurrogateRemover());

    /**
     * Translator object for escaping XML 1.1.
     *
     * While {@link #escapeXml11(String)} is the expected method of use, this
     * object allows the XML escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.3
     */
    public static final CharSequenceTranslator ESCAPE_XML11 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.APOS_ESCAPE()), new LookupTranslator(new String[][] { { "\u0000", StringUtils.EMPTY }, { "\u000b", "&#11;" }, { "\u000c", "&#12;" }, { "\ufffe", StringUtils.EMPTY }, { "\uffff", StringUtils.EMPTY } }), NumericEntityEscaper.between(0x1, 0x8), NumericEntityEscaper.between(0xe, 0x1f), NumericEntityEscaper.between(0x7f, 0x84), NumericEntityEscaper.between(0x86, 0x9f), new UnicodeUnpairedSurrogateRemover());

    /**
     * Translator object for escaping HTML version 3.0.
     *
     * While {@link #escapeHtml3(String)} is the expected method of use, this
     * object allows the HTML escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator ESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()));

    /**
     * Translator object for escaping HTML version 4.0.
     *
     * While {@link #escapeHtml4(String)} is the expected method of use, this
     * object allows the HTML escaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator ESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_ESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE()));

    /* UNESCAPE TRANSLATORS */
    /**
     * Translator object for unescaping escaped Java.
     *
     * While {@link #unescapeJava(String)} is the expected method of use, this
     * object allows the Java unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    // TODO: throw "illegal character: \92" as an Exception if a \ on the end of the Java (as per the compiler)?
    public static final CharSequenceTranslator UNESCAPE_JAVA = new AggregateTranslator(// .between('\1', '\377'),
    new OctalUnescaper(), new UnicodeUnescaper(), new LookupTranslator(EntityArrays.JAVA_CTRL_CHARS_UNESCAPE()), new LookupTranslator(new String[][] { { "\\\\", "\\" }, { "\\\"", "\"" }, { "\\'", "'" }, { "\\", "" } }));

    /**
     * Translator object for unescaping escaped EcmaScript.
     *
     * While {@link #unescapeEcmaScript(String)} is the expected method of use, this
     * object allows the EcmaScript unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator UNESCAPE_ECMASCRIPT = UNESCAPE_JAVA;

    /**
     * Translator object for unescaping escaped Json.
     *
     * While {@link #unescapeJson(String)} is the expected method of use, this
     * object allows the Json unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.2
     */
    public static final CharSequenceTranslator UNESCAPE_JSON = UNESCAPE_JAVA;

    /**
     * Translator object for unescaping escaped HTML 3.0.
     *
     * While {@link #unescapeHtml3(String)} is the expected method of use, this
     * object allows the HTML unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator UNESCAPE_HTML3 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new NumericEntityUnescaper());

    /**
     * Translator object for unescaping escaped HTML 4.0.
     *
     * While {@link #unescapeHtml4(String)} is the expected method of use, this
     * object allows the HTML unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator UNESCAPE_HTML4 = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.ISO8859_1_UNESCAPE()), new LookupTranslator(EntityArrays.HTML40_EXTENDED_UNESCAPE()), new NumericEntityUnescaper());

    /**
     * Translator object for unescaping escaped XML.
     *
     * While {@link #unescapeXml(String)} is the expected method of use, this
     * object allows the XML unescaping functionality to be used
     * as the foundation for a custom translator.
     *
     * @since 3.0
     */
    public static final CharSequenceTranslator UNESCAPE_XML = new AggregateTranslator(new LookupTranslator(EntityArrays.BASIC_UNESCAPE()), new LookupTranslator(EntityArrays.APOS_UNESCAPE()), new NumericEntityUnescaper());

    /* Helper functions */
    /**
     * <p>{@code StringEscapeUtils} instances should NOT be constructed in
     * standard programming.</p>
     *
     * <p>Instead, the class should be used as:</p>
     * <pre>StringEscapeUtils.escapeJava("foo");</pre>
     *
     * <p>This constructor is public to permit tools that require a JavaBean
     * instance to operate.</p>
     */
    public StringEscapeUtils() {
        super();
    }

    // Java and JavaScript
    //--------------------------------------------------------------------------
    public static final String escapeJava(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String escapeEcmaScript(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String escapeJson(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String unescapeJava(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String unescapeEcmaScript(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String unescapeJson(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // HTML and XML
    //--------------------------------------------------------------------------
    public static final String escapeHtml4(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String escapeHtml3(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //-----------------------------------------------------------------------
    public static final String unescapeHtml4(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final String unescapeHtml3(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String escapeXml10(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String escapeXml11(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //-----------------------------------------------------------------------
    public static final String unescapeXml(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
    //-----------------------------------------------------------------------
}
