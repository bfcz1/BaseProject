package com.me.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;

public class NewTest3 {


    public static void main(String[] args) {

        long sum = 0l;

        String dirPath = "C:\\Users\\dell、\\Desktop\\代理数据";
        File[] files = new File(dirPath).listFiles();
        for (File file : files) {
            if (StringUtils.contains(file.getName(), "全网并发6")) {

                File[] excelDir = file.listFiles();
                for (File execel : excelDir) {

                    int i = readeExcel2List(execel.getPath());
                    sum = sum + (long) i;


                }


            }


        }
        System.out.println("平均延时"+(sum/100l)+"ms");


    }


    public static int readeExcel2List(String fileName) {

        int value = 8000;
        try {
            // 读取的时候可以使用流，也可以直接使用文件名
            XSSFWorkbook xwb = new XSSFWorkbook(fileName);
            // 循环工作表sheet
            for (int numSheet = 0; numSheet < xwb.getNumberOfSheets(); numSheet++) {
                XSSFSheet xSheet = xwb.getSheetAt(numSheet);
                if (xSheet == null) {
                    continue;
                }
                // 循环行row
                XSSFCell cell = xSheet.getRow(1).getCell(0);
                String valueStr = getValue(cell);
                value = Integer.parseInt(valueStr);

            }
            xwb.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    @SuppressWarnings("deprecation")
    private static String getValue(XSSFCell xCell) {
        if (xCell.getCellType() == XSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xCell.getBooleanCellValue());
        } else if (xCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(xCell.getNumericCellValue());
        } else {
            return String.valueOf(xCell.getStringCellValue());
        }
    }
}
