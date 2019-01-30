import static org.junit.Assert.*;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import fr.istic.videoGen.*;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.VideoGeneratorModel;

public class VideoGenTest1 {

	String filePath = "example1.videogen";
	@Test
	public void testInJava1() {

		VideoGeneratorModel videoGen = new VideoGenHelper().loadVideoGenerator(URI.createURI(filePath));
		assertNotNull(videoGen);
		
		List<Media> medias = videoGen.getMedias();
		assertNotNull(medias);
		for (Media media : medias) {
			if (media instanceof MandatoryMedia) {
				MandatoryMedia mandatory = (MandatoryMedia) media;
			}
		}

	}


}



