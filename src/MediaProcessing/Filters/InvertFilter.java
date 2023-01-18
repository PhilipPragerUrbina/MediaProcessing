package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

import java.util.stream.Stream;

/**
 * Get the negative of an image
 * Ignores alpha
 */
public class InvertFilter implements Filter<RGBA>{

    @Override
    public void apply(Image<RGBA> image) {
                image.forEach(pixel -> {
                    pixel.color = new RGBA ((255-pixel.color.getR()),(255-pixel.color.getG()),(255-pixel.color.getB())); //invert channels except alpha
                    image.setPixel(pixel);
                });

    }
}
