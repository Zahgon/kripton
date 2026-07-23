/*
 * Copyright (C) 2012 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abubusoft.kripton.common;

import java.util.BitSet;
import com.abubusoft.kripton.common.CharMatcher.NamedFastMatcher;

/**
 * An immutable version of CharMatcher for smallish sets of characters that uses a hash table
 * with linear probing to check for matches.
 *
 * @author Christopher Swenson
 */
final class SmallCharMatcher extends NamedFastMatcher {

    /**
     * The Constant MAX_SIZE.
     */
    static final int MAX_SIZE = 1023;

    /**
     * The table.
     */
    private final char[] table;

    /**
     * The contains zero.
     */
    private final boolean containsZero;

    /**
     * The filter.
     */
    private final long filter;

    /**
     * Instantiates a new small char matcher.
     *
     * @param table the table
     * @param filter the filter
     * @param containsZero the contains zero
     * @param description the description
     */
    private SmallCharMatcher(char[] table, long filter, boolean containsZero, String description) {
        super(description);
        this.table = table;
        this.filter = filter;
        this.containsZero = containsZero;
    }

    /**
     * The Constant C1.
     */
    private static final int C1 = 0xcc9e2d51;

    /**
     * The Constant C2.
     */
    private static final int C2 = 0x1b873593;

    /**
     * Smear.
     *
     * @param hashCode the hash code
     * @return the int
     */
    /*
   * This method was rewritten in Java from an intermediate step of the Murmur hash function in
   * http://code.google.com/p/smhasher/source/browse/trunk/MurmurHash3.cpp, which contained the
   * following header:
   *
   * MurmurHash3 was written by Austin Appleby, and is placed in the public domain. The author
   * hereby disclaims copyright to this source code.
   */
    static int smear(int hashCode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check filter.
     *
     * @param c the c
     * @return true, if successful
     */
    private boolean checkFilter(int c) {
        return 1 == (1 & (filter >> c));
    }

    // This is all essentially copied from ImmutableSet, but we have to duplicate because
    // of dependencies.
    /**
     * The Constant DESIRED_LOAD_FACTOR.
     */
    // Represents how tightly we can pack things, as a maximum.
    private static final double DESIRED_LOAD_FACTOR = 0.5;

    static int chooseTableSize(int setSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static CharMatcher from(BitSet chars, String description) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.common.CharMatcher#matches(char)
   */
    @Override
    public boolean matches(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
   * @see com.abubusoft.kripton.common.CharMatcher#setBits(java.util.BitSet)
   */
    @Override
    void setBits(BitSet table) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
