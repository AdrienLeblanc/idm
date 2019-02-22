import java.util.Random;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.ImageDescription;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoDescription;
import fr.istic.videoGen.VideoGenInformation;
import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Classe de génération de variante de playlist FFmpeg
 * 
 * @author Adrien
 * 
 */
public class FFmpegGenerator {

	public StringBuilder builder = new StringBuilder();

	public static void main(String[] args) {
		String input = "videogen/data.videogen";
		String output = "data.txt";

		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		Utils.createFile(output, ffmpegGen.toString());
	}
	
	public String toString() {
		return builder.toString();
	}
	
	public FFmpegGenerator(VideoGeneratorModel videoGen) {
		if (videoGen.getInformation() != null) {
			compile(videoGen.getInformation());
		}
		if (videoGen.getMedias() != null) {
			compile(videoGen.getMedias());
		}
	}

	private void compile(VideoGenInformation information) {
		if (information.getAuthorName() != null) {
			builder.append("#author=" + information.getAuthorName() + "\n");
		}
		if (information.getVersion() != null) {
			builder.append("#version=" + information.getVersion() + "\n");
		}
		if (information.getCreationDate() != null) {
			builder.append("#creationDate=" + information.getCreationDate().toString() + "\n");
		}
	}

	private void compile(EList<Media> medias) {
		for (Media media : medias) {
			compile(media);
		}
	}

	private void compile(Media media) {
		if (media instanceof MandatoryMedia) {
			MandatoryMedia mandatory = (MandatoryMedia) media;
			compile(mandatory);
		} else if (media instanceof OptionalMedia) {
			OptionalMedia optional = (OptionalMedia) media;
			compile(optional);
		} else if (media instanceof AlternativesMedia) {
			AlternativesMedia alternatives = (AlternativesMedia) media;
			compile(alternatives);
		}
	}
	
	private void compile(MandatoryMedia mandatory) {
		builder.append("file '" + mandatory.getDescription().getLocation() + "'\n");
		
	}
	
	private void compile(OptionalMedia optional) {
		compile(optional.getDescription());
	}

	private void compile(AlternativesMedia alternatives) {
		int ponderation = 0;
		int pond[] = new int[alternatives.getMedias().size()];
		int i = 0;
		
		for (MediaDescription media : alternatives.getMedias()) {
			if (media instanceof VideoDescription) {
				VideoDescription video = (VideoDescription) media;
				int increment = (video.getProbability() != 0) ? video.getProbability() : 1 *100 / alternatives.getMedias().size();
				ponderation += increment;
				pond[i] = ponderation;
				i++;
			}
		}
		
		// Random generated
		int random = new Random().nextInt(ponderation);
        MediaDescription description = null;
        
		for (int j = 0; j < pond.length; j++) {
			if (pond[j] > random) {
				description = alternatives.getMedias().get(j);
				break;
			}
		}
        builder.append("file '" + description.getLocation() + "'\n");
	}
	
	private void compile(MediaDescription description) {
		if (description instanceof ImageDescription) {
			ImageDescription image = (ImageDescription) description;
			compile(image);
		} else if (description instanceof VideoDescription) {
			VideoDescription video = (VideoDescription) description;
			compile(video);
		}
	}
	
	private void compile(ImageDescription image) {
		// Not supported
	}
	
	private void compile(VideoDescription video) {
		if (video.getProbability() != 0) {
			if (video.getProbability() > Math.random() * 100) {
		        builder.append("file '" + video.getLocation() + "'\n");
			}
		} else if (0.5 < Math.random()) {
		    builder.append("file '" + video.getLocation() + "'\n");
		}
	}
}
