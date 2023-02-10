package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

/**
 * Simple edge detection
 */
public class PrewittEdgeDetection<ColorType extends Color> implements Filter<ColorType> {

    final Filter<ColorType> filter = new ConvolutionalFilter<>(new Kernel2D(new double[][]{
            {-1,-1,-1},
            {-1,8,-1},
            {-1,-1,-1}
    })); //store filter

    @Override
    public void apply(Image<ColorType> image) {
        filter.apply(image); //apply convolution
    }
}
