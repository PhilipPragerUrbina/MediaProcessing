package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Add color noise to image
 */
public class ColorNoise implements Filter<RGBA> {
    final private double probability;

    /**
     * Add noise to image
     * Ignores alpha
     * Used Math.random().
     * @param probability 0-1 probability of noise being added to pixel. Larger means more.
     */
    public ColorNoise(double probability) {
        this.probability = probability;
    }


    @Override
    public void apply(Image<RGBA> image) {
        image.forEach(pixel -> {
            if(Math.random() < probability){ //Random based on probability
                //Should add noise
                pixel.color = new RGBA((short)(Math.random() * 256.0), (short)(Math.random() * 256.0),(short)(Math.random() * 256.0), pixel.color.getA());
                image.setPixel(pixel); //Add random noise(ignoring alpha)
            }
        });
    }
}
