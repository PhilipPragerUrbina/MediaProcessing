package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

/**
 * Emboss convolutional filter
 */
public class EmbossFilter<ColorType extends Color> implements Filter<ColorType> {

    final Filter<ColorType> filter = new ConvolutionalFilter(new Kernel2D(new double[][]{
            {-4,0,0},
            {0,0,0},
            {0,0,4}
    })); //store emboss filter

    @Override
    public void apply(Image<ColorType> image) {
        filter.apply(image); //apply convolution
    }
}
