/**
 * ****************************************************************************
 *  Copyright 2015, 2017 Francesco Benincasa (info@abubusoft.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.abubusoft.kripton.android.sqlite.commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import com.abubusoft.kripton.android.Logger;
import android.content.Context;

/**
 * The Class IOUtils.
 */
public class IOUtils {

    /**
     * The listener interface for receiving onReadLine events.
     * The class that is interested in processing a onReadLine
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnReadLineListener</code> method. When
     * the onReadLine event occurs, that object's appropriate
     * method is invoked.
     */
    public interface OnReadLineListener {

        /**
         * On text line.
         *
         * @param line the line
         */
        void onTextLine(String line);
    }

    public static String readTextFile(Context context, int resId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String readTextFile(String fileName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String readText(InputStream inputStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static List<String> readTextLines(InputStream openRawResource) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void readTextLines(InputStream openRawResource, OnReadLineListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
