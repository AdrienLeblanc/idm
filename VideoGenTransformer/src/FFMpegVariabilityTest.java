import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoGeneratorModel;

/**
 * Classe de test vérifiant le bon fonctionnement de la génération des variabilités d'une playlist
 * 
 * @author adrien
 *
 */
public class FFMpegVariabilityTest {
	
	private int nbVariantes(VideoGeneratorModel videoGen) {
		int nbVariantes = 0;
		for (Media media : videoGen.getMedias()) {
			if (media instanceof MandatoryMedia) {
				if (nbVariantes == 0) {
					nbVariantes = 1;
				}
			} else if (media instanceof OptionalMedia) {
				if (nbVariantes == 0) {
					if (videoGen.getMedias().size() == 1) {
						nbVariantes = 1; // Empty variant doesn't exist
					} else {
						nbVariantes = 2; // We allow to use empty list to add more variants
					}
				} else {
					nbVariantes *= 2;
				}
			} else if (media instanceof AlternativesMedia) {
				AlternativesMedia alt = (AlternativesMedia) media;
				if (nbVariantes == 0) {
					nbVariantes = alt.getMedias().size();
				} else {
					nbVariantes *= alt.getMedias().size();
				}
			}
		}
		if (videoGen.getMedias().size() > 1 && videoGen.getMedias().get(videoGen.getMedias().size() - 1) instanceof OptionalMedia) {
			nbVariantes--; // We remove the last empty variant
		}
		return nbVariantes;
	}

	@Test
	public void test0() {
		String input = "test/data.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(6, variability.variants.size());
	}
	
	@Test
	public void test1() {
		String input = "test/test_1_mandatory.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}

	@Test
	public void test2() {
		String input = "test/test_2_optional.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test3() {
		String input = "test/test_3_alternatives.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test4() {
		String input = "test/test_4_mandOptAlt.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test5() {
		String input = "test/test_5_optional100probability.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test6() {
		String input = "test/test_6.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test7() {
		String input = "test/test_7.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test8() {
		String input = "test/test_8.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test9() {
		String input = "test/test_9.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
	
	@Test
	public void test10() {
		String input = "test/test_10.videogen";
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		Assert.assertEquals(nbVariantes(videoGen), variability.variants.size());
	}
}
