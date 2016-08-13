package hu.ait.android.shoppinglist.adapter;

/**
 * Created by nathanmajumder on 4/8/16.
 */
public interface ItemTouchHelperAdapter {
    void onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
