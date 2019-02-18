import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.*;

/**
 * Calcule la variante avec la plus longue durée
 * 
 * @author Adrien
 *
 */
public class FFmpegDuration {

	public int duration;
	
	public static void main(String[] args) {

		String input = "data/data.videogen";
		String output = "data/datavariants.videogen";
		
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegDuration variability = new FFmpegDuration(videoGen);
	}
	
	public String toString() {
		String output = "";
		return output;
	}

	public FFmpegDuration(VideoGeneratorModel videoGen) {
		if (videoGen.getMedias() != null) {
			duration(videoGen.getMedias());
		}
	}

	private void duration(EList<Media> medias) {
		for (Media media : medias) {
			duration(media);
		}
	}

	private void duration(Media media) {
		if (media instanceof MandatoryMedia) {
			MandatoryMedia mandatory = (MandatoryMedia) media;
			duration(mandatory);
		} else if (media instanceof OptionalMedia) {
			OptionalMedia optional = (OptionalMedia) media;
			duration(optional);
		} else if (media instanceof AlternativesMedia) {
			AlternativesMedia alternatives = (AlternativesMedia) media;
			duration(alternatives);
		}
	}
	
	private void duration(MandatoryMedia mandatory) {
		duration += seekDuration(mandatory.getDescription().getLocation());
	}
	
	private void duration(OptionalMedia optional) {
		duration += seekDuration(optional.getDescription().getLocation());
	}
	
	private void duration(AlternativesMedia alternatives) {
		int best = 0;
		for (MediaDescription description : alternatives.getMedias()) {
			int current = seekDuration(description.getLocation());
			if (current > best) {
				best = current;
			}
		}
		duration += best;
	}
	
	private int seekDuration(String path) {
		return Integer.parseInt(Utils.exec(path));
	}
}
