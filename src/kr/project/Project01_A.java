package kr.project;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.soldesk.BookDTO;

public class Project01_A {

	public static void main(String[] args) {
		
		//Object(BookDTO) -> JSON(String)
		BookDTO dto = new BookDTO("JAVA", 21000, "에이콘", 680);
		Gson g = new Gson();
		String json = g.toJson(dto);	//BookDTO의 데이터(객체)를 json으로 변환
		
		System.out.println("----- Object(BookDTO) -> JSON(String) -----");
		System.out.println(json);
		
		
		//JSON(String) -> Object(BookDTO)
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		
		System.out.println("----- JSON(String) -> Object(BookDTO) -----");
		System.out.println(dto1);
		System.out.println("도서제목: " + dto.getTitle() + ", 가격: " + dto.getPrice());	//json 형식이 다시 자바가 됨
																					//gson: 객체 -> json, json -> 객체
		//Object(List<BookDTO>) -> JSON(String) : [ {...}, {...}, {...} ]
		
		List<BookDTO> lst = new ArrayList<BookDTO>();
		lst.add(new BookDTO("JSP", 28000, "솔데스크", 720));
		lst.add(new BookDTO("JPA", 32000, "솔데스크", 680));
		lst.add(new BookDTO("Spring", 54000, "솔데스크", 900));
		System.out.println("----- Object(List(BookDTO)) -> JSON(String) -----");
		String lst_Json = g.toJson(lst);
		System.out.println(lst_Json);
		
		System.out.println("----- JSON(String) -> Object(List(BookDTO)) -----");
		List<BookDTO> lst1 = g.fromJson(lst_Json, new TypeToken<List<BookDTO>>() {}.getType());
		for(BookDTO vo:lst1) {
			System.out.println(vo);
		}
	}
}
