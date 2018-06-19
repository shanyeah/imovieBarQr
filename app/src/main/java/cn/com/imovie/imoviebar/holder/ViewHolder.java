package cn.com.imovie.imoviebar.holder;

import android.util.Log;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by 浜庨噾骞� on 2015/3/31.
 */
public final class ViewHolder {
    static final String TAG = "ViewHolder";
    static boolean debug = false;

    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
            if (debug)
                Log.d(TAG, "call new SparseArray");
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            if(childView!=null)
                viewHolder.put(id, childView);
            if (debug)
                Log.d(TAG, "call findViewById");
        } else {
            if (debug)
                Log.d(TAG, "found cached view!");
        }
        return (T) childView;
    }
}
