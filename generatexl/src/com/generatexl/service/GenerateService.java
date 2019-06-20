package com.generatexl.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.generatexl.model.Employee;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("/generate")
public class GenerateService {
	
	

	
	@GET
	@Path("/getRecords")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getRecords() {
		MongoClient Db=new MongoClient();
		DB db=Db.getDB("mydb");  
		
		DBCollection table=db.getCollection("employeedetail"); 
	      DBCursor cursor = table.find(); 
	      List<Employee> list = new ArrayList();
	      while(cursor.hasNext()) {
	    	  DBObject obj = cursor.next();
	    	  Employee emp = new Employee();
	    	  emp.setName((String) obj.get("first_name"));
	    	  emp.setEmail((String) obj.get("email"));
	    	  emp.setSalary( (double) obj.get("salary"));

	    	
	    	  list.add(emp);
	      }
	      
	      writeStudentsListToExcel(list);
	      
	      
	   return list;
	}
	
	 public static void writeStudentsListToExcel(List<Employee> list){
		 	
		 final String FILE_PATH = "/Users/JE20051838/Documents/generatexl.xlsx";
		 
		 String[] columns = {"Name", "Email", "Salary"};
		 
	        // Using XSSF for xlsx format, for xls use HSSF
	        Workbook workbook = new XSSFWorkbook();
	 
	        Sheet empSheet = workbook.createSheet("Employee");
	        
	     // Create a Font for styling header cells
	        Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 14);
	        
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
	        for(Employee emp : list){
	            Row row = empSheet.createRow(rowIndex++);
	            int cellIndex = 0;
	           
	            row.createCell(cellIndex++).setCellValue(emp.getName());
	 
	           
	            row.createCell(cellIndex++).setCellValue(emp.getEmail());
	 
	           
	            row.createCell(cellIndex++).setCellValue(emp.getSalary());
	 
	         
	 
	        }
	        
	     // Resize all columns to fit the content size
	        for(int i = 0; i < columns.length; i++) {
	        	empSheet.autoSizeColumn(i);
	        }
	        
	        
	 
	        //write this workbook in excel file.
	        try {
	            FileOutputStream fos = new FileOutputStream(FILE_PATH);
	            workbook.write(fos);
	            fos.close();
	 
	            System.out.println(FILE_PATH + " is successfully written");
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	 
	    }
}
