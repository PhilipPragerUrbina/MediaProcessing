package MediaProcessing.Utils.Vectors;

import MediaProcessing.Utils.Colors.RGBA;

import java.util.Objects;
import java.util.Random;

/**
 * Three component double vector, useful for color calculations that are not possible with shorts or representing positions
 *  Immutable
 */
public class Vector3 implements Vector<Vector3>{
    final private double x,y,z; //Components

    /**
     * Null vector (0,0,0)
     */
    public Vector3(){
        this(0);
    }

    /**
     * Create vector
     * @param x,y,z Components
     */
    public Vector3(double x, double y, double z){
        this.x = x; this.y = y; this.z = z;
    }

    /**
     * Create vector from scalar values
     * @param scalar Set all components to
     */
    public Vector3(double scalar){
        this(scalar,scalar,scalar);
    }

    /**
     * Create vector from RGB values of an RGBA color.
     * @param color Color to get values from
     */
    public Vector3(RGBA color){
        this(color.getR(), color.getG(), color.getB());
    }

    /**
     * Important. Make sure vector can be properly compared
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //Check for reference equality
        if (o == null || getClass() != o.getClass()) return false; //Make sure they are the same class
        Vector3 vector3 = (Vector3) o;
        return Double.compare(vector3.x, x) == 0 && Double.compare(vector3.y, y) == 0 && Double.compare(vector3.z, z) == 0; //Compare each component
        //Uses proper Double.compare which is better than == since it takes into account some floating point specific things
    }

    /**
     * Auto generated hash code(why not)
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * Get RGBA color from vector
     * @param a Alpha channel
     * @return rgba color with components (x,y,z,a) cast to short
     */
    public RGBA getColor(short a){
        return new RGBA((short)x,(short)y,(short)z,a);
    }

    /**
     * Add corresponding components
     * @return this + b
     */
    @Override
    public Vector3 add(Vector3 b){
        return new Vector3(x + b.x, y + b.y, z + b.z);
    }

    /**
     * Subtract corresponding components
     * @return this - b
     */
    @Override
    public Vector3 subtract(Vector3 b){
        return new Vector3(x - b.x, y - b.y, z - b.z);
    }

    /**
     * Multiply corresponding components
     * @return this * b
     */
    @Override
    public Vector3 multiply(Vector3 b){
        return new Vector3(x * b.x, y * b.y, z * b.z);
    }

    /**
     * Divide corresponding components
     * @return this / b
     */
    @Override
    public Vector3 divide(Vector3 b){
        return new Vector3(x / b.x, y / b.y, z / b.z);
    }

    /**
     * Get the dot or scalar product
     */
    @Override
    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z*other.z;
    }

    /**
     * Get random vector whose components are in the range of (0)-(Corresponding component in this vector exclusive)
     * @return new vec3(rand*x, rand*y, rand*z)
     */
    @Override
    public Vector3 randomRange(Random random) {
        return new Vector3(random.nextDouble() * x, random.nextDouble() * y, random.nextDouble() * z);
    }

    @Override
    public Vector3 multiply(double scalar) {
        return new Vector3(x * scalar,y*scalar,z*scalar);
    }

    @Override
    public Vector3 add(double scalar) {
        return new Vector3(x + scalar,y+scalar,z+scalar);
    }

    @Override
    public Vector3 divide(double scalar) {
        return new Vector3(x / scalar,y/scalar,z/scalar);
    }

    @Override
    public Vector3 subtract(double scalar) {
        return new Vector3(x - scalar,y-scalar,z-scalar);
    }

    /**
     * Get magnitude of vector
     */
    @Override
    public double length(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Get distance between this vector and other vector
     */
    @Override
    public double distance(Vector3 other){
        return (this.subtract(other)).length();
    }

    /**
     * Get normalized vector
     * @return Unit vector
     */
    @Override
    public Vector3 normalized(){
        return this.divide(new Vector3(length()));
    }


}
