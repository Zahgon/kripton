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
package com.abubusoft.kripton.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.android.ColumnAffinityType;
import com.abubusoft.kripton.android.ColumnType;
import com.abubusoft.kripton.android.annotation.BindContentProvider;
import com.abubusoft.kripton.android.annotation.BindContentProviderEntry;
import com.abubusoft.kripton.android.annotation.BindContentProviderPath;
import com.abubusoft.kripton.android.annotation.BindDao;
import com.abubusoft.kripton.android.annotation.BindDaoMany2Many;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.android.annotation.BindDataSourceOptions;
import com.abubusoft.kripton.android.annotation.BindGeneratedDao;
import com.abubusoft.kripton.android.annotation.BindSqlAdapter;
import com.abubusoft.kripton.android.annotation.BindSqlChildSelect;
import com.abubusoft.kripton.android.annotation.BindSqlColumn;
import com.abubusoft.kripton.android.annotation.BindSqlDelete;
import com.abubusoft.kripton.android.annotation.BindSqlInsert;
import com.abubusoft.kripton.android.annotation.BindSqlRelation;
import com.abubusoft.kripton.android.annotation.BindSqlSelect;
import com.abubusoft.kripton.android.annotation.BindSqlType;
import com.abubusoft.kripton.android.annotation.BindSqlUpdate;
import com.abubusoft.kripton.android.sqlite.ForeignKeyAction;
import com.abubusoft.kripton.android.sqlite.NoPopulator;
import com.abubusoft.kripton.annotation.BindDisabled;
import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.processor.bind.BindEntityBuilder;
import com.abubusoft.kripton.processor.bind.model.BindEntity;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.bind.model.many2many.M2MEntity;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.Touple;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.AnnotationFilter;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.AnnotationFoundListener;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.MethodFoundListener;
import com.abubusoft.kripton.processor.core.reflect.PropertyFactory;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility.PropertyCreatedListener;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.element.GeneratedTypeElement;
import com.abubusoft.kripton.processor.exceptions.DaoDefinitionWithoutAnnotatedMethodException;
import com.abubusoft.kripton.processor.exceptions.InvalidBeanTypeException;
import com.abubusoft.kripton.processor.exceptions.InvalidDefinition;
import com.abubusoft.kripton.processor.exceptions.InvalidKindForAnnotationException;
import com.abubusoft.kripton.processor.exceptions.InvalidNameException;
import com.abubusoft.kripton.processor.exceptions.NoDaoElementFound;
import com.abubusoft.kripton.processor.exceptions.PropertyNotFoundException;
import com.abubusoft.kripton.processor.exceptions.SQLPrimaryKeyNotFoundException;
import com.abubusoft.kripton.processor.exceptions.SQLPrimaryKeyNotValidTypeException;
import com.abubusoft.kripton.processor.exceptions.TooManySQLPrimaryKeyFoundException;
import com.abubusoft.kripton.processor.sqlite.BindAsyncTaskBuilder;
import com.abubusoft.kripton.processor.sqlite.BindContentProviderBuilder;
import com.abubusoft.kripton.processor.sqlite.BindCursorBuilder;
import com.abubusoft.kripton.processor.sqlite.BindDaoBuilder;
import com.abubusoft.kripton.processor.sqlite.BindDataSourceBuilder;
import com.abubusoft.kripton.processor.sqlite.BindTableGenerator;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility.SelectType;
import com.abubusoft.kripton.processor.sqlite.SqlAnalyzer;
import com.abubusoft.kripton.processor.sqlite.SqlBuilderHelper;
import com.abubusoft.kripton.processor.sqlite.SqlKeywordsHelper;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLDeclarationType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlBaseListener;
import com.abubusoft.kripton.processor.sqlite.grammars.jsql.JqlParser.Where_stmt_clausesContext;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLRelationType;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelContentProvider;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;
import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

/**
 * The Class BindDataSourceSubProcessor.
 */
public class BindDataSourceSubProcessor extends BaseProcessor {

    private static BindDataSourceSubProcessor instance;

    public static BindDataSourceSubProcessor getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<String> getSupportedOptions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The property annotation filter.
     */
    private final AnnotationFilter propertyAnnotationFilter = AnnotationFilter.builder().add(BindDisabled.class).add(BindSqlColumn.class).add(BindSqlAdapter.class).add(BindSqlRelation.class).build();

    /**
     * The global dao elements.
     */
    public final Map<String, TypeElement> globalDaoElements = new HashMap<String, TypeElement>();

    /**
     * The data sets.
     */
    public Set<TypeElement> dataSets;

    /**
     * The schemas.
     */
    public LinkedHashSet<SQLiteDatabaseSchema> schemas = new LinkedHashSet<>();

    /**
     * The global dao generated.
     */
    public Set<String> globalDaoGenerated = new HashSet<String>();

    /**
     * The generated daos.
     */
    public Set<GeneratedTypeElement> generatedDaos;

    /**
     * The generated entities.
     */
    public Set<GeneratedTypeElement> generatedEntities;

    /*
     * (non-Javadoc)
     *
     * @see com.abubusoft.kripton.processor.BaseProcessor# getSupportedAnnotationClasses()
     */
    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * (non-Javadoc)
     *
     * @see com.abubusoft.kripton.processor.BaseProcessor#clear()
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * for each method with custom bean, check if:
     * <ul>
     * <li>bean is not included in schema</li>
     * <li>method must have declared with explicit JQL</li>
     * </ul>
     *
     * @param currentSchema
     */
    private void analyzeCustomBeanForSelect(SQLiteDatabaseSchema schema) {
        for (SQLiteDaoDefinition dao : schema.getCollection()) {
            for (SQLiteModelMethod method : dao.getCollection()) {
                if (method.hasCustomProjection()) {
                    SQLiteEntity entity = method.getEntity();
                    AssertKripton.assertTrueOrInvalidMethodSignException(schema.getEntity(entity.getName()) == null, method, "'%s' must be read with its DAO", entity.getSimpleName());
                    AssertKripton.assertTrueOrInvalidMethodSignException(method.jql.declarationType == JQLDeclarationType.JQL_EXPLICIT, method, "select with custom projection must be declared with explicit JQL");
                }
            }
        }
    }

    /**
     * Analyze relation between entities
     *
     * @param currentSchema
     * @throws @throws IOException
     */
    private void analyzeRelations(SQLiteDatabaseSchema schema) {
        // resolve child entity
        for (SQLiteEntity entity : schema.getEntities()) {
            // if there is not relations, go on
            if (entity.relations.size() == 0)
                continue;
            for (Touple<SQLProperty, String, SQLiteEntity, SQLRelationType> item : entity.relations) {
                TypeName typeName = TypeUtility.typeName(item.value0.getElement());
                if (TypeUtility.isSet(typeName) || TypeUtility.isList(typeName)) {
                    // ASSERT: list, set of type
                    AssertKripton.assertTrueOfInvalidDefinition(((ParameterizedTypeName) typeName).typeArguments.size() == 1, item.value0, String.format("invalid type for @%s annotated element", BindSqlRelation.class.getSimpleName()));
                    typeName = ((ParameterizedTypeName) typeName).typeArguments.get(0);
                    SQLiteEntity referredEntity = schema.getEntity(typeName.toString());
                    checkForeignKeyContraint(entity, item, referredEntity);
                    item.value2 = referredEntity;
                    item.value3 = SQLRelationType.ONE_2_MANY;
                } else {
                    SQLiteEntity referredEntity = schema.getEntity(typeName.toString());
                    checkForeignKeyContraint(entity, item, referredEntity);
                    item.value2 = referredEntity;
                    item.value3 = SQLRelationType.ONE_2_ONE;
                }
                // get dao definition for entity
                SQLiteDaoDefinition parentDaoDefinition = schema.findDaoDefinitionForEntity(entity);
                for (final SQLiteModelMethod method : parentDaoDefinition.getCollection()) {
                    if (method.hasChildrenSelects()) {
                        // Pair<relation name, method name>
                        for (Triple<String, String, SQLiteModelMethod> childrenSelect : method.childrenSelects) {
                            final Touple<SQLProperty, String, SQLiteEntity, SQLRelationType> relation = entity.findRelationByParentProperty(childrenSelect.value0);
                            AssertKripton.assertTrueOrInvalidMethodSignException(relation != null, method, " property '%s#%s' does not exits (referred by annotation @%s(%s='%s', %s='%s'))", entity.getSimpleName(), childrenSelect.value0, BindSqlChildSelect.class.getSimpleName(), AnnotationAttributeType.FIELD.getValue(), childrenSelect.value0, AnnotationAttributeType.METHOD.getValue(), childrenSelect.value1);
                            final SQLiteDaoDefinition childDaoDefinition = schema.findDaoDefinitionForEntity(relation.value2);
                            AssertKripton.assertTrueOrInvalidMethodSignException(childDaoDefinition != null, method, " dao for entity '%s', referred by @%s annotation, does not exists", relation.value2, BindSqlChildSelect.class.getSimpleName());
                            final SQLiteModelMethod subMethod = childDaoDefinition.get(childrenSelect.value1);
                            AssertKripton.assertTrueOrInvalidMethodSignException(subMethod != null, method, " method '%s#%s', referred by @%s annotation, does not exists", childDaoDefinition.getElement().getSimpleName().toString(), childrenSelect.value1, BindSqlChildSelect.class.getSimpleName());
                            AssertKripton.assertTrueOrInvalidMethodSignException(subMethod.getParameters().size() == 1, method, " method '%s#%s', referred by annotation @%s(%s='%s', %s='%s'), can have only one parameter", childDaoDefinition.getTypeName(), subMethod.getName(), BindSqlChildSelect.class.getSimpleName(), AnnotationAttributeType.FIELD.getValue(), childrenSelect.value0, AnnotationAttributeType.METHOD.getValue(), childrenSelect.value1);
                            // set sub method to invoke
                            childrenSelect.value2 = subMethod;
                            final String defaultConditionToTest1 = relation.value1 + "=" + SqlAnalyzer.PARAM_PREFIX + subMethod.findParameterAliasByName(subMethod.getParameters().get(0).value0) + SqlAnalyzer.PARAM_SUFFIX;
                            final String defaultConditionToTest2 = SqlAnalyzer.PARAM_PREFIX + subMethod.findParameterAliasByName(subMethod.getParameters().get(0).value0) + SqlAnalyzer.PARAM_SUFFIX + "=" + relation.value1;
                            final Set<String> conditionToTest = new HashSet<String>();
                            String[] prefix = { "${", ":{", ":" };
                            String[] suffix = { "}", "}", "" };
                            for (int i = 0; i < prefix.length; i++) {
                                final String conditionToTest1 = relation.value1 + "=" + prefix[i] + subMethod.findParameterAliasByName(subMethod.getParameters().get(0).value0) + suffix[i];
                                final String conditionToTest2 = prefix[i] + subMethod.findParameterAliasByName(subMethod.getParameters().get(0).value0) + suffix[i] + "=" + relation.value1;
                                conditionToTest.add(conditionToTest1);
                                conditionToTest.add(conditionToTest2);
                            }
                            JQLChecker.getInstance().analyze(subMethod, subMethod.jql, new JqlBaseListener() {

                                @Override
                                public void enterWhere_stmt_clauses(Where_stmt_clausesContext ctx) {
                                    throw new UnsupportedOperationException("STUB: not implemented");
                                }
                            });
                            AssertKripton.assertTrueOrInvalidMethodSignException(TypeUtility.isEquals(subMethod.getParameters().get(0).value1, entity.getPrimaryKey().getPropertyType().getTypeName()), method, " method '%s#%s' referred by annotation @%s(%s='%s', %s='%s') has invalid parameter type ", childDaoDefinition.getTypeName(), subMethod.getName(), BindSqlChildSelect.class.getSimpleName(), AnnotationAttributeType.FIELD.getValue(), childrenSelect.value0, AnnotationAttributeType.METHOD.getValue(), childrenSelect.value1);
                            TypeName parentFieldTypeName = relation.value0.getPropertyType().getTypeName();
                            AssertKripton.assertTrueOrInvalidMethodSignException((parentFieldTypeName.equals(subMethod.getReturnClass()) || (TypeUtility.isList(parentFieldTypeName) == TypeUtility.isList(subMethod.getReturnClass())) && (TypeUtility.isSet(parentFieldTypeName) == TypeUtility.isSet(subMethod.getReturnClass()))), method, "field '%s#%s' is incompatible with '%s#%s' referred by @%s annotation ", TypeUtility.typeName(relation.value0.getParent().getElement()).toString(), relation.value0.getName(), childDaoDefinition.getTypeName(), childrenSelect.value1, BindSqlChildSelect.class.getSimpleName());
                            // check existence of method
                            AssertKripton.assertTrueOrInvalidMethodSignException(subMethod != null, method, "an nonexistent method '%s#%s' is referred by @%s annotation ", childDaoDefinition.getTypeName(), relation.value1, BindSqlChildSelect.class.getSimpleName());
                            // check signature
                            AssertKripton.assertTrueOrInvalidMethodSignException(subMethod.getParameters().size() == 1, method, " method '%s#%s' referred by @%s annotation can have one parameter binded to %s property", childDaoDefinition.getTypeName(), relation.value1, BindSqlChildSelect.class.getSimpleName(), relation.value1);
                            // check parameter type
                            AssertKripton.assertTrueOrInvalidMethodSignException(TypeUtility.isTypeIncludedIn(subMethod.getParameters().get(0).value1, Long.TYPE, Long.class, String.class), method, " method '%s#%s' referred by @%s annotation can have only one parameter of type String, Long or long", childDaoDefinition.getTypeName(), relation.value1, BindSqlChildSelect.class.getSimpleName(), relation.value1);
                            SelectType selectSubMethodResultType = SelectBuilderUtility.detectSelectType(subMethod);
                            // specified dao can only return bean of a type
                            // TypeName returnTypeName =
                            // method.getReturnClass();
                            switch(relation.value3) {
                                case ONE_2_MANY:
                                    // we can have a set or a list
                                    AssertKripton.assertTrueOrInvalidMethodSignException(selectSubMethodResultType == SelectType.LIST_BEAN, method, " method '%s#%s' referred by @%s annotation does not return an acceptable value from '%s' property", childDaoDefinition.getTypeName(), relation.value1, BindSqlChildSelect.class.getSimpleName(), relation.value1);
                                    break;
                                case ONE_2_ONE:
                                    // we can receive only a bean
                                    AssertKripton.assertTrueOrInvalidMethodSignException(selectSubMethodResultType == SelectType.BEAN, method, " method '%s#%s' referred by @%s annotation does not return an acceptable value from '%s' property", childDaoDefinition.getTypeName(), relation.value1, BindSqlChildSelect.class.getSimpleName(), relation.value1);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void checkForeignKeyContraint(SQLiteEntity entity, Touple<SQLProperty, String, SQLiteEntity, SQLRelationType> item, SQLiteEntity referredEntity) {
        // ASSERT: check valid type
        AssertKripton.assertTrueOfInvalidDefinition(referredEntity != null, item.value0, String.format("invalid type for @%s annotated element", BindSqlRelation.class.getSimpleName()));
        // ASSERT: check if child entity has field
        List<SQLProperty> foreignKeyPropertyList = referredEntity.getForeignKeysToEntity(entity, item.value1);
        AssertKripton.assertTrueOfInvalidDefinition(foreignKeyPropertyList.size() == 1, item.value0, String.format("@%s#%s need to specify a valid foreign key to entity '%s'", BindSqlRelation.class.getSimpleName(), AnnotationAttributeType.FOREIGN_KEY.getValue(), referredEntity.getName()));
        // set field name in case it is no specified
        if (!StringUtils.hasText(item.value1)) {
            item.value1 = foreignKeyPropertyList.get(0).getName();
        }
        SQLProperty foreignKey = referredEntity.get(item.value1);
        AssertKripton.assertTrueOfInvalidDefinition(TypeUtility.isEquals(entity.getPrimaryKey().getPropertyType().getTypeName(), foreignKey.getPropertyType().getTypeName()), item.value0, String.format("%s#%s is a foreign key to %s#%s: they have to be same type", referredEntity.getName(), foreignKey.getName(), entity.getName(), entity.getPrimaryKey().getName()));
    }

    public boolean processSecondRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean analyzeSecondRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean analyzeRound(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Analyze foreign key.
     *
     * @param schema the schema
     */
    private void analyzeForeignKey(SQLiteDatabaseSchema schema) {
        for (SQLiteEntity entity : schema.getEntities()) {
            for (SQLProperty property : entity.getCollection()) {
                if (property.isForeignKey()) {
                    SQLiteEntity reference = schema.getEntity(property.foreignParentClassName);
                    AssertKripton.asserTrueOrUnspecifiedBeanException(reference != null, schema, entity, property.foreignParentClassName);
                    if (!entity.equals(reference)) {
                        entity.referedEntities.add(reference);
                    }
                }
            }
        }
        for (SQLiteDaoDefinition dao : schema.getCollection()) {
            if (dao.getElement().getAnnotation(BindDaoMany2Many.class) != null) {
                ClassName entity1 = TypeUtility.className(AnnotationUtility.extractAsClassName(dao.getElement(), BindDaoMany2Many.class, AnnotationAttributeType.ENTITY_1));
                ClassName entity2 = TypeUtility.className(AnnotationUtility.extractAsClassName(dao.getElement(), BindDaoMany2Many.class, AnnotationAttributeType.ENTITY_2));
                // only if dao has an entity
                if (dao.getEntity() != null) {
                    // check foreign key to entity1 and entity2
                    checkForeignKeyForM2M(schema, dao.getEntity(), entity1);
                    checkForeignKeyForM2M(schema, dao.getEntity(), entity2);
                }
            }
        }
    }

    /**
     * Checks if is generated entity.
     *
     * @param fullName the full name
     * @return true, if is generated entity
     */
    private boolean isGeneratedEntity(String fullName) {
        for (GeneratedTypeElement item : this.generatedEntities) {
            if (item.getQualifiedName().equals(fullName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Create bean's definition for each dao definition contained in dataSource
     * </p>
     * .
     *
     * @param schema     the schema
     * @param dataSource the data source
     * @param daoName    the dao name
     * @return true, if successful
     */
    private boolean createSQLEntityFromDao(final SQLiteDatabaseSchema schema, TypeElement dataSource, String daoName) {
        TypeElement daoElement = globalDaoElements.get(daoName);
        if (daoElement == null) {
            String msg = String.format("Data source %s references a DAO %s without @%s annotation", dataSource.toString(), daoName, BindDao.class.getSimpleName());
            throw (new InvalidNameException(msg));
        }
        String entityClassName = AnnotationUtility.extractAsClassName(daoElement, BindDao.class, AnnotationAttributeType.VALUE);
        // if there is no entity, exit now
        if (!StringUtils.hasText(entityClassName)) {
            return false;
        }
        final SQLiteEntity currentEntity = createSQLEntity(schema, daoElement, entityClassName, true);
        if (!schema.contains(currentEntity.getName())) {
            schema.addEntity(currentEntity);
        }
        return true;
    }

    public SQLiteEntity createSQLEntity(final SQLiteDatabaseSchema schema, TypeElement daoElement, String beanClassName, boolean primaryKeyCheck) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check foreign key for M 2 M.
     *
     * @param currentSchema the current schema
     * @param currentEntity the current entity
     * @param m2mEntity     the m 2 m entity
     */
    private void checkForeignKeyForM2M(SQLiteDatabaseSchema currentSchema, final SQLiteEntity currentEntity, ClassName m2mEntity) {
        // check for m2m relationship
        if (m2mEntity != null) {
            SQLiteEntity temp = currentSchema.getEntity(m2mEntity.toString());
            AssertKripton.asserTrueOrForeignKeyNotFound(currentEntity.referedEntities.contains(temp), currentEntity, m2mEntity);
        }
    }

    protected void generateClasses(RoundEnvironment roundEnv) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void generateClassesSecondRound(RoundEnvironment roundEnv) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void createSQLDaoDefinition(SQLiteDatabaseSchema schema, final Map<String, TypeElement> globalBeanElements, final Map<String, TypeElement> globalDaoElements, String daoItem) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Fill methods.
     *
     * @param currentDaoDefinition the current dao definition
     * @param daoElement           the dao element
     */
    private void fillMethods(final SQLiteDaoDefinition currentDaoDefinition, Element daoElement) {
        final One<Boolean> methodWithAnnotation = new One<Boolean>(false);
        // create method for dao
        SqlBuilderHelper.forEachMethods((TypeElement) daoElement, new MethodFoundListener() {

            @Override
            public void onMethod(ExecutableElement element) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            private void addWithCheckMethod(SQLiteDaoDefinition currentDaoDefinition, SQLiteModelMethod newMethod) {
                SQLiteModelMethod oldMethod = currentDaoDefinition.findPropertyByName(newMethod.getName());
                // ASSERT: same name and same number
                if (oldMethod != null && oldMethod.getParameters().size() == newMethod.getParameters().size()) {
                    boolean sameParameters = true;
                    for (int i = 0; i < oldMethod.getParameters().size(); i++) {
                        if (!oldMethod.getParameters().get(i).value1.equals(newMethod.getParameters().get(i).value1)) {
                            sameParameters = false;
                            break;
                        }
                    }
                    AssertKripton.failWithInvalidMethodSignException(sameParameters, newMethod, "conflict between generated method and declared method.");
                }
                // add method
                currentDaoDefinition.add(newMethod);
            }
        });
    }

    protected SQLiteDatabaseSchema createDataSource(Element databaseSchema) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String generateEntityName(SQLiteDaoDefinition dao, SQLiteEntity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static String generateEntityQualifiedName(SQLiteDaoDefinition dao, SQLiteEntity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
