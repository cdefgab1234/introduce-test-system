package it.com.excel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Repository;


@Repository
public class InputOutput {
	

	
/**
 * 到入Excel文件
 */
	public List<Map> importExcel(String xlsPath){
		List<Map> qstList = new ArrayList<Map>();
	
		//需要解析的Excel文件
		File file = new File(xlsPath);
		try {
			//创建Excel，读取文件内容
			HSSFWorkbook workbook = 
				new HSSFWorkbook(FileUtils.openInputStream(file));
			//获取第一个工作表workbook.getSheet("Sheet0");
		   // HSSFSheet sheet = workbook.getSheet("Sheet0");
			//读取默认第一个工作表sheet
			HSSFSheet sheet = workbook.getSheetAt(0);
			int firstRowNum = 1;
			//获取sheet中最后一行行号
			int lastRowNum = sheet.getLastRowNum();
			
			for (int i = firstRowNum; i <=lastRowNum; i++) {
				HSSFRow row = sheet.getRow(i);
				//获取当前行最后单元格列号
				//int lastCellNum = row.getLastCellNum();
				
				//HSSFCell cell = row.getCell(i);
//					String value = cell.getStringCellValue();
//					System.out.print(value + "  ");
					
					//创建实体类
				
					Map<String, String> map = new HashMap<String, String>();
					if(row.getCell(0)!=null){
				          row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("detail",row.getCell(0).getStringCellValue());  
				          
				     }else {
				    	 map.put("detail",null); 
				     }
					if(row.getCell(1)!=null){
				          row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("A", row.getCell(1).getStringCellValue());     
				     }else {
				    	 map.put("A", null);   
				     }
					if(row.getCell(2)!=null){
				          row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("B", row.getCell(2).getStringCellValue());   
				     }else {
				    	  map.put("B",null);   
				     }
					if(row.getCell(3)!=null){
				          row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("C", row.getCell(3).getStringCellValue());				          
				     }else {
				    	 map.put("C", null);		
				     }
					if(row.getCell(4)!=null){
				          row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("D", row.getCell(4).getStringCellValue());
				     }else {
				    	 map.put("D",null);
				     }
					if(row.getCell(5)!=null){
				          row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("quesanswer", row.getCell(5).getStringCellValue());
				     }else {
				    	 map.put("quesanswer",null);
				     }
					if(row.getCell(6)!=null){
				          row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("diffculty", row.getCell(6).getStringCellValue());
				     }else {
				    	  map.put("diffculty",null);
				     }
					if(row.getCell(7)!=null){
				          row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("position", row.getCell(7).getStringCellValue());
				     }else {
				    	 map.put("position", null);
				     }
					if(row.getCell(8)!=null){
				          row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("kpoint", row.getCell(8).getStringCellValue());
				     }else {
				    	  map.put("kpoint",null);
				     }
					if(row.getCell(9)!=null){
				          row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
				          map.put("type", row.getCell(9).getStringCellValue());
				     }else {
				    	 map.put("type",null);
				     }
				
					
					qstList.add(map);
				}
				System.out.println();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	       return qstList;
	}
	
	//导入    .xlsx格式
	public List<?> in1(){		
		InputOutput te=new InputOutput();
		List<?> list = te.importExcel("d:\\testExcelOut.xlsx");
		return list;
	}
	//导入    .xls格式
	public List<Map> in2(String truepath){		
		InputOutput te=new InputOutput();
		List<Map> list = te.importExcel(truepath);
		return list;
	}

	
}

