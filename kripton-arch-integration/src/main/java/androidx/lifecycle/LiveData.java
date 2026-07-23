/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.lifecycle;

import static androidx.lifecycle.Lifecycle.State.DESTROYED;
import static androidx.lifecycle.Lifecycle.State.STARTED;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.executor.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

/**
 * LiveData is a data holder class that can be observed within a given lifecycle.
 * This means that an {@link Observer} can be added in a pair with a {@link LifecycleOwner}, and
 * this observer will be notified about modifications of the wrapped data only if the paired
 * LifecycleOwner is in active state. LifecycleOwner is considered as active, if its state is
 * {@link Lifecycle.State#STARTED} or {@link Lifecycle.State#RESUMED}. An observer added via
 * {@link #observeForever(Observer)} is considered as always active and thus will be always notified
 * about modifications. For those observers, you should manually call
 * {@link #removeObserver(Observer)}.
 *
 * <p> An observer added with a Lifecycle will be automatically removed if the corresponding
 * Lifecycle moves to {@link Lifecycle.State#DESTROYED} state. This is especially useful for
 * activities and fragments where they can safely observe LiveData and not worry about leaks:
 * they will be instantly unsubscribed when they are destroyed.
 *
 * <p>
 * In addition, LiveData has {@link LiveData#onActive()} and {@link LiveData#onInactive()} methods
 * to get notified when number of active {@link Observer}s change between 0 and 1.
 * This allows LiveData to release any heavy resources when it does not have any Observers that
 * are actively observing.
 * <p>
 * This class is designed to hold individual data fields of {@link ViewModel},
 * but can also be used for sharing data between different modules in your application
 * in a decoupled fashion.
 *
 * @param <T> The type of data held by this instance
 * @see ViewModel
 */
public abstract class LiveData<T> {

    private final Object mDataLock = new Object();

    static final int START_VERSION = -1;

    private static final Object NOT_SET = new Object();

    private SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers = new SafeIterableMap<>();

    // how many observers are in active state
    private int mActiveCount = 0;

    private volatile Object mData = NOT_SET;

    // when setData is called, we set the pending data and actual data swap happens on the main
    // thread
    private volatile Object mPendingData = NOT_SET;

    private int mVersion = START_VERSION;

    private boolean mDispatchingValue;

    @SuppressWarnings("FieldCanBeLocal")
    private boolean mDispatchInvalidated;

    private final Runnable mPostValueRunnable = new Runnable() {

        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    private void considerNotify(ObserverWrapper observer) {
        if (!observer.mActive) {
            return;
        }
        // Check latest state b4 dispatch. Maybe it changed state but we didn't get the event yet.
        //
        // we still first check observer.active to keep it as the entrance for events. So even if
        // the observer moved to an active state, if we've not received that event, we better not
        // notify for a more predictable notification order.
        if (!observer.shouldBeActive()) {
            observer.activeStateChanged(false);
            return;
        }
        if (observer.mLastVersion >= mVersion) {
            return;
        }
        observer.mLastVersion = mVersion;
        //noinspection unchecked
        observer.mObserver.onChanged((T) mData);
    }

    private void dispatchingValue(@Nullable ObserverWrapper initiator) {
        if (mDispatchingValue) {
            mDispatchInvalidated = true;
            return;
        }
        mDispatchingValue = true;
        do {
            mDispatchInvalidated = false;
            if (initiator != null) {
                considerNotify(initiator);
                initiator = null;
            } else {
                for (Iterator<Map.Entry<Observer<? super T>, ObserverWrapper>> iterator = mObservers.iteratorWithAdditions(); iterator.hasNext(); ) {
                    considerNotify(iterator.next().getValue());
                    if (mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (mDispatchInvalidated);
        mDispatchingValue = false;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    public void observeForever(@NonNull Observer<? super T> observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    public void removeObserver(@NonNull final Observer<? super T> observer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    @MainThread
    public void removeObservers(@NonNull final LifecycleOwner owner) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void postValue(T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @MainThread
    protected void setValue(T value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    public T getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getVersion() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onActive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void onInactive() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public boolean hasObservers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("WeakerAccess")
    public boolean hasActiveObservers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    class LifecycleBoundObserver extends ObserverWrapper implements GenericLifecycleObserver {

        @NonNull
        final LifecycleOwner mOwner;

        LifecycleBoundObserver(@NonNull LifecycleOwner owner, Observer<? super T> observer) {
            super(observer);
            mOwner = owner;
        }

        @Override
        boolean shouldBeActive() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        boolean isAttachedTo(LifecycleOwner owner) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        void detachObserver() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private abstract class ObserverWrapper {

        final Observer<? super T> mObserver;

        boolean mActive;

        int mLastVersion = START_VERSION;

        ObserverWrapper(Observer<? super T> observer) {
            mObserver = observer;
        }

        abstract boolean shouldBeActive();

        boolean isAttachedTo(LifecycleOwner owner) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void detachObserver() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void activeStateChanged(boolean newActive) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private class AlwaysActiveObserver extends ObserverWrapper {

        AlwaysActiveObserver(Observer<? super T> observer) {
            super(observer);
        }

        @Override
        boolean shouldBeActive() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private static void assertMainThread(String methodName) {
        if (!ArchTaskExecutor.getInstance().isMainThread()) {
            throw new IllegalStateException("Cannot invoke " + methodName + " on a background" + " thread");
        }
    }
}
