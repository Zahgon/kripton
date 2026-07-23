package com.abubusoft.kripton.processor;

import java.io.File;
import javax.annotation.processing.ProcessingEnvironment;
import com.abubusoft.kripton.common.StringUtils;

public abstract class KriptonOptions {

    public static String DEBUG_OPTION_NAME = "kripton.debug";

    public static String SCHEMA_LOCATION_OPTION_NAME = "kripton.schemaLocation";

    //public static String ANDROID_X_OPTION_NAME = "kripton.androidx";
    //public static String ANDROID_X_DB_OPTION_NAME = "kripton.androidx.db";
    public static String LOG_ENABLED_OPTION_NAME = "kripton.log";

    public static String schemaLocationDirectory;

    public static boolean androidX;

    public static String getSchemaLocation() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void init(KriptonProcessor kriptonProcessor, ProcessingEnvironment processingEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static String getOptionOrigin(ProcessingEnvironment processingEnv, String name) {
        {
            String value = processingEnv.getOptions().get(name);
            if (StringUtils.hasText(value)) {
                return " from annotation processor configuration (" + value + ")";
            }
        }
        {
            String value = System.getProperty(name);
            if (StringUtils.hasText(value)) {
                return " from system property (" + value + ")";
            }
        }
        {
            String value = System.getenv(name);
            if (StringUtils.hasText(value)) {
                return " from system environment (" + value + ")";
            }
        }
        return " from default value";
    }

    private static String getOptionValue(ProcessingEnvironment processingEnv, String name) {
        String result = System.getenv(name);
        if (StringUtils.hasText(System.getProperty(name))) {
            result = System.getProperty(name);
        }
        if (StringUtils.hasText(processingEnv.getOptions().get(name))) {
            result = processingEnv.getOptions().get(name);
        }
        return result;
    }
}
