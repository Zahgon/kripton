/**
 */
package com.abubusoft.kripton.processor.sqlite.transform;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.setter;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

/**
 * The Class WrappedSQLTransformation.
 */
public abstract class WrappedSQLTransformation extends TypeAdapterAwareSQLTransform {

    /**
     * The read from cursor.
     */
    protected String READ_FROM_CURSOR = null;

    /**
     * The nullable.
     */
    protected boolean nullable;

    /**
     * The default value.
     */
    protected String defaultValue = null;

    /**
     * Instantiates a new wrapped SQL transformation.
     *
     * @param nullable the nullable
     */
    protected WrappedSQLTransformation(boolean nullable) {
        this.nullable = nullable;
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.SQLTransform#generateReadPropertyFromCursor(com.squareup.javapoet.MethodSpec.Builder, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.core.ModelProperty, java.lang.String, java.lang.String)
	 */
    @Override
    public void generateReadPropertyFromCursor(SQLiteEntity tableEntity, Builder methodBuilder, TypeName beanClass, String beanName, ModelProperty property, String cursorName, String indexName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.AbstractSQLTransform#generateReadValueFromCursor(com.squareup.javapoet.MethodSpec.Builder, com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition, com.squareup.javapoet.TypeName, java.lang.String, java.lang.String)
	 */
    @Override
    public void generateReadValueFromCursor(Builder methodBuilder, SQLiteDaoDefinition daoDefinition, TypeName paramTypeName, String cursorName, String indexName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.SQLTransform#generateResetProperty(com.squareup.javapoet.MethodSpec.Builder, com.squareup.javapoet.TypeName, java.lang.String, com.abubusoft.kripton.processor.core.ModelProperty, java.lang.String, java.lang.String)
	 */
    @Override
    public void generateResetProperty(Builder methodBuilder, TypeName beanClass, String beanName, ModelProperty property, String cursorName, String indexName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.AbstractSQLTransform#isTypeAdapterAware()
	 */
    @Override
    public boolean isTypeAdapterAware() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
