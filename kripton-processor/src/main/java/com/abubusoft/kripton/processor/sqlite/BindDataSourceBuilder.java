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

import static com.abubusoft.kripton.processor.core.reflect.TypeUtility.className;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import androidx.annotation.NonNull;
import com.abubusoft.kripton.android.KriptonLibrary;
import com.abubusoft.kripton.android.Logger;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.android.sqlite.AbstractDataSource;
import com.abubusoft.kripton.android.sqlite.AbstractDataSource.AbstractExecutable;
import com.abubusoft.kripton.android.sqlite.AbstractDataSource.OnErrorListener;
import com.abubusoft.kripton.android.sqlite.DataSourceOptions;
import com.abubusoft.kripton.android.sqlite.SQLContext;
import com.abubusoft.kripton.android.sqlite.SQLContextInSessionImpl;
import com.abubusoft.kripton.android.sqlite.SQLiteEvent;
import com.abubusoft.kripton.android.sqlite.SQLiteTable;
import com.abubusoft.kripton.android.sqlite.SQLiteUpdateTask;
import com.abubusoft.kripton.android.sqlite.SQLiteUpdateTaskHelper;
import com.abubusoft.kripton.android.sqlite.TransactionResult;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.KriptonOptions;
import com.abubusoft.kripton.processor.KriptonProcessorVersion;
import com.abubusoft.kripton.processor.bind.JavaWriterHelper;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.element.GeneratedTypeElement;
import com.abubusoft.kripton.processor.exceptions.CircularRelationshipException;
import com.abubusoft.kripton.processor.sqlite.core.EntitySorter;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.utils.AnnotationProcessorUtilis;
import com.squareup.javapoet.*;
import com.squareup.javapoet.FieldSpec.Builder;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.subjects.PublishSubject;

/**
 * Generates database class.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindDataSourceBuilder extends AbstractBuilder {

    /**
     * The Constant DATA_SOURCE_SINGLE_THREAD_NAME.
     */
    private static final String DATA_SOURCE_SINGLE_THREAD_NAME = "DataSourceSingleThread";

    /**
     * The Constant PREFIX.
     */
    public static final String PREFIX = "Bind";

    /**
     * The Constant SUFFIX.
     */
    public static final String SUFFIX = "DataSource";

    /**
     * Instantiates a new bind data source builder.
     *
     * @param elementUtils the element utils
     * @param filer        the filer
     * @param model        the model
     */
    public BindDataSourceBuilder(Elements elementUtils, Filer filer, SQLiteDatabaseSchema model) {
        super(elementUtils, filer, model);
    }

    public static void generate(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate schema.
     *
     * @param schema the schema
     * @throws FileNotFoundException the file not found exception
     * @throws IOException           Signals that an I/O exception has occurred.
     */
    private static void generateSchema(SQLiteDatabaseSchema schema) throws FileNotFoundException, IOException {
        // when we run in JUNIT of Kripton, we don't have to generate schemas
        // if (BindDataSourceSubProcessor.JUNIT_TEST_MODE)
        // return;
        if (!schema.generateSchema)
            return;
        // and now, write schema.create.v and schema.drop.v
        String schemaCreation = defineFileName(schema);
        String schemaLocation = KriptonOptions.getSchemaLocation();
        File schemaCreatePath = new File(schemaLocation).getAbsoluteFile();
        File schemaCreateFile = new File(schemaLocation, schemaCreation).getAbsoluteFile();
        schemaCreatePath.mkdirs();
        AnnotationProcessorUtilis.infoOnGeneratedFile(BindDataSource.class, schemaCreateFile);
        FileOutputStream fos = new FileOutputStream(schemaCreateFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write("------------------------------------------------------------------------------------\n");
        bw.write("--\n");
        bw.write("-- Filename: " + schemaCreation + "\n");
        if (!BindDataSourceSubProcessor.JUNIT_TEST_MODE) {
            bw.write("-- Generated by: kripton annotation processor " + KriptonProcessorVersion.VERSION + "\n");
            bw.write(String.format("-- Date: %s", (new Date()).toString()) + "\n");
        }
        bw.write(String.format("--\n"));
        bw.write("------------------------------------------------------------------------------------\n");
        bw.newLine();
        for (String sql : schema.sqlForCreate) {
            bw.write(sql);
            bw.newLine();
        }
        bw.close();
    }

    static String defineFileName(SQLiteDatabaseSchema model) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName generateDataSourceName(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void buildDataSource(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema, String daoFactoryName) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateTransactions(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate constructor.
     *
     * @param schema the schema
     */
    private void generateConstructor(SQLiteDatabaseSchema schema) {
        // constructor
        MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder().addParameter(DataSourceOptions.class, "options").addModifiers(Modifier.PROTECTED);
        if (schema.generateLog) {
            methodBuilder.addStatement("super($S, $L, options)", schema.fileName, schema.version);
        } else {
            methodBuilder.addStatement("super($S, $L, $T.builder().createFrom(options).log(false).build())", schema.fileName, schema.version, DataSourceOptions.class);
        }
        classBuilder.addMethod(methodBuilder.build());
    }

    public static void generateDaoUids(TypeSpec.Builder classBuilder, SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generate data source single thread.
     *
     * @param schema         the schema
     * @param dataSourceName the data source name
     */
    private void generateDataSourceSingleThread(SQLiteDatabaseSchema schema, String dataSourceName) {
        // class DataSourceSingleThread
        String daoFactoryName = BindDaoFactoryBuilder.generateDaoFactoryName(schema);
        TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(DATA_SOURCE_SINGLE_THREAD_NAME).addSuperinterface(TypeUtility.typeName(daoFactoryName));
        // _context field
        clazzBuilder.addField(FieldSpec.builder(SQLContextInSessionImpl.class, "_context", Modifier.PRIVATE).build());
        // constructor
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder();
        constructorBuilder.addStatement("_context=new $T($L.this)", SQLContextInSessionImpl.class, dataSourceName);
        // all dao
        for (SQLiteDaoDefinition dao : schema.getCollection()) {
            TypeName daoImplName = BindDaoBuilder.daoTypeName(dao);
            // dao with external connections
            {
                String daoFieldName = extractDaoFieldNameForInternalDataSource(dao);
                MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("get" + dao.getName()).addModifiers(Modifier.PUBLIC).addJavadoc("\nretrieve dao $L\n", dao.getName()).returns(daoImplName);
                methodBuilder.beginControlFlow("if ($L==null)", daoFieldName);
                methodBuilder.addStatement("$L=new $T(this)", daoFieldName, daoImplName);
                methodBuilder.endControlFlow();
                methodBuilder.addStatement("return $L", daoFieldName);
                clazzBuilder.addMethod(methodBuilder.build());
                clazzBuilder.addField(FieldSpec.builder(daoImplName, daoFieldName, Modifier.PROTECTED).build());
            }
        }
        // constructor
        clazzBuilder.addMethod(constructorBuilder.build());
        // public SQLContext context()
        {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("getContext").addModifiers(Modifier.PUBLIC).returns(SQLContext.class);
            methodBuilder.addAnnotation(Override.class);
            methodBuilder.addStatement("return _context");
            clazzBuilder.addMethod(methodBuilder.build());
        }
        // onSessionOpened
        {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onSessionOpened").addModifiers(Modifier.PROTECTED).returns(Void.TYPE);
            if (schema.hasLiveData()) {
                methodBuilder.addComment("support for live data");
                methodBuilder.addStatement("_context.onSessionOpened()");
            }
            clazzBuilder.addMethod(methodBuilder.build());
        }
        // onSessionClear
        {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onSessionClear").addModifiers(Modifier.PROTECTED).returns(Void.TYPE);
            if (schema.hasLiveData()) {
                methodBuilder.addComment("support for live data");
                methodBuilder.addStatement("_context.onSessionOpened()");
            }
            clazzBuilder.addMethod(methodBuilder.build());
        }
        // onSessionClosed
        {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onSessionClosed").addModifiers(Modifier.PROTECTED).returns(Void.TYPE);
            if (schema.hasLiveData()) {
                methodBuilder.addComment("support for live data");
                methodBuilder.addStatement("$T daosWithEvents=_context.onSessionClosed()", ParameterizedTypeName.get(Set.class, Integer.class));
                for (SQLiteDaoDefinition dao : schema.getCollection()) {
                    String daoFieldName = extractDaoFieldNameForInternalDataSource(dao);
                    if (dao.hasLiveData()) {
                        methodBuilder.beginControlFlow("if ($L!=null && daosWithEvents.contains($L))", daoFieldName, dao.daoUidName);
                        methodBuilder.addStatement("$L.invalidateLiveData()", daoFieldName);
                        methodBuilder.endControlFlow();
                    } else {
                        methodBuilder.addComment("$S has no live data", daoFieldName);
                    }
                }
            }
            clazzBuilder.addMethod(methodBuilder.build());
        }
        // build method bindToThread
        {
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bindToThread").addModifiers(Modifier.PUBLIC).returns(TypeUtility.typeName(DATA_SOURCE_SINGLE_THREAD_NAME));
            // methodBuilder.addStatement("_context.bindToThread()");
            methodBuilder.addStatement("return this");
            clazzBuilder.addMethod(methodBuilder.build());
            // build single thread daoFactory used in transaction
            classBuilder.addField(FieldSpec.builder(TypeUtility.typeName(DATA_SOURCE_SINGLE_THREAD_NAME), "_daoFactorySingleThread", Modifier.PROTECTED).addJavadoc("Used only in transactions (that can be executed one for time\n").initializer("new DataSourceSingleThread()").build());
        }
        // build transactions
        {
            SchemaUtility.generateTransaction(clazzBuilder, schema, false);
        }
        classBuilder.addType(clazzBuilder.build());
    }

    /**
     * Extract dao field name for internal data source.
     *
     * @param dao the dao
     * @return the string
     */
    private String extractDaoFieldNameForInternalDataSource(SQLiteDaoDefinition dao) {
        return "_" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, dao.getName());
    }

    /**
     * Generate inner code for instance and build methods. Inspired by <a href=
     * "https://www.journaldev.com/171/thread-safety-in-java-singleton-classes-with-example-code">this
     * link<</a>
     *
     * @param schema     the schema
     * @param schemaName the schema name
     * @param instance   the instance
     */
    private void generateInstanceOrBuild(SQLiteDatabaseSchema schema, String schemaName, boolean instance) {
        // instance
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(instance ? "getInstance" : "build").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(className(schemaName));
        if (!instance) {
            methodBuilder.addParameter(DataSourceOptions.class, "options");
            methodBuilder.addJavadoc("<p>Build instance. This method can be used only one time, on the application start.</p>\n");
        } else {
            methodBuilder.addJavadoc("<p>Retrieve instance.</p>\n");
        }
        if (!instance) {
            //ASSERT: we are in build
            methodBuilder.beginControlFlow("if (options.forceBuild && instance!=null)");
            methodBuilder.addStatement("$T.info(\"Datasource $L is forced to be (re)builded\")", Logger.class, schemaName);
            methodBuilder.addStatement("instance=null");
            methodBuilder.endControlFlow();
        }
        methodBuilder.addStatement("$T result=instance", className(schemaName));
        methodBuilder.beginControlFlow("if (result==null)");
        methodBuilder.beginControlFlow("synchronized(mutex)");
        methodBuilder.addStatement("result=instance");
        methodBuilder.beginControlFlow("if (result==null)");
        if (instance) {
            methodBuilder.addCode("$T options=$T.builder()", DataSourceOptions.class, DataSourceOptions.class);
            if (schema.configOpenHelperFactoryClazz != null) {
                methodBuilder.addCode("\n\t.openHelperFactory(new $T())", TypeUtility.className(schema.configOpenHelperFactoryClazz));
            }
            if (schema.configDatabaseErrorHandlerClazz != null) {
                methodBuilder.addCode("\n\t.errorHandler(new $T())", TypeUtility.className(schema.configDatabaseErrorHandlerClazz));
            }
            if (schema.configDatabaseLifecycleHandlerClazz != null) {
                methodBuilder.addCode("\n\t.databaseLifecycleHandler(new $T())", TypeUtility.className(schema.configDatabaseLifecycleHandlerClazz));
            }
            if (schema.configPopulatorClazz != null) {
                methodBuilder.addCode("\n\t.populator(new $T())", TypeUtility.className(schema.configPopulatorClazz));
            }
            methodBuilder.addCode("\n\t.inMemory($L)", schema.configInMemory);
            methodBuilder.addCode("\n\t.log($L)", schema.configLogEnabled);
            methodBuilder.addCode("\n\t.neverClose($L)", schema.configNeverClose);
            if (schema.configUpdateTasks != null && schema.configUpdateTasks.size() > 0) {
                for (Pair<Integer, String> task : schema.configUpdateTasks) {
                    methodBuilder.addCode("\n\t.addUpdateTask($L, new $T())", task.value0, TypeUtility.className(task.value1));
                }
            }
            methodBuilder.addCode("\n\t.build();\n", DataSourceOptions.class, DataSourceOptions.class);
        }
        methodBuilder.addStatement("instance=result=new $L(options)", schemaName);
        generatePopulate(schema, methodBuilder, instance);
        if (!instance) {
            methodBuilder.nextControlFlow("else");
            methodBuilder.addStatement("throw new $T($S)", KriptonRuntimeException.class, "Datasource " + schemaName + " is already builded");
        }
        methodBuilder.endControlFlow();
        methodBuilder.endControlFlow();
        if (!instance) {
            methodBuilder.nextControlFlow("else");
            methodBuilder.addStatement("throw new $T($S)", KriptonRuntimeException.class, "Datasource " + schemaName + " is already builded");
        }
        methodBuilder.endControlFlow();
        if (!instance) {
            //ASSERT: we are in build
            methodBuilder.addStatement("$T.info(\"Datasource $L is created\")", Logger.class, schemaName);
        }
        methodBuilder.addCode("return result;\n");
        classBuilder.addMethod(methodBuilder.build());
    }

    /**
     * Generate populate.
     *
     * @param schema        the schema
     * @param methodBuilder the method builder
     * @param instance      the instance
     */
    private void generatePopulate(SQLiteDatabaseSchema schema, MethodSpec.Builder methodBuilder, boolean instance) {
        methodBuilder.beginControlFlow("try");
        methodBuilder.addStatement("instance.openWritableDatabase()");
        methodBuilder.addStatement("instance.close()");
        if ((instance && schema.configPopulatorClazz != null) || (!instance)) {
            methodBuilder.addComment("force database DDL run");
            methodBuilder.beginControlFlow("if (options.populator!=null && instance.justCreated)");
            methodBuilder.addComment("run populator only a time");
            methodBuilder.addStatement("instance.justCreated=false");
            // populator manage its connection
            methodBuilder.addComment("run populator");
            methodBuilder.addStatement("options.populator.execute()");
            // methodBuilder.nextControlFlow("finally");
            // methodBuilder.addStatement("instance.close()");
            // methodBuilder.endControlFlow();
            methodBuilder.endControlFlow();
        }
        methodBuilder.nextControlFlow("catch($T e)", Throwable.class);
        methodBuilder.addStatement("$T.error(e.getMessage())", Logger.class);
        methodBuilder.addStatement("e.printStackTrace()");
        methodBuilder.endControlFlow();
    }

    /**
     * Generate open.
     *
     * @param schemaName the schema name
     */
    private void generateOpen(String schemaName) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("open").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(className(schemaName));
        methodBuilder.addJavadoc("Retrieve data source instance and open it.\n");
        methodBuilder.addJavadoc("@return opened dataSource instance.\n");
        methodBuilder.addStatement("$L instance=getInstance()", schemaName);
        methodBuilder.addStatement("instance.openWritableDatabase()");
        methodBuilder.addCode("return instance;\n");
        classBuilder.addMethod(methodBuilder.build());
    }

    /**
     * Generate open read only.
     *
     * @param schemaName the schema name
     */
    private void generateOpenReadOnly(String schemaName) {
        // instance
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("openReadOnly").addModifiers(Modifier.PUBLIC, Modifier.STATIC).returns(className(schemaName));
        methodBuilder.addJavadoc("Retrieve data source instance and open it in read only mode.\n");
        methodBuilder.addJavadoc("@return opened dataSource instance.\n");
        methodBuilder.addStatement("$L instance=getInstance()", schemaName);
        methodBuilder.addStatement("instance.openReadOnlyDatabase()");
        methodBuilder.addCode("return instance;\n");
        classBuilder.addMethod(methodBuilder.build());
    }

    /**
     * Generate on create.
     *
     * @param schema          the schema
     * @param orderedEntities the ordered entities
     * @return true, if successful
     */
    private boolean generateOnCreate(SQLiteDatabaseSchema schema, List<SQLiteEntity> orderedEntities) {
        boolean useForeignKey = false;
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onCreate").addAnnotation(Override.class).addModifiers(Modifier.PROTECTED);
        methodBuilder.addParameter(KriptonDynamicClassManager.getInstance().getDatabaseClazz(), "database");
        methodBuilder.addJavadoc("onCreate\n");
        methodBuilder.addCode("// generate tables\n");
        if (schema.isLogEnabled()) {
            // generate log section - BEGIN
            methodBuilder.addComment("log section create BEGIN");
            methodBuilder.beginControlFlow("if (this.logEnabled)");
            methodBuilder.beginControlFlow("if (options.inMemory)");
            methodBuilder.addStatement("$T.info(\"Create database in memory\")", Logger.class);
            methodBuilder.nextControlFlow("else");
            methodBuilder.addStatement("$T.info(\"Create database '%s' version %s\",this.name, this.version)", Logger.class);
            methodBuilder.endControlFlow();
            // generate log section - END
            methodBuilder.endControlFlow();
            methodBuilder.addComment("log section create END");
        }
        for (SQLiteEntity item : orderedEntities) {
            if (schema.isLogEnabled()) {
                // generate log section - BEGIN
                methodBuilder.addComment("log section create BEGIN");
                methodBuilder.beginControlFlow("if (this.logEnabled)");
                methodBuilder.addStatement("$T.info(\"DDL: %s\",$T.CREATE_TABLE_SQL)", Logger.class, BindTableGenerator.tableClassName(null, item));
                // generate log section - END
                methodBuilder.endControlFlow();
                methodBuilder.addComment("log section create END");
            }
            methodBuilder.addStatement("database.execSQL($T.CREATE_TABLE_SQL)", BindTableGenerator.tableClassName(null, item));
            if (item.referedEntities.size() > 0) {
                useForeignKey = true;
            }
        }
        // use generated entities too
        // if we have generated entities, we use foreign key for sure
        if (schema.generatedEntities.size() > 0)
            useForeignKey = true;
        for (GeneratedTypeElement item : schema.generatedEntities) {
            if (schema.isLogEnabled()) {
                // generate log section - BEGIN
                methodBuilder.addComment("log section BEGIN");
                methodBuilder.beginControlFlow("if (this.logEnabled)");
                methodBuilder.addStatement("$T.info(\"DDL: %s\",$T.CREATE_TABLE_SQL)", Logger.class, TypeUtility.className(BindTableGenerator.getTableClassName(item.getQualifiedName())));
                // generate log section - END
                methodBuilder.endControlFlow();
                methodBuilder.addComment("log section END");
            }
            methodBuilder.addStatement("database.execSQL($T.CREATE_TABLE_SQL)", TypeUtility.className(BindTableGenerator.getTableClassName(item.getQualifiedName())));
        }
        methodBuilder.beginControlFlow("if (options.databaseLifecycleHandler != null)");
        methodBuilder.addStatement("options.databaseLifecycleHandler.onCreate(database)");
        methodBuilder.endControlFlow();
        methodBuilder.addStatement("justCreated=true");
        classBuilder.addMethod(methodBuilder.build());
        return useForeignKey;
    }

    /**
     * Generate on upgrade.
     *
     * @param schema          the schema
     * @param orderedEntities the ordered entities
     */
    private void generateOnUpgrade(SQLiteDatabaseSchema schema, List<SQLiteEntity> orderedEntities) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("onUpgrade").addAnnotation(Override.class).addModifiers(Modifier.PROTECTED);
        methodBuilder.addParameter(KriptonDynamicClassManager.getInstance().getDatabaseClazz(), "database");
        methodBuilder.addParameter(Integer.TYPE, "previousVersion");
        methodBuilder.addParameter(Integer.TYPE, "currentVersion");
        methodBuilder.addJavadoc("onUpgrade\n");
        Collections.reverse(orderedEntities);
        if (schema.isLogEnabled()) {
            // generate log section - BEGIN
            methodBuilder.addComment("log section BEGIN");
            methodBuilder.beginControlFlow("if (this.logEnabled)");
            methodBuilder.addStatement("$T.info(\"Update database '%s' from version %s to version %s\",this.name, previousVersion, currentVersion)", Logger.class);
            // generate log section - END
            methodBuilder.endControlFlow();
            methodBuilder.addComment("log section END");
        }
        methodBuilder.addComment("if we have a list of update task, try to execute them");
        methodBuilder.beginControlFlow("if (options.updateTasks != null)");
        methodBuilder.addStatement("$T<$T> tasks = buildTaskList(previousVersion, currentVersion)", List.class, SQLiteUpdateTask.class);
        methodBuilder.beginControlFlow("for ($T task : tasks)", SQLiteUpdateTask.class);
        // generate log section - BEGIN
        methodBuilder.addComment("log section BEGIN");
        methodBuilder.beginControlFlow("if (this.logEnabled)");
        methodBuilder.addStatement("$T.info(\"Begin update database from version %s to %s\", previousVersion, previousVersion+1)", Logger.class);
        // generate log section - END
        methodBuilder.endControlFlow();
        methodBuilder.addComment("log section END");
        methodBuilder.addStatement("task.execute(database, previousVersion, previousVersion+1)");
        // generate log section - BEGIN
        methodBuilder.addComment("log section BEGIN");
        methodBuilder.beginControlFlow("if (this.logEnabled)");
        methodBuilder.addStatement("$T.info(\"End update database from version %s to %s\", previousVersion, previousVersion+1)", Logger.class);
        // generate log section - END
        methodBuilder.endControlFlow();
        methodBuilder.addComment("log section END");
        methodBuilder.addStatement("previousVersion++");
        methodBuilder.endControlFlow();
        methodBuilder.nextControlFlow("else");
        methodBuilder.addComment("drop all tables");
        methodBuilder.addStatement("$T.dropTablesAndIndices(database)", SQLiteUpdateTaskHelper.class);
        // reorder entities
        Collections.reverse(orderedEntities);
        methodBuilder.addCode("\n");
        methodBuilder.addCode("// generate tables\n");
        for (SQLiteEntity item : orderedEntities) {
            if (schema.isLogEnabled()) {
                // generate log section - BEGIN
                methodBuilder.addComment("log section BEGIN");
                methodBuilder.beginControlFlow("if (this.logEnabled)");
                methodBuilder.addCode("$T.info(\"DDL: %s\",$T.CREATE_TABLE_SQL);\n", Logger.class, BindTableGenerator.tableClassName(null, item));
                // generate log section - END
                methodBuilder.endControlFlow();
                methodBuilder.addComment("log section END");
            }
            methodBuilder.addCode("database.execSQL($T.CREATE_TABLE_SQL);\n", BindTableGenerator.tableClassName(null, item));
        }
        // use generated entities too
        for (GeneratedTypeElement item : schema.generatedEntities) {
            if (schema.isLogEnabled()) {
                // generate log section - BEGIN
                methodBuilder.addComment("log section BEGIN");
                methodBuilder.beginControlFlow("if (this.logEnabled)");
                methodBuilder.addStatement("$T.info(\"DDL: %s\",$T.CREATE_TABLE_SQL)", Logger.class, TypeUtility.className(BindTableGenerator.getTableClassName(item.getQualifiedName())));
                // generate log section - END
                methodBuilder.endControlFlow();
                methodBuilder.addComment("log section END");
            }
            methodBuilder.addStatement("database.execSQL($T.CREATE_TABLE_SQL)", TypeUtility.className(BindTableGenerator.getTableClassName(item.getQualifiedName())));
        }
        methodBuilder.endControlFlow();
        methodBuilder.beginControlFlow("if (options.databaseLifecycleHandler != null)");
        methodBuilder.addStatement("options.databaseLifecycleHandler.onUpdate(database, previousVersion, currentVersion, true)");
        methodBuilder.endControlFlow();
        classBuilder.addMethod(methodBuilder.build());
    }

    /**
     * Generate on configure.
     *
     * @param useForeignKey the use foreign key
     */
    private void generateHasForeignKeysNeeded(boolean useForeignKey) {
        // onConfigure
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("hasForeignKeys").addAnnotation(Override.class).returns(Boolean.TYPE).addModifiers(Modifier.PUBLIC);
        //methodBuilder.addParameter(KriptonDynamicClassManager.getInstance().getDatabaseClazz(), "database");
        methodBuilder.addJavadoc("Returns <code>true</code> if database needs foreign keys.\n");
        methodBuilder.addStatement("return $L", useForeignKey);
        /*if (useForeignKey) {
			
		}*/
        //methodBuilder.addCode("// configure database\n");
        /*if (useForeignKey) {
			methodBuilder.addStatement("database.setForeignKeyConstraintsEnabled(true)");
		}

		methodBuilder.beginControlFlow("if (options.databaseLifecycleHandler != null)");
		methodBuilder.addStatement("options.databaseLifecycleHandler.onConfigure(database)");
		methodBuilder.endControlFlow();*/
        classBuilder.addMethod(methodBuilder.build());
    }

    public static List<SQLiteEntity> orderEntitiesList(SQLiteDatabaseSchema schema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generatExecuteTransactionRx(ClassName dataSourceName, String daoFactory, RxType rxType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generatExecuteBatchRx(ClassName dataSourceName, String daoFactory, RxType rxType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateRx(ClassName dataSourceName, String daoFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Enum RxInterfaceType.
     */
    private enum RxInterfaceType {

        /**
         * The batch.
         */
        BATCH,
        /**
         * The transaction.
         */
        TRANSACTION
    }

    /**
     * The Enum RxType.
     */
    private enum RxType {

        /**
         * The observable.
         */
        OBSERVABLE(Observable.class, true),
        /**
         * The single.
         */
        SINGLE(Single.class, false),
        /**
         * The maybe.
         */
        MAYBE(Maybe.class, false),
        /**
         * The flowable.
         */
        FLOWABLE(Flowable.class, true);

        /**
         * Instantiates a new rx type.
         *
         * @param clazz      the clazz
         * @param onComplete the on complete
         */
        private RxType(Class<?> clazz, boolean onComplete) {
            this.clazz = clazz;
            this.onComplete = onComplete;
        }

        /**
         * The clazz.
         */
        public Class<?> clazz;

        /**
         * The on complete.
         */
        public boolean onComplete;
    }

    /**
     * Generate rx interface.
     *
     * @param daoFactory    the dao factory
     * @param interfaceType the interface type
     * @param clazz         the clazz
     */
    private void generateRxInterface(String daoFactory, RxInterfaceType interfaceType, Class<?> clazz) {
        // create interfaces
        {
            ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(clazz), TypeVariableName.get("T"));
            String preExecutorName = clazz.getSimpleName().replace("Emitter", "");
            String postExecutorName = com.abubusoft.kripton.common.CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, interfaceType.toString());
            // @formatter:off
            if (interfaceType == RxInterfaceType.BATCH) {
                classBuilder.addType(TypeSpec.interfaceBuilder(preExecutorName + postExecutorName).addModifiers(Modifier.PUBLIC).addTypeVariable(TypeVariableName.get("T")).addMethod(MethodSpec.methodBuilder("onExecute").addParameter(className(daoFactory), "daoFactory").addParameter(parameterizedTypeName, "emitter").addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).returns(Void.TYPE).build()).// .addModifiers(Modifier.PUBLIC,
                // Modifier.ABSTRACT).returns(TransactionResult.class).build())
                build());
            } else {
                classBuilder.addType(TypeSpec.interfaceBuilder(preExecutorName + postExecutorName).addModifiers(Modifier.PUBLIC).addTypeVariable(TypeVariableName.get("T")).addMethod(MethodSpec.methodBuilder("onExecute").addParameter(className(daoFactory), "daoFactory").addParameter(parameterizedTypeName, "emitter").// .addModifiers(Modifier.PUBLIC,
                // Modifier.ABSTRACT).returns(Void.TYPE).build())
                addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT).returns(TransactionResult.class).build()).build());
            }
            // @formatter:on
        }
    }

    private void generateMethodAsyncBatch(String daoFactory, boolean withErrorListener) {
        // create interface
        String transationExecutorName = "Batch";
        MethodSpec.Builder executeMethod = MethodSpec.methodBuilder("executeBatchAsync").addModifiers(Modifier.PUBLIC).addTypeVariable(TypeVariableName.get("T")).returns(ParameterizedTypeName.get(ClassName.get(Future.class), TypeVariableName.get("T"))).addParameter(ParameterizedTypeName.get(className(transationExecutorName), TypeVariableName.get("T")), "commands", Modifier.FINAL);
        if (withErrorListener) {
            executeMethod.addParameter(Boolean.TYPE, "writeMode", Modifier.FINAL);
        }
        ParameterizedTypeName futureType = ParameterizedTypeName.get(ClassName.get(Callable.class), TypeVariableName.get("T"));
        TypeSpec innerBuilder = TypeSpec.anonymousClassBuilder("").addSuperinterface(futureType).addMethod(MethodSpec.methodBuilder("call").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).returns(TypeVariableName.get("T")).addException(Exception.class).addStatement(withErrorListener ? "return executeBatch(commands, writeMode)" : "return executeBatch(commands, false)").build()).build();
        executeMethod.addStatement("return $T.getExecutorService().submit($L)", KriptonLibrary.class, innerBuilder);
        {
            // generate javadoc
            executeMethod.addJavadoc("<p>Executes a batch command in async mode. This method <strong>is thread safe</strong> to avoid concurrent problems. The " + "drawback is only one transaction at time can be executed. The database will be open in write mode. This method uses default error listener to intercept errors.</p>\n");
            executeMethod.addJavadoc("\n");
            executeMethod.addJavadoc("@param commands\n\tcommands to execute\n");
            if (withErrorListener) {
                executeMethod.addJavadoc("@param writeMode\n\true if you need to writeable connection\n");
            }
            executeMethod.addJavadoc("@return <code>true</code> when transaction successful finished\n");
            classBuilder.addMethod(executeMethod.build());
        }
    }

    private void generateMethodExecuteAsyncTransaction(String daoFactory, boolean withErrorListener) {
        // create interface
        String transationExecutorName = "Transaction";
        MethodSpec.Builder executeMethod = MethodSpec.methodBuilder("executeAsync").addModifiers(Modifier.PUBLIC).returns(ParameterizedTypeName.get(Future.class, Boolean.class)).addParameter(className(transationExecutorName), "transaction", Modifier.FINAL);
        if (withErrorListener) {
            executeMethod.addParameter(AbstractDataSource.OnErrorListener.class, "onErrorListener", Modifier.FINAL);
        }
        ParameterizedTypeName futureType = ParameterizedTypeName.get(Callable.class, Boolean.class);
        TypeSpec innerBuilder = TypeSpec.anonymousClassBuilder("").addSuperinterface(futureType).addMethod(MethodSpec.methodBuilder("call").addModifiers(Modifier.PUBLIC).addAnnotation(Override.class).returns(Boolean.class).addException(Exception.class).addStatement("return execute(transaction, onErrorListener)").build()).build();
        executeMethod.addStatement("return $T.getExecutorService().submit($L)", KriptonLibrary.class, innerBuilder);
        {
            // generate javadoc
            executeMethod.addJavadoc("<p>Executes a transaction in async mode. This method <strong>is thread safe</strong> to avoid concurrent problems. The " + "drawback is only one transaction at time can be executed. The database will be open in write mode. This method uses default error listener to intercept errors.</p>\n");
            executeMethod.addJavadoc("\n");
            executeMethod.addJavadoc("@param transaction\n\ttransaction to execute\n");
            if (withErrorListener) {
                executeMethod.addJavadoc("@param onErrorListener\n\tlistener for errors\n");
            }
            executeMethod.addJavadoc("@return <code>true</code> when transaction successful finished\n");
            classBuilder.addMethod(executeMethod.build());
        }
    }

    public void generateMethodExecuteTransaction(String daoFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateMethodExecuteBatch(String daoFactory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
