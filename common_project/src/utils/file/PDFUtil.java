package utils.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;


/** 需要引入jar包：itextpdf-5.5.9.jar，itext-asian-5.2.0.jar，需要注意两个版本兼容问题
 * @author Jeno
 *
 */
public class PDFUtil {
	
//	 Font font = null;
//	 try {  
//            // 中文支持，需要引入 itext-asian.jar  
//		 BaseFont chinessFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
//		 font = new Font(chinessFont, 10, Font.NORMAL);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }

	/**
	 * 图文生成pdf
	 * 
	 * @param filess
	 *            多张图片的保存路径，例如：D:\\projectPath\\55555.jpg
	 * @param pdfPath
	 *            pdf的保存路径，例如：D:\\projectPath\\55555.pdf
	 * @param word
	 *            最后pdf添加文章内容
	 * @return
	 * @throws IOException
	 */
	public static boolean imgToPdf(String[] filess, String pdfPath, String word){

		try {
			// 图片文件夹地址
			String imagePath = null;
			// PDF文件保存地址
			// 输入流
			FileOutputStream fos = new FileOutputStream(pdfPath);
			// 创建文档
			Document doc = new Document(null, 0, 0, 0, 0);

			// 写入PDF文档
			PdfWriter.getInstance(doc, fos);
		  
			// 读取图片流
			BufferedImage img = null;
			// 实例化图片
			Image image = null;
			// 循环获取图片文件夹内的图片

			for (String imageFolderPath : filess) {
				if (imageFolderPath.endsWith(".png") || imageFolderPath.endsWith(".jpg")
						|| imageFolderPath.endsWith(".gif") || imageFolderPath.endsWith(".jpeg")
						|| imageFolderPath.endsWith(".bmp")) {

					// System.out.println(file1.getName());
					imagePath = imageFolderPath;
					// 读取图片流
					img = ImageIO.read(new File(imagePath));
					// 根据图片大小设置文档大小
					doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
					// 实例化图片
					image = Image.getInstance(imagePath);
					// 添加图片到文档
					doc.open();

					doc.add(image);
				}
			}
			doc.setPageSize(PageSize.B6.rotate());
			doc.open();
			Paragraph title = new Paragraph(word,
//					FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 255, 17)));
			FontFactory.getFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED, 16, Font.BOLD, new CMYKColor(0, 255, 255, 17)));
		
			title.setFirstLineIndent(30f);

			doc.add(title);
			System.out.println("生成pdf成功");
			// 关闭文档
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}



	public static void main(String[] args) {
			imgToPdf(new String[0],"D://1.pdf","safdsafdsafdssafdsa法师法师打sadfsadfdsa123；‘】【p都是");
		
	}

}
