/**
 */
package com.abubusoft.kripton.processor.sqlite;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import com.abubusoft.kripton.android.sqlite.TransactionResult;
import com.abubusoft.kripton.processor.core.reflect.TypeUtility;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteDatabaseSchema;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public abstract class SchemaUtility {

    /*
	public static void generateTransactionForDaoFactory(TypeSpec.Builder classBuilder, SQLiteDatabaseSchema schema) {
		generateMethodDeclaration(schema, classBuilder, true);
		generateMethodDeclaration(schema, classBuilder, false);
	}*/
    /**
     * @param schema
     * @param classBuilder
     * @return
     */
    /*
	private static void generateMethodDeclaration(SQLiteDatabaseSchema schema, Builder classBuilder, boolean async) {
		for (ExecutableElement item : schema.transactions) {
			Set<String> daoNames = new HashSet<String>();
			daoNames.addAll(schema.getDaoNameSet());

			MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(item.getSimpleName().toString())
					.addModifiers(Modifier.PUBLIC);
			
			if (async) {
				methodBuilder.returns(ParameterizedTypeName.get(Future.class, Boolean.TYPE));
			} else {
				methodBuilder.returns(Boolean.TYPE);
			}

			methodBuilder.addJavadoc("Executes $L {@link $L}\n", (async? "in async mode":""), item.getSimpleName().toString());

			{
				for (VariableElement p : item.getParameters()) {
					// schema.contains(name)
					if (!daoNames.contains(TypeUtility.typeName(p.asType()).toString())) {
						methodBuilder.addParameter(TypeUtility.typeName(p.asType()), p.getSimpleName().toString());
					}
				}
			}
			
			classBuilder.addMethod(methodBuilder.build());
		}
	}*/
    public static void generateTransaction(TypeSpec.Builder classBuilder, SQLiteDatabaseSchema schema, boolean onlyInterface) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
