package com.tr.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tr.Base.BaseClass;




public class ExcelFile {
	
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	public String projectPath = null;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;

public static void main(String a[]) {
	
	ExcelFile datatable =  new ExcelFile("C:\\Auto\\restAssuredFramework\\InputFiles\\openweathermap.xlsx");	
	datatable.getData("ProdDBQuery","");
	
//	System.out.println(datatable.getData("Suite","").length);
		 for(int col=0 ;col< datatable.getData("ProdDBQuery","").length; col++){
//				System.out.println(datatable.getCellData("ProdDBQuery", 1,1));
			}
		 
		 Object[][] exl = new ExcelFile("C:\\Auto\\restAssuredFramework\\InputFiles\\openweathermap.xlsx").getData("Suite","");
//		 System.out.println(exl.);

		 Hashtable<String,String> excel = new Hashtable<String, String>();
		 System.out.println(excel.get("Run Mode"));
//				 new excel.get("RunMode").equalsIgnoreCase("N")
	
}
	
public ExcelFile(String path) {
		System.out.println("Excel loading started...");
	}
	
public  Object[][] getData(String sheetName, String packageName){
		
		String finalPath=null;
		String path=  ImportFiles.InputFile;
		System.out.println(ImportFiles.InputFile);
		
		String fName[] = path.split(";");
		String pName[] = packageName.split("\\.");
		
		for (int i = 0; i < fName.length; i++) {
			String fName1[] = fName[i].split("\\.");
		
		if(fName1[0].contains(pName[4])) {
			finalPath=System.getProperty("user.dir") + "\\InputFiles\\"+fName[i];
			System.out.println(finalPath);
			
			}				
		}


//		testing 
//		String sn=sheetName;
//		String finalPath="C:\\Auto\\restAssuredFramework\\InputFiles\\openweathermap.xlsx";
//				"C:\\Auto\\ContributionTool-UI\\InputData\\DCM-Testcases.xlsx";
		
		try {
			System.out.println(finalPath);
			fis = new FileInputStream(finalPath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		
		int headerRowNum=1;
		int testStartRowNum=2;
		int rows = getRowCount(sheetName);
		int cols= getColumnCount(sheetName);
		
		Object[][] data = new Object[rows-1][2];
		
		//read the data
		int dataRow=0;
		Hashtable<String,String> table=null;
		for(int rNum=testStartRowNum;rNum<=rows;rNum++){
			table = new Hashtable<String,String>();
			for(int cNum=1;cNum<cols;cNum++){
				String key=getCellData(sheetName,cNum,headerRowNum);
				String value= getCellData(sheetName, cNum, rNum);
				table.put(key, value);
				System.out.println(key+"::"+value);
			}
			data[dataRow][0] =getCellData(sheetName, "TC Name", rNum);
			data[dataRow][1]=table;
			dataRow++;
		}
		return data;
	}

public int getRowCount(String sheetName){
	int index = workbook.getSheetIndex(sheetName);
	if(index==-1)
		return 0;
	else{
	sheet = workbook.getSheetAt(index);
	int number=sheet.getLastRowNum()+1;
	return number;
	}
	
}

public int getColumnCount(String sheetName){
	// check if sheet exists
	if(!isSheetExist(sheetName))
	 return -1;
	
	sheet = workbook.getSheet(sheetName);
	row = sheet.getRow(0);
	
	if(row==null)
		return -1;
	
	return row.getLastCellNum();
	
	
	
}

public boolean isSheetExist(String sheetName){
	int index = workbook.getSheetIndex(sheetName);
	if(index==-1){
		index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
	}
	else
		return true;
}

public String getCellData(String sheetName,int colNum,int rowNum){
	try{
		if(rowNum <=0)
			return "";
	
	int index = workbook.getSheetIndex(sheetName);

	if(index==-1)
		return "";
	

	sheet = workbook.getSheetAt(index);
	row = sheet.getRow(rowNum-1);
	if(row==null)
		return "";
	cell = row.getCell(colNum);
	if(cell==null)
		return "";
	
  if(cell.getCellType()==Cell.CELL_TYPE_STRING)
	  return cell.getStringCellValue();
  else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
	  String cellText  = String.valueOf(cell.getNumericCellValue());
	  if (HSSFDateUtil.isCellDateFormatted(cell)) {
           // format in form of M/D/YY
		  double d = cell.getNumericCellValue();

		  Calendar cal =Calendar.getInstance();
		  cal.setTime(HSSFDateUtil.getJavaDate(d));
		  String  cellYear = (String.valueOf(cal.get(Calendar.YEAR)));
          int cellDay=cal.get(Calendar.DAY_OF_MONTH);
          String cellDay1 = null;
          if(cellDay<=9)
          {
        	  cellDay1="0"+cellDay;
          }
          else
          {
        	  cellDay1=String.valueOf(cellDay);
          }
         String cellMonth=DateFormatSymbols.getInstance().getMonths()[cal.get(Calendar.MONTH)].substring(0,3);
          cellText = cellDay1+ "-" +cellMonth+ "-" +cellYear;
          
          /*
            cellText =(String.valueOf(cal.get(Calendar.YEAR)));
            cellText = cal.get(Calendar.DAY_OF_MONTH) + "-" +
                      DateFormatSymbols.getInstance().getMonths()[cal.get(Calendar.MONTH)].substring(0,3)+ "-" +
                      cellText;*/
            
	  }
	  return cellText; 
	  
  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
      return "";
  else 
	  return String.valueOf(cell.getBooleanCellValue());
	}
	catch(Exception e){
		
		e.printStackTrace();
		return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
	}
}

public String getCellData(String sheetName,String colName,int rowNum){
	try{
		if(rowNum <=0)
			return "";
	
	int index = workbook.getSheetIndex(sheetName);
	int col_Num=-1;
	if(index==-1)
		return "";
	
	sheet = workbook.getSheetAt(index);
	row=sheet.getRow(0);
	for(int i=0;i<row.getLastCellNum();i++){
		if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName.trim()))
			col_Num=i;
	}
	if(col_Num==-1)
		return "";
	
	sheet = workbook.getSheetAt(index);
	row = sheet.getRow(rowNum-1);
	if(row==null)
		return "";
	cell = row.getCell(col_Num);
	
	if(cell==null)
		return "";
	
	if(cell.getCellType()==Cell.CELL_TYPE_STRING)
		  return cell.getStringCellValue();
	else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
		  
		  String cellText  = String.valueOf(cell.getNumericCellValue());
		  if (HSSFDateUtil.isCellDateFormatted(cell)) {
			  double d = cell.getNumericCellValue();
			  Calendar cal =Calendar.getInstance();
			  cal.setTime(HSSFDateUtil.getJavaDate(d));
	          String  cellYear = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
	          int cellDay=cal.get(Calendar.DAY_OF_MONTH);
	          String cellDay1 = null;
	          if(cellDay<9)
	          {
	        	  cellDay1="0"+cellDay;
	          }
	          else
	          {
	        	  cellDay1=String.valueOf(cellDay);
	          }
	         String cellMonth=DateFormatSymbols.getInstance().getMonths()[cal.get(Calendar.MONTH)].substring(0,3);
	          cellText = cellDay1+ "-" +cellMonth+ "-" +cellYear;
	        		  /*cal.get(Calendar.DAY_OF_MONTH) + "-" +
	                      DateFormatSymbols.getInstance().getMonths()[cal.get(Calendar.MONTH)].substring(0,3)+ "-" +
	                      cellText;*/
	         }
		  new SimpleDateFormat("dd-MMM-yyyy").format(cellText);
		  return cellText;
	  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
	      return ""; 
	  else 
		  return String.valueOf(cell.getBooleanCellValue());
	
	}
	catch(Exception e){
		
		e.printStackTrace();
		return "row "+rowNum+" or column "+colName +" does not exist in xls";
	}
}

public  boolean isExecutable(String tcName){
	
	for(int rowNum=2; rowNum<=getRowCount("Suite"); rowNum++){
		
		if(getCellData("Suite", "TestSheet", rowNum).trim().equalsIgnoreCase(tcName)){
			if(getCellData("Suite", "	", rowNum).equalsIgnoreCase("Y")){
				
				return true;
				
			}else{
				
				return false;
			}
		
		}
	
	}
	
	return false;
	
}
	}
	
