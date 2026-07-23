/**
 * Copyright (c) 2017 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain	a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS,	WITHOUT	WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * Covered in detail in the book _The Busy Coder's Guide to Android Development_
 * https://commonsware.com/Android
 */
package com.abubusoft.kripton.android.sqlcipher;

import com.abubusoft.kripton.android.KriptonLibrary;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;

/**
 * KriptonSQLCipherHelperFactory.Factory implementation, for use with Room and similar
 * libraries, that supports SQLCipher for Android.
 */
public class KriptonSQLCipherHelperFactory implements SupportSQLiteOpenHelper.Factory {

    public static final String POST_KEY_SQL_MIGRATE = "PRAGMA cipher_migrate;";

    public static final String POST_KEY_SQL_V3 = "PRAGMA cipher_compatibility = 3;";

    final private byte[] passphrase;

    final private Options options;

    private static boolean debugMode = false;

    private static final String DEBUG_SHARED_PREFS_NAME = KriptonSQLCipherHelperFactory.class.getName();

    public static void setDebugMode(boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KriptonSQLCipherHelperFactory fromUser(Editable editor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KriptonSQLCipherHelperFactory fromUser(Editable editor, String postKeySql) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static KriptonSQLCipherHelperFactory fromUser(Editable editor, Options options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void rekey(SupportSQLiteDatabase db, char[] passphrase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void rekey(SupportSQLiteDatabase db, Editable editor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * If you are using an EditText to collect the passphrase from the user,
     * call getText() on the EditText, and pass that Editable to the
     * SafeHelperFactory.fromUser() factory method.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     */
    public KriptonSQLCipherHelperFactory(char[] passphrase) {
        this(passphrase, (String) null);
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * If you are using an EditText to collect the passphrase from the user,
     * call getText() on the EditText, and pass that Editable to the
     * SafeHelperFactory.fromUser() factory method.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     * @param postKeySql
     *            optional callback to be called after database has been "keyed"
     *            but before any database access is performed
     */
    public KriptonSQLCipherHelperFactory(char[] passphrase, String postKeySql) {
        this(SQLiteDatabase.getBytes(passphrase), postKeySql);
        if (options.clearPassphrase) {
            clearPassphrase(passphrase);
        }
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * If you are using an EditText to collect the passphrase from the user,
     * call getText() on the EditText, and pass that Editable to the
     * SafeHelperFactory.fromUser() factory method.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     * @param options
     *            options for pre-key, post-key SQL
     */
    public KriptonSQLCipherHelperFactory(char[] passphrase, Options options) {
        this(SQLiteDatabase.getBytes(passphrase), options);
        if (options.clearPassphrase) {
            clearPassphrase(passphrase);
        }
    }

    public KriptonSQLCipherHelperFactory() {
        // no data is stored
        this.passphrase = new byte[0];
        this.options = KriptonSQLCipherHelperFactory.Options.builder().build();
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     */
    public KriptonSQLCipherHelperFactory(byte[] passphrase) {
        this(passphrase, new Options.Builder().build());
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     * @param postKeySql
     *            optional callback to be called after database has been "keyed"
     *            but before any database access is performed
     */
    public KriptonSQLCipherHelperFactory(byte[] passphrase, String postKeySql) {
        this(passphrase, new Options.Builder().setPostKeySql(postKeySql).build());
    }

    /**
     * Standard constructor.
     *
     * Note that the passphrase supplied here will be filled in with zeros after
     * the database is opened. Ideally, you should not create additional copies
     * of this passphrase, particularly as String objects.
     *
     * If you are using an EditText to collect the passphrase from the user,
     * call getText() on the EditText, and pass that Editable to the
     * SafeHelperFactory.fromUser() factory method.
     *
     * @param passphrase
     *            user-supplied passphrase to use for the database
     * @param options
     *            options for pre-key, post-key SQL
     */
    public KriptonSQLCipherHelperFactory(byte[] passphrase, Options options) {
        this.passphrase = passphrase;
        this.options = options;
    }

    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SupportSQLiteOpenHelper create(Context context, String name, SupportSQLiteOpenHelper.Callback callback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void clearPassphrase(char[] passphrase) {
        for (int i = 0; i < passphrase.length; i++) {
            passphrase[i] = (byte) 0;
        }
    }

    /**
     * Class for encapsulating pre- and post-key SQL statements to be executed
     * as part of opening the database. Use the static builder() method to get a
     * Builder for creating one of these.
     */
    public static class Options {

        /**
         * SQL to be executed before keying the database
         */
        public final String preKeySql;

        /**
         * SQL to be executed after keying the database
         */
        public final String postKeySql;

        /**
         * True if we should clear the in-memory cached copy of the passphrase
         * after opening the database; false otherwise. Defaults to true.
         */
        public final boolean clearPassphrase;

        /**
         * A passphrase is required to open the database
         */
        public final boolean requiredPassphrase;

        private Options(String preKeySql, String postKeySql, boolean clearPassphrase, boolean requiredPassphrase) {
            this.preKeySql = preKeySql;
            this.postKeySql = postKeySql;
            this.clearPassphrase = clearPassphrase;
            this.requiredPassphrase = requiredPassphrase;
        }

        public static Builder builder() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * A builder of Options objects. Use the builder() method on Options to
         * create one of these, call various setters to configure it, then call
         * build() to create the Options matching your requested specifications.
         */
        public static class Builder {

            private String preKeySql;

            private String postKeySql;

            private boolean clearPassphrase = false;

            /**
             * A passphrase is required to open the database
             */
            private boolean requiredPassphrase = true;

            private Builder() {
                // use the builder() method on SafeRoomHelper.Options
            }

            public Builder setRequiredPassphrase(boolean requiredPassphrase) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Builder setPreKeySql(String preKeySql) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Builder setPostKeySql(String postKeySql) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Builder setClearPassphrase(boolean value) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            public Options build() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }
    }
}
