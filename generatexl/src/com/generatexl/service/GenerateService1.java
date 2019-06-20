package com.generatexl.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.generatexl.dao.GenerateDao;
import com.generatexl.model.BulkEmployee;
import com.generatexl.model.Employee;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Path("/generate1")
public class GenerateService1 {
	
	private static final String FILE_PATH = "d:\\excel-file.xlsx";
	
	@GET
	@Path("/getRecords")
	@Produces("application/vnd.ms-excel")
	public Response getRecords() throws FileNotFoundException, IOException {
	
	      
	      List<BulkEmployee> list = GenerateDao.generateRecords();
	      
	     File file= GenerateDao.writeStudentsListToExcel(list);

		  ResponseBuilder response = Response.ok((Object) file);
		  response.header("Content-Disposition","attachment; filename=new-excel-file.xlsx");

		  return response.build();
	 
	      
	  
	}
	
	

}
