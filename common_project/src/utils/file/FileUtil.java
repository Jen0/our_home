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
     * ��ȡָ�������ļ��в���ֵ  
     * @param propertyName  
     *        ���÷�ʽ��  
     *            1.�����ļ�����resourceԴ���£����üӺ�׺ !!!!
     *              FileUtil.getAllMessage("message");  
     *            2.���ڰ������  
     *              FileUtil.getAllMessage("com.test.message");  
     * @return  
     */  
    public static String getKeyValue(String propertyName,String param) {  
        // �����Դ��  
        ResourceBundle rb = ResourceBundle.getBundle(propertyName.trim());  
        // ͨ����Դ���õ����е�key  
        Enumeration<String> allKey = rb.getKeys();  
        // ����key �õ� value  
        while (allKey.hasMoreElements()) {  
            String key = allKey.nextElement();  
            if(key.equals(param))//ȡ��������key
            return rb.getString(key); 
        }  
        return "";  
    }  
	
	
	/** ��ȡ�ļ����ƣ���������׺��
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
	
	/** ��ȡ�ļ���׺��
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
