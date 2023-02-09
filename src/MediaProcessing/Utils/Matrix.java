package MediaProcessing.Utils;

import java.util.Arrays;

/**
 * A 2d  matrix
 *  Immutable
 *  Good for kernels, transformation, rotation, etc
 */
public class Matrix {
    final private double[][] values;

    /**
     * Create matrix from value array
     */
    public Matrix(double[][] values){
        this.values = values.clone();
    }

    /**
     * Get identity matrix of width and height. Height is y.
     */
    public static Matrix identity(int width, int height){
        double[][] values = new double[width][height];
        for (int i = 0; i < Math.min(width,height); i++) { //Go diagonal
            values[i][i] = 1;
        }
        return new Matrix(values);
    }

    /**
     * Get x dim
     */
   public int getWidth(){
        return values.length;
    }

    /**
     * Get y dim
     */
    public int getHeight(){
        return values[0].length;
    }

    /**
     * Get a value from matrix
     */
    public double getValue(int x, int y){
        return values[x][y];
    }

    /**
     * Equivalent layout to java array initialization
     */
    @Override
    public String toString() {
        String out = " {  \n"; //print matrix.
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                    out += " " + getValue(x,y);
            }
            out += "\n";
        }
        return out + " } ";
    }

    /**
     * Get a new array that is same size as current matrix
     */
    private double[][] getTemporaryArray(){
        return new double[getWidth()][getHeight()];
    }



    /**
     * Switch rows an column of matrix
     * @return New transposed matrix
     */
    Matrix getTranspose(){
        double[][] out = getTemporaryArray();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                out[y][x] = getValue(x,y);
            }
        }
        return new Matrix(out);
    }

    /**
     * Subtract two same size matrices
     */
    public Matrix subtract(Matrix other){
        double[][] out = getTemporaryArray();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                out[x][y] = getValue(x,y) - other.getValue(x,y);
            }
        }
        return new Matrix(out);
    }




    /**
     * Get copy of matrix array
     */
    public double[][] getArrayCopy(){
        return values.clone();
    }


}
