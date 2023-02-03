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
     * Useful for inverse
     */
    public Matrix getCoFactor(int p, int q, int n){
        if(getWidth() != getHeight()){
            throw  new IllegalArgumentException("Matrix must be square.");
        }

        double[][] out = getTemporaryArray();
        int i = 0,j = 0;

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if(x != p && y != q){

                    out[i][j] = getValue(x,y);
                    j++;
                    if(j == n-1){
                        j=0;
                        i++;
                    }
                }
            }
        }
        return new Matrix(out);
    }

    //dont ask me what any of this does
    double getDeterminant(int n){
        double determinant_output = 0;

       if(n == 1){
           return getValue(0,0);
       }

       Matrix co_factor;

       int sign = 1;

        for (int f = 0; f < n; f++) {
            co_factor = getCoFactor(0,f,n);
            determinant_output += sign * getValue(0,f) * co_factor.getDeterminant(n-1);
        }

        return determinant_output;

    }

    Matrix getAdjoint(){
        double[][] out = getTemporaryArray();
        int sign = 1;
       Matrix temp;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                temp = getCoFactor(x,y,getWidth());
                sign = ((x + y) % 2 == 0) ? 1 : -1;
                out[y][x] = sign * (temp.getDeterminant(getWidth()-1)); //transpose
            }
        }
        return new Matrix(out);
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
     * Get inverse for square matrix.
     * Matrix must be square
     * Original * Inverse = identity.
     */
    public Matrix getInverse(){
        double[][] out = getTemporaryArray();
        double determinant = getDeterminant(getWidth());
        if(determinant == 0){
            return null; //no inverse
        }

        Matrix adjoint = getAdjoint();
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                out[x][y] = adjoint.getValue(x,y)/determinant;
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
