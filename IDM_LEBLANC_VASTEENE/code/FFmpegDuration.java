import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Calcule la variante avec la plus longue durée
 * 
 * @author Adrien
 *
 */
public class FFmpegDuration {

	public double duration;
	
	public static void main(String[] args) {

		String input = "videogen/data.videogen";
		
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegDuration ffmpegDuration = new FFmpegDuration(videoGen);
		System.out.println(ffmpegDuration.duration + " secondes");
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
		double best = 0;
		for (MediaDescription description : alternatives.getMedias()) {
			double current = seekDuration(description.getLocation());
			if (current > best) {
				best = current;
			}
		}
		duration += best;
	}
	
	private double seekDuration(String path) {
		String cmd = "mediainfo --Inform=\"Video;%Duration%\" " + path;
		String result = Utils.exec(cmd);
		String[] split = result.split("VideoID");
		String firstPart = split[0];
		String[] p = firstPart.split(":");
		String time = p[p.length-1].trim();
		return Double.parseDouble(time) / 1000.0;
	}
}
