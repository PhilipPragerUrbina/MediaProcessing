package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Simple filter to set red to 0
 */
public class RemoveRed implements Filter<RGBA> {

    @Override
    public void apply(Image<RGBA> image) {
        image.forEach(pixel -> {
            pixel.color = new RGBA(0, pixel.color.getG(),pixel.color.getB(), pixel.color.getA());
            image.setPixel(pixel);
        });
    }
}
