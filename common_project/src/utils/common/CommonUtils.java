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
	 * �ַ�������λ
	 * @param str
	 * @param digit ��λ��
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
	 * �Ƿ�Ϊ0���߿�
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
	 * �Ƿ�Ϊ0���߿�
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNotZero(Integer num) {
		return !isZero(num);
	}

	/**
	 * ��ȡ��ǰʱ���
	 * 
	 * @return
	 */
	public static Timestamp currTimestamp() {
		return new Timestamp(new Date().getTime());
	}

	/**
	 * �������ڣ�������������
	 * 
	 * @param time
	 * @param days
	 * @return
	 */
	public static Date addTime(Date time, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

	/**
	 * �жϼ��Ϸǿ�
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
	 * �жϼ��Ϸǿ�
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}
}
