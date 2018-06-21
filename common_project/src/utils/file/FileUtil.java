package utils.file;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class FileUtil {
	
	public static final String ROOTPATH = getKeyValue("config", "rootPath");
	public static final String SAVEPATH = getKeyValue("config", "savePath");

	
	public static void main(String[] args) {
		System.out.println(ROOTPATH);
		System.out.println(SAVEPATH);
	}
	
	/**  
     * 获取指定配置文件中参数值  
     * @param propertyName  
     *        调用方式：  
     *            1.配置文件放在resource源包下，不用加后缀 !!!!
     *              FileUtil.getAllMessage("message");  
     *            2.放在包里面的  
     *              FileUtil.getAllMessage("com.test.message");  
     * @return  
     */  
    public static String getKeyValue(String propertyName,String param) {  
        // 获得资源包  
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());  
        // 通过资源包拿到所有的key  
        Enumeration<String> allKey = rb.getKeys();  
        // 遍历key 得到 value  
        while (allKey.hasMoreElements()) {  
            String key = allKey.nextElement();  
            if(key.equals(param))//取出给定的key
            return rb.getString(key); 
        }  
        return "";  
    }  
	
	
	/** 获取文件名称，不包含后缀名
	 * @param fileName
	 * @return
	 */
	public static String getFileNameNoSuffix(String fileName){
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length()))) {
				return fileName.substring(0, dot);
			}
		}
		return fileName;
	}
	
	/** 获取文件后缀名
	 * @param filename
	 * @return
	 */
	public static String getFileSuffix(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

}
