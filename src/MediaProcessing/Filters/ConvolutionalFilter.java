package MediaProcessing.Filters;

import MediaProcessing.Data.Image;
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
public class ConvolutionalFilter implements Filter<RGBA> {
    //todo work with multiple color types by adding vector to color conversion and vice versa
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
    public void apply(Image<RGBA> image) {
        Image<RGBA> new_image = image.makeCopy(); //Make copy of image
       image.getStream().parallel().forEach(pixel -> { //parallel loop for this heavy operation
           Vector3 rgb = averageCoordinate(pixel.getX(), pixel.getY(), image).clamped(0,255);//get rgb
           pixel.color = rgb.getColor(pixel.color.getA()); //convert to color
           new_image.setPixel(pixel); //set pixel in new image
       });
       image.setData(new_image); //save
    }

    /**
     * Use kernel to average a coordinate on a grid
     */
    private Vector3 averageCoordinate(int c_x, int c_y, Image<RGBA> grid){
        Vector3 sum = new Vector3();//Get 0 vector
        //Loop through possible values that are not out of bounds. By not processing out of bounds coordinates, it is equivalent to setting them to 0 in the average calculation.
        for (int x = Math.max(0,c_x- kernel.getSize()) ; x < Math.min(grid.getWidth(),c_x+ kernel.getSize()+1); x++) {
            for (int y = Math.max(0, c_y - kernel.getSize()); y < Math.min(grid.getHeight(), c_y + kernel.getSize() + 1); y++) {
                Vector3 value = new Vector3(grid.getPixel(x , y)); //Get the value
                sum = sum.add(value.multiply(kernel.getWeight(x - c_x + kernel.getSize(), y - c_y + kernel.getSize()))); //Add value multiplied by weight
            }
        }
        return sum.divide(kernel.getTotalWeight()); //Divide by total weight. Is automatically divided by 1 if total weight is 0.
    }
}
