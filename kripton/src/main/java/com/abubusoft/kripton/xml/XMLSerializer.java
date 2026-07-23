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
package com.abubusoft.kripton.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Stack;
import com.abubusoft.kripton.common.Base64Utils;
import com.fasterxml.jackson.core.JsonEncoding;

/**
 * Implementation of MXSerializer interface from XmlPull V1 API. This
 * implementation is optimzied for performance and low memory footprint.
 *
 * <p>
 * Implemented features:
 * <ul>
 * <li>FEATURE_NAMES_INTERNED - when enabled all returned names (namespaces,
 * prefixes) will be interned and it is required that all names passed as
 * arguments MUST be interned
 * <li>FEATURE_SERIALIZER_ATTVALUE_USE_APOSTROPHE
 * </ul>
 * <p>
 * Implemented properties:
 * <ul>
 * <li>PROPERTY_SERIALIZER_INDENTATION
 * <li>PROPERTY_SERIALIZER_LINE_SEPARATOR
 * </ul>
 */
/**
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class XMLSerializer {

    /**
     * The Constant XML_URI.
     */
    protected final static String XML_URI = "http://www.w3.org/XML/1998/namespace";

    /**
     * The Constant XMLNS_URI.
     */
    protected final static String XMLNS_URI = "http://www.w3.org/2000/xmlns/";

    /**
     * The Constant TRACE_SIZING.
     */
    private static final boolean TRACE_SIZING = false;

    /**
     * The Constant TRACE_ESCAPING.
     */
    private static final boolean TRACE_ESCAPING = false;

    /**
     * The Constant FEATURE_SERIALIZER_ATTVALUE_USE_APOSTROPHE.
     */
    public static final String FEATURE_SERIALIZER_ATTVALUE_USE_APOSTROPHE = "http://xmlpull.org/v1/doc/features.html#serializer-attvalue-use-apostrophe";

    /**
     * The feature names interned.
     */
    protected final String FEATURE_NAMES_INTERNED = "http://xmlpull.org/v1/doc/features.html#names-interned";

    /**
     * The Constant PROPERTY_SERIALIZER_INDENTATION.
     */
    public static final String PROPERTY_SERIALIZER_INDENTATION = "http://xmlpull.org/v1/doc/properties.html#serializer-indentation";

    /**
     * The Constant PROPERTY_SERIALIZER_LINE_SEPARATOR.
     */
    public static final String PROPERTY_SERIALIZER_LINE_SEPARATOR = "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator";

    /**
     * The Constant PROPERTY_LOCATION.
     */
    protected final static String PROPERTY_LOCATION = "http://xmlpull.org/v1/doc/properties.html#location";

    /**
     * The names interned.
     */
    // properties/features
    protected boolean namesInterned;

    /**
     * The attribute use apostrophe.
     */
    protected boolean attributeUseApostrophe;

    /**
     * The indentation string.
     */
    // " ";
    protected String indentationString = null;

    /**
     * The line separator.
     */
    protected String lineSeparator = "\n";

    /**
     * The location.
     */
    protected String location;

    /**
     * The out.
     */
    protected Writer out;

    /**
     * The auto declared prefixes.
     */
    protected int autoDeclaredPrefixes;

    /**
     * The depth.
     */
    protected int depth = 0;

    /**
     * The el namespace.
     */
    // element stack
    protected String[] elNamespace = new String[2];

    /**
     * The el name.
     */
    protected String[] elName = new String[elNamespace.length];

    /**
     * The el prefix.
     */
    protected String[] elPrefix = new String[elNamespace.length];

    /**
     * The el namespace count.
     */
    protected int[] elNamespaceCount = new int[elNamespace.length];

    /**
     * The namespace end.
     */
    // namespace stack
    protected int namespaceEnd = 0;

    /**
     * The namespace prefix.
     */
    protected String[] namespacePrefix = new String[8];

    /**
     * The namespace uri.
     */
    protected String[] namespaceUri = new String[namespacePrefix.length];

    /**
     * The finished.
     */
    protected boolean finished;

    /**
     * The past root.
     */
    protected boolean pastRoot;

    /**
     * The set prefix called.
     */
    protected boolean setPrefixCalled;

    /**
     * The start tag incomplete.
     */
    protected boolean startTagIncomplete;

    /**
     * The do indent.
     */
    protected boolean doIndent;

    /**
     * The seen tag.
     */
    protected boolean seenTag;

    /**
     * The seen bracket.
     */
    protected boolean seenBracket;

    /**
     * The seen bracket bracket.
     */
    protected boolean seenBracketBracket;

    /**
     * The Constant BUF_LEN.
     */
    // buffer output if neede to write escaped String see text(String)
    private static final int BUF_LEN = Runtime.getRuntime().freeMemory() > 1000000L ? 8 * 1024 : 256;

    /**
     * The buf.
     */
    protected char[] buf = new char[BUF_LEN];

    /**
     * The Constant precomputedPrefixes.
     */
    protected static final String[] precomputedPrefixes;

    static {
        // arbitrary number ...
        precomputedPrefixes = new String[32];
        for (int i = 0; i < precomputedPrefixes.length; i++) {
            precomputedPrefixes[i] = ("n" + i).intern();
        }
    }

    /**
     * The check names interned.
     */
    private boolean checkNamesInterned = false;

    /**
     * Instantiates a new XML serializer.
     *
     * @param writer the writer
     */
    public XMLSerializer(Writer writer) {
        setOutput(writer);
    }

    /**
     * Check interning.
     *
     * @param name the name
     */
    private void checkInterning(String name) {
        if (namesInterned && name != name.intern()) {
            throw new IllegalArgumentException("all names passed as arguments must be interned" + "when NAMES INTERNED feature is enabled");
        }
    }

    protected void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void ensureElementsCapacity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void ensureNamespacesCapacity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setFeature(String name, boolean state) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean getFeature(String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The offset new line.
     */
    // precomputed variables to simplify writing indentation
    protected int offsetNewLine;

    /**
     * The indentation jump.
     */
    protected int indentationJump;

    /**
     * The indentation buf.
     */
    protected char[] indentationBuf;

    /**
     * The max indent level.
     */
    protected int maxIndentLevel;

    /**
     * The write line separtor.
     */
    // should end-of-line be written
    protected boolean writeLineSepartor;

    /**
     * The write indentation.
     */
    // is indentation used?
    protected boolean writeIndentation;

    protected void rebuildIndentationBuf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Write indent.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    // if(doIndent) writeIndent();
    protected void writeIndent() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setProperty(String name, Object value) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Object getProperty(String name) throws IllegalArgumentException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    private String getLocation() {
        return location != null ? " @" + location : "";
    }

    public Writer getWriter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setOutput(Writer writer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setOutput(OutputStream os, String encoding) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void startDocument(String encoding, Boolean standalone) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void endDocument() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unused")
    public void setPrefix(String prefix, String namespace) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected String lookupOrDeclarePrefix(String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getPrefix(String namespace, boolean generatePrefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected String getPrefix(String namespace, boolean generatePrefix, boolean nonEmpty) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate prefix.
     *
     * @param namespace the namespace
     * @return the string
     */
    private String generatePrefix(String namespace) {
        // assert namespace == namespace.intern();
        while (true) {
            ++autoDeclaredPrefixes;
            // fast lookup uses table that was pre-initialized in static{} ....
            final String prefix = autoDeclaredPrefixes < precomputedPrefixes.length ? precomputedPrefixes[autoDeclaredPrefixes] : ("n" + autoDeclaredPrefixes).intern();
            // make sure this prefix is not declared in any scope (avoid hiding
            // in-scope prefixes)!
            for (int i = namespaceEnd - 1; i >= 0; --i) {
                if (prefix == namespacePrefix[i]) {
                    // prefix is already declared - generate new and
                    continue;
                    // try again
                }
            }
            // declare prefix
            if (namespaceEnd >= namespacePrefix.length) {
                ensureNamespacesCapacity();
            }
            namespacePrefix[namespaceEnd] = prefix;
            namespaceUri[namespaceEnd] = namespace;
            ++namespaceEnd;
            return prefix;
        }
    }

    public int getDepth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getNamespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public XMLSerializer startTag(String namespace, String name) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public XMLSerializer attribute(String namespace, String name, String value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void closeStartTag() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Write namespace declarations.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void writeNamespaceDeclarations() throws IOException {
        // int start = elNamespaceCount[ depth - 1 ];
        for (int i = elNamespaceCount[depth - 1]; i < namespaceEnd; i++) {
            if (doIndent && namespaceUri[i].length() > 40) {
                writeIndent();
                out.write(" ");
            }
            if ("".equals(namespacePrefix[i])) {
                out.write(" xmlns:");
                out.write(namespacePrefix[i]);
                out.write('=');
            } else {
                out.write(" xmlns=");
            }
            out.write(attributeUseApostrophe ? '\'' : '"');
            // NOTE: escaping of namespace value the same way as attributes!!!!
            writeAttributeValue(namespaceUri[i], out);
            out.write(attributeUseApostrophe ? '\'' : '"');
        }
    }

    public XMLSerializer endTag(String namespace, String name) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public XMLSerializer text(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public XMLSerializer text(char[] buf, int start, int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void cdsect(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void entityRef(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void processingInstruction(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void comment(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void docdecl(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void ignorableWhitespace(String text) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void flush() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // --- utility methods
    protected void writeAttributeValue(String value, Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void writeElementContent(String text, Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void writeElementContent(char[] buf, int off, int len, Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static final String printable(String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static final String printable(char ch) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds the printable.
     *
     * @param retval the retval
     * @param ch the ch
     */
    private static void addPrintable(StringBuffer retval, char ch) {
        switch(ch) {
            case '\b':
                retval.append("\\b");
                break;
            case '\t':
                retval.append("\\t");
                break;
            case '\n':
                retval.append("\\n");
                break;
            case '\f':
                retval.append("\\f");
                break;
            case '\r':
                retval.append("\\r");
                break;
            case '\"':
                retval.append("\\\"");
                break;
            case '\'':
                retval.append("\\\'");
                break;
            case '\\':
                retval.append("\\\\");
                break;
            default:
                if (ch < 0x20 || ch > 0x7e) {
                    final String ss = "0000" + Integer.toString(ch, 16);
                    retval.append("\\u" + ss.substring(ss.length() - 4, ss.length()));
                } else {
                    retval.append(ch);
                }
        }
    }

    /**
     * The name stack.
     */
    protected Stack<String> nameStack = new Stack<>();

    public void writeStartElement(String tag) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeAttribute(String attributeName, String value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeCData(String value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeEndElement() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeCharacters(String value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeBoolean(boolean valueBool) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeInt(int valueInt) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeFloat(float valueFloatType) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLong(long valueLong) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeDouble(double valueDouble) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeEmptyElement(String tag) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeBinary(byte[] value, int start, int length) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeBinary(byte[] value) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeDecimalAttribute(String prefix, String namespaceURI, String localName, BigDecimal value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeIntegerAttribute(String prefix, String namespaceURI, String localName, BigInteger value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeDoubleAttribute(String prefix, String namespaceURI, String localName, double value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeAttribute(String namespaceURI, String localName, String value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeBooleanAttribute(String prefix, String namespaceURI, String localName, boolean value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeEmptyElement(String namespaceURI, String localName) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeComment(String data) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeDecimal(BigDecimal value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeEndDocument() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeStartDocument() throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
