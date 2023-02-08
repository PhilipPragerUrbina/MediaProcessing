package MediaProcessing.Utils.Vectors;

import java.util.Random;

/**
 * A 2d coordinate
 * immutable
 */
public class Point implements Vector<Point>{
    private int x,y; //Coordinates

    /**
     * Create a coordinate
     */
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Create a point from a vector. Will cast components to int.
     */
    public Point(Vector2 vector){
        this((int)(vector.getX()),(int)(vector.getY()));
    }

    /**
     * Get the x component
     */
    public int getX(){
        return x;
    }

    /**
     * Get the y component
     */
    public int getY(){
        return y;
    }

    @Override
    public double distance(Point other) {
        return Math.sqrt(length());
    }

    @Override
    public double length() {
        return x*x + y*y;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point normalized() {
        return null;
    }


    @Override
    public Point add(Point other) {
        return new Point(x + other.x,y + other.y);
    }

    @Override
    public Point subtract(Point other) {
        return new Point(x - other.x,y - other.y);
    }


    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point multiply(Point other) {
        return null;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point divide(Point other) {
        return null;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public double dot(Point other) {
        return 0;
    }


    @Override
    public Point randomRange(Random random) {
        return new Point((int)(random.nextDouble() * x),(int)(random.nextDouble() * y));
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point multiply(double scalar) {
        return null;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point add(double scalar) {
        return null;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point divide(double scalar) {
        return null;
    }

    /**
     * Not practical for integer coordinates
     */
    @Override
    public Point subtract(double scalar) {
        return null;
    }


    @Override
    public Point clamped(double min, double max) {
        return new Point((int)Math.min(Math.max(x,min),max),(int)Math.min(Math.max(y,min),max));
    }
}
