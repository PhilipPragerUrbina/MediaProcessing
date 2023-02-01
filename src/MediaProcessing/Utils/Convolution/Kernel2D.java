package MediaProcessing.Utils.Convolution;

import MediaProcessing.Utils.Vectors.Vector;

/**
 * A 2d kernel for convolution
 * Currently uses scalar values, but could be updated to use vector values
 */
public class Kernel2D {
    private int size;
    final private double[][] array;
    final double total_weight;

    /**
     * Create a kernel
     * @param array The kernel weights
     *  Array must be same width, as height. Must also have a center.
     */
    public Kernel2D(double[][] array){
        if(array.length == 0 || array.length != array[0].length){
            throw new IllegalArgumentException("Kernel dimensions invalid");
        }
        if(array.length % 2 != 1){
            throw new IllegalArgumentException("Kernel must have center");
        }
        this.array = array;
        this.size = (array.length-1)/2;

        //Calculate total weight once
        double sum = 0;
        for (double[] sub_array : array) {
            for (double weight : sub_array){
                sum+= weight;
            }
        }
        if(sum == 0.0){//Exact is fine here
            total_weight = 1;
        }else {
            total_weight = sum;
        }
    }

    /**
     * Get the size of the kernel away from the center. How many values from center to edge.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the total weight of kernel
     * Will replace total of 0 with 1
     */
    public double getTotalWeight(){
        return total_weight;
    }

    /**
     * Get the weight at an index
     */
    public double getWeight(int x, int y){
        return array[x][y];
    }


}
