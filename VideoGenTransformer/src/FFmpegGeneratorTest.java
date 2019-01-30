import org.eclipse.emf.common.util.URI;
import org.junit.Assert;
import org.junit.Test;
import fr.istic.videoGen.VideoGeneratorModel;

public class FFmpegGeneratorTest {

	@Test
	public void readAndGenerateOneMandatory() {
		// We check that the mandatory is generated
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_1_mandatory.videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		Assert.assertTrue(ffmpegGen.builder.toString().contains("V1/v1.mp4"));
	}

	@Test
	public void readAndGenerateOneOptional() {
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_2_optional.videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		// Check if a line as   file '...'   has been generated (50%)
		int occurrences = ffmpegGen.builder.toString().split("file", -1).length - 1;

		if (occurrences == 1) {
			Assert.assertTrue(ffmpegGen.builder.toString().contains("V2/v2.mp4"));
		} else if (occurrences != 0) {
			Assert.fail("readAndGenerateOneOptional failed : Two or more lines have been generated.");
		}
	}

	@Test
	public void readAndGenerateOneAlternative() {
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_3_alternatives.videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);

		// Check if a line as   file '...'   has been generated (100%)
		int occurrences = ffmpegGen.builder.toString().split("file", -1).length - 1;
		Assert.assertEquals(1, occurrences);

		if (occurrences == 1) {
			// We check that has least one of these locations has been generated
			Assert.assertTrue(ffmpegGen.builder.toString().contains("V3/v31.mp4")
					|| ffmpegGen.builder.toString().contains("V3/v32.mp4")
					|| ffmpegGen.builder.toString().contains("V3/v33.mp4"));
		} else {
			Assert.fail("readAndGenerateOneAlternative failed : None or too many lines have been generated.");
		}
	}

	@Test
	public void readAndGenerateMandOptAlt() {
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_4_mandOptAlt.videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);

		// Mandatory check
		Assert.assertTrue(ffmpegGen.builder.toString().contains("V1/v1.mp4"));

		// Alternatives check
		int occurrences = ffmpegGen.builder.toString().split("file", -1).length - 1;

		// We check that has least one of these locations has been generated
		Assert.assertTrue(ffmpegGen.builder.toString().contains("V3/v31.mp4")
				|| ffmpegGen.builder.toString().contains("V3/v32.mp4")
				|| ffmpegGen.builder.toString().contains("V3/v33.mp4"));
		
		if (occurrences == 3) {
			// Optional check
			Assert.assertTrue(ffmpegGen.builder.toString().contains("V2/v2.mp4"));
		} else if (occurrences != 2) {
			Assert.fail("readAndGenerateMandOptAlt failed : None or too many lines have been generated.");
		}
	}

	@Test
	public void readAndGenerateOneOptional100probability() {
		// We put 100% probability to simulate a mandatory generation
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI("src/test/test_5_optional100probability.videogen"));
		FFmpegGenerator ffmpegGen = new FFmpegGenerator(videoGen);
		Assert.assertTrue(ffmpegGen.builder.toString().contains("V2/v2.mp4"));
	}

}
