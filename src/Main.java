import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		// create folder named manga to c:\
		File file = new File("C:\\Manga");
		if(!file.exists()) {
			file.mkdir();
			System.out.println(">> Manga directory is created in Local Disk C!!");
		}
		
		Nhentai nh = new Nhentai();
		
		boolean isTrue = false;
		do {
			System.out.println(">> [1] NHentai");
			System.out.print(">> ");
			int userInput = sc.nextInt();
			if(userInput == 1) {
				sc.nextLine();
				nh.nhentai();
			}
		} while(!isTrue);
		
		// prevents console from closing
		try {
			System.in.read();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
