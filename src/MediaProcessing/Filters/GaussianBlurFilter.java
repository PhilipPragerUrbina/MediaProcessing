package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;

//todo put in folder

/**
 * Complex blur convolutional filter
 */
public class GaussianBlurFilter implements Filter<RGBA> {

    final Filter<RGBA> filter; //store convolution filter

    /**
     * Generate a gaussian blur convolution kernel
     * @param size Size of blur. Must be greater than 1.
     * @param sigma Strength of blur. Must be positive.
     */
    public GaussianBlurFilter(int size, double sigma){

        int width = size*2 + 1; //The width of the kernel. Is the center with size on either side.
        double[][] weights = new double[width][width]; //Create weight array.

        //Calculate weights: https://stackoverflow.com/questions/8204645/implementing-gaussian-blur-how-to-calculate-convolution-matrix-kernel

        double mean = width/2.0; //Value for calculating blur

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                weights[x][y] =  Math.exp( -0.5 * (Math.pow((x-mean)/sigma, 2.0) + Math.pow((y-mean)/sigma,2.0)) )
                        / (2 * Math.PI * sigma * sigma); //Calculate gaussian blur weight at given position in kernel
            }
        }

        //Post says to normalize matrix to stop image from getting dark, but it seems to work fine without

        filter = new ConvolutionalFilter(new Kernel2D(weights)); //create kernel
    }

    @Override
    public void apply(Image<RGBA> image) {
        filter.apply(image); //apply convolution
    }
}
