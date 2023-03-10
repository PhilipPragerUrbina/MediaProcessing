package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Convolution.ConvolutionalFilter;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;



/**
 * Simple blur convolutional filter
 */
public class BoxBlurFilter<ColorType extends Color> implements Filter<ColorType> {

    final ConvolutionalFilter<ColorType> filter; //store convolution filter

    /**
     * Generate a simple box blur convolution kernel
     * @param size Amount of blur. Must be greater than 1.
     */
    public BoxBlurFilter(int size){

        int width = size*2 + 1; //The width of the kernel. Is the center with size on either side.
        double[][] weights = new double[width][width]; //Create weight array.

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                weights[x][y] = 1; //Fill array with 1. Box blur takes average of all pixels equally unlike Gaussian blur.
            }
        }

        filter = new ConvolutionalFilter<>(new Kernel2D(weights)); //create kernel
    }

    /**
     * Get the kernel used in this filter
     */
    public Kernel2D getKernel(){
        return filter.getKernel();
    }

    @Override
    public void apply(Image<ColorType> image) {
        filter.apply(image); //apply convolution
    }
}
