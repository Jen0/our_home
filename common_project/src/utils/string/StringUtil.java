package utils.string;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils{
	
	public static void main(String[] args) {
		System.out.println(StringUtils.isBlank(" 1"));
	}

	/** 清除字符串
	 * @param str  检查的字符串
	 * @param clearStr 需要清除的字符。例如:","，清除逗号
	 * @return
	 */
	public static  String clearComma(String str,String clearStr) {
		String result = "";
		String[] arr = str.split(clearStr);
		for (int i = 0; i < arr.length; i++) {
			result += arr[i];
		}
		return result;
	}
}
