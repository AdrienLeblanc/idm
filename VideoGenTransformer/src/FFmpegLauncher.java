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
		
		FFmpegLauncher launcher = new FFmpegLauncher();
		//launcher.readPlayList("data.flv");
		launcher.generateAndReadPlayList();
	}

	public FFmpegLauncher() {
	}
	
	public void readPlayList(String playListName) {
		String random = UUID.randomUUID().toString();
		Utils.exec("ffmpeg -f concat -safe 0 -i " + playListName + " -c copy src/playlists/" + random + ".mp4");
		Utils.exec("vlc src/playlists/" + random + ".mp4");
	}
	
	public void generateAndReadPlayList() {
		String input = "src/data/data.videogen";
		String output = "data.txt";

		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		Utils.createFile(output, ffmpegGen.toString());
		
		String random = UUID.randomUUID().toString();
		Utils.exec("ffmpeg -f concat -safe 0 -i data.flv -c copy src/playlists/" + random + ".mp4");
		Utils.exec("vlc src/playlists/" + random + ".mp4");
	}
	
}
