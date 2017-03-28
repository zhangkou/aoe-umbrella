/**
 * 
 */
package com.aoe.umbrella.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MemoryCacheUtils {
	private static MemoryCacheUtils instance;
	private static Object monitor = new Object();
	private Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());

	private MemoryCacheUtils() {
	}

	public void put(String cacheKey, Object value) {
		this.cache.put(cacheKey, value);
	}

	public Object get(String cacheKey) {
		return this.cache.get(cacheKey);
	}

	public void clear(String cacheKey) {
		this.cache.put(cacheKey, null);
	}

	public void clear() {
		this.cache.clear();
	}

	public static MemoryCacheUtils getInstance() {
		if (instance == null) {
			synchronized (monitor) {
				if (instance == null) {
					instance = new MemoryCacheUtils();
				}
			}
		}
		return instance;
	}
}
