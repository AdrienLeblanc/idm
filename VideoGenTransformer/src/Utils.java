import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Utils {

	public static void createFile(String filename, String content) {
		try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
			writer.println(content);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String exec(String command) {
		StringBuilder builder = new StringBuilder();
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			
			
		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}

		return builder.toString();
	}
}
