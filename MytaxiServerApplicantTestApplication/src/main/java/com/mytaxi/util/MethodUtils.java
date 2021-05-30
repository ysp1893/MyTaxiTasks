package com.mytaxi.util;

import java.util.List;

public class MethodUtils {

	/***
	 * This generic method checks weather zero or more objects are null or empty.
	 * 
	 * @param t indicates zero or more object of any type.
	 * @return returns true any object is null or empty, otherwise returns false.
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isObjectisNullOrEmpty(T... t) {
		for (T ob : t) {
			if (ob == null || ob.toString().trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/***
	 * This method checks weather zero or more passed lists are null or empty.
	 * 
	 * @param listT indicates zero or more list of any type.
	 * @return returns true if any of list is empty or null, otherwise returns
	 *         false.
	 */
	public static <T> boolean isListIsNullOrEmpty(List<T> listT) {

		if (listT == null || listT.isEmpty()) {
			return true;
		}
		return false;
	}
}
