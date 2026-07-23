/*
 * Copyright 2013-2014 Richard M. Hightower
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * __________                              _____          __   .__
 * \______   \ ____   ____   ____   /\    /     \ _____  |  | _|__| ____    ____
 *  |    |  _//  _ \ /  _ \ /    \  \/   /  \ /  \\__  \ |  |/ /  |/    \  / ___\
 *  |    |   (  <_> |  <_> )   |  \ /\  /    Y    \/ __ \|    <|  |   |  \/ /_/  >
 *  |______  /\____/ \____/|___|  / \/  \____|__  (____  /__|_ \__|___|  /\___  /
 *         \/                   \/              \/     \/     \/       \//_____/
 *      ____.                     ___________   _____    ______________.___.
 *     |    |____ ___  _______    \_   _____/  /  _  \  /   _____/\__  |   |
 *     |    \__  \\  \/ /\__  \    |    __)_  /  /_\  \ \_____  \  /   |   |
 * /\__|    |/ __ \\   /  / __ \_  |        \/    |    \/        \ \____   |
 * \________(____  /\_/  (____  / /_______  /\____|__  /_______  / / ______|
 *               \/           \/          \/         \/        \/  \/
 */
package com.abubusoft.kripton.common;

import java.nio.charset.StandardCharsets;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * The Class DynamicByteBufferHelper.
 */
public class DynamicByteBufferHelper {

    public static byte[] grow(byte[] array, final int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] grow(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] shrink(byte[] array, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] compact(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] arrayOfByte(final int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Array.
     *
     * @param array            array
     * @return array
     */
    public static byte[] array(final byte... array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Bytes.
     *
     * @param array            array
     * @return array
     */
    public static byte[] bytes(final byte... array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Bytes.
     *
     * @param str            string
     * @return array
     */
    public static byte[] bytes(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int len(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int lengthOf(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte atIndex(final byte[] array, final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte idx(final byte[] array, final int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void atIndex(final byte[] array, int index, byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void idx(final byte[] array, int index, byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sliceOf(byte[] array, int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] slc(byte[] array, int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] sliceOf(byte[] array, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] slc(byte[] array, int startIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] endSliceOf(byte[] array, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] slcEnd(byte[] array, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean in(int value, byte... array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean inIntArray(byte value, int[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean in(int value, int offset, byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean in(int value, int offset, int end, byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] copy(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] copy(byte[] array, int offset, int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] add(byte[] array, byte v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] add(byte[] array, byte[] array2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insert(final byte[] array, final int idx, final byte v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insert(final byte[] array, final int fromIndex, final byte[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculate index.
     *
     * @param array the array
     * @param originalIndex the original index
     * @return the int
     */
    /* End universal methods. */
    private static int calculateIndex(byte[] array, int originalIndex) {
        final int length = array.length;
        int index = originalIndex;
        /*
		 * Adjust for reading from the right as in -1 reads the 4th element if
		 * the length is 5
		 */
        if (index < 0) {
            index = length + index;
        }
        /*
		 * Bounds check if it is still less than 0, then they have an negative
		 * index that is greater than length
		 */
        /*
		 * Bounds check if it is still less than 0, then they have an negative
		 * index that is greater than length
		 */
        if (index < 0) {
            index = 0;
        }
        if (index >= length) {
            index = length - 1;
        }
        return index;
    }

    /**
     * Calculate end index.
     *
     * @param array the array
     * @param originalIndex the original index
     * @return the int
     */
    /* End universal methods. */
    private static int calculateEndIndex(byte[] array, int originalIndex) {
        final int length = array.length;
        int index = originalIndex;
        /*
		 * Adjust for reading from the right as in -1 reads the 4th element if
		 * the length is 5
		 */
        if (index < 0) {
            index = length + index;
        }
        /*
		 * Bounds check if it is still less than 0, then they have an negative
		 * index that is greater than length
		 */
        /*
		 * Bounds check if it is still less than 0, then they have an negative
		 * index that is greater than length
		 */
        if (index < 0) {
            index = 0;
        }
        if (index > length) {
            index = length;
        }
        return index;
    }

    public static int idxInt(byte[] bytes, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addInt(byte[] array, int v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insertIntInto(byte[] array, int index, int v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void intTo(byte[] b, int off, int val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void longTo(byte[] b, int off, long val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addLong(byte[] array, long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long idxUnsignedInt(byte[] bytes, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long idxLong(byte[] b, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static short idxShort(byte[] b, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addShort(byte[] array, short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insertShortInto(byte[] array, int index, short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void shortTo(byte[] b, int off, short val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char idxChar(byte[] b, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addChar(byte[] array, char value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insertCharInto(byte[] array, int index, char value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void charTo(byte[] b, int off, char val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void charTo(byte[] b, char val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static float idxFloat(byte[] array, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addFloat(byte[] array, float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insertFloatInto(byte[] array, int index, float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void floatTo(byte[] array, int off, float val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] addDouble(byte[] array, double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] insertDoubleInto(byte[] array, int index, double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void doubleTo(byte[] b, int off, double val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static double idxDouble(byte[] b, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //
    //
    // public static boolean booleanAt(byte[] b, int off) {
    // return b[off] != 0;
    // }
    //
    //
    // public static boolean booleanInBytePos1(int val) {
    // val = val & 0x01;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos2(int val) {
    // val = val & 0x02;
    // return val != 0;
    // }
    //
    //
    // public static boolean booleanInBytePos3(int val) {
    // val = val & 0x04;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos4(int val) {
    // val = val & 0x08;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos1(byte[] b, int off) {
    // int val = b[off] & 0x01;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos2(byte[] b, int off) {
    // int val = b[off] & 0x02;
    // return val != 0;
    // }
    //
    //
    // public static boolean booleanInBytePos3(byte[] b, int off) {
    // int val = b[off] & 0x04;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos4(byte[] b, int off) {
    // int val = b[off] & 0x08;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos5(byte[] b, int off) {
    // int val = b[off] & 0x10;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos6(byte[] b, int off) {
    // int val = b[off] & 0x20;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos7(byte[] b, int off) {
    // int val = b[off] & 0x40;
    // return val != 0;
    // }
    //
    // public static boolean booleanInBytePos8(byte[] b, int off) {
    // int val = b[off] & 0x80;
    // return val != 0;
    // }
    //
    //
    // public static int byteAt(byte[] b, int off) {
    // return b[off];
    // }
    //
    //
    // public static int topNibbleAt(byte[] b, int off) {
    // return topNibbleAt (b[off] );
    // }
    //
    // public static int bottomNibbleAt(byte[] b, int off) {
    // return bottomNibbleAt (b[off] );
    // }
    //
    // public static int topNibbleAt(int val) {
    // return (val & 0xF0);
    // }
    //
    // public static int bottomNibbleAt(int val) {
    // return (val & 0x0F);
    // }
    //
    //
    // public static char charAt1(byte[] b, int off) {
    // return (char) ((b[off + 1] & 0xFF) +
    // (b[off] << 8));
    // }
    //
    public static void _idx(final byte[] array, int startIndex, byte[] input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void _idx(final byte[] array, int startIndex, byte[] input, int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void _idx(final byte[] output, int ouputStartIndex, byte[] input, int inputOffset, int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int idxUnsignedShort(byte[] buffer, int off) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static short idxUnsignedByte(byte[] array, int location) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void unsignedIntTo(byte[] b, int off, long val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void unsignedShortTo(byte[] buffer, int off, int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void unsignedByteTo(byte[] buffer, int off, short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String utfString(byte[] jsonBytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean equalsOrDie(byte[] expected, byte[] got) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean equals(byte[] expected, byte[] got) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
