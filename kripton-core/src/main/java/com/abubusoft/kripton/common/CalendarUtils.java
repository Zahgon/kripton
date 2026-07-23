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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Class CalendarUtils.
 */
public abstract class CalendarUtils {

    private CalendarUtils() {
    }

    /**
     * The full.
     */
    public final static String FULL = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * The long.
     */
    public final static String LONG = "yyyy-MM-dd HH:mm:ss z";

    /**
     * The normal.
     */
    public final static String NORMAL = "yyyy-MM-dd z";

    /**
     * The short.
     */
    public final static String SHORT = "yyyy-MM-dd";

    /**
     * The time zone.
     */
    public final static String TIME_ZONE = "GMT";

    public static Calendar read(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String write(Calendar value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String getPattern(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Class ThreadLocalDateFormatter.
     */
    public static class ThreadLocalDateFormatter {

        /**
         * The Constant FORMATTERS.
         */
        private static final ThreadLocal<Map<String, DateFormat>> FORMATTERS = new ThreadLocal<Map<String, DateFormat>>() {

            protected Map<String, DateFormat> initialValue() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        };

        /**
         * Gets the formatter.
         *
         * @param pattern the pattern
         * @return the formatter
         */
        static private final DateFormat getFormatter(final String pattern) {
            Map<String, DateFormat> formatterMap = FORMATTERS.get();
            DateFormat df = formatterMap.get(pattern);
            if (null == df) {
                df = new SimpleDateFormat(pattern);
                TimeZone timeZoneGMT = TimeZone.getTimeZone(CalendarUtils.TIME_ZONE);
                df.setTimeZone(timeZoneGMT);
                formatterMap.put(pattern, df);
            }
            return df;
        }

        static public Date parse(final String strDate, final String pattern) throws ParseException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        static public String format(final Date theDate, final String pattern) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
