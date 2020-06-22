import java.io.File;
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
		
		while(true) {
			nh.nhentai();	
		}
	}

}
