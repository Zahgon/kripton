/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abubusoft.kripton.android.sqlite;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import com.abubusoft.kripton.common.Triple;
import android.content.ContentValues;
import androidx.sqlite.db.SupportSQLiteStatement;

/**
 * The Class KriptonContentValues.
 */
public final class KriptonContentValues {

    /**
     * The Enum ParamType.
     */
    public enum ParamType {

        /**
         * The boolean.
         */
        BOOLEAN,
        /**
         * The byte array.
         */
        BYTE_ARRAY,
        /**
         * The double.
         */
        DOUBLE,
        /**
         * The float.
         */
        FLOAT,
        /**
         * The integer.
         */
        INTEGER,
        /**
         * The long.
         */
        LONG,
        /**
         * The short.
         */
        SHORT,
        /**
         * The string.
         */
        STRING,
        /**
         * The null.
         */
        NULL,
        /**
         * The byte.
         */
        BYTE,
        /**
         * The character.
         */
        CHARACTER
    }

    /**
     * The values.
     */
    private ContentValues values;

    /**
     * The value type.
     */
    private ArrayList<ParamType> valueType;

    /**
     * The names.
     */
    private ArrayList<String> names;

    /**
     * The args.
     */
    private ArrayList<Object> args;

    /**
     * The where args.
     */
    private ArrayList<String> whereArgs;

    public ArrayList<String> whereArgs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates an empty set of values using the default initial size.
     */
    public KriptonContentValues() {
        // Choosing a default size of 8 based on analysis of typical
        // consumption by applications.
        // values = new ContentValues();
        valueType = new ArrayList<>(8);
        names = new ArrayList<>(8);
        args = new ArrayList<>(4);
        whereArgs = new ArrayList<>(4);
    }

    public void put(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Byte value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Short value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, BigDecimal value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, BigInteger value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Character value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Integer value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Integer value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Long value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Float value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, Boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(String key, byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void putNull(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(Boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void put(byte[] value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void putNull() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // ---
    public void addWhereArgs(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void bind(SupportSQLiteStatement statement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clearBindIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The triple.
     */
    protected Triple<String, Object, ParamType> triple = new Triple<>();

    /**
     * The compiled statement.
     */
    private SupportSQLiteStatement compiledStatement;

    /**
     * The compiled statement bind index.
     */
    private int compiledStatementBindIndex;

    public Triple<String, Object, ParamType> get(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String keyList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String keyValueList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ContentValues values() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String[] whereArgsAsArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clear(ContentValues values) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<String> keys() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clear(SupportSQLiteStatement compiledStatement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
