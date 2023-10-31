package kr.project;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_C {
	public static void main(String[] args) {
		String src = "info.json";
		//IO -> Stream 
		InputStream is = Project01_C.class.getResourceAsStream(src);
		
		if(is == null) {
			throw new NullPointerException("Cannot find resource file");
		}
		JSONTokener tokener = new JSONTokener(is);	//문자열을 객체화 시켜서 메모리에 load함
		JSONObject object = new JSONObject(tokener);	//객체를 JSON의 object로 변환
		
		JSONArray students = object.getJSONArray("students");	//Array 타입으로 변경
		
		for(int i = 0; i<students.length(); i++) {
			JSONObject student = (JSONObject)students.get(i);
			System.out.println(student.get("name")+"\t");
			System.out.println(student.get("address")+"\t");
			System.out.println(student.get("phone")+"\t");
		}
	}
}
