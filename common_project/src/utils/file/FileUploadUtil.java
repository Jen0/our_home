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
		response.setContentType("text/html;charset=utf-8");// 用于定义网络文件的类型和网页的编码，决定浏览器将以什么形式、什么编码读取这个文件
		// 存放其他非文件参数
		Map<String, Object> resultParam = new HashMap<String, Object>();

		// 文件路径
		String resultfilepath = "";
		// 文件路径数组
		List<String> resultList = new ArrayList<String>();

		File file = new File(savePath);
		// 判断上传文件和文件目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("目录不存在，需要创建");
			// 创建目录
			// .mkdirs() 建立多级文件夹
			boolean isOk = file.mkdirs(); // 原来
											// file.mkdir();只能建一级的文件夹会形成找不到文件返回false
			System.out.println(isOk);
		}
		// 消息提示
		String message = "";
		try {
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂，设置缓冲区大小和临时文件目录
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 3.设置上传文件名编码格式
			upload.setHeaderEncoding("UTF-8");

			// 4、使用ServletFileUpload.parseRequest解析器解析上传数据，解析结果返回的是一个所有上传内容List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// isFormField()方法判断某项是否是普通的表单类型。
				// 如果FileItem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					System.out.println(name + "=" + value);
					resultParam.put(name, value);
				} else {
					// 否则该表单项是file 类型的
					// 得到上传的文件名称，
					String filename = item.getName();
					System.out.println(filename);
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
					// c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);// lastIndexOf最后出现的位置
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 传到远程服务器上文件.
					filename = new Date().getTime() + "." + FileUtil.getFileSuffix(filename);
					// //设置图片文件名格式
					// filename= FileUtil.getFileNameNoEx(filename)+"_"+
					// DateUtils.toShortDateTime(new Date())+
					// ".png";
					// 创建一个文件输出流 保存到服务器
					FileOutputStream out = new FileOutputStream(savePath + filename);
					//
					resultfilepath = rootPath + filename;

					resultList.add(resultfilepath);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
						// + filename)当中
						out.write(buffer, 0, len);
					}
					// // 关闭输入流
					in.close();
					// // 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();
					message = "文件上传成功！";
				}
			}
		} catch (Exception e) {
			message = "文件上传失败！";
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
