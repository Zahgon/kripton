/**
 */
package com.abubusoft.kripton.processor.bind;

import java.io.IOException;
import javax.annotation.processing.Filer;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

/**
 * The Class JavaWriterHelper.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class JavaWriterHelper {

    public static void writeJava2File(Filer filer, String packageName, TypeSpec typeSpec) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
