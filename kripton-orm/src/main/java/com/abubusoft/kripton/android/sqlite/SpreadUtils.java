package com.abubusoft.kripton.android.sqlite;

import java.util.Collection;

/**
 * Contains utilitity function for split arguments and generate ?.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class SpreadUtils {

    public static <E> String generateQuestion(byte[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @param args
     * @return
     */
    private static String generateInternal(int length) {
        StringBuilder buffer = new StringBuilder("?");
        for (int i = 1; i < length; i++) {
            buffer.append(", ?");
        }
        return buffer.toString();
    }

    public static <E> String generateQuestion(char[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(short[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(int[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(long[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(float[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(double[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(E[] args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <E> String generateQuestion(Collection<E> args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
