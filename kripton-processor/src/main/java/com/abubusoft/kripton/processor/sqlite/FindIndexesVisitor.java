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
import javax.lang.model.util.SimpleAnnotationValueVisitor7;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;

public class FindIndexesVisitor extends SimpleAnnotationValueVisitor7<Void, String> {

    boolean inTasks = false;

    Pair<List<String>, Boolean> currentValue;

    /**
     * The tasks.
     */
    ArrayList<Pair<List<String>, Boolean>> indexes = new ArrayList<Pair<List<String>, Boolean>>();

    public ArrayList<Pair<List<String>, Boolean>> getAllIndexes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Void visitBoolean(boolean b, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ArrayList<Pair<List<String>, Boolean>> getUniqueIndexes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ArrayList<Pair<List<String>, Boolean>> getNotUniqueIndexes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected ArrayList<Pair<List<String>, Boolean>> getIndexes(boolean unique) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.lang.model.util.SimpleAnnotationValueVisitor6#visitString(java.lang
	 * .String, java.lang.Object)
	 */
    @Override
    public Void visitString(String s, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.lang.model.util.SimpleAnnotationValueVisitor6#visitAnnotation(javax
	 * .lang.model.element.AnnotationMirror, java.lang.Object)
	 */
    @Override
    public Void visitAnnotation(AnnotationMirror a, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.lang.model.util.SimpleAnnotationValueVisitor6#visitArray(java.util.
	 * List, java.lang.Object)
	 */
    @Override
    public Void visitArray(List<? extends AnnotationValue> vals, String p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
