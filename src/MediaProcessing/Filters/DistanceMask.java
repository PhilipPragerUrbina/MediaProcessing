package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Vector3;

/**
 * Masks image based on color distance
 */
public class DistanceMask implements Filter<RGBA> {
    final private Vector3 target_vector;
    final private double threshold;

    /**
     * Create a mask based on color distance. White is in the mask, black(and transparent) is outside the mask.
     * @param target_color Target color ignoring alpha. Will be white.
     * @param threshold How close color needs to be.
     */
    public DistanceMask(RGBA target_color, double threshold) {
        this.target_vector = new Vector3(target_color);
        this.threshold = threshold;
    }


    @Override
    public void apply(Image<RGBA> image) {
        image.forEach(pixel -> {
            Vector3 color_vector = new Vector3(pixel.color); //Get double vector representation
            if(color_vector.distance(target_vector) < threshold) {  //Compare distance
                //Is close enough to be part of mask
                pixel.color = new RGBA (255,255,255,255); //Set to white

            }else {
                //Is not close enough to be part of mask
                pixel.color = new RGBA(0,0,0,0); //Set to black
            }
            image.setPixel(pixel); //Set color
        });
    }
}
