package mypro01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadFile {
	public static void main(String[] args) {
		Path path = Paths.get("D:\\test\\a.txt");
		try {
			//Files.readAllBytes最大支持2GB的文件。超过会报OutOfMemoryError异常
			byte[] data = Files.readAllBytes(path);
			String s = new String(data);
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
