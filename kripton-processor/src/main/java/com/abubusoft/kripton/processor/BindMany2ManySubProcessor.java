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
/**
 */
package com.abubusoft.kripton.processor;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.android.annotation.BindDaoMany2Many;
import com.abubusoft.kripton.common.Pair;
import com.abubusoft.kripton.processor.bind.model.many2many.M2MEntity;
import com.abubusoft.kripton.processor.bind.model.many2many.M2MModel;
import com.abubusoft.kripton.processor.element.GeneratedTypeElement;
import com.abubusoft.kripton.processor.exceptions.InvalidKindForAnnotationException;
import com.abubusoft.kripton.processor.sqlite.BindM2MBuilder;

/**
 * Annotation processor for shared preferences.
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindMany2ManySubProcessor extends BaseProcessor {

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.BaseProcessor#clear()
	 */
    public void clear() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * The model.
     */
    private M2MModel model;

    /**
     * The result.
     */
    public Pair<Set<GeneratedTypeElement>, Set<GeneratedTypeElement>> result;

    /*
	 * (non-Javadoc)
	 * 
	 * @see com.abubusoft.kripton.processor.BaseProcessor#
	 * getSupportedAnnotationClasses()
	 */
    protected Set<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set,
	 * javax.annotation.processing.RoundEnvironment)
	 */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
