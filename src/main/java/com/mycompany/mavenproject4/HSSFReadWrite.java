package com.mycompany.mavenproject4;

import java.io.File; 
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;

public class HSSFReadWrite {

	public static void main (String[] args) throws Exception{
		
		int[] serial = new int[10];
		for(int i=0 ; i<serial.length ; i++ ) {
			serial[i] = i+1;
		}
		
		String[] name = new String[10];
		name[0] = "studentA";
		name[1] = "studentA";
		name[2] = "studentA";
		name[3] = "studentA";
		name[4] = "studentA";
		name[5] = "studentA";
		name[6] = "studentA";
		name[7] = "studentA";
		name[8] = "studentA";
		name[9] = "studentA";
		
		String[] result = new String[10];
		result[0] = "Pass";
		result[1] = "Pass";
		result[2] = "Pass";
		result[3] = "Pass";
		result[4] = "Pass";
		result[5] = "Pass";
		result[6] = "Pass";
		result[7] = "Pass";
		result[8] = "Pass";
		result[9] = "Pass";
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		XSSFSheet sheet = workbook.createSheet("Results");
		
		XSSFRow row;
		
		row = sheet.createRow(0);
		Cell cell0 = row.createCell(0);
		Cell cell1 = row.createCell(1);
		Cell cell2 = row.createCell(2);
		
		cell0.setCellValue("Serial no");
		cell1.setCellValue("Name of the students");
		cell2.setCellValue("Results");
		
		for(int i=0 ; i<serial.length ; i++ ) {
			row = sheet.createRow(i+1);
			
			for(int j=0 ; j<3 ; j++) {
				Cell cell = row.createCell(j);
				if(cell.getColumnIndex() == 0) {
					cell.setCellValue(serial[i]);
				}else if(cell.getColumnIndex() == 1) {
					cell.setCellValue(name[i]);
				}else if(cell.getColumnIndex() == 2) {
					cell.setCellValue(result[i]);
				}
			}
		}
		
		try {
			FileOutputStream out = new FileOutputStream(new File("Results.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("Excel file is created!");
		}catch(Exception e) {
			System.out.println("ERROR"  + e.toString());
		}
		
    }
}
