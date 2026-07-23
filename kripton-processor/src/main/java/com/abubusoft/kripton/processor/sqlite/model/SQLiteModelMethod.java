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

import com.abubusoft.kripton.android.annotation.*;
import com.abubusoft.kripton.android.annotation.BindSqlDynamicWhere.PrependType;
import com.abubusoft.kripton.android.sqlite.NoAdapter;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.escape.StringEscapeUtils;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.KriptonDynamicClassManager;
import com.abubusoft.kripton.processor.core.*;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.exceptions.IncompatibleAnnotationException;
import com.abubusoft.kripton.processor.sqlite.FindSqlChildSelectVisitor;
import com.abubusoft.kripton.processor.sqlite.SelectBuilderUtility;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQL.JQLType;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLBuilder;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker.JQLParameterName;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLContext;
import com.abubusoft.kripton.processor.sqlite.grammars.uri.ContentUriChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.uri.ContentUriChecker.UriPlaceHolderReplacerListener;
import com.abubusoft.kripton.processor.sqlite.grammars.uri.ContentUriPlaceHolder;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import javax.lang.model.element.*;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.util.*;

/**
 * The Class SQLiteModelMethod.
 */
public class SQLiteModelMethod extends ModelMethod implements SQLiteModelElement, JQLContext {

    /**
     * Entity associated to method. For insert/modify/update must be the same of
     * the dao definition For select statement it can be another (it must be
     * annotated with @BindSqlType)
     */
    private SQLiteEntity entity;

    public SQLiteEntity getEntity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The Interface OnFoundDynamicParameter.
     */
    interface OnFoundDynamicParameter {

        /**
         * On found parameter.
         *
         * @param parameterName the parameter name
         */
        void onFoundParameter(String parameterName);
    }

    /**
     * <p>
     * It is the typeName of parameter used to dynamic order by condition
     * (defined at runtime).
     * </p>
     */
    public String dynamicOrderByParameterName;

    /**
     * <p>
     * It is the typeName of parameter used to dynamic where condition (defined
     * at runtime).
     * </p>
     */
    public String dynamicWhereParameterName;

    /**
     * <p>
     * It's the name of the parameter used to define arguments for dynamic where
     * statement. It can be used only on String[] parameter type.
     * </p>
     */
    public String dynamicWhereArgsParameterName;

    /**
     * <p>
     * It is the typeName of parameter used to dynamic page size (defined at
     * runtime).
     * </p>
     */
    public String dynamicPageSizeName;

    /**
     * typeName of the paginated result parameter typeName.
     */
    public String paginatedResultName;

    /**
     * The parameter alias 2 name field.
     */
    protected Map<String, String> parameterAlias2NameField;

    /**
     * The parameter name 2 alias.
     */
    protected Map<String, String> parameterName2Alias;

    /**
     * The parameter name 2 adapter.
     */
    protected Map<String, String> parameterName2Adapter;

    /**
     * The parent.
     */
    private WeakReference<SQLiteDaoDefinition> parent;

    public long nextCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long currentCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The jql.
     */
    public final JQL jql;

    /**
     * The content provider entry path enabled.
     */
    public boolean contentProviderEntryPathEnabled;

    /**
     * it's the path defined in @ContentProviderEntry.path
     */
    public String contentProviderEntryPath;

    /**
     * name of method generated for content provider.
     */
    public String contentProviderMethodName;

    /**
     * The parameter bean name.
     */
    public String parameterBeanName;

    /**
     * The content provider uri variables.
     */
    public List<ContentUriPlaceHolder> contentProviderUriVariables;

    /**
     * <p>
     * It's the uri with place holder replaced with <code>#</code> or
     * <code>*</code>. An example:
     * </p>
     *
     * <pre>
     * content://sqlite.contentprovider.kripton35/persons/#/children
     * </pre>
     */
    public String contentProviderUriTemplate;

    /**
     * The content provider entry path template.
     */
    public String contentProviderEntryPathTemplate;

    /**
     * The dynamic where prepend.
     */
    public PrependType dynamicWherePrepend;

    /**
     * if true, means that this method returns live data.
     */
    private final boolean liveDataEnabled;

    public boolean hasLiveData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * return type of the original method. Defined only for LiveData methods.
     */
    public ParameterizedTypeName liveDataReturnClass;

    /**
     * list of children selects. Valid only for select methods
     */
    public List<Triple<String, String, SQLiteModelMethod>> childrenSelects = new ArrayList<>();

    private boolean pagedLiveData;

    private boolean staticMethod;

    public boolean isStaticMethod() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasBeanAsParameter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasChildrenSelects() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new SQ lite model method.
     *
     * @param parent         the parent
     * @param element        the element
     * @param annotationList the annotation list
     */
    public SQLiteModelMethod(SQLiteDaoDefinition parent, ExecutableElement element, List<ModelAnnotation> annotationList) {
        super(element);
        staticMethod = element.getModifiers().contains(Modifier.STATIC);
        // before proceed convert typevariable in right typename
        parent.resolveTypeVariable(this);
        this.parent = new WeakReference<SQLiteDaoDefinition>(parent);
        // detect type of operation
        if (annotationList != null) {
            this.annotations.addAll(annotationList);
        }
        this.parameterAlias2NameField = new HashMap<>();
        this.parameterName2Alias = new HashMap<>();
        this.parameterName2Adapter = new HashMap<>();
        // element.isV
        // analyze method looking for BindSqlParam
        for (VariableElement p : element.getParameters()) {
            BindSqlParam paramAlias = p.getAnnotation(BindSqlParam.class);
            if (paramAlias != null) {
                // check for name
                if (StringUtils.hasText(paramAlias.value())) {
                    String alias = paramAlias.value();
                    parameterAlias2NameField.put(alias, p.getSimpleName().toString());
                    parameterName2Alias.put(p.getSimpleName().toString(), alias);
                }
                // check for adapter
                String paramAdapter = AnnotationUtility.extractAsClassName(p, BindSqlParam.class, AnnotationAttributeType.ADAPTER);
                if (!NoAdapter.class.getCanonicalName().equals(paramAdapter)) {
                    this.parameterName2Adapter.put(p.getSimpleName().toString(), paramAdapter);
                }
            }
            if (TypeUtility.isEquals(TypeUtility.typeName(p.asType()), parent.getEntityClassName())) {
                this.parameterBeanName = p.getSimpleName().toString();
            }
            BindSqlDynamicWhere paramDynamicWhereName = p.getAnnotation(BindSqlDynamicWhere.class);
            if (paramDynamicWhereName != null) {
                this.dynamicWhereParameterName = p.getSimpleName().toString();
                PrependType prepend = PrependType.valueOf(AnnotationUtility.extractAsEnumerationValue(p, BindSqlDynamicWhere.class, AnnotationAttributeType.PREPEND));
                this.dynamicWherePrepend = prepend;
                // CONSTRAINT: @BindSqlWhere can be used only on String
                // parameter type
                AssertKripton.assertTrueOrInvalidTypeForAnnotationMethodParameterException(TypeUtility.isEquals(TypeUtility.typeName(String.class), TypeUtility.typeName(p.asType())), getParent().getElement(), getElement(), p, BindSqlDynamicWhere.class);
            }
            BindSqlDynamicWhereParams paramDynamicWhereArgsName = p.getAnnotation(BindSqlDynamicWhereParams.class);
            if (paramDynamicWhereArgsName != null) {
                this.dynamicWhereArgsParameterName = p.getSimpleName().toString();
                // only String[] parameter can be marked as dynamicWhereArgs
                // CONSTRAINT: @BindSqlWhereArgs can be used only on
                // String[] parameter type
                AssertKripton.assertTrueOrInvalidTypeForAnnotationMethodParameterException(TypeUtility.isEquals(ArrayTypeName.of(String.class), TypeUtility.typeName(p.asType())), getParent().getElement(), getElement(), p, BindSqlDynamicWhereParams.class);
            }
        }
        // looks for dynamic where conditions
        findStringDynamicStatement(parent, BindSqlDynamicWhere.class, unsupportedSQLForDynamicWhere, new OnFoundDynamicParameter() {

            @Override
            public void onFoundParameter(String parameterName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        // looks for dynamic orderBy conditions
        findStringDynamicStatement(parent, BindSqlDynamicOrderBy.class, unsupportedSQLForDynamicOrderBy, new OnFoundDynamicParameter() {

            @Override
            public void onFoundParameter(String parameterName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        // looks for dynamic pageSize
        findIntDynamicStatement(parent, BindSqlPageSize.class, unsupportedSQLForDynamicOrderBy, new OnFoundDynamicParameter() {

            @Override
            public void onFoundParameter(String parameterName) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        // live data support BEFORE RETURN TYPE
        this.liveDataEnabled = SQLiteModelMethod.isLiveData(this);
        if (liveDataEnabled) {
            ParameterizedTypeName returnParameterizedTypeName = (ParameterizedTypeName) getReturnClass();
            this.liveDataReturnClass = returnParameterizedTypeName;
            setReturnClass(returnParameterizedTypeName.typeArguments.get(0));
            // if true, the live data is paged
            this.pagedLiveData = KriptonDynamicClassManager.getInstance().isPagedLiveData(this.liveDataReturnClass);
        }
        // detect entity, before other things like jql
        if (element.getAnnotation(BindSqlSelect.class) != null) {
            // try to detect if
            TypeName returnType = SelectBuilderUtility.extractReturnType(this);
            if (returnType == null) {
                this.entity = getParent().getEntity();
            } else {
                this.entity = BindDataSourceSubProcessor.getInstance().createSQLEntity(parent.getParent(), parent.getElement(), returnType.toString(), false);
            }
        } else {
            // this is not a select
            this.entity = getParent().getEntity();
        }
        // check if we have jql annotation attribute
        String preparedJql = getJQLDeclared();
        this.jql = JQLBuilder.buildJQL(this, preparedJql);
        // content provider generation
        BindContentProviderEntry annotation = element.getAnnotation(BindContentProviderEntry.class);
        BindContentProviderPath annotationPath = parent.getElement().getAnnotation(BindContentProviderPath.class);
        if (annotationPath != null && annotation != null) {
            // manage content provider generation
            String methodPath = "";
            if (StringUtils.hasText(annotation.path())) {
                methodPath = annotation.path();
            }
            AssertKripton.assertTrue(!this.hasDynamicPageSizeConditions(), "Method %s.%s can not be marked with @%s annotation and contains parameter with @%s annotation", getParent().getName(), getName(), BindContentProviderEntry.class.getSimpleName(), BindSqlPageSize.class.getSimpleName(), IncompatibleAnnotationException.class);
            this.contentProviderEntryPathEnabled = true;
            this.contentProviderEntryPath = methodPath;
            this.contentProviderMethodName = getElement().getSimpleName().toString() + parent.contentProviderCounter + "ForContentProvider";
            parent.contentProviderCounter++;
            final SQLiteEntity entity = this.getEntity();
            String contentProviderUri = contentProviderUri();
            AssertKripton.assertTrueOrInvalidMethodSignException(!contentProviderUri.endsWith("/"), this, " content provider URI '%s' can not finish with '/'", contentProviderUri);
            AssertKripton.assertTrueOrInvalidMethodSignException(!this.contentProviderPath().contains("//"), this, " content provider URI '%s' can not contain with '//'", contentProviderUri);
            List<ContentUriPlaceHolder> uriParams = ContentUriChecker.getInstance().extract(contentProviderUri);
            String uriTemplate = ContentUriChecker.getInstance().replace(contentProviderUri(), new UriPlaceHolderReplacerListener() {

                @Override
                public String onParameterName(int pathSegmentIndex, String name) {
                    throw new UnsupportedOperationException("STUB: not implemented");
                }
            });
            this.contentProviderUriVariables = uriParams;
            this.contentProviderUriTemplate = uriTemplate;
            // if we have a path, we have to remove the initial /
            this.contentProviderEntryPathTemplate = uriTemplate.substring(getParent().getParent().contentProviderUri().length());
            if (this.contentProviderEntryPathTemplate.startsWith("/"))
                contentProviderEntryPathTemplate = this.contentProviderEntryPathTemplate.substring(1);
            // INSERT from SELECT type SQL can not be used with content provider
            AssertKripton.assertTrueOrInvalidMethodSignException(!(this.jql != null && this.jql.operationType == JQLType.INSERT && this.jql.containsSelectOperation), this, " INSERT-FROM-SELECT sql can not be used for content provider");
            // UPDATE from SELECT type SQL can not be used with content provider
            AssertKripton.assertTrueOrInvalidMethodSignException(!(this.jql != null && this.jql.operationType == JQLType.UPDATE && this.jql.containsSelectOperation), this, " UPDATE-FROM-SELECT sql can not be used for content provider");
        }
        if (element.getAnnotation(BindSqlSelect.class) != null) {
            FindSqlChildSelectVisitor visitor = new FindSqlChildSelectVisitor();
            for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();
                if (BindSqlSelect.class.getName().equals(annotationMirror.getAnnotationType().toString())) {
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                        String key = entry.getKey().getSimpleName().toString();
                        entry.getValue().accept(visitor, key);
                    }
                    List<Triple<String, String, SQLiteModelMethod>> childrenSelects = visitor.getChildrenSelects();
                    this.childrenSelects = childrenSelects;
                    break;
                }
            }
        }
    }

    public boolean isPagedLiveData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isLiveData(SQLiteModelMethod methodDefinition) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasAdapterForParam(String paramName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the JQL declared.
     *
     * @return the JQL declared
     */
    private String getJQLDeclared() {
        ModelAnnotation inserAnnotation = this.getAnnotation(BindSqlInsert.class);
        ModelAnnotation updateAnnotation = this.getAnnotation(BindSqlUpdate.class);
        ModelAnnotation selectAnnotation = this.getAnnotation(BindSqlSelect.class);
        ModelAnnotation deleteAnnotation = this.getAnnotation(BindSqlDelete.class);
        String jql = null;
        int counter = 0;
        if (selectAnnotation != null) {
            jql = selectAnnotation.getAttribute(AnnotationAttributeType.JQL);
            if (StringUtils.hasText(jql)) {
                counter++;
                AssertKripton.assertTrue(selectAnnotation.getAttributeCount() > 1, "Annotation %s in method %s.%s have more than one annotation with JQL attribute", selectAnnotation.getSimpleName(), this.getParent().getName(), this.getName());
            }
        }
        if (inserAnnotation != null) {
            jql = inserAnnotation.getAttribute(AnnotationAttributeType.JQL);
            if (StringUtils.hasText(jql)) {
                counter++;
                AssertKripton.assertTrue(inserAnnotation.getAttributeCount() > 1, "Annotation %s in method %s.%s have more than one annotation with JQL attribute", inserAnnotation.getSimpleName(), this.getParent().getName(), this.getName());
            }
        }
        if (updateAnnotation != null) {
            jql = updateAnnotation.getAttribute(AnnotationAttributeType.JQL);
            if (StringUtils.hasText(jql)) {
                counter++;
                AssertKripton.assertTrue(updateAnnotation.getAttributeCount() > 1, "Annotation %s in method %s.%s have more than one annotation with JQL attribute", updateAnnotation.getSimpleName(), this.getParent().getName(), this.getName());
            }
        }
        if (deleteAnnotation != null) {
            jql = deleteAnnotation.getAttribute(AnnotationAttributeType.JQL);
            if (StringUtils.hasText(jql)) {
                counter++;
                AssertKripton.assertTrue(deleteAnnotation.getAttributeCount() > 1, "Annotation %s in method %s.%s have more than one annotation with JQL attribute", deleteAnnotation.getSimpleName(), this.getParent().getName(), this.getName());
            }
        }
        AssertKripton.assertTrue(counter <= 1, "Method %s.%s have more than one annotation with JQL attribute", this.getParent().getName(), this.getName());
        // remove unscape charater (example \'%\' -> '%')
        jql = StringEscapeUtils.unescapeEcmaScript(jql);
        return jql;
    }

    /*
   * (non-Javadoc)
   *
   * @see
   * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelElement#accept(
   * com.abubusoft.kripton.processor.sqlite.model.SQLiteModelElementVisitor)
   */
    @Override
    public void accept(SQLiteModelElementVisitor visitor) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The unsupported SQL for dynamic where.
     */
    static List<Class<? extends Annotation>> unsupportedSQLForDynamicWhere = new ArrayList<>();

    /**
     * The unsupported SQL for dynamic order by.
     */
    static List<Class<? extends Annotation>> unsupportedSQLForDynamicOrderBy = new ArrayList<>();

    static {
        unsupportedSQLForDynamicWhere.add(BindSqlInsert.class);
        unsupportedSQLForDynamicOrderBy.add(BindSqlInsert.class);
        unsupportedSQLForDynamicOrderBy.add(BindSqlUpdate.class);
        unsupportedSQLForDynamicOrderBy.add(BindSqlDelete.class);
    }

    /**
     * Look for a method parameter which is annotated with an annotationClass
     * annotation. When it is found, a client action is required through
     * listener.
     *
     * @param <A>                  the generic type
     * @param parent               the parent
     * @param annotationClazz      the annotation clazz
     * @param unsupportedQueryType the unsupported query type
     * @param listener             the listener
     */
    private <A extends Annotation> void findStringDynamicStatement(SQLiteDaoDefinition parent, Class<A> annotationClazz, List<Class<? extends Annotation>> unsupportedQueryType, OnFoundDynamicParameter listener) {
        int counter = 0;
        for (VariableElement p : element.getParameters()) {
            A annotation = p.getAnnotation(annotationClazz);
            if (annotation != null) {
                // Dynamic queries can not be used in Inser SQL.
                for (Class<? extends Annotation> item : unsupportedQueryType) {
                    AssertKripton.assertTrueOrInvalidMethodSignException(element.getAnnotation(item) == null, this, "in this method is not allowed to mark parameters with @%s annotation.", annotationClazz.getSimpleName());
                }
                AssertKripton.assertTrueOrInvalidMethodSignException(TypeUtility.isString(TypeUtility.typeName(p)), this, "only String parameters can be marked with @%s annotation.", annotationClazz.getSimpleName());
                listener.onFoundParameter(p.getSimpleName().toString());
                counter++;
            }
        }
        AssertKripton.assertTrueOrInvalidMethodSignException(counter < 2, this, "there are %s parameters marked with @%s. Only one is allowed.", counter, annotationClazz.getSimpleName());
    }

    /**
     * Look for a method parameter which is annotated with an annotationClass
     * annotation. When it is found, a client action is required through
     * listener.
     *
     * @param <A>                  the generic type
     * @param parent               the parent
     * @param annotationClazz      the annotation clazz
     * @param unsupportedQueryType the unsupported query type
     * @param listener             the listener
     */
    private <A extends Annotation> void findIntDynamicStatement(SQLiteDaoDefinition parent, Class<A> annotationClazz, List<Class<? extends Annotation>> unsupportedQueryType, OnFoundDynamicParameter listener) {
        int counter = 0;
        for (VariableElement p : element.getParameters()) {
            A annotation = p.getAnnotation(annotationClazz);
            if (annotation != null) {
                // Dynamic queries can not be used in Inser SQL.
                for (Class<? extends Annotation> item : unsupportedQueryType) {
                    AssertKripton.assertTrueOrInvalidMethodSignException(element.getAnnotation(item) == null, this, "in this method is not allowed to mark parameters with @%s annotation.", annotationClazz.getSimpleName());
                }
                AssertKripton.assertTrueOrInvalidMethodSignException(TypeUtility.isTypeIncludedIn(TypeUtility.typeName(p), Integer.TYPE), this, "only a int parameter can be marked with @%s annotation.", annotationClazz.getSimpleName());
                listener.onFoundParameter(p.getSimpleName().toString());
                counter++;
            }
        }
        AssertKripton.assertTrueOrInvalidMethodSignException(counter < 2, this, "there are %s parameters marked with @%s. Only one is allowed.", counter, annotationClazz.getSimpleName());
    }

    public String findParameterAliasByName(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String findParameterNameByAlias(String nameOrAlias) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName findParameterTypeByAliasOrName(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SQLiteDaoDefinition getParent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasDynamicOrderByConditions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasDynamicWhereConditions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasDynamicWhereArgs() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasDynamicPageSizeConditions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isThisDynamicWhereConditionsName(String parameterName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isLogEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isThisDynamicPageSizeName(String parameterName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isThisDynamicWhereArgsName(String parameterName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasPaginatedResultParameter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contentProviderUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contentProviderPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
   * (non-Javadoc)
   *
   * @see com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLContext#
   * getContextDescription()
   */
    @Override
    public String getContextDescription() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getAdapterForParam(String paramName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String findEntityProperty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The prepared statement name.
     */
    private String preparedStatementName;

    public String buildPreparedStatementName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasDynamicParts() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String buildSQLName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String buildSQLNameWithCurrentCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isSpreadParameter(String methodParamName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getParentName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Finder<SQLProperty> findEntityByName(String entityName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasCustomProjection() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasOptionalResult() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getOptionalReturnClass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
