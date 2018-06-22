package utils.reflect;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtil {
	
	
	/** 只返回private修饰的属性生成的Map
	 * @param t 传入一个对象
	 * @return 返回一个Map，键：对象的属性 值：对象对应的属性值
	 */
	public static  <T> Map<String,Object> getPrivateParams(T t) {
		Map<String,Object> param = new HashMap<String,Object>();
		Field[] arr =  t.getClass().getDeclaredFields();
		for(int i =0;i<arr.length;i++){
			if(arr[i].getModifiers()==2){//只找private修饰符的
				arr[i].setAccessible(true);  
				try {
					param.put(arr[i].getName(), arr[i].get(t));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return param;
	}
}
