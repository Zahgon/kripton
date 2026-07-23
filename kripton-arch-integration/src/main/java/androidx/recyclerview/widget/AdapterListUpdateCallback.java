package androidx.recyclerview.widget;

import androidx.annotation.NonNull;

/**
 * ListUpdateCallback that dispatches update events to the given adapter.
 *
 * @see DiffUtil.DiffResult#dispatchUpdatesTo(RecyclerView.Adapter)
 */
public final class AdapterListUpdateCallback implements ListUpdateCallback {

    @NonNull
    private final RecyclerView.Adapter mAdapter;

    /**
     * Creates an AdapterListUpdateCallback that will dispatch update events to the given adapter.
     *
     * @param adapter
     *            The Adapter to send updates to.
     */
    public AdapterListUpdateCallback(@NonNull RecyclerView.Adapter adapter) {
        mAdapter = adapter;
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
