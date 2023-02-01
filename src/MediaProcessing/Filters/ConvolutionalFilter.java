package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Convolution2D;
import MediaProcessing.Utils.Convolution.Kernel2D;
import MediaProcessing.Utils.Vectors.Vector;
import MediaProcessing.Utils.Vectors.Vector3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Convolutional filter
 */
public class ConvolutionalFilter implements Filter<RGBA> {
    final private Kernel2D kernel;
    final private Convolution2D<Vector3> convolution;

    /**
     * Apply a convolution kernel to an image
     * Ignores alpha
     * @param kernel Weights to use
     */
    public ConvolutionalFilter(Kernel2D kernel) {
        this.kernel = kernel;
        convolution = new Convolution2D<>(kernel); //todo combine with kernel since redundant
    }

    @Override
    public void apply(Image<RGBA> image) {
        //Generate value array
        Vector3[][] input_array = new Vector3[image.getWidth()][image.getHeight()];
        image.forEach(pixel -> {
            input_array[pixel.getX()][pixel.getY()] =new Vector3(pixel.color);
        });

        Vector3[][] output_array = convolution.getConvolution(input_array);//Apply convolution

        //Save
        image.forEach(pixel -> {
            pixel.color = output_array[pixel.getX()][pixel.getY()].clamped(0, 255).getColor(pixel.color.getA());
             image.setPixel(pixel);
        });
    }
}
