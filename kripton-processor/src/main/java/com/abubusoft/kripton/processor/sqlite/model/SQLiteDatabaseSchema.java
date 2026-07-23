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
package com.abubusoft.kripton.processor.sqlite.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.android.annotation.BindDataSourceOptions;
import com.abubusoft.kripton.android.annotation.BindSqlTransaction;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.KriptonOptions;
import com.abubusoft.kripton.processor.ReferredClasses;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.Finder;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.ModelBucket;
import com.abubusoft.kripton.processor.core.TypeAdapterHelper;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.AnnotationFoundListener;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.MethodFoundListener;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.element.GeneratedTypeElement;
import com.abubusoft.kripton.processor.sqlite.FindSqlTypeAdapterVisitor;
import com.abubusoft.kripton.processor.sqlite.FindTasksVisitor;
import com.abubusoft.kripton.processor.sqlite.SqlBuilderHelper;
import com.squareup.javapoet.ClassName;

/**
 * The Class SQLiteDatabaseSchema.
 */
public class SQLiteDatabaseSchema extends ModelBucket<SQLiteDaoDefinition, TypeElement> {

    /**
     * The config populator clazz.
     */
    public final String configPopulatorClazz;

    /**
     * The class name converter.
     */
    public Converter<String, String> classNameConverter = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    /**
     * The column name converter.
     */
    public Converter<String, String> columnNameConverter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);

    /**
     * The entities.
     */
    protected Map<String, SQLiteEntity> entities = new HashMap<String, SQLiteEntity>();

    /**
     * The entities by simple name.
     */
    protected Map<String, SQLiteEntity> entitiesBySimpleName = new HashMap<String, SQLiteEntity>();

    /**
     * The sql for create.
     */
    public List<String> sqlForCreate = new ArrayList<String>();

    /**
     * The sql for drop.
     */
    public List<String> sqlForDrop = new ArrayList<String>();

    /**
     * used to.
     */
    protected long globalCounter = 0;

    public long nextCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The file name.
     */
    public String fileName;

    /**
     * The generated class name.
     */
    public String generatedClassName;

    public String getGeneratedClassName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The version.
     */
    public int version;

    /**
     * The generate log.
     */
    public boolean generateLog;

    /**
     * The generate async task.
     */
    public boolean generateAsyncTask;

    /**
     * The generate cursor.
     */
    public boolean generateCursor;

    /**
     * The generate schema.
     */
    public boolean generateSchema;

    /**
     * The generated entities.
     */
    public LinkedHashSet<GeneratedTypeElement> generatedEntities;

    public List<ExecutableElement> transactions = new ArrayList<ExecutableElement>();

    /**
     * if <code>true</code>, content provider is generated.
     */
    public boolean generateContentProvider;

    /**
     * The content provider.
     */
    public SQLiteModelContentProvider contentProvider;

    /**
     * The property by simple name.
     */
    private Map<String, Set<SQLProperty>> propertyBySimpleName = new HashMap<>();

    /**
     * The dao name set.
     */
    private List<String> daoNameSet;

    /**
     * The generate rx.
     */
    public boolean generateRx;

    /**
     * The config update tasks.
     */
    public final ArrayList<Pair<Integer, String>> configUpdateTasks;

    /**
     * The config in memory.
     */
    public final boolean configInMemory;

    /**
     * The config cursor factory clazz.
     */
    public final String configOpenHelperFactoryClazz;

    /**
     * The config log enabled.
     */
    public final boolean configLogEnabled;

    public final boolean configNeverClose;

    /**
     * The config database error handler clazz.
     */
    public final String configDatabaseErrorHandlerClazz;

    /**
     * The config database lifecycle handler clazz.
     */
    public final String configDatabaseLifecycleHandlerClazz;

    /**
     * The global sql type adapter.
     */
    public final Map<String, String> globalSqlTypeAdapter = new HashMap<String, String>();

    /**
     * directory used for generate schema
     */
    public String schemaLocationDirectory;

    public List<String> getDaoNameSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new SQ lite database schema.
     *
     * @param item
     * @param schemaFileName
     * @param schemaVersion
     * @param schema
     * @param log
     * @param asyncTask
     * @param generateCursor
     * @param generateRx
     * @param daoIntoDataSource
     * @param configOpenHelperFactoryClass
     * @param configDatabaseErrorHandlerClass
     * @param configDatabaseLifecycleHandlerClass
     * @param configInMemory
     * @param configLogEnabled
     * @param configPopulatorClass
     * @param neverClose
     */
    public SQLiteDatabaseSchema(TypeElement item, String schemaFileName, int schemaVersion, boolean schema, boolean log, boolean asyncTask, boolean generateCursor, boolean generateRx, List<String> daoIntoDataSource, String configOpenHelperFactoryClass, String configDatabaseErrorHandlerClass, String configDatabaseLifecycleHandlerClass, boolean configInMemory, boolean configLogEnabled, String configPopulatorClass, boolean neverClose) {
        super(item.getSimpleName().toString(), item);
        this.fileName = schemaFileName;
        this.version = schemaVersion;
        this.generateLog = log;
        this.generateAsyncTask = asyncTask;
        this.generateCursor = generateCursor;
        this.generateSchema = schema;
        this.generatedClassName = "Bind" + getName();
        this.generateContentProvider = false;
        this.generateRx = generateRx;
        this.contentProvider = null;
        this.generatedEntities = new LinkedHashSet<>();
        this.daoNameSet = daoIntoDataSource;
        this.schemaLocationDirectory = KriptonOptions.getSchemaLocation();
        this.configNeverClose = neverClose;
        FindTasksVisitor valueVisitor = new FindTasksVisitor();
        FindSqlTypeAdapterVisitor typeAdapterVisitors = new FindSqlTypeAdapterVisitor();
        List<? extends AnnotationMirror> annotationMirrors = item.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
            if (BindDataSourceOptions.class.getName().equals(annotationMirror.getAnnotationType().toString())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    // The 'entry.getKey()' here is the annotation attribute
                    // name.
                    String key = entry.getKey().getSimpleName().toString();
                    entry.getValue().accept(valueVisitor, key);
                }
            } else if (BindDataSource.class.getName().equals(annotationMirror.getAnnotationType().toString())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    entry.getValue().accept(typeAdapterVisitors, key);
                }
            }
        }
        // TODO add supported type and check about types
        {
            List<String> list = typeAdapterVisitors.getAdapters();
            for (String typeAdapter : list) {
                String sourceType = TypeAdapterHelper.detectSourceType(typeAdapter);
                AssertKripton.assertTrueOrInvalidGlobalTypeApdaterException(!globalSqlTypeAdapter.containsKey(sourceType), this, typeAdapter, globalSqlTypeAdapter.get(sourceType));
                globalSqlTypeAdapter.put(sourceType, typeAdapter);
            }
        }
        this.configLogEnabled = configLogEnabled;
        this.configInMemory = configInMemory;
        this.configUpdateTasks = valueVisitor.getTasks();
        this.configOpenHelperFactoryClazz = fillClazz(configOpenHelperFactoryClass, ReferredClasses.DEFAULT_OPEN_HELPER_FACTORY_CLASS_NAME);
        this.configDatabaseErrorHandlerClazz = fillClazz(configDatabaseErrorHandlerClass, ReferredClasses.NO_DATABASE_ERROR_HANDLER_CLASS_NAME);
        this.configDatabaseLifecycleHandlerClazz = fillClazz(configDatabaseLifecycleHandlerClass, ReferredClasses.NO_DATABASE_LIFECYCLE_HANDLER_CLASS_NAME);
        this.configPopulatorClazz = fillClazz(configPopulatorClass, ReferredClasses.NO_POPULATOR_CLASS_NAME);
        // methods
        // create method for dao
        SqlBuilderHelper.forEachMethods((TypeElement) element, new MethodFoundListener() {

            @Override
            public void onMethod(ExecutableElement methodElement) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
    }

    /**
     * Fill clazz.
     *
     * @param configClazz the config clazz
     * @param clazz       the clazz
     * @return the string
     */
    private String fillClazz(String configClazz, String clazz) {
        if (!clazz.equals(configClazz)) {
            return configClazz;
        } else {
            return null;
        }
    }

    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addEntity(SQLiteEntity value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * property in different class, but same name, must have same column name.
     *
     * @param listEntity the list entity
     * @param p          the p
     */
    private void checkName(Set<SQLProperty> listEntity, SQLProperty p) {
        for (SQLProperty item : listEntity) {
            AssertKripton.assertTrueOrInvalidPropertyName(item.columnName.equals(p.columnName), item, p);
        }
    }

    public Collection<SQLiteEntity> getEntities() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<SQLiteEntity> getEntitiesAsList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SQLiteEntity getEntity(String entityClassName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Finder<SQLProperty> getEntityBySimpleName(String entityName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<SQLProperty> getPropertyBySimpleName(String propertyName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String findColumnNameByPropertyName(SQLiteModelMethod method, String propertyName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isLogEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contentProviderUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getGeneratedClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasLiveData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SQLiteDaoDefinition findDaoDefinitionForEntity(SQLiteEntity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long currentCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
