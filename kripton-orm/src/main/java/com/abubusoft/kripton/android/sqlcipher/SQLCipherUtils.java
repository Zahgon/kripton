/*
 * Copyright (c) 2012-2017 CommonsWare, LLC
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
package com.abubusoft.kripton.android.sqlcipher;

import android.content.Context;
import android.text.Editable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

public class SQLCipherUtils {

    /**
     * The detected state of the database, based on whether we can open it
     * without a passphrase.
     */
    public enum State {

        DOES_NOT_EXIST, UNENCRYPTED, ENCRYPTED
    }

    public static State getDatabaseState(Context ctxt, String dbName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static State getDatabaseState(File dbPath) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encrypt(Context ctxt, String dbName, Editable editor) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encrypt(Context ctxt, String dbName, char[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encrypt(Context ctxt, String dbName, byte[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encrypt(Context ctxt, File originalFile, char[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void encrypt(Context ctxt, File originalFile, byte[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void decrypt(Context ctxt, File originalFile, char[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void decrypt(Context ctxt, File originalFile, byte[] passphrase) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
