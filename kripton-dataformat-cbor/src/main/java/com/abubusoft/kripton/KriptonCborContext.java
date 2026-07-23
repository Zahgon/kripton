package com.abubusoft.kripton;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;

/**
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class KriptonCborContext extends AbstractJacksonContext {

    @Override
    public BinderType getSupportedFormat() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected JsonFactory createInnerFactory() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
