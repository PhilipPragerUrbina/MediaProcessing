package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;
import MediaProcessing.Utils.Matrix;


/**
 * Sharpen any kind of blur
 */
public class AnySharpenFilter<ColorType extends Color> implements Filter<ColorType> {

    final Filter<ColorType> filter; //store convolution filter

    /**
     * Generate a sharpening filter for any blur
     * @param blur_kernel Kernel that represents the blur
     * @param brightness Strength of sharpen
     */
    public AnySharpenFilter(Kernel2D blur_kernel, double brightness){
        //2 * identity(original) - blur

        //Create same size identity kernel
        double[][] array = new double[blur_kernel.getMatrix().getWidth()][blur_kernel.getMatrix().getWidth()];
        //Will have center since kernel must be odd. Truncation will find this center automatically on divide.
        array[(blur_kernel.getMatrix().getWidth()/2)][(blur_kernel.getMatrix().getWidth()/2)] = 2 * brightness; //Set center to 1 * 2
        Matrix identity_kernel = new Matrix(array);
        filter=new ConvolutionalFilter<>(new Kernel2D(identity_kernel.subtract(blur_kernel.getMatrix())));//Subtract and create new kernel
    }

    @Override
    public void apply(Image<ColorType> image) {
        filter.apply(image); //apply convolution
    }
}
