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

import static com.abubusoft.kripton.common.Preconditions.checkArgument;
import static com.abubusoft.kripton.common.Preconditions.checkNotNull;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Determines a true or false value for any Java {@code char} value, just as {@link Predicate} does
 * for any {@link Object}. Also offers basic text processing methods based on this function.
 * Implementations are strongly encouraged to be side-effect-free and immutable.
 *
 * <p>Throughout the documentation of this class, the phrase "matching character" is used to mean
 * "any character {@code c} for which {@code this.matches(c)} returns {@code true}".
 *
 * <p><b>Note:</b> This class deals only with {@code char} values; it does not understand
 * supplementary Unicode code points in the range {@code 0x10000} to {@code 0x10FFFF}. Such logical
 * characters are encoded into a {@code String} using surrogate pairs, and a {@code CharMatcher}
 * treats these just as two separate characters.
 *
 * <p>Example usages: <pre>
 *   String trimmed = {@link #WHITESPACE WHITESPACE}.{@link #trimFrom trimFrom}(userInput);
 *   if ({@link #ASCII ASCII}.{@link #matchesAllOf matchesAllOf}(s)) { ... }</pre>
 *
 * <p>See the Guava User Guide article on <a href=
 * "http://code.google.com/p/guava-libraries/wiki/StringsExplained#CharMatcher">
 * {@code CharMatcher}</a>.
 *
 * @author Kevin Bourrillion
 * @since 1.0
 */
public abstract class CharMatcher implements Predicate<Character> {

    // Constants
    /**
     * Determines whether a character is a breaking whitespace (that is, a whitespace which can be
     * interpreted as a break between words for formatting purposes). See {@link #WHITESPACE} for a
     * discussion of that term.
     *
     * @since 2.0
     */
    public static final CharMatcher BREAKING_WHITESPACE = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is ASCII, meaning that its code point is less than 128.
     */
    public static final CharMatcher ASCII = new NamedFastMatcher("CharMatcher.ASCII") {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * The Class RangesMatcher.
     */
    private static class RangesMatcher extends CharMatcher {

        /**
         * The description.
         */
        private final String description;

        /**
         * The range starts.
         */
        private final char[] rangeStarts;

        /**
         * The range ends.
         */
        private final char[] rangeEnds;

        /**
         * Instantiates a new ranges matcher.
         *
         * @param description the description
         * @param rangeStarts the range starts
         * @param rangeEnds the range ends
         */
        RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
            this.description = description;
            this.rangeStarts = rangeStarts;
            this.rangeEnds = rangeEnds;
            checkArgument(rangeStarts.length == rangeEnds.length);
            for (int i = 0; i < rangeStarts.length; i++) {
                checkArgument(rangeStarts[i] <= rangeEnds[i]);
                if (i + 1 < rangeStarts.length) {
                    checkArgument(rangeEnds[i] < rangeStarts[i + 1]);
                }
            }
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#matches(char)
     */
        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#toString()
     */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Constant ZEROES.
     */
    // Must be in ascending order.
    private static final String ZEROES = "0\u0660\u06f0\u07c0\u0966\u09e6\u0a66\u0ae6\u0b66\u0be6" + "\u0c66\u0ce6\u0d66\u0e50\u0ed0\u0f20\u1040\u1090\u17e0\u1810\u1946\u19d0\u1b50\u1bb0" + "\u1c40\u1c50\ua620\ua8d0\ua900\uaa50\uff10";

    /**
     * Determines whether a character is a digit according to
     * <a href="http://unicode.org/cldr/utility/list-unicodeset.jsp?a=%5Cp%7Bdigit%7D">Unicode</a>.
     * If you only care to match ASCII digits, you can use {@code inRange('0', '9')}.
     */
    public static final CharMatcher DIGIT;

    static {
        char[] zeroes = ZEROES.toCharArray();
        char[] nines = new char[zeroes.length];
        for (int i = 0; i < zeroes.length; i++) {
            nines[i] = (char) (zeroes[i] + 9);
        }
        DIGIT = new RangesMatcher("CharMatcher.DIGIT", zeroes, nines);
    }

    /**
     * Determines whether a character is a digit according to {@linkplain Character#isDigit(char)
     * Java's definition}. If you only care to match ASCII digits, you can use {@code
     * inRange('0', '9')}.
     */
    public static final CharMatcher JAVA_DIGIT = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is a letter according to {@linkplain Character#isLetter(char)
     * Java's definition}. If you only care to match letters of the Latin alphabet, you can use {@code
     * inRange('a', 'z').or(inRange('A', 'Z'))}.
     */
    public static final CharMatcher JAVA_LETTER = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is a letter or digit according to {@linkplain
     * Character#isLetterOrDigit(char) Java's definition}.
     */
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is upper case according to {@linkplain
     * Character#isUpperCase(char) Java's definition}.
     */
    public static final CharMatcher JAVA_UPPER_CASE = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is lower case according to {@linkplain
     * Character#isLowerCase(char) Java's definition}.
     */
    public static final CharMatcher JAVA_LOWER_CASE = new CharMatcher() {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is an ISO control character as specified by {@link
     * Character#isISOControl(char)}.
     */
    public static final CharMatcher JAVA_ISO_CONTROL = new NamedFastMatcher("CharMatcher.JAVA_ISO_CONTROL") {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Determines whether a character is invisible; that is, if its Unicode category is any of
     * SPACE_SEPARATOR, LINE_SEPARATOR, PARAGRAPH_SEPARATOR, CONTROL, FORMAT, SURROGATE, and
     * PRIVATE_USE according to ICU4J.
     */
    public static final CharMatcher INVISIBLE = new RangesMatcher("CharMatcher.INVISIBLE", ("\u0000\u007f\u00ad\u0600\u061c\u06dd\u070f\u1680\u180e\u2000\u2028\u205f\u2066\u2067\u2068" + "\u2069\u206a\u3000\ud800\ufeff\ufff9\ufffa").toCharArray(), ("\u0020\u00a0\u00ad\u0604\u061c\u06dd\u070f\u1680\u180e\u200f\u202f\u2064\u2066\u2067\u2068" + "\u2069\u206f\u3000\uf8ff\ufeff\ufff9\ufffb").toCharArray());

    /**
     * Show character.
     *
     * @param c the c
     * @return the string
     */
    private static String showCharacter(char c) {
        String hex = "0123456789ABCDEF";
        char[] tmp = { '\\', 'u', '\0', '\0', '\0', '\0' };
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 0xF);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    /**
     * Determines whether a character is single-width (not double-width). When in doubt, this matcher
     * errs on the side of returning {@code false} (that is, it tends to assume a character is
     * double-width).
     *
     * <p><b>Note:</b> as the reference file evolves, we will modify this constant to keep it up to
     * date.
     */
    public static final CharMatcher SINGLE_WIDTH = new RangesMatcher("CharMatcher.SINGLE_WIDTH", "\u0000\u05be\u05d0\u05f3\u0600\u0750\u0e00\u1e00\u2100\ufb50\ufe70\uff61".toCharArray(), "\u04f9\u05be\u05ea\u05f4\u06ff\u077f\u0e7f\u20af\u213a\ufdff\ufeff\uffdc".toCharArray());

    /**
     * Matches any character.
     */
    public static final CharMatcher ANY = new NamedFastMatcher("CharMatcher.ANY") {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int indexIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int indexIn(CharSequence sequence, int start) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int lastIndexIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String removeFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String trimFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int countIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher negate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    /**
     * Matches no characters.
     */
    public static final CharMatcher NONE = new NamedFastMatcher("CharMatcher.NONE") {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int indexIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int indexIn(CharSequence sequence, int start) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int lastIndexIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String removeFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String replaceFrom(CharSequence sequence, char replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String replaceFrom(CharSequence sequence, CharSequence replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String collapseFrom(CharSequence sequence, char replacement) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String trimFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String trimLeadingFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String trimTrailingFrom(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int countIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher and(CharMatcher other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher or(CharMatcher other) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public CharMatcher negate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    // Static factories
    public static CharMatcher is(final char match) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static CharMatcher isNot(final char match) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static CharMatcher anyOf(final CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if is either.
     *
     * @param match1 the match 1
     * @param match2 the match 2
     * @return the char matcher
     */
    private static CharMatcher isEither(final char match1, final char match2) {
        return new FastMatcher() {

            @Override
            public boolean matches(char c) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            void setBits(BitSet table) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public String toString() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static CharMatcher inRange(final char startInclusive, final char endInclusive) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Constructors
    /**
     * Constructor for use by subclasses. When subclassing, you may want to override
     * {@code toString()} to provide a useful description.
     */
    protected CharMatcher() {
    }

    // Abstract methods
    /**
     *  Determines a true or false value for the given character.
     *
     * @param c the c
     * @return true, if successful
     */
    public abstract boolean matches(char c);

    // Non-static factories
    public CharMatcher negate() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class NegatedMatcher.
     */
    private static class NegatedMatcher extends CharMatcher {

        /**
         * The original.
         */
        final CharMatcher original;

        /**
         * Instantiates a new negated matcher.
         *
         * @param original the original
         */
        NegatedMatcher(CharMatcher original) {
            this.original = checkNotNull(original);
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#matches(char)
     */
        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#matchesAllOf(java.lang.CharSequence)
     */
        @Override
        public boolean matchesAllOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#matchesNoneOf(java.lang.CharSequence)
     */
        @Override
        public boolean matchesNoneOf(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#countIn(java.lang.CharSequence)
     */
        @Override
        public int countIn(CharSequence sequence) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#setBits(java.util.BitSet)
     */
        @Override
        void setBits(BitSet table) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#negate()
     */
        @Override
        public CharMatcher negate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#toString()
     */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public CharMatcher and(CharMatcher other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class And.
     */
    private static class And extends CharMatcher {

        /**
         * The first.
         */
        final CharMatcher first;

        /**
         * The second.
         */
        final CharMatcher second;

        /**
         * Instantiates a new and.
         *
         * @param a the a
         * @param b the b
         */
        And(CharMatcher a, CharMatcher b) {
            first = checkNotNull(a);
            second = checkNotNull(b);
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

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#toString()
     */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public CharMatcher or(CharMatcher other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class Or.
     */
    private static class Or extends CharMatcher {

        /**
         * The first.
         */
        final CharMatcher first;

        /**
         * The second.
         */
        final CharMatcher second;

        /**
         * Instantiates a new or.
         *
         * @param a the a
         * @param b the b
         */
        Or(CharMatcher a, CharMatcher b) {
            first = checkNotNull(a);
            second = checkNotNull(b);
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#setBits(java.util.BitSet)
     */
        @Override
        void setBits(BitSet table) {
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
     * @see com.abubusoft.kripton.common.CharMatcher#toString()
     */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public CharMatcher precomputed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Constant DISTINCT_CHARS.
     */
    private static final int DISTINCT_CHARS = Character.MAX_VALUE - Character.MIN_VALUE + 1;

    CharMatcher precomputedInternal() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A matcher for which precomputation will not yield any significant benefit.
     */
    abstract static class FastMatcher extends CharMatcher {

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#precomputed()
     */
        @Override
        public final CharMatcher precomputed() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#negate()
     */
        @Override
        public CharMatcher negate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class NamedFastMatcher.
     */
    abstract static class NamedFastMatcher extends FastMatcher {

        /**
         * The description.
         */
        private final String description;

        /**
         * Instantiates a new named fast matcher.
         *
         * @param description the description
         */
        NamedFastMatcher(String description) {
            this.description = checkNotNull(description);
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#toString()
     */
        @Override
        public final String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * The Class NegatedFastMatcher.
     */
    static class NegatedFastMatcher extends NegatedMatcher {

        /**
         * Instantiates a new negated fast matcher.
         *
         * @param original the original
         */
        NegatedFastMatcher(CharMatcher original) {
            super(original);
        }

        /* (non-Javadoc)
     * @see com.abubusoft.kripton.common.CharMatcher#precomputed()
     */
        @Override
        public final CharMatcher precomputed() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Helper method for {@link #precomputedInternal} that doesn't test if the negation is cheaper.
     *
     * @param totalCharacters the total characters
     * @param table the table
     * @param description the description
     * @return the char matcher
     */
    private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
        switch(totalCharacters) {
            case 0:
                return NONE;
            case 1:
                return is((char) table.nextSetBit(0));
            case 2:
                char c1 = (char) table.nextSetBit(0);
                char c2 = (char) table.nextSetBit(c1 + 1);
                return isEither(c1, c2);
            default:
                return isSmall(totalCharacters, table.length()) ? SmallCharMatcher.from(table, description) : new BitSetMatcher(table, description);
        }
    }

    /**
     * Checks if is small.
     *
     * @param totalCharacters the total characters
     * @param tableLength the table length
     * @return true, if is small
     */
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= SmallCharMatcher.MAX_SIZE && tableLength > (totalCharacters * 4 * Character.SIZE);
        // err on the side of BitSetMatcher
    }

    /**
     * The Class BitSetMatcher.
     */
    private static class BitSetMatcher extends NamedFastMatcher {

        /**
         * The table.
         */
        private final BitSet table;

        /**
         * Instantiates a new bit set matcher.
         *
         * @param table the table
         * @param description the description
         */
        private BitSetMatcher(BitSet table, String description) {
            super(description);
            if (table.length() + Long.SIZE < table.size()) {
                table = (BitSet) table.clone();
                // If only we could actually call BitSet.trimToSize() ourselves...
            }
            this.table = table;
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
        void setBits(BitSet bitSet) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    void setBits(BitSet table) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Text processing routines
    public boolean matchesAnyOf(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean matchesAllOf(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int indexIn(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int indexIn(CharSequence sequence, int start) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int lastIndexIn(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int countIn(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String removeFrom(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String retainFrom(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String replaceFrom(CharSequence sequence, char replacement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String trimFrom(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String trimLeadingFrom(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String trimTrailingFrom(CharSequence sequence) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String collapseFrom(CharSequence sequence, char replacement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finish collapse from.
     *
     * @param sequence the sequence
     * @param start the start
     * @param end the end
     * @param replacement the replacement
     * @param builder the builder
     * @param inMatchingGroup the in matching group
     * @return the string
     */
    private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (!inMatchingGroup) {
                    builder.append(replacement);
                    inMatchingGroup = true;
                }
            } else {
                builder.append(c);
                inMatchingGroup = false;
            }
        }
        return builder.toString();
    }

    /**
     * Apply.
     *
     * @param character the character
     * @return true, if successful
     * @deprecated Provided only to satisfy the {@link Predicate} interface; use {@link #matches}
     *     instead.
     */
    @Deprecated
    @Override
    public boolean apply(Character character) {
        return matches(character);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Constant WHITESPACE_TABLE.
     */
    static final String WHITESPACE_TABLE = "" + "\u2002\u3000\r\u0085\u200A\u2005\u2000\u3000" + "\u2029\u000B\u3000\u2008\u2003\u205F\u3000\u1680" + "\u0009\u0020\u2006\u2001\u202F\u00A0\u000C\u2009" + "\u3000\u2004\u3000\u3000\u2028\n\u2007\u3000";

    /**
     * The Constant WHITESPACE_MULTIPLIER.
     */
    static final int WHITESPACE_MULTIPLIER = 1682554634;

    /**
     * The Constant WHITESPACE_SHIFT.
     */
    static final int WHITESPACE_SHIFT = Integer.numberOfLeadingZeros(WHITESPACE_TABLE.length() - 1);

    /**
     * Determines whether a character is whitespace according to the latest Unicode standard, as
     * illustrated
     * <a href="http://unicode.org/cldr/utility/list-unicodeset.jsp?a=%5Cp%7Bwhitespace%7D">here</a>.
     * This is not the same definition used by other Java APIs. (See a
     * <a href="http://spreadsheets.google.com/pub?key=pd8dAQyHbdewRsnE5x5GzKQ">comparison of several
     * definitions of "whitespace"</a>.)
     *
     * <p><b>Note:</b> as the Unicode definition evolves, we will modify this constant to keep it up
     * to date.
     */
    public static final CharMatcher WHITESPACE = new NamedFastMatcher("WHITESPACE") {

        @Override
        public boolean matches(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        void setBits(BitSet table) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };
}
