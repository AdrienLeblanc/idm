import java.io.IOException;
import java.util.UUID;

import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Permet d'exécuter FFmpeg
 * 
 * @author adrien
 *
 */
public class FFmpegLauncher {
	
	public static void main(String[] args) {
		//generatePlayListGif("data");
		generateAndReadPlayListVideo("spec");
	}

	public static String generatePlayListGif(String filename) {
		Utils.exec("ffmpeg -y -ss 24 -t 1 -i playlists/" + filename + "_playlist.mp4 -f gif gifs/" + filename + "_playlist.gif");
		return "gifs/" + filename + "_playlist.gif";
	}

	public static String generatePlayListVideo(String filename) {
		Utils.exec("ffmpeg -y -f concat -safe 0 -i " + filename + ".txt -c copy playlists/" + filename + "_playlist.mp4");
		return "playlists/" + filename + "_playlist.mp4";
	}
	
	public static void generateAndReadPlayListVideo(String filename) {

		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("data/" + filename + ".videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		int duration = ffmpegGen.getTotalDuration();
		String durationCmd = "";
		if (duration != 0) {
			durationCmd = "-t " + duration;
		}
		Utils.createFile(filename + ".txt", ffmpegGen.toString());
		Utils.exec("ffmpeg -y -f concat -i " + filename + ".txt -c copy playlists/" + filename + "_playlist.mp4");
		System.out.println("ffmpeg -ss 0 -i playlists/" + filename + "_playlist.mp4 -c copy " + durationCmd + " playlists/" + filename + "_playlist.mp4");
		Utils.exec("ffmpeg -ss 0 -i playlists/" + filename + "_playlist.mp4 -c copy " + durationCmd + " playlists/" + filename + "_playlist.mp4");
		Utils.exec("vlc playlists/" + filename + "_playlist.mp4");
	}
}
