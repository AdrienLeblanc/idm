import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Utils {

	/**
	 * Creates file with parameter as content
	 * 
	 * @param filename to create
	 * @param content to write in the file
	 */
	public static void createFile(String filename, String content) {
		try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
			writer.println(content);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Executes a command and returns result as String object
	 * 
	 * @param command to execute
	 * @return result as a string
	 */
	public static String exec(String command) {
		StringBuilder builder = new StringBuilder();
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);

			BufferedReader stdInput = new BufferedReader(new 
			     InputStreamReader(proc.getInputStream()));

			String s = null;
			while ((s = stdInput.readLine()) != null) {
			    builder.append(s);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return builder.toString();
	}
}
