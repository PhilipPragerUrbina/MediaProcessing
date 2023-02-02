package MediaProcessing.Utils.Vectors;


import java.util.Objects;
import java.util.Random;

/**
 * N component double vector. Perfect for weird #'s of dimensions. Use common vector classes like Vector3 instead if you can, as they are faster and more featured.
 *  Immutable
 */
public class HighDimVector implements Vector<HighDimVector>{
    final private double[] components;

    /**
     * Create a high dimensional vector from components. The dimensionality of the vector is specified by the # of components.
     */
    public HighDimVector(double... values){
        components = values;
    }



    /**
     * Get # of components
     */
    int getDimensions(){
        return components.length;
    }

    /**
     * Important. This makes sure the vector can be properly compared
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //Check for reference equality
        if (o == null || getClass() != o.getClass()) return false; //Make sure they are the same class
        HighDimVector vec = (HighDimVector) o;
        if(getDimensions() != vec.getDimensions()){return false;} //Check dimensions
        //Check components
        for (int i = 0; i < getDimensions(); i++) {
            if(Double.compare(vec.components[i], components[i]) != 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Auto generated hash code(why not)
     */
    @Override
    public int hashCode() {
        return Objects.hash((Object) components); //Just hashes whole array, not each component. (Oh well)
    }


    /**
     * Add corresponding components
     * Assumes same dim
     * @return this + b
     */
    @Override
    public HighDimVector add(HighDimVector b){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i] + b.components[i];
        }
        return new HighDimVector(out);
    }

    /**
     * Subtract corresponding components
     * Assumes same dim
     * @return this - b
     */
    @Override
    public HighDimVector subtract(HighDimVector b){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i] - b.components[i];
        }
        return new HighDimVector(out);
    }

    /**
     * Multiply corresponding components
     * Assumes same dim
     * @return this * b
     */
    @Override
    public HighDimVector multiply(HighDimVector b){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i] * b.components[i];
        }
        return new HighDimVector(out);
    }

    /**
     * Divide corresponding components
     * Assumes same dim
     * @return this / b
     */
    @Override
    public HighDimVector divide(HighDimVector b){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i] / b.components[i];
        }
        return new HighDimVector(out);
    }

    /**
     * Get the dot or scalar product
     */
    @Override
    public double dot(HighDimVector other) {
        double out = 0;
        for (int i = 0; i < getDimensions(); i++) {
            out += components[i] * other.components[i];
        }
        return out;
    }

    /**
     * Get random vector whose components are in the range of (0)-(Corresponding component in this vector exclusive)
     * @return new vec3(rand*x, rand*y, rand*z)
     */
    @Override
    public HighDimVector randomRange(Random random) {
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = random.nextDouble() * components[i];
        }
        return new HighDimVector(out);
    }

    /**
     * Scalar addition
     */
    @Override
    public HighDimVector add(double scalar){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i]  + scalar;
        }
        return new HighDimVector(out);
    }

    /**
     * Scalar subtraction
     */
    @Override
    public HighDimVector subtract(double scalar){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i]  - scalar;
        }
        return new HighDimVector(out);
    }

    /**
     * Scalar multiplication
     */
    @Override
    public HighDimVector multiply(double scalar){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i]  * scalar;
        }
        return new HighDimVector(out);
    }

    /**
     * Scalar division
     */
    @Override
    public HighDimVector divide(double scalar){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = components[i]  / scalar;
        }
        return new HighDimVector(out);
    }

    /**
     * Get magnitude of vector
     */
    @Override
    public double length(){
        double out = 0;
        for (int i = 0; i < getDimensions(); i++) {
            out += components[i]  * components[i];
        }
        return Math.sqrt(out);
    }

    /**
     * Get distance between this vector and other vector
     */
    @Override
    public double distance(HighDimVector other){
        return (this.subtract(other)).length();
    }

    /**
     * Get normalized vector
     * @return Unit vector
     */
    @Override
    public HighDimVector normalized(){
        return this.divide(new HighDimVector(length()));
    }

    /**
     * Clamp each component between min and max values both inclusive
     * @return clamped vector
     */
    public HighDimVector clamped(double min, double max){
        double[] out = new double[getDimensions()];
        for (int i = 0; i < getDimensions(); i++) {
            out[i] = Math.min(Math.max(components[i],min),max);
        }
        return new HighDimVector(out);
    }


}
