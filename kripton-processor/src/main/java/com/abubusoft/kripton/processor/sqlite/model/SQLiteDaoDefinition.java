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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.ModelBucket;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeVariableResolver;
import com.squareup.javapoet.TypeName;

/**
 * The Class SQLiteDaoDefinition.
 */
public class SQLiteDaoDefinition extends ModelBucket<SQLiteModelMethod, TypeElement> implements SQLiteModelElement {

    /**
     * The Constant PARAM_PARSER_PREFIX.
     */
    public static final String PARAM_PARSER_PREFIX = "parser";

    /**
     * The Constant PARAM_SERIALIZER_PREFIX.
     */
    public static final String PARAM_SERIALIZER_PREFIX = "serializer";

    /**
     * The parent.
     */
    private WeakReference<SQLiteDatabaseSchema> parent;

    /**
     * The type variable resolver.
     */
    private TypeVariableResolver typeVariableResolver;

    public SQLiteDatabaseSchema getParent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long nextCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long currentCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getCurrentCounter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void resolveTypeVariable(SQLiteModelMethod value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.core.ModelBucket#add(com.abubusoft.
	 * kripton.processor.core.ModelEntity)
	 */
    @Override
    public void add(SQLiteModelMethod value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The entity class name.
     */
    private String entityClassName;

    /**
     * The entity simply class name.
     */
    private String entitySimplyClassName;

    public boolean isGenerated() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The generated.
     */
    private boolean generated;

    public String getEntitySimplyClassName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getEntityClassName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getSimpleEntityClassName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Instantiates a new SQ lite dao definition.
     *
     * @param databaseSchema
     *            the database schema
     * @param name
     *            the name
     * @param element
     *            the element
     * @param entityClassName
     *            the entity class name
     * @param generated
     *            the generated
     */
    public SQLiteDaoDefinition(SQLiteDatabaseSchema databaseSchema, String name, TypeElement element, String entityClassName, boolean generated) {
        super(element.getSimpleName().toString(), element);
        this.generated = generated;
        this.parent = new WeakReference<SQLiteDatabaseSchema>(databaseSchema);
        this.entityClassName = entityClassName;
        int i = 0;
        i = entityClassName.indexOf(".");
        if (i > 0) {
            entitySimplyClassName = entityClassName.substring(entityClassName.lastIndexOf(".") + 1);
        } else {
            entitySimplyClassName = entityClassName;
        }
        typeVariableResolver = TypeVariableResolver.build(element);
        implementedInterface = new HashSet<>();
        i = name.indexOf(".");
        if (i > 0) {
            this.name = name.substring(name.lastIndexOf(".") + 1);
        } else {
            this.name = name;
        }
    }

    public TypeName resolveTypeVariable(TypeName inputTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
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

    public SQLiteEntity getEntity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isLogEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * map of params for which generate a java2Content method converter.
     */
    public Map<TypeName, String> managedParams = new HashMap<TypeName, String>();

    /**
     * <p>
     * if <code>true</code> indicates that content provider generation is
     * enabled.
     */
    public boolean contentProviderEnabled;

    /**
     * Base path associated to content provider.
     */
    public String contentProviderPath;

    /**
     * type name exposed by content provider.
     */
    public String contentProviderTypeName;

    /**
     * Collections of prepared statements.
     */
    public List<String> preparedStatementNames = new ArrayList<String>();

    String buildPreparedStatementName(String methodName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * number of element generated for content provider.
     */
    public long contentProviderCounter;

    /**
     * The implemented interface.
     */
    public Set<TypeName> implementedInterface;

    /**
     * Example: DAO_PERSON_UID.
     */
    public String daoUidName;

    /**
     * Example: 0 to n.
     */
    public int daoUidValue;

    public String generateJava2ContentSerializer(TypeName paramTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String generateJava2ContentParser(TypeName paramTypeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contentProviderUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String contentProviderPath() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addImplementedInterface(TypeName className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public TypeName getTypeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasLiveData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasSamePackageOfSchema() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasRelations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getTableName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
