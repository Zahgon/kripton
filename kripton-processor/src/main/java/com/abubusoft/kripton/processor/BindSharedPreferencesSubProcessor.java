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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import com.abubusoft.kripton.android.annotation.BindPreference;
import com.abubusoft.kripton.android.annotation.BindPreferenceAdapter;
import com.abubusoft.kripton.android.annotation.BindSharedPreferences;
import com.abubusoft.kripton.android.sharedprefs.PreferenceType;
import com.abubusoft.kripton.annotation.BindDisabled;
import com.abubusoft.kripton.annotation.BindType;
import com.abubusoft.kripton.exception.KriptonRuntimeException;
import com.abubusoft.kripton.processor.bind.BindEntityBuilder;
import com.abubusoft.kripton.processor.bind.model.BindEntity;
import com.abubusoft.kripton.processor.bind.model.BindProperty;
import com.abubusoft.kripton.processor.core.AnnotationAttributeType;
import com.abubusoft.kripton.processor.core.AssertKripton;
import com.abubusoft.kripton.processor.core.ImmutableUtility;
import com.abubusoft.kripton.processor.core.ModelAnnotation;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility;
import com.abubusoft.kripton.processor.core.reflect.AnnotationUtility.AnnotationFilter;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility;
import com.abubusoft.kripton.processor.core.reflect.PropertyUtility.PropertyCreatedListener;
import com.abubusoft.kripton.processor.exceptions.InvalidDefinition;
import com.abubusoft.kripton.processor.sharedprefs.BindSharedPreferencesBuilder;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsEntity;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsModel;
import com.abubusoft.kripton.processor.sharedprefs.model.PrefsProperty;

/**
 * <p>Annotation processor for shared preferences.</p>
 *
 * <p>This processor is one-step processor.</p>
 *
 * @author Francesco Benincasa (info@abubusoft.com)
 */
public class BindSharedPreferencesSubProcessor extends BaseProcessor {

    /**
     * The model.
     */
    private PrefsModel model;

    /**
     * The class annotation filter.
     */
    private final AnnotationFilter classAnnotationFilter = AnnotationFilter.builder().add(BindType.class).add(BindSharedPreferences.class).build();

    /**
     * The property annotation filter.
     */
    private final AnnotationFilter propertyAnnotationFilter = AnnotationFilter.builder().add(BindDisabled.class).add(BindPreference.class).add(BindPreferenceAdapter.class).build();

    /* (non-Javadoc)
	 * @see com.abubusoft.kripton.processor.BaseProcessor#getSupportedAnnotationClasses()
	 */
    protected Set<Class<? extends Annotation>> getSupportedAnnotationClasses() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* (non-Javadoc)
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set, javax.annotation.processing.RoundEnvironment)
	 */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void generateClasses() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Analyze shared preferences.
     *
     * @param sharedPreference the shared preference
     * @return the string
     */
    private String analyzeSharedPreferences(final TypeElement sharedPreference) {
        TypeElement beanElement = sharedPreference;
        String result = beanElement.getSimpleName().toString();
        // create equivalent entity in the domain of bind processor
        final BindEntity bindEntity = BindEntityBuilder.parse(null, sharedPreference);
        final PrefsEntity currentEntity = new PrefsEntity(beanElement.getSimpleName().toString(), beanElement, AnnotationUtility.buildAnnotationList(beanElement, classAnnotationFilter));
        final boolean bindAllFields = AnnotationUtility.getAnnotationAttributeAsBoolean(currentEntity, BindType.class, AnnotationAttributeType.ALL_FIELDS, Boolean.TRUE);
        PropertyUtility.buildProperties(elementUtils, currentEntity, (entity, propertyElement) -> new PrefsProperty(currentEntity, propertyElement, AnnotationUtility.buildAnnotationList(propertyElement)), propertyAnnotationFilter, new PropertyCreatedListener<PrefsEntity, PrefsProperty>() {

            @Override
            public boolean onProperty(PrefsEntity entity, PrefsProperty property) {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        });
        ImmutableUtility.buildConstructors(elementUtils, currentEntity);
        model.entityAdd(currentEntity);
        return result;
    }
}
