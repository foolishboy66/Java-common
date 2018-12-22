package com.foolishboy.common;

import java.util.Date;

import com.foolishboy.common.utils.DateUtil;

public class DateUtilTest {

	public static void main(String[] args) {

		System.out.println(DateUtil.format(new Date()));
		System.out.println(DateUtil.format(DateUtil.plusYears(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusMonths(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusDays(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusHours(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusMinutes(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusSeconds(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.plusMilliSeconds(new Date(), 1)));
		System.out.println(DateUtil.format(DateUtil.getStartOfDay(new Date())));
		System.out.println(DateUtil.format(DateUtil.getEndOfDay(new Date())));
		System.out.println(DateUtil.format(DateUtil.getStartOfMonth(new Date())));
		System.out.println(DateUtil.format(DateUtil.getEndOfMonth(new Date())));

		System.out.println(DateUtil.isSameYear(new Date(1544587932000L), new Date(1544591532000L)));
		System.out.println(DateUtil.isSameYear(new Date(1513051212000L), new Date(1544591532000L)));

		System.out.println(DateUtil.isSameDay(new Date(1544587932000L), new Date(1544591532000L)));
		System.out.println(DateUtil.isSameDay(new Date(), new Date(1545494412000L)));
		System.out.println(DateUtil.daysBetween(new Date(), new Date(1545580812000L)));

	}
}
