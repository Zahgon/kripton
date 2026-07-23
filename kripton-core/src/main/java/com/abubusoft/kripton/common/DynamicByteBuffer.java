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

import java.io.ByteArrayInputStream;

/**
 * The Class DynamicByteBuffer.
 */
public class DynamicByteBuffer {

    public static DynamicByteBuffer create(byte[] buffer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static DynamicByteBuffer create() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static DynamicByteBuffer create(int capacity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static DynamicByteBuffer createExact(final int capacity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The buffer.
     */
    protected byte[] buffer;

    /**
     * The capacity.
     */
    protected int capacity = 16;

    /**
     * The length.
     */
    protected int length = 0;

    /**
     * Instantiates a new dynamic byte buffer.
     */
    protected DynamicByteBuffer() {
        init();
    }

    /**
     * Instantiates a new dynamic byte buffer.
     *
     * @param capacity the capacity
     */
    protected DynamicByteBuffer(int capacity) {
        this.capacity = capacity;
        init();
    }

    public DynamicByteBuffer add(byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(byte[] array) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(final byte[] array, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(byte[] array, final int offset, final int length) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(char value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer add(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer addByte(int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addUnsignedByte(short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public DynamicByteBuffer addUnsignedInt(long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addUnsignedShort(int value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Do write double array.
     *
     * @param values the values
     * @param byteSize the byte size
     */
    private void doWriteDoubleArray(double[] values, int byteSize) {
        if (!(byteSize + length < capacity)) {
            buffer = DynamicByteBufferHelper.grow(buffer, buffer.length * 2 + byteSize);
        }
        for (int index = 0; index < values.length; index++) {
            this.add(values[index]);
        }
    }

    /**
     * Do write float array.
     *
     * @param values the values
     * @param byteSize the byte size
     */
    private void doWriteFloatArray(float[] values, int byteSize) {
        if (!(byteSize + length < capacity)) {
            buffer = DynamicByteBufferHelper.grow(buffer, buffer.length * 2 + byteSize);
        }
        for (int index = 0; index < values.length; index++) {
            this.add(values[index]);
        }
    }

    /**
     * Do write int array.
     *
     * @param values the values
     * @param byteSize the byte size
     */
    private void doWriteIntArray(int[] values, int byteSize) {
        if (!(byteSize + length < capacity)) {
            buffer = DynamicByteBufferHelper.grow(buffer, buffer.length * 2 + byteSize);
        }
        for (int index = 0; index < values.length; index++) {
            this.add(values[index]);
        }
    }

    /**
     * Do write long array.
     *
     * @param values the values
     * @param byteSize the byte size
     */
    private void doWriteLongArray(long[] values, int byteSize) {
        if (!(byteSize + length < capacity)) {
            buffer = DynamicByteBufferHelper.grow(buffer, buffer.length * 2 + byteSize);
        }
        for (int index = 0; index < values.length; index++) {
            this.add(values[index]);
        }
    }

    /**
     * Do write short array.
     *
     * @param values the values
     * @param byteSize the byte size
     */
    private void doWriteShortArray(short[] values, int byteSize) {
        if (!(byteSize + length < capacity)) {
            buffer = DynamicByteBufferHelper.grow(buffer, buffer.length * 2 + byteSize);
        }
        for (int index = 0; index < values.length; index++) {
            this.add(values[index]);
        }
    }

    /**
     * Inits the.
     */
    private void init() {
        buffer = new byte[capacity];
    }

    public ByteArrayInputStream input() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int len() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] readAndReset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] readForRecycle() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] slc(int startIndex, int endIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public byte[] toBytes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void write(byte[] b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void write(byte[] b, int off, int len) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void write(int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeBoolean(boolean v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeByte(byte v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeChar(char v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeDouble(double v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeFloat(float v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeInt(int v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeByteArray(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeDoubleArray(double[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeFloatArray(float[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeIntArray(int[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeLongArray(long[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeShortArray(short[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLargeString(String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeLong(long v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumByteArray(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumDoubleArray(double[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumFloatArray(float[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumIntArray(int[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumLongArray(long[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumShortArray(short[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeMediumString(String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeShort(short v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallByteArray(byte[] bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallDoubleArray(double[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallFloatArray(float[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallIntArray(int[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallLongArray(long[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallShortArray(short[] values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeSmallString(String s) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeUnsignedByte(short v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeUnsignedInt(long v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void writeUnsignedShort(int v) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
