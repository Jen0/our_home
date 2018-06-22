package utils.common;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


public class CommonUtils {
	
	public static void main(String[] args) {
		System.out.println(prefixAppendZero("21",8));
//		StringUtils.class
	}

	/**
	 * 字符串补零位
	 * @param str
	 * @param digit 总位数
	 * @return
	 */
	public static String prefixAppendZero(String str, int digit) {
		if (digit < str.length())
			return str;
		int num = digit - str.length();
		String zero = "";
		for (int i = 0; i < num; i++) 
			zero += "0";
		return zero+str;
	}

	/**
	 * 是否为0或者空
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isZero(Integer num) {
		if (num != null && num != 0) {
			return false;
		}
		return true;
	}

	/**
	 * 是否为0或者空
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNotZero(Integer num) {
		return !isZero(num);
	}

	
	/**
	 * 判断集合非空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		if (c != null && !c.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断集合非空
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
}
