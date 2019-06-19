package com.foolishboy.common;

import java.util.Date;

import com.foolishboy.common.utils.DateUtils;

public class DateUtilsTest {

	public static void main(String[] args) {

		System.out.println(DateUtils.format(new Date()));
		System.out.println(DateUtils.format(DateUtils.plusYears(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusMonths(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusDays(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusHours(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusMinutes(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusSeconds(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.plusMilliSeconds(new Date(), 1)));
		System.out.println(DateUtils.format(DateUtils.getStartOfDay(new Date())));
		System.out.println(DateUtils.format(DateUtils.getEndOfDay(new Date())));
		System.out.println(DateUtils.format(DateUtils.getStartOfMonth(new Date())));
		System.out.println(DateUtils.format(DateUtils.getEndOfMonth(new Date())));

		System.out.println(DateUtils.isSameYear(new Date(1544587932000L), new Date(1544591532000L)));
		System.out.println(DateUtils.isSameYear(new Date(1513051212000L), new Date(1544591532000L)));

		System.out.println(DateUtils.isSameDay(new Date(1544587932000L), new Date(1544591532000L)));
		System.out.println(DateUtils.isSameDay(new Date(), new Date(1545494412000L)));
		System.out.println(DateUtils.daysBetween(new Date(), new Date(1545580812000L)));
		
		System.out.println(DateUtils.getDayOfWeek(new Date()));

	}
}
