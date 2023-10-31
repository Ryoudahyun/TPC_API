package kr.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_E {
    //지도 이미지 생성 메서드
   public static void map_service(String point_x, String point_y, String address) {
      
      String client_id="ad25ohl6j3";
      String client_secret="F91pHjawz06REbzKuFbpCMb6EHXgDF267EtcdRXE";
      String URL_STATICMAP="https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
      
      try {
         //공백이 입력될시 끝으로 인지하여 이후 코드는 읽지못하므로 UTF-8로 공백을 %20으로 변환히여 position설정
         String pos=URLEncoder.encode(point_x+" " + point_y , "UTF-8");
         String url=URL_STATICMAP;
         url += "center=" +point_x+ "," + point_y; //찾고자 하는 위치가 정 가운데 오도록 설정
         url += "&level=16&w=700&h=500"; //700X500
         
         //풍선도움말
         url += "&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8");
         URL u=new URL(url);
         //HttpsURLConnection : 연결해주는 API클래스
         HttpsURLConnection con=(HttpsURLConnection)u.openConnection();
         con.setRequestMethod("GET");
         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
         con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
         int responseCode = con.getResponseCode();
          BufferedReader br;
              if(responseCode==200) { // 정상 호출
                      InputStream is = con.getInputStream();
                      
                      int read = 0;
                      byte[] bytes = new byte[1024]; //이미지는 byte단위로 받아옴
                      // 랜덤한 이름으로 파일 생성
                      String tempname = Long.valueOf(new Date().getTime()).toString();
                      File f = new File(tempname + ".jpg");   
                      f.createNewFile(); //파일생성
                      
                      OutputStream outputStream = new FileOutputStream(f); //출력스트림
                     
                      while ((read =is.read(bytes)) != -1) { //-1: 끝이 아니면
                          outputStream.write(bytes, 0, read); //0부터 읽어들인만큼 바이트로 저장
                      }//while
                      is.close();
              }else { //에러발생
                 br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                     String inputLine;
                     StringBuffer response = new StringBuffer();
                     while ((inputLine = br.readLine()) != null) {
                         response.append(inputLine);
                     }
                     br.close();
                     System.out.println(response.toString());
                 }
      } catch (Exception e) {
         System.out.println(e);
      }
      
   }
   
   public static void main(String[] args) {
      //apiurl :  "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode"
      
      String client_id="ad25ohl6j3";
      String client_secret="F91pHjawz06REbzKuFbpCMb6EHXgDF267EtcdRXE";
      
      BufferedReader io=new BufferedReader(new InputStreamReader(System.in));
      
      try {//요청
         System.out.print("주소를 입력하세요 : ");
         String address=io.readLine();
         String addr=URLEncoder.encode(address, "UTF-8"); //한글보정
         String reqUrl="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
         
         URL url=new URL(reqUrl);
         //HttpsURLConnection : 연결해주는 API클래스
         HttpsURLConnection con=(HttpsURLConnection)url.openConnection();
         con.setRequestMethod("GET");
         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id);
         con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
         
         //예외처리
         BufferedReader br; //BufferedReader : 한글깨짐없이 라인단위로 읽어들임
         int responseCode=con.getResponseCode(); //200
         
         if(responseCode ==200) { //정상접속이 되면
            br=new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8")); //한글보정까지 해서 라인단위로 받음
         }else {
            br=new BufferedReader(new InputStreamReader(con.getErrorStream())); //에러스트림 
         }
         
         String line;
         StringBuffer response=new StringBuffer(); //StringBuffer 객체생성
         String x="";
         String y="";
         String z="";
         

         while((line=br.readLine())!=null){
            response.append(line); //스트림버퍼에 담기
         }
         br.close();
         
         JSONTokener tokener=new JSONTokener(response.toString()); //전처리작업 : 스트림버퍼 객체화시킴
         JSONObject object=new JSONObject(tokener);//JSON의 object로 변환
         System.out.println(object.toString());
         
         JSONArray arr=object.getJSONArray("addresses"); //Array타입으로 변경
         for(int i=0;i<arr.length();i++) {
            JSONObject temp=(JSONObject)arr.get(i);
             System.out.println("address:" + temp.get("roadAddress"));
             System.out.println("jibunAddress:" + temp.get("jibunAddress"));
             System.out.println("경도:" + temp.get("x"));
             System.out.println("위도:" + temp.get("y"));
             //추가선언
             x=(String)temp.get("x");
             y=(String)temp.get("y");
             z=(String)temp.get("roadAddress");
         }
         map_service(x,y,z);      
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      
   }

}