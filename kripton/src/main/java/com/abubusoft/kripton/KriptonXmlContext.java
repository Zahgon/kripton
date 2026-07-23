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
package com.abubusoft.kripton;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.persistence.XmlWrapperParser;
import com.abubusoft.kripton.persistence.XmlWrapperSerializer;
import com.abubusoft.kripton.xml.XMLSerializer;
import com.fasterxml.jackson.core.JsonEncoding;

/**
 * The Class KriptonXmlContext.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class KriptonXmlContext extends AbstractContext {

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createParser(byte[])
	 */
    public XmlWrapperParser createParser(byte[] data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createParser(java.io.File)
	 */
    public XmlWrapperParser createParser(File file) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createParser(java.io.InputStream)
	 */
    public XmlWrapperParser createParser(InputStream inputStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createParser(java.io.Reader)
	 */
    public XmlWrapperParser createParser(Reader reader) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createParser(java.lang.String)
	 */
    public XmlWrapperParser createParser(String content) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createSerializer(java.io.File)
	 */
    public XmlWrapperSerializer createSerializer(File file) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createSerializer(java.io.File, com.fasterxml.jackson.core.JsonEncoding)
	 */
    public XmlWrapperSerializer createSerializer(File file, JsonEncoding encoding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createSerializer(java.io.OutputStream)
	 */
    public XmlWrapperSerializer createSerializer(OutputStream out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createSerializer(java.io.OutputStream, com.fasterxml.jackson.core.JsonEncoding)
	 */
    public XmlWrapperSerializer createSerializer(OutputStream out, JsonEncoding encoding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.AbstractContext#createSerializer(java.io.Writer)
	 */
    public XmlWrapperSerializer createSerializer(Writer output) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.BinderContext#getSupportedFormat()
	 */
    @Override
    public BinderType getSupportedFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
