package MediaProcessing.Tracking;

import MediaProcessing.Data.Image;
import MediaProcessing.Utils.Vectors.Point;
import MediaProcessing.Utils.Vectors.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents an outline of a shape
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
     * Detect corners of outline by approximating polygon
     * @param epsilon Maximum distance of approximated shape from actual shape
     * @return List of corners
     */
    public ArrayList<Point> detectCorners(double epsilon){
        ArrayList<Point> corners = (ArrayList<Point>)approximatePolygon(outline,epsilon);
        corners.remove(corners.size()-1);//remove last duplicate corner
        return corners;
    }

    /**
     * Use Douglas Peucker algorithm to approximate shape(very cool)
     * @param points Points to far(recursive)
     * @param epsilon Maximum distance of approximated shape from actual shape
     * @return Points
     * Cool reference: https://cartography-playground.gitlab.io/playgrounds/douglas-peucker-algorithm/
     */
    private static List<Point> approximatePolygon(List<Point> points, double epsilon){
        //Get maximum distance of the line from the first to last point, and the approximated points to far

        double max_distance = 0;
        int max_distance_point = 0; //Index of max
        int last = points.size()-1; //Last element

        for (int i = 1; i < last; i++) { //Do not include first and last
            double distance = linePerpendicularDistance(points.get(i), points.get(0), points.get(last)); //Get distance perpendicular to line
            if(distance > max_distance){
                max_distance = distance;
                max_distance_point = i;
            }
        }

        ArrayList<Point> output = new ArrayList<>();

        if(max_distance > epsilon){ //Keep dividing
            //Recursively divide
            List<Point> a = approximatePolygon( points.subList(0, max_distance_point+1),epsilon);
            List<Point> b = approximatePolygon( points.subList(max_distance_point, last+1),epsilon);
            //Put list together, not duplicating middle point
            output.addAll(a.subList(0,a.size()-1));
            output.addAll(b);
        }else { //Do not divide(close enough)
            output.add(points.get(0)); //Return first and last point
            output.add(points.get(last));
        }

        return output;
    }

    /**
     * Get the perpendicular distance from a point to a line
     * @param point Point
     * @param line_a, line_b Two points on the line
     */
    private static double linePerpendicularDistance(Point point, Point line_a, Point line_b) {
        //Find general form line
        double a = line_b.getY()-line_a.getY();
        double b = line_a.getX()-line_b.getX();
        double c = line_a.getY()*line_b.getX()-line_a.getX()*line_b.getY();
        //Get perpendicular distance
        return Math.abs(a*point.getX()+b*point.getY()+c)/Math.sqrt(a*a+b*b);
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

    /**
     * Get # of points
     */
    public int size() {
        return outline.size();
    }
}
