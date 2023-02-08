package MediaProcessing.Converters;


import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.MaskValue;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Vectors.Vector;
import MediaProcessing.Utils.Vectors.Vector3;

/**
 * Masks image based on color distance
 */
public class DistanceMask<ColorType extends Color> implements Converter<ColorType, MaskValue> {
    final private Vector target_vector;
    final private double threshold;

    /**
     * Create a mask based on color distance.
     * @param target_color Target color. Will be white one the mask.
     * @param threshold How close color needs to be.
     */
    public DistanceMask(ColorType target_color, double threshold) {
        this.target_vector = target_color.getVectorRepresentation();
        this.threshold = threshold;
    }
    @Override
    public Image<MaskValue> convert(Image<ColorType> input) {
        Image<MaskValue> mask = new Image<>(input.getWidth(), input.getHeight()); //Create mask
        input.forEach(pixel -> {
            Vector color_vector = pixel.color.getVectorRepresentation(); //Get double vector representation
            if(color_vector.distance(target_vector) < threshold) {  //Compare distance
                //Is close enough to be part of mask
                mask.setPixel(pixel.getX(), pixel.getY(), new MaskValue(true)); //Set to white
            }else {
                //Is not close enough to be part of mask
                mask.setPixel(pixel.getX(), pixel.getY(), new MaskValue(false)); //Set to black
            }
        });
        return mask;
    }
}
