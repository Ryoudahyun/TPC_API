package kr.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;

//메모리에 가상의 이미지를 만들어 저장하고 불러오는 프로젝트
public class Project03_B {
	public static void main(String[] args) {
		try {
			Workbook wb = new HSSFWorkbook(); //워크북 생성
			Sheet sheet = wb.createSheet("My Sample Excel"); //sheet 생성
			InputStream is = new FileInputStream("D:\\Web_20231019\\TPC_API\\src\\kr\\project\\pic.jpg"); //이미지 파일 읽어오기: 이미지 -> byte -> int
			byte[] bytes = IOUtils.toByteArray(is); //이미지는 byte 배열로 변환해야함
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG); //워크북 안의 메모리에 이미지 load
			is.close();
			
			//실제로 이미지 드로잉을 도와주는 메소드
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch();	//시트에 이미지 드로잉 객체 생성
			ClientAnchor anchor = helper.createClientAnchor(); //Anchor: (그림 그릴)위치지정
			
			anchor.setCol1(1); //첫번째 컬럼의 앵커(열)
			anchor.setRow1(2); //첫번째 컬럼의 두번째 줄(행)
			anchor.setCol2(2); //두번째 컬럼
			anchor.setRow2(3); //세번째 줄
			
			//지정된 위치 이미지 생성
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			
			Cell cell = sheet.createRow(2).createCell(1);
			int w = 20 * 256 * 1 / 256; //폭 하나당 256분의 1
			sheet.setColumnWidth(1, w); //폭 설정
			
			short h = 120 * 20; //(폭 하나당 20)
			cell.getRow().setHeight(h); //높이 설정
			
			FileOutputStream fileOut = new FileOutputStream("myFile.xls"); //Excel 파일 생성
			wb.write(fileOut); //파일 이미지 저장
			fileOut.close();
			
			System.out.println("이미지 저장 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
