package cn.com.imovie.imoviebar.utils;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 锟斤拷锟斤拷锟斤拷牡墓锟斤拷锟�
 * 锟斤拷锟斤拷锟斤拷锟绞癸拷锟斤拷惴� 
 * @author 锟斤拷锟斤拷锟斤拷
 * 2011-4-1锟斤拷锟斤拷12:08:32
 */
public class LruCache<K, V> {
	private final HashMap<K, V> mLruMap;
	private final HashMap<K, Entry<K, V>> mWeakMap = new HashMap<K, Entry<K, V>>();
	private ReferenceQueue<V> mQueue = new ReferenceQueue<V>();
	public final static int DEFAULT_CAPACITY=100;
	@SuppressWarnings("serial")
	public LruCache(final int capacity) {
		mLruMap = new LinkedHashMap<K, V>(16, 0.75f, true) {
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > capacity;
			}
		};
	}

	private static class Entry<K, V> extends WeakReference<V> {
		K mKey;
		public Entry(K key, V value, ReferenceQueue<V> queue) {
			super(value, queue);
			mKey = key;
		}
	}

	@SuppressWarnings("unchecked")
	private void cleanUpWeakMap() {
		Entry<K, V> entry = (Entry<K, V>) mQueue.poll();
		while (entry != null) {
			mWeakMap.remove(entry.mKey);
			entry = (Entry<K, V>) mQueue.poll();
		}
	}

	public synchronized V put(K key, V value) {
		cleanUpWeakMap();
		mLruMap.put(key, value);
		Entry<K, V> entry = mWeakMap.put(key, new Entry<K, V>(key, value,
				mQueue));
		return entry == null ? null : entry.get();
	}

	public synchronized V get(K key) {
		cleanUpWeakMap();
		V value = mLruMap.get(key);
		if (value != null)
			return value;
		Entry<K, V> entry = mWeakMap.get(key);
		return entry == null ? null : entry.get();
	}

	public synchronized void clear() {
		mLruMap.clear();
		mWeakMap.clear();
		mQueue = new ReferenceQueue<V>();
	}
}