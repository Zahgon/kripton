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
package com.abubusoft.kripton.processor.bind.model.many2many;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.android.annotation.BindDao;
import com.abubusoft.kripton.android.annotation.BindDaoMany2Many;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.StringUtils;
import com.abubusoft.kripton.processor.BaseProcessor;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

/**
 * The Class M2MEntity.
 */
public class M2MEntity extends M2MBase {

    /**
     * object must be immutable
     */
    public boolean immutable;

    public TypeName propertyPrimaryKey;

    public TypeName propertyKey1;

    public TypeName propertyKey2;

    /**
     * The package name.
     */
    private String packageName;

    /**
     * The entity 1 name.
     */
    public ClassName entity1Name;

    /**
     * The entity 2 name.
     */
    public ClassName entity2Name;

    /**
     * The id name.
     */
    public String idName;

    public String getPackageName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The name.
     */
    public String name;

    /**
     * The table name.
     */
    public String tableName;

    /**
     * The dao name.
     */
    public ClassName daoName;

    /**
     * The need to create.
     */
    public boolean needToCreate;

    /**
     * The dao element.
     */
    public TypeElement daoElement;

    /**
     * The generate methods.
     */
    public boolean generateMethods;

    /**
     * Instantiates a new m 2 M entity.
     *
     * @param daoElement the dao element
     * @param packageName the package name
     * @param entityName the entity name
     * @param daoClazzName the dao clazz name
     * @param entity1ClazzName the entity 1 clazz name
     * @param entity2ClazzName the entity 2 clazz name
     * @param idName the id name
     * @param tableName the table name
     * @param needToCreate the need to create
     * @param generatedMethods the generated methods
     */
    public M2MEntity(TypeElement daoElement, String packageName, String entityName, ClassName daoClazzName, ClassName entity1ClazzName, ClassName entity2ClazzName, String idName, String tableName, boolean needToCreate, boolean generatedMethods, boolean immutable) {
        this.packageName = packageName;
        this.entity1Name = entity1ClazzName;
        this.entity2Name = entity2ClazzName;
        this.daoName = daoClazzName;
        this.idName = idName;
        this.name = entityName;
        this.tableName = StringUtils.hasText(tableName) ? tableName : (CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.name));
        this.needToCreate = needToCreate;
        this.daoElement = daoElement;
        this.generateMethods = generatedMethods;
        this.immutable = immutable;
    }

    public static String extractClassName(String fullName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getClassName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static M2MEntity extractEntityManagedByDAO(TypeElement daoElement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getQualifiedName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getSimpleName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getDaoQualifiedName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
