package MediaProcessing.Filters.GrayScale;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.G;

/**
 * Quantize greyscale to black and white based on threshold
 */
public class GrayscaleThresholdFilter implements Filter<G> {
private short threshold;

    /**
     * Create greyscale filter
     * @param threshold Value to split black and white
     */
   public GrayscaleThresholdFilter(short threshold){
        this.threshold = threshold;
    }

    @Override
    public void apply(Image<G> image) {
        image.getStream().parallel().forEach(color ->{  //todo decide to use parallel
                    if(color.getValue() > threshold){
                        color.setValue((short)255);
                    }else {
                        color.setValue((short)0);
                    }
                }
        );
    }
}
