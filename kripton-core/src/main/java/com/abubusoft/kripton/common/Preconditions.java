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

/**
 * Static convenience methods that help a method or constructor check whether it was invoked correctly (whether its <i>preconditions</i> have been met). These methods generally
 * accept a {@code boolean} expression which is expected to be {@code true} (or in the case of {@code checkNotNull}, an object reference which is expected to be non-null). When
 * {@code false} (or {@code null}) is passed instead, the {@code Preconditions} method throws an unchecked exception, which helps the calling method communicate to <i>its</i>
 * caller that <i>that</i> caller has made a mistake. Example:
 *
 * <pre>
 *   public static double sqrt(double value) {
 *     Preconditions.checkArgument(value &gt;= 0.0, "negative value: %s", value);
 *     // calculate the square root
 *   }
 *
 *   void exampleBadCaller() {
 *     double d = sqrt(-1.0);
 *   }}
 * </pre>
 *
 * In this example, {@code checkArgument} throws an {@code IllegalArgumentException} to indicate that {@code exampleBadCaller} made an error in <i>its</i> call to {@code sqrt}.
 *
 * <h3>Warning about performance</h3>
 *
 * <p>
 * The goal of this class is to improve readability of code, but in some circumstances this may come at a significant performance cost. Remember that parameter values for message
 * construction must all be computed eagerly, and autoboxing and varargs array creation may happen as well, even when the precondition check then succeeds (as it should almost
 * always do in production). In some circumstances these wasted CPU cycles and allocations can add up to a real problem. Performance-sensitive precondition checks can always be
 * converted to the customary form:
 *
 * <pre>
 * {@code
 *
 *   if (value &lt; 0.0) {
 *     throw new IllegalArgumentException("negative value: " + value);
 *   }}
 * </pre>
 *
 * <h3>Other types of preconditions</h3>
 *
 * <p>
 * Not every type of precondition failure is supported by these methods. Continue to throw standard JDK exceptions such as {@link java.util.NoSuchElementException} or
 * {@link UnsupportedOperationException} in the situations they are intended for.
 *
 * <h3>Non-preconditions</h3>
 *
 * <p>
 * It is of course possible to use the methods of this class to check for invalid conditions which are <i>not the caller's fault</i>. Doing so is <b>not recommended</b> because it
 * is misleading to future readers of the code and of stack traces. See <a href="http://code.google.com/p/guava-libraries/wiki/ConditionalFailuresExplained">Conditional failures
 * explained</a> in the Guava User Guide for more advice.
 *
 * <h3>{@code java.util.Objects.requireNonNull()}</h3>
 *
 * <p>
 * Projects which use {@code com.google.common} should generally avoid the use of {@link java.util.Objects#requireNonNull(Object)}. Instead, use whichever of
 * checkNotNull(Object) or Verify#verifyNotNull(Object) is appropriate to the situation. (The same goes for the message-accepting overloads.)
 *
 * <h3>Only {@code %s} is supported</h3>
 *
 * <p>
 * In {@code Preconditions} error message template strings, only the {@code "%s"} specifier is supported, not the full range of {@link java.util.Formatter} specifiers.
 *
 * <h3>More information</h3>
 *
 * <p>
 * See the Guava User Guide on <a href="http://code.google.com/p/guava-libraries/wiki/PreconditionsExplained">using {@code Preconditions}</a>.
 *
 * @author Kevin Bourrillion
 * @since 2.0 (imported from Google Collections Library)
 */
public final class Preconditions {

    /**
     * Instantiates a new preconditions.
     */
    private Preconditions() {
    }

    public static void checkArgument(boolean expression) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkArgument(boolean expression, Object errorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkState(boolean expression) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkState(boolean expression, Object errorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T> T checkNotNull(T reference) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T> T checkNotNull(T reference, Object errorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageArgs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * All recent hotspots (as of 2009) *really* like to have the natural code
	 * 
	 * if (guardExpression) { throw new BadException(messageExpression); }
	 * 
	 * refactored so that messageExpression is moved to a separate String-returning method.
	 * 
	 * if (guardExpression) { throw new BadException(badMsg(...)); }
	 * 
	 * The alternative natural refactorings into void or Exception-returning methods are much slower. This is a big deal - we're talking factors of 2-8 in microbenchmarks, not just
	 * 10-20%. (This is a hotspot optimizer bug, which should be fixed, but that's a separate, big project).
	 * 
	 * The coding pattern above is heavily used in java.util, e.g. in ArrayList. There is a RangeCheckMicroBenchmark in the JDK that was used to test this.
	 * 
	 * But the methods in this class want to throw different exceptions, depending on the args, so it appears that this pattern is not directly applicable. But we can use the
	 * ridiculous, devious trick of throwing an exception in the middle of the construction of another exception. Hotspot is fine with that.
	 */
    public static int checkElementIndex(int index, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int checkElementIndex(int index, int size, String desc) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Bad element index.
     *
     * @param index the index
     * @param size the size
     * @param desc the desc
     * @return the string
     */
    private static String badElementIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else {
            // index >= size
            return format("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }

    public static int checkPositionIndex(int index, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static int checkPositionIndex(int index, int size, String desc) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Bad position index.
     *
     * @param index the index
     * @param size the size
     * @param desc the desc
     * @return the string
     */
    private static String badPositionIndex(int index, int size, String desc) {
        if (index < 0) {
            return format("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else {
            // index > size
            return format("%s (%s) must not be greater than size (%s)", desc, index, size);
        }
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Bad position indexes.
     *
     * @param start the start
     * @param end the end
     * @param size the size
     * @return the string
     */
    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return format("end index (%s) must not be less than start index (%s)", end, start);
    }

    /**
     * Substitutes each {@code %s} in {@code template} with an argument. These are matched by position: the first {@code %s} gets {@code args[0]}, etc. If there are more arguments
     * than placeholders, the unmatched arguments will be appended to the end of the formatted message in square braces.
     *
     * @param template            a non-null string containing 0 or more {@code %s} placeholders.
     * @param args            the arguments to be substituted into the message template. Arguments are converted to strings using {@link String#valueOf(Object)}. Arguments can be null.
     * @return the string
     */
    // Note that this is somewhat-improperly used from Verify.java as well.
    static String format(String template, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
