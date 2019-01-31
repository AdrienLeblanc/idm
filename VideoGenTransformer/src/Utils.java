import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Utils {

	public static void createFile(String filename, String content) {
		try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
			writer.println(content);
			System.out.print(content);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void exec(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			process.destroy();
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}
