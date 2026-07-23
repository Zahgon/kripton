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
package com.abubusoft.kripton.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * Base64 encoding/decoding util.
 *
 * @author bulldog
 */
public abstract class Base64Utils {

    private Base64Utils() {
    }

    /**
     * The Constant S_BASE64CHAR.
     */
    private static final char[] S_BASE64CHAR = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

    /**
     * The Constant S_BASE64PAD.
     */
    private static final char S_BASE64PAD = '=';

    /**
     * The Constant S_DECODETABLE.
     */
    private static final byte[] S_DECODETABLE = new byte[128];

    static {
        for (int i = 0; i < S_DECODETABLE.length; i++) // 127
        S_DECODETABLE[i] = Byte.MAX_VALUE;
        for (// 0 to 63
        int i = 0; // 0 to 63
        i < S_BASE64CHAR.length; // 0 to 63
        i++) S_DECODETABLE[S_BASE64CHAR[i]] = (byte) i;
    }

    /**
     * Decode 0.
     *
     * @param ibuf the ibuf
     * @param obuf the obuf
     * @param wp the wp
     * @return the int
     */
    private static int decode0(char[] ibuf, byte[] obuf, int wp) {
        int outlen = 3;
        if (ibuf[3] == S_BASE64PAD)
            outlen = 2;
        if (ibuf[2] == S_BASE64PAD)
            outlen = 1;
        int b0 = S_DECODETABLE[ibuf[0]];
        int b1 = S_DECODETABLE[ibuf[1]];
        int b2 = S_DECODETABLE[ibuf[2]];
        int b3 = S_DECODETABLE[ibuf[3]];
        switch(outlen) {
            case 1:
                obuf[wp] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
                return 1;
            case 2:
                obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
                obuf[wp] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
                return 2;
            case 3:
                obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
                obuf[wp++] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
                obuf[wp] = (byte) (b2 << 6 & 0xc0 | b3 & 0x3f);
                return 3;
            default:
                throw new KriptonRuntimeException("Couldn't decode.");
        }
    }

    public static byte[] decode(char[] data, int off, int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decode(String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] decode(byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void decode(char[] data, int off, int len, OutputStream ostream) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void decode(String data, OutputStream ostream) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encode(byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String encode(byte[] data, int off, int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encode(byte[] data, int off, int len, OutputStream ostream) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encode(byte[] data, int off, int len, Writer writer) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
