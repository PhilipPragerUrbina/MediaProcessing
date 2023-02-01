package MediaProcessing.Utils.Convolution;

import MediaProcessing.Utils.Vectors.Vector;

import java.util.Arrays;

/**
 *  2d convolution
 * @param <ValueType> The type that each coordinate should contain. Like a color.
 */
public class Convolution2D  <ValueType extends Vector<ValueType>>{
    final private Kernel2D kernel;

    /**
     * Create a convolution with a kernel
     */
    public Convolution2D(Kernel2D kernel) {
        this.kernel = kernel;
    }

    /**
     * Apply the kernel to a grid of values
     * @param input The input array. Vectors are the values. Can be a scalar single component vector, or a multi component vector like a color.
     * @return Output
     */
    public ValueType[][] getConvolution(ValueType[][] input) {
        ValueType[][] output = (ValueType[][]) new Vector[input.length][input[0].length];//Only output so cast is fine
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                output[x][y] = averageCoordinate(x, y, input);
            }
        }
        return output;
    }

    //todo parallel computation

    /**
     * Use kernel to average a coordinate on a grid
     */
    private ValueType averageCoordinate(int c_x, int c_y, ValueType[][] grid){
        ValueType sum = grid[0][0].multiply(0);//Get 0 vector
        //Loop through possible values that are not out of bounds. By not processing out of bounds coordinates, it is equivalent to setting them to 0 in the average calculation.
        for (int x = Math.max(0,c_x- kernel.getSize()) ; x < Math.min(grid.length,c_x+ kernel.getSize()+1); x++) {
            for (int y = Math.max(0, c_y - kernel.getSize()); y < Math.min(grid[0].length, c_y + kernel.getSize() + 1); y++) {
                sum = sum.add(grid[0][0].multiply(kernel.getWeight(x - c_x, y - c_y))); //Add value multiplied by weight
            }
        }
        return sum.divide(kernel.getTotalWeight()); //Divide by total weight. Is automatically divided by 1 if total weight is 0.
    }

}
