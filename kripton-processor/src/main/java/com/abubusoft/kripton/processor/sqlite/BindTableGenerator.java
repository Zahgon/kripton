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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Filer;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.util.Elements;
import org.apache.commons.lang3.StringUtils;
import com.abubusoft.kripton.android.ColumnType;
import com.abubusoft.kripton.android.annotation.BindDataSource;
import com.abubusoft.kripton.android.annotation.BindSqlType;
import com.abubusoft.kripton.android.sqlite.ForeignKeyAction;
import com.abubusoft.kripton.android.sqlite.SQLiteTable;
import com.abubusoft.kripton.common.CaseFormat;
import com.abubusoft.kripton.common.Converter;
import com.abubusoft.kripton.common.One;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.processor.BindDataSourceSubProcessor;
import com.abubusoft.kripton.processor.bind.BindTypeContext;
import com.abubusoft.kripton.processor.bind.JavaWriterHelper;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.Finder;
import com.abubusoft.kripton.processor.core.ManagedPropertyPersistenceHelper;
import com.abubusoft.kripton.processor.core.ManagedPropertyPersistenceHelper.PersistType;
import com.abubusoft.kripton.processor.core.ModelElementVisitor;
import com.abubusoft.kripton.processor.core.ModelProperty;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.element.GeneratedTypeElement;
import com.abubusoft.kripton.processor.exceptions.InvalidBeanTypeException;
import com.abubusoft.kripton.processor.exceptions.InvalidForeignKeyTypeException;
import com.abubusoft.kripton.processor.exceptions.NoDaoElementFound;
import com.abubusoft.kripton.processor.sqlite.core.JavadocUtility;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLChecker;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLContext;
import com.abubusoft.kripton.processor.sqlite.grammars.jql.JQLReplacerListenerImpl;
import com.abubusoft.kripton.processor.sqlite.model.SQLProperty;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDaoDefinition;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteEntity;
import com.abubusoft.kripton.processor.sqlite.transform.SQLTransformer;
import com.abubusoft.kripton.processor.utils.AnnotationProcessorUtilis;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.FieldSpec.Builder;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * <p>
 * Generate class :{entity}Table which represents table for entity.
 * </p>
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindTableGenerator extends AbstractBuilder implements ModelElementVisitor<SQLiteEntity, SQLProperty> {

    /**
     * The Constant SUFFIX.
     */
    public static final String SUFFIX = "Table";

    /**
     * The column name to upper case converter.
     */
    private Converter<String, String> columnNameToUpperCaseConverter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_UNDERSCORE);

    /**
     * Instantiates a new bind table generator.
     *
     * @param elementUtils
     *            the element utils
     * @param filer
     *            the filer
     * @param model
     *            the model
     */
    public BindTableGenerator(Elements elementUtils, Filer filer, SQLiteDatabaseSchema model) {
        super(elementUtils, filer, model);
    }

    public static void generate(Elements elementUtils, Filer filer, SQLiteDatabaseSchema schema, Set<GeneratedTypeElement> generatedEntities) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Visit.
     *
     * @param schema
     *            the schema
     * @param entity
     *            the entity
     * @throws Exception
     *             the exception
     */
    private void visit(SQLiteDatabaseSchema schema, GeneratedTypeElement entity) throws Exception {
        int indexCounter = 0;
        // generate the class name that represents the table
        String classTableName = getTableClassName(entity.getSimpleName());
        PackageElement pkg = elementUtils.getPackageElement(entity.packageName);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();
        AnnotationProcessorUtilis.infoOnGeneratedClasses(BindDataSource.class, packageName, classTableName);
        classBuilder = TypeSpec.classBuilder(classTableName).addModifiers(Modifier.PUBLIC).addSuperinterface(SQLiteTable.class);
        BindTypeContext context = new BindTypeContext(classBuilder, TypeUtility.typeName(packageName, classTableName), Modifier.PRIVATE, Modifier.STATIC);
        // javadoc for class
        classBuilder.addJavadoc("<p>");
        classBuilder.addJavadoc("\nEntity <code>$L</code> is associated to table <code>$L</code>\n", entity.getSimpleName(), entity.getTableName());
        classBuilder.addJavadoc("This class represents table associated to entity.\n");
        classBuilder.addJavadoc("</p>\n");
        JavadocUtility.generateJavadocGeneratedBy(classBuilder);
        classBuilder.addJavadoc(" @see $T\n", TypeUtility.className(entity.getName()));
        {
            // @formatter:off
            // table_name
            FieldSpec fieldSpec = FieldSpec.builder(String.class, "TABLE_NAME", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL).initializer("\"$L\"", entity.getTableName()).addJavadoc("Costant represents typeName of table $L\n", entity.getTableName()).build();
            classBuilder.addField(fieldSpec);
            // @formatter:on
        }
        StringBuilder bufferTable = new StringBuilder();
        StringBuilder bufferForeignKey = new StringBuilder();
        // shared between create table and drop table
        StringBuilder bufferIndexesCreate = new StringBuilder();
        StringBuilder bufferDropTable = new StringBuilder();
        StringBuilder bufferIndexesDrop = new StringBuilder();
        bufferTable.append("CREATE TABLE " + entity.getTableName());
        // define column typeName set
        String separator = "";
        bufferTable.append(" (");
        List<String> uniqueConstraintColumn = new ArrayList<>();
        // for each column, that need to be persisted on table
        for (SQLProperty item : entity.getCollection()) {
            bufferTable.append(separator);
            bufferTable.append(item.columnName);
            // every column is a long
            if (item.getPropertyType() == null) {
                bufferTable.append(" " + SQLTransformer.lookup(TypeName.LONG).getColumnTypeAsString());
            } else {
                bufferTable.append(" " + SQLTransformer.lookup(item.getPropertyType().getTypeName()).getColumnTypeAsString());
            }
            switch(item.columnType) {
                case PRIMARY_KEY:
                    bufferTable.append(" PRIMARY KEY AUTOINCREMENT NOT NULL");
                    break;
                case PRIMARY_KEY_UNMANGED:
                    bufferTable.append(" PRIMARY KEY NOT NULL");
                    break;
                case UNIQUE:
                    bufferTable.append(" UNIQUE");
                    break;
                case INDEXED:
                    bufferIndexesCreate.append(String.format(" CREATE INDEX idx_%s_%s ON %s(%s);", entity.getTableName(), item.columnName, entity.getTableName(), item.columnName));
                    bufferIndexesDrop.append(String.format(" DROP INDEX IF EXISTS idx_%s_%s;", entity.getTableName(), item.columnName));
                    uniqueConstraintColumn.add(item.columnName);
                    break;
                case STANDARD:
                    break;
            }
            boolean nullable = item.isNullable();
            // if it is not primary key and it is not nullable, then add not
            // null
            if (!nullable && !(item.columnType == ColumnType.PRIMARY_KEY || item.columnType == ColumnType.PRIMARY_KEY_UNMANGED)) {
                bufferTable.append(" NOT NULL");
            }
            // foreign key
            String foreignClassName = item.foreignParentClassName;
            if (item.isForeignKey()) {
                SQLiteEntity reference = model.getEntity(foreignClassName);
                if (reference == null) {
                    // check if we have a DAO associated into DataSource
                    // definition
                    boolean found = false;
                    for (SQLiteDaoDefinition daoDefinition : schema.getCollection()) {
                        if (daoDefinition.getEntityClassName().equals(foreignClassName)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        throw new NoDaoElementFound(schema, TypeUtility.className(foreignClassName));
                    } else {
                        throw new InvalidBeanTypeException(item, foreignClassName);
                    }
                }
                bufferForeignKey.append(", FOREIGN KEY(" + item.columnName + ") REFERENCES " + reference.getTableName() + "(" + reference.getPrimaryKey().columnName + ")");
                if (item.onDeleteAction != ForeignKeyAction.NO_ACTION) {
                    bufferForeignKey.append(" ON DELETE " + item.onDeleteAction.toString().replaceAll("_", " "));
                }
                if (item.onUpdateAction != ForeignKeyAction.NO_ACTION) {
                    bufferForeignKey.append(" ON UPDATE " + item.onUpdateAction.toString().replaceAll("_", " "));
                }
                // INSERT as dependency only if reference is another entity.
                // Same entity can not be own dependency.
                if (!entity.getClassName().equals(TypeUtility.typeName(reference.getElement()))) {
                    entity.referedEntities.add(reference);
                }
            }
            separator = ", ";
        }
        // add foreign key
        bufferTable.append(bufferForeignKey.toString());
        // add contrants unique
        bufferTable.append(String.format(", UNIQUE (%s)", StringUtils.join(uniqueConstraintColumn, ", ")));
        bufferTable.append(");");
        // add indexes creation one table
        if (bufferIndexesCreate.length() > 0) {
            bufferTable.append(bufferIndexesCreate.toString());
        }
        // add single column indexes (NOT UNIQUE)
        {
            Triple<String, String, String> multiIndexes = buildIndexes(entity, false, indexCounter);
            if (!StringUtils.isEmpty(multiIndexes.value0)) {
                bufferTable.append(multiIndexes.value0 + ";");
                bufferIndexesDrop.append(multiIndexes.value1 + ";");
            }
        }
        {
            // create table SQL
            // @formatter:off
            FieldSpec.Builder fieldSpec = FieldSpec.builder(String.class, "CREATE_TABLE_SQL").addModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC);
            // @formatter:on
            // @formatter:off
            fieldSpec.addJavadoc("<p>\nDDL to create table $L\n</p>\n", entity.getTableName());
            fieldSpec.addJavadoc("\n<pre>$L</pre>\n", bufferTable.toString());
            // @formatter:on
            classBuilder.addField(fieldSpec.initializer("$S", bufferTable.toString()).build());
        }
        // drop table SQL
        // index does not need to be dropped, they are automatically detroyed
        // with tables
        if (bufferIndexesDrop.length() > 0) {
            bufferDropTable.append(bufferIndexesDrop.toString());
        }
        bufferDropTable.append("DROP TABLE IF EXISTS " + entity.getTableName() + ";");
        {
            // @formatter:off
            FieldSpec fieldSpec = FieldSpec.builder(String.class, "DROP_TABLE_SQL").addModifiers(Modifier.STATIC, Modifier.FINAL, Modifier.PUBLIC).initializer("$S", bufferDropTable.toString()).addJavadoc("<p>\nDDL to drop table $L\n</p>\n", entity.getTableName()).addJavadoc("\n<pre>$L</pre>\n", bufferDropTable.toString()).build();
            // @formatter:on
            classBuilder.addField(fieldSpec);
        }
        // define column typeName set
        for (ModelProperty item : entity.getCollection()) {
            item.accept(this);
        }
        ManagedPropertyPersistenceHelper.generateFieldPersistance(context, entity.getCollection(), PersistType.BYTE, true, Modifier.STATIC, Modifier.PUBLIC);
        model.sqlForCreate.add(bufferTable.toString());
        model.sqlForDrop.add(bufferDropTable.toString());
        generateColumnsArray(entity);
        TypeSpec typeSpec = classBuilder.build();
        JavaWriterHelper.writeJava2File(filer, packageName, typeSpec);
    }

    public static String getTableClassName(String entityName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ClassName tableClassName(SQLiteDaoDefinition dao, SQLiteEntity entity) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.core.ModelElementVisitor#visit(com. abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema,
	 * com.abubusoft.kripton.processor.core.ModelClass)
	 */
    @Override
    public void visit(SQLiteDatabaseSchema schema, SQLiteEntity entity) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * generate columns array.
     *
     * @param entity
     *            the entity
     */
    private void generateColumnsArray(Finder<SQLProperty> entity) {
        // generate columns array
        Builder sp = FieldSpec.builder(ArrayTypeName.of(String.class), "COLUMNS", Modifier.STATIC, Modifier.PRIVATE, Modifier.FINAL);
        String s = "";
        StringBuilder buffer = new StringBuilder();
        for (SQLProperty property : entity.getCollection()) {
            buffer.append(s + "COLUMN_" + columnNameToUpperCaseConverter.convert(property.getName()));
            s = ", ";
        }
        classBuilder.addField(sp.addJavadoc("Columns array\n").initializer("{" + buffer.toString() + "}").build());
        classBuilder.addMethod(MethodSpec.methodBuilder("columns").addModifiers(Modifier.PUBLIC).addJavadoc("Columns array\n").addAnnotation(Override.class).returns(ArrayTypeName.of(String.class)).addStatement("return COLUMNS").build());
        classBuilder.addMethod(MethodSpec.methodBuilder("name").addModifiers(Modifier.PUBLIC).addJavadoc("table name\n").addAnnotation(Override.class).returns(TypeName.get(String.class)).addStatement("return TABLE_NAME").build());
    }

    public static Triple<String, String, String> buldIndexes(final SQLiteEntity entity, ArrayList<Pair<List<String>, Boolean>> indexList, boolean unique, int counter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Triple<String, String, String> buildIndexes(final GeneratedTypeElement entity, boolean unique, int counter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.core.ModelElementVisitor#visit(com. abubusoft.kripton.processor.core.ModelProperty)
	 */
    @Override
    public void visit(SQLProperty kriptonProperty) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
