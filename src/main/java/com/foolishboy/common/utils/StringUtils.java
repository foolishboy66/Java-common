package com.foolishboy.common.utils;

import java.text.MessageFormat;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 字符串工具类
 * 
 * @author wang
 *
 */
public class StringUtils {

    private static final String EMPTY_STR = "";
    private static final int NOT_FOUND_INDEX = -1;

    private StringUtils() {

    }

    /**
     * 判断一个字符序列是否为空
     * 
     * <p>
     * 为null或""时返回true
     * </p>
     * 
     * @param cs 待判空的字符串
     * @return true:字符串为空，false:字符串非空
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
     * @param cs 待判空的字符串
     * @return true:字符串非空，false:字符串为空
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
     * @param css 待判空的字符串数组
     * @return true:至少有一个字符串为空，false:所有字符串非空
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
     * @param css 待判空的字符串数组
     * @return true:所有字符串非空，false:至少有一个字符串为空
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
     * @param cs 待判空的字符串
     * @return true:字符串为空串，false:字符串为非空串
     */
    public static boolean isBlank(final CharSequence cs) {

        int len;
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
     * @param cs 待判空的字符串
     * @return true:字符串为非空串，false:字符串为空串
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
     * @param css 待判空的字符串数组
     * @return true:有空串，false:无空串
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
     * @param css 待判空的字符串数组
     * @return true:无空串，false:有空串
     */
    public static boolean isNoneBlank(final CharSequence... css) {

        return !isAnyBlank(css);
    }

    /**
     * 删除字符串中出现的所有空格以及制表符
     * 
     * @param str 要删除的字符串
     * @return 去空格以及制表符的新字符串
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

    /**
     * 将可被迭代对象(list、array、set等)的迭代器中元素用分隔符连接起来
     * 
     * @param iterator 可被迭代的对象
     * @param separator 分隔符
     * @return 用分隔符连接起来的字符串
     */
    public static String join(final Iterator<?> iterator, final CharSequence separator) {

        if (iterator == null) {
            return null;
        }

        if (!iterator.hasNext()) {
            return EMPTY_STR;
        }

        Object first = iterator.next();
        StringBuilder sb = new StringBuilder();
        if (first != null) {
            sb.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                sb.append(separator);
            }
            Object next = iterator.next();
            if (next != null) {
                sb.append(next);
            }
        }

        return sb.toString();
    }

    /**
     * 将可被迭代的对象(list、array、set等)用分隔符连接起来
     * 
     * @param it 可被迭代的字符序列对象
     * @param separator 分隔符
     * @return 用分隔符连接起来的字符串
     */
    public static String join(final Iterable<? extends CharSequence> it, final CharSequence separator) {

        if (it == null) {
            return null;
        }
        Iterator<? extends CharSequence> iterator = it.iterator();

        if (!iterator.hasNext()) {
            return EMPTY_STR;
        }

        CharSequence first = iterator.next();
        StringBuilder sb = new StringBuilder();
        if (first != null) {
            sb.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                sb.append(separator);
            }
            CharSequence next = iterator.next();
            if (next != null) {
                sb.append(next);
            }
        }

        return sb.toString();
    }

    /**
     * 返回字符串指定index左边的部分
     * 
     * @param str 要操作的字符串
     * @param index 下标
     * @return 字符串指定index左边的部分
     */
    public static String left(final String str, final int index) {

        if (str == null || index < 0) {
            return null;
        }

        if (index >= str.length()) {
            return str;
        }

        return str.substring(0, index);
    }

    /**
     * 返回字符串最早出现指定子串左边的部分
     * 
     * @param str 要操作的字符串
     * @param separator 子串
     * @return 字符串最早出现指定子串左边的部分
     */
    public static String firstLeftOf(final String str, final String separator) {

        if (str == null || isEmpty(separator)) {
            return null;
        }
        int index = str.indexOf(separator);

        return left(str, index);
    }

    /**
     * 返回字符串最晚出现指定子串左边的部分
     *
     * @param str 要操作的字符串
     * @param separator 子串
     * @return 字符串最晚出现指定子串左边的部分
     */
    public static String lastLeftOf(final String str, final String separator) {

        if (str == null || isEmpty(separator)) {
            return null;
        }
        int index = str.lastIndexOf(separator);

        return left(str, index);
    }

    /**
     * 返回字符串指定index右边的部分
     * 
     * @param str 要操作的字符串
     * @param index 下标
     * @return 字符串指定index右边的部分
     */
    public static String right(final String str, final int index) {

        if (str == null || index >= (str.length() - 1)) {
            return null;
        }

        if (index < 0) {
            return str;
        }

        return str.substring(index + 1);
    }

    /**
     * 返回字符串最早出现指定子串右边的部分
     * 
     * @param str 要操作的字符串
     * @param separator 子串
     * @return 字符串最早出现指定子串右边的部分
     */
    public static String firstRightOf(final String str, final String separator) {

        if (str == null || isEmpty(separator)) {
            return null;
        }
        int index = str.indexOf(separator);
        if (index == NOT_FOUND_INDEX) {
            return EMPTY_STR;
        }

        index += separator.length() - 1;

        return right(str, index);
    }

    /**
     * 返回字符串最晚出现指定子串右边的部分
     * 
     * @param str 要操作的字符串
     * @param separator 子串
     * @return 字符串最晚出现指定子串右边的部分
     */
    public static String lastRightOf(final String str, final String separator) {

        if (str == null || isEmpty(separator)) {
            return null;
        }
        int index = str.lastIndexOf(separator);
        if (index == NOT_FOUND_INDEX) {
            return EMPTY_STR;
        }

        index += separator.length() - 1;

        return right(str, index);
    }

    /**
     * 格式化字符串,将形如https://github.com/{0}/{1}字符串中的占位符替换为指定对象
     * 
     * @param str 原始字符串
     * @param css 替换对象数组
     * @return 原始字符串占位符替换为指定对象的新字符串
     */
    public static String format(final String str, final Object... css) {

        return MessageFormat.format(str, css);
    }
}
