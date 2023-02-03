package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;


/**
 * Take inverse of other filter
 */
public class InverseSharpenFilter implements Filter<RGBA> {

    final Filter<RGBA> filter; //store convolution filter

    /**
     * Generate a filter with the inverse kernel
     * Use inverse of blur for sharpen
     * @param other_kernel Kernel to inverse
     */
    public InverseSharpenFilter(Kernel2D other_kernel){
        filter = new ConvolutionalFilter(new Kernel2D(other_kernel.getMatrix().getInverse())); //create kernel from inverse matrix
    }

    @Override
    public void apply(Image<RGBA> image) {
        filter.apply(image); //apply convolution
    }
}
