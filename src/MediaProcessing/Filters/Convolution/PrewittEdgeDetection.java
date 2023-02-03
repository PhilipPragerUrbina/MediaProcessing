package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

/**
 * Simple edge detection
 */
public class PrewittEdgeDetection implements Filter<RGBA> {

    final Filter<RGBA> filter = new ConvolutionalFilter(new Kernel2D(new double[][]{
            {-1,-1,-1},
            {-1,8,-1},
            {-1,-1,-1}
    })); //store filter

    @Override
    public void apply(Image<RGBA> image) {
        filter.apply(image); //apply convolution
    }
}
