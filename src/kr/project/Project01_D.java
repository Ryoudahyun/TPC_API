package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_D {
	public static void main(String[] args) {
		String client_id = "ad25ohl6j3";
		String client_secret = "F91pHjawz06REbzKuFbpCMb6EHXgDF267EtcdRXE";

		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("주소를 입력하세요: ");
			String address = io.readLine();
			String addr = URLEncoder.encode(address, "UTF-8"); // 한글 보정
			String reqUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; // json

			URL url = new URL(reqUrl);
			// HttpsURLConnection: 연결해주는 API 클래스
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
			con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);

			// 예외처리
			BufferedReader br;
			int responseCode = con.getResponseCode(); // 200

			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); // 한글보정까지 받음
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream())); // 에러스트림
			}

			String line;
			StringBuffer response = new StringBuffer(); // StringBuffer 객체생성하여 줄단위로 스트림 읽어와서 입력
			while ((line = br.readLine()) != null) {
				response.append(line); // 스트림버퍼에 담기
			}
			br.close();

			JSONTokener tokener = new JSONTokener(response.toString()); // 전처리작업 : 스트림버퍼 객체화시킴
			JSONObject object = new JSONObject(tokener);// JSON의 object로 변환
			System.out.println(object.toString());
			JSONArray arr = object.getJSONArray("addresses"); // Array타입으로 변경

			for (int i = 0; i < arr.length(); i++) {
				JSONObject temp = (JSONObject) arr.get(i);
				System.out.println("address:" + temp.get("roadAddress"));
				System.out.println("jibunAddress:" + temp.get("jibunAddress"));
				System.out.println("경도:" + temp.get("x"));
				System.out.println("위도:" + temp.get("y"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
