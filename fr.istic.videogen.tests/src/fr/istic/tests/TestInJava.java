package fr.istic.tests;

import org.junit.Test;

import fr.istic.videoGen.AlternativesMedia;
import fr.istic.videoGen.MandatoryMedia;
import fr.istic.videoGen.Media;
import fr.istic.videoGen.OptionalMedia;
import fr.istic.videoGen.VideoGeneratorModel;

public class TestInJava {
	
	@Test
	public void test() {
		VideoGeneratorModel videoGeneratorModel = null;
		
		for (Media media : videoGeneratorModel.getMedias()) {
			
			if (media instanceof MandatoryMedia) {
				
			} else if (media instanceof OptionalMedia) {
				
			} else if (media instanceof AlternativesMedia) {
				
			}
		}
	}
}
