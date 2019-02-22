import java.io.File;

import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Génère des playlists depuis le dossier téléchargé depuis Google Drive
 *  -- 10 playlists générées par sous-dossiers -- 
 * 
 * @author adrien
 *
 */
public class GenerateFromDrive {

	public static void main(String[] args) {
		String input = "drive";
		new GenerateFromDrive(input);
	}

	public GenerateFromDrive(String pathToFolder) {
		File root = new File(pathToFolder);
		File[] binomes = root.listFiles();
		for (File binome : binomes) {
			File[] files = binome.listFiles();
			for (File file : files) {
				if (file.getName().endsWith(".videogen")) {
					generate10Playlists(file);
					break;
				}
			}
		}
	}

	private void generate10Playlists(File videogen) {
		String dir =  "";
		for (int i = 0; i < videogen.getName().length(); i++) {
			if (videogen.getName().charAt(i) != '.') {
				dir += videogen.getName().charAt(i);
			} else {
				break;
			}
		}
		createDirectoryIfDoesntExists("playlists/" + dir);
		for (int i = 0; i < 10; i++) {

			String generatedTxt =  i + "_" + dir + ".txt";
			
			VideoGeneratorModel videoGen = new VideoGenHelper()
					.loadVideoGenerator(URI.createURI(videogen.getPath()));
			FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
			Utils.createFile("drive/" + dir + "/" + generatedTxt, ffmpegGen.toString());
			generatePlayListVideoDrive("drive/" + dir + "/" + generatedTxt, "playlists/" + dir + "/" + i + "_" + dir);
			File output = new File("playlists/" + dir + "/" + i + "_" + dir + ".mp4");
			logFileCreation(output, "playlists/" + dir + "/" + i + "_" + dir + ".mp4");
		}
	}


	public static void generatePlayListVideoDrive(String absolutePath, String filename) {
		Runnable runnable = () -> {
			String cmd = "ffmpeg -y -f concat -safe 0 -i " + absolutePath + " -c copy " + filename + ".mp4";
			System.out.println(cmd);
			Utils.exec(cmd);
		};
		Thread t = new Thread(runnable);
		t.start();
	}
	
	private void logFileCreation(File output, String generatedName) {
		if (output.exists()) {
			System.out.print("Fichier généré : '");
			System.out.print(generatedName);
			System.out.println("'");
		} else {
			System.out.print("Erreur génération fichier : '");
			System.out.print(generatedName);
			System.out.println("'");
		}
	}

	/** Creates parent directories if necessary. */
	private static void createDirectoryIfDoesntExists(String directory) {
	    File dir = new File(directory);
	    if (!dir.exists()) dir.mkdirs();
	}
	
}
