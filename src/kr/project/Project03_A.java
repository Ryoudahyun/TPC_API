package kr.project;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import kr.soldesk.ExcelVO;

public class Project03_A {
	public static void main(String[] args) {
		String fileName = "D:\\Web_20231019\\TPC_API\\src\\kr\\project\\bookList.xls";
		
		List<ExcelVO> data = new ArrayList<ExcelVO>();
		
		try(FileInputStream fis = new FileInputStream(fileName)) {
			HSSFWorkbook workbook = new HSSFWorkbook(fis);	//bookList.xls 메모리에 적재
			HSSFSheet sheet = workbook.getSheetAt(0);	//sheet
			Iterator<Row> rows = sheet.rowIterator();	//행
			rows.next();	//첫번째 줄은 지나가기
			String[] imsi = new String[5];
			
			while(rows.hasNext()) {
				HSSFRow row = (HSSFRow) rows.next();	//줄
				Iterator<Cell> cells = row.cellIterator();	//컬럼
				int i = 0;
				//셀데이터 가져오기
				while(cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next(); 
					imsi[i] = cell.toString();
					i++;
				}
				ExcelVO vo = new ExcelVO(imsi[0], imsi[1], imsi[2], imsi[3], imsi[4]);
				data.add(vo);
			}
			showExcelData(data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showExcelData(List<ExcelVO> data) {
		for(ExcelVO vo : data) {
			System.out.println(vo);
		}
	}
}
