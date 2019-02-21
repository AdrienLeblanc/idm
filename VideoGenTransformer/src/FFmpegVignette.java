import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoGeneratorModel;

/**
 * CLasse générant des vignettes pour un fichier videogen
 * 
 * @author adrien
 *
 */
public class FFmpegVignette {
	
	public List<String> paths = new ArrayList<String>();
	
	public static void main(String[] args) {

		String input = "videogen/data.videogen";
		
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVignette ffmpegVignette = new FFmpegVignette(videoGen);
		for (String path : ffmpegVignette.paths) {
			System.out.println("Vignette de '" + path +"' générée. (vignettes/" + ffmpegVignette.getFilename(path) + ".png)");
		}
	}

	public FFmpegVignette(VideoGeneratorModel videoGen) {
		if (videoGen.getMedias() != null) {
			vignette(videoGen.getMedias());
		}
	}
	
	private void vignette(EList<Media> medias) {
		for (Media media : medias) {
			vignette(media);
		}
	}

	private void vignette(Media media) {
		if (media instanceof MandatoryMedia) {
			MandatoryMedia mandatory = (MandatoryMedia) media;
			vignette(mandatory);
		} else if (media instanceof OptionalMedia) {
			OptionalMedia optional = (OptionalMedia) media;
			vignette(optional);
		} else if (media instanceof AlternativesMedia) {
			AlternativesMedia alternatives = (AlternativesMedia) media;
			vignette(alternatives);
		}
	}
	
	private void vignette(MandatoryMedia mandatory) {
		createVignette(mandatory.getDescription().getLocation());
	}
	
	private void vignette(OptionalMedia optional) {
		createVignette(optional.getDescription().getLocation());
	}
	
	private void vignette(AlternativesMedia alternatives) {
		for (MediaDescription description : alternatives.getMedias()) {
			createVignette(description.getLocation());
		}
	}

	private void createVignette(String path) {
		if (!paths.contains(path)) {
			Utils.exec("ffmpeg -y -i " + path + " -r 1 -t 00:00:01 -ss 00:00:02 -f image2 vignettes/" + getFilename(path) + ".png");
			paths.add(path);
		}
	}
	
	private String getFilename(String path) {
		String[] tab = path.split("/");
		String res =  "";
		for (int i = 0; i < tab[1].length(); i++) {
			if (tab[1].charAt(i) != '.') {
				res += tab[1].charAt(i);
			} else {
				break;
			}
		}
		return res;
	}
}
