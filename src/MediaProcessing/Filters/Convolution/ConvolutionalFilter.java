package MediaProcessing.Filters.Convolution;

import MediaProcessing.Data.Image;
import MediaProcessing.Filters.Filter;
import MediaProcessing.Tracking.Shape;
import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;
import MediaProcessing.Utils.Convolution.Kernel2D;
import MediaProcessing.Utils.Vectors.Vector;
import MediaProcessing.Utils.Vectors.Vector3;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Convolutional filter
 */
public class ConvolutionalFilter<ColorType extends Color> implements Filter<ColorType> {
    final private Kernel2D kernel;

    /**
     * Apply a convolution kernel to an image
     * Ignores alpha
     * @param kernel Weights to use
     */
    public ConvolutionalFilter(Kernel2D kernel) {
        this.kernel = kernel;
    }

    @Override
    public void apply(Image<ColorType> image) {
        Image<ColorType> new_image = image.makeCopy(); //Make copy of image
       image.getStream().parallel().forEach(pixel -> { //parallel loop for this heavy operation
           Vector color_vec = averageCoordinate(pixel.getX(), pixel.getY(), image);//get color
           pixel.color = (ColorType) pixel.color.getColorFromVector(color_vec); //convert to color
           new_image.setPixel(pixel); //set pixel in new image
       });
       image.setData(new_image); //save
    }

    /**
     * Use kernel to average a coordinate on a grid
     */
    private Vector averageCoordinate(int c_x, int c_y, Image<ColorType> grid){
        Vector sum = (Vector) grid.getPixel(0,0).getVectorRepresentation().multiply(0);//Get 0 vector
        //Loop through possible values that are not out of bounds. By not processing out of bounds coordinates, it is equivalent to setting them to 0 in the average calculation.
        for (int x = Math.max(0,c_x- kernel.getSize()) ; x < Math.min(grid.getWidth(),c_x+ kernel.getSize()+1); x++) {
            for (int y = Math.max(0, c_y - kernel.getSize()); y < Math.min(grid.getHeight(), c_y + kernel.getSize() + 1); y++) {
                Vector value = grid.getPixel(x , y).getVectorRepresentation(); //Get the value
                sum =  (Vector) sum.add(value.multiply(kernel.getWeight(x - c_x + kernel.getSize(), y - c_y + kernel.getSize()))); //Add value multiplied by weight
            }
        }
        return (Vector)sum.divide(kernel.getTotalWeight()); //Divide by total weight. Is automatically divided by 1 if total weight is 0.
    }

    /**
     * Get the kernel used in this filter
     */
    public Kernel2D getKernel(){
        return kernel;
    }
}
