package com.me.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOutHandler {
	@SuppressWarnings("resource")
	public static void OutputToHomeFile(List<List<String>> list,String outTag) throws IOException {
		String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
		// 导出文件路径
		String basePath = System.getProperty("user.dir");
		// 文件名
		String exportFileName = outTag+"数据_" + now + ".xlsx";
		// 表头
		 String[] cellTitle = {"全网代理超时时间","全网代理延迟","芝麻代理超时时间","芝麻代理延迟"};
		// 声明一个工作薄
		XSSFWorkbook workBook = null;
		workBook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workBook.createSheet();
		workBook.setSheetName(0, "sheet1");
		// 创建表格标题行 第一行
		 XSSFRow titleRow = sheet.createRow(0);
//		 for(int i=0;i<cellTitle.length;i++){
//		 titleRow.createCell(i).setCellValue(cellTitle[i]);
//		 }
		// 插入需导出的数据
		for (int i = 1; i < list.size()+1; i++) {
			XSSFRow row = sheet.createRow(i);
			//存放一条数据的
			List<String> dataList = list.get(i-1);
			for (int j = 0; j < dataList.size(); j++) {
				row.createCell(j).setCellValue(dataList.get(j));
			}

		}
		File file = new File(basePath + File.separator + exportFileName);
		// 文件输出流
		FileOutputStream outStream = new FileOutputStream(file);
		workBook.write(outStream);
		outStream.flush();
		outStream.close();
		System.out.println("导出2007文件成功！文件导出路径：--" + basePath + File.separator + exportFileName);
	}

}
