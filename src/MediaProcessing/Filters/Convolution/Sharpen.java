package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

//todo sharpen that does inverse of other filters
/**
 * Simple sharpen
 */
public class Sharpen implements Filter<RGBA> {

    final Filter<RGBA> filter = new ConvolutionalFilter(new Kernel2D(new double[][]{
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    })); //store filter

    @Override
    public void apply(Image<RGBA> image) {
        filter.apply(image); //apply convolution
    }
}
