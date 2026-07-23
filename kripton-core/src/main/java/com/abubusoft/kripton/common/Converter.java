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

import static com.abubusoft.kripton.common.Preconditions.checkNotNull;
import java.io.Serializable;
import java.util.Iterator;

/**
 * A function from {@code A} to {@code B} with an associated <i>reverse</i>
 * function from {@code B} to {@code A}; used for converting back and forth
 * between <i>different representations of the same information</i>.
 *
 * <h3>Invertibility</h3>
 *
 * <p>
 * The reverse operation <b>may</b> be a strict <i>inverse</i> (meaning that
 * {@code
 * converter.reverse().convert(converter.convert(a)).equals(a)} is always true).
 * However, it is very common (perhaps <i>more</i> common) for round-trip
 * conversion to be <i>lossy</i>. Consider an example round-trip using
 * com.google.common.primitives.Doubles#stringConverter:
 *
 * <ol>
 * <li>{@code stringConverter().convert("1.00")} returns the {@code Double}
 * value {@code 1.0}
 * <li>{@code stringConverter().reverse().convert(1.0)} returns the string
 * {@code "1.0"} -- <i>not</i> the same string ({@code "1.00"}) we started with
 * </ol>
 *
 * <p>
 * Note that it should still be the case that the round-tripped and original
 * objects are <i>similar</i>.
 *
 * <h3>Nullability</h3>
 *
 * <p>
 * A converter always converts {@code null} to {@code null} and non-null
 * references to non-null references. It would not make sense to consider
 * {@code null} and a non-null reference to be "different representations of the
 * same information", since one is distinguishable from <i>missing</i>
 * information and the other is not. The {@link #convert} method handles this
 * null behavior for all converters; implementations of {@link #doForward} and
 * {@link #doBackward} are guaranteed to never be passed {@code null}, and must
 * never return {@code null}.
 *
 * <h3>Common ways to use</h3>
 *
 * <p>
 * Getting a converter:
 *
 * <ul>
 * <li>Use a provided converter implementation, such as Enums#stringConverter,
 * com.google.common.primitives.Ints#stringConverter Ints.stringConverter or the
 * {@linkplain #reverse reverse} views of these.
 * <li>Convert between specific preset values using
 * com.google.common.collect.Maps#asConverter Maps.asConverter. For
 * example, use this to create a "fake" converter for a unit test. It is
 * unnecessary (and confusing) to <i>mock</i> the {@code Converter} type using a
 * mocking framework.
 * <li>Otherwise, extend this class and implement its {@link #doForward} and
 * {@link #doBackward} methods.
 * </ul>
 *
 * <p>
 * Using a converter:
 *
 * <ul>
 * <li>Convert one instance in the "forward" direction using
 * {@code converter.convert(a)}.
 * <li>Convert multiple instances "forward" using
 * {@code converter.convertAll(as)}.
 * <li>Convert in the "backward" direction using
 * {@code converter.reverse().convert(b)} or {@code
 *     converter.reverse().convertAll(bs)}.
 * <li>Use {@code converter} or {@code converter.reverse()} anywhere a
 * {@link Function} is accepted
 * <li><b>Do not</b> call {@link #doForward} or {@link #doBackward} directly;
 * these exist only to be overridden.
 * </ul>
 *
 * @author Mike Ward
 * @author Kurt Alfred Kluever
 * @author Gregory Kick
 * @param <A>
 *            the generic type
 * @param <B>
 *            the generic type
 * @since 16.0
 */
public abstract class Converter<A, B> implements Function<A, B> {

    /**
     * The handle null automatically.
     */
    private final boolean handleNullAutomatically;

    /**
     * The reverse.
     */
    // We lazily cache the reverse view to avoid allocating on every call to
    // reverse().
    private transient Converter<B, A> reverse;

    /**
     * Constructor for use by subclasses.
     */
    protected Converter() {
        this(true);
    }

    /**
     * Constructor used only by {@code LegacyConverter} to suspend automatic
     * null-handling.
     *
     * @param handleNullAutomatically
     *            the handle null automatically
     */
    Converter(boolean handleNullAutomatically) {
        this.handleNullAutomatically = handleNullAutomatically;
    }

    // SPI methods (what subclasses must implement)
    /**
     * Returns a representation of {@code a} as an instance of type {@code B}.
     * If {@code a} cannot be converted, an unchecked exception (such as
     * {@link IllegalArgumentException}) should be thrown.
     *
     * @param a
     *            the instance to convert; will never be null
     * @return the converted instance; <b>must not</b> be null
     */
    protected abstract B doForward(A a);

    /**
     * Returns a representation of {@code b} as an instance of type {@code A}.
     * If {@code b} cannot be converted, an unchecked exception (such as
     * {@link IllegalArgumentException}) should be thrown.
     *
     * @param b
     *            the instance to convert; will never be null
     * @return the converted instance; <b>must not</b> be null
     * @throws UnsupportedOperationException
     *             if backward conversion is not implemented; this should be
     *             very rare. Note that if backward conversion is not only
     *             unimplemented but unimplement<i>able</i> (for example,
     *             consider a {@code Converter<Chicken, ChickenNugget>}), then
     *             this is not logically a {@code Converter} at all, and should
     *             just implement {@link Function}.
     */
    protected abstract A doBackward(B b);

    // API (consumer-side) methods
    public final B convert(A a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    B correctedDoForward(A a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    A correctedDoBackward(B b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Iterable<B> convertAll(final Iterable<? extends A> fromIterable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the reversed view of this converter, which converts
     * {@code this.convert(a)} back to a value roughly equivalent to {@code a}.
     *
     * <p>
     * The returned converter is serializable if {@code this} converter is.
     *
     * @return the converter
     */
    // TODO(kak): Make this method final
    public Converter<B, A> reverse() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class ReverseConverter.
     *
     * @param <A>
     *            the generic type
     * @param <B>
     *            the generic type
     */
    private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable {

        /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = 5073708032168078367L;

        /**
         * The original.
         */
        final Converter<A, B> original;

        /**
         * Instantiates a new reverse converter.
         *
         * @param original
         *            the original
         */
        ReverseConverter(Converter<A, B> original) {
            this.original = original;
        }

        /*
		 * These gymnastics are a little confusing. Basically this class has
		 * neither legacy nor non-legacy behavior; it just needs to let the
		 * behavior of the backing converter shine through. So, we override the
		 * correctedDo* methods, after which the do* methods should never be
		 * reached.
		 */
        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doForward(java.lang.Object)
		 */
        @Override
        protected A doForward(B b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doBackward(java.lang.Object)
		 */
        @Override
        protected B doBackward(A a) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#correctedDoForward(java.lang.
		 * Object)
		 */
        @Override
        A correctedDoForward(B b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#correctedDoBackward(java.lang.
		 * Object)
		 */
        @Override
        B correctedDoBackward(A a) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#reverse()
		 */
        @Override
        public Converter<A, B> reverse() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#equals(java.lang.Object)
		 */
        @Override
        public boolean equals(Object object) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public final <C> Converter<A, C> andThen(Converter<B, C> secondConverter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    <C> Converter<A, C> doAndThen(Converter<B, C> secondConverter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class ConverterComposition.
     *
     * @param <A>
     *            the generic type
     * @param <B>
     *            the generic type
     * @param <C>
     *            the generic type
     */
    private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable {

        /**
         * The first.
         */
        final Converter<A, B> first;

        /**
         * The second.
         */
        final Converter<B, C> second;

        /**
         * Instantiates a new converter composition.
         *
         * @param first
         *            the first
         * @param second
         *            the second
         */
        ConverterComposition(Converter<A, B> first, Converter<B, C> second) {
            this.first = first;
            this.second = second;
        }

        /*
		 * These gymnastics are a little confusing. Basically this class has
		 * neither legacy nor non-legacy behavior; it just needs to let the
		 * behaviors of the backing converters shine through (which might even
		 * differ from each other!). So, we override the correctedDo* methods,
		 * after which the do* methods should never be reached.
		 */
        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doForward(java.lang.Object)
		 */
        @Override
        protected C doForward(A a) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doBackward(java.lang.Object)
		 */
        @Override
        protected A doBackward(C c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#correctedDoForward(java.lang.
		 * Object)
		 */
        @Override
        C correctedDoForward(A a) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#correctedDoBackward(java.lang.
		 * Object)
		 */
        @Override
        A correctedDoBackward(C c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#equals(java.lang.Object)
		 */
        @Override
        public boolean equals(Object object) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = 0L;
    }

    /**
     * Apply.
     *
     * @param a
     *            the a
     * @return the b
     * @deprecated Provided to satisfy the {@code Function} interface; use
     *             {@link #convert} instead.
     */
    @Deprecated
    @Override
    public final B apply(A a) {
        return convert(a);
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    @Override
    public boolean equals(Object object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <A, B> Converter<A, B> from(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class FunctionBasedConverter.
     *
     * @param <A>
     *            the generic type
     * @param <B>
     *            the generic type
     */
    private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {

        /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = 1519401989279200113L;

        /**
         * The forward function.
         */
        private final Function<? super A, ? extends B> forwardFunction;

        /**
         * The backward function.
         */
        private final Function<? super B, ? extends A> backwardFunction;

        /**
         * Instantiates a new function based converter.
         *
         * @param forwardFunction
         *            the forward function
         * @param backwardFunction
         *            the backward function
         */
        private FunctionBasedConverter(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
            this.forwardFunction = checkNotNull(forwardFunction);
            this.backwardFunction = checkNotNull(backwardFunction);
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doForward(java.lang.Object)
		 */
        @Override
        protected B doForward(A a) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doBackward(java.lang.Object)
		 */
        @Override
        protected A doBackward(B b) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#equals(java.lang.Object)
		 */
        @Override
        public boolean equals(Object object) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    // implementation is "fully variant"
    @SuppressWarnings("unchecked")
    public static <T> Converter<T, T> identity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A converter that always converts or reverses an object to itself. Note
     * that T is now a "pass-through type".
     *
     * @param <T>
     *            the generic type
     */
    private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable {

        /**
         * The Constant serialVersionUID.
         */
        private static final long serialVersionUID = -163613376588335881L;

        /**
         * The Constant INSTANCE.
         */
        @SuppressWarnings("rawtypes")
        static final IdentityConverter INSTANCE = new IdentityConverter();

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doForward(java.lang.Object)
		 */
        @Override
        protected T doForward(T t) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.abubusoft.kripton.common.Converter#doBackward(java.lang.Object)
		 */
        @Override
        protected T doBackward(T t) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#reverse()
		 */
        @Override
        public IdentityConverter<T> reverse() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * (non-Javadoc)
		 * 
		 * @see com.abubusoft.kripton.common.Converter#doAndThen(com.abubusoft.
		 * kripton.common.Converter)
		 */
        @Override
        <S> Converter<T, S> doAndThen(Converter<T, S> otherConverter) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /*
		 * We *could* override convertAll() to return its input, but it's a
		 * rather pointless optimization and opened up a weird type-safety
		 * problem.
		 */
        /*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Read resolve.
         *
         * @return the object
         */
        private Object readResolve() {
            return INSTANCE;
        }
    }
}
