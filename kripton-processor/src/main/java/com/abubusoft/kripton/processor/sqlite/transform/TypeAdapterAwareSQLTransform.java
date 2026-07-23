/**
 */
package com.abubusoft.kripton.processor.sqlite.transform;

import static com.abubusoft.kripton.processor.core.reflect.PropertyUtility.getter;
import com.abubusoft.kripton.common.SQLTypeAdapterUtils;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

/**
 * The Class TypeAdapterAwareSQLTransform.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class TypeAdapterAwareSQLTransform extends AbstractSQLTransform {

    /**
     * The write costant.
     */
    protected String WRITE_COSTANT = "$L";

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.AbstractSQLTransform#generateWriteParam2ContentValues(com.squareup.javapoet.MethodSpec.Builder, com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod, java.lang.String, com.squareup.javapoet.TypeName, com.abubusoft.kripton.processor.core.ModelProperty)
	 */
    @Override
    public void generateWriteParam2ContentValues(Builder methodBuilder, SQLiteModelMethod method, String paramName, TypeName paramTypeName, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.SQLTransform#generateWriteParam2WhereCondition(com.squareup.javapoet.MethodSpec.Builder, com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod, java.lang.String, com.squareup.javapoet.TypeName)
	 */
    @Override
    public void generateWriteParam2WhereCondition(Builder methodBuilder, SQLiteModelMethod method, String paramName, TypeName paramTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.SQLTransform#generateWriteProperty2ContentValues(com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, com.abubusoft.kripton.processor.core.ModelProperty)
	 */
    @Override
    public void generateWriteProperty2ContentValues(Builder methodBuilder, String beanName, TypeName beanClass, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.sqlite.transform.AbstractSQLTransform#generateWriteProperty2WhereCondition(com.squareup.javapoet.MethodSpec.Builder, java.lang.String, com.squareup.javapoet.TypeName, com.abubusoft.kripton.processor.core.ModelProperty)
	 */
    @Override
    public void generateWriteProperty2WhereCondition(Builder methodBuilder, String beanName, TypeName beanClass, ModelProperty property) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
