package cn.com.imovie.imoviebar.event;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class OnAutoLoad implements OnScrollListener {
	public interface OnAutoLoadCallBack {
		void execute(String url);
	}

	private OnAutoLoadCallBack mCallback;
	private OnScrollListener mExternalListener;

	public OnAutoLoad(OnAutoLoadCallBack callback) {
		this(callback, null);
	}

	public OnAutoLoad(OnAutoLoadCallBack callback, OnScrollListener externalListener) {
		this.mCallback = callback;
		this.mExternalListener = externalListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
			// 婊氬姩鍒板簳閮�
			if (view.getLastVisiblePosition() == (view.getCount()-1)) {
				mCallback.execute(">>>>>鎷栬嚦搴曢儴");
			}
		}
		if (mExternalListener != null) {
			mExternalListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (mExternalListener != null) {
			mExternalListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}
}
