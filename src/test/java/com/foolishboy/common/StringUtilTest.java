package com.foolishboy.common;

import com.foolishboy.common.utils.StringUtil;

public class StringUtilTest {

	public static void main(String[] args) {

		System.out.println(StringUtil.firstLeftOf("ad", ""));
		System.out.println(StringUtil.lastLeftOf("adddd", "dd"));
		System.out.println(StringUtil.firstRightOf("adddddd", "dd"));
		System.out.println(StringUtil.lastRightOf("adddddda", null));

		System.out.println(StringUtil.format("https://github.com/{0}/{1}", "foolishboy66", "Java-common"));
	}
}
