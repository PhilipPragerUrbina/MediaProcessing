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
     * @param threshold Maximum error for a straight line
     * @param  num_corners How many corners to detect.
     */
    public Point[] detectCorners(double threshold, int num_corners){
        Point[] corners = new Point[num_corners];
        ArrayList<Edge> edges = new ArrayList<>();
        //First we need to detect sides
        Vector2 last_slope = new Vector2(outline.get(0).subtract(outline.get(1)));
        Point start = outline.get(0);
        int count = 0;
        for (int i = 1; i < outline.size(); i++) {
            Vector2 slope = new Vector2(outline.get(i-1).subtract(outline.get(i)));
            if(slope.distance(last_slope) > threshold){ //Significant difference
                //create edge if big enough
                if(count > 3){
                    edges.add(new Edge(start,outline.get(i),count));
                }
                count = 0;
                start = outline.get(i);
            }
            count++;
            last_slope =slope;
        }
        //Get N largest edges
        if(edges.size() < num_corners){
            num_corners = edges.size();
            corners = new Point[num_corners];
        }
        edges.sort(Comparator.comparing(Edge::getLength));//Increasing order

        for (int i = edges.size()-1; i >= edges.size()-num_corners; i--) { //Loop through largest except 1
            Edge current = edges.get(i);
            //get the closest connecting points
            Vector2 closest = new Vector2();
            double closest_dist = 1000000000;
            for (int j = edges.size()-1; j >= edges.size()-num_corners; j--) { //Loop through largest
                if(j == i){
                    continue;
                }
                Edge other = edges.get(j);
                if(new Vector2(other.a).distance(new Vector2(current.b)) < closest_dist){ //Compare end to start
                    closest = new Vector2(other.a);
                    closest_dist = new Vector2(other.a).distance(new Vector2(current.b)) ;
                }
            }
            corners[edges.size() - i -1] = new Point(closest.add(new Vector2(current.b)).divide(2.0));//take average
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
