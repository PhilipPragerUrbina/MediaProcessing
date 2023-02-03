package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

/**
 * Emboss convolutional filter
 */
public class EmbossFilter implements Filter<RGBA> {

    final Filter<RGBA> filter = new ConvolutionalFilter(new Kernel2D(new double[][]{
            {-4,0,0},
            {0,0,0},
            {0,0,4}
    })); //store emboss filter

    @Override
    public void apply(Image<RGBA> image) {
        filter.apply(image); //apply convolution
    }
}
