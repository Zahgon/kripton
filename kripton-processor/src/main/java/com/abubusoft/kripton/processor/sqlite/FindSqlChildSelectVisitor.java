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
package com.abubusoft.kripton.processor.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import com.abubusoft.kripton.common.Triple;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.sqlite.model.SQLiteModelMethod;

/**
 * The Class FindSqlChildSelectVisitor.
 */
public class FindSqlChildSelectVisitor extends SimpleAnnotationValueVisitor7<Void, String> {

    /**
     * <code>true</code> if we are in interested part
     */
    boolean activate = false;

    private Triple<String, String, SQLiteModelMethod> current;

    List<Triple<String, String, SQLiteModelMethod>> childrenSelects = new ArrayList<Triple<String, String, SQLiteModelMethod>>();

    public List<Triple<String, String, SQLiteModelMethod>> getChildrenSelects() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Void visitAnnotation(AnnotationMirror a, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Void visitString(String s, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Void visitType(TypeMirror t, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Void visitArray(List<? extends AnnotationValue> vals, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
