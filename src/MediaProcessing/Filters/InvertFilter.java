package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Get the negative of an image
 * Ignores alpha
 */
public class InvertFilter implements Filter<RGBA>{

    @Override
    public void apply(Image<RGBA> image) {
        image.getStream().forEach(color ->//for each pixel
                color.setValues((255-color.getR()),(255-color.getG()),(255-color.getB()),255) //invert(not A)
        );
    }
}
