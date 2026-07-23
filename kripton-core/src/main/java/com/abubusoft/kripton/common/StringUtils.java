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

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * A few string utils.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class StringUtils {

    private StringUtils() {
    }

    /**
     * The Constant VIEW_SIZE.
     */
    private static final int VIEW_SIZE = 64;

    public static boolean isEmpty(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String startWithSpace(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean hasText(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String checkSize(Object value, int limitSize, String defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Constant hexArray.
     */
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String checkSize(byte[] value, int limitSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String formatParam(Object value, String delimiter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String checkSize(Object value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String checkSize(Object value, String defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String checkSize(byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String lowercaseFirstLetter(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void string2Writer(String source, Writer out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String reader2String(Reader source) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String ifNotEmptyAppend(String chekString, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String ifNotEmptyPrepend(String chekString, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String nvl(String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String printIf(boolean test, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
