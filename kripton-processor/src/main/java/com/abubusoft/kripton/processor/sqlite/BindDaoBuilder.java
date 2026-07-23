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
package com.abubusoft.kripton.processor.sqlite;

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.typeName;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import com.abubusoft.kripton.android.LiveDataHandler;
import com.abubusoft.kripton.android.annotation.BindDao;
import com.abubusoft.kripton.android.annotation.BindDaoMany2Many;
import com.abubusoft.kripton.android.annotation.BindGeneratedDao;
import com.abubusoft.kripton.android.annotation.BindSqlDelete;
import com.abubusoft.kripton.android.annotation.BindSqlInsert;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.annotation.BindSqlUpdate;
import com.abubusoft.kripton.android.sqlite.Dao;
import com.abubusoft.kripton.android.sqlite.SQLiteEvent;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.bind.BindTypeContext;
import com.abubusoft.kripton.processor.bind.JavaWriterHelper;
import com.abubusoft.kripton.processor.bind.transform.BindTransformer;
import com.abubusoft.kripton.processor.core.ManagedPropertyPersistenceHelper;
import com.abubusoft.kripton.processor.core.ManagedPropertyPersistenceHelper.PersistType;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelElementVisitor;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.abubusoft.kripton.processor.utils.AnnotationProcessorUtilis;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import io.reactivex.subjects.PublishSubject;

/**
 * Dao generator.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindDaoBuilder implements SQLiteModelElementVisitor {

    /**
     * The Constant METHOD_NAME_REGISTRY_EVENT.
     */
    public static final String METHOD_NAME_REGISTRY_EVENT = "registryEvent";

    /**
     * The Constant METHOD_NAME_INVALIDATE_LIVE_DATA.
     */
    public static final String METHOD_NAME_INVALIDATE_LIVE_DATA = "invalidateLiveData";

    /**
     * The Constant METHOD_NAME_REGISTRY_LIVE_DATA.
     */
    public static final String METHOD_NAME_REGISTRY_LIVE_DATA = "registryLiveData";

    /**
     * Suffix to add to DAO interface to define DAO implementation typeName.
     */
    public static final String SUFFIX = "Impl";

    /**
     * The element utils.
     */
    protected Elements elementUtils;

    /**
     * The filer.
     */
    protected Filer filer;

    /**
     * The builder.
     */
    private Builder builder;

    /**
     * The current dao definition.
     */
    private SQLiteDaoDefinition currentDaoDefinition;

    /**
     * Instantiates a new bind dao builder.
     *
     * @param elementUtils
     *            the element utils
     * @param filer
     *            the filer
     */
    public BindDaoBuilder(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    public static void generate(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void generateSecondRound(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelElementVisitor#
	 * visit(com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition)
	 */
    @Override
    public void visit(SQLiteDaoDefinition value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String daoName(SQLiteDaoDefinition value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName daoTypeName(SQLiteDaoDefinition value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static TypeName daoInterfaceTypeName(SQLiteDaoDefinition value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelElementVisitor#
	 * visit(com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod)
	 */
    @Override
    public void visit(SQLiteModelMethod value) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
