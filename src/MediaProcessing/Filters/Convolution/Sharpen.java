package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

//todo sharpen that does inverse of other filters
/**
 * Simple sharpen
 */
public class Sharpen<ColorType extends Color> implements Filter<ColorType> {

    final Filter<ColorType> filter = new ConvolutionalFilter<>(new Kernel2D(new double[][]{
            {0,-1,0},
            {-1,5,-1},
            {0,-1,0}
    })); //store filter

    @Override
    public void apply(Image<ColorType> image) {
        filter.apply(image); //apply convolution
    }
}
