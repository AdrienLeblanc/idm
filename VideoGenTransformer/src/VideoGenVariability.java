import java.io.File;
import java.util.ArrayList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.*;

/**
 * Génère une liste de variantes possibles pour un fichier videogen
 * Sous la forme d'une liste de listes de MediaDescription
 * 
 * @author Adrien
 *
 */
public class VideoGenVariability {

	ArrayList<ArrayList<MediaDescription>> variants = new ArrayList<ArrayList<MediaDescription>>();

	public static void main(String[] args) {
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_4_mandOptAlt.videogen"));
		VideoGenVariability variability = new VideoGenVariability(videoGen);
		System.out.println(variability.toString());
	}
	
	@Override
	public String toString() {
		String output = "";
		for (ArrayList<MediaDescription> variant : variants) {
			for (MediaDescription media : variant) {
				output += getIdFromMediaDescription(media) + File.separatorChar;
			}
			output += "\n";
		}
		return output;
	}

	public VideoGenVariability(VideoGeneratorModel videoGen) {
		if (videoGen.getMedias() != null) {
			variability(videoGen.getMedias(), variants);
		}
	}

	private void variability(EList<Media> medias, ArrayList<ArrayList<MediaDescription>> variants) {
		for (Media media : medias) {
			variability(media, variants);
		}
	}

	private void variability(Media media, ArrayList<ArrayList<MediaDescription>> variants) {
		if (media instanceof MandatoryMedia) {
			MandatoryMedia mandatory = (MandatoryMedia) media;
			for (ArrayList<MediaDescription> variant : variants) {
				variant.add(mandatory.getDescription());
			}
			if (variants.isEmpty()) {
				ArrayList<MediaDescription> variant = new ArrayList<MediaDescription>();
				variant.add(mandatory.getDescription());
				variants.add(variant);
			}
		} else if (media instanceof OptionalMedia) {
			OptionalMedia optional = (OptionalMedia) media;
			ArrayList<ArrayList<MediaDescription>> clones = new ArrayList<ArrayList<MediaDescription>>();
			for (ArrayList<MediaDescription> variant : variants) {
				ArrayList<MediaDescription> clone = new ArrayList<MediaDescription>(variant);
				clones.add(clone);
				variant.add(optional.getDescription());
			}
			variants.addAll(clones);
			if (variants.isEmpty()) {
				ArrayList<MediaDescription> variant = new ArrayList<MediaDescription>();
				variant.add(optional.getDescription());
				variants.add(variant);
			}
		} else if (media instanceof AlternativesMedia) {
			AlternativesMedia alternatives = (AlternativesMedia) media;
			ArrayList<ArrayList<MediaDescription>> newVariants = new ArrayList<ArrayList<MediaDescription>>();
			for (ArrayList<MediaDescription> variant : variants) {
				for (int i = 0; i < alternatives.getMedias().size(); i++) {
					ArrayList<MediaDescription> clone = new ArrayList<MediaDescription>(variant);
					clone.add(alternatives.getMedias().get(i));
					newVariants.add(clone);
				}
			}
			variants.clear();
			variants.addAll(newVariants);
			if (variants.isEmpty()) {
				for (int i = 0; i < alternatives.getMedias().size(); i++) {
					ArrayList<MediaDescription> variant = new ArrayList<MediaDescription>();
					variant.add(alternatives.getMedias().get(i));
					variants.add(variant);
				}
			}
		}
	}

	private static String getIdFromMediaDescription(MediaDescription description) {
		String id = null;
		if (description instanceof ImageDescription) {
			ImageDescription image = (ImageDescription) description;
			id = image.getImageid();
		} else if (description instanceof VideoDescription) {
			VideoDescription video = (VideoDescription) description;
			id = video.getVideoid();
		}
		return id;
	}
}
