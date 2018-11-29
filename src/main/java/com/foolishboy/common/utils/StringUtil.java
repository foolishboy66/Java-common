package com.foolishboy.common.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 字符串工具类
 * 
 * @author wang
 *
 */
public class StringUtil {

	private StringUtil() {

	}

	/**
	 * 判断一个字符序列是否为空
	 * 
	 * <p>
	 * 为null或""时返回true
	 * </p>
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isEmpty(final CharSequence cs) {

		return cs == null || cs.length() == 0;
	}

	/**
	 * 判断一个字符序列是否为空
	 * 
	 * <p>
	 * 不为null且不为""时返回true
	 * </p>
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isNotEmpty(final CharSequence cs) {

		return !isEmpty(cs);
	}

	/**
	 * 判断多个字符序列中是否存在为空的序列
	 * 
	 * <p>
	 * 任意一个字符序列为空(null或"")时返回true
	 * </p>
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isAnyEmpty(final CharSequence... css) {

		if (ArrayUtils.isEmpty(css)) {
			return false;
		}
		for (final CharSequence cs : css) {
			if (isEmpty(cs)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断多个字符序列是否全不为空
	 * 
	 * <p>
	 * 全部字符序列都不为null且不为""时返回true
	 * </p>
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isNoneEmpty(final CharSequence... css) {

		return !isAnyEmpty(css);
	}

	/**
	 * 判断一个字符序列是否为空
	 * 
	 * <p>
	 * 为null或空串" "时返回true
	 * </p>
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {

		int len = 0;
		if (cs == null || (len = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断一个字符序列是否为空
	 * 
	 * <p>
	 * 不为null且不为空串" "时返回true
	 * </p>
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isNotBlank(final CharSequence cs) {

		return !isBlank(cs);
	}

	/**
	 * 判断多个字符序列中是否存在为空的序列
	 * 
	 * <p>
	 * 任意一个字符序列为空(null或空串" ")时返回true
	 * </p>
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isAnyBlank(final CharSequence... css) {

		if (ArrayUtils.isEmpty(css)) {
			return false;
		}
		for (final CharSequence cs : css) {
			if (isBlank(cs)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断多个字符序列是否全不为空
	 * 
	 * <p>
	 * 全部字符序列都不为null且不为空串" "时返回true
	 * </p>
	 * 
	 * @param css
	 * @return
	 */
	public static boolean isNoneBlank(final CharSequence... css) {

		return !isAnyBlank(css);
	}

	/**
	 * 删除字符串中出现的所有空格以及制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteWhitespace(final String str) {

		if (isEmpty(str)) {
			return str;
		}
		final int len = str.length();
		final char[] chs = new char[len];
		int index = 0;
		for (int i = 0; i < len; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[index] = str.charAt(i);
				++index;
			}
		}
		if (index == len) {
			return str;
		}
		return new String(chs, 0, index);
	}

}
