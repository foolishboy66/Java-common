package com.foolishboy.common;

import com.foolishboy.common.utils.StringUtils;

public class StringUtilsTest {

	public static void main(String[] args) {

		System.out.println(StringUtils.firstLeftOf("ad", ""));
		System.out.println(StringUtils.lastLeftOf("adddd", "dd"));
		System.out.println(StringUtils.firstRightOf("adddddd", "dd"));
		System.out.println(StringUtils.lastRightOf("adddddda", null));

		System.out.println(StringUtils.format("https://github.com/{0}/{1}", "foolishboy66", "Java-common"));
	}
}
