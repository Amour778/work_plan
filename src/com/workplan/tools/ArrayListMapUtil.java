package com.workplan.tools;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;



public class ArrayListMapUtil {

	/**
	 * 数组去重并保留排序
	 * @param a
	 * @return
	 */
	public static String[] deduplicationArray(String a[]) {
		int n = a.length;
		String[] b = null;
		for (int i = 0; i < a.length; i++) {
			for (int l = i + 1; l < a.length; l++) {
				if (a[i].equals(a[l])) {
					for (int k = l + 1; k < a.length; k++) {
						a[k - 1] = a[k];
					}
					n--;
					l--;
					a = Arrays.copyOf(a, n);
				}
			}
			b = Arrays.copyOf(a, n);
		}
		//System.out.println(Arrays.toString(b));
		return b;
	}
	/**
	 * MAP去重
	 * @param String a[]
	 * @return
	 */
	public static Map<Integer,String> deduplicationMap(Map<Integer,String> map) {
		Map<Integer,String> map2=new HashMap<Integer,String>();
		for(Integer key:map.keySet()){
			if(!map2.containsValue(map.get(key))){
				map2.put(key, map.get(key));
			}
		}
		return map2;
	}

	
}
