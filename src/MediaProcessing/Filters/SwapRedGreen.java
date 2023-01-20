package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Simple filter to swap red and green
 */
public class SwapRedGreen implements Filter<RGBA> {

    @Override
    public void apply(Image<RGBA> image) {
        image.forEach(pixel -> {
            pixel.color = new RGBA(pixel.color.getG(), pixel.color.getR(),pixel.color.getB(), pixel.color.getA());
            image.setPixel(pixel);
        });
    }
}
