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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import com.abubusoft.kripton.exception.KriptonRuntimeException;

/**
 * Utility to work with list and array Json conversion.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class CollectionUtils {

    private CollectionUtils() {
    }

    public static <L extends Collection<T>, E extends Collection<T>, T> L merge(L collection, E initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<T>, T> L merge(L collection, T[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Byte>> L merge(L collection, byte[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Byte>> L merge(L collection, Byte[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Short>> L merge(L collection, short[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Short>> L merge(L collection, Short[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Character>> L merge(L collection, char[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Character>> L merge(L collection, Character[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Double>> L merge(L collection, double[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Double>> L merge(L collection, Double[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Float>> L merge(L collection, float[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Float>> L merge(L collection, Float[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Integer>> L merge(L collection, int[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Integer>> L merge(L collection, Integer[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Long>> L merge(L collection, long[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <L extends Collection<Long>> L merge(L collection, Long[] initialValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<T> asSet(Class<T> itemType, T... objects) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Byte>> E asList(Byte[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * As list.
     *
     * @param <E>
     *            the element type
     * @param array
     *            the array
     * @param listType
     *            the list type
     * @return the e
     */
    //
    public static <E extends List<Boolean>> E asList(Boolean[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Character>> E asList(Character[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    public static <E extends List<T>, T> E asList(T[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Short>> E asList(Short[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Integer>> E asList(Integer[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Long>> List<Long> asList(Long[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Float>> List<Float> asList(Float[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Double>> List<Double> asList(Double[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    //
    public static <E extends List<Boolean>> E asList(boolean[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends Collection<Character>> E asList(char[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Short>> E asList(short[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Integer>> E asList(int[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Long>> List<Long> asList(long[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Float>> List<Float> asList(float[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E extends List<Double>> List<Double> asList(double[] array, Class<E> listType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean[] asBooleanTypeArray(List<Boolean> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static byte[] asByteTypeArray(List<Byte> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static char[] asCharacterTypeArray(List<Character> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static short[] asShortTypeArray(List<Short> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int[] asIntegerTypeArray(List<Integer> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static long[] asLongTypeArray(List<Long> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static float[] asFloatTypeArray(List<Float> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static double[] asDoubleTypeArray(List<Double> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String[] asStringArray(List<String> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Boolean[] asBooleanArray(List<Boolean> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Byte[] asByteArray(List<Byte> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Character[] asCharacterArray(List<Character> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Short[] asShortArray(List<Short> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Integer[] asIntegerArray(List<Integer> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Long[] asLongArray(List<Long> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> E[] asArray(List<E> input, E[] newArray) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Float[] asFloatArray(List<Float> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Double[] asDoubleArray(List<Double> input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void trim(List<String> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
