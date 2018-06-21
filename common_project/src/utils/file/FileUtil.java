package utils.file;

public class FileUtil {
	public static void main(String[] args) {
		System.out.println(getFileSuffix("safdsa.123"));
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
