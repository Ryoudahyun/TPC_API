package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.soldesk.DownloadBroker;

public class Project02_B {
	public static void main(String[] args) {
		String url = "https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 한글 깨짐 방지. 더 빠른 , 주:
																					// InputStreamReader, 보조:
																					// BufferedReader

		try {
			System.out.print("[입력 -> 년(yyyy)-월(mm)-일(dd)]: ");
			String bible = br.readLine(); // 한 줄씩 읽기
			url = url + "&Base_de=" + bible + "&bibleType=1"; // Base_de: 날짜, bibleType: 기본1 //?: 처음 시작, &: 웹에서 변수방을 가지고
																// 다님
			System.out.println("--------------------------");
			Document doc = Jsoup.connect(url).post();
			Element bible_text = doc.select(".bible_text").first(); // 주제목
			System.out.println(bible_text.text());

			Element bibleinfo_box = doc.select(".bibleinfo_box").first(); // 소제목
			System.out.println(bibleinfo_box.text());

			Elements liList = doc.select(".body_list > li"); // 내용. first() 안 붙이면 전문 -> Elements
			System.out.println(liList.text());

			for (Element li : liList) {
				System.out.print(li.select(".num").first().text() + ": ");
				System.out.println(li.select(".info").first().text());
			}

			// 리소스(이미지, 소리, 영상) // *[@id="video"]/source
			Element tag = doc.select("source").first();
			String dPath = tag.attr("src").trim();
			System.out.println(dPath); // http://meditation.su.or.kr/meditation_mp3/2019/20191010.mp3
			String fileName = dPath.substring(dPath.lastIndexOf("/") + 1);
			System.out.println(fileName);

			// 이미지 다운로드
			Element tag1 = doc.select(".img > img").first();
			String dPath1 = "http://sum.su.or.kr:8888" + tag1.attr("src").trim();
			System.out.println(dPath1);
			String fileName1 = dPath.substring(dPath.lastIndexOf("/") + 1);

			// 다운로드
			Runnable r = new DownloadBroker(dPath, fileName);
			Thread dLoad = new Thread(r); // 스레드 구현
			dLoad.start();

			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000); // 1초
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print("" + (i + 1));
			}
			System.out.println();
			System.out.println("===============================");

		} catch (Exception e) {

		}
	}
}
