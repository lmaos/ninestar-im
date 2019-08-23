package org.ninestar.im.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Utils {

	public int testVar = 10; // 一个测试时使用变量 没有任何含义

	public static final char[] azAZ_CHARS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
	public static final char[] ABCZ_CHARS = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
	public static final char[] abcz_CHARS = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
	public static final char[] NUM123_CHARS = "1234567890".toCharArray();
	public static final char[] BASIC_CHARS = "qwertyuiopasdfghjklzxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM1234567890"
			.toCharArray();
	public static final byte[] BYTE_ZERO = {};
	public static final char[] CHAR_ZERO = {};
	public static final String OBJECT_PRINT_DATATYPE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/*
	 * * 判断是那种类型
	 */

	public static boolean isbyte(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == byte.class;
	}

	public static boolean isByte(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Byte.class;
	}

	public static boolean isshort(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == short.class;
	}

	public static boolean isShort(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Short.class;
	}

	public static boolean isint(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == int.class;
	}

	public static boolean isInteger(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Integer.class;
	}

	public static boolean islong(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == long.class;
	}

	public static boolean isLong(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Long.class;
	}

	public static boolean isfloat(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == float.class;
	}

	public static boolean isFloat(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Float.class;
	}

	public static boolean isdouble(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == double.class;
	}

	public static boolean isDouble(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Double.class;
	}

	public static boolean isboolean(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == boolean.class;
	}

	public static boolean isBoolean(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Boolean.class;
	}

	public static boolean ischar(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == char.class;
	}

	public static boolean isCharacter(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Character.class;
	}

	public static boolean isbytes(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == byte[].class;
	}

	public static boolean isBytes(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Byte[].class;
	}

	public static boolean isshorts(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == short[].class;
	}

	public static boolean isShorts(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Short[].class;
	}

	public static boolean isInts(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == int[].class;
	}

	public static boolean isIntegers(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Integer[].class;
	}

	public static boolean islongs(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == long[].class;
	}

	public static boolean isLongs(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Long[].class;
	}

	public static boolean isfloats(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == float[].class;
	}

	public static boolean isFloats(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Float[].class;
	}

	public static boolean isdoubles(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == double[].class;
	}

	public static boolean isDoubles(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Double[].class;
	}

	public static boolean isbooleans(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == boolean[].class;
	}

	public static boolean isBooleans(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Boolean[].class;
	}

	public static boolean ischars(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == char[].class;
	}

	public static boolean isCharacters(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == Character[].class;
	}

	public static boolean isString(Class<?> type) {
		if (type == null) {
			return false;
		}
		return type == String.class;
	}

	/*
	 * * 一些类型获取工具
	 */
	/**
	 * 获得类的基本类型，如果获得的类没有基本类型 则返回当前类本身 <BR>
	 * 时间：Jul 20, 2019 5:58:29 PM
	 *
	 * @param type
	 * @return
	 */
	public static Class<?> getPrimitiveType(Class<?> type) {
		if (type == null) {
			throw new NullPointerException("parameter type is null");
		}
		if (type.isPrimitive()) {
			return type;
		}
		Class<?> typex = type;
		if (isByte(type)) {
			typex = byte.class;
		} else if (isShort(type)) {
			typex = short.class;
		} else if (isInteger(type)) {
			typex = int.class;
		} else if (isLong(type)) {
			typex = long.class;
		} else if (isFloat(type)) {
			typex = float.class;
		} else if (isdouble(type)) {
			typex = double.class;
		} else if (isboolean(type)) {
			typex = boolean.class;
		} else if (ischar(type)) {
			typex = char.class;
		}
		return typex;
	}

	/**
	 * 获得类的默认值，如果是基本类型则返回默认值，如果不是基本类型则返回 null <BR>
	 * 时间：Jul 20, 2019 5:58:57 PM
	 *
	 * @param type
	 *            传入类型
	 * @return
	 */
	public static Object getDefaultValue(Class<?> type) {
		if (type == null) {
			throw new NullPointerException("parameter type is null");
		}
		Object defvalue = null;
		if (type.isPrimitive()) {
			if (isbyte(type)) {
				defvalue = (byte) 0;
			} else if (isshort(type)) {
				defvalue = (short) 0;
			} else if (isint(type)) {
				defvalue = 0;
			} else if (islong(type)) {
				defvalue = 0L;
			} else if (isfloat(type)) {
				defvalue = 0.0F;
			} else if (isdouble(type)) {
				defvalue = 0D;
			} else if (isboolean(type)) {
				defvalue = false;
			} else if (ischar(type)) {
				defvalue = '\0';
			}
		}

		return defvalue;
	}

	/**
	 * 通过string
	 * 转换成指定类型，目前支持(byte,short,int,long,float,double,boolean,char,String,StringBuilder,StringBuffer,date)
	 * date[时间类型目前传入时间戳] <BR>
	 * 
	 * 如果获取失败了则返回类型的默认值 <BR>
	 * 
	 * 时间：Jul 22, 2019 12:12:00 PM
	 *
	 * @param type
	 * @param value
	 * @param defValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<T> type, String value, BiFunction<Class<T>, String, T> fail) {
		if (value == null || value.length() == 0) {
			return (T) getDefaultValue(type);
		}

		@SuppressWarnings("unused")
		String src = value; // 保存元数据 以便于后期扩展
		Object res = null;
		try {
			if (isbyte(type) || isByte(type)) {
				value = substringIndexOf(value, ".", true);
				res = (byte) Integer.parseInt(value.trim());
			} else if (isshort(type) || isShort(type)) {
				value = substringIndexOf(value, ".", true);
				res = (short) Integer.parseInt(value.trim());
			} else if (isint(type) || isInteger(type)) {
				value = substringIndexOf(value, ".", true);
				res = Integer.parseInt(value.trim());
			} else if (islong(type) || isLong(type)) {
				value = substringIndexOf(value, ".", true);
				res = Long.parseLong(value.trim());
			} else if (isfloat(type) || isFloat(type)) {
				res = Float.parseFloat(value.trim());
			} else if (isdouble(type) || isDouble(type)) {
				res = Double.parseDouble(value.trim());
			} else if (isboolean(type) || isBoolean(type)) {
				res = "true".equals(value.trim());
			} else if (ischar(type) || isCharacter(type)) {
				res = value.charAt(0);
			} else if (type == String.class) {
				res = value;
			} else if (type == StringBuilder.class) {
				res = new StringBuilder(value.length() + 36).append(value);
			} else if (type == StringBuffer.class) {
				res = new StringBuffer(value.length() + 36).append(value);
			} else if (type == Date.class) {
				value = substringIndexOf(value, ".", true);
				res = new Date(Long.parseLong(value));
			}
		} catch (Exception e) {

		}
		if (res == null && fail != null) {
			res = fail.apply(type, value);
		}
		if (res == null) {
			res = getDefaultValue(type);
		}
		return (T) res;
	}

	public static class ConstructorParameter {
		private Class<?>[] paramTypes = new Class<?>[0];
		private Object[] paramValue = new Object[0];

		public ConstructorParameter(Class<?>[] paramTypes, Object[] paramValue) {
			if (paramTypes != null) {
				this.paramTypes = paramTypes;
			}
			if (paramValue != null) {
				this.paramValue = paramValue;
			}
		}

	}

	public static <T> T newObject(Class<T> type) throws NoSuchMethodException {
		return newObject(type, null);
	}

	public static <T> T newObject(Class<T> type, ConstructorParameter constructorParameter)
			throws NoSuchMethodException, IllegalArgumentException {
		if (constructorParameter == null) {
			try {
				Constructor<T> constructor = type.getDeclaredConstructor();
				constructor.setAccessible(true);
				return constructor.newInstance();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			Constructor<T> constructor = type.getDeclaredConstructor(constructorParameter.paramTypes);
			constructor.setAccessible(true);
			try {
				return constructor.newInstance(constructorParameter.paramValue);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获得一个字符串，通过 text + args[] 拼接而成。 <BR>
	 * 时间：Jul 20, 2019 6:16:01 PM
	 *
	 * @param text
	 * @param args
	 * @return
	 */
	public static String getStringByObjArrays(String text, Object... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(text);
		for (Object arg : args) {
			sb.append(arg);
		}
		return sb.toString();
	}

	/**
	 * 创建一个map 并提前初始化,初始化方式 args = {"key1", val1, "key2", val2} <BR>
	 * 时间：Jul 20, 2019 6:20:11 PM
	 *
	 * @param args
	 * @return
	 */
	public static HashMap<String, Object> newHashMapAndInit(Object... args) {
		HashMap<String, Object> result = newHashMap();
		int length = args.length / 2 * 2;
		for (int i = 0; i < length; i += 2) {
			if (args[i] != null && args[i + 1] != null) {
				String key = String.valueOf(args[i]);
				Object val = args[i + 1];
				result.put(key, val);
			}
		}
		return result;
	}

	public static <K, V> HashMap<K, V> newHashMap() {
		return new HashMap<K, V>();
	}

	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	}

	public static <E> ArrayList<E> newArrayList() {
		return new ArrayList<E>();
	}

	public static <E> LinkedList<E> newLinkedList() {
		return new LinkedList<E>();
	}

	public static <E> HashSet<E> newHashSet() {
		return new HashSet<E>();
	}

	/**
	 * 截取文本(sp从前部开始查找文本)，left = false时 从0位置开始截取，截取到 指定内容 sp 到起始位置。<BR>
	 * left = true 时， 从 指定内容 sp 到结束后到位置截取到 全文本末尾。<BR>
	 * 查找到内容位置如果不存在则返回原文本<BR>
	 * 
	 * 时间：Jul 20, 2019 6:23:16 PM
	 *
	 * @param text
	 * @param sp
	 * @return
	 */
	public static String substringIndexOf(String text, String sp, boolean left) {
		if (text == null) {
			return null;
		}
		int index = text.indexOf(sp);
		if (index == -1) {
			return text;
		}
		if (left) {
			return text.substring(0, index);
		} else {
			return text.substring(index + sp.length());
		}
	}

	/**
	 * 截取文本(sp从末尾开始查找文本)，left = false时 从0位置开始截取，截取到 指定内容 sp 到起始位置。<BR>
	 * left = true 时， 从 指定内容 sp 到结束后到位置截取到 全文本末尾。 <BR>
	 * 查找到内容位置如果不存在则返回原文本 <BR>
	 * 
	 * 时间：Jul 20, 2019 6:23:16 PM
	 *
	 * @param text
	 * @param sp
	 * @return
	 */
	public static String substringLastIndexOf(String text, String sp, boolean left) {
		if (text == null) {
			return null;
		}
		int index = text.lastIndexOf(sp);
		if (index == -1) {
			return text;
		}
		if (left) {
			return text.substring(0, index);
		} else {
			return text.substring(index + sp.length());
		}
	}

	public static class StringResult implements CharSequence {
		private String result;
		private String text;
		private int startIndex;
		private int endIndex;

		public StringResult(String result, String text, int startIndex, int endIndex) {
			this.result = result;
			this.text = text;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
		}

		public StringResult(String text) {
			this.text = text;
			this.startIndex = -1;
			this.endIndex = -1;
		}

		public int length() {

			return result.length();
		}

		public char charAt(int index) {
			return result.charAt(index);
		}

		public CharSequence subSequence(int start, int end) {
			return result.subSequence(start, end);
		}

		@Override
		public String toString() {
			return result;
		}

		public String getResult() {
			return result;
		}

		public String getText() {
			return text;
		}

		public int getStartIndex() {
			return startIndex;
		}

		public int getEndIndex() {
			return endIndex;
		}

	}

	/**
	 * 截取文本, 从0位置截取，默认不包含截取的关键字。 <BR>
	 * 举例说明: <BR>
	 * String res = ("x--{1234{567}890}--x", "{", "}") <BR>
	 * res 的结果为: 1234{567}890<BR>
	 *
	 * 时间：Jul 20, 2019 6:40:22 PM
	 *
	 * @param text
	 * @param start
	 * @param end
	 * @return
	 */
	public static StringResult substringIndexOfStartAndLastIndexOfEnd(String text, String start, String end) {
		return substringIndexOfStartAndLastIndexOfEnd(text, start, end, 0, false);
	}

	/**
	 * 截取文本。 <BR>
	 * 举例说明: <BR>
	 * String res = ("x--{1234{567}890}--x", "{", "}", 0, false) <BR>
	 * res 的结果为: 1234{567}890<BR>
	 *
	 * String res = ("x--{1234{567}890}--x", "{", "}", 0, true) <BR>
	 * res 的结果为: {1234{567}890}<BR>
	 * 
	 * 时间：Jul 20, 2019 6:40:22 PM
	 *
	 * @param text
	 * @param start
	 * @param end
	 * @param fromIndex
	 *            从哪个位置开始
	 * @param include
	 *            是否包含前后关键字 true 包含, false 不包含，如果不包含则截取 start...end 中间的内容
	 * @return
	 */
	public static StringResult substringIndexOfStartAndLastIndexOfEnd(String text, String start, String end,
			int fromIndex, boolean include) {
		if (text == null) {
			return new StringResult(text);
		}
		int startIndex = 0;
		int endIndex = text.length();
		if (start != null) {
			startIndex = text.indexOf(start, fromIndex);
		}
		if (end != null) {
			endIndex = text.lastIndexOf(end);
		}
		if (startIndex == -1) {
			startIndex = 0;
		}
		if (endIndex == -1) {
			endIndex = text.length();
		}
		if (endIndex <= startIndex) {
			return new StringResult(text);
		}

		if (startIndex == 0 && endIndex == text.length()) {
			return new StringResult(text, text, startIndex, endIndex);
		}
		String result = null;
		if (include) {
			result = text.substring(startIndex, endIndex + end.length());
		} else {
			result = text.substring(startIndex + start.length(), endIndex);
		}
		return new StringResult(result, text, startIndex, endIndex);
	}

	/**
	 * 截取文本。 <BR>
	 * 举例说明: <BR>
	 * String res = ("x--{1234{567}890}--x", "{", "}") <BR>
	 * res 的结果为: 1234{567<BR>
	 *
	 * 时间：Jul 20, 2019 6:40:22 PM
	 *
	 * @param text
	 * @param start
	 * @param end
	 * @return
	 */
	public static StringResult substringIndexOfStartAndIndexOfEnd(String text, String start, String end) {
		return substringIndexOfStartAndIndexOfEnd(text, start, end, 0, false);
	}

	/**
	 * 截取文本, 从0位置截取，默认不包含截取的关键字。 <BR>
	 * 举例说明: <BR>
	 * String res = ("x--{1234{567}890}--x", "{", "}", 0, false) <BR>
	 * res 的结果为: 1234{567<BR>
	 *
	 * String res = ("x--{1234{567}890}--x", "{", "}", 0, true) <BR>
	 * res 的结果为: {1234{567}<BR>
	 * 
	 * 时间：Jul 20, 2019 6:40:22 PM
	 *
	 * @param text
	 * @param start
	 * @param end
	 * @param fromIndex
	 *            从哪个位置开始
	 * @param include
	 *            是否包含前后关键字 true 包含, false 不包含，如果不包含则截取 start...end 中间的内容
	 * @return
	 */
	public static StringResult substringIndexOfStartAndIndexOfEnd(String text, String start, String end, int fromIndex,
			boolean include) {
		if (text == null) {
			return new StringResult(text);
		}
		int startIndex = 0;
		int endIndex = text.length();
		if (start != null) {
			startIndex = text.indexOf(start, fromIndex);
		}
		if (end != null) {
			endIndex = text.indexOf(end, startIndex + 1);

		}
		if (startIndex == -1) {
			startIndex = 0;
		}
		if (endIndex == -1) {
			endIndex = text.length();
		}
		if (endIndex <= startIndex) {
			return new StringResult(text);
		}
		if (startIndex == 0 && endIndex == text.length()) {
			return new StringResult(text, text, startIndex, endIndex);
		}
		String result = null;
		if (include) {
			result = text.substring(startIndex, endIndex + end.length());
		} else {
			result = text.substring(startIndex + start.length(), endIndex);
		}
		return new StringResult(result, text, startIndex, endIndex);
	}

	public static Integer toInt(String value, Integer def) {
		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static Long toLong(String value, Long def) {
		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static Short toShort(String value, Short def) {

		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return (short) Integer.parseInt(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static Byte toByte(String value, Byte def) {

		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return (byte) Integer.parseInt(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static Double toDouble(String value, Double def) {

		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static Float toFloat(String value, Float def) {

		if (value == null || value.length() == 0) {
			return def;
		}
		value = substringIndexOf(value, ".", true).trim();
		if (value.length() == 0) {
			return def;
		}
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 是否是纯数字组成的 <BR>
	 * 时间：Jul 22, 2019 1:42:49 PM
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumberChar(char c) {
		return c >= '0' && c <= '9';
	}

	/**
	 * 是否是 a-z A-Z的字母组成的 <BR>
	 * 时间：Jul 22, 2019 1:42:13 PM
	 *
	 * @param str
	 * @return
	 */
	public static boolean isAbcLetter(String str) {
		int length = str.length();
		for (int i = 0; i < length; i++) {
			char c = str.charAt(i);
			boolean ok = isAbcLetterChar(c);
			if (!ok) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 当前字符是不是 a-z A-Z <BR>
	 * 时间：Jul 22, 2019 2:15:14 PM
	 *
	 * @param c
	 * @return
	 */
	public static boolean isAbcLetterChar(char c) {
		return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
	}

	/**
	 * 是合法的字符 a-z A-Z 0 - 9 _ <BR>
	 * 时间：Jul 22, 2019 1:44:51 PM
	 *
	 * @param c
	 * @return
	 */
	public static boolean isLegalChar(char c) {
		return isAbcLetterChar(c) || isNumberChar(c) || c == '_';
	}

	public static boolean isEMail(String mail) {
		if (mail == null) {
			return false;
		}
		mail = mail.trim();
		int length = mail.length();
		if (length < 5) { // a@b.c
			return false;
		}
		if (!isLegalChar(mail.charAt(0)) || !isLegalChar(mail.charAt(length - 1))) {
			return false;
		}
		boolean dx = false;
		boolean ax = false;
		int acount = 0;
		int dcount = 0;
		for (int i = 0; i < length; i++) {
			char c = mail.charAt(i);
			if (c == '.') {
				if (dx || ax) {
					return false;
				}
				dx = true;
				ax = false;
				if (acount == 1) {
					dcount++;
				}
			} else if (c == '@') {
				if (dx || ax || acount == 1) {
					return false;
				}
				dx = false;
				ax = true;
				acount++;
			} else if (isLegalChar(c)) {
				dx = false;
				ax = false;
			} else {
				return false;
			}
		}
		if (dcount == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 添加前缀，如果存在前缀，或其他更多的前缀则不添加前缀 <BR>
	 * 时间：Jul 23, 2019 3:04:12 PM
	 *
	 * @param src
	 * @param prefix
	 * @param exists
	 * @return
	 */
	public static String addPrefix(String src, String prefix, String... exists) {
		Set<String> pd = new HashSet<>();
		pd.add(prefix);
		for (String exist : exists) {
			if (src.startsWith(exist)) {
				return src;
			}
		}
		return prefix + src;

	}

	/**
	 * 添加http:// 前缀，如果存在 http:// 或 https:// 前缀时，则返回原值，否则在前面添加 <BR>
	 * 时间：Jul 23, 2019 3:13:24 PM
	 *
	 * @param src
	 * @return
	 */
	public static String addHttpPrefix(String src) {
		if (src.startsWith("https://") || src.startsWith("http://")) {
			return src;
		}
		return "http://" + src;
	}

	/**
	 * 添加https:// 前缀，如果存在 http:// 或 https:// 前缀时，则返回原值，否则在前面添加 <BR>
	 * 时间：Jul 23, 2019 3:13:24 PM
	 *
	 * @param src
	 * @return
	 */
	public static String addHttpsPrefix(String src) {
		if (src.startsWith("https://") || src.startsWith("http://")) {
			return src;
		}
		return "https://" + src;
	}

	/**
	 * 替换为 http:// 前缀，如果存在 https:// 前缀时，则替换为http:// <BR>
	 * 时间：Jul 23, 2019 3:13:24 PM
	 *
	 * @param src
	 * @return
	 */
	public static String replaceHttpPrefix(String src) {
		if (src.startsWith("http://")) {
			return src;
		}
		int index = src.lastIndexOf("://", 8);
		if (index == -1) {
			return "http://" + src;
		}

		return "http://" + src.substring(index + 3);
	}

	/**
	 * 替换为 https:// 前缀，如果存在 http:// 前缀时，则替换为https:// <BR>
	 * 时间：Jul 23, 2019 3:13:24 PM
	 *
	 * @param src
	 * @return
	 */
	public static String replaceHttpsPrefix(String src) {
		if (src.startsWith("https://")) {
			return src;
		}
		int index = src.lastIndexOf("://", 7);
		if (index == -1) {
			return "https://" + src;
		}

		return "https://" + src.substring(index + 3);
	}

	public static enum FieldSetFailStatus {
		fieldNotExist(-1, "字段不存在"), ok(0, "设置成功"), fail(1, "其他失败"), typeErr(2, "类型不匹配"), objectNull(3, "对象空指针"),;
		private int status;
		private String msg;

		private FieldSetFailStatus(int status, String msg) {
			this.status = status;
			this.msg = msg;
		}

		public int getStatus() {
			return status;
		}

		public String getMsg() {
			return msg;
		}

		@Override
		public String toString() {
			return getStringByObjArrays("{\"status\":", status, ", \"msg\":\"", msg, "\"}");
		}
	}

	/**
	 * 给这个对象中这个属性设置一个新的值 <BR>
	 * 时间：Jul 23, 2019 3:30:42 PM
	 *
	 * @param obj
	 * @param name
	 * @param value
	 * @return
	 */
	public static FieldSetFailStatus setFieldValue(Object obj, String name, Object value) {
		if (obj == null) {
			return FieldSetFailStatus.objectNull;
		}
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(obj, value);
			return FieldSetFailStatus.ok;
		} catch (NoSuchFieldException e) {
			return FieldSetFailStatus.fieldNotExist;
		} catch (IllegalArgumentException e) {
			return FieldSetFailStatus.typeErr;
		} catch (Exception e) {
			e.printStackTrace();
			return FieldSetFailStatus.fail;
		}
	}

	/**
	 * 获得这个对象中指定属性名的值，如果属性不存在或对象传入错误则均返回null <BR>
	 * 时间：Jul 23, 2019 3:31:08 PM
	 *
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getFieldValue(Object obj, String name) {
		if (obj == null) {
			return null;
		}
		try {
			Field field = obj.getClass().getDeclaredField(name);
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获得 从传入的字符数组中随机获取字符生成指定长度的字符串。 <BR>
	 * 时间：Jul 23, 2019 3:35:16 PM
	 *
	 * @param cs
	 *            可以随机的字符集合
	 * @param length
	 *            生成的长度
	 * @return
	 */
	public static String getRandomString(char[] cs, int length) {
		StringBuilder res = new StringBuilder(length);
		Random r = new Random();
		r.nextInt(); // 不做任何用处
		for (int i = 0; i < length; i++) {
			int index = r.nextInt(length);
			char c = cs[index];
			res.append(c);
		}
		return res.toString();
	}

	/**
	 * 获得 从传入的字符内容中随机获取字符生成指定长度的字符串。 <BR>
	 * 时间：Jul 23, 2019 3:35:16 PM
	 *
	 * @param cs
	 *            可以随机的字符集合
	 * @param length
	 *            生成的长度
	 * @return
	 */
	public static String getRandomString(String cs, int length) {
		return getRandomString(cs.toCharArray(), length);
	}

	/**
	 * 获得 64标记位置 <BR>
	 * 时间：Jul 23, 2019 4:12:48 PM
	 *
	 * @param val
	 * @return
	 */
	public static byte[] getStatusByBit(long val) {
		byte[] statuss = new byte[64];
		for (int i = statuss.length - 1; i >= 0; i--) {
			statuss[i] = (byte) (val & 1);
			val = val >> 1;
		}
		return statuss;
	}

	/**
	 * 获得 32标记位置 <BR>
	 * 时间：Jul 23, 2019 4:12:48 PM
	 *
	 * @param val
	 * @return
	 */
	public static byte[] getStatusByBit(int val) {
		byte[] statuss = new byte[32];
		for (int i = statuss.length - 1; i >= 0; i--) {
			statuss[i] = (byte) (val & 1);
			val = val >> 1;
		}
		return statuss;
	}

	/**
	 * 写入文件状态
	 *
	 * 时间：Jul 23, 2019 4:24:22 PM
	 */
	public static enum WriteFileStatus {
		fileNotExist(-1, "文件不存在"), ok(0, "写入成功"), createFileFail(1, "创建文件失败"), writeFail(2, "写文件失败"), charsetNameErr(3,
				"编码类型错误");
		private int status;
		private String msg;

		private WriteFileStatus(int status, String msg) {
			this.status = status;
			this.msg = msg;
		}

		public int getStatus() {
			return status;
		}

		public String getMsg() {
			return msg;
		}

		@Override
		public String toString() {
			return getStringByObjArrays("{\"status\":", status, ", \"msg\":\"", msg, "\"}");
		}
	}

	/**
	 * 写入文件并返回文件写入的状态 <BR>
	 * 时间：Jul 23, 2019 4:24:05 PM
	 *
	 * @param file
	 *            文件
	 * @param text
	 *            内容
	 * @param charsetName
	 *            编码名字
	 * @param appendContent
	 *            是否追究内容
	 * @return
	 */
	public static WriteFileStatus writeFile(File file, String text, String charsetName, boolean appendContent) {
		if (file == null || text == null) {
			return WriteFileStatus.ok;
		}
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			if (!file.getParentFile().exists()) {
				parentFile.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				return WriteFileStatus.createFileFail;
			}

		}
		byte[] content = null;
		try {
			content = text.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			return WriteFileStatus.charsetNameErr;
		}
		try (FileOutputStream out = new FileOutputStream(file, appendContent)) {
			out.write(content);
			out.flush();
		} catch (Exception e) {
			return WriteFileStatus.writeFail;
		}
		return WriteFileStatus.ok;
	}

	public static WriteFileStatus writeFile(File file, byte[] content, boolean appendContent) {
		if (file == null || content == null || content.length == 0) {
			return WriteFileStatus.ok;
		}
		if (!file.exists()) {
			File parentFile = file.getParentFile();
			if (!file.getParentFile().exists()) {
				parentFile.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e) {
				return WriteFileStatus.createFileFail;
			}

		}
		try (FileOutputStream out = new FileOutputStream(file, appendContent)) {
			out.write(content);
			out.flush();
		} catch (Exception e) {
			return WriteFileStatus.writeFail;
		}
		return WriteFileStatus.ok;
	}

	/**
	 * 读取文件内容，如果读取失败发生异常则返回 null <BR>
	 * 时间：Jul 23, 2019 4:28:15 PM
	 *
	 * @param file
	 * @return
	 */
	public static byte[] readFile(File file) {
		if (file == null || !file.exists() || file.length() == 0) {
			return BYTE_ZERO;
		}
		try (FileInputStream in = new FileInputStream(file)) {
			byte[] content = new byte[(int) file.length()];
			in.read(content);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readFileUtf8(File file) {
		byte[] bs = readFile(file);
		if (bs == null) {
			return null;
		}
		if (bs.length == 0) {
			return "";
		}
		return new String(bs, Charset.forName("UTF-8"));
	}

	public static void printObjectln(Object obj) {
		if (obj == null) {
			System.out.println(obj);
		}
		if (obj instanceof Collection) {
			Collection<?> colls = (Collection<?>) obj;
			for (Object object : colls) {
				System.out.println(object);
			}
		} else if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			for (Entry<?, ?> entrty : map.entrySet()) {
				System.out.println("key = " + entrty.getKey() + ", val = " + entrty.getValue());
			}
		} else if (obj.getClass().isArray()) {
			int length = Array.getLength(obj);
			for (int i = 0; i < length; i++) {
				Object object = Array.get(obj, i);
				System.out.println(object);
			}
		} else if (obj instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(OBJECT_PRINT_DATATYPE_PATTERN);
			String content = sdf.format((Date) obj);
			System.out.println(content);
		} else {
			System.out.println(obj);
		}
	}

	public static void main(String[] args) {

		System.out.println(substringIndexOfStartAndLastIndexOfEnd("x--{1234{567}890}--x", "{", "}", 0, false));
		System.out.println(substringIndexOfStartAndIndexOfEnd("x--{1234{567}890}--x", "{", "}", 0, false));
		System.out.println(getValue(int.class, "1234-6", (t, v) -> {
			return null;
		}));
		System.out.println(isEMail("m.vip@qq.com"));
		System.out.println(replaceHttpPrefix("https://21312312"));
		System.out.println(replaceHttpsPrefix("http://21312312"));

		System.out.println(setFieldValue(new Utils(), "testVar", 10));
		System.out.println(getFieldValue(new Utils(), "testVar"));

		System.out.println(getRandomString("1234567890", 10));

		System.out.println(Arrays.toString(getStatusByBit(1000)));

		printObjectln(newHashMapAndInit("mmm", 1, "name", "sunkai"));
		printObjectln(new Date());

	}
}
