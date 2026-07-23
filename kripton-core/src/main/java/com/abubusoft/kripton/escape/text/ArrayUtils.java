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
 * <p>Operations on arrays, primitive arrays (like {@code int[]}) and
 * primitive wrapper arrays (like {@code Integer[]}).
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null}
 * array input. However, an Object array that contains a {@code null}
 * element may throw an exception. Each method documents its behaviour.
 *
 * <p>#ThreadSafe#
 * @since 2.0
 */
public class ArrayUtils {

    /**
     * <p>ArrayUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as <code>ArrayUtils.clone(new int[] {2})</code>.
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.
     */
    public ArrayUtils() {
        super();
    }

    // NOTE: Cannot use {@code} to enclose text which includes {}, but <code></code> is OK
    // Clone
    //-----------------------------------------------------------------------
    public static <T> T[] clone(final T[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long[] clone(final long[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int[] clone(final int[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static short[] clone(final short[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] clone(final char[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] clone(final byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static double[] clone(final double[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static float[] clone(final float[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean[] clone(final boolean[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
    // IndexOf search
    // ----------------------------------------------------------------------
    // Primitive/Object array converters
    // ----------------------------------------------------------------------
}
