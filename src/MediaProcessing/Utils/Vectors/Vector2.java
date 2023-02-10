package MediaProcessing.Utils.Vectors;

import MediaProcessing.Utils.Colors.Color;
import MediaProcessing.Utils.Colors.RGBA;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Random;

/**
 * Two component double vector, useful for color calculations representing positions
 *  Immutable
 */
public class Vector2 implements Vector<Vector2>{
    final private double x,y; //Components

    /**
     * Null vector (0,0,0)
     */
    public Vector2(){
        this(0);
    }

    /**
     * Create vector
     * @param x,y Components
     */
    public Vector2(double x, double y){
        this.x = x; this.y = y;
    }

    /**
     * Create a vector from a point
     */
    public Vector2(Point point){
        this(point.getX(), point.getY());
    }


    /**
     * Create vector from scalar values
     * @param scalar Set all components to
     */
    public Vector2(double scalar){
        this(scalar,scalar);
    }



    /**
     * Important. Make sure vector can be properly compared
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //Check for reference equality
        if (o == null || getClass() != o.getClass()) return false; //Make sure they are the same class
        Vector2 vector3 = (Vector2) o;
        return Double.compare(vector3.x, x) == 0 && Double.compare(vector3.y, y) == 0; //Compare each component
        //Uses proper Double.compare which is better than == since it takes into account some floating point specific things
    }

    /**
     * Auto generated hash code(why not)
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }



    /**
     * Add corresponding components
     * @return this + b
     */
    @Override
    public Vector2 add(Vector2 b){
        return new Vector2(x + b.x, y + b.y);
    }

    /**
     * Subtract corresponding components
     * @return this - b
     */
    @Override
    public Vector2 subtract(Vector2 b){
        return new Vector2(x - b.x, y - b.y);
    }

    /**
     * Multiply corresponding components
     * @return this * b
     */
    @Override
    public Vector2 multiply(Vector2 b){
        return new Vector2(x * b.x, y * b.y);
    }

    /**
     * Divide corresponding components
     * @return this / b
     */
    @Override
    public Vector2 divide(Vector2 b){
        return new Vector2(x / b.x, y / b.y);
    }

    /**
     * Get the dot or scalar product
     */
    @Override
    public double dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    /**
     * Get random vector whose components are in the range of (0)-(Corresponding component in this vector exclusive)
     * @return new vec3(rand*x, rand*y)
     */
    @Override
    public Vector2 randomRange(Random random) {
        return new Vector2(random.nextDouble() * x, random.nextDouble() * y);
    }

    @Override
    public Vector2 multiply(double scalar) {
        return new Vector2(x * scalar,y*scalar);
    }

    @Override
    public Vector2 add(double scalar) {
        return new Vector2(x + scalar,y+scalar);
    }

    @Override
    public Vector2 divide(double scalar) {
        return new Vector2(x / scalar,y/scalar);
    }

    @Override
    public Vector2 subtract(double scalar) {
        return new Vector2(x - scalar,y-scalar);
    }

    /**
     * Get magnitude of vector
     */
    @Override
    public double length(){
        return Math.sqrt(x*x + y*y );
    }

    /**
     * Get distance between this vector and other vector
     */
    @Override
    public double distance(Vector2 other){
        return (this.subtract(other)).length();
    }

    /**
     * Get normalized vector
     * @return Unit vector
     */
    @Override
    public Vector2 normalized(){
        return this.divide(new Vector2(length()));
    }

    /**
     * Clamp each component between min and max values both inclusive
     * @return clamped vector
     */
    @Override
    public Vector2 clamped(double min, double max){
        return new Vector2(Math.min(Math.max(x,min),max),Math.min(Math.max(y,min),max));
    }

    /**
     * get x component
     */
    public double getX(){
        return x;
    }

    /**
     * get y component
     */
    public double getY(){
        return y;
    }
}
