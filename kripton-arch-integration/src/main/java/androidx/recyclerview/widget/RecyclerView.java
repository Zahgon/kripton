package androidx.recyclerview.widget;

/*
 * Copyright 2018 The Android Open Source Project
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
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A flexible view for providing a limited window into a large data set.
 *
 * <h3>Glossary of terms:</h3>
 *
 * <ul>
 *     <li><em>Adapter:</em> A subclass of {@link Adapter} responsible for providing views
 *     that represent items in a data set.</li>
 *     <li><em>Position:</em> The position of a data item within an <em>Adapter</em>.</li>
 *     <li><em>Index:</em> The index of an attached child view as used in a call to
 *     {@link ViewGroup#getChildAt}. Contrast with <em>Position.</em></li>
 *     <li><em>Binding:</em> The process of preparing a child view to display data corresponding
 *     to a <em>position</em> within the adapter.</li>
 *     <li><em>Recycle (view):</em> A view previously used to display data for a specific adapter
 *     position may be placed in a cache for later reuse to display the same type of data again
 *     later. This can drastically improve performance by skipping initial layout inflation
 *     or construction.</li>
 *     <li><em>Scrap (view):</em> A child view that has entered into a temporarily detached
 *     state during layout. Scrap views may be reused without becoming fully detached
 *     from the parent RecyclerView, either unmodified if no rebinding is required or modified
 *     by the adapter if the view was considered <em>dirty</em>.</li>
 *     <li><em>Dirty (view):</em> A child view that must be rebound by the adapter before
 *     being displayed.</li>
 * </ul>
 *
 * <h3>Positions in RecyclerView:</h3>
 * <p>
 * RecyclerView introduces an additional level of abstraction between the {@link Adapter} and
 * {@link LayoutManager} to be able to detect data set changes in batches during a layout
 * calculation. This saves LayoutManager from tracking adapter changes to calculate animations.
 * It also helps with performance because all view bindings happen at the same time and unnecessary
 * bindings are avoided.
 * <p>
 * For this reason, there are two types of <code>position</code> related methods in RecyclerView:
 * <ul>
 *     <li>layout position: Position of an item in the latest layout calculation. This is the
 *     position from the LayoutManager's perspective.</li>
 *     <li>adapter position: Position of an item in the adapter. This is the position from
 *     the Adapter's perspective.</li>
 * </ul>
 * <p>
 * These two positions are the same except the time between dispatching <code>adapter.notify*
 * </code> events and calculating the updated layout.
 * <p>
 * Methods that return or receive <code>*LayoutPosition*</code> use position as of the latest
 * layout calculation (e.g. {@link ViewHolder#getLayoutPosition()},
 * {@link #findViewHolderForLayoutPosition(int)}). These positions include all changes until the
 * last layout calculation. You can rely on these positions to be consistent with what user is
 * currently seeing on the screen. For example, if you have a list of items on the screen and user
 * asks for the 5<sup>th</sup> element, you should use these methods as they'll match what user
 * is seeing.
 * <p>
 * The other set of position related methods are in the form of
 * <code>*AdapterPosition*</code>. (e.g. {@link ViewHolder#getAdapterPosition()},
 * {@link #findViewHolderForAdapterPosition(int)}) You should use these methods when you need to
 * work with up-to-date adapter positions even if they may not have been reflected to layout yet.
 * For example, if you want to access the item in the adapter on a ViewHolder click, you should use
 * {@link ViewHolder#getAdapterPosition()}. Beware that these methods may not be able to calculate
 * adapter positions if {@link Adapter#notifyDataSetChanged()} has been called and new layout has
 * not yet been calculated. For this reasons, you should carefully handle {@link #NO_POSITION} or
 * <code>null</code> results from these methods.
 * <p>
 * When writing a {@link LayoutManager} you almost always want to use layout positions whereas when
 * writing an {@link Adapter}, you probably want to use adapter positions.
 * <p>
 * <h3>Presenting Dynamic Data</h3>
 * To display updatable data in a RecyclerView, your adapter needs to signal inserts, moves, and
 * deletions to RecyclerView. You can build this yourself by manually calling
 * {@code adapter.notify*} methods when content changes, or you can use one of the easier solutions
 * RecyclerView provides:
 * <p>
 * <h4>List diffing with DiffUtil</h4>
 * If your RecyclerView is displaying a list that is re-fetched from scratch for each update (e.g.
 * from the network, or from a database), {@link DiffUtil} can calculate the difference between
 * versions of the list. {@code DiffUtil} takes both lists as input and computes the difference,
 * which can be passed to RecyclerView to trigger minimal animations and updates to keep your UI
 * performant, and animations meaningful. This approach requires that each list is represented in
 * memory with immutable content, and relies on receiving updates as new instances of lists. This
 * approach is also ideal if your UI layer doesn't implement sorting, it just presents the data in
 * the order it's given.
 * <p>
 * The best part of this approach is that it extends to any arbitrary changes - item updates,
 * moves, addition and removal can all be computed and handled the same way. Though you do have
 * to keep two copies of the list in memory while diffing, and must avoid mutating them, it's
 * possible to share unmodified elements between list versions.
 * <p>
 * There are three primary ways to do this for RecyclerView. We recommend you start with
 * {@link ListAdapter}, the higher-level API that builds in {@link List} diffing on a background
 * thread, with minimal code. {@link AsyncListDiffer} also provides this behavior, but without
 * defining an Adapter to subclass. If you want more control, {@link DiffUtil} is the lower-level
 * API you can use to compute the diffs yourself. Each approach allows you to specify how diffs
 * should be computed based on item data.
 * <p>
 * <h4>List mutation with SortedList</h4>
 * If your RecyclerView receives updates incrementally, e.g. item X is inserted, or item Y is
 * removed, you can use {@link SortedList} to manage your list. You define how to order items,
 * and it will automatically trigger update signals that RecyclerView can use. SortedList works
 * if you only need to handle insert and remove events, and has the benefit that you only ever
 * need to have a single copy of the list in memory. It can also compute differences with
 * {@link SortedList#replaceAll(Object[])}, but this method is more limited than the list diffing
 * behavior above.
 * <p>
 * <h4>Paging Library</h4>
 * The <a href="https://developer.android.com/topic/libraries/architecture/paging/">Paging
 * library</a> extends the diff-based approach to additionally support paged loading. It provides
 * the {@link androidx.paging.PagedList} class that operates as a self-loading list, provided a
 * source of data like a database, or paginated network API. It provides convenient list diffing
 * support out of the box, similar to {@code ListAdapter} and {@code AsyncListDiffer}. For more
 * information about the Paging library, see the
 * <a href="https://developer.android.com/topic/libraries/architecture/paging/">library
 * documentation</a>.
 *
 * {@link androidx.recyclerview.R.attr#layoutManager}
 */
public class RecyclerView extends ViewGroup {

    static final String TAG = "RecyclerView";

    static final boolean DEBUG = false;

    static final boolean VERBOSE_TRACING = false;

    private static final int[] NESTED_SCROLLING_ATTRS = { 16843830 /* android.R.attr.nestedScrollingEnabled */
    };

    private static final int[] CLIP_TO_PADDING_ATTR = { android.R.attr.clipToPadding };

    /**
     * On Kitkat and JB MR2, there is a bug which prevents DisplayList from being invalidated if
     * a View is two levels deep(wrt to ViewHolder.itemView). DisplayList can be invalidated by
     * setting View's visibility to INVISIBLE when View is detached. On Kitkat and JB MR2, Recycler
     * recursively traverses itemView and invalidates display list for each ViewGroup that matches
     * this criteria.
     */
    static final boolean FORCE_INVALIDATE_DISPLAY_LIST = Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20;

    /**
     * On M+, an unspecified measure spec may include a hint which we can use. On older platforms,
     * this value might be garbage. To save LayoutManagers from it, RecyclerView sets the size to
     * 0 when mode is unspecified.
     */
    static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC = Build.VERSION.SDK_INT >= 23;

    static final boolean POST_UPDATES_ON_ANIMATION = Build.VERSION.SDK_INT >= 16;

    /**
     * On L+, with RenderThread, the UI thread has idle time after it has passed a frame off to
     * RenderThread but before the next frame begins. We schedule prefetch work in this window.
     */
    static final boolean ALLOW_THREAD_GAP_WORK = Build.VERSION.SDK_INT >= 21;

    /**
     * FocusFinder#findNextFocus is broken on ICS MR1 and older for View.FOCUS_BACKWARD direction.
     * We convert it to an absolute direction such as FOCUS_DOWN or FOCUS_LEFT.
     */
    private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION = Build.VERSION.SDK_INT <= 15;

    /**
     * on API 15-, a focused child can still be considered a focused child of RV even after
     * it's being removed or its focusable flag is set to false. This is because when this focused
     * child is detached, the reference to this child is not removed in clearFocus. API 16 and above
     * properly handle this case by calling ensureInputFocusOnFirstFocusable or rootViewRequestFocus
     * to request focus on a new child, which will clear the focus on the old (detached) child as a
     * side-effect.
     */
    private static final boolean IGNORE_DETACHED_FOCUSED_CHILD = Build.VERSION.SDK_INT <= 15;

    public @interface Orientation {
    }

    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

    public static final int VERTICAL = LinearLayout.VERTICAL;

    static final int DEFAULT_ORIENTATION = VERTICAL;

    public static final int NO_POSITION = -1;

    public static final long NO_ID = -1;

    public static final int INVALID_TYPE = -1;

    /**
     * Constant for use with {@link #setScrollingTouchSlop(int)}. Indicates
     * that the RecyclerView should use the standard touch slop for smooth,
     * continuous scrolling.
     */
    public static final int TOUCH_SLOP_DEFAULT = 0;

    /**
     * Constant for use with {@link #setScrollingTouchSlop(int)}. Indicates
     * that the RecyclerView should use the standard touch slop for scrolling
     * widgets that snap to a page or other coarse-grained barrier.
     */
    public static final int TOUCH_SLOP_PAGING = 1;

    static final int UNDEFINED_DURATION = Integer.MIN_VALUE;

    static final int MAX_SCROLL_DURATION = 2000;

    /**
     * RecyclerView is calculating a scroll.
     * If there are too many of these in Systrace, some Views inside RecyclerView might be causing
     * it. Try to avoid using EditText, focusable views or handle them with care.
     */
    static final String TRACE_SCROLL_TAG = "RV Scroll";

    /**
     * OnLayout has been called by the View system.
     * If this shows up too many times in Systrace, make sure the children of RecyclerView do not
     * update themselves directly. This will cause a full re-layout but when it happens via the
     * Adapter notifyItemChanged, RecyclerView can avoid full layout calculation.
     */
    private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";

    /**
     * NotifyDataSetChanged or equal has been called.
     * If this is taking a long time, try sending granular notify adapter changes instead of just
     * calling notifyDataSetChanged or setAdapter / swapAdapter. Adding stable ids to your adapter
     * might help.
     */
    private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";

    /**
     * RecyclerView is doing a layout for partial adapter updates (we know what has changed)
     * If this is taking a long time, you may have dispatched too many Adapter updates causing too
     * many Views being rebind. Make sure all are necessary and also prefer using notify*Range
     * methods.
     */
    private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";

    /**
     * RecyclerView is rebinding a View.
     * If this is taking a lot of time, consider optimizing your layout or make sure you are not
     * doing extra operations in onBindViewHolder call.
     */
    static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";

    /**
     * RecyclerView is attempting to pre-populate off screen views.
     */
    static final String TRACE_PREFETCH_TAG = "RV Prefetch";

    /**
     * RecyclerView is attempting to pre-populate off screen itemviews within an off screen
     * RecyclerView.
     */
    static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";

    /**
     * RecyclerView is creating a new View.
     * If too many of these present in Systrace:
     * - There might be a problem in Recycling (e.g. custom Animations that set transient state and
     * prevent recycling or ItemAnimator not implementing the contract properly. ({@link
     * > Adapter#onFailedToRecycleView(ViewHolder)})
     *
     * - There might be too many item view types.
     * > Try merging them
     *
     * - There might be too many itemChange animations and not enough space in RecyclerPool.
     * >Try increasing your pool size and item cache size.
     */
    static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";

    private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[] { Context.class, AttributeSet.class, int.class, int.class };

    private final RecyclerViewDataObserver mObserver = new RecyclerViewDataObserver();

    final Recycler mRecycler = new Recycler();

    /**
     * Prior to L, there is no way to query this variable which is why we override the setter and
     * track it here.
     */
    boolean mClipToPadding;

    /**
     * Note: this Runnable is only ever posted if:
     * 1) We've been through first layout
     * 2) We know we have a fixed size (mHasFixedSize)
     * 3) We're attached
     */
    final Runnable mUpdateChildViewsRunnable = new Runnable() {

        @Override
        public void run() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    final Rect mTempRect = new Rect();

    private final Rect mTempRect2 = new Rect();

    final RectF mTempRectF = new RectF();

    Adapter mAdapter;

    RecyclerListener mRecyclerListener;

    final ArrayList<ItemDecoration> mItemDecorations = new ArrayList<>();

    private final ArrayList<OnItemTouchListener> mOnItemTouchListeners = new ArrayList<>();

    private OnItemTouchListener mInterceptingOnItemTouchListener;

    boolean mIsAttached;

    boolean mHasFixedSize;

    boolean mEnableFastScroller;

    boolean mFirstLayoutComplete;

    /**
     * The current depth of nested calls to {@link #startInterceptRequestLayout()} (number of
     * calls to {@link #startInterceptRequestLayout()} - number of calls to
     * {@link #stopInterceptRequestLayout(boolean)} .  This is used to signal whether we
     * should defer layout operations caused by layout requests from children of
     * {@link RecyclerView}.
     */
    private int mInterceptRequestLayoutDepth = 0;

    /**
     * True if a call to requestLayout was intercepted and prevented from executing like normal and
     * we plan on continuing with normal execution later.
     */
    boolean mLayoutWasDefered;

    boolean mLayoutSuppressed;

    private boolean mIgnoreMotionEventTillDown;

    // binary OR of change events that were eaten during a layout or scroll.
    private int mEatenAccessibilityChangeFlags;

    boolean mAdapterUpdateDuringMeasure;

    private final AccessibilityManager mAccessibilityManager;

    private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;

    /**
     * True after an event occurs that signals that the entire data set has changed. In that case,
     * we cannot run any animations since we don't know what happened until layout.
     *
     * Attached items are invalid until next layout, at which point layout will animate/replace
     * items as necessary, building up content from the (effectively) new adapter from scratch.
     *
     * Cached items must be discarded when setting this to true, so that the cache may be freely
     * used by prefetching until the next layout occurs.
     *
     * @see #processDataSetCompletelyChanged(boolean)
     */
    boolean mDataSetHasChangedAfterLayout = false;

    /**
     * True after the data set has completely changed and
     * {@link LayoutManager#onItemsChanged(RecyclerView)} should be called during the subsequent
     * measure/layout.
     *
     * @see #processDataSetCompletelyChanged(boolean)
     */
    boolean mDispatchItemsChangedEvent = false;

    /**
     * This variable is incremented during a dispatchLayout and/or scroll.
     * Some methods should not be called during these periods (e.g. adapter data change).
     * Doing so will create hard to find bugs so we better check it and throw an exception.
     *
     * @see #assertInLayoutOrScroll(String)
     * @see #assertNotInLayoutOrScroll(String)
     */
    private int mLayoutOrScrollCounter = 0;

    /**
     * Similar to mLayoutOrScrollCounter but logs a warning instead of throwing an exception
     * (for API compatibility).
     * <p>
     * It is a bad practice for a developer to update the data in a scroll callback since it is
     * potentially called during a layout.
     */
    private int mDispatchScrollCounter = 0;

    private EdgeEffect mLeftGlow, mTopGlow, mRightGlow, mBottomGlow;

    private static final int INVALID_POINTER = -1;

    /**
     * The RecyclerView is not currently scrolling.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_IDLE = 0;

    /**
     * The RecyclerView is currently being dragged by outside input such as user touch input.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * The RecyclerView is currently animating to a final position while not under
     * outside control.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_SETTLING = 2;

    static final long FOREVER_NS = Long.MAX_VALUE;

    // Touch/scrolling handling
    private int mScrollState = SCROLL_STATE_IDLE;

    private int mScrollPointerId = INVALID_POINTER;

    private VelocityTracker mVelocityTracker;

    private int mInitialTouchX;

    private int mInitialTouchY;

    private int mLastTouchX;

    private int mLastTouchY;

    private int mTouchSlop;

    private OnFlingListener mOnFlingListener;

    private final int mMinFlingVelocity;

    private final int mMaxFlingVelocity;

    // This value is used when handling rotary encoder generic motion events.
    private float mScaledHorizontalScrollFactor = Float.MIN_VALUE;

    private float mScaledVerticalScrollFactor = Float.MIN_VALUE;

    private boolean mPreserveFocusAfterLayout = true;

    final State mState = new State();

    private OnScrollListener mScrollListener;

    private List<OnScrollListener> mScrollListeners;

    // For use in item animations
    boolean mItemsAddedOrRemoved = false;

    boolean mItemsChanged = false;

    boolean mPostedAnimatorRunner = false;

    private ChildDrawingOrderCallback mChildDrawingOrderCallback;

    // simple array to keep min and max child position during a layout calculation
    // preserved not to create a new one in each layout pass
    private final int[] mMinMaxLayoutPositions = new int[2];

    private final int[] mScrollOffset = new int[2];

    private final int[] mNestedOffsets = new int[2];

    // Reusable int array to be passed to method calls that mutate it in order to "return" two ints.
    final int[] mReusableIntPair = new int[2];

    static final Interpolator sQuinticInterpolator = new Interpolator() {

        @Override
        public float getInterpolation(float t) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    };

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, CLIP_TO_PADDING_ATTR, defStyle, 0);
            mClipToPadding = a.getBoolean(0, true);
            a.recycle();
        } else {
            mClipToPadding = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == View.OVER_SCROLL_NEVER);
        initAdapterManager();
        initChildrenHelper();
        initAutofill();
        // If not explicitly specified this view is important for accessibility.
        mAccessibilityManager = (AccessibilityManager) getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        // Create the layoutManager if specified.
        boolean nestedScrollingEnabled = true;
        // Re-set whether nested scrolling is enabled so that it is set on all API levels
        setNestedScrollingEnabled(nestedScrollingEnabled);
    }

    String exceptionLabel() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If not explicitly specified, this view and its children don't support autofill.
     * <p>
     * This is done because autofill's means of uniquely identifying views doesn't work out of the
     * box with View recycling.
     */
    @SuppressLint("InlinedApi")
    private void initAutofill() {
    }

    /**
     * Instantiate and set a LayoutManager, if specified in the attributes.
     */
    private void createLayoutManager(Context context, String className, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (className != null) {
            className = className.trim();
            if (!className.isEmpty()) {
                className = getFullClassName(context, className);
            }
        }
    }

    private String getFullClassName(Context context, String className) {
        if (className.charAt(0) == '.') {
            return context.getPackageName() + className;
        }
        if (className.contains(".")) {
            return className;
        }
        return RecyclerView.class.getPackage().getName() + '.' + className;
    }

    private void initChildrenHelper() {
    }

    void initAdapterManager() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasFixedSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void setClipToPadding(boolean clipToPadding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean getClipToPadding() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setScrollingTouchSlop(int slopConstant) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setAdapter(Adapter adapter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void removeAndRecycleViews() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Replaces the current adapter with the new one and triggers listeners.
     * @param adapter The new adapter
     * @param compatibleWithPrevious If true, the new adapter is using the same View Holders and
     *                               item types with the current adapter (helps us avoid cache
     *                               invalidation).
     * @param removeAndRecycleViews  If true, we'll remove and recycle all existing views. If
     *                               compatibleWithPrevious is false, this parameter is ignored.
     */
    private void setAdapterInternal(Adapter adapter, boolean compatibleWithPrevious, boolean removeAndRecycleViews) {
    }

    /**
     * Retrieves the previously set adapter or null if no adapter is set.
     *
     * @return The previously set adapter
     * @see #setAdapter(Adapter)
     */
    public Adapter getAdapter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setRecyclerListener(RecyclerListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int getBaseline() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clearOnChildAttachStateChangeListeners() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setOnFlingListener(OnFlingListener onFlingListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public OnFlingListener getOnFlingListener() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds a view to the animatingViews list.
     * mAnimatingViews holds the child views that are currently being kept around
     * purely for the purpose of being animated out of view. They are drawn as a regular
     * part of the child list of the RecyclerView, but they are invisible to the LayoutManager
     * as they are managed separately from the regular child views.
     * @param viewHolder The ViewHolder to be removed
     */
    private void addAnimatingView(ViewHolder viewHolder) {
    }

    boolean removeAnimatingView(View view) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve this RecyclerView's {@link RecycledViewPool}. This method will never return null;
     * if no pool is set for this view a new one will be created. See
     * {@link #setRecycledViewPool(RecycledViewPool) setRecycledViewPool} for more information.
     *
     * @return The pool used to store recycled item views for reuse.
     * @see #setRecycledViewPool(RecycledViewPool)
     */
    public RecycledViewPool getRecycledViewPool() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setRecycledViewPool(RecycledViewPool pool) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setViewCacheExtension(ViewCacheExtension extension) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setItemViewCacheSize(int size) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getScrollState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addItemDecoration(ItemDecoration decor, int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addItemDecoration(ItemDecoration decor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns an {@link ItemDecoration} previously added to this RecyclerView.
     *
     * @param index The index position of the desired ItemDecoration.
     * @return the ItemDecoration at index position
     * @throws IndexOutOfBoundsException on invalid index
     */
    public ItemDecoration getItemDecorationAt(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getItemDecorationCount() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeItemDecorationAt(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeItemDecoration(ItemDecoration decor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set a listener that will be notified of any changes in scroll state or position.
     *
     * @param listener Listener to set or null to clear
     *
     * @deprecated Use {@link #addOnScrollListener(OnScrollListener)} and
     *             {@link #removeOnScrollListener(OnScrollListener)}
     */
    @Deprecated
    public void setOnScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }

    public void addOnScrollListener(OnScrollListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeOnScrollListener(OnScrollListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void clearOnScrollListeners() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void scrollToPosition(int position) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void jumpToPositionForSmoothScroller(int position) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void smoothScrollToPosition(int position) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void scrollTo(int x, int y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void scrollBy(int x, int y) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void scrollStep(int dx, int dy, int[] consumed) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void consumePendingUpdateOperations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @return True if an existing view holder needs to be updated
     */
    private boolean hasUpdatedView() {
        return false;
    }

    boolean scrollByInternal(int x, int y, MotionEvent ev) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeHorizontalScrollOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeHorizontalScrollExtent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeHorizontalScrollRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeVerticalScrollOffset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeVerticalScrollExtent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int computeVerticalScrollRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void startInterceptRequestLayout() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void stopInterceptRequestLayout(boolean performLayoutChildren) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public final void suppressLayout(boolean suppress) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public final boolean isLayoutSuppressed() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Enable or disable layout and scroll.  After <code>setLayoutFrozen(true)</code> is called,
     * Layout requests will be postponed until <code>setLayoutFrozen(false)</code> is called;
     * child views are not updated when RecyclerView is frozen, {@link #smoothScrollBy(int, int)},
     * {@link #scrollBy(int, int)}, {@link #scrollToPosition(int)} and
     * {@link #smoothScrollToPosition(int)} are dropped; TouchEvents and GenericMotionEvents are
     * dropped; {@link LayoutManager#onFocusSearchFailed(View, int, Recycler, State)} will not be
     * called.
     *
     * <p>
     * <code>setLayoutFrozen(true)</code> does not prevent app from directly calling {@link
     * LayoutManager#scrollToPosition(int)}, {@link LayoutManager#smoothScrollToPosition(
     * RecyclerView, State, int)}.
     * <p>
     * {@link #setAdapter(Adapter)} and {@link #swapAdapter(Adapter, boolean)} will automatically
     * stop frozen.
     * <p>
     * Note: Running ItemAnimator is not stopped automatically,  it's caller's
     * responsibility to call ItemAnimator.end().
     *
     * @param frozen   true to freeze layout and scroll, false to re-enable.
     *
     * @deprecated Use {@link #suppressLayout(boolean)}.
     */
    @Deprecated
    public void setLayoutFrozen(boolean frozen) {
        suppressLayout(frozen);
    }

    /**
     * @return true if layout and scroll are frozen
     *
     * @deprecated Use {@link #isLayoutSuppressed()}.
     */
    @Deprecated
    public boolean isLayoutFrozen() {
        return isLayoutSuppressed();
    }

    /**
     * @deprecated Use {@link #setItemAnimator(ItemAnimator)} ()}.
     */
    @Deprecated
    @Override
    public void setLayoutTransition(LayoutTransition transition) {
        if (transition == null) {
            super.setLayoutTransition(null);
        } else {
            throw new IllegalArgumentException("Providing a LayoutTransition into RecyclerView is " + "not supported. Please use setItemAnimator() instead for animating changes " + "to the items in this RecyclerView");
        }
    }

    public void smoothScrollBy(int dx, int dy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void smoothScrollBy(int dx, int dy, Interpolator interpolator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean fling(int velocityX, int velocityY) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Similar to {@link #stopScroll()} but does not set the state.
     */
    private void stopScrollersInternal() {
    }

    public int getMinFlingVelocity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getMaxFlingVelocity() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Apply a pull to relevant overscroll glow effects
     */
    private void pullGlows(float x, float overscrollX, float y, float overscrollY) {
    }

    private void releaseGlows() {
        boolean needsInvalidate = false;
        if (mLeftGlow != null) {
            mLeftGlow.onRelease();
            needsInvalidate = mLeftGlow.isFinished();
        }
        if (mTopGlow != null) {
            mTopGlow.onRelease();
            needsInvalidate |= mTopGlow.isFinished();
        }
        if (mRightGlow != null) {
            mRightGlow.onRelease();
            needsInvalidate |= mRightGlow.isFinished();
        }
        if (mBottomGlow != null) {
            mBottomGlow.onRelease();
            needsInvalidate |= mBottomGlow.isFinished();
        }
        if (needsInvalidate) {
        }
    }

    void considerReleasingGlowsOnScroll(int dx, int dy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void absorbGlows(int velocityX, int velocityY) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void ensureLeftGlow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void ensureRightGlow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void ensureTopGlow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void ensureBottomGlow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void invalidateGlows() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public View focusSearch(View focused, int direction) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if the new focus candidate is a good enough candidate such that RecyclerView will
     * assign it as the next focus View instead of letting view hierarchy decide.
     * A good candidate means a View that is aligned in the focus direction wrt the focused View
     * and is not the RecyclerView itself.
     * When this method returns false, RecyclerView will let the parent make the decision so the
     * same View may still get the focus as a result of that search.
     */
    private boolean isPreferredNextFocus(View focused, View next, int direction) {
        return false;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Requests that the given child of the RecyclerView be positioned onto the screen. This method
     * can be called for both unfocusable and focusable child views. For unfocusable child views,
     * the {@param focused} parameter passed is null, whereas for a focusable child, this parameter
     * indicates the actual descendant view within this child view that holds the focus.
     * @param child The child view of this RecyclerView that wants to come onto the screen.
     * @param focused The descendant view that actually has the focus if child is focusable, null
     *                otherwise.
     */
    private void requestChildOnScreen(View child, View focused) {
    }

    @Override
    public boolean requestChildRectangleOnScreen(View child, Rect rect, boolean immediate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onAttachedToWindow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onDetachedFromWindow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isAttachedToWindow() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void assertInLayoutOrScroll(String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void assertNotInLayoutOrScroll(String message) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void addOnItemTouchListener(OnItemTouchListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void removeOnItemTouchListener(OnItemTouchListener listener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Dispatches the motion event to the intercepting OnItemTouchListener or provides opportunity
     * for OnItemTouchListeners to intercept.
     * @param e The MotionEvent
     * @return True if handled by an intercepting OnItemTouchListener.
     */
    private boolean dispatchToOnItemTouchListeners(MotionEvent e) {
        // OnItemTouchListeners should receive calls to their methods in the same pattern that
        // ViewGroups do. That pattern is a bit confusing, which in turn makes the below code a
        // bit confusing.  Here are rules for the pattern:
        //
        // 1. A single MotionEvent should not be passed to either OnInterceptTouchEvent or
        // OnTouchEvent twice.
        // 2. ACTION_DOWN MotionEvents may be passed to both onInterceptTouchEvent and
        // onTouchEvent.
        // 3. All other MotionEvents should be passed to either onInterceptTouchEvent or
        // onTouchEvent, not both.
        // Side Note: If we are to truly mimic how MotionEvents work in the view system, for every
        // MotionEvent, any OnItemTouchListener that is before the intercepting OnItemTouchEvent
        // should still have a chance to intercept, and if it does, the previously intercepting
        // OnItemTouchEvent should get an ACTION_CANCEL event.
        if (mInterceptingOnItemTouchListener == null) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                return false;
            }
            return findInterceptingOnItemTouchListener(e);
        } else {
            mInterceptingOnItemTouchListener.onTouchEvent(this, e);
            final int action = e.getAction();
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                mInterceptingOnItemTouchListener = null;
            }
            return true;
        }
    }

    /**
     * Looks for an OnItemTouchListener that wants to intercept.
     *
     * <p>Passes the MotionEvent to all registered OnItemTouchListeners one at a time. If one wants
     * to intercept and the action is not ACTION_UP or ACTION_CANCEL, saves the intercepting
     * OnItemTouchListener and immediately returns true. If none want to intercept
     * or the action is ACTION_UP or ACTION_CANCEL, returns false.
     *
     * @param e The MotionEvent
     * @return true if an OnItemTouchListener is saved as intercepting.
     */
    private boolean findInterceptingOnItemTouchListener(MotionEvent e) {
        int action = e.getAction();
        final int listenerCount = mOnItemTouchListeners.size();
        for (int i = 0; i < listenerCount; i++) {
            final OnItemTouchListener listener = mOnItemTouchListeners.get(i);
            if (listener.onInterceptTouchEvent(this, e) && action != MotionEvent.ACTION_UP && action != MotionEvent.ACTION_CANCEL) {
                mInterceptingOnItemTouchListener = listener;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void resetScroll() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
        }
        releaseGlows();
    }

    private void onPointerUp(MotionEvent e) {
        final int actionIndex = e.getActionIndex();
        if (e.getPointerId(actionIndex) == mScrollPointerId) {
            // Pick a new pointer to pick up the slack.
            final int newIndex = actionIndex == 0 ? 1 : 0;
            mScrollPointerId = e.getPointerId(newIndex);
            mInitialTouchX = mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
            mInitialTouchY = mLastTouchY = (int) (e.getY(newIndex) + 0.5f);
        }
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void defaultOnMeasure(int widthSpec, int heightSpec) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void onEnterLayoutOrScroll() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void onExitLayoutOrScroll() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void onExitLayoutOrScroll(boolean enableChangeEvents) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isAccessibilityEnabled() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void dispatchContentChangedIfNecessary() {
        final int flags = mEatenAccessibilityChangeFlags;
        mEatenAccessibilityChangeFlags = 0;
        if (flags != 0 && isAccessibilityEnabled()) {
            final AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED);
            sendAccessibilityEventUnchecked(event);
        }
    }

    public boolean isComputingLayout() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean shouldDeferAccessibilityEvent(AccessibilityEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Consumes adapter updates and calculates which type of animations we want to run.
     * Called in onMeasure and dispatchLayout.
     * <p>
     * This method may process only the pre-layout state of updates or all of them.
     */
    private void processAdapterUpdatesAndSetAnimationFlags() {
    }

    void dispatchLayout() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void saveFocusInfo() {
        View child = null;
        if (mPreserveFocusAfterLayout && hasFocus() && mAdapter != null) {
            child = getFocusedChild();
        }
        final ViewHolder focusedVh = child == null ? null : findContainingViewHolder(child);
        if (focusedVh == null) {
            resetFocusInfo();
        } else {
            mState.mFocusedItemId = mAdapter.hasStableIds() ? focusedVh.getItemId() : NO_ID;
            // mFocusedItemPosition should hold the current adapter position of the previously
            // focused item. If the item is removed, we store the previous adapter position of the
            // removed item.
            mState.mFocusedItemPosition = mDataSetHasChangedAfterLayout ? NO_POSITION : (focusedVh.isRemoved() ? focusedVh.mOldPosition : focusedVh.getAdapterPosition());
            mState.mFocusedSubChildId = getDeepestFocusedViewWithId(focusedVh.itemView);
        }
    }

    private void resetFocusInfo() {
        mState.mFocusedItemId = NO_ID;
        mState.mFocusedItemPosition = NO_POSITION;
        mState.mFocusedSubChildId = View.NO_ID;
    }

    private int getDeepestFocusedViewWithId(View view) {
        int lastKnownId = view.getId();
        while (!view.isFocused() && view instanceof ViewGroup && view.hasFocus()) {
            view = ((ViewGroup) view).getFocusedChild();
            final int id = view.getId();
            if (id != View.NO_ID) {
                lastKnownId = view.getId();
            }
        }
        return lastKnownId;
    }

    private boolean didChildRangeChange(int minPositionPreLayout, int maxPositionPreLayout) {
        return false;
    }

    @Override
    protected void removeDetachedView(View child, boolean animate) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    long getChangedHolderKey(ViewHolder holder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void requestLayout() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void markItemDecorInsetsDirty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onDraw(Canvas c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isAnimating() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void saveOldPositions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void clearOldPositions() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void offsetPositionRecordsForMove(int from, int to) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void offsetPositionRecordsForRemove(int positionStart, int itemCount, boolean applyToPreLayout) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void viewRangeUpdate(int positionStart, int itemCount, Object payload) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean canReuseUpdatedViewHolder(ViewHolder viewHolder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void processDataSetCompletelyChanged(boolean dispatchItemsChanged) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void markKnownViewsInvalid() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void invalidateItemDecorations() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean getPreserveFocusAfterLayout() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void setPreserveFocusAfterLayout(boolean preserveFocusAfterLayout) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ViewHolder getChildViewHolder(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Traverses the ancestors of the given view and returns the item view that contains it and
     * also a direct child of the RecyclerView. This returned view can be used to get the
     * ViewHolder by calling {@link #getChildViewHolder(View)}.
     *
     * @param view The view that is a descendant of the RecyclerView.
     *
     * @return The direct child of the RecyclerView which contains the given view or null if the
     * provided view is not a descendant of this RecyclerView.
     *
     * @see #getChildViewHolder(View)
     * @see #findContainingViewHolder(View)
     */
    public View findContainingItemView(View view) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the ViewHolder that contains the given view.
     *
     * @param view The view that is a descendant of the RecyclerView.
     *
     * @return The ViewHolder that contains the given view or null if the provided view is not a
     * descendant of this RecyclerView.
     */
    public ViewHolder findContainingViewHolder(View view) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static ViewHolder getChildViewHolderInt(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @deprecated use {@link #getChildAdapterPosition(View)} or
     * {@link #getChildLayoutPosition(View)}.
     */
    @Deprecated
    public int getChildPosition(View child) {
        return getChildAdapterPosition(child);
    }

    public int getChildAdapterPosition(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public int getChildLayoutPosition(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public long getChildItemId(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean drawChild(Canvas canvas, View child, long drawingTime) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onChildAttachedToWindow(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onChildDetachedFromWindow(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void getDecoratedBoundsWithMargins(View view, Rect outBounds) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void getDecoratedBoundsWithMarginsInt(View view, Rect outBounds) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Rect getItemDecorInsetsForChild(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onScrolled(int dx, int dy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void dispatchOnScrolled(int hresult, int vresult) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void onScrollStateChanged(int state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void repositionShadowingViews() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private class RecyclerViewDataObserver extends AdapterDataObserver {

        RecyclerViewDataObserver() {
        }

        @Override
        public void onChanged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void triggerUpdateProcessor() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * RecycledViewPool lets you share Views between multiple RecyclerViews.
     * <p>
     * If you want to recycle views across RecyclerViews, create an instance of RecycledViewPool
     * and use {@link RecyclerView#setRecycledViewPool(RecycledViewPool)}.
     * <p>
     * RecyclerView automatically creates a pool for itself if you don't provide one.
     */
    public static class RecycledViewPool {

        private static final int DEFAULT_MAX_SCRAP = 5;

        /**
         * Tracks both pooled holders, as well as create/bind timing metadata for the given type.
         *
         * Note that this tracks running averages of create/bind time across all RecyclerViews
         * (and, indirectly, Adapters) that use this pool.
         *
         * 1) This enables us to track average create and bind times across multiple adapters. Even
         * though create (and especially bind) may behave differently for different Adapter
         * subclasses, sharing the pool is a strong signal that they'll perform similarly, per type.
         *
         * 2) If {@link #willBindInTime(int, long, long)} returns false for one view, it will return
         * false for all other views of its type for the same deadline. This prevents items
         * constructed by {@link GapWorker} prefetch from being bound to a lower priority prefetch.
         */
        static class ScrapData {

            final ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();

            int mMaxScrap = DEFAULT_MAX_SCRAP;

            long mCreateRunningAverageNs = 0;

            long mBindRunningAverageNs = 0;
        }

        SparseArray<ScrapData> mScrap = new SparseArray<>();

        private int mAttachCount = 0;

        public void clear() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void setMaxRecycledViews(int viewType, int max) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getRecycledViewCount(int viewType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Acquire a ViewHolder of the specified type from the pool, or {@code null} if none are
         * present.
         *
         * @param viewType ViewHolder type.
         * @return ViewHolder of the specified type acquired from the pool, or {@code null} if none
         * are present.
         */
        public ViewHolder getRecycledView(int viewType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        int size() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void putRecycledView(ViewHolder scrap) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        long runningAverage(long oldAverage, long newValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void factorInCreateTime(int viewType, long createTimeNs) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void factorInBindTime(int viewType, long bindTimeNs) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean willCreateInTime(int viewType, long approxCurrentNs, long deadlineNs) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean willBindInTime(int viewType, long approxCurrentNs, long deadlineNs) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void attach() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void detach() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void onAdapterChanged(Adapter oldAdapter, Adapter newAdapter, boolean compatibleWithPrevious) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private ScrapData getScrapDataForType(int viewType) {
            ScrapData scrapData = mScrap.get(viewType);
            if (scrapData == null) {
                scrapData = new ScrapData();
                mScrap.put(viewType, scrapData);
            }
            return scrapData;
        }
    }

    static RecyclerView findNestedRecyclerView(View view) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void clearNestedRecyclerViewIfNotNested(ViewHolder holder) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    long getNanoTime() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A Recycler is responsible for managing scrapped or detached item views for reuse.
     *
     * <p>A "scrapped" view is a view that is still attached to its parent RecyclerView but
     * that has been marked for removal or reuse.</p>
     *
     * <p>Typical use of a Recycler by a {@link LayoutManager} will be to obtain views for
     * an adapter's data set representing the data at a given position or item ID.
     * If the view to be reused is considered "dirty" the adapter will be asked to rebind it.
     * If not, the view can be quickly reused by the LayoutManager with no further work.
     * Clean views that have not {@link android.view.View#isLayoutRequested() requested layout}
     * may be repositioned by a LayoutManager without remeasurement.</p>
     */
    public final class Recycler {

        public void clear() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * ViewCacheExtension is a helper class to provide an additional layer of view caching that can
     * be controlled by the developer.
     * <p>
     * When {@link Recycler#getViewForPosition(int)} is called, Recycler checks attached scrap and
     * first level cache to find a matching View. If it cannot find a suitable View, Recycler will
     * call the {@link #getViewForPositionAndType(Recycler, int, int)} before checking
     * {@link RecycledViewPool}.
     * <p>
     * Note that, Recycler never sends Views to this method to be cached. It is developers
     * responsibility to decide whether they want to keep their Views in this custom cache or let
     * the default recycling policy handle it.
     */
    public abstract static class ViewCacheExtension {

        /**
         * Returns a View that can be binded to the given Adapter position.
         * <p>
         * This method should <b>not</b> create a new View. Instead, it is expected to return
         * an already created View that can be re-used for the given type and position.
         * If the View is marked as ignored, it should first call
         * {@link LayoutManager#stopIgnoringView(View)} before returning the View.
         * <p>
         * RecyclerView will re-bind the returned View to the position if necessary.
         *
         * @param recycler The Recycler that can be used to bind the View
         * @param position The adapter position
         * @param type     The type of the View, defined by adapter
         * @return A View that is bound to the given position or NULL if there is no View to re-use
         * @see LayoutManager#ignoreView(View)
         */
        public abstract View getViewForPositionAndType(Recycler recycler, int position, int type);
    }

    /**
     * Base class for an Adapter
     *
     * <p>Adapters provide a binding from an app-specific data set to views that are displayed
     * within a {@link RecyclerView}.</p>
     *
     * @param <VH> A class that extends ViewHolder that will be used by the adapter.
     */
    public abstract static class Adapter<VH extends ViewHolder> {

        private final AdapterDataObservable mObservable = new AdapterDataObservable();

        private boolean mHasStableIds = false;

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent The ViewGroup into which the new View will be added after it is bound to
         *               an adapter position.
         * @param viewType The view type of the new View.
         *
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link android.widget.ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         *
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder The ViewHolder which should be updated to represent the contents of the
         *        item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        public abstract void onBindViewHolder(VH holder, int position);

        public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * This method calls {@link #onCreateViewHolder(ViewGroup, int)} to create a new
         * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
         *
         * @see #onCreateViewHolder(ViewGroup, int)
         */
        public final VH createViewHolder(ViewGroup parent, int viewType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void bindViewHolder(VH holder, int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getItemViewType(int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void setHasStableIds(boolean hasStableIds) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public long getItemId(int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        public abstract int getItemCount();

        public final boolean hasStableIds() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onViewRecycled(VH holder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean onFailedToRecycleView(VH holder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onViewAttachedToWindow(VH holder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onViewDetachedFromWindow(VH holder) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final boolean hasObservers() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyDataSetChanged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemChanged(int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemChanged(int position, Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemRangeChanged(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemInserted(int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemMoved(int fromPosition, int toPosition) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemRangeInserted(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemRemoved(int position) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void notifyItemRangeRemoved(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    void dispatchChildDetached(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void dispatchChildAttached(View child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * An ItemDecoration allows the application to add a special drawing and layout offset
     * to specific item views from the adapter's data set. This can be useful for drawing dividers
     * between items, highlights, visual grouping boundaries and more.
     *
     * <p>All ItemDecorations are drawn in the order they were added, before the item
     * views (in {@link ItemDecoration#onDraw(Canvas, RecyclerView, RecyclerView.State) onDraw()}
     * and after the items (in {@link ItemDecoration#onDrawOver(Canvas, RecyclerView,
     * RecyclerView.State)}.</p>
     */
    public abstract static class ItemDecoration {

        public void onDraw(Canvas c, RecyclerView parent, State state) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @deprecated
         * Override {@link #onDraw(Canvas, RecyclerView, RecyclerView.State)}
         */
        @Deprecated
        public void onDraw(Canvas c, RecyclerView parent) {
        }

        public void onDrawOver(Canvas c, RecyclerView parent, State state) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @deprecated
         * Override {@link #onDrawOver(Canvas, RecyclerView, RecyclerView.State)}
         */
        @Deprecated
        public void onDrawOver(Canvas c, RecyclerView parent) {
        }

        /**
         * @deprecated
         * Use {@link #getItemOffsets(Rect, View, RecyclerView, State)}
         */
        @Deprecated
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            outRect.set(0, 0, 0, 0);
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * An OnItemTouchListener allows the application to intercept touch events in progress at the
     * view hierarchy level of the RecyclerView before those touch events are considered for
     * RecyclerView's own scrolling behavior.
     *
     * <p>This can be useful for applications that wish to implement various forms of gestural
     * manipulation of item views within the RecyclerView. OnItemTouchListeners may intercept
     * a touch interaction already in progress even if the RecyclerView is already handling that
     * gesture stream itself for the purposes of scrolling.</p>
     *
     * @see SimpleOnItemTouchListener
     */
    public interface OnItemTouchListener {

        /**
         * Silently observe and/or take over touch events sent to the RecyclerView
         * before they are handled by either the RecyclerView itself or its child views.
         *
         * <p>The onInterceptTouchEvent methods of each attached OnItemTouchListener will be run
         * in the order in which each listener was added, before any other touch processing
         * by the RecyclerView itself or child views occurs.</p>
         *
         * @param e MotionEvent describing the touch event. All coordinates are in
         *          the RecyclerView's coordinate system.
         * @return true if this OnItemTouchListener wishes to begin intercepting touch events, false
         *         to continue with the current behavior and continue observing future events in
         *         the gesture.
         */
        boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e);

        /**
         * Process a touch event as part of a gesture that was claimed by returning true from
         * a previous call to {@link #onInterceptTouchEvent}.
         *
         * @param e MotionEvent describing the touch event. All coordinates are in
         *          the RecyclerView's coordinate system.
         */
        void onTouchEvent(RecyclerView rv, MotionEvent e);

        /**
         * Called when a child of RecyclerView does not want RecyclerView and its ancestors to
         * intercept touch events with
         * {@link ViewGroup#onInterceptTouchEvent(MotionEvent)}.
         *
         * @param disallowIntercept True if the child does not want the parent to
         *            intercept touch events.
         * @see ViewParent#requestDisallowInterceptTouchEvent(boolean)
         */
        void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept);
    }

    /**
     * An implementation of {@link RecyclerView.OnItemTouchListener} that has empty method bodies
     * and default return values.
     * <p>
     * You may prefer to extend this class if you don't need to override all methods. Another
     * benefit of using this class is future compatibility. As the interface may change, we'll
     * always provide a default implementation on this class so that your code won't break when
     * you update to a new version of the support library.
     */
    public static class SimpleOnItemTouchListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * An OnScrollListener can be added to a RecyclerView to receive messages when a scrolling event
     * has occurred on that RecyclerView.
     * <p>
     * @see RecyclerView#addOnScrollListener(OnScrollListener)
     * @see RecyclerView#clearOnChildAttachStateChangeListeners()
     */
    public abstract static class OnScrollListener {

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * A RecyclerListener can be set on a RecyclerView to receive messages whenever
     * a view is recycled.
     *
     * @see RecyclerView#setRecyclerListener(RecyclerListener)
     */
    public interface RecyclerListener {

        /**
         * This method is called whenever the view in the ViewHolder is recycled.
         *
         * RecyclerView calls this method right before clearing ViewHolder's internal data and
         * sending it to RecycledViewPool. This way, if ViewHolder was holding valid information
         * before being recycled, you can call {@link ViewHolder#getAdapterPosition()} to get
         * its adapter position.
         *
         * @param holder The ViewHolder containing the view that was recycled
         */
        void onViewRecycled(ViewHolder holder);
    }

    /**
     * A Listener interface that can be attached to a RecylcerView to get notified
     * whenever a ViewHolder is attached to or detached from RecyclerView.
     */
    public interface OnChildAttachStateChangeListener {

        /**
         * Called when a view is attached to the RecyclerView.
         *
         * @param view The View which is attached to the RecyclerView
         */
        void onChildViewAttachedToWindow(View view);

        /**
         * Called when a view is detached from RecyclerView.
         *
         * @param view The View which is being detached from the RecyclerView
         */
        void onChildViewDetachedFromWindow(View view);
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     *
     * <p>{@link Adapter} implementations should subclass ViewHolder and add fields for caching
     * potentially expensive {@link View#findViewById(int)} results.</p>
     *
     * <p>While {@link LayoutParams} belong to the {@link LayoutManager},
     * {@link ViewHolder ViewHolders} belong to the adapter. Adapters should feel free to use
     * their own custom ViewHolder implementations to store data that makes binding view contents
     * easier. Implementations should assume that individual item views will hold strong references
     * to <code>ViewHolder</code> objects and that <code>RecyclerView</code> instances may hold
     * strong references to extra off-screen item views for caching purposes</p>
     */
    public abstract static class ViewHolder {

        public final View itemView;

        WeakReference<RecyclerView> mNestedRecyclerView;

        int mPosition = NO_POSITION;

        int mOldPosition = NO_POSITION;

        long mItemId = NO_ID;

        int mItemViewType = INVALID_TYPE;

        int mPreLayoutPosition = NO_POSITION;

        // The item that this holder is shadowing during an item change event/animation
        ViewHolder mShadowedHolder = null;

        // The item that is shadowing this holder during an item change event/animation
        ViewHolder mShadowingHolder = null;

        /**
         * This ViewHolder has been bound to a position; mPosition, mItemId and mItemViewType
         * are all valid.
         */
        static final int FLAG_BOUND = 1 << 0;

        /**
         * The data this ViewHolder's view reflects is stale and needs to be rebound
         * by the adapter. mPosition and mItemId are consistent.
         */
        static final int FLAG_UPDATE = 1 << 1;

        /**
         * This ViewHolder's data is invalid. The identity implied by mPosition and mItemId
         * are not to be trusted and may no longer match the item view type.
         * This ViewHolder must be fully rebound to different data.
         */
        static final int FLAG_INVALID = 1 << 2;

        /**
         * This ViewHolder points at data that represents an item previously removed from the
         * data set. Its view may still be used for things like outgoing animations.
         */
        static final int FLAG_REMOVED = 1 << 3;

        /**
         * This ViewHolder should not be recycled. This flag is set via setIsRecyclable()
         * and is intended to keep views around during animations.
         */
        static final int FLAG_NOT_RECYCLABLE = 1 << 4;

        /**
         * This ViewHolder is returned from scrap which means we are expecting an addView call
         * for this itemView. When returned from scrap, ViewHolder stays in the scrap list until
         * the end of the layout pass and then recycled by RecyclerView if it is not added back to
         * the RecyclerView.
         */
        static final int FLAG_RETURNED_FROM_SCRAP = 1 << 5;

        /**
         * This ViewHolder is fully managed by the LayoutManager. We do not scrap, recycle or remove
         * it unless LayoutManager is replaced.
         * It is still fully visible to the LayoutManager.
         */
        static final int FLAG_IGNORE = 1 << 7;

        /**
         * When the View is detached form the parent, we set this flag so that we can take correct
         * action when we need to remove it or add it back.
         */
        static final int FLAG_TMP_DETACHED = 1 << 8;

        /**
         * Set when we can no longer determine the adapter position of this ViewHolder until it is
         * rebound to a new position. It is different than FLAG_INVALID because FLAG_INVALID is
         * set even when the type does not match. Also, FLAG_ADAPTER_POSITION_UNKNOWN is set as soon
         * as adapter notification arrives vs FLAG_INVALID is set lazily before layout is
         * re-calculated.
         */
        static final int FLAG_ADAPTER_POSITION_UNKNOWN = 1 << 9;

        /**
         * Set when a addChangePayload(null) is called
         */
        static final int FLAG_ADAPTER_FULLUPDATE = 1 << 10;

        /**
         * Used by ItemAnimator when a ViewHolder's position changes
         */
        static final int FLAG_MOVED = 1 << 11;

        /**
         * Used by ItemAnimator when a ViewHolder appears in pre-layout
         */
        static final int FLAG_APPEARED_IN_PRE_LAYOUT = 1 << 12;

        static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;

        /**
         * Used when a ViewHolder starts the layout pass as a hidden ViewHolder but is re-used from
         * hidden list (as if it was scrap) without being recycled in between.
         *
         * When a ViewHolder is hidden, there are 2 paths it can be re-used:
         *   a) Animation ends, view is recycled and used from the recycle pool.
         *   b) LayoutManager asks for the View for that position while the ViewHolder is hidden.
         *
         * This flag is used to represent "case b" where the ViewHolder is reused without being
         * recycled (thus "bounced" from the hidden list). This state requires special handling
         * because the ViewHolder must be added to pre layout maps for animations as if it was
         * already there.
         */
        static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 1 << 13;

        int mFlags;

        private static final List<Object> FULLUPDATE_PAYLOADS = Collections.emptyList();

        List<Object> mPayloads = null;

        List<Object> mUnmodifiedPayloads = null;

        private int mIsRecyclableCount = 0;

        // If non-null, view is currently considered scrap and may be reused for other data by the
        // scrap container.
        Recycler mScrapContainer = null;

        // Keeps whether this ViewHolder lives in Change scrap or Attached scrap
        boolean mInChangeScrap = false;

        // Saves isImportantForAccessibility value for the view item while it's in hidden state and
        // marked as unimportant for accessibility.
        private int mWasImportantForAccessibilityBeforeHidden = 0;

        // set if we defer the accessibility state change of the view holder
        int mPendingAccessibilityState = PENDING_ACCESSIBILITY_STATE_NOT_SET;

        /**
         * Is set when VH is bound from the adapter and cleaned right before it is sent to
         * {@link RecycledViewPool}.
         */
        RecyclerView mOwnerRecyclerView;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }

        void flagRemovedAndOffsetPosition(int mNewPosition, int offset, boolean applyToPreLayout) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void offsetPosition(int offset, boolean applyToPreLayout) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void clearOldPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void saveOldPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean shouldIgnore() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @deprecated This method is deprecated because its meaning is ambiguous due to the async
         * handling of adapter updates. You should use {@link #getLayoutPosition()} or
         * {@link #getAdapterPosition()} depending on your use case.
         *
         * @see #getLayoutPosition()
         * @see #getAdapterPosition()
         */
        @Deprecated
        public final int getPosition() {
            return mPreLayoutPosition == NO_POSITION ? mPosition : mPreLayoutPosition;
        }

        public final int getLayoutPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final int getAdapterPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final int getOldPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final long getItemId() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final int getItemViewType() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isScrap() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void unScrap() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean wasReturnedFromScrap() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void clearReturnedFromScrapFlag() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void clearTmpDetachFlag() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void stopIgnoring() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void setScrapContainer(Recycler recycler, boolean isChangeScrap) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isInvalid() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean needsUpdate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isBound() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isRemoved() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean hasAnyOfTheFlags(int flags) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isTmpDetached() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isAttachedToTransitionOverlay() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isAdapterPositionUnknown() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void setFlags(int flags, int mask) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void addFlags(int flags) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void addChangePayload(Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void createPayloadsIfNeeded() {
            if (mPayloads == null) {
                mPayloads = new ArrayList<Object>();
                mUnmodifiedPayloads = Collections.unmodifiableList(mPayloads);
            }
        }

        void clearPayload() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        List<Object> getUnmodifiedPayloads() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void resetInternal() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void onEnteredHiddenState(RecyclerView parent) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void onLeftHiddenState(RecyclerView parent) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final void setIsRecyclable(boolean recyclable) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public final boolean isRecyclable() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean shouldBeKeptAsChild() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean doesTransientStatePreventRecycling() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isUpdated() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * {@link android.view.ViewGroup.MarginLayoutParams LayoutParams} subclass for children of
     * {@link RecyclerView}. Custom {@link LayoutManager layout managers} are encouraged
     * to create their own subclass of this <code>LayoutParams</code> class
     * to store any additional required per-child view metadata about the layout.
     */
    public static class LayoutParams extends android.view.ViewGroup.MarginLayoutParams {

        ViewHolder mViewHolder;

        final Rect mDecorInsets = new Rect();

        boolean mInsetsDirty = true;

        // Flag is set to true if the view is bound while it is detached from RV.
        // In this case, we need to manually call invalidate after view is added to guarantee that
        // invalidation is populated through the View hierarchy
        boolean mPendingInvalidate = false;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(LayoutParams source) {
            super((ViewGroup.LayoutParams) source);
        }

        public boolean viewNeedsUpdate() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isViewInvalid() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isItemRemoved() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isItemChanged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * @deprecated use {@link #getViewLayoutPosition()} or {@link #getViewAdapterPosition()}
         */
        @Deprecated
        public int getViewPosition() {
            return mViewHolder.getPosition();
        }

        public int getViewLayoutPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getViewAdapterPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Observer base class for watching changes to an {@link Adapter}.
     * See {@link Adapter#registerAdapterDataObserver(AdapterDataObserver)}.
     */
    public abstract static class AdapterDataObserver {

        public void onChanged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static class AdapterDataObservable extends Observable<AdapterDataObserver> {

        public boolean hasObservers() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyChanged() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyItemRangeInserted(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyItemRangeRemoved(int positionStart, int itemCount) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void notifyItemMoved(int fromPosition, int toPosition) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * <p>Contains useful information about the current RecyclerView state like target scroll
     * position or view focus. State object can also keep arbitrary data, identified by resource
     * ids.</p>
     * <p>Often times, RecyclerView components will need to pass information between each other.
     * To provide a well defined data bus between components, RecyclerView passes the same State
     * object to component callbacks and these components can use it to exchange data.</p>
     * <p>If you implement custom components, you can use State's put/get/remove methods to pass
     * data between your components without needing to manage their lifecycles.</p>
     */
    public static class State {

        static final int STEP_START = 1;

        static final int STEP_LAYOUT = 1 << 1;

        static final int STEP_ANIMATIONS = 1 << 2;

        void assertLayoutStep(int accepted) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Owned by SmoothScroller
         */
        int mTargetPosition = RecyclerView.NO_POSITION;

        private SparseArray<Object> mData;

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Fields below are carried from one layout pass to the next
        ////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * Number of items adapter had in the previous layout.
         */
        int mPreviousLayoutItemCount = 0;

        /**
         * Number of items that were NOT laid out but has been deleted from the adapter after the
         * previous layout.
         */
        int mDeletedInvisibleItemCountSincePreviousLayout = 0;

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Fields below must be updated or cleared before they are used (generally before a pass)
        ////////////////////////////////////////////////////////////////////////////////////////////
        @Retention(RetentionPolicy.SOURCE)
        @interface LayoutState {
        }

        @LayoutState
        int mLayoutStep = STEP_START;

        /**
         * Number of items adapter has.
         */
        int mItemCount = 0;

        boolean mStructureChanged = false;

        /**
         * True if the associated {@link RecyclerView} is in the pre-layout step where it is having
         * its {@link LayoutManager} layout items where they will be at the beginning of a set of
         * predictive item animations.
         */
        boolean mInPreLayout = false;

        boolean mTrackOldChangeHolders = false;

        boolean mIsMeasuring = false;

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Fields below are always reset outside of the pass (or passes) that use them
        ////////////////////////////////////////////////////////////////////////////////////////////
        boolean mRunSimpleAnimations = false;

        boolean mRunPredictiveAnimations = false;

        /**
         * This data is saved before a layout calculation happens. After the layout is finished,
         * if the previously focused view has been replaced with another view for the same item, we
         * move the focus to the new item automatically.
         */
        int mFocusedItemPosition;

        long mFocusedItemId;

        // when a sub child has focus, record its id and see if we can directly request focus on
        // that one instead
        int mFocusedSubChildId;

        int mRemainingScrollHorizontal;

        int mRemainingScrollVertical;

        ////////////////////////////////////////////////////////////////////////////////////////////
        void prepareForNestedPrefetch(Adapter adapter) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isMeasuring() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isPreLayout() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean willRunPredictiveAnimations() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean willRunSimpleAnimations() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void remove(int resourceId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @SuppressWarnings("TypeParameterUnusedInFormals")
        public <T> T get(int resourceId) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public void put(int resourceId, Object data) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getTargetScrollPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean hasTargetScrollPosition() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean didStructureChange() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getItemCount() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getRemainingScrollHorizontal() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public int getRemainingScrollVertical() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * This class defines the behavior of fling if the developer wishes to handle it.
     * <p>
     * Subclasses of {@link OnFlingListener} can be used to implement custom fling behavior.
     *
     * @see #setOnFlingListener(OnFlingListener)
     */
    public abstract static class OnFlingListener {

        /**
         * Override this to handle a fling given the velocities in both x and y directions.
         * Note that this method will only be called if the associated {@link LayoutManager}
         * supports scrolling and the fling is not handled by nested scrolls first.
         *
         * @param velocityX the fling velocity on the X axis
         * @param velocityY the fling velocity on the Y axis
         *
         * @return true if the fling was handled, false otherwise.
         */
        public abstract boolean onFling(int velocityX, int velocityY);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A callback interface that can be used to alter the drawing order of RecyclerView children.
     * <p>
     * It works using the {@link ViewGroup#getChildDrawingOrder(int, int)} method, so any case
     * that applies to that method also applies to this callback. For example, changing the drawing
     * order of two views will not have any effect if their elevation values are different since
     * elevation overrides the result of this callback.
     */
    public interface ChildDrawingOrderCallback {

        /**
         * Returns the index of the child to draw for this iteration. Override this
         * if you want to change the drawing order of children. By default, it
         * returns i.
         *
         * @param i The current iteration.
         * @return The index of the child to draw this iteration.
         *
         * @see RecyclerView#setChildDrawingOrderCallback(RecyclerView.ChildDrawingOrderCallback)
         */
        int onGetChildDrawingOrder(int childCount, int i);
    }
}
