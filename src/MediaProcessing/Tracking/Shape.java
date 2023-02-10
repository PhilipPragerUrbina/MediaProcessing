package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector2;

import java.util.ArrayList;
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
     * Get estimated corner positions
     * @param threshold Maximum error for a straight line
     * @param  num_corners How many corners to detect.
     */
    Point[] detectCorners(double threshold, int num_corners){
        Point[] corners = new Point[num_corners];


        //First we need to detect sides
        Vector2 last_slope = new Vector2(outline.get(0).subtract(outline.get(1)));
        for (int i = 1; i < outline.size(); i++) {
            Vector2 slope = new Vector2(outline.get(i-1).subtract(outline.get(i)));
            if(slope.distance(last_slope) > threshold){
                //create edge

            }
        }
//todo finish
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
