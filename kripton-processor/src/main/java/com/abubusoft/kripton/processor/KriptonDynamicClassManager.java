package com.abubusoft.kripton.processor;

import java.util.HashSet;
import java.util.Set;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class KriptonDynamicClassManager {

    private static final String KRIPTON_X_DATABASE_CLASS_NAME = "androidx.sqlite.db.SupportSQLiteDatabase";

    private static final String KRIPTON_X_STATEMENT_CLASS_NAME = "androidx.sqlite.db.SupportSQLiteStatement";

    private static final String KRIPTON_X_PREFERENCE_MANAGER_CLASS_NAME = "androidx.preference.PreferenceManager";

    /*
	 * private static final String
	 * KRIPTON_X_PAGED_LIVE_DATA_HANDLER_IMPL_CLASS_NAME =
	 * "com.abubusoft.kripton.androidx.livedata.KriptonXPagedLiveDataHandlerImpl";
	 * private static final String
	 * KRIPTON_X_PAGED_LIVE_DATA_HANDLER_IMPL_CLASS_NAME =
	 * "com.abubusoft.kripton.androidx.livedata.KriptonXPagedLiveDataHandlerImpl";
	 */
    private static final String KRIPTON_X_PAGED_LIVE_DATA_HANDLER_IMPL_CLASS_NAME = "com.abubusoft.kripton.androidx.livedata.KriptonXPagedLiveDataHandlerImpl";

    private static final String KRIPTON_X_PAGED_LIVE_DATA_CLASS_NAME = "com.abubusoft.kripton.androidx.livedata.PagedLiveData";

    private static final String KRIPTON_X_MUTABLE_LIVE_DATA_CLASS_NAME = "androidx.lifecycle.MutableLiveData";

    private static final String KRIPTON_X_LIVE_DATA_CLASS_NAME = "com.abubusoft.kripton.androidx.livedata.KriptonXLiveData";

    private static final String KRIPTON_X_LIVE_DATA_HANDLER_IMPL_CLASS_NAME = "com.abubusoft.kripton.androidx.livedata.KriptonXLiveDataHandlerImpl";

    private static KriptonDynamicClassManager instance;

    public static KriptonDynamicClassManager getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Set<String> liveDataClazzSet = new HashSet<>();

    private ClassName preferenceManagerClazz;

    // public static void init(String androidxSupportValue, String
    // androidxDbSupportValue) {
    public static void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getPreferenceManagerClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private ClassName liveDataClazz;

    private ClassName liveDataHandlerClazz;

    private ClassName mutableLiveDataClazz;

    private ClassName pagedLiveDataClazz;

    private ClassName pagedLiveDataHandlerClazz;

    private ClassName databaseClazz;

    private ClassName statementClazz;

    public ClassName getStatementClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getDatabaseClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private KriptonDynamicClassManager() {
    }

    public ClassName getLiveDataClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getLiveDataHandlerClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getMutableLiveDataClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getPagedLiveDataClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ClassName getPagedLiveDataHandlerClazz() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isLiveData(String wrapperName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isPagedLiveData(TypeName liveDataReturnClass) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
