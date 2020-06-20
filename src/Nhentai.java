import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Nhentai extends Main {
	
	public static void nhentai() {
		
		// ask user while input is false
		boolean isTrue = false;
		do {
			try {
				// ask user for link
				String url;
				System.out.print(">> Enter code: ");
				url = sc.nextLine();

				// get link
				Document doc = Jsoup
						.connect("https://nhentai.net/g/" + url + "/")
						.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
								+ "AppleWebKit/537.36 (KHTML, like Gecko) "
								+ "Chrome/83.0.4103.106 Safari/537.36")
						.timeout(10*2000)
						.ignoreHttpErrors(true)
						.get();
				
				// get manga name and manga code
				String mangaName = doc.select("div#info > h1 > span.pretty").text();
				String mangaCode = url;
				System.out.println(">> Downloading: " + mangaName);
				
				// create manga code folder
				File mangaDir = new File("C:\\Manga\\" + mangaCode);
				if(!mangaDir.exists()) {
					mangaDir.mkdir();
				}
				
				// create title.txt and write the title in it
				BufferedWriter txtTitle = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
						"C:\\Manga\\" + mangaCode + "\\" + "title.txt"), "utf-8"));
				txtTitle.write(mangaName);
				txtTitle.close();
				
				// make folder for chapters
				String chapNum = mangaCode;
				File chapDir = new File("C:\\Manga\\" + mangaCode + "\\" + chapNum);
				if(!chapDir.exists()) {
					chapDir.mkdir();
				}
				
				// select 'a' tag
				Elements selectAElements = doc.select("a.gallerythumb");
				
				// iterate over each image link
				for(Element aElement : selectAElements) {
					// get link prefix
					String getAUrl = aElement.attr("href");
					downloadImage(getAUrl, chapNum, mangaCode);
				}
				
			} catch(IOException e) {
				System.out.println(">> No internet access.");
			}
			
			System.out.println(">> Download is complete.");
			System.out.println();
		} while(!isTrue);
	}
	
	
	public static void downloadImage(String getAUrl, String chapNum, String mangaCode) {
		
		try {
			// open stream from first link
			URL getUrl = new URL("https://nhentai.net" + getAUrl);
			InputStream is = getUrl.openStream();

			// get second link
			Document doc2 = Jsoup
					.connect(getUrl.toString()) // convert URL to string
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
							+ "AppleWebKit/537.36 (KHTML, like Gecko) "
							+ "Chrome/83.0.4103.106 Safari/537.36")
					.timeout(10*2000)
					.ignoreHttpErrors(true)
					.get();
			
			// get image element
			Elements selectImgElements = doc2.select("section#image-container img");
			String imgUrl = "";
			for(Element imgElement : selectImgElements) {
				imgUrl = imgElement.attr("abs:src");
				
				// get image name
				String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
				
				// open stream from second link
				URL getImgUrl = new URL(imgUrl);
				is = getImgUrl.openStream();
				
				URLConnection con = getImgUrl.openConnection();
				con.connect();
				
				// get file size
				int lengthOfFile = con.getContentLength();
				System.out.println(">> [" + lengthOfFile / 1024 + "kb]");	
				
				byte[] buffer = new byte[4096];
				int n = -1;
				
				// destination folder
				OutputStream os = new FileOutputStream("C:\\Manga" + "\\" + mangaCode + "\\" + 
				chapNum + "\\" + imgName);
				
				// download image
				while((n = is.read(buffer)) != -1) {
					os.write(buffer, 0, n);
				}
				os.close();
			}	
			
		} catch(IOException e) {
			System.out.println(">> Error: Access denied.");
		}
	}
	
}
