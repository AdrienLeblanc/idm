import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Permet d'exécuter FFmpeg
 * Classe non testée
 * 
 * @author adrien
 *
 */
public class FFmpegLauncher {
	
	public static void main(String[] args) {
		//generatePlayListGif("spec");
		generateAndReadPlayListVideo("spec");
	}

	public static String generatePlayListGif(String filename) {
		Utils.exec("ffmpeg -y -i playlists/" + filename + "_playlist.mp4 -f gif gifs/" + filename + ".gif");
		return "gifs/" + filename + ".gif";
	}

	public static String generatePlayListVideo(String filename) {
		Utils.exec("ffmpeg -y -f concat -safe 0 -i " + filename + ".txt -c copy playlists/" + filename + ".mp4");
		return "playlists/" + filename + ".mp4";
	}

	public static String generatePlayListVideoDrive(String absolutePath, String filename) {
		String cmd = "ffmpeg -y -f concat -safe 0 -i " + absolutePath + " -c copy " + filename + ".mp4";
		System.out.println(cmd);
		Utils.exec(cmd);
		return "playlists/" + filename + ".mp4";
	}
	
	public static void generateAndReadPlayListVideo(String filename) {
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("videogen/" + filename + ".videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		Utils.createFile(filename + ".txt", ffmpegGen.toString());
		Utils.exec("ffmpeg -y -f concat -i " + filename + ".txt -c copy playlists/" + filename + "_playlist.mp4");
		Utils.exec("vlc playlists/" + filename + "_playlist.mp4");
	}
}
