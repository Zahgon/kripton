package androidx.recyclerview.widget;

import androidx.annotation.NonNull;

/**
 * Wraps a {@link ListUpdateCallback} callback and batches operations that can be merged.
 * <p>
 * For instance, when 2 add operations comes that adds 2 consecutive elements,
 * BatchingListUpdateCallback merges them and calls the wrapped callback only once.
 * <p>
 * This is a general purpose class and is also used by
 * {@link DiffUtil.DiffResult DiffResult} and
 * {@link SortedList} to minimize the number of updates that are dispatched.
 * <p>
 * If you use this class to batch updates, you must call {@link #dispatchLastEvent()} when the
 * stream of update events drain.
 */
public class BatchingListUpdateCallback implements ListUpdateCallback {

    private static final int TYPE_NONE = 0;

    private static final int TYPE_ADD = 1;

    private static final int TYPE_REMOVE = 2;

    private static final int TYPE_CHANGE = 3;

    final ListUpdateCallback mWrapped;

    int mLastEventType = TYPE_NONE;

    int mLastEventPosition = -1;

    int mLastEventCount = -1;

    Object mLastEventPayload = null;

    public BatchingListUpdateCallback(@NonNull ListUpdateCallback callback) {
        mWrapped = callback;
    }

    public void dispatchLastEvent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onInserted(int position, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onRemoved(int position, int count) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
