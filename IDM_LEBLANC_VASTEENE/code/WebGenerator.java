import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoDescription;
import fr.istic.videoGen.VideoGeneratorModel;

public class WebGenerator {
	
	public StringBuilder html = new StringBuilder();
	
	private final int MANDATORY = 1;
	private final int OPTIONAL = 2;
	private final int ALTERNATIVES = 3;

	public static void main(String[] args) {
		String input = "videogen/data.videogen";
		String output = "index.html";

		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		WebGenerator ffmpegGen = new WebGenerator(videoGen);
		Utils.createFile(output, ffmpegGen.html.toString());
	}
	
	public WebGenerator(VideoGeneratorModel videoGen) {
		if (videoGen.getMedias() != null) {
			compile(videoGen.getMedias());
		}
		html.append("<button type=\"button\">Create</button>");
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
		addVideo(mandatory.getDescription().getLocation(), MANDATORY);
	}
	
	private void compile(OptionalMedia optional) {
		addVideo(optional.getDescription().getLocation(), OPTIONAL);
	}
	
	private void compile(AlternativesMedia alternatives) {
		html.append("<p>Select an alternative video :</p>");
		for (MediaDescription description : alternatives.getMedias()) {
			addVideo(description.getLocation(), ALTERNATIVES);
		}
		boolean checked = true;
		for (MediaDescription description : alternatives.getMedias()) {
			String id = ((VideoDescription) description).getVideoid();
			html.append("<div>");
			if (checked) {
				html.append("<input type=\"radio\" id=\"" + id + "\" name=\"alternative\" value=\"" + id + "\" checked>");
				checked = false;
			}
			else html.append("<input type=\"radio\" id=\"" + id + "\" name=\"alternative\" value=\"" + id + "\">");
			html.append("<label for=\"" + id + "\">" + id + "</label>");
			html.append("</div>");
		}
		html.append("<br>");
	}
	
	private void addVideo(String path, int type) {
		switch (type) {
		case MANDATORY:
			html.append("<video height=150 width=260 controls src=\"" + path + "\"></video>");
			html.append("<br>");
			break;
		case OPTIONAL:
			html.append("<video height=150 width=260 controls src=\"" + path + "\"></video>");
			html.append("<input type=\"checkbox\" name=\"optional\" value=\"Bike\">Add optional video<br>");
			html.append("<br>");
			break;
		case ALTERNATIVES:
			html.append("<video height=150 width=260 controls src=\"" + path + "\"></video>");
			break;
		}
	}

}
