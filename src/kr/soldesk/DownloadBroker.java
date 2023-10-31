package kr.soldesk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadBroker implements Runnable{

	private String address;
	private String fileName;
	public DownloadBroker(String address, String fileName) {
		super();
		this.address = address;
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		try {
			//파일 이름 읽어들임
			FileOutputStream fos = new FileOutputStream(fileName);
			
			//쓰기 위한 스트림 생성
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			URL url = new URL(address);	//실제 다운 받는 사이트로 연결
			InputStream is = url.openStream();	//url 인식
			
			//속도가 빠른 버퍼
			BufferedInputStream input = new BufferedInputStream(is);
			
			int data;
			while((data = input.read()) != -1) {
				bos.write(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
