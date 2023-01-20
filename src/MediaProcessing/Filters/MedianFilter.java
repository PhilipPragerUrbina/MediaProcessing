package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;

/**
 * Denoising filter
 */
public class MedianFilter implements Filter<RGBA> {
    final private int radius;

    /**
     * Denoise an image using the median of a region of pixels around each pixel
     * @param radius How many pixels around to take median of
     */
    public MedianFilter(int radius) {
        this.radius = radius;
    }

    @Override
    public void apply(Image<RGBA> image) {
        //todo copy image
        //todo parallel stream computation
    }
}
