package utils.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadUtil {
	public static String rootPath = "http://39.105.91.19:80/pinglouImg/";
	public static String savePath = "/home/apache-tomcat-8.5.30/webapps/pinglouImg/";

	public static Map<String, Object> saveCapture(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");// ���ڶ��������ļ������ͺ���ҳ�ı��룬�������������ʲô��ʽ��ʲô�����ȡ����ļ�
		// ����������ļ�����
		Map<String, Object> resultParam = new HashMap<String, Object>();

		// �ļ�·��
		String resultfilepath = "";
		// �ļ�·������
		List<String> resultList = new ArrayList<String>();

		File file = new File(savePath);
		// �ж��ϴ��ļ����ļ�Ŀ¼�Ƿ����
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("Ŀ¼�����ڣ���Ҫ����");
			// ����Ŀ¼
			// .mkdirs() �����༶�ļ���
			boolean isOk = file.mkdirs(); // ԭ��
											// file.mkdir();ֻ�ܽ�һ�����ļ��л��γ��Ҳ����ļ�����false
			System.out.println(isOk);
		}
		// ��Ϣ��ʾ
		String message = "";
		try {
			// ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
			// 1������һ��DiskFileItemFactory���������û�������С����ʱ�ļ�Ŀ¼
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2������һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 3.�����ϴ��ļ��������ʽ
			upload.setHeaderEncoding("UTF-8");

			// 4��ʹ��ServletFileUpload.parseRequest�����������ϴ����ݣ�����������ص���һ�������ϴ�����List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// isFormField()�����ж�ĳ���Ƿ�����ͨ�ı����͡�
				// ���FileItem�з�װ������ͨ�����������
				if (item.isFormField()) {
					String name = item.getFieldName();
					// �����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					System.out.println(name + "=" + value);
					resultParam.put(name, value);
				} else {
					// ����ñ�����file ���͵�
					// �õ��ϴ����ļ����ƣ�
					String filename = item.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺
					// c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					// �����ȡ�����ϴ��ļ����ļ���·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("\\") + 1);// lastIndexOf�����ֵ�λ��
					// ��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					// ����Զ�̷��������ļ�.
					filename = new Date().getTime() + "." + FileUtil.getFileSuffix(filename);
					// //����ͼƬ�ļ�����ʽ
					// filename= FileUtil.getFileNameNoEx(filename)+"_"+
					// DateUtils.toShortDateTime(new Date())+
					// ".png";
					// ����һ���ļ������ ���浽������
					FileOutputStream out = new FileOutputStream(savePath + filename);
					//
					resultfilepath = rootPath + filename;

					resultList.add(resultfilepath);
					// ����һ��������
					byte buffer[] = new byte[1024];
					// �ж��������е������Ƿ��Ѿ�����ı�ʶ
					int len = 0;
					// ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while ((len = in.read(buffer)) > 0) {
						// ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\"
						// + filename)����
						out.write(buffer, 0, len);
					}
					// // �ر�������
					in.close();
					// // �ر������
					out.close();
					// ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					item.delete();
					message = "�ļ��ϴ��ɹ���";
				}
			}
		} catch (Exception e) {
			message = "�ļ��ϴ�ʧ�ܣ�";
			e.printStackTrace();
		}
		System.out.println(message);
		System.out.println(resultList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("images", resultList);
		if (resultParam.size() > 1) {
			return result;
		}
		result.put("param", resultParam);
		return result;
	}

}
