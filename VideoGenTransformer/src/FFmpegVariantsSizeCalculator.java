import java.io.File;
import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;

import fr.istic.videoGen.MediaDescription;
import fr.istic.videoGen.VideoGeneratorModel;

public class FFmpegVariantsSizeCalculator {
	
	ArrayList<ArrayList<MediaDescription>> variants;
	ArrayList<Integer> variantsSizes = new ArrayList<>();
	ArrayList<Integer> variantsRealSizes = new ArrayList<>();
	ArrayList<Integer> variantsRealSizesGif = new ArrayList<>();

	public static void main(String[] args) {
		String input = "data/data.videogen";
		
		VideoGeneratorModel videoGen = new VideoGenHelper()
				.loadVideoGenerator(URI.createURI(input));
		FFmpegVariability variability = new FFmpegVariability(videoGen);
		FFmpegVariantsSizeCalculator calculator = new FFmpegVariantsSizeCalculator(variability.variants);
		for (int i = 0; i < calculator.variants.size(); i++) {
			System.out.print("Variante" + i + "=\t");
			System.out.print("size=" + calculator.variantsSizes.get(i) + "\t");
			System.out.print("realSize=" + calculator.variantsRealSizes.get(i) + "\t");
			System.out.println(".gifSize=" + calculator.variantsRealSizesGif.get(i));
		}
	}
	
	public FFmpegVariantsSizeCalculator(ArrayList<ArrayList<MediaDescription>> variants) {
		this.variants = variants;
		for (ArrayList<MediaDescription> medias : variants) {
			variantsSizes.add(getSize(medias));
			variantsRealSizes.add(getRealSize(medias));
			variantsRealSizesGif.add(getRealSizeGif(medias));
		}
	}

	private Integer getSize(ArrayList<MediaDescription> medias) {
		int size = 0;
		for (MediaDescription description : medias) {
			size += new File(description.getLocation()).length();
		}
		return size;
	}

	private Integer getRealSize(ArrayList<MediaDescription> medias) {
		int realSize = 0;
		StringBuilder builder = new StringBuilder();
		for (MediaDescription description : medias) {
			builder.append("file '" + description.getLocation() + "'\n");
		}
		
		String output = "variantTmp";
		Utils.createFile(output + ".txt", builder.toString());
		String location = FFmpegLauncher.generatePlayListVideo(output);
		realSize = (int) new File(location).length();
		return realSize;
	}

	private Integer getRealSizeGif(ArrayList<MediaDescription> medias) {
		int realSizeGif = 0;
		StringBuilder builder = new StringBuilder();
		for (MediaDescription description : medias) {
			builder.append("file '" + description.getLocation() + "'\n");
		}
		
		String output = "variantTmp";
		Utils.createFile(output + ".txt", builder.toString());
		FFmpegLauncher.generatePlayListVideo(output);
		String  location = FFmpegLauncher.generatePlayListGif(output);
		realSizeGif = (int) new File(location).length();
		return realSizeGif;
	}

}
