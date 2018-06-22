package utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import test.bean.ExcelTest;
import utils.reflect.ReflectUtil;

/**
 * 需要的jar包：jxl.jar
 * 
 * @author Jeno
 *
 */
public class ExcelUtil {

	public static void main(String[] args) {
		ExcelTest bean1 = new ExcelTest("小强", 1);
		ExcelTest bean2 = new ExcelTest("小李", 2);
		List<ExcelTest> list = new ArrayList<ExcelTest>();
		list.add(bean1);
		list.add(bean2);
		export("C:\\Users\\admin\\Desktop\\sql.xls", list);
	}

	/**
	 * 根据数据导出Excel
	 * 
	 * @param filePath
	 *            生成的Excel文件路径
	 * @param dataList
	 *            数据数组
	 */
	public static <T> void export(String filePath, List<T> dataList) {
		OutputStream os = null;
		WritableWorkbook wwb = null;
		try {
			// String filePath =
			// "C:\\Users\\Administrator\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\pinglou\\tmp\\"+filename;
			File file = new File(filePath);
			if (!file.isFile())// 如果指定文件不存在，则新建该文件
				file.createNewFile();
			os = new FileOutputStream(file);// 创建一个输出流
			wwb = Workbook.createWorkbook(os);
			WritableSheet sheet = wwb.createSheet("sheet1", 0);// 创建一个工作页，第一个参数的页名，第二个参数表示该工作页在excel中处于哪一页

			// 先在第一行插入标题
			T title = dataList.get(0);
			Map<String, Object> oneParam = ReflectUtil.getPrivateParams(title);
			int titleIndex = 0;
			for (String key : oneParam.keySet()) {
				// new Label(列，行，值)
				Label label = new Label(titleIndex++, 0, key);
				sheet.addCell(label);
			}
			// 从第二行开始插入数据
			for (int i = 1; i <= dataList.size(); i++) {
				T t = dataList.get(i - 1);
				Map<String, Object> params = ReflectUtil.getPrivateParams(t);
				int index = 0;
				for (String key : params.keySet()) {
					// new Label(列，行，值)
					Label label = new Label(index++, i, params.get(key).toString());
					sheet.addCell(label);
				}
			}
			wwb.write();
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} finally {
			try {
				if (wwb != null)
					wwb.close();
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取指定的Excel文件
	 * 
	 * @param file
	 * @return 返回一个数组，包含每行的多组键值对，键：第一行的标题，值：对应行列的值。
	 */
	public static List<Map<String, String>> readExcel(String excelPath) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			// 判断文件是否存在
			File file = new File(excelPath);
			// 创建工作簿
			Workbook workbook = Workbook.getWorkbook(file);
			// 获得第一个工作表sheet1
			Sheet[] sheets = workbook.getSheets();
			for (int a = 0; a < sheets.length; a++) {

				Sheet sheet = workbook.getSheet(a);
				System.out.println(sheet.getName());
				System.out.println(sheet.getColumns());
				System.out.println(sheet.getRows());

				// 每条数据从第几行开始累计数据
				for (int j = 1; j < sheet.getRows(); j++) {// 行
					Map<String, String> map = new LinkedHashMap<String, String>();
					for (int k = 0; k < sheet.getColumns(); k++) {// 列
						map.put(sheet.getCell(k, 0).getContents(), sheet.getCell(k, j).getContents());
						list.add(map);
					}
				}
			}
			System.out.println("end");

			workbook.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取Excel文件生成sql语句到文件
	 * 
	 * @param excelPath
	 *            需要读取的Excel文件路径，只能是xls后缀，并且文件中没有特殊的格式。
	 * @param savePath
	 *            生成sql语句的文件保存路径
	 * @return
	 */
	public static List<Map<String, String>> readExcelToFile(String excelPath, String savePath) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {

			File file = new File(excelPath);
			// 判断文件是否存在
			// 创建工作簿
			Workbook workbook = Workbook.getWorkbook(file);
			// 获得第一个工作表sheet1
			Sheet[] sheets = workbook.getSheets();
			String sql = "";
			for (int a = 0; a < sheets.length; a++) {

				Sheet sheet = workbook.getSheet(a);
				System.out.println(sheet.getName());
				System.out.println(sheet.getColumns());
				System.out.println(sheet.getRows());

				// 每条数据从第几行开始累计数据
				for (int j = 1; j < sheet.getRows(); j++) {// 行
					Map<String, String> map = new LinkedHashMap<String, String>();
					for (int k = 0; k < sheet.getColumns(); k++) {// 列
						map.put(sheet.getCell(k, 0).getContents(), sheet.getCell(k, j).getContents());
						list.add(map);
					}

					// 生成sql
					String Name = isEmpty(map.get("用汽单位"));
					String Contacts = isEmpty(map.get("联系人"));
					String ConntactTel = isEmpty(map.get("电话"));
					String Longitude = isEmpty(map.get("经度"));
					String Latitude = isEmpty(map.get("纬度"));
					String Pipediameter = isEmpty(map.get("管径"));
					String Address = isEmpty(map.get("厂址"));
					sql += "insert into dbo.tb_Project (Name,Contacts,ConntactTel,Longitude,Latitude,Pipediameter,Address)values"
							+ "(" + "'" + Name + "', " + "'" + Contacts + "', " + "'" + ConntactTel + "', " + "'"
							+ Longitude + "', " + "'" + Latitude + "', '" + Pipediameter + "', '" + Address + "');\r\n";
				}
			}

			// 将sql保存到文件
			FileWriter fw = null;
			// File f = new File("C:\\Users\\admin\\Desktop\\sql.txt");
			File f = new File(savePath);
			try {
				if (!f.exists()) {
					f.createNewFile();
				}
				fw = new FileWriter(f);
				BufferedWriter out = new BufferedWriter(fw);
				out.write(sql, 0, sql.length() - 1);
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("end");

			workbook.close();// 关闭
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String isEmpty(Object o) {
		return o == null ? "" : o.toString().trim();
	}

}