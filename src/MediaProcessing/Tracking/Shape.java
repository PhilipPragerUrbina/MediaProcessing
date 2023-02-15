package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Represents an outline of a shape
 * Immutable
 */
public class Shape {
    private final ArrayList<Point> outline = new ArrayList<>();

    /**
     * At a point to the outline of the shape
     * It is expected to be adjacent to last point and eventually form a full enclosed shape
     */
    void addPoint(Point point){
        outline.add(point);
    }

    /**
     * Get the center of the shape
     */
    Point getCenter(){
        Point average = new Point(0,0);
        for ( Point point: outline) {
           average =  average.add(point);
        }
        return average.divide(outline.size()); //Get the average position
    }

    /**
     * Simple edge struct
     */
    private class Edge{
        public Edge(Point a, Point b, int length) {
            this.a = a;
            this.b = b;
            this.length = length;
        }
        public int length;
        public Point a;
        public Point b;
        public  int getLength(){
            return length; //Number of pixels long
        }
    }



    /**
     * Get estimated corner positions
     * @param threshold Maximum error for a straight line in radians
     * @param  num_corners How many corners to detect.
     */
    public ArrayList<Point> detectCorners(double threshold, int num_corners){
        ArrayList<Point> corners = new ArrayList<>();
        int step = 5;
        if(outline.size() < step+1){
            return corners;
        }
        //First we need to detect sides
        Vector2 last_slope = new Vector2(outline.get(0).subtract(outline.get(step)));
        int count = 0;
        for (int i = step; i < outline.size(); i+=step) {
            Vector2 slope = new Vector2(outline.get(i-step).subtract(outline.get(i)));
            if(Math.abs(Math.atan2(slope.getX(), slope.getY())-Math.atan2(last_slope.getX(), last_slope.getY())) > threshold){ //Significant difference
                //create edge if big enough
                if(count > 3){
                    corners.add(outline.get(i));
                }
                last_slope = slope;
                count = 0;
            }
            count++;
            last_slope =  ( last_slope.multiply((count-1)).add( slope)).divide(count );
        }
        return corners;
    }

    /**
     * Do something for each data point
     * @param consumer Operation
     */
    public void forEach(Consumer<Point> consumer){
        for (Point point: outline) {
            consumer.accept(point);
        }
    }

    public int size() {
        return outline.size();
    }
}
