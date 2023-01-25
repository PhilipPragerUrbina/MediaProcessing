package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Vector3;

/**
 * Simple color "green" screen key
 */
public class SimpleKey implements Filter<RGBA> {
    final private Vector3 key_color_vector;
    final private double threshold;

    /**
     * Create a simple key to apply alpha to image
     * @param key_color Target background color(Ignores Alpha)
     * @param threshold How close color needs to be
     */
    public SimpleKey(RGBA key_color, double threshold) {
        this.key_color_vector = new Vector3(key_color);
        this.threshold = threshold;
    }


    @Override
    public void apply(Image<RGBA> image) {
        image.forEach(pixel -> {
            Vector3 color_vector = new Vector3(pixel.color); //Get double vector representation
            if(color_vector.distance(key_color_vector) < threshold) {  //Compare distance
                //Is close enough to be background
                pixel.color = new RGBA (0,0,0,0);
                image.setPixel(pixel); //Set to transparent black background
            }
            //otherwise, don't change
        });
    }
}
