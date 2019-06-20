package com.generatexl.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.generatexl.model.BulkEmployee;
import com.generatexl.utils.DBUtils;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class GenerateDao {
	
	
	public static List<BulkEmployee> generateRecords() {
		DB db = DBUtils.connection();
		
		
		DBCollection table=db.getCollection("employeedetail"); 
	      DBCursor cursor = table.find(); 
	      List<BulkEmployee> list = new ArrayList();
	      while(cursor.hasNext()) {
	    	  DBObject obj = cursor.next();
	    	  BulkEmployee emp = new BulkEmployee();
	    	  emp.setEmployeeId((double)obj.get("employeeId"));
	    	  emp.setFirst_name((String) obj.get("first_name"));
	    	  emp.setLast_name((String) obj.get("last_name"));
	    	  emp.setEmail((String) obj.get("email"));
	    	  emp.setGender((String)obj.get("gender"));
	    	  emp.setSalary( (double) obj.get("salary"));
	    	  emp.setMobile((String)obj.get("mobile"));
	    	  emp.setLocation_city((String)obj.get("location_city"));
	    	  emp.setLocation_country((String)obj.get("location_country"));
	    	  emp.setCompany((String)obj.get("company"));


	    	
	    	  list.add(emp);
	      }
	      return list;
	}
	 public static File writeStudentsListToExcel(List<BulkEmployee> list){
		 	
		 final String FILE_PATH = "/Users/JE20051838/Documents/bulkgeneratexl.xlsx";
		 
		 String[] columns = {"EmployeeId","First Name","Last Name", "Email","Gender", "Salary","Mobile","City","Country","Company"};
		 
	        // Using XSSF for xlsx format, for xls use HSSF
	        Workbook workbook = new XSSFWorkbook();
	 
	        Sheet empSheet = workbook.createSheet("EmployeeDetails");
	        
	     // Create a Font for styling header cells
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);
	        
	        // Create a CellStyle with the font
	        CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	        
	        // Create a Row
	        Row headerRow = empSheet.createRow(0);
	        
	     // Create cells
	        for(int i = 0; i < columns.length; i++) {
	            Cell cell = headerRow.createCell(i);
	            cell.setCellValue(columns[i]);
	            cell.setCellStyle(headerCellStyle);
	        }
	 
	        int rowIndex = 1;
	        for(BulkEmployee emp : list){
	            Row row = empSheet.createRow(rowIndex++);
	            int cellIndex = 0;
	            row.createCell(cellIndex++).setCellValue(emp.getEmployeeId());
	            row.createCell(cellIndex++).setCellValue(emp.getFirst_name());
	            row.createCell(cellIndex++).setCellValue(emp.getLast_name());
	            row.createCell(cellIndex++).setCellValue(emp.getEmail());
	            row.createCell(cellIndex++).setCellValue(emp.getGender());
	            row.createCell(cellIndex++).setCellValue(emp.getSalary());
	            row.createCell(cellIndex++).setCellValue(emp.getMobile());
	            row.createCell(cellIndex++).setCellValue(emp.getLocation_city());
	            row.createCell(cellIndex++).setCellValue(emp.getLocation_country());
	            row.createCell(cellIndex++).setCellValue(emp.getCompany());
	            
	           
	           
	 
	         
	 
	        }
	        
	     // Resize all columns to fit the content size
	        for(int i = 0; i < columns.length; i++) {
	        	empSheet.autoSizeColumn(i);
	        }
	        
	        
	        File file = new File(FILE_PATH);
	        //write this workbook in excel file.
	        try {
	        	//File file = new File(FILE_PATH);
	        	
	            FileOutputStream fos = new FileOutputStream(file);
	            workbook.write(fos);
	            fos.close();
	            return file;
	            
	 
	           // System.out.println(FILE_PATH + " is successfully written");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return file;
	 
	 
	    }

}
